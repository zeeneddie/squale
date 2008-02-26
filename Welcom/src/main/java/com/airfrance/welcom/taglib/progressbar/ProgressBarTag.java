/*
 * Créé le 24 août 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.progressbar;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.CanvasUtil;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * Tag de barre de progression
 * 
 * @author 6361371
 */
public class ProgressBarTag extends TagSupport {

	private static final String DEFAULT_REFRESH_RATE = "500";
	private static final long serialVersionUID = 6168631067224722725L;

	private String refreshRate = DEFAULT_REFRESH_RATE;

	private static IProgressbarRenderer render = (IProgressbarRenderer) RendererFactory.getRenderer(RendererFactory.PROGRESSBAR);

	/**
	 * Largeur de la zone utilisée par le composant.
	 */
	private String width;

	/**
	 * Fonction java script invoquée pour toute modification de l'etat de la
	 * progress bar.
	 */
	private String onChangeHook = "null";

	/**
	 * Fonction java script invoquée lors de l'arrêt de l'etat de la progress
	 * bar.
	 */
	private String onCompleteHook = "null";

	/**
	 * Indique si la zone de status doit être utilisée ou non.
	 */
	private boolean showStatusText;

	/**
	 * Position de la zone de texte de status (TOP, BOTTOM, LEFT, RIGHT).
	 */
	private String statusTextPosition = POS_TOP;

	/**
	 * Indique si on affiche la zone d'affichage du pct actuel
	 */
	private boolean showPctText;

	/**
	 * Position de la zone d'affichage du pourcentage (TOP, BOTTOM, LEFT,
	 * RIGHT).
	 */
	private String pctTextPosition = POS_RIGHT;

	/**
	 * Indique si la progressbar est en plein écran ou non.
	 * FullScreen => progressbar centrée dans une frame différente + reste de la fenêtre figé par div translucide.
	 * 
	 */
	private boolean isFullScreen;

	public static final String POS_TOP = "TOP";

	public static final String POS_BOTTOM = "BOTTOM";

	public static final String POS_RIGHT = "RIGHT";

	public static final String POS_LEFT = "LEFT";

	public static final String POS_UNDEFINED = "_UNDEFINED_";
	public static final String STYLE_TD_NOBORDER = "style=\"border-top:0px; border-bottom:0px;font:0.9em Verdana, Arial, Helvetica, sans-serif;\"";
    public static final String STYLE_TD_NOBORDER_PADDING = "style=\"border-top:0px; border-bottom:0px; padding:0px 10px 0px 10px;ont:0.9em Verdana, Arial, Helvetica, sans-serif;\"";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();



