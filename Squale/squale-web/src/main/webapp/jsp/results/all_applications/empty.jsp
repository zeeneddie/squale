<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<% String title = "all_applications.empty.title"; %>
<logic:present name="isPublic">
	<% title = "all_public_applications.empty.title"; %>
</logic:present>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="<%=title%>">
			<br />
			<div align="center">
			<br /><br />
			<br /><br />
			<af:buttonBar>
				<af:button name="homepage" onclick="utilLink.do?action=homepage"/>
			</af:buttonBar></div>
		</af:canvasCenter>
	</af:body>
</af:page>

