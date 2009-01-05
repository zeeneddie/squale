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
package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

/**
 * Visiteur de composant Ce design pattern permet d'externaliser des traitements sur les composants
 */
public interface ComponentVisitor
{

    /**
     * @param pApplication l'application
     * @param pArgument argument
     * @return objet
     */
    public Object visit( ApplicationBO pApplication, Object pArgument );

    /**
     * @param pProject le projet
     * @param pArgument argument
     * @return objet
     */
    public Object visit( ProjectBO pProject, Object pArgument );

    /**
     * @param pPackage la class
     * @param pArgument argument
     * @return objet
     */
    public Object visit( PackageBO pPackage, Object pArgument );

    /**
     * @param pClass la class
     * @param pArgument argument
     * @return objet
     */
    public Object visit( ClassBO pClass, Object pArgument );

    /**
     * @param pMethod la méthode
     * @param pArgument argument
     * @return objet
     */
    public Object visit( MethodBO pMethod, Object pArgument );

    /**
     * @param pJsp la jsp
     * @param pArgument argument
     * @return objet
     */
    public Object visit( JspBO pJsp, Object pArgument );

    /**
     * @param pUMLModel le model UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit( UmlModelBO pUMLModel, Object pArgument );

    /**
     * @param pUMLPackage le package UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit( UmlPackageBO pUMLPackage, Object pArgument );

    /**
     * @param pUMLInterface l'interface UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit( UmlInterfaceBO pUMLInterface, Object pArgument );

    /**
     * @param pUMLClass la classe UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit( UmlClassBO pUMLClass, Object pArgument );

}
