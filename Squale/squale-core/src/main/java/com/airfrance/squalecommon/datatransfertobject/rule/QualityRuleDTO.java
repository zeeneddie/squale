package com.airfrance.squalecommon.datatransfertobject.rule;

/**
 * DTO d'une règle qualité
 */
public class QualityRuleDTO
    implements Comparable
{
    /** Identificateur */
    private long mId;

    /** Nom du facteur */
    private String mName;

    /**
     * la clé permettant de récupérer l'aide associée à la pratique
     */
    private String helpKey;

    /**
     * @return name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pString name
     */
    public void setName( String pString )
    {
        mName = pString;
    }

    /**
     * @return id
     */
    public long getId()
    {
        return mId;
    }

    /**
     * @param pL id
     */
    public void setId( long pL )
    {
        mId = pL;
    }

    /**
     * @return la clé de l'aide
     */
    public String getHelpKey()
    {
        return helpKey;
    }

    /**
     * @param newKey la nouvelle clé
     */
    public void setHelpKey( String newKey )
    {
        helpKey = newKey;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object o )
    {
        int result = 0;
        if ( o instanceof QualityRuleDTO )
        {
            QualityRuleDTO rule = (QualityRuleDTO) o;
            if ( ( rule.getName() != null ) && ( getName() != null ) )
            {
                result = getName().compareTo( rule.getName() );
            }
        }
        return result;
    }

}
