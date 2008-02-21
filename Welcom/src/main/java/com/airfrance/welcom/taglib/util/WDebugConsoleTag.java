/*
 * Créé le 10 oct. 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author 6361371
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WDebugConsoleTag extends TagSupport {

	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {

			out.print("<SCRIPT>");
			out.print("function wJSDebug(msg){");
			out.print("	var content = document.getElementById(\"wDbgZone\").value;");

			out.print("	if (content.length > 5000){");
			out.print("		content = content.substring (100, content.length);");
			out.print("	}");

			out.print("	document.getElementById(\"wDbgZone\").value = content + \"\\n\" + msg;");
			out.print("}");
			out.print("</SCRIPT>");
			out.print("<div id=wDbgConsole style=\"z-index: 201; width:300; visibility: visible;  margin: 0 2px; font-size: 8px;	line-height: 1px \">");
			out.print("		<table>");
			out.print("		<tr>");
			out.print("			<textarea id=\"wDbgZone\" cols='80' rows='10'  style='font:Verdana, Sans-serif;' ></textarea>");
			out.print("		</tr>");
			out.print("		<tr>");
			out.print("			<td align='center'>");
			out.print("				<span><button onclick=\"javascript:document.getElementById('wDbgZone').value=''\">Clear</button></span>");
			out.print("			</td>");			
			out.print("			<td align='center'>");
			out.print("				<span><button onclick=\"javascript:document.getElementById('wDbgConsole').style.visibility='hidden'\">Hide</button></span>");			
			out.print("			</td>");
			out.print("		</tr>");
			out.print("		</table>");
			out.print("</div>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;

	}

}
