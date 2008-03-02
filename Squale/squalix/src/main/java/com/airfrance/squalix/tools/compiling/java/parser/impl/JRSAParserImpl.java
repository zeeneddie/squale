package com.airfrance.squalix.tools.compiling.java.parser.impl;

import java.util.List;

import com.airfrance.squalix.tools.compiling.java.adapter.JComponentAdapter;
import com.airfrance.squalix.tools.compiling.java.parser.rsa.JRSAParser;

/**
 * Implémentation de l'adapteur du parser pour RSA7
 */
public class JRSAParserImpl
    extends JComponentAdapter
{

    /**
     * @param pProjectList la liste des projets
     * @throws Exception erreur
     */
    public JRSAParserImpl( List pProjectList )
        throws Exception
    {
        mRSAParser = new JRSAParser( pProjectList );
    }

    /**
     * Parser à utiliser.
     */
    private JRSAParser mRSAParser;

    /**
     * Méthode de lancement du parsing.
     * 
     * @throws Exception exception lancée en cas d'erreur du parsing.
     */
    public void execute()
        throws Exception
    {
        mRSAParser.execute();
    }

    /**
     * Getter.
     * 
     * @return la liste des projets WSAD.
     */
    public List getProjectList()
    {
        return mRSAParser.getProjectList();
    }
}
