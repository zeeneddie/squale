//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\util\\csv\\CSVBeanInstantiator.java

package com.airfrance.squalix.util.csv;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Permet d'instancier des objets, et de manipuler les appels de méthodes sur ceux-ci.
 * 
 * @author m400842
 * @version 1.0
 */
public class CSVBeanInstanciator
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CSVBeanInstanciator.class );

    /**
     * Instancie un objet de la classe identifiée par la paramètre, en utilisant le constructeur sans paramètre.
     * 
     * @param pClassName nom de la classe pleinement qualifié.
     * @return une instance de CSVBean
     * @exception Exception si un problème apparaît.
     * @roseuid 42942CA70075
     */
    public Object instanciate( String pClassName )
        throws Exception
    {
        Class objClass = Class.forName( pClassName );
        Object bean = objClass.newInstance();
        return bean;
    }

    /**
     * Invoque la méthode de l'objet <code>pBean</code> désignée par <code>pMethod</code> en passant en paramètre
     * une instance de la classe <code>pType</code> contenant la valeur <code>pValue.<br>
     * La classe <code>pType</code> doit posséder un constructeur prenant en paramètre 
     * une <code>String</code>.<br>
     * Si un problème survient, le bean est retourné tel que reçu.
     * 
     * @param pBean Le bean à modifier.
     * @param pMethod Le champ.
     * @param pValue Le type objet du champ.
     * @return le bean modifié.
     * @throws Exception si un problème d'affectation apparaît
     * @roseuid 42942D2D0077
     */
    public Object setValue( final Object pBean, final Method pMethod, final String pValue )
        throws Exception
    {
        Object value = getValue( pMethod.getParameterTypes()[0], pValue );
        if ( null != value )
        {
            Object values[] = { value };
            pMethod.invoke( pBean, values );
        }
        return pBean;
    }

    /**
     * Crée une instance de <code>pObjClass</code> avec la valeur initiale <code>pValue</code>.<br>
     * La classe <code>pType</code> doit posséder un constructeur prenant en paramètre une <code>String</code>.
     * 
     * @param pObjClass la classe de l'objet à instancier
     * @param pValue la valeur initiale
     * @return un objet initialisé à pValue
     * @roseuid 42B2BC3500E0
     */
    private Object getValue( final Class pObjClass, final String pValue )
    {
        Class[] types = { String.class };
        Object[] values = { pValue };
        Object field = null;
        if ( null != pValue )
        {
            try
            {
                field = pObjClass.getConstructor( types ).newInstance( values );
            }
            catch ( Exception e )
            {
                // Les exceptions attrapées ici n'ont rien de grave,
                // elles sont dues à une différence entre le type attendu et le type
                // réel du paramètre...
                LOGGER.debug( e, e );
                field = null;
            }
        }
        return field;
    }

    /**
     * Constructeur par défaut.
     * 
     * @roseuid 42CE6C6C012C
     */
    public CSVBeanInstanciator()
    {
    }
}
