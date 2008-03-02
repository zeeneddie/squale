package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.CheckstyleRuleSetDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.rulechecking.CheckstyleTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml.CheckstyleImport;
import com.airfrance.squalecommon.util.file.FileUtility;

/**
 * @author henix
 */
public class CheckstyleFacade
    implements IFacade
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Contructeur vide : evite l'instanciation de cet objet
     */
    private CheckstyleFacade()
    {
    }

    /**
     * Donne les différentes versions du fichier de configuartion checkstyle
     * 
     * @return Collection
     * @throws JrafEnterpriseException exception JRAF
     */

    public static ArrayList getAllVersions()
        throws JrafEnterpriseException
    {

        // Initialisation
        Collection versionBOs = null; // retour de la DAO
        ArrayList versionDTOs = new ArrayList(); // retour de la facade

        ISession session = null;
        try
        {
            // création d'une session Hibernate
            session = PERSISTENTPROVIDER.getSession();

            CheckstyleRuleSetDAOImpl versionDAO = CheckstyleRuleSetDAOImpl.getInstance();
            versionBOs = versionDAO.findAllSorted( session );

            Iterator iterator = versionBOs.iterator();
            CheckstyleRuleSetBO versionBO = null;
            while ( iterator.hasNext() )
            {
                versionDTOs.add( CheckstyleTransform.bo2Dto( (CheckstyleRuleSetBO) iterator.next() ) );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, CheckstyleFacade.class.getName() + ".getAllversions" );
        }
        finally
        {
            FacadeHelper.closeSession( session, CheckstyleFacade.class.getName() + ".getAllversions" );
        }
        return versionDTOs;
    }

    /**
     * Insert une nouvelle version du fichier de configuration checkstyle. Et assure l'ascendance de la nouvelle version
     * vers les anciennes
     * 
     * @param pStream flux à parser
     * @param pErrors les erreurs générés pendant le parsing.
     * @return VersionDTO version
     * @throws JrafEnterpriseException exception JRAF
     */

    public static CheckstyleDTO importCheckstyleConfFile( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        CheckstyleDTO version = null;
        ISession session = null;
        try
        {
            // création d'une session Hibernate
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Importation du fichier
            CheckstyleImport checkstyleImport = new CheckstyleImport();
            byte[] bytes = FileUtility.toByteArrayImpl( pStream );

            CheckstyleRuleSetBO versionBo =
                checkstyleImport.importCheckstyle( new ByteArrayInputStream( bytes ), pErrors );

            if ( pErrors.length() == 0 )
            {
                // Il faut tester que le fichier parsé était paramètré pour squale
                // Dans le cas inverse, le parsing se passe bien mais versionBo est null
                if ( versionBo != null )
                {
                    // verification des éventuelles ambiguités au niveau des modules
                    Collection colModuleName = versionBo.isAmbiguityModules();

                    if ( colModuleName.size() == 0 )
                    {

                        // persistance des résultats
                        versionBo.setValue( bytes );
                        versionBo = CheckstyleRuleSetDAOImpl.getInstance().createCheckstyleRuleSet( session, versionBo );
                        version = CheckstyleTransform.bo2Dto( versionBo );
                        // on persiste les résultats dans la base
                        session.commitTransactionWithoutClose();
                    }
                    else
                    {

                        pErrors.append( CheckstyleParsingMessages.getString( "checkstyle.file.unupload" ) + "\n" );
                        // on enrégistre le nom des modules concernés
                        pErrors.append( CheckstyleParsingMessages.getString( "checkstyle.ambiguty.detected" ) + "\n" );
                        String moduleName = null;
                        Iterator it = colModuleName.iterator();
                        while ( it.hasNext() )
                        {
                            moduleName = (String) it.next();
                            pErrors.append( moduleName + "\n" );
                        }
                        session.rollbackTransactionWithoutClose();
                    }
                }
                else
                {
                    session.rollbackTransactionWithoutClose();
                    pErrors.append( CheckstyleParsingMessages.getString( "checkstyle.squale.format.violation" ) );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            session.rollbackTransactionWithoutClose();
            FacadeHelper.convertException( e, CheckstyleFacade.class.getName() + ".parseFile" );

        }
        catch ( IOException e )
        {
            session.rollbackTransactionWithoutClose();
            FacadeHelper.convertException( e, CheckstyleFacade.class.getName() + ".parseFile" );
        }
        finally
        {
            FacadeHelper.closeSession( session, CheckstyleFacade.class.getName() + ".parseFile" );
        }
        return version;
    }

    /**
     * Destruction de rulesets obsolètes
     * 
     * @param pRuleSets ruleSets devant être détruits
     * @return rulesets obsolètes ne pouvant pas être supprimés
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection deleteRuleSets( Collection pRuleSets )
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            CheckstyleRuleSetDAOImpl checkstyleRuleSetDAO = CheckstyleRuleSetDAOImpl.getInstance();
            Iterator ruleSetsIt = pRuleSets.iterator();
            // Parcours des rulesets à détruire
            ArrayList rulesetsId = new ArrayList();
            RuleCheckingTransgressionDAOImpl ruleCheckingTransgressionDAO =
                RuleCheckingTransgressionDAOImpl.getInstance();
            while ( ruleSetsIt.hasNext() )
            {
                CheckstyleDTO checkstyleDTO = (CheckstyleDTO) ruleSetsIt.next();
                Long ruleSetId = new Long( checkstyleDTO.getId() );
                // On vérifie que le jeu de règles n'est pas utilisé
                // au niveau des mesures réalisées, pour les projets paramétrés mais non encore audités, on ne le gère
                // pas
                if ( ruleCheckingTransgressionDAO.isRuleSetUsed( session, ruleSetId ) )
                {
                    result.add( checkstyleDTO );
                }
                else
                {
                    // Ajout dans les rulesets à détruire
                    rulesetsId.add( ruleSetId );
                }
            }
            // Destruction des rulesets qui ne sont plus référencés
            if ( rulesetsId.size() > 0 )
            {
                checkstyleRuleSetDAO.removeCheckstyleRuleSets( session, rulesetsId );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, CheckstyleFacade.class.getName() + ".get" );
        }
        finally
        {
            FacadeHelper.closeSession( session, CheckstyleFacade.class.getName() + ".get" );
        }
        return result;
    }
}
