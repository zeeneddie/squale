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
package org.squale.welcom.taglib.renderer;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.outils.Charte;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.button.impl.ButtonRendererv3001;
import org.squale.welcom.taglib.button.impl.ButtonSkinv1;
import org.squale.welcom.taglib.button.impl.ButtonSkinv2;
import org.squale.welcom.taglib.button.impl.ButtonSkinv3;
import org.squale.welcom.taglib.canvas.impl.CanvasCenterRendererV2001;
import org.squale.welcom.taglib.canvas.impl.CanvasCenterRendererV2002;
import org.squale.welcom.taglib.canvas.impl.CanvasCenterRendererV3001;
import org.squale.welcom.taglib.canvas.impl.CanvasHeaderRendererV2001;
import org.squale.welcom.taglib.canvas.impl.CanvasHeaderRendererV2002;
import org.squale.welcom.taglib.canvas.impl.CanvasHeaderRendererV3001;
import org.squale.welcom.taglib.canvas.impl.CanvasLeftMenuTagRendererV2001;
import org.squale.welcom.taglib.canvas.impl.CanvasLeftMenuTagRendererV2002;
import org.squale.welcom.taglib.canvas.impl.CanvasLeftMenuTagRendererV3001;
import org.squale.welcom.taglib.canvas.impl.CanvasPopupRendererV2001;
import org.squale.welcom.taglib.canvas.impl.CanvasPopupRendererV2002;
import org.squale.welcom.taglib.canvas.impl.CanvasPopupRendererV3001;
import org.squale.welcom.taglib.canvas.impl.CanvasRendererV2001;
import org.squale.welcom.taglib.canvas.impl.CanvasRendererV2002;
import org.squale.welcom.taglib.canvas.impl.CanvasRendererV3001;
import org.squale.welcom.taglib.formulaire.impl.FormulaireBottomRendererV200X;
import org.squale.welcom.taglib.formulaire.impl.FormulaireBottomRendererV3001;
import org.squale.welcom.taglib.menu.impl.MenuSkinLight;
import org.squale.welcom.taglib.menu.impl.MenuSkinV2;
import org.squale.welcom.taglib.menu.impl.MenuSkinV3001;
import org.squale.welcom.taglib.onglet.impl.JSOngletRendererV2001;
import org.squale.welcom.taglib.onglet.impl.JSOngletRendererV2002;
import org.squale.welcom.taglib.onglet.impl.JSOngletRendererV3001;
import org.squale.welcom.taglib.progressbar.impl.ProgressbarRendererV2001;
import org.squale.welcom.taglib.progressbar.impl.ProgressbarRendererV2002;
import org.squale.welcom.taglib.progressbar.impl.ProgressbarRendererV3001;
import org.squale.welcom.taglib.table.impl.TableNavigatorRendererV200X;
import org.squale.welcom.taglib.table.impl.TableNavigatorRendererV3001;
import org.squale.welcom.taglib.table.impl.TableRendererV2001;
import org.squale.welcom.taglib.table.impl.TableRendererV2002;
import org.squale.welcom.taglib.table.impl.TableRendererV3001;


/**
 * Class fournissant tout les renders
 * 
 * @author M327837
 */
public class RendererFactory
{

    /** logger */
    private static Log logger = LogFactory.getLog( RendererFactory.class );

    /** Clef du renderer pour le canasHeader */
    public final static String CANVAS_HEADER = "canvasheader";

    /** Clef du renderer pour le canvasLeft */
    public final static String CANVAS_LEFT = "canvasleft";

    /** Clef du renderer pour le canvas (body) */
    public final static String CANVAS = "canvas";

    /** Clef du renderer pour le canvas center */
    public final static String CANVAS_CENTER = "canvascenter";

    /** Clef du renderer pour le canvas center */
    public final static String CANVAS_POPUP = "canvaspopup";

    /** Clef du renderer pour le canvas center */
    public final static String MENU = "menu";

    /** Clef du renderer pour boutons */
    public final static String BUTTON = "bouton";

    /** Clef du renderer pour la barre de boutons formulaire */
    public final static String FORM_BOTTOM_BAR = "formbar";

    /** Clef du renderer pour la barre de boutons formulaire */
    public final static String TABLE = "table";

    /** Clef du renderer pour la barre de boutons formulaire */
    public final static String TABLE_NAVIGATOR = "tablenavigator";

    /** Clef du renderer pour la barre de boutons formulaire */
    public final static String ONGLET = "onlget";

    /** Clef du renderer pour la barre de boutons formulaire */
    public final static String PROGRESSBAR = "progbar";