		try {
			// Inclusion de la librairie JS
			CanvasUtil.addJs(
				WelcomConfigurator.getMessage(
					WelcomConfigurator.HEADER_LOCALJS_PATH_KEY)
					+ "progressbar.js",
				this,
				pageContext);


			boolean isIE = false;
			boolean isSafari = false;
			boolean isOpera = false;
			String userAgent =
				((HttpServletRequest) pageContext.getRequest()).getHeader("User-Agent");
			
			if (userAgent != null) {
				if (userAgent.indexOf("MSIE") != -1) {
					isIE = true;
				} else {
					if (userAgent.indexOf("Safari") != -1) {
						isSafari = true;
					}
					else {
						if (userAgent.indexOf("Opera") != -1) {
							isOpera = true;
						}
					}
				}
			}

			String pbProperties = " wRefreshRate='" + refreshRate + "'";

			if (!GenericValidator.isBlankOrNull(onChangeHook)) {
				pbProperties += " wOnChangeHook='" + onChangeHook + "'";
			}

			if (!GenericValidator.isBlankOrNull(onCompleteHook)) {
				pbProperties += " wOnCompleteHook='" + onCompleteHook + "'";
			}
			
			pbProperties += " wIsFullScreen='" + isFullScreen + "'";
            
            out.println("<input type='hidden' id='wWatchedTaskId' name='wWatchedTaskId'>");

			out.println("<DIV id='" + id + "' " + pbProperties + ">");

			if (isFullScreen) {

				// Création de variable pour éviter de passer ces infos dans l'appel
				// JS



				if (isIE) {
					out.println(
						"<IFRAME frameborder=0 id=wDivProgressBarBG style=\"background-color:#ffffff;");
					out.println("filter:alpha(opacity=60);");
					out.println("visibility: hidden;");
					out.println("position:absolute; top:0px; left:0px; width:1400px; height:909px; z-index:123; padding:0; border-width:0; border-style:none, margin:0;\">");
					out.println("</IFRAME>");
				} else {
					out.println(
						"<div id=wDivProgressBarBG style=\"background-color:#ffffff;");
					out.print("-moz-opacity:0.6;");
					out.print("opacity: 0.6;");
					out.print("visibility: hidden;");
					out.print("position:absolute; top:0px; left:0px; width:100%; height:100%; z-index:123; margin:0;\">");
					out.println("</div>");
				}

				out.println("<div id=wDivProgressBar style=\"background-color:#FFFFFF;");
				out.print("position:absolute;");
				out.print("left:50%;");
				out.print("top:50%;");
				out.print("visibility: hidden;");
				out.print("margin-left:-50px;");
				out.print("margin-top:-50px; ");
				out.print("z-index:124;");
				out.print("border:1px solid #888\">");
			} // Fin IF isFullScreen


			// Création de la zone d'affichage
			String tdStatus;
			if (showStatusText) {
				tdStatus =
					"<span style=\"color:#000\" id='"
						+ getId()
						+ "_status'>-</span>";
			} else {
				tdStatus = "";
			}

			String tdPct;
			if (showPctText) {
				tdPct =
					"<span style=\"color:#000\" id=\""
						+ getId()
						+ "_pctText\">0%</span>";
			} else {
				tdPct = "";
			}

			boolean leftExists = false;
			boolean topExists = false;
			boolean rightExists = false;
			boolean bottomExists = false;
			boolean progressSolo = true;

			if (showPctText || showStatusText) {
				if ((pctTextPosition.equals(POS_LEFT))
					|| (statusTextPosition.equals(POS_LEFT))) {
					leftExists = true;
					progressSolo = false;
				}

				if ((pctTextPosition.equals(POS_TOP))
					|| (statusTextPosition.equals(POS_TOP))) {
					topExists = true;
					progressSolo = false;
				}

				if ((pctTextPosition.equals(POS_RIGHT))
					|| (statusTextPosition.equals(POS_RIGHT))) {
					rightExists = true;
					progressSolo = false;
				}

				if ((pctTextPosition.equals(POS_BOTTOM))
					|| (statusTextPosition.equals(POS_BOTTOM))) {
					bottomExists = true;
					progressSolo = false;
				}
			}

			if (isSafari){
				progressSolo = true;
				bottomExists = false;
				topExists = false;
				leftExists = false;
				rightExists = false;
			}

            if(isFullScreen())
            {
                //out.println("<div height=\"50px\" width=\"100%\" style=\"background-image:url(http://cmsintranet.airfrance.fr/charte_v03_001/img/lignage/lignage_trans.gif)\">&nbsp;</div>");
                out.println("<div style=\"background-image:url(theme/charte_v03_001/img/lignage/lignage_trans.gif);background-position:left bottom;background-repeat:repeat-x;height:32px;width:"+width+"px;\" class=\"bg_theme\"><div style=\"margin: 0pt; padding: 0px 5px; font-family: Verdana,Arial,Helvetica,sans-serif; font-weight: bold; font-size: 10px; color: rgb(255, 255, 255); background-color: rgb(5, 16, 57); cursor: default; height: 16px; float: left;\">Execution</div></div>");
                //out.println("<div style=\"background-image:url(http://cmsintranet.airfrance.fr/charte_v03_001/img/lignage/lignage_trans.gif);line-height:32px;\" class=\"bg_theme\">&nbsp</div>");
            }
            
//			out.println("<div>");
			out.println("<table border=\"0\" cellspacing=\"2px\" style=\"0px 3px 3px 3px\" width=\"" + width + "px\" >");

			// Formattage
			out.print("<tr>");
			if (leftExists) {
				out.print("<th style='width: 15%'></th>");
			}



			if (!progressSolo) {
				if (leftExists && rightExists) {
					out.print("<th style='width: 70%'></th>");
				} else {
					if (leftExists || rightExists){
						out.print("<th style='width: 85%'></th>"); 
					}
					else {
						out.print("<th style='width: 100%'></th>");
					}
						
				}
			}

			if (rightExists) {
				out.print("<th style='width: 15%'></th>");
			}

			out.println("</tr>");

			// ###############
			// LIGNE du haut
			// ###############

			if (topExists) {
				out.println("<tr>");

				// ------------

				if (leftExists || rightExists) {
					out.println("<td " + STYLE_TD_NOBORDER_PADDING + " colspan=2>");
				} else {
					out.println("<td " + STYLE_TD_NOBORDER_PADDING + " >");
				}

				if (pctTextPosition.equals(POS_TOP)) {
					out.println(tdPct);
				}

				if (statusTextPosition.equals(POS_TOP)) {
					out.println(tdStatus);
				}
				out.println("</td>");

				// ------------

				out.println("</tr>");
			}

			// ###############
			// LIGNE du milieu
			// ###############
			// ------------
			out.println("<tr>");
			if (leftExists) {

				out.println("<td " + STYLE_TD_NOBORDER_PADDING + " >");
				if (pctTextPosition.equals(POS_LEFT)) {
					out.println(tdPct);
				}

				if (statusTextPosition.equals(POS_LEFT)) {
					out.println(tdStatus);
				}
				out.println("</td>");

			}
			// ------------

			out.print(" <td " + STYLE_TD_NOBORDER_PADDING +">");

			// ----- PROGRESS BAR -----
			if (isSafari){
				out.print(render.drawAnimatedProgressBar());
			}
			else {
				out.print(render.drawRealProgressBar(getId()));
			}
			
			// ----- PROGRESS BAR -----
			out.println("	</td>");

			// ------------
			if (rightExists) {
				out.println("<td " + STYLE_TD_NOBORDER + " >");
				if (pctTextPosition.equals(POS_RIGHT)) {
					out.println(tdPct);
				}

				if (statusTextPosition.equals(POS_RIGHT)) {
					out.println(tdStatus);
				}
				out.println("</td>");
			}

			out.println("</tr>");
			// ------------

			// ###############
			// LIGNE du bas
			// ###############
			if (bottomExists) {
				out.println("<tr>");

				// ------------

				if (leftExists || rightExists) {
					out.println("<td  " + STYLE_TD_NOBORDER_PADDING + " colspan=2>");
				} else {
					out.println("<td " + STYLE_TD_NOBORDER_PADDING + " >");
				}

				if (pctTextPosition.equals(POS_BOTTOM)) {
					out.println(tdPct);
				}

				if (statusTextPosition.equals(POS_BOTTOM)) {
					out.println(tdStatus);
				}
				out.println("</td>");

				// ------------

				out.println("</tr>");
			}

			out.println("</table>");
//			out.println("</div>");
			if (isFullScreen) {
                out.println("<div style=\"background-image:url(theme/charte_v03_001/img/lignage/footer_trans.gif);background-position:left bottom;clear:both;height:20px;width:"+width+"px;\" class=\"bg_theme\"></div>");
				out.println("</div>");
			}
			out.println("</div>");
		} catch (IOException e) {
			try {
				out.println(
					"unable to render tag due to "
						+ e.getClass().getName()
						+ ":"
						+ e.getMessage());
			} catch (IOException e1) {
				// On fait rien car l'exception doit être la même que celle du
				// bloc catch englobant
			}
			e.printStackTrace();
		}

