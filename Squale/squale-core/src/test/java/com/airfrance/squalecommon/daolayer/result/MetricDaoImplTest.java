package com.airfrance.squalecommon.daolayer.result;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.ComponentFactory;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;

/**
 */
public class MetricDaoImplTest
    extends SqualeTestCase
{

    /**
     * @throws JrafDaoException si erreur
     */
    public void testGetVolumetry()
        throws JrafDaoException
    {
        int volumetry = -1;
        int volumetryBySite = -1;
        int volumetryBySiteAndProfil = -1;
        ISession session = getSession();
        MetricDAOImpl metricDAO = MetricDAOImpl.getInstance();
        session.beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        ProjectProfileBO profil = getComponentFactory().createProjectProfileWithName( getSession(), "java" );
        ProjectBO proj = getComponentFactory().createProject( getSession(), appli, null, profil, null, null );
        PackageBO packageBO = getComponentFactory().createPackage( session, proj );
        ClassBO classBO = getComponentFactory().createClass( session, packageBO );
        MethodBO method = getComponentFactory().createMethod( session, classBO );
        AuditBO audit = getComponentFactory().createAudit( getSession(), proj );
        audit.setStatus( AuditBO.TERMINATED );
        AuditDAOImpl.getInstance().save( getSession(), audit );
        // On créer des mesures
        getComponentFactory().createMeasures( session, audit, proj, classBO, method );
        session.commitTransactionWithoutClose();
        session.beginTransaction();
        volumetry = metricDAO.getVolumetry( session, new Long( proj.getId() ) );
        volumetryBySite = metricDAO.getVolumetryBySite( session, appli.getServeurBO().getServeurId() );
        volumetryBySiteAndProfil =
            metricDAO.getVolumetryBySiteAndProfil( session, appli.getServeurBO().getServeurId(), "java" );
        session.commitTransactionWithoutClose();
        // on doit avoir la meme volumétrie que celle qui est crée par
        // cf getComponentFactory().createComments pour la valeur
        assertTrue( volumetry == ComponentFactory.SLOC );
        assertTrue( volumetryBySite == ComponentFactory.SLOC );
        assertTrue( volumetryBySiteAndProfil == ComponentFactory.SLOC );
    }

}
