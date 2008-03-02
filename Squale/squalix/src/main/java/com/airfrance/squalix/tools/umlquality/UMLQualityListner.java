package com.airfrance.squalix.tools.umlquality;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.umlquality.connector.QualimetryData;
import com.umlquality.connector.QualimetryDataListener;
import com.umlquality.connector.QualimetryMessageListener;

/**
 * @author sportorico
 */
public class UMLQualityListner
    implements QualimetryDataListener, QualimetryMessageListener
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityListner.class );

    /**
     * la tache umlquality
     */
    private UMLQualityTask mUmlqualityTask;

    /**
     * Constructeur par defaut
     * 
     * @param pUmlqualityTask la tache umlquality
     */
    public UMLQualityListner( UMLQualityTask pUmlqualityTask )
    {
        mUmlqualityTask = pUmlqualityTask;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryDataListener#incomingData(com.umlquality.connector.QualimetryData)
     */
    public void incomingData( QualimetryData pArg0 )
    {

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryMessageListener#connectorMessage(java.lang.String, java.lang.String, int)
     */
    public void connectorMessage( String pArg0, String pArg1, int pArg2 )
    {

    }

    /**
     * {@inheritDoc}
     * 
     * @see com.umlquality.connector.QualimetryMessageListener#connectorError(java.lang.String, java.lang.String, int)
     */
    public void connectorError( String pArg0, String pArg1, int pArg2 )
    {

        LOGGER.error( UMLQualityMessages.getString( "logs.error.tools_error" ) + "  " + pArg0 + "  " + pArg1 + "  "
            + pArg2 );
        mUmlqualityTask.initError( "" + pArg0 + "  " + pArg1 + "  " + pArg2 );
    }
}
