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
