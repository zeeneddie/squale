package com.airfrance.squaleweb;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.ComponentFactory;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.UserForm;
import com.airfrance.squaleweb.transformer.LogonBeanTransformer;
import com.airfrance.squaleweb.transformer.UserTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

import servletunit.struts.MockStrutsTestCase;

/**
 * Test unitaire en contexte WEB
 */
public class SqualeWebTestCase extends MockStrutsTestCase {

    static {
        // Initialisation de la couche JRAF
        JRafConfigurator.initialize();
    }

    /** Session */
    private ISession mSession;

    /** Fabrique de composant */
    private ComponentFactory mComponentFactory;
    /**
     * @return fabrique
     */
    public ComponentFactory getComponentFactory() {
        return mComponentFactory;
    }

    /** (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

        // Initialisation
        ISession session = null; // session Hibernate
        session = PERSISTENTPROVIDER.getSession();
        session.beginTransaction();
        Connection conn = ((SessionImpl) session).getSession().connection();
        Statement stm = conn.createStatement();
        stm.execute("SET REFERENTIAL_INTEGRITY FALSE");
        IDatabaseConnection dc = new DatabaseConnection(((SessionImpl) session).getSession().connection());
        DatabaseOperation.DELETE_ALL.execute(dc, dc.createDataSet());
        stm.execute("SET REFERENTIAL_INTEGRITY TRUE");
        session.commitTransaction();
        session.closeSession();

        // Utilisation d'une autre session
        mSession = PERSISTENTPROVIDER.getSession();
        mComponentFactory = new ComponentFactory(mSession);
        setContextDirectory(new File("../squaleWeb/WebContent"));
        setConfigFile("../squaleWeb/WebContent/WEB-INF/config/struts-config.xml");
    }

    /**
     * 
     * @param pMatricule matricule utilisateur
     * @param pAdmin indique s'il y a un privilège administrateur
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    protected void setupLogonBean(String pMatricule, boolean pAdmin) throws JrafEnterpriseException, WTransformerException {
        UserDTO user = new UserDTO();
        user.setMatricule(pMatricule);

        IApplicationComponent ac = AccessDelegateHelper.getInstance("Login");
        Object[] paramIn = { user, Boolean.valueOf(pAdmin)};

        user = (UserDTO) ac.execute("verifyUser", paramIn);
        UserForm userForm = (UserForm) WTransformerFactory.objToForm(UserTransformer.class, user);
        LogonBean logonBeanSecurity = new LogonBean();
        WTransformerFactory.formToObj(LogonBeanTransformer.class, userForm, new Object[]{logonBeanSecurity, Boolean.valueOf(pAdmin)});
        getRequest().getSession().setAttribute(WConstants.USER_KEY, logonBeanSecurity);
    }
    
    /**
     * Obtention de la session
     * @return session
     */
    protected ISession getJRAFSession() {
        return mSession;
    }
    
    /** (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        getJRAFSession().closeSession();
        super.tearDown();
    }

}
