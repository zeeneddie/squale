package com.airfrance.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.airfrance.squalecommon.datatransfertobject.component.AuditGridDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * @author M400842 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProjectFactorsTransformer
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
        ProjectFactorsForm form = new ProjectFactorsForm();
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
        List dto = (List) pObject[1];
        ProjectFactorsForm form = (ProjectFactorsForm) pForm;
        // Obtention des résultats
        Map.Entry curResults = (Entry) dto.get( 0 );
        // Obtention de la grille
        QualityGridDTO curGrid = ( (AuditGridDTO) curResults.getKey() ).getGrid();
        // Obtention des facteurs
        Collection factorsKey = curGrid.getFactors();
        // Obtention des notes
        Collection valeurs = SqualeWebActionUtils.getAsStringsList( (List) curResults.getValue() );
        // Anciennes valeurs
        Map.Entry predResults = null;
        List predMarks = null;
        Iterator marks = null;
        if ( dto.size() > 1 )
        {
            predResults = (Entry) dto.get( 1 );
            // Vérification de la cohérence des grilles
            // pour le calcul des tendances
            QualityGridDTO predGrid = ( (AuditGridDTO) predResults.getKey() ).getGrid();
            if ( predGrid.getId() == curGrid.getId() )
            {
                form.setComparableAudits( true );
                predMarks = SqualeWebActionUtils.getAsStringsList( (List) predResults.getValue() );
                if ( predResults != null && predMarks != null && predMarks.size() > 0 )
                {
                    marks = predMarks.iterator();
                }
            }
        }
        ArrayList factorForms = new ArrayList();
        // Conversion de chacun des facteurs
        Iterator factor = factorsKey.iterator();
        Iterator value = valeurs.iterator();
        // Conversion de chacun des facteurs
        while ( factor.hasNext() )
        {
            Object[] params;
            // Dans ce cas il ,n'y a pas de note précédente
            if ( null == marks )
            {
                // il y a une valeur pour ce factor
                if ( value.hasNext() )
                {
                    params = new Object[] { factor.next(), value.next() };
                }
                else
                { // Pas de valeur
                    params = new Object[] { factor.next() };
                }
            }
            else
            {
                // Iterator marks = predMarks.iterator();
                params = new Object[] { factor.next(), value.next(), marks.next() };
            }
            factorForms.add( WTransformerFactory.objToForm( ProjectFactorTransformer.class, params ) );
        }
        form.setFactors( factorForms );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
    }
}
