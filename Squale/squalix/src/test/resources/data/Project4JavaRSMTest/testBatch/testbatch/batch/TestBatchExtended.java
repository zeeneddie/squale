package testbatch.batch;

import com.airfrance.squalix.util.process.ProcessManager;
import com.airfrance.squalix.util.process.ProcessOutputHandler;
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
        TestCycle t = new TestCycle();
        ProcessManager process = new ProcessManager(pString, null, pFile);
       final StringBuffer outputBuffer = new StringBuffer();
        process.setOutputHandler(new ProcessOutputHandler() {
            public void processOutput(String pOutputLine) {
                outputBuffer.append(pOutputLine);
            }
        });
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
