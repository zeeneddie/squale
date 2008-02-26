<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<%
	String title = "all_applications.title";
	String help = "all_applications.results.factors";
%>
<logic:present name="isPublic">
	<%
		title = "all_public_applications.title";
		help = "all_public_applications.results.factors";
	%>
</logic:present>


<af:page titleKey="<%=title%>">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body>	
	
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="ConsultationExpert::SyntheseApplisProjets" />
		</jsp:include>
		
		<af:canvasCenter>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="<%=help%>" />
			</af:dropDownPanel>
			<br />
			<af:form action="all_applications.do">
				<logic:iterate id="gridResult" name="resultListForm" property="list"
					indexId="index">
					<br />
					<logic:notEqual name="gridResult" property="gridName" value="">
						<center>
						<h2><bean:message
							key="project_creation.field.quality_grid" /> <bean:write
							name="gridResult" property="gridName" /> <af:field key="empty"
							name="gridResult" property="gridUpdateDate"
							dateFormatKey="date.format.simple" type="DATE" disabled="true" />
						</h2>
						</center>
						<af:table name="resultListForm"
							property='<%="list[" + index + "].results"%>'
							totalLabelPos="none" emptyKey="table.results.none"
							displayNavigation="false">
							<af:cols id="element">
								<bean:define id="projectId" name="element" property="id"
									type="String" />
								<bean:define id="currentAuditId" name="element"
									property="currentAuditId" type="String" />
								<af:col property="applicationName" key="application.name"
									width="15%" sortable="true"
									href="<%=\"/squale/application.do?action=summary&currentAuditId=\"+currentAuditId%>"
									paramName="element" paramId="applicationId"
									paramProperty="applicationId" />
								<af:col property="name" key="project.name" sortable="true"
									width="15%"
									href="<%=\"/squale/project.do?action=select&currentAuditId=\"+currentAuditId%>"
									paramName="element" paramId="projectId" paramProperty="id" />
								<logic:iterate id="factor" name="element" property="factors">
									<bean:define id="name" name="factor" property="name"
										type="String" />
									<bean:define id="factorId" name="factor" property="id"
										type="Long" />
									<af:col property="value" key="<%=name%>">
										<%String link = "/squale/project.do?action=factor&projectId=" + projectId + "&which=" + factorId.longValue() + "&currentAuditId=" + currentAuditId;%>
										<a href="<%=link%>" class="nobottom"> <squale:mark
											name="factor" mark="currentMark" /> &nbsp; <squale:trend
											name="factor" current="currentMark"
											predecessor="predecessorMark" /> &nbsp;&nbsp; <squale:picto
											name="factor" property="currentMark" /> </a>
									</af:col>
								</logic:iterate>
							</af:cols>
						</af:table>
					</logic:notEqual>
				</logic:iterate>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>