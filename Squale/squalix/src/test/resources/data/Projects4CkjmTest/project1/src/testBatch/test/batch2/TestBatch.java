package testBatch.test.batch2;

import testCommon.test.TestCommon;
import testBatch.test.batch.TestBatchInterface;
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
