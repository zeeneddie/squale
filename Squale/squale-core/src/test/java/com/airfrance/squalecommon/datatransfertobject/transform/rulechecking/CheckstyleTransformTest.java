package com.airfrance.squalecommon.datatransfertobject.transform.rulechecking;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;

/**
 * @author E6400802 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CheckstyleTransformTest
    extends SqualeTestCase
{

    /**
     * Test de Dto2Bo
     */

    public void testDto2Bo()
    {

        try
        {
            CheckstyleDTO versionDto = new CheckstyleDTO();

            versionDto.setBytes( "n'importe quoi".getBytes() );

            CheckstyleRuleSetBO versionBo = CheckstyleTransform.dto2Bo( versionDto );

            assertEquals( "id identique", versionDto.getId(), versionBo.getId() );
            assertEquals( "contentfile identique", versionDto.getBytes(), versionBo.getValue() );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }

    }

    /**
     * Test de Bo2Dto
     */

    public void testBo2Dto()
    {

        try
        {
            CheckstyleRuleSetBO versionBo = new CheckstyleRuleSetBO();

            versionBo.setValue( "n'importe quoi".getBytes() );

            CheckstyleDTO versionDto = CheckstyleTransform.bo2Dto( versionBo );

            assertEquals( "id identique", versionDto.getId(), versionBo.getId() );
            assertEquals( "contentfile identique", versionDto.getBytes(), versionBo.getValue() );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "Exception inattendue" );
        }

    }

}
