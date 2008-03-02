package testbatch.batch2;

import test.TestCommon;
import testbatch.batch.TestBatchInterface;

/**
 *
 */
public class TestBatch implements TestBatchInterface{

    public static void main(String[] args) {
        
        TestCommon test;
        
    }
    
    class InnerTestBatch{
        
        public String getString(){
            return "test inner class";
        }
    }
}
