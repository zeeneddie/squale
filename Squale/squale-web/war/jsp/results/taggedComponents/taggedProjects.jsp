<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.util.SqualeWebActionUtils"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm"%>

<script type="text/javascript"
	src="theme/charte_v03_001/js/format_page.js"></script>

<%
            String title = "tagged.all_projects.title";
            String help = "tagged.all_projects.help";
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
			<af:form action="all_tagged_projects.do">
				<center>
				<h2><bean:message key="tagged.all_projects.tag" /><%=nomTag%>
				</h2>
				</center>
				<af:table name="projectListForm" property="list"
					totalLabelPos="none" emptyKey="table.results.none"
					displayNavigation="false">
					<af:cols id="element">
						<bean:define id="projectId" name="element" property="projectId" />
						<bean:define id="currentAuditId" name="element"
							property="currentAuditId" />
						<bean:define id="previousAuditId" name="element"
							property="previousAuditId" />
						<%
						String paramsLink = "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
						%>
						<%
						String projectLink = "project.do?action=select" + paramsLink + "&projectId=" + projectId;
						%>
						<%
						String factorLink = "project.do?action=factor" + paramsLink;
						%>
						<af:col key="project.name" property="projectName"
							href="<%=projectLink%>" paramName="element" paramId="projectId"
							paramProperty="id" width="15%">
						</af:col>
						<logic:iterate id="factor" name="element"
							property="factors.factors" indexId="index">
							<bean:define id="factorName" name="factor" property="name"
								type="String" />
							<bean:define id="factorId" name="factor" property="id" />
							<af:col
								property='<%="factors.factors[" + index + "].currentMark"%>'
								key="<%=factorName%>" contentClass="weatherInfo">
								<%
								String link = factorLink + "&projectId=" + projectId.toString() + "&which=" + factorId.toString();
								%>
								<a href="<%=link%>" class="nobottom"> 
									<squale:mark name="factor" mark="currentMark" /> 
									<squale:picto name="factor" property="currentMark" /> 
									&nbsp; 
									<squale:trend name="factor" current="currentMark" predecessor="predecessorMark" /> 
								</a>
							</af:col>
						</logic:iterate>
					</af:cols>
				</af:table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>