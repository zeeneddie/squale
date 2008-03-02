/*
 * Créé le 25 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.transformer;

import java.util.HashMap;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WTransformerFactory
{

    /** Liste des transformers instancié */
    private static HashMap factoryMap = new HashMap();

    /**
     * Recupere le tranformer en fonction du nom de la classe Le met en cache pour le reutiliser ....
     * 
     * @param transformerClass Nom de la classe du transformer
     * @return Le transformer
     * @throws WTransformerException Probleme sur le transformer, ne peut le creer
     */
    public static WITransformer getSingleTransformer( final Class transformerClass )
        throws WTransformerException
    {
        final String className = transformerClass.getName();

        synchronized ( factoryMap )
        {
            if ( !factoryMap.containsKey( className ) )
            {
                Object instance = null;

                try
                {
                    instance = transformerClass.newInstance();
                }
                catch ( final InstantiationException e )
                {
                    throw new WTransformerException( e );
                }
                catch ( final IllegalAccessException e )
                {
                    throw new WTransformerException( e );
                }

                if ( !( instance instanceof WITransformer ) )
                {
                    throw new WTransformerException( transformerClass + " must implement WITransformer" );
                }

                factoryMap.put( className, instance );
            }
        }

        return ( (WITransformer) factoryMap.get( className ) );
    }

    /**
     * Transforme le Bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : BO/DTO an convertir
     * @return : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static WActionForm objToForm( final Class transformerClass, final Object object1 )
        throws WTransformerException
    {
        final Object obj[] = { object1 };

        return getSingleTransformer( transformerClass ).objToForm( obj );
    }

    /**
     * Transforme le Bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : BO/DTO an convertir
     * @param object2 : BO/DTO an convertir
     * @return : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static WActionForm objToForm( final Class transformerClass, final Object object1, final Object object2 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2 };

        return getSingleTransformer( transformerClass ).objToForm( obj );
    }

    /**
     * Transforme le Bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : BO/DTO an convertir
     * @param object2 : BO/DTO an convertir
     * @param object3 : BO/DTO an convertir
     * @return : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static WActionForm objToForm( final Class transformerClass, final Object object1,
                                               final Object object2, final Object object3 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2, object3 };

        return getSingleTransformer( transformerClass ).objToForm( obj );
    }

    /**
     * Transforme le Bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param objects : Tableau de BO/DTO an convertir
     * @return : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static WActionForm objToForm( final Class transformerClass, final Object objects[] )
        throws WTransformerException
    {
        return getSingleTransformer( transformerClass ).objToForm( objects );
    }

    /**
     * Transforme bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : DTO/BO
     * @param form : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static void objToForm( final Class transformerClass, final WActionForm form, final Object object1 )
        throws WTransformerException
    {
        final Object obj[] = { object1 };
        getSingleTransformer( transformerClass ).objToForm( obj, form );
    }

    /**
     * Transforme bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : DTO/BO
     * @param object2 : DTO/BO
     * @param form : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static void objToForm( final Class transformerClass, final WActionForm form, final Object object1,
                                        final Object object2 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2 };
        getSingleTransformer( transformerClass ).objToForm( obj, form );
    }

    /**
     * Transforme bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param object1 : DTO/BO
     * @param object2 : DTO/BO
     * @param object3 : DTO/BO
     * @param form : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static void objToForm( final Class transformerClass, final WActionForm form, final Object object1,
                                        final Object object2, final Object object3 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2, object3 };
        getSingleTransformer( transformerClass ).objToForm( obj, form );
    }

    /**
     * Transforme bean BO/DTO en bean Struts
     * 
     * @param transformerClass : Classe Transformer
     * @param objects : tableau de DTO/BO
     * @param form : Bean Struts
     * @throws WTransformerException Pb a la transformation
     */
    public final static void objToForm( final Class transformerClass, final WActionForm form, final Object objects[] )
        throws WTransformerException
    {
        getSingleTransformer( transformerClass ).objToForm( objects, form );
    }

    /**
     * Transforme bean Struts en bean BO/DTO
     * 
     * @param transformerClass : Classe Transformer
     * @param form : Bean Struts
     * @return tableau de DTO/BO
     * @throws WTransformerException Pb a la transformation
     */
    public final static Object[] formToObj( final Class transformerClass, final WActionForm form )
        throws WTransformerException
    {
        return getSingleTransformer( transformerClass ).formToObj( form );
    }

    /**
     * Transforme bean Struts en bean BO/DTO
     * 
     * @param transformerClass : Classe Transformer
     * @param form : Bean Struts
     * @param object1 : BO /DTO
     * @throws WTransformerException Pb a la transformation
     */
    public final static void formToObj( final Class transformerClass, final WActionForm form, final Object object1 )
        throws WTransformerException
    {
        final Object obj[] = { object1 };
        getSingleTransformer( transformerClass ).formToObj( form, obj );
    }

    /**
     * Transforme bean Struts en bean BO/DTO
     * 
     * @param transformerClass : Classe Transformer
     * @param form : Bean Struts
     * @param object1 : BO /DTO
     * @param object2 : BO /DTO
     * @throws WTransformerException Pb a la transformation
     */
    public final static void formToObj( final Class transformerClass, final WActionForm form, final Object object1,
                                        final Object object2 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2 };
        getSingleTransformer( transformerClass ).formToObj( form, obj );
    }

    /**
     * Transforme bean Struts en bean BO/DTO
     * 
     * @param transformerClass : Classe Transformer
     * @param form : Bean Struts
     * @param object1 : BO /DTO
     * @param object2 : BO /DTO
     * @param object3 : BO /DTO
     * @throws WTransformerException Pb a la transformation
     */
    public final static void formToObj( final Class transformerClass, final WActionForm form, final Object object1,
                                        final Object object2, final Object object3 )
        throws WTransformerException
    {
        final Object obj[] = { object1, object2, object3 };
        getSingleTransformer( transformerClass ).formToObj( form, obj );
    }

    /**
     * Transforme bean Struts en bean BO/DTO
     * 
     * @param transformerClass : Classe Transformer
     * @param form : Bean Struts
     * @param object : tableau de BO /DTO
     * @throws WTransformerException Pb a la transformation
     */
    public final static void formToObj( final Class transformerClass, final WActionForm form, final Object object[] )
        throws WTransformerException
    {
        getSingleTransformer( transformerClass ).formToObj( form, object );
    }
}