    /** Map du tous les renderers */
    private static HashMap renders = null;

    /**
     * Retourne le renderer demandé
     * 
     * @param renderName nom du renderer
     * @return le rendere demandé
     */
    public static Object getRenderer( String renderName )
    {
        if ( renders == null )
        {
            init();
        }
        return renders.get( renderName );
    }

    /**
     * Initialise les renders ...
     */
    private static void init()
    {
        logger.info( "initialisation de render des taglibs en fonction de la charte graphique" );
        renders = new HashMap();
        if ( WelcomConfigurator.getCharte() == Charte.V2_001 )
        {
            renders.put( CANVAS_HEADER, new CanvasHeaderRendererV2001() );
            renders.put( CANVAS_LEFT, new CanvasLeftMenuTagRendererV2001() );
            renders.put( CANVAS, new CanvasRendererV2001() );
            renders.put( CANVAS_CENTER, new CanvasCenterRendererV2001() );
            renders.put( CANVAS_POPUP, new CanvasPopupRendererV2001() );
            if ( isMenuLight() )
            {
                renders.put( MENU, new MenuSkinLight() );
            }
            else
            {
                renders.put( MENU, new MenuSkinV2() );
            }
            renders.put( BUTTON, new ButtonSkinv1() );
            renders.put( FORM_BOTTOM_BAR, new FormulaireBottomRendererV200X() );
            renders.put( TABLE, new TableRendererV2001() );
            renders.put( TABLE_NAVIGATOR, new TableNavigatorRendererV200X() );
            renders.put( ONGLET, new JSOngletRendererV2001() );
            renders.put( PROGRESSBAR, new ProgressbarRendererV2001() );
        }
        else if ( WelcomConfigurator.getCharte() == Charte.V2_002 )
        {
            renders.put( CANVAS_HEADER, new CanvasHeaderRendererV2002() );
            renders.put( CANVAS_LEFT, new CanvasLeftMenuTagRendererV2002() );
            renders.put( CANVAS, new CanvasRendererV2002() );
            renders.put( CANVAS_CENTER, new CanvasCenterRendererV2002() );
            renders.put( CANVAS_POPUP, new CanvasPopupRendererV2002() );
            if ( isMenuLight() )
            {
                renders.put( MENU, new MenuSkinLight() );
            }
            else
            {
                renders.put( MENU, new MenuSkinV2() );
            }
            if ( isButtonSkinv2() )
            {
                renders.put( BUTTON, new ButtonSkinv2() );
            }
            else
            {
                renders.put( BUTTON, new ButtonSkinv3() );
            }
            renders.put( FORM_BOTTOM_BAR, new FormulaireBottomRendererV200X() );
            renders.put( TABLE, new TableRendererV2002() );
            renders.put( TABLE_NAVIGATOR, new TableNavigatorRendererV200X() );
            renders.put( ONGLET, new JSOngletRendererV2002() );
            renders.put( PROGRESSBAR, new ProgressbarRendererV2002() );

        }
        else
        {
            renders.put( CANVAS_HEADER, new CanvasHeaderRendererV3001() );
            renders.put( CANVAS_LEFT, new CanvasLeftMenuTagRendererV3001() );
            renders.put( CANVAS, new CanvasRendererV3001() );
            renders.put( CANVAS_CENTER, new CanvasCenterRendererV3001() );
            renders.put( CANVAS_POPUP, new CanvasPopupRendererV3001() );
            renders.put( MENU, new MenuSkinV3001() );
            renders.put( BUTTON, new ButtonRendererv3001() );
            renders.put( FORM_BOTTOM_BAR, new FormulaireBottomRendererV3001() );
            renders.put( TABLE, new TableRendererV3001() );
            renders.put( ONGLET, new JSOngletRendererV3001() );
            renders.put( TABLE_NAVIGATOR, new TableNavigatorRendererV3001() );
            renders.put( PROGRESSBAR, new ProgressbarRendererV3001() );
        }

    }

    /**
     * Retourn si c'est un menu light
     * 
     * @return menu light
     */
    private static boolean isMenuLight()
    {
        final String menuKey = WelcomConfigurator.getMessage( WelcomConfigurator.WELCOM_MENU_LIGHT );
        return Util.isTrue( menuKey );
    }

    /**
     * Retourn vrai si les boutons sont charte V2
     * 
     * @return vrai si les boutons sont charte V2
     */
    private static boolean isButtonSkinv2()
    {
        final String classButtonSkinName = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_BOUTON_SKIN );
        return ( "org.squale.welcom.taglib.button.ButtonSkinv2".equals( classButtonSkinName ) );
    }

}
