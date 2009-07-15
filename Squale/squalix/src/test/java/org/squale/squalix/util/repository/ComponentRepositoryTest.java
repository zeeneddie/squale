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
package com.airfrance.squalix.util.repository;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.util.parser.JavaParser;

/**
 * Teste ComponentRepository.
 */
public class ComponentRepositoryTest
    extends SqualeTestCase
{

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** le projet à auditer */
    private ComponentRepository mRepository;

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        mAppli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid );
        // Enregistrement du ProjectBO dans la base
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        MapParameterBO params = new MapParameterBO();
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        PackageBO packageBO1 = getComponentFactory().createPackage( getSession(), mProject );
        PackageBO packageBO2 = getComponentFactory().createPackage( getSession(), "package2", packageBO1 );
        ClassBO classBO = getComponentFactory().createClass( getSession(), packageBO2 );
        mRepository = new ComponentRepository( mProject, getSession() );
    }

    /**
     * Teste de la méthode persisteClass avec une classe qui n'existe pas en base.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testPersisteNotExistingClass()
        throws JrafDaoException
    {
        // On crée une classe
        ClassBO classBO = createClassBO();
        ClassBO persistentClass = (ClassBO) mRepository.persisteComponent( classBO );
        getSession().commitTransactionWithoutClose();
        assertEquals( "ClassTest", persistentClass.getName() );
        assertTrue( -1 != persistentClass.getId() );
    }

    /**
     * Teste de la méthode persisteClass avec une classe qui existe en base.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testPersisteExistingClass()
        throws JrafDaoException
    {
        // On crée une classe
        ClassBO classBO = createClassBO();
        ClassBO persistentClass = (ClassBO) mRepository.persisteComponent( classBO );
        getSession().commitTransactionWithoutClose();
        ClassBO classResult = (ClassBO) mRepository.persisteComponent( classBO );
        assertEquals( "ClassTest", classResult.getName() );
        assertTrue( -1 != classResult.getId() );
    }

    /**
     * Teste la persistance d'une classe sans package.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testPersisteExistingClassWithoutPackage()
        throws JrafDaoException
    {
        // On crée une classe sans package
        ClassBO classBO = createClassBOWithoutPackage();
        ClassBO persistentClass = (ClassBO) mRepository.persisteComponent( classBO );
        getSession().commitTransactionWithoutClose();
        ClassBO classResult = (ClassBO) mRepository.persisteComponent( classBO );
        assertEquals( "ClassTest", classResult.getName() );
        assertTrue( -1 != classResult.getId() );
    }

    /**
     * Teste de la méthode persisteMethod avec une méthode qui n'existe pas en base.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testPersisteNotExistingMethod()
        throws JrafDaoException
    {
        // On crée une méthode
        ClassBO classBO = createClassBO();
        MethodBO methodBO = getComponentFactory().createMethod( getSession(), classBO );
        MethodBO persistentMethod = (MethodBO) mRepository.persisteComponent( methodBO );
        getSession().commitTransactionWithoutClose();
        assertEquals( "method", persistentMethod.getName() );
        assertEquals( "fileName", persistentMethod.getLongFileName() );
        assertTrue( -1 != persistentMethod.getId() );
    }

    /**
     * Teste de la méthode persisteMethod avec la classe de la méthode qui existe en base.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testPersisteMethodWithExistingClass()
        throws JrafDaoException
    {
        // On crée une classe
        ClassBO classBO1 = createClassBO();
        ClassBO persistentClass = (ClassBO) mRepository.persisteComponent( classBO1 );
        // On crée une méthode avec la classe persistante
        ClassBO classBO2 = createClassBO();
        MethodBO methodBO = getComponentFactory().createMethod( getSession(), classBO2 );
        MethodBO persistentMethod = (MethodBO) mRepository.persisteComponent( methodBO );
        getSession().commitTransactionWithoutClose();
        assertEquals( "method", persistentMethod.getName() );
        assertEquals( "fileName", persistentMethod.getLongFileName() );
        assertTrue( -1 != persistentMethod.getId() );
    }

    /**
     * @return une classe
     */
    private ClassBO createClassBO()
    {
        String absoluteClassName = "com.airfrance.projetTest.ClassTest";
        return createClassBO( absoluteClassName );
    }

    /**
     * @param pAbsoluteClassName le nom absolu de la classe
     * @return une classe
     */
    private ClassBO createClassBO( String pAbsoluteClassName )
    {
        JavaParser javaParser = new JavaParser( mProject );
        return javaParser.getClass( pAbsoluteClassName );
    }

    /**
     * @return une classe sans package
     */
    private ClassBO createClassBOWithoutPackage()
    {
        String absoluteClassName = "ClassTest";
        JavaParser javaParser = new JavaParser( mProject );
        return javaParser.getClass( absoluteClassName );
    }
}
