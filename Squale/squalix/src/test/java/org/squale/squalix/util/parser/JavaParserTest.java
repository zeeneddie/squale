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
package org.squale.squalix.util.parser;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Test pour le parser java
 */
public class JavaParserTest
    extends SqualeTestCase
{

    /**
     * Test la méthode getClass
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getClass(java.lang.String, java.lang.String)
     */
    public void testGetClass()
    {
        String fileName = "D:/chemin/projetTest/ClassTest.java";
        String absoluteClassName = "org.squale.projetTest.ClassTest";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertEquals( "ClassTest", classBO.getName() );
        assertEquals( 0, classBO.getChildren().size() );
        assertEquals( "projetTest", classBO.getParent().getName() );
        assertEquals( "squale", classBO.getParent().getParent().getName() );
        assertEquals( "org", classBO.getParent().getParent().getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getParent().getParent().getName() );
        // On test la récupération de la classe avec son fichier
        classBO = javaParser.getClass( absoluteClassName, "chemin/projetTest/ClassTest.java" );
        assertEquals( "chemin/projetTest/ClassTest.java", classBO.getFileName() );
    }

    /**
     * Test la méthode getClass avec classe imbriquée façon McCabe
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getClass(java.lang.String, java.lang.String)
     */
    public void testGetInnerClass()
    {
        String absoluteClassName = "org.squale.projetTest.ClassTest.InnerClass1";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        javaParser.addKnownClass( "org.squale.projetTest.ClassTest" );
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertEquals( "InnerClass1", classBO.getName() );
        assertEquals( 0, classBO.getChildren().size() );
        assertEquals( "ClassTest", classBO.getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getName() );
        assertEquals( "squale", classBO.getParent().getParent().getParent().getName() );
        assertEquals( "org", classBO.getParent().getParent().getParent().getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getParent().getParent().getParent().getName() );
    }

    /**
     * Test la méthode getClass avec classes imbriquées façon McCabe
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getClass(java.lang.String, java.lang.String)
     */
    public void testGetInnerClasses()
    {
        String absoluteClassName = "org.squale.projetTest.ClassTest.InnerClass1.InnerClass2";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        javaParser.addKnownClass( "org.squale.projetTest.ClassTest" );
        javaParser.addKnownClass( "org.squale.projetTest.ClassTest.InnerClass1" );
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertEquals( "InnerClass2", classBO.getName() );
        assertEquals( 0, classBO.getChildren().size() );
        assertEquals( "InnerClass1", classBO.getParent().getName() );
        assertEquals( "ClassTest", classBO.getParent().getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getParent().getName() );
        assertEquals( "squale", classBO.getParent().getParent().getParent().getParent().getName() );
        assertEquals( "org", classBO.getParent().getParent().getParent().getParent().getParent().getName() );
        assertEquals( "projetTest",
                      classBO.getParent().getParent().getParent().getParent().getParent().getParent().getName() );
    }

    /**
     * Teste la méthode getClass avec une classe interne définie dans un bloc
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetBlockInnerClass()
    {
        String absoluteClassName = "org.squale.projetTest.ClassTest$1InnerClass";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertEquals( "InnerClass", classBO.getName() );
        assertEquals( "ClassTest", classBO.getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getName() );
        assertEquals( "squale", classBO.getParent().getParent().getParent().getName() );
    }

    /**
     * Teste la méthode getClass avec une classe buggée par McCabe
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetIncorrectClassName()
    {
        String absoluteClassName = "org.squale.1.InnerClass";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertNull( classBO );
    }

    /**
     * Teste la méthode getMethod avec une classe interne
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetMethod()
    {
        String fileName = "ClassTest.java";
        String absoluteMethodName = "org.squale.projetTest.ClassTest$InnerClass.myMethod(String qq)";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertEquals( "myMethod(String qq)", methodBO.getName() );
        assertEquals( fileName, methodBO.getLongFileName() );
        assertEquals( "InnerClass", methodBO.getParent().getName() );
        assertEquals( "ClassTest", methodBO.getParent().getParent().getName() );
        assertEquals( "projetTest", methodBO.getParent().getParent().getParent().getName() );
        assertEquals( "squale", methodBO.getParent().getParent().getParent().getParent().getName() );
    }

    /**
     * Teste la méthode getMethod avec une méthode static
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetMethodStatic()
    {
        String fileName = "ClassTest.java";
        String absoluteMethodName = "org.squale.projetTest.ClassTest$InnerClass#StaticInitializer#1";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertEquals( "#StaticInitializer#1", methodBO.getName() );
        assertEquals( fileName, methodBO.getLongFileName() );
        assertEquals( "InnerClass", methodBO.getParent().getName() );
        assertEquals( "ClassTest", methodBO.getParent().getParent().getName() );
        assertEquals( "projetTest", methodBO.getParent().getParent().getParent().getName() );
        assertEquals( "squale", methodBO.getParent().getParent().getParent().getParent().getName() );
    }

    /**
     * Teste la méthode getMethod avec une méthode buggée par McCabe.
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetDuplicateMethod()
    {
        String fileName = "ClassTest.java";
        String absoluteMethodName = "org.squale.projetTest.ClassTest.main(java.lang.String)_#1";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertNull( methodBO );
    }

    /**
     * Teste la méthode getMethod avec une méthode définie dans une classe interne elle-même définie dans un bloc façon
     * McCabe.
     * 
     * @see org.squale.squalix.util.parser.JavaParser#getMethod(java.lang.String, java.lang.String)
     */
    public void testGetMcCabeMethod()
    {
        String fileName = "ClassTest.java";
        String absoluteMethodName = "org.squale.projetTest.ClassTest.method`InnerClass.main(java.lang.String)";
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        javaParser.addKnownClass( "org.squale.projetTest.ClassTest" );
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertEquals( "main(java.lang.String)", methodBO.getName() );
        assertEquals( fileName, methodBO.getLongFileName() );
        assertEquals( "InnerClass", methodBO.getParent().getName() );
        assertEquals( "ClassTest", methodBO.getParent().getParent().getName() );
        assertEquals( "projetTest", methodBO.getParent().getParent().getParent().getName() );
        assertEquals( "squale", methodBO.getParent().getParent().getParent().getParent().getName() );
    }

    /**
     * Teste le cas des classes anonymes McCabe
     */
    public void testAnonymousClasses()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        String fileName = "ClassTest.java";
        // Première méthode
        String absoluteMethodName =
            "org.squale.projetTest.ClassTest.method1(java.lang.String)$" + JavaParser.MC_CABE_ANONYMOUS_CLASS_NAME
                + "001_" + ".method1_1(java.io.File, java.lang.String)";
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertEquals( "method1_1(java.io.File, java.lang.String)", methodBO.getName() );
        assertEquals( fileName, methodBO.getLongFileName() );
        assertEquals( JavaParser.ANONYMOUS_CLASS_NAME + "1", methodBO.getParent().getName() );
        assertEquals( "ClassTest", methodBO.getParent().getParent().getName() );
        assertEquals( "projetTest", methodBO.getParent().getParent().getParent().getName() );
        assertEquals( "squale", methodBO.getParent().getParent().getParent().getParent().getName() );
        // Deuxième méthode
        String absoluteMethodName2 =
            "org.squale.projetTest.ClassTest.method2(java.lang.String)$" + JavaParser.MC_CABE_ANONYMOUS_CLASS_NAME
                + "001_" + ".method2_1(java.io.File, java.lang.String)";
        MethodBO methodBO2 = javaParser.getMethod( absoluteMethodName2, fileName );
        assertEquals( "method2_1(java.io.File, java.lang.String)", methodBO2.getName() );
        assertEquals( fileName, methodBO2.getLongFileName() );
        assertEquals( JavaParser.ANONYMOUS_CLASS_NAME + "2", methodBO2.getParent().getName() );
        assertEquals( "ClassTest", methodBO2.getParent().getParent().getName() );
        assertEquals( "projetTest", methodBO2.getParent().getParent().getParent().getName() );
        assertEquals( "squale", methodBO2.getParent().getParent().getParent().getParent().getName() );
        // Classe de la deuxième méthode
        String absoluteClassName2 =
            "org.squale.projetTest.ClassTest.method2(java.lang.String)$" + JavaParser.MC_CABE_ANONYMOUS_CLASS_NAME
                + "001_";
        ClassBO classBO2 = javaParser.getClass( absoluteClassName2 );
        assertEquals( JavaParser.ANONYMOUS_CLASS_NAME + "2", classBO2.getName() );
        assertEquals( "ClassTest", classBO2.getParent().getName() );
        assertEquals( "projetTest", classBO2.getParent().getParent().getName() );
        assertEquals( "squale", classBO2.getParent().getParent().getParent().getName() );
        // Classe de la première méthode
        String absoluteClassName =
            "org.squale.projetTest.ClassTest.method1(java.lang.String)$" + JavaParser.MC_CABE_ANONYMOUS_CLASS_NAME
                + "001_";
        ClassBO classBO = javaParser.getClass( absoluteClassName );
        assertEquals( JavaParser.ANONYMOUS_CLASS_NAME + "1", classBO.getName() );
        assertEquals( "ClassTest", classBO.getParent().getName() );
        assertEquals( "projetTest", classBO.getParent().getParent().getName() );
        assertEquals( "squale", classBO.getParent().getParent().getParent().getName() );
    }

    /**
     * Test les remontées statiques dans le cas d'un lien symbolique (McCabe casse ne remonte pas les packages)
     */
    public void testBrokenLink()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        JavaParser javaParser = new JavaParser( project );
        javaParser.addKnownClass( "ClasseTest.ClasseTest" );
        javaParser.addKnownClass( "ClasseTest" );
        String fileName = "ClassTest.java";
        String absoluteMethodName = "ClassTest.ClassTest#StaticInitializer#1";
        MethodBO methodBO = javaParser.getMethod( absoluteMethodName, fileName );
        assertEquals( "#StaticInitializer#1", methodBO.getName() );
    }

}
