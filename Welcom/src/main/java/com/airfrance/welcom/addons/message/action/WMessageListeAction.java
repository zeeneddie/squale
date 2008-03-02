package com.airfrance.welcom.addons.message.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.welcom.addons.config.AddonsConfig;
import com.airfrance.welcom.addons.message.MessageResourcesAddons;
import com.airfrance.welcom.addons.message.WAddOnsMessageManager;
import com.airfrance.welcom.addons.message.bean.MessageBean;
import com.airfrance.welcom.addons.message.bean.MessagesBean;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WStatement;
import com.airfrance.welcom.struts.action.WDispatchAction;

/*
 * Créé le 25 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WMessageListeAction
    extends WDispatchAction
{

    /**
     * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward unspecified( final ActionMapping mapping, final ActionForm form,
                                      final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        MessagesBean messagesBean = (MessagesBean) form;
        String localesStr = WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_MESSAGE_MANAGER_LOCALES );
        ArrayList locales = new ArrayList();
        if ( locales != null )
        {
            StringTokenizer tokenizer = new StringTokenizer( localesStr, "," );
            while ( tokenizer.hasMoreTokens() )
            {
                locales.add( tokenizer.nextToken() );
            }
        }
        if ( locales.size() < 1 )
        {
            throw new ServletException( "La variable addons.messageManager.locales doit etre renseignée" );
        }

        // associe a un messageKey, le messageBean associe
        HashMap messageMap = new HashMap();

        MessageResourcesAddons messageResourcesAddons = ( (MessageResourcesAddons) getResources( request ) );

        Enumeration enumeration = messageResourcesAddons.getDefaultPropertiesKeys();
        ArrayList list = new ArrayList();
        while ( enumeration.hasMoreElements() )
        {
            String key = (String) enumeration.nextElement();
            String value = messageResourcesAddons.getDefaultProperty( key );
            MessageBean messageBean = new MessageBean();
            messageBean.setMessageKey( key );
            messageBean.setDefaultValue( value );
            messageMap.put( key, messageBean );
            list.add( messageBean );
        }

        WJdbcMagic jdbc = null;

        try
        {
            jdbc = new WJdbcMagic();
            WStatement sta = jdbc.getWStatement();
            sta.add( "select * from " + AddonsConfig.WEL_MSGLIBELLE );
            ResultSet rs = sta.executeQuery();
            if ( rs != null )
            {
                while ( rs.next() )
                {
                    MessageBean bean = (MessageBean) messageMap.get( rs.getString( "MESSAGEKEY" ) );
                    if ( bean == null )
                    {
                        MessageBean messageBean = new MessageBean();
                        messageBean.setMessageKey( rs.getString( "MESSAGEKEY" ) );
                        messageBean.setValue( rs.getString( "LOCALE" ), rs.getString( "MESSAGE" ) );
                        messageBean.setDefaultValue( rs.getString( "MESSAGE" ) );
                        messageMap.put( messageBean.getMessageKey(), messageBean );
                    }
                    else
                    {
                        bean.setValue( rs.getString( "LOCALE" ), rs.getString( "MESSAGE" ) );
                    }
                }

                rs.close();
            }
            sta.close();

            messagesBean.setListMessages( list );
            messagesBean.setListLocales( locales );

            // Pour l'autocomplete de la recherche
            request.getSession().setAttribute( "listMessages", list );
            return mapping.findForward( "success" );
        }
        catch ( SQLException sqle )
        {
            throw new ServletException( sqle.getMessage() );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }
    }

    /**
     * Action Modifier
     * 
     * @param mapping le mapping
     * @param form l'actionform
     * @param request la request
     * @param response la response
     * @return l'actionForward retourné
     * @throws Exception exception pouvant etre levee
     */
    public ActionForward modifier( final ActionMapping mapping, final ActionForm form,
                                   final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        final MessagesBean messagesBean = (MessagesBean) form;
        final ArrayList locales = messagesBean.getListLocales();
        final ArrayList messages = messagesBean.getListMessages();
        String locale;
        String value;
        WJdbcMagic jdbc = null;
        try
        {
            jdbc = new WJdbcMagic();
            WStatement sta = null;
            for ( int loc = 0; loc < locales.size(); loc++ )
            {
                locale = (String) locales.get( loc );
                for ( int i = 0; i < messages.size(); i++ )
                {
                    sta = jdbc.getWStatement();
                    final MessageBean message = (MessageBean) messages.get( i );
                    if ( message.isChanged( locale ) )
                    {
                        value = message.getValueNew( locale );
                        if ( GenericValidator.isBlankOrNull( value ) )
                        {
                            // DELETE
                            sta.add( "delete from " + AddonsConfig.WEL_MSGLIBELLE + " where " );
                            sta.addParameter( "MESSAGEKEY=?", message.getMessageKey() );
                            sta.addParameter( "and LOCALE=?", locale );
                            sta.executeUpdate();

                        }
                        else
                        {
                            sta.add( "update " + AddonsConfig.WEL_MSGLIBELLE + " set" );
                            sta.addParameter( "MESSAGE=?", value );
                            sta.addParameter( "where MESSAGEKEY=?", message.getMessageKey() );
                            sta.addParameter( "and LOCALE=?", locale );
                            final int result = sta.executeUpdate();

                            if ( result == 0 )
                            {
                                sta.close();
                                sta = jdbc.getWStatement();
                                // La ligne n'existait pas, on la cree
                                sta.add( "insert into " + AddonsConfig.WEL_MSGLIBELLE
                                    + " (MESSAGEKEY,MESSAGE,LOCALE) values (" );
                                sta.addParameter( "?,", message.getMessageKey() );
                                sta.addParameter( "?,", value );
                                sta.addParameter( "?)", locale );
                                sta.executeUpdate();
                            }
                        }
                        sta.close();
                    }
                }
            }
            // Met a jour le timestamp
            sta = jdbc.getWStatement();
            sta.add( "update WEL_ADDONS set" );
            sta.addParameter( "PARAMETERS=?", new Date().getTime() );
            sta.addParameter( "where NAME=?", WAddOnsMessageManager.ADDONS_MESSAGEMANAGER_NAME );
            sta.executeUpdate();
            sta.close();

            jdbc.commit();
            ( (MessageResourcesAddons) getResources( request ) ).resetCache();
        }
        catch ( final SQLException sqle )
        {
            throw new ServletException( sqle.getMessage() );
        }
        finally
        {
            if ( jdbc != null )
            {
                jdbc.close();
            }
        }
        return mapping.findForward( "success" );
    }

    /**
     * Action recherce
     * 
     * @param mapping le mapping
     * @param form l'actionform
     * @param request la request
     * @param response la response
     * @return l'actionForward retourné
     * @throws Exception exception pouvant etre levee
     */
    public ActionForward recherche( final ActionMapping mapping, final ActionForm form,
                                    final HttpServletRequest request, final HttpServletResponse response )
        throws Exception
    {
        final MessagesBean messagesBean = (MessagesBean) form;
        final String messageKeyFiltre = messagesBean.getMessageKeyFiltre();
        final String messageFiltre = messagesBean.getMessageFiltre();

        final ArrayList listSession = (ArrayList) request.getSession().getAttribute( "listMessages" );
        ArrayList listRecherche = new ArrayList();
        MessageBean messageBean;

        if ( messageKeyFiltre.equals( "" ) && messageFiltre.equals( "" ) )
        {
            listRecherche = listSession;
        }

        for ( int i = 0; i < listSession.size(); i++ )
        {
            messageBean = (MessageBean) listSession.get( i );
            if ( messageKeyFiltre.equals( "" ) && messageBean.contain( messageFiltre ) )
            {
                listRecherche.add( messageBean );
            }
            else if ( messageBean.getMessageKey().indexOf( messageKeyFiltre ) >= 0 )
            {
                if ( messageBean.contain( messageFiltre ) )
                { // true si le messageFiltre est vide
                    listRecherche.add( messageBean );
                }
            }
        }

        messagesBean.setListMessages( listRecherche );

        return mapping.findForward( "success" );
    }
}
