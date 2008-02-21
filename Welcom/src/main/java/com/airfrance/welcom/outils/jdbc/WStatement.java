package com.airfrance.welcom.outils.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.airfrance.welcom.outils.jdbc.wrapper.IStatement;
import com.airfrance.welcom.outils.jdbc.wrapper.StatementAssembly;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WStatement extends StatementAssembly {

    /** Taille max pour l'affichage du nom de l'utlisateur */
    private static final int MAX_LENGTH_USER = 10;

    /** Nom de l'ulisateur par default */
    public final static String DEFAULTUSER = "????????";

    /** Temps limite pour que la requete soit bien passé, sino passe en warning car delai d'execution trop lent */
    public final static long LIMITSPEED = 5000;

    /** Longeur de la chaine pour le tracing sql */
    public final static int STRING_LENGTH = 100;

    /** Nombre d'espace pour l'ecriture en debut de chaine */
    public final static int STRING_TABS = 40;

    /** Nom de l'utilisateur par defaut */
    private String user = DEFAULTUSER;

    /** Connexion sql */
    private Connection connection = null;

    /** IStatement, WParaletrizedStatement ou WSimpleStatement en fonction, du type de requete */
    private IStatement result = null;

    /**
     * Creation d'un WStatement
     * @param pConnection : Connexion jdbc sur lequel le produire
     */
    public WStatement(final Connection pConnection) {
        super(STRING_LENGTH, STRING_TABS);
        this.connection = pConnection;
    }

    /**
     * Creation d'un WStatement
     * @param pConnection : Connexion jdbc sur lequel le produire
     * @param pUser : Nom de l'utilisateur
     */
    public WStatement(final Connection pConnection, final String pUser) {
        super(STRING_LENGTH, STRING_TABS);
        this.user = pUser;
        this.connection = pConnection;
    }

    /**
     * Creation d'un WStatement
     * @param pConnection : Connexion jdbc sur lequel le produire
     * @param pMaxLineLength : Longueur maximale pour une chaine avant retour chariot
     * @param pIndent : Nombre de char pour l'identation
     */
    public WStatement(final Connection pConnection, final int pMaxLineLength, final int pIndent) {
        super(pMaxLineLength, pIndent);
        this.connection = pConnection;
    }

    /**
     * Creation d'un WStatement
     * @param pConnection : Connexion jdbc sur lequel le produire
     * @param pMaxLineLength : Longueur maximale pour une chaine avant retour chariot
     * @param pIndent : Nombre de char pour l'identation
     * @param pUser : Nom de l'utilisateur
     */
    public WStatement(final Connection pConnection, final int pMaxLineLength, final int pIndent, final String pUser) {
        super(pMaxLineLength, pIndent);
        this.user = pUser;
        this.connection = pConnection;
    }


    /** 
     * Intancie le bon object (@link WParameterizedStatement, @link com.airfrance.welcom.outils.jdbc.wrapper.SimpleStatement)
     * en fonction du nombre de parametres
     * si 0 alors @link com.airfrance.welcom.outils.jdbc.WSimpleStatement
     * si > 0 alors @link com.airfrance.welcom.outils.jdbc.WParameterizedStatement
     * @return IStatement Statement a utiliser 
     * @throws SQLException Erreur SQL
     */
    private IStatement createStatement() throws SQLException {
        String sql;
        IStatement iresult=null;

        sql = buffer.toString();

        if (parameters == null) {
            iresult = new WSimpleStatement(sql, connection, user);
        } else {
            iresult = new WParameterizedStatement(sql, connection, parameters, user);
        }

        return iresult;
    }

    /**
     * Execute la requete de consultation
     * @throws SQLException : Probleme SQL
     * @return ResultSet : Resultset de la requete
     * */
    public ResultSet executeQuery() throws SQLException {
        result = createStatement();       
        return result.executeQuery();
    }

    /**
     * Execute la requete de modification
     * @throws SQLException : Probleme SQL
     * @return int : Nombre d'element modifiés
     * */
    public int executeUpdate() throws SQLException {
        result = createStatement();

        return result.executeUpdate();
    }

    /**
     * Trace et retourne le resultSet
     * @return : retourne le resusultSet du stament
     * @throws SQLException : Probleme SQL
     */
    public ResultSet getResultSet() throws SQLException {
        result = createStatement();

        if (result instanceof WSimpleStatement) {
            return ((WSimpleStatement) (result)).getResultSet();
        } else {
            return ((WParameterizedStatement) (result)).getResultSet();
        }
    }

    /**
     * Gets the user
     * @return Returns a String
     */
    public String getUser() {
        if (user.length() < MAX_LENGTH_USER) {
            while (user.length() < MAX_LENGTH_USER) {
                user += " ";
            }
        }

        return user;
    }

    /**
     * Sets the user
     * @param pUser The user to set
     */
    public void setUser(final String pUser) {
        this.user = pUser;
    }

    /**
     * Permet d'ajouter un parametre primitif
     * @param text : Texte a concatener a la requete SQL
     * @param date : Date a ajouter (Parametres, remplace ?) Attantion doit etre sous format jj/mm/aaa hh:mm
     * @param format : Format JDBC dans la quel doit etre la date
     */
    public void addParameterDate(String text, final String date, final String format) {
        // si la date n'ext pas null on ajoute en fonction du format ;)
        if ((date != null) && (date.length() > 0)) {
            
            final int indexInter = text.indexOf("?");
            
            // S'il n'y a pas de ?, on rajoute la date directement
            if ((indexInter == -1) || (indexInter == text.length())) {
                text += StringtoDate(date, format);
            } else if (indexInter == 0) {
                // si on est a coté d'un ?, on le supprim, pouis on insere la date
                text = StringtoDate(date, format) + text.substring(1, text.length());
            } else {
                // Sinon on remplace le ?, par la date
                text = text.substring(0, indexInter) + StringtoDate(date, format) + text.substring(indexInter + 1, text.length());
            }

            add(text);
        } else {
            // on ajoute le parametre null
            addParameter(text, null);
        }
    }

    /** 
     * Permet d'ajouter un WClob (Chaine de carcatere de plus de 4000car)
     * @param text : Texte a concatener a la requete SQL
     * @param value : Valeur
     * @deprecated
     */
    public void addParameterJClob(final String text, final String value) {
        addParameter(text, new WClob(value));
    }

    /**
     * Permet d'ajouter un parametre primitif
     * @param text : Texte a concatener a la requete SQL
     * @param o : Object a ajouter
     */
   public void addParameter(String text, final Object o) {
        // Verifie si on a pensé a mettre un = sinon le rajoute
        //if (text.indexOf("=") == -1 && text.indexOf("?") == -1)
        // text += "=";
        // Rajoute le ? si on l'a oublié
        if (text.indexOf("?") == -1) {
            text += "?";
        }
        super.addParameter(text, o);
    }

    /**
     * Permet d'ajouter un parametre primitif
     * @param text : Texte a concatener a la requete SQL
     * @param param : Object a ajouter
     *        Remplace les * par de % pour Oracle
     */
    public void addParameterStringFiltre(final String text, String param) {
        if (param == null) {
            param = "";
        }

        param = param.replace('*', '%');
        addParameter(text, param.toUpperCase());
    }

    /**
     * Permet d'ajouter un parametre primitif
     * @param text : Texte a concatener a la requete SQL
     * @param value : Double 
     */
    public void addParameter(final String text, final double value) {
        addParameter(text, new Double(value));
    }

    /**
     * Permet d'ajouter un parametre primitif
     * @param text : Texte a concatener a la requete SQL
     * @param value : Float 
     */
    public void addParameter(final String text, final float value) {
        addParameter(text, new Float(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param text : Texte a concatener a la requete SQL
    * @param value : Integer 
    */
    public void addParameter(final String text, final int value) {
        addParameter(text, new Integer(value));
    }


    /**
    * Permet d'ajouter un parametre primitif
    * @param text : Texte a concatener a la requete SQL
    * @param value : Integer 
    */
    public void addParameter(final String text, final long value) {
        addParameter(text, new Long(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param text : Texte a concatener a la requete SQL
    * @param value : Short
    */
    public void addParameter(final String text, final short value) {
        addParameter(text, new Short(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param text : Texte a concatener a la requete SQL
    * @param value : boolean
    */
    public void addParameter(final String text, final boolean value) {
        addParameter(text, value ? "1" : "0");
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Double a concatener a la requete SQL
    */
    public void addParameter(final double value) {
        super.addParameter(new Double(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Float a concatener a la requete SQL
    */
    public void addParameter(final float value) {
        super.addParameter(new Float(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Integer a concatener a la requete SQL
    */
    public void addParameter(final int value) {
        super.addParameter(new Integer(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Long a concatener a la requete SQL
    */
    public void addParameter(final long value) {
        super.addParameter(new Long(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Long a concatener a la requete SQL
    */
    public void addParameter(final short value) {
        super.addParameter(new Short(value));
    }

    /**
    * Permet d'ajouter un parametre primitif
    * @param value : Boolean a concatener a la requete SQL
    */
    public void addParameter(final boolean value) {
        super.addParameter(value ? "1" : "0");
    }

    /**
    * Fermeture des WSimpleStatement ou du WParameterizedStatement
    * end focntion de l'instanctiation
    * @throws SQLException : Probleme SQL
    */
    public void close() throws SQLException {
        if (result != null) {
            if (result instanceof WSimpleStatement) {
                ((WSimpleStatement) (result)).close();
            } else {
                ((WParameterizedStatement) (result)).close();
            }
        }
    }

    /**
    * @return Retourn Vrai Si la connexion est deja fermé
    */
    public boolean isClose() {
        if (result != null) {
            if (result instanceof WSimpleStatement) {
                return ((WSimpleStatement) (result)).isClose();
            } else {
                return ((WParameterizedStatement) (result)).isClose();
            }
        }

        return true;
    }

    /**
     * Fonction Oracle 
     * @param date : Date en entrée
     * @param format : Fomat de la date
     * @return formatage oracle (to_date(,))
     */
    public static String StringtoDate(String date, final String format) {
        if (date.length() > format.length()) {
            date = date.substring(0, format.length());
        }

        return "to_date('" + date + "','" + format + "')";
    }

    /** 
     * Fonction Oracle 
     * @param date : Date en entrée
     * @param format : Fomat de la date
     * @return formatage oracle (to_date(,))
     */
    public static String toDate(final String date, final String format) {
        if (date.length() > format.length()) {
            date.substring(0, format.length());
        }

        return "to_date(" + date + ",'" + format + "')";
    }

    /** 
     * Fonction oracle
     * @param number : nombre
     * @return to_number('nb')
     */
    public static String StringtoNumber(final String number) {
        return "to_number('" + number + "')";
    }

    /** 
     * Transforme la chaine en nombre oracle
     * @param number : nombre
     * @return to_number('nb')
     */
    public static String toNumber(final String number) {
        return "to_number(" + number + ")";
    }

    /**
     * Transforme la chaine en caretere oracle 
     * @param text : Texte a changer
     * @param format : Format ...
     * @return to_char(text,format)
     */
    public static String toChar(final String text, final String format) {
        return "to_char(" + text + ",'" + format + "')";
    }

    /** Return Now
     * Mysql : SYSDATE()
     * Oracle : CURDATE()
     * sinon : SYSDATE
     * @return now
     */
    public String now(){
        try {
            if (connection.getMetaData().getDriverName().toLowerCase().indexOf("oracle")>-1){
                return "SYSDATE";        
            } else {
                return "SYSDATE()";
            }
        } catch (final SQLException e) {
            return "SYSDATE";
        }
        
    }


    /**
     * 
     * @return la represtation oracle de la chaine
     */
    public String getSQL() {
        if (result != null) {
            if (result instanceof WSimpleStatement) {
                return ((WSimpleStatement) (result)).getSQL();
            } else {
                return ((WParameterizedStatement) (result)).getSQL();
            }
        }

        return "";
    }
}