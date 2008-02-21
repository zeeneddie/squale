package com.airfrance.squalix.tools.external.bugtracking.qc;

import com.airfrance.squalecommon.util.messages.BaseMessages;

public class BugTrackingQCMessages extends BaseMessages {
  /** Instance de BugTrackingMessages */ 
    static private BugTrackingQCMessages mMessages = new BugTrackingQCMessages();
    
    
    /**
     * Constructeur par défaut
     */
    private BugTrackingQCMessages () {
        super("com.airfrance.squalix.tools.external.bugtracking.bugtrackingqc");
    }
    
    
    /**
     * Retourne la chaine de caractère identifiée par la clé
     * @param pKey le nom de la clé
     * @return la chaine de caractère
     */
    public static String getString(String pKey) {
        return mMessages.getBundleString(pKey);
    }

}
