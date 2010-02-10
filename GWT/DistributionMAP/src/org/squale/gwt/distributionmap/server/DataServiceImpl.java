package org.squale.gwt.distributionmap.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.squale.gwt.distributionmap.client.DataService;
import org.squale.gwt.distributionmap.client.widget.data.Child;
import org.squale.gwt.distributionmap.client.widget.data.Parent;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings( "serial" )
public class DataServiceImpl
    extends RemoteServiceServlet
    implements DataService
{

    private Date initialDate;

    public ArrayList<Parent> getData()
    {
        ArrayList<Parent> result = new ArrayList<Parent>();

        handleTime();

        pause();

        Random r = new Random();
        int parentNumber = r.nextInt( 30 ) + 1;
        for ( int i = 0; i < parentNumber; i++ )
        {
            Parent p = new Parent( "Class #" + i );
            int childNumber = r.nextInt( 20 ) + 1;
            for ( int j = 0; j < childNumber; j++ )
            {
                Child c = new Child( "Method #" + j, ( (float) r.nextInt( 31 ) ) / 10 );
                p.addChild( c );
            }
            result.add( p );
        }

        handleTime();

        return result;
    }

    private void pause()
    {
        try
        {
            Thread.sleep( 3000 );
        }
        catch ( InterruptedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void handleTime()
    {
        if ( initialDate == null )
        {
            initialDate = new Date();
        }
        else
        {
            Date current = new Date();
            System.out.println( "Temps d'exécution : " + ( current.getTime() - initialDate.getTime() ) );
            initialDate = null;
        }
    }
}
