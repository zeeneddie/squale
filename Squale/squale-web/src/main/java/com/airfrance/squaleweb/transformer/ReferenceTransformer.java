package com.airfrance.squaleweb.transformer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.result.ReferenceFactorDTO;
import com.airfrance.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.FactorRuleDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.reference.ReferenceForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une référence
 */
public class ReferenceTransformer
    implements WITransformer
{
    /**
     * Transforme une liste de résultats de facteurs (les listes doivent directement contenir les valeurs) en un form.
     * 
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ReferenceForm form = new ReferenceForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        int index = 0;
        ReferenceForm form = (ReferenceForm) pForm;
        // La transformation d'une référence se fait soit avec des
        // données provenant du référentiel, soit avec des données
        // provenant des applications de l'utilisateur
        if ( pObject[0] instanceof SqualeReferenceDTO )
        {
            objToForm( (SqualeReferenceDTO) pObject[0], form );
        }
        else
        {
            objToForm( (AuditGridDTO) pObject[++index], (Collection) pObject[++index], (Collection) pObject[++index],
                       (Map) pObject[++index], form );
        }
    }

    /**
     * Conversion des données d'une référence
     * 
     * @param pReference l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    private void objToForm( SqualeReferenceDTO pReference, ReferenceForm pForm )
        throws WTransformerException
    {
        pForm.setId( pReference.getId() );
        pForm.setApplicationName( pReference.getApplicationName() );
        pForm.setHidden( pReference.getHidden() );
        pForm.setPublic( pReference.isPublic() );
        pForm.setName( pReference.getProjectName() );
        pForm.setTechnology( pReference.getLanguage() );
        pForm.setAuditType( pReference.getAuditType() );
        Iterator values = pReference.getFactorValues().iterator();
        // Traitement des valeurs de chacun des facteurs
        while ( values.hasNext() )
        {
            ReferenceFactorDTO dto = (ReferenceFactorDTO) values.next();
            pForm.addFactor( objToForm( dto, dto.getValue() ) );
        }
        pForm.setNumberOfClasses( Integer.toString( pReference.getClassNumber() ) );
        pForm.setNumberOfCodeLines( Integer.toString( pReference.getCodeLineNumber() ) );
        pForm.setNumberOfMethods( Integer.toString( pReference.getMethodNumber() ) );
    }

    /**
     * Conversion des données d'un projet
     * 
     * @param pAuditGrid audit et grille associée
     * @param pValues valeurs
     * @param pApplications applications
     * @param pApplicationMeasures mesures sur les applications
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    private void objToForm( AuditGridDTO pAuditGrid, Collection pValues, Collection pApplications,
                            Map pApplicationMeasures, ReferenceForm pForm )
        throws WTransformerException
    {

        Iterator values = pValues.iterator();
        pForm.setId( pAuditGrid.getProject().getID() );
        pForm.setApplicationName( TransformerUtils.getApplicationName( pAuditGrid.getProject().getIDParent(),
                                                                       pApplications ) );
        pForm.setName( pAuditGrid.getProject().getName() );
        pForm.setTechnology( pAuditGrid.getProject().getTechnology() );
        pForm.setAuditType( pAuditGrid.getAudit().getType() );
        // Parcours de la grille et des valeurs associées
        Iterator factors = pAuditGrid.getGrid().getFactors().iterator();
        while ( factors.hasNext() )
        {
            FactorRuleDTO dto = (FactorRuleDTO) factors.next();
            Float value = (Float) values.next();
            pForm.addFactor( objToForm( dto, value ) );
        }
        List measuresKeys = (List) pApplicationMeasures.get( null );
        List projectMeasures = (List) pApplicationMeasures.get( pAuditGrid.getProject() );
        // Traitement de chacune des mesures
        if ( projectMeasures != null )
        {
            // On protège le code dans le cas où tous les calculs n'auraient pas été fait
            int index = measuresKeys.indexOf( "mccabe.project.numberOfClasses" );
            if ( index != -1 )
            {
                Integer nbClasses = (Integer) projectMeasures.get( index );
                pForm.setNumberOfClasses( nbClasses + "" );
            }
            index = measuresKeys.indexOf( "rsm.project.sloc" );
            if ( index != -1 )
            {
                Integer nbLOC = (Integer) projectMeasures.get( index );
                pForm.setNumberOfCodeLines( nbLOC + "" );
            }
            index = measuresKeys.indexOf( "mccabe.project.numberOfMethods" );
            if ( index != -1 )
            {
                Integer nbMethods = (Integer) projectMeasures.get( index );
                pForm.setNumberOfMethods( nbMethods + "" );
            }
        }
    }

    /**
     * Conversion d'une note de facteur
     * 
     * @param pFactorDTO facteur
     * @param pValue valeur
     * @return form
     * @throws WTransformerException si erreur
     */
    private WActionForm objToForm( FactorRuleDTO pFactorDTO, Float pValue )
        throws WTransformerException
    {
        Object[] params;
        // Une valeur peut être affectée ou pas
        if ( ( pValue != null ) && ( pValue.floatValue() != -1 ) )
        {
            params = new Object[] { pFactorDTO, "" + pValue.floatValue() };
        }
        else
        {
            params = new Object[] { pFactorDTO };
        }
        return WTransformerFactory.objToForm( ProjectFactorTransformer.class, params );

    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] result = new Object[] { new SqualeReferenceDTO() };
        formToObj( pForm, result );
        return result;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        SqualeReferenceDTO dto = (SqualeReferenceDTO) pObject[0];
        ReferenceForm form = (ReferenceForm) pForm;
        dto.setAuditType( form.getAuditType() );
        dto.setId( form.getId() );
        dto.setHidden( form.isHidden() );
        dto.setApplicationName( form.getApplicationName() );
        dto.setProjectName( form.getName() );
    }

}
