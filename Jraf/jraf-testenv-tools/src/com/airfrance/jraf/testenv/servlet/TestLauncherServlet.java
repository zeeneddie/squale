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
package com.airfrance.jraf.testenv.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.testenv.integrationlayer.ITestLauncher;

/**
 * <p>Title : TestLauncherServlet.java</p>
 * <p>Description : Servlet générique permettant de lancer des tests 
 * au fil de l'eau dans un contexte Web/JRAF.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class TestLauncherServlet extends HttpServlet implements Servlet {

	/** logger */
	private final static Log log = LogFactory.getLog(TestLauncherServlet.class);

	/** constante definissant la classe de test */
	public final static String TEST_CLASS = "testClass";

	public void execute(ITestLauncher in_testLaucher, PrintWriter out)
		throws JrafEnterpriseException {

		try {
			//execution des tests
			log.info("Debut de l'execution des tests...");	
			out.println("<p>Debut de l'execution des tests...</p>");

			// execution des tests
			in_testLaucher.execute();

			log.info("Execution effectuee.");
			out.println("<br><p>Execution effectuee.</p>");

		} catch (JrafEnterpriseException e) {
			log.fatal(e);
			e.printStackTrace(out);
			throw e;
		}

	}
	public void pageButton(PrintWriter out) {
		// bouton pour rejouer les tests
		out.print("<form action=\"TestLauncherServlet\" method=GET>");
		out.println("<input type='submit' value='Rejouer les tests'/></form>");
		out.print("</form>");

	}

	public void pageInit(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Execution des tests</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
	}

	public void pageClose(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

	private void pagePrintException(PrintWriter out, Exception e) {
		out.println("Probleme lors de l'execution des tests.");
		out.println("Exception lancee : ");
		out.println(e);
		out.println("<br/>");
		out.println("<p>" + e.getMessage() + "</p>");
		out.println("<br/>");
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest in_req, HttpServletResponse in_resp)
		throws ServletException, IOException {

		Class lc_testClassObject = null;
		ITestLauncher lc_testLaucher = null;

		// recuperation du nom de la classe testLauncher			
		String lc_testClass = getInitParameter(TEST_CLASS);

		in_resp.setContentType("text/html");
		PrintWriter out = in_resp.getWriter();

		// si le nom n'est pas null ou vide
		if (lc_testClass != null && !lc_testClass.equals("")) {

			try {
				// recuperation de l'objet classe
				lc_testClassObject = Class.forName(lc_testClass);

				// instanciation de la classe
				lc_testLaucher =
					(ITestLauncher) lc_testClassObject.newInstance();

				// bouton pour rejouer les tests
				pageButton(out);

				// initialisation de la page html
				pageInit(out);

				//execution des tests	
				execute(lc_testLaucher, out);

			} catch (Exception e) {
				log.fatal(e);
				e.printStackTrace();
				pagePrintException(out, e);
			}

			// fermeture de la page html
			pageClose(out);

		}

	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest in_req, HttpServletResponse in_resp)
		throws ServletException, IOException {

		// redirige sur doGet
		doGet(in_req, in_resp);

	}

}
