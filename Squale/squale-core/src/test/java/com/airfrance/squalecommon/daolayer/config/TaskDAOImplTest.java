package com.airfrance.squalecommon.daolayer.config;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;

/**
 * 
 */
public class TaskDAOImplTest extends SqualeTestCase {

    /**
     * Teste la récupération de toutes les tâches
     */
    public void testListAllTasks() {
        Collection coll = null;
        ISession session = getSession();
        try {
            getComponentFactory().createTask(session);
            TaskDAOImpl dao = TaskDAOImpl.getInstance();
            coll = dao.listAllTasks(session);
        } catch (JrafDaoException e) {
            fail("unexpected exception");
            e.printStackTrace();
        }
        assertNotNull(coll);
    }

}