<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="testweb.TestServlet" %>
<%@ page import="testweb.TestServlet" %>

<% 
	TestServlet servlet = new TestServlet();
	servlet.doGet(request, response); 
%>

<html:text property="field">Test</html:text>