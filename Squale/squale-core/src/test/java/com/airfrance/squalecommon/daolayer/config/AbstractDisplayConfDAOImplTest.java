package com.airfrance.squalecommon.daolayer.config;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.config.web.AbstractDisplayConfDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;

/**
 * Test du DAO des configurations d'affichage
 */
public class AbstractDisplayConfDAOImplTest
    extends SqualeTestCase
{

    /**
     * Teste la récupération des configurations en fonction du subclass
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindAllSubclass()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On crée deux configurations de type différent
        AbstractDisplayConfDAOImpl dao = AbstractDisplayConfDAOImpl.getInstance();
        // Un bubble
        BubbleConfBO bubble = new BubbleConfBO();
        bubble.setXTre( "project.mccabe.evg" );
        bubble.setYTre( "project.mccabe.vg" );
        dao.create( getSession(), bubble );
        // Une volumétrie
        VolumetryConfBO volumetry = new VolumetryConfBO();
        volumetry.setComponentType( DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        volumetry.addTre( "project.rsm.sloc" );
        dao.create( getSession(), volumetry );
        getSession().commitTransactionWithoutClose();
        Collection all = dao.findAll( getSession() );
        assertEquals( 2, all.size() );
        // On veut récupérer toutes les configurations des bubble
        Collection bubbles = dao.findAllSubclass( getSession(), BubbleConfBO.class );
        assertEquals( 1, bubbles.size() );

    }

}
