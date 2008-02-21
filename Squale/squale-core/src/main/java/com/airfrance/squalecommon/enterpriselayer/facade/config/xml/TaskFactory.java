package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de tâche
 */
public class TaskFactory extends FactoryAdapter {

    /** Tâches */
    private Hashtable mTasks;
    
    /**
     * Constructeur
     *
     */
    public TaskFactory() {
        mTasks = new Hashtable();
    }
    /** (non-Javadoc)
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject(Attributes attributes) throws Exception {
        String name = attributes.getValue("name");
        TaskBO task = (TaskBO) mTasks.get(name);
        if (task == null) {
            task = new TaskBO();
            task.setName(name);
            mTasks.put(name, task);
        } else {
            // Détection d'objet dupliqué
            throw new Exception(XmlConfigMessages.getString("task.duplicate", new Object[]{name}));
        }
        return task;
    }

    /**
     * @return facteurs
     */
    public Hashtable getTasks() {
        return mTasks;
    }


}
