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

			<bean:message key="homepage.details.all" />
			<br />
			<br />
			<h2><bean:message key="news.consult" /></h2>
			<br />
			<logic:present name="newsListForm" scope="session">
				<af:form action="news.do" scope="session" method="POST">
					<af:table name="newsListForm" property="newsList" scope="session">
						<af:cols id="cols" selectable="false">
							<bean:define id="lang" name="cols" property="message.language"
								type="String" />
							<bean:define id="key" name="cols" property="message.key" />
							<%String link = "news.do?action=listNews&lang=" + lang + "&which=current#" + key;%>
							<af:col property="message.title" key="news.col.title"
								href='<%=link%>' />
							<af:col property="beginningDate" key="news.col.beginDate"
								href='<%=link%>' type="DATE" dateFormatKey="date.format.simple" />
						</af:cols>
					</af:table>
				</af:form>
			</logic:present>
			<logic:notPresent name="newsListForm" scope="session">
				<bean:message key="news.none" />
				<br />
				<br />
			</logic:notPresent>
			<af:form action="index.do" scope="session" method="POST">
				<h2><bean:message key="homepage.audits_list" /></h2>

				<jsp:include page="homepage_common.jsp">
					<jsp:param name="h3_title" value="homepage.my_audits" />
					<jsp:param name="property" value="audits" />
				</jsp:include>

				<br />

				<jsp:include page="homepage_common.jsp">
					<jsp:param name="h3_title" value="homepage.public_audits" />
					<jsp:param name="property" value="publicAudits" />
				</jsp:include>

				<br />
				<p class="surtitre"><bean:message key="homepage.configuration" /></p>
				<h3><bean:message key="homepage.time_setup" /></h3>
				<br />
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<af:field key="homepage.all" name="splitAuditsListForm"
							property="allAudits" type="CHECKBOX" styleClassLabel="td1"
							styleClass="normal" accessKey="default" overridePageAccess="true" />
						<td><bean:message key="homepage.defaut"
							arg0="<%=String.valueOf(IndexAction.NUMBER_OF_DAYS_FOR_NEWS)%>" /></td>
					</tr>
				</table>
				<af:buttonBar>
					<af:button type="form" name="valider" accessKey="default"
						overridePageAccess="true" />
				</af:buttonBar>

			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>