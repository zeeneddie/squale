package testBatch.test.batch;

import testCommon.test.TestCommon;
import testCommon.test.TestCommonInterface;
import testBatch.test.batch.TestBatchInterface;

/**
 *
 */
public class TestBatch implements TestBatchInterface,TestCommonInterface{

    public static void main(String[] args) {        
        TestCommon test = new TestCommon();
        System.out.println(test.getDisplayString());
    }
    
    public String getDisplayString(){
        return "ok";
    }
}
