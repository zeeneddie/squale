package testCommon.test;

/**
 *
 */
public class TestCommon {

    private int mTestNumber;
    
    public TestCommon() {
        this(0);
    }
    
    public TestCommon(int pTestNumber){
        mTestNumber = pTestNumber;
    }

    public static void main(String[] args) {
            
    }
    
    public void setTestNumber(int pTestNumber){
        mTestNumber = pTestNumber;
    }
    
    public int getTestNumber(){
        return mTestNumber;
    }
    
    public String getDisplayString(){
        return "la valeur du nombre de test est : "+mTestNumber;
    }
    
}
