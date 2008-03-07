package test;

/**
 * Class commune
 */
public class TestCommon {

    private int mTestNumber;

    public TestCommon() {
        this(0);
    }

    /** constructeur */
    public TestCommon(int pTestNumber) {
        mTestNumber = pTestNumber;
    }

    public static void main(String[] args) {

    }

    public void setTestNumber(int pTestNumber) {
        mTestNumber = pTestNumber;
    }

    public int getTestNumber() {
        return mTestNumber;
    }

    public String getDisplayString() {
        return "la valeur du nombre de test est : " + mTestNumber;
    }

    // Pour tester avec 2 méthodes aux signatures différentes
    // en particulier pour RSM
    // On rajoute du code bidon pour augmenter la complexité
    

    public String getDisplayString(String test) {
        String result = test ;
        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                result += i;
            } else {
                result = "-" + i;
            }
            String whatFor = "?";
        }
        return "la valeur du nombre de test est : " + result;
    }
    
    

}
