package com.airfrance.welcom.outils.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.airfrance.welcom.outils.jdbc.wrapper.ConnectionPool;
import com.airfrance.welcom.struts.bean.JCryptable;
import com.airfrance.welcom.struts.bean.JCryptableException;
import com.airfrance.welcom.struts.bean.WComboValue;
import com.airfrance.welcom.struts.bean.WComboValueLabel;
import com.airfrance.welcom.struts.bean.WCouple;

/**
 * @author M327837 Gestion de la conexion JDBC Effectue un garbageCollector SQL.
 */
public class WJdbc
{
    /** logger */
    private static Log log = LogFactory.getLog( WJdbc.class );

    /** Affiche le trace */
    protected static boolean enabledTrace = true;

    /** Nom e l'utilisateur pour traver les requetes SQL */
    protected String userName = "";

    /** La connexion */
    protected Connection conn = null;

    /** Liste des statement ouvert a partir de cette connexion */
    private final Vector statements = new Vector();

    /**
     * Contructeur caché
     * 
     * @throws SQLException : Erreur SQL sur l'initisalisation
     */
    protected WJdbc()
        throws SQLException
    {
        // init(); // Initialise la connexion
    }

    /**
     * Contructeur d'une nouvelle connection
     * 
     * @param pUserName Nom de l'utilisateur
     * @throws SQLException : Erreur SQL sur l'initisalisation
     */
    public WJdbc( final String pUserName )
        throws SQLException
    {
        this.userName = pUserName;
        init(); // Initialise la connexion
    }

    /**
     * @return Vrai si la connection est fermé
     */
    public boolean isClosed()
    {
        return ( conn == null );
    }

    /**
     * initialise la connexion
     * 
     * @throws SQLException leve une erreur SQL
     */
    protected void init()
        throws SQLException
    {
        conn = ConnectionPool.getConnection();

        if ( conn != null )
        {
            if ( conn.isClosed() )
            {
                log.error( "2004-critical-Database--BD close/Relancer le serveur de BD" );
            }

            // Enleve l'autocommit
            conn.setAutoCommit( false );
        }
    }

    /**
     * Ferme tout ce qui ouvert
     */
    public void close()
    {
        if ( conn != null )
        {
            synchronized ( conn )
            {
                // Statcke les erreurs possible
                final ActionErrors errors = new ActionErrors();

                // Fermture de tous les statements
                final Enumeration enumeration = statements.elements();

                while ( enumeration.hasMoreElements() )
                {
                    final WStatement statement = (WStatement) enumeration.nextElement();

                    try
                    {
                        if ( !statement.isClose() )
                        {
                            statement.close();
                        }
                    }
                    catch ( final SQLException sqle )
                    {
                        log.error( "2003-critical-Database--Erreur sur le close GDCtatement" + sqle.toString() );
                        errors.add( ActionErrors.GLOBAL_ERROR, new ActionError( "error.database.GDCStatementClose" ) );

                        // servlet.log("SQLError", sqle);
                    }
                }

                try
                {
                    // Fermeture de la connection
                    if ( ( conn != null ) && !conn.isClosed() )
                    {
                        conn.rollback();
                        conn.close();

                        // conn = null;
                    }
                }
                catch ( final SQLException sqle )
                {
                    log.error( "2004-critical-Database--Erreur sur le close Connection" + sqle.toString() );
                    errors.add( ActionErrors.GLOBAL_ERROR, new ActionError( "error.database.connectionClose" ) );

                    // servlet.log("SQLError", sqle);
                }
            }
        }
    }

    /**
     * Fournis un WStatement pour la suite des opérations
     * 
     * @return Retourne un Statement
     * @throws SQLException probleme sql
     */
    public WStatement getWStatement()
        throws SQLException
    {
        if ( conn == null )
        {
            log.error( "2004-critical-Database--BD close/Relancer le serveur de BD" );
            throw new SQLException( "Connection nulle, ne peut creer un WStatement" );
        }

        synchronized ( conn )
        {
            if ( !conn.isClosed() )
            {
                WStatement statement = null;

                statement = new WStatement( conn, userName );

                statements.add( statement );

                return statement;
            }
            else
            {
                log.error( "2004-critical-Database--BD close/Relancer le serveur de BD" );
                throw new SQLException( "Connection fermer, ne peut creer un WStatement" );
            }
        }
    }

    /**
     * Retourne la connection active
     * 
     * @return Connexion Active
     */
    public Connection getConnection()
    {
        return conn;
    }

    /**
     * Effectue un commit de modifcation dur la BD
     * 
     * @throws SQLException probleme sql
     */
    public void commit()
        throws SQLException
    {
        conn.commit();
    }

    /**
     * Effectue un roolback
     * 
     * @throws SQLException Probleme sql
     */
    public void rollback()
        throws SQLException
    {
        conn.rollback();
    }

