package com.airfrance.squalix.util.parser;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;

/**
 * Test de parser Cpp
 */
public class CppParserTest
    extends SqualeTestCase
{

    /**
     * Test de l'obtention du nom de la classe Les test comportent des noms de classe qualifiées ou non qualifiées ainsi
     * que l'usage de templates
     */
    public void testgetClassName()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        CppParser parser = new CppParser( project );
        String className;
        StringBuffer context = new StringBuffer();
        className = parser.geRelativeElementName( "vector<bool,allocator<bool>>::reference", context );
        assertEquals( "reference", className );
        assertEquals( "vector<bool,allocator<bool>>", context.toString() );
        className = parser.geRelativeElementName( "locale::facet", context );
        assertEquals( "facet", className );
        assertEquals( "locale", context.toString() );
        className = parser.geRelativeElementName( "char_traits<char>", context );
        assertEquals( "char_traits<char>", className );
        assertEquals( "", context.toString() );
        className = parser.geRelativeElementName( "__rwstd::rw_traits<char,char_traits<char>>", context );
        assertEquals( "rw_traits<char,char_traits<char>>", className );
        assertEquals( "__rwstd", context.toString() );
        className =
            parser.geRelativeElementName( "__rwstd::__string_ref<wchar_t,char_traits<wchar_t>,allocator<wchar_t>>",
                                          context );
        assertEquals( "__string_ref<wchar_t,char_traits<wchar_t>,allocator<wchar_t>>", className );
        assertEquals( "__rwstd", context.toString() );
        className =
            parser.geRelativeElementName(
                                          "__rwstd::__rw_basis<wchar_t_*,basic_string<wchar_t,char_traits<wchar_t>,allocator<wchar_t>>::allocator_type>",
                                          context );
        assertEquals(
                      "__rw_basis<wchar_t_*,basic_string<wchar_t,char_traits<wchar_t>,allocator<wchar_t>>::allocator_type>",
                      className );
        assertEquals( "__rwstd", context.toString() );
    }

    /**
     * Test de décomposition de classe
     */
    public void testGetClass()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        CppParser parser = new CppParser( project );

        // Test sans namespace
        ClassBO cls = parser.getClass( "class" );
        assertEquals( PackageBO.class, cls.getParent().getClass() );
        assertEquals( ProjectBO.class, cls.getParent().getParent().getClass() );

        // Test avec classe imbriquée
        parser.addKnownClass( "class" );
        cls = parser.getClass( "class::nestedclass" );
        assertEquals( ClassBO.class, cls.getParent().getClass() );
        assertEquals( PackageBO.class, cls.getParent().getParent().getClass() );
        assertEquals( ProjectBO.class, cls.getParent().getParent().getParent().getClass() );

        // Test avec namespace
        cls = parser.getClass( "pkg::class" );
        assertEquals( PackageBO.class, cls.getParent().getClass() );
        assertEquals( ProjectBO.class, cls.getParent().getParent().getClass() );
    }

    /**
     * Test de la méthode getMethodName
     */
    public void testGetMethodName()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        CppParser parser = new CppParser( project );
        String methodName;
        // Méthode simple
        methodName = parser.getMethodName( "length_error::length_error(const_string_&)" );
        assertEquals( "length_error(const_string_&)", methodName );
        // Méthode avec namespace
        methodName = parser.getMethodName( "namespace::length_error::length_error(const_string_&)" );
        assertEquals( "length_error(const_string_&)", methodName );
        // Méthode avec template
        methodName =
            parser.getMethodName( "char_traits<wchar_t>::compare(const_char_traits<wchar_t>::char_type_*,const_char_traits<wchar_t>::char_type_*,size_t)" );
        assertEquals(
                      "compare(const_char_traits<wchar_t>::char_type_*,const_char_traits<wchar_t>::char_type_*,size_t)",
                      methodName );
    }

    /**
     * Test de décomposition de méthode
     */
    public void testGetMethod()
    {
        ProjectBO project = new ProjectBO( "projetTest" );
        CppParser parser = new CppParser( project );

        // Test sans namespace
        MethodBO method = parser.getMethod( "class::method(int arg1, int arg2)", "file.C" );
        assertEquals( ClassBO.class, method.getParent().getClass() );
        assertEquals( PackageBO.class, method.getParent().getParent().getClass() );
        assertEquals( ProjectBO.class, method.getParent().getParent().getParent().getClass() );

        // Test d'une fonction
        method = parser.getMethod( "function(int arg1, int arg2)", "file.C" );
        assertEquals( ClassBO.class, method.getParent().getClass() );
        assertEquals( PackageBO.class, method.getParent().getParent().getClass() );
        assertEquals( ProjectBO.class, method.getParent().getParent().getParent().getClass() );

        // Test avec classe imbriquée
        parser.addKnownClass( "class" );
        method = parser.getMethod( "class::nestedclass::method(int arg1, int arg2)", "file.C" );
        assertEquals( ClassBO.class, method.getParent().getClass() );
        assertEquals( ClassBO.class, method.getParent().getParent().getClass() );
        assertEquals( PackageBO.class, method.getParent().getParent().getParent().getClass() );
        assertEquals( ProjectBO.class, method.getParent().getParent().getParent().getParent().getClass() );

        // Test avec namespace
        method = parser.getMethod( "pkg::class::method(int arg1, int arg2)", "file.C" );
        assertEquals( ClassBO.class, method.getParent().getClass() );
        assertEquals( PackageBO.class, method.getParent().getParent().getClass() );
        assertEquals( ProjectBO.class, method.getParent().getParent().getParent().getClass() );

        // Test avec un nom de méthode mal formé
        method = parser.getMethod( "pkg::class::method#int arg1, int arg2)", "file.C" );
        assertEquals( null, method );
    }

}
