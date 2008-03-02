<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="testweb.TestServlet" %>

<% 
	TestServlet servlet = new TestServlet();
	servlet.doGet(request, response); 
%>

<af:page titleKey="homepage.title" subTitleKey="homepage.subtitle">
	<af:body>
		<af:canvasCenter>
			<html:text property="field">Test</html:text>
		</af:canvasCenter>
	</af:body>
</af:page>