    /**
     * Recupere une sequence
     * 
     * @param seq Nom de la sequence
     * @throws SQLException Probleme sur la sequence
     * @return String indice de la séquence Oracle
     */
    public String getSequence( final String seq )
        throws SQLException
    {
        WStatement sta = null;
        java.sql.ResultSet rs = null;

        final String cmd = "select " + seq + ".NEXTVAL from DUAL";
        sta = getWStatement();
        sta.add( cmd );
        rs = sta.executeQuery();

        String result = "0";
        if ( rs != null )
        {
            if ( rs.next() )
            {
                result = rs.getString( "NEXTVAL" );
            }
        }
        sta.close();

        return result;
    }

    /**
     * Recupere la sequence sous un entier
     * 
     * @param seq om de la sequence
     * @return numero de la sequence
     * @throws SQLException Probleme sur la sequence
     */
    public int getIntSequence( final String seq )
        throws SQLException
    {
        return Integer.parseInt( getSequence( seq ) );
    }

    /**
     * Decryptage d'un bean
     * 
     * @param beanCrypt Bean crypté
     * @throws JCryptableException Probleme sur le Cryptage
     */
    public void decrypte( final JCryptable beanCrypt )
        throws JCryptableException
    {
        beanCrypt.decrypte( getConnection() );
    }

    /**
     * Cryptage d'un bean
     * 
     * @param beanCrypt Bean crypté
     * @throws JCryptableException Probleme sur le Cryptage
     */
    public void crypte( final JCryptable beanCrypt )
        throws JCryptableException
    {
        beanCrypt.crypte( getConnection() );
    }

    /**
     * Cryptage d'un combo Value
     * 
     * @param newCombo Combo a crypter
     * @throws SQLException Probleme sur le Cryptage
     */
    public void crypte( final WComboValue newCombo )
        throws SQLException
    {
        // Décrypter
        final Iterator it = newCombo.getListe().iterator();
        int i = 0;

        while ( it.hasNext() )
        {
            final String comp = (String) it.next();
            newCombo.getListe().set( i++, this.crypte( comp ) );
        }
    }

    /**
     * Decryptage D'un combo Value
     * 
     * @param newCombo Combo a crypter
     * @throws SQLException Probleme sur le Cryptage
     */
    public void decrypte( final WComboValue newCombo )
        throws SQLException
    {
        // Décrypter
        final Iterator it = newCombo.getListe().iterator();
        int i = 0;

        while ( it.hasNext() )
        {
            final String comp = (String) it.next();
            newCombo.getListe().set( i++, this.decrypte( comp ) );
        }
    }

    /**
     * Decryptage D'un combo Value Label
     * 
     * @param newCombo Combo a crypter
     * @throws SQLException Probleme sur le Cryptage
     */
    public void decrypte( final WComboValueLabel newCombo )
        throws SQLException
    {
        // Décrypter
        final Iterator it = newCombo.getListe().iterator();
        int i = 0;

        while ( it.hasNext() )
        {
            final WCouple comp = (WCouple) it.next();
            newCombo.getListe().set( i++,
                                     new WCouple( this.decrypte( comp.getValue() ), this.decrypte( comp.getLabel() ) ) );
        }
    }

    /**
     * DeCrype la chaine via l'appel à la Focntion decrypte_FCT
     * 
     * @param text Texte a décripté
     * @return Chaine décrypté
     * @throws SQLException Erreur SQL
     */
    public String decrypte( final String text )
        throws SQLException
    {
        CallableStatement cs = null;

        try
        {
            cs = conn.prepareCall( "{? = call decrypte_FCT(?)}" );
            cs.registerOutParameter( 1, java.sql.Types.VARCHAR );
            cs.setString( 2, text );
            cs.executeUpdate();

            return cs.getString( 1 );
        }
        finally
        {
            if ( cs != null )
            {
                cs.close();
            }
        }
    }

    /**
     * Crype la chaine via l'appel à la Focntion decrypte_FCT
     * 
     * @param text Texte a décripté
     * @return Chaine décrypté
     * @throws SQLException Erreur SQL
     */
    public String crypte( final String text )
        throws SQLException
    {
        CallableStatement cs = null;

        try
        {
            cs = conn.prepareCall( "{? = call crypte_FCT(?)}" );
            cs.registerOutParameter( 1, java.sql.Types.VARCHAR );
            cs.setString( 2, text );
            cs.executeUpdate();

            return cs.getString( 1 );
        }
        finally
        {
            if ( cs != null )
            {
                cs.close();
            }
        }
    }

    /**
     * @return trace active ?
     */
    public static boolean isEnabledTrace()
    {
        return enabledTrace;
    }

    /**
     * @param b active la trace
     */
    public static void setEnabledTrace( final boolean b )
    {
        enabledTrace = b;
    }

}