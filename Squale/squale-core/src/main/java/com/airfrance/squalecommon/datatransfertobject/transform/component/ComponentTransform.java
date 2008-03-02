package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlModelBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlPackageBO;

/**
 * Transforme les composants en bo<->ComponentDTO
 */
public class ComponentTransform
    implements Serializable, ComponentVisitor
{

    /**
     * Constructeur prive
     */
    private ComponentTransform()
    {
    }

    /**
     * DTO -> BO pour un Component
     * 
     * @param pComponentDTO ComponentDTO à transformer
     * @return ComponentBO
     * @deprecated A ne pas utiliser
     */
    public static AbstractComponentBO dto2BoProxy( ComponentDTO pComponentDTO )
    {
        return null;
    }

    /**
     * BO -> DTO pour un Component
     * 
     * @param pComponentBO ComponentBO
     * @return ComponentDTO
     */
    public static ComponentDTO bo2Dto( AbstractComponentBO pComponentBO )
    {
        ComponentDTO componentDTO = (ComponentDTO) pComponentBO.accept( new ComponentTransform(), null );
        return componentDTO;
    }

    /**
     * bo -> dto avec le nom complet du composant
     * 
     * @param pComponentBO le composant à transformer
     * @return le dto
     */
    public static ComponentDTO bo2DtoWithFullName( AbstractComponentBO pComponentBO )
    {
        ComponentDTO dto = bo2Dto( pComponentBO );
        dto.setName( pComponentBO.getFullName() );
        return dto;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO,
     *      java.lang.Object)
     */
    public Object visit( ApplicationBO pApplication, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pApplication );
        componentDTO.setLastUpdate( pApplication.getLastUpdate() );
        componentDTO.setLastUser( pApplication.getLastUser() );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO,
     *      java.lang.Object)
     */
    public Object visit( ProjectBO pProject, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pProject );
        componentDTO.setTechnology( pProject.getName() );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO,
     *      java.lang.Object)
     */
    public Object visit( PackageBO pPackage, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pPackage );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO,
     *      java.lang.Object)
     */
    public Object visit( ClassBO pClass, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pClass );
        componentDTO.setFileName( pClass.getFileName() );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO,
     *      java.lang.Object)
     */
    public Object visit( MethodBO pMethod, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonAttributes( pMethod );
        componentDTO.setFileName( pMethod.getLongFileName() );
        componentDTO.setStartLine( "" + pMethod.getStartLine() );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.JspBO,
     *      java.lang.Object)
     */
    public Object visit( JspBO pJsp, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonAttributes( pJsp );
        componentDTO.setFileName( pJsp.getFileName() );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlModelBO,
     *      java.lang.Object)
     */
    public Object visit( UmlModelBO pUMLModel, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pUMLModel );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlPackageBO,
     *      java.lang.Object)
     */
    public Object visit( UmlPackageBO pUMLPackage, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pUMLPackage );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlInterfaceBO,
     *      java.lang.Object)
     */
    public Object visit( UmlInterfaceBO pUMLInterface, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonAttributes( pUMLInterface );
        return componentDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentVisitor#visit(com.airfrance.squalecommon.enterpriselayer.businessobject.component.UmlClassBO,
     *      java.lang.Object)
     */
    public Object visit( UmlClassBO pUMLClass, Object pArgument )
    {
        ComponentDTO componentDTO = setCommonComplexAttributes( pUMLClass );
        return componentDTO;
    }

    /**
     * Modifie les atrtributs communs pour les composants complexes
     * 
     * @param pComponentBO le composant sous forme BO
     * @return le composant sous forme DTO avec les paramétres communs modifiés
     */
    private ComponentDTO setCommonComplexAttributes( AbstractComplexComponentBO pComponentBO )
    {
        ComponentDTO componentDTO = setCommonAttributes( pComponentBO );
        componentDTO.setNumberOfChildren( pComponentBO.getChildren().size() );
        return componentDTO;
    }

    /**
     * @param pComponentBO le composant sous forme BO
     * @return le composant sous forme DTO avec les paramétres communs modifiés
     */
    private ComponentDTO setCommonAttributes( AbstractComponentBO pComponentBO )
    {
        // Initialisation du retour
        ComponentDTO componentDTO = new ComponentDTO();
        componentDTO.setExcludedFromActionPlan( pComponentBO.getExcludedFromActionPlan() );
        componentDTO.setJustification( pComponentBO.getJustification() );
        componentDTO.setID( pComponentBO.getId() );
        componentDTO.setName( pComponentBO.getName() );
        componentDTO.setFullName( pComponentBO.getFullName() );
        componentDTO.setHasResults( pComponentBO.hasResults() );
        componentDTO.setType( pComponentBO.getType() );
        componentDTO.setNumberOfChildren( 0 );
        if ( null != pComponentBO.getParent() )
        {
            componentDTO.setIDParent( pComponentBO.getParent().getId() );
        }
        return componentDTO;
    }
}
