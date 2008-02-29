package com.airfrance.squalecommon.util.messages;

import java.util.MissingResourceException;

import com.airfrance.squalecommon.SqualeTestCase;

/**
 * @author M400841
 *
 * Cette classe test ne permet pas de tester dans 
 * de mauvaises conditions les methodes ! ! ! ! ! ! ! ! ! ! 
 *
 */
public class CommonMessagesTest extends SqualeTestCase {

    /**
     * Permet de voir le bon fonctionnement d'une methode
     */
    private boolean test = true;

    /**
     * Test une mauvaise clé dans le fichier
     */
    public void testGetBadKey() {
        String result = CommonMessages.getString("bambi.manu");
        assertNull(result);
    }

    /**
     * Test une bonne clé dans le fichier
     */
    public void testGetGoodKey() {

        test = true;

        try {
            assertTrue(CommonMessages.getString("tr.measure.mccabe.classresult") instanceof String);
        } catch (MissingResourceException e) {
            test = false;
        }
        assertTrue(test);

    }

    /**
     * Test une clé ne pouvant pas convertir en entier
     */
    public void testGetBadInteger() {
        Integer result = new Integer(CommonMessages.getInt("tr.measure.mccabe.classresult"));
        //-1 est la valeur en cas d'échec
        assertEquals(result,new Integer(-1));
    }

    /**
     * Test une clé pouvant etre converti en entier
     */
    public void testGetGoodInteger() {

        test = true;

        try {
            CommonMessages.getInt("audit.status.notattempted");
        } catch (NumberFormatException e) {
            test = false;
        }
        assertTrue(test);
    }

}