		// Continue processing this page
		release();
		return (EVAL_PAGE);
	}


	/**
	 * @return
	 */
	public String getOnCompleteHook() {
		return onCompleteHook;
	}

	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setOnCompleteHook(String string) {
		onCompleteHook = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @return
	 */
	public String getOnChangeHook() {
		return onChangeHook;
	}

	/**
	 * @param string
	 */
	public void setOnChangeHook(String string) {
		onChangeHook = string;
	}

	/**
	 * @return
	 */
	public String getPctTextPosition() {
		return pctTextPosition;
	}

	/**
	 * @return
	 */
	public boolean isShowPctText() {
		return showPctText;
	}

	/**
	 * @return
	 */
	public boolean isShowStatusText() {
		return showStatusText;
	}

	/**
	 * @return
	 */
	public String getStatusTextPosition() {
		return statusTextPosition;
	}

	/**
	 * @param string
	 */
	public void setPctTextPosition(String string) {
		pctTextPosition = string;
	}

	/**
	 * @param b
	 */
	public void setShowPctText(boolean b) {
		showPctText = b;
	}

	/**
	 * @param b
	 */
	public void setShowStatusText(boolean b) {
		showStatusText = b;
	}

	/**
	 * @param string
	 */
	public void setStatusTextPosition(String string) {
		statusTextPosition = string;
	}

	/**
	 * @return
	 */
	public boolean isFullScreen() {
		return isFullScreen;
	}

	/**
	 * @param b
	 */
	public void setFullScreen(boolean b) {
		isFullScreen = b;
	}

	/**
	 * @return
	 */
	public String getRefreshRate() {
		return refreshRate;
	}

	/**
	 * @param string
	 */
	public void setRefreshRate(String string) {
		refreshRate = string;
	}

}
