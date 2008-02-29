package com.airfrance.squalecommon.enterpriselayer.facade.component;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * Test de la facade User
 *
 */
public class UserFacadeTest extends SqualeTestCase {

    /**
     * provider de persistence
     */
    private static IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Test de getUser
     *
     */
    public void testGetUser() {
        try {
            ISession session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            UserBO user = getComponentFactory().createUser(session);
            UserDTO dto = new UserDTO();
            dto.setID(user.getId());
            UserDTO out = UserFacade.getUser(dto, Boolean.FALSE);
            assertNotNull(out);
            dto.setID(-1);
            out = UserFacade.getUser(dto, Boolean.FALSE);
            assertNull(out);
            FacadeHelper.closeSession(session, "");
        } catch (Exception e) {
            e.printStackTrace();
            fail("unexpected exception");
        }
    }

}
