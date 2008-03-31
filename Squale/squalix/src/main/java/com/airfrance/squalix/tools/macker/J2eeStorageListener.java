package com.airfrance.squalix.tools.macker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.util.file.FileUtility;
import com.airfrance.squalix.util.file.JspFileUtility;
import com.airfrance.squalix.util.parser.J2EEParser;

/**
 *
 */
public class J2eeStorageListener
    extends JavaStorageListener
{

    /** La liste des chemins absolus vers les pages jsps */
    private List mJspPaths;

    /** Répertoire contenant les jsps compilées */
    private String mCompiledJsp;

    /** le lien entre le nom des fichiers .java générés et le nom initial des jsps */
    private Map mJspMapNames;

    /**
     * Constructeur par défaut
     * 
     * @param pSession la session
     * @param pProject le projet à auditer
     * @param pConfiguration la configuration Macker
     * @param jspNamesMap le lien entre le nom des fichiers .java générés et le nom initial des jsps
     */
    public J2eeStorageListener( ISession pSession, ProjectBO pProject, MackerConfiguration pConfiguration,
                                Map jspNamesMap )
    {
        super( pSession, pProject, pConfiguration );
        this.mParser = new J2EEParser( pProject );
        mJspPaths = pConfiguration.getJsps();
        mCompiledJsp = pConfiguration.getJspRoot();
        mJspMapNames = jspNamesMap;
    }

    /**
     * Créer et fait persister la page JSP dont le nom absolu est <code>pFullName</code> si celle-ci appartient au
     * projet à auditer et n'appartient pas à un des répertoires exclus.
     * 
     * @param pFullName le nom absolu de la classe
     * @throws IOException si erreur de flux
     * @throws JrafDaoException si erreur de persistance
     * @return le composant persisté
     */
    protected AbstractComponentBO getComponent( String pFullName )
        throws IOException, JrafDaoException
    {
        AbstractComponentBO jspOrClassBO = null;
        String absoluteFileName = null;
        // On récupère le nom absolu du fichier compilé associé à la classe
        String classFileName = FileUtility.getClassFileName( mFilesToAnalyse, pFullName );
        if ( null != classFileName )
        { // On a trouvé le .class associé
            File compiledJspDir = new File( mCompiledJsp );
            if ( classFileName.startsWith( compiledJspDir.getCanonicalPath() ) )
            { // Il s'agit d'une jsp compilée
                // On récupère le nom absolu du fichier parmis les jsps
                // si elle existe et peut être persistée
                absoluteFileName = (String) mJspMapNames.get( pFullName );
                if ( null != absoluteFileName && mIncludedFiles.contains( absoluteFileName ) )
                { // Il faut créer une jsp
                    // On récupère la jsp
                    String name =
                        absoluteFileName.substring( absoluteFileName.lastIndexOf( "/" ) + 1,
                                                    absoluteFileName.lastIndexOf( "." ) );
                    int id =
                        JspFileUtility.getJspDirectoryId( mJspPaths, pFullName.substring( 0, pFullName.indexOf( "." ) ) );
                    File rootDir = new File( (String) mJspPaths.get( id ) );
                    String relativeRootDir = FileUtility.getRelativeFileName( rootDir.getAbsolutePath(), mViewPath );
                    String relativeFileName = FileUtility.getRelativeFileName( absoluteFileName, mViewPath );
                    jspOrClassBO = ( (J2EEParser) mParser ).getJsp( name, relativeFileName, relativeRootDir, id );
                    // On fait persister la jsp
                    jspOrClassBO = (JspBO) mRepository.persisteComponent( jspOrClassBO );
                }
            }
            else
            {
                // On vérifie que le nom absolu du fichier .java correspondant
                // appartient à la liste des fichiers qui peuvent être analysés
                String relativeFileName = isInclude( pFullName );
                if ( null != relativeFileName )
                { // Il faut créer une classe
                    // On récupère la classe
                    jspOrClassBO = mParser.getClass( pFullName, relativeFileName );
                    // On fait persister la classe
                    jspOrClassBO = (ClassBO) mRepository.persisteComponent( jspOrClassBO );
                }
            }
        }
        return jspOrClassBO;
    }

    /**
     * True si le nom absolu du fichier jsp correspondant à la classe <code>pFullName</code> appartient à la liste des
     * fichiers qui peuvent être analysés
     * 
     * @param pFullName le nom absolu de la classe JSP
     * @return le nom absolu du fichier dans lequel la jsp est définie
     * @throws IOException si erreur de flux
     */
    protected String getAbsoluteFileName( String pFullName )
        throws IOException
    {
        String absoluteFileName = null;
        // On récupère le nom absolu du fichier compilé associé à la classe
        absoluteFileName = JspFileUtility.getAbsoluteJspFileName( mJspPaths, pFullName );
        if ( null != absoluteFileName )
        {
            // On vérifie que la jsp peut être persistée
            // On remplace le séparateur par celui par défaut
            absoluteFileName = absoluteFileName.replaceAll( "\\\\", "/" );
            String parentName = absoluteFileName.substring( 0, absoluteFileName.lastIndexOf( "/" ) );
            if ( !mIncludedFiles.contains( absoluteFileName ) )
            {
                absoluteFileName = null;
            }
        }
        return absoluteFileName;
    }
}