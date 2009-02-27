<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils"%>

<script type="text/javascript"
	src="theme/charte_v03_001/js/format_page.js"></script>

<%
	String title = "tagged.all_applications.title";
	String help = "tagged.all_applications.help";
%>

<af:page titleKey="<%=title%>">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body>

		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page"
					value="ConsultationExpert::SyntheseApplisProjets" />
			</jsp:include>

			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="<%=help%>" />
			</af:dropDownPanel>
			<br />
			<bean:define id="nomTag" name="tagName" type="String" scope="request" />
			<af:form action="all_tagged_applications.do">
				<center>
				<h2><bean:message key="tagged.all_applications.tag" /><%= nomTag %>
				</h2>
				</center>
				<logic:iterate id="gridResult" name="resultListForm" property="list"
					indexId="index">
					<br />
					<af:table name="resultListForm"
						property='<%="list[" + index + "].results"%>' totalLabelPos="none"
						emptyKey="table.results.none" displayNavigation="false">
						<af:cols id="element">
							<bean:define id="projectId" name="element" property="id"
								type="String" />
							<bean:define id="currentAuditId" name="element"
								property="currentAuditId" type="String" />
							<af:col property="applicationName" key="application.name"
								width="15%" sortable="true"
								href="<%=\"application.do?action=summary&currentAuditId=\"+currentAuditId%>"
								paramName="element" paramId="applicationId"
								paramProperty="applicationId" />
							<af:col property="name" key="project.name" sortable="true"
								width="15%"
								href="<%=\"project.do?action=select&currentAuditId=\"+currentAuditId%>"
								paramName="element" paramId="projectId" paramProperty="id" />
							<logic:iterate id="factor" name="element" property="factors">
								<bean:define id="name" name="factor" property="name"
									type="String" />
								<bean:define id="factorId" name="factor" property="id"
									type="Long" />
								<af:col property="value" key="<%=name%>">
									<%String link = "project.do?action=factor&projectId=" + projectId + "&which=" + factorId.longValue() + "&currentAuditId=" + currentAuditId;%>
									<a href="<%=link%>" class="nobottom"> <squale:mark
										name="factor" mark="currentMark" /> &nbsp; <squale:trend
										name="factor" current="currentMark"
										predecessor="predecessorMark" /> &nbsp;&nbsp; <squale:picto
										name="factor" property="currentMark" /> </a>
								</af:col>
							</logic:iterate>
						</af:cols>
					</af:table>
				</logic:iterate>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>