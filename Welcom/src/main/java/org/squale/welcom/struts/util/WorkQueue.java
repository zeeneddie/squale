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
/*
 * Créé le 20 sept. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.util;

import java.util.LinkedList;

public class WorkQueue
{
    private final PoolWorker[] threads;

    private final LinkedList queue;

    public WorkQueue( int nThreads )
    {
        queue = new LinkedList();
        threads = new PoolWorker[nThreads];

        for ( int i = 0; i < nThreads; i++ )
        {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public int getPoolSize()
    {
        return threads.length;
    }

    public int getWaitingTasks()
    {
        return queue.size();
    }

    public void execute( Runnable r )
    {
        synchronized ( queue )
        {
            queue.addLast( r );
            queue.notify();
        }
    }

    private class PoolWorker
        extends Thread
    {
        Runnable r;

        public void run()
        {

            while ( true )
            {
                synchronized ( queue )
                {
                    while ( queue.isEmpty() )
                    {
                        try
                        {
                            queue.wait();
                        }
                        catch ( InterruptedException ignored )
                        {
                        }
                    }

                    r = (Runnable) queue.removeFirst();
                }

                // If we don't catch RuntimeException,
                // the pool could leak threads
                try
                {
                    r.run();
                }
                catch ( RuntimeException e )
                {
                    e.printStackTrace();
                }
                finally
                {
                    r = null;
                }
            }
        }

        public Runnable getActiveTask()
        {
            return r;
        }
    }

    public void stopTask( Runnable batch )
    {
        synchronized ( queue )
        {
            // 1) Look if the task isn't waiting in the queue
            if ( queue.contains( batch ) )
            {
                queue.remove( batch );
                return;
            }

            // 2) Maybe it's running
            for ( int i = 0; i < threads.length; i++ )
            {
                PoolWorker t = threads[i];

                if ( batch.equals( t.getActiveTask() ) )
                {
                    try
                    {
                        t.interrupt();
                        queue.notify();
                    }
                    catch ( Exception e )
                    {
                        e.printStackTrace();
                    }
                    return;
                }
            }

        }
    }
}