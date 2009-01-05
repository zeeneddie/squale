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
package testweb;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionServlet;

import test.TestCommon;
/**
 * @version 	1.0
 * @author
 */
public class TestServlet extends ActionServlet implements Servlet {

    /**
    * @see org.apache.struts.action.ActionServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        super.doGet(req, resp);

    }

    /**
    * @see org.apache.struts.action.ActionServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        super.doPost(req, resp);

    }

}
