package testbatch.batch;

import test.TestCommon;
import test.TestCommonInterface;



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
