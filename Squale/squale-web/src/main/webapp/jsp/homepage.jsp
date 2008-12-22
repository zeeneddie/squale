<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.action.IndexAction" %>

<af:page titleKey="homepage.title" accessKey="reader">
	<af:head>
		<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body>
		<af:canvasCenter>
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="Consultation::Accueil" />
			</jsp:include>
		<%-- element --%>
			<div>
				<logic:match name="homepageForm" property="default" value="true">
					<bean:message key="homepage.defaultConfig" />
					<a href="homepage_management.do?action=state">
						<bean:message key="homepage.defaultConfigPage"/>
					</a>
					<br/>
				</logic:match>
				<br>
				<logic:iterate id="compo" name="homepageForm" property="elementToDisplay">
					<logic:present name="compo">
						<jsp:include page="<%= compo.toString() %>"></jsp:include>
					</logic:present>
				</logic:iterate>
			</div>
		</af:canvasCenter>
	</af:body>
</af:page>