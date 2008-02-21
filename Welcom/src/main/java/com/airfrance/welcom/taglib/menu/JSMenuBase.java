package com.airfrance.welcom.taglib.menu;

import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * @author user
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JSMenuBase {
    /** constante*/
    public static final String DEFAULT_NAME = "m";
    /** attribut*/
    private String libelle;
    /** attribut*/
    private JSMenuColor color;
    /** attribut*/
    private String action;
    /** attribut*/
    private String name = DEFAULT_NAME;
    /** render */
    private IMenuRender render = (IMenuRender)RendererFactory.getRenderer(RendererFactory.MENU);

    /**
     * Returns the action.
     * @return String
     */
    public String getAction() {
        return render.getAction(action);
    }

    /**
     * Returns the libelle.
     * @return String
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Sets the action.
     * @param pAction The action to set
     */
    public void setAction(final String pAction) {
        action = pAction;
    }

    /**
     * Sets the libelle.
     * @param pLibelle The libelle to set
     */
    public void setLibelle(final String pLibelle) {
        libelle = pLibelle;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param string le name
     */
    public void setName(final String string) {
        name = string;
    }

    /**
     * @return color
     */
    public JSMenuColor getColor() {
        return color;
    }

    /**
     * @param pColor la nouvelle color
     */
    public void setColor(final JSMenuColor pColor) {
        color = pColor;
    }
}