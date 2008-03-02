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
