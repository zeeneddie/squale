package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Représente un composant du projet au sens implémentation.
 * 
 * @author m400842
 * @hibernate.class table="Component" mutable="true" lazy="true" discriminator-value="AbstractComponent"
 * @hibernate.discriminator column="subclass"
 */
public abstract class AbstractComponentBO
    implements Serializable
{

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Nom du composant
     */
    protected String mName;

    /**
     * Le composant parent
     */
    protected AbstractComplexComponentBO mParent;

    /**
     * Le projet auquel appartient le Composant
     */
    protected AbstractComplexComponentBO mProject;

    /**
     * Collection des audits liés à ce composant
     */
    protected Collection mAudits;

    /** l'éventuelle justification associée au composant */
    private String justification;

    /** un booléen permettant de savoir si le composant est à exclure du plan d'aciton */
    private boolean excludedFromActionPlan;

    /**
     * @return le booléen indiquant si le composant courant est exclu du plan d'action
     * @hibernate.property name="excludedFromActionPlan" update="true" insert="true" column="Excluded" type="boolean"
     *                     not-null="true" unique="false"
     */
    public boolean getExcludedFromActionPlan()
    {
        return excludedFromActionPlan;
    }

    /**
     * @return la justification du composant
     * @hibernate.property name="justification" update="true" insert="true" column="Justification" type="string"
     *                     length="4000" not-null="false" unique="false"
     */
    public String getJustification()
    {
        return justification;
    }

    /**
     * @param pExcluded le booléen indiquant si il faut exclure le composant ou pas
     */
    public void setExcludedFromActionPlan( boolean pExcluded )
    {
        excludedFromActionPlan = pExcluded;
    }

    /**
     * @param pJustification la nouvelle valeur de la justification
     */
    public void setJustification( String pJustification )
    {
        justification = pJustification;
    }

    /**
     * Récupère la clé correspondant au composant
     * 
     * @return la clé
     */
    public String getType()
    {
        return Mapping.getComponentName( getClass() );
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="name" update="true" insert="true" column="Name" type="string" length="1024"
     *                     not-null="true" unique="false"
     * @roseuid 42BACECB0228
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     * @roseuid 42BACECB0238
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="ComponentId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="component_sequence"
     * @roseuid 42BFCA590082
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFCA5900F0
     */
    public void setId( long pId )
    {
        // mTranscientId = pId;
        mId = pId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setPersistedId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mParent property.
     * 
     * @return le composant parent
     * @hibernate.many-to-one outer-join="auto" update="true" insert="true" column="Parent"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO"
     *                        cascade="save-update"
     * @roseuid 42CB90ED038C
     */
    public AbstractComplexComponentBO getParent()
    {
        return mParent;
    }

    /**
     * Sets the value of the mParent property.
     * 
     * @param pParent le composant parent
     * @roseuid 42CB90EE0050
     */
    // * @throws UnexpectedRelationException si la relation ne peut etre ajouté
    public void setParent( AbstractComplexComponentBO pParent ) /* throws UnexpectedRelationException */
    {
        mParent = pParent;
    }

    /**
     * Sets the value of the mParent property.
     * 
     * @param pParent le composant parent
     * @deprecated
     * @roseuid 42CB90EE0050
     */
    public void setParentForce( AbstractComplexComponentBO pParent )
    {
        mParent = pParent;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CB90EE01B7
     */
    public AbstractComponentBO()
    {
        mId = -1;
        Collection emptyCol = new HashSet();
        setAudits( emptyCol );
    }

    /**
     * Constructeur complet
     * 
     * @param pName le nom du composant
     * @param pParent le composant parent
     * @roseuid 42CB90EE0282
     */
    // * @throws UnexpectedRelationException si la relation ne peut etre ajouté
    public AbstractComponentBO( String pName, AbstractComplexComponentBO pParent ) /*
                                                                                     * throws
                                                                                     * UnexpectedRelationException
                                                                                     */
    {
        mId = -1;
        Collection emptyCol = new HashSet();
        setAudits( emptyCol );
        mName = pName;
        setParent( pParent );
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @roseuid 42E617A903AE
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        AbstractComponentBO component = null;
        if ( pObj instanceof AbstractComponentBO )
        {
            component = (AbstractComponentBO) pObj;
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append( mName, component.getName() );
            // equalsBuilder.append(this.getClass(), pObj.getClass());
            equalsBuilder.append( mId, component.getId() ); // Attention: utilisation de l'id est
            // fortement déconseillée par Hibernate
            ret = equalsBuilder.isEquals();
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @roseuid 42E617A903BD
     */
    public int hashCode()
    {
        HashCodeBuilder hashBuilder = new HashCodeBuilder();
        hashBuilder.append( mName );
        hashBuilder.append( mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return hashBuilder.toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * @roseuid 42E617A903BE
     */
    public String toString()
    {
        ToStringBuilder stringBuilder = new ToStringBuilder( this );
        stringBuilder.append( "Name", mName );
        stringBuilder.append( "Id", mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return stringBuilder.toString();
    }

    /**
     * Récupère les audits liés à ce composant
     * 
     * @return les audits
     * @hibernate.set table="Components_Audits" lazy="true" cascade="none" inverse="false" sort="unsorted"
     * @hibernate.key column="ComponentId"
     * @hibernate.many-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                         column="AuditId" outer-join="auto"
     */
    public Collection getAudits()
    {
        return mAudits;
    }

    /**
     * Affecte les audits liés à ce composant
     * 
     * @param pCollection les audits
     */
    private void setAudits( Collection pCollection )
    {
        mAudits = pCollection;
    }

    /**
     * ajoute un audit à ce composant
     * 
     * @param pAudit l'audit à ajouter
     */
    public void addAudit( AuditBO pAudit )
    {
        mAudits.add( pAudit );
    }

    /**
     * vérifie si un audit est rattaché au composant
     * 
     * @param pIdAudit id de l'audit à vérifier, <b>Attention</b>, si l'id passé est -1, le résultat ne sera pas
     *            valide.
     * @return <code>true</code> si l'audit est rattaché à ce composant
     */
    public boolean containsAuditById( long pIdAudit )
    {
        boolean ret = false;
        Iterator it = getAudits().iterator();
        while ( it.hasNext() && ( false == ret ) )
        {
            AuditBO audit = (AuditBO) it.next();
            if ( audit.getId() == pIdAudit )
            { // si -1 la question se pose...
                ret = true;
            }
        }
        return ret;
    }

    /**
     * @return <code>true</code> si le composant possède un audit terminé
     */
    public boolean hasResults()
    {
        boolean hasResult = false;
        for ( Iterator it = getAudits().iterator(); it.hasNext() && !hasResult; )
        {
            AuditBO audit = (AuditBO) it.next();
            if ( audit.getStatus() == AuditBO.TERMINATED )
            {
                hasResult = true;
            }
        }
        return hasResult;
    }

    /**
     * @return le nom complet du composant
     */
    public String getFullName()
    {
        AbstractComplexComponentBO currentParent = getParent();
        String fullName = getName();
        while ( currentParent != null && !( currentParent instanceof ProjectBO )
            && !( currentParent instanceof ApplicationBO ) )
        {
            fullName = currentParent.getName() + "." + fullName;
            currentParent = currentParent.getParent();
        }
        return fullName;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return le Projet auquel appartient
     * @hibernate.many-to-one column="ProjectId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO"
     *                        outer-join="auto" update="true" insert="true" cascade="none"
     */
    public AbstractComplexComponentBO getProject()
    {
        return mProject;
    }

    /**
     * Met à jour le projet parent
     * 
     * @param componentBO Nouveau projet
     */
    public void setProject( AbstractComplexComponentBO componentBO )
    {
        mProject = componentBO;
    }

    /**
     * @param pVisitor visiteur
     * @param pArgument argument
     * @return objet
     */
    public abstract Object accept( ComponentVisitor pVisitor, Object pArgument );

}
