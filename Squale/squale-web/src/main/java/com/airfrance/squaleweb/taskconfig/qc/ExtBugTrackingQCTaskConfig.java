package com.airfrance.squaleweb.taskconfig.qc;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.taskconfig.AbstractConfigTask;
import com.airfrance.squaleweb.taskconfig.FieldInfoConfig;


/**
 * 
 */
public class ExtBugTrackingQCTaskConfig extends AbstractConfigTask {

    /*
     * Definition des Parameters_Constants 
     */
    
    public static final String TASK_NAME ="ExtBugTrackingQC";
    public static final String BUGTRACKINGQC_LOGIN ="btqcLogin";
    public static final String BUGTRACKINGQC_PWD ="btqcPwd";
    public static final String BUGTRACKINGQC_URL ="btqcURL";
    public static final String BUGTRACKINGQC_TRACE="btqcTrace"; 
     

    /** 
     * Definition de la liste des infos de configuration nécessaire
     */
    public void init() {
        Collection collec = new ArrayList();
        
        FieldInfoConfig QCLogin = new FieldInfoConfig("project_creation.QC.BugTracking.Field.Login","QCLogin","true","60","TEXT");
        collec.add(QCLogin);
        FieldInfoConfig QCPassword = new FieldInfoConfig("project_creation.QC.BugTracking.Field.Pwd","QCPassword","true","60","PASSWORD");
        collec.add(QCPassword);
        FieldInfoConfig QCUrl = new FieldInfoConfig("project_creation.QC.BugTracking.Field.Url","QCUrl","true","60","TEXT");
        collec.add(QCUrl);
        FieldInfoConfig QCTrace = new FieldInfoConfig("project_creation.QC.Bugtracking.Field.Trace","QCTrace","false","60","TEXT");
        collec.add(QCTrace);
        
        setInfoConfigTask(collec);
    }
    
    
    /**
     * Création de l'ensemble des infos nécessaire pour définir la page JSP de configuration
     */
    public ExtBugTrackingQCTaskConfig(){
        setTaskName("ExtBugTrackingQCTask");
        setHelpKeyTask("project_creation.QCTask.BugTracking_conf.details");
        init();
    }
}
