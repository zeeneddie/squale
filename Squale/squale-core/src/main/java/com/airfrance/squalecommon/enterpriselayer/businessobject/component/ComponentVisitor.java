package com.airfrance.squalecommon.enterpriselayer.businessobject.component;

/**
 * Visiteur de composant
 * Ce design pattern permet d'externaliser des traitements
 * sur les composants
 */
public interface ComponentVisitor {
    
    /**
     * @param pApplication l'application
     * @param pArgument argument
     * @return objet
     */
    public Object visit(ApplicationBO pApplication, Object pArgument);
    
    /**
     * 
     * @param pProject le projet
     * @param pArgument argument
     * @return objet
     */
    public Object visit(ProjectBO pProject, Object pArgument);
    
    /**
     * @param pPackage la class
     * @param pArgument argument
     * @return objet
     */
    public Object visit(PackageBO pPackage, Object pArgument);
    
    /**
     * @param pClass la class
     * @param pArgument argument
     * @return objet
     */
    public Object visit(ClassBO pClass, Object pArgument);
    
    /**
     * 
     * @param pMethod la méthode
     * @param pArgument argument
     * @return objet
     */
    public Object visit(MethodBO pMethod, Object pArgument);
    
    /**
     * @param pJsp la jsp
     * @param pArgument argument
     * @return objet
     */
    public Object visit(JspBO pJsp, Object pArgument);
    
    /**
     * @param pUMLModel le model UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit(UmlModelBO pUMLModel, Object pArgument);
    
    /**
     * @param pUMLPackage le package UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit(UmlPackageBO pUMLPackage, Object pArgument);
    
    /**
     * @param pUMLInterface l'interface UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit(UmlInterfaceBO pUMLInterface, Object pArgument);
    
    /**
     * @param pUMLClass la classe UML
     * @param pArgument argument
     * @return objet
     */
    public Object visit(UmlClassBO pUMLClass, Object pArgument);

}
