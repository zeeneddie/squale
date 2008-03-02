package com.airfrance.squaleweb.applicationlayer.formbean.reference;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.applicationlayer.formbean.ActionIdFormSelectable;

/**
 * Contient une référence du portail Squale.
 * 
 * @author M400842
 */
public class ReferenceForm
    extends ActionIdFormSelectable
{

    // Constantes pour l'affichage

    /**
     * Masqué
     */
    public final static String HIDDEN = "reference.hidden";

    /**
     * Affiché
     */
    public final static String DISPLAYED = "reference.displayed";

    /**
     * Caractère public
     */
    private boolean mPublic;

    /** le type de l'audit */
    private String mAuditType;

    /**
     * Liste des facteurs du referentiel
     */
    private Collection mFactors = new ArrayList();

    /**
     * Technologie de la référence
     */
    private String mTechnology;

    /**
     * Nombre de classes de la référence
     */
    private String mNumberOfClasses;

    /**
     * Nombre de méthodes de la référence
     */
    private String mNumberOfMethods;

    /**
     * Nombre de lignes de codes
     */
    private String mNumberOfCodeLines;

    /**
     * Nom du la référence
     */
    private String mName = "";

    /**
     * Spécifie si la référence est cachée
     */
    private boolean mHidden = false;

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return le nombre de classes
     */
    public String getNumberOfClasses()
    {
        return mNumberOfClasses;
    }

    /**
     * @return la technologie
     */
    public String getTechnology()
    {
        return mTechnology;
    }

    /**
     * @return le nombre de méthodes
     */
    public String getNumberOfMethods()
    {
        return mNumberOfMethods;
    }

    /**
     * @return le nombre de lignes de codes
     */
    public String getNumberOfCodeLines()
    {
        return mNumberOfCodeLines;
    }

    /**
     * @param pName le nom
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pNumberOfClasses le nombre de classes
     */
    public void setNumberOfClasses( String pNumberOfClasses )
    {
        mNumberOfClasses = pNumberOfClasses;
    }

    /**
     * @param pTechnology la technologie
     */
    public void setTechnology( String pTechnology )
    {
        mTechnology = pTechnology;
    }

    /**
     * @param pNumberOfMethods le nombre de méthodes
     */
    public void setNumberOfMethods( String pNumberOfMethods )
    {
        mNumberOfMethods = pNumberOfMethods;
    }

    /**
     * @param pNumberOfCodeLines le nombre de lignes de code
     */
    public void setNumberOfCodeLines( String pNumberOfCodeLines )
    {
        mNumberOfCodeLines = pNumberOfCodeLines;
    }

    /**
     * @return l'état de validation de la référence
     */
    public boolean isHidden()
    {
        return mHidden;
    }

    /**
     * @param pHidden indique si la référence est masquée ou pas
     */
    public void setHidden( boolean pHidden )
    {
        mHidden = pHidden;
    }

    /**
     * @return Valeur courante de mFactors
     */
    public Collection getFactors()
    {
        return mFactors;
    }

    /**
     * @param pPublic caractère public
     */
    public void setPublic( boolean pPublic )
    {
        mPublic = pPublic;
    }

    /**
     * @return caractère public
     */
    public boolean isPublic()
    {
        return mPublic;
    }

    /**
     * @param pFactor facteur
     */
    public void addFactor( Object pFactor )
    {
        mFactors.add( pFactor );

    }

    /**
     * @return le type de l'audit
     */
    public String getAuditType()
    {
        return mAuditType;
    }

    /**
     * @param pType le type de l'audit
     */
    public void setAuditType( String pType )
    {
        mAuditType = pType;
    }

    /**
     * string décrivant l'état, sert juste pour l'affichage
     */
    private String mState = "";

    /**
     * @param pState le nouvel état
     */
    public void setState( String pState )
    {
        mState = pState;
    }

    /**
     * Méthode renvoyant la string décrivant l'état, sert juste pour l'affichage
     * 
     * @return l'état
     */
    public String getState()
    {
        return mState;
    }

}
