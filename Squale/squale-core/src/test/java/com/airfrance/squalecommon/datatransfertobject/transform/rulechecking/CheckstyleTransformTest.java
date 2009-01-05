/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
