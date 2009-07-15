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
package testbatch.batch;

import org.squale.squalix.util.process.ProcessManager;
import org.squale.squalix.util.process.ProcessOutputHandler;
import java.io.File;

import testbatch.batch2.TestCycle;

/**
 * Sous-classe
 */
public class TestBatchExtended extends TestBatch {
    
    /**
     * Class anonyme dans une méthode.
     * 
     * @throws Exception exception lors du traitement.
     */
    private void getLineNumbers(String[] pString, File pFile) throws Exception {
        try {
        TestCycle t = new TestCycle();
        ProcessManager process = new ProcessManager(pString, null, pFile);
       final StringBuffer outputBuffer = new StringBuffer();
        process.setOutputHandler(new ProcessOutputHandler() {
            public void processOutput(String pOutputLine) {
                outputBuffer.append(pOutputLine);
            }
        });
        } catch(Exception e) {
            TestCycle t = new TestCycle();
        } finally {
            throw new Exception();
        }
    }

    private void getLineNumber(String[] pString, File pFile) throws Exception {
        ProcessManager process = new ProcessManager(pString, null, pFile);
       final StringBuffer outputBuffer = new StringBuffer();
        process.setOutputHandler(new ProcessOutputHandler() {
            public void processOutput(String pOutputLine) {
                outputBuffer.append(pOutputLine);
            }
        });
        final class BlockInnerClass {
            public void doNothing() {
            }
        };
    }

}
