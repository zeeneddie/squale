//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\tools\\compiling\\java\\parser\\ParserAdapterWSADImpl.java

package com.airfrance.squalix.tools.compiling.java.parser.impl;

import java.util.List;

import com.airfrance.squalix.tools.compiling.java.adapter.JComponentAdapter;
import com.airfrance.squalix.tools.compiling.java.parser.wsad.JWSADParser;

/**
 * Implémentation de l'adapteur du parser pour WSAD 5.x.
 * @author m400832
 * @version 1.0
 */
public class JWSADParserImpl extends JComponentAdapter {
    
    /**
     * Parser à utiliser.
     */
    private JWSADParser mWSADParser;
    
    /**
     * Méthode de lancement du parsing.
     * @throws Exception exception lancée en cas d'erreur du parsing.
     */
    public void execute() throws Exception {
        mWSADParser.execute();
        // On modifie la liste des erreurs
        mErrors.addAll(mWSADParser.getErrors());
    }
    
    /**
     * Constructeur par défaut.
     * @param pProjectList liste des projets WSAD à parser.
     * @throws Exception exception en cas d'erreur.
     */
    public JWSADParserImpl(List pProjectList) throws Exception {
        mWSADParser = new JWSADParser(pProjectList);
    }
    
    /**
     * Getter.
     * @return la liste des projets WSAD.
     */
    public List getProjectList(){
        return mWSADParser.getProjectList();
    }
}
