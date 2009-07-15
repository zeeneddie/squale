<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.resources.WebMessages"%>

<bean:define id="projectId" name="topListForm" property="projectId"
	type="String" />
<bean:define id="projectName" name="topListForm" property="projectName"
	type="String" />
<bean:define id="currentAuditId" name="topListForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="topListForm"
	property="previousAuditId" type="String" />
<bean:define id="currentTre" name="topListForm" property="tre"
	type="String" />
<bean:define id="current_comptype" name="topListForm"
	property="componentType" type="String" />

<%
//On récupère la valeur du langage
String language = (String) request.getAttribute("language");
if ( language==null ) {
	language = (String) request.getParameter("language");
}
else {
	request.setAttribute("language",language);
}

String customizedCompType = new String();
if ( WebMessages.existString(current_comptype + "." + language ).booleanValue() )
{
	customizedCompType = current_comptype + "." + language;
}
else
{
	customizedCompType = current_comptype;
}
%>
<af:page titleKey="project.top.title" titleKeyArg0="<%=projectName%>">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<squale:tracker directWay="false" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />

		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="ConsultationExpert::top" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="topListForm" />
			<br />
			<h2><bean:message key="project.top.subtitle"
				arg0="<%=WebMessages.getString(request, customizedCompType)%>"
				arg1="<%=WebMessages.getString(request, currentTre)%>" /></h2>
			<br />

			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.top.details"
					arg0="<%=WebMessages.getString(request, currentTre)%>" />
			</af:dropDownPanel>
			<br />

			<af:form action="top.do">
				<af:table name="topListForm" property="componentListForm"
					pageLength="10" totalLabelPos="none" scope="session"
					emptyKey="table.results.none">
					<af:cols id="element">
						<af:col property="name" contentTruncate="80" key="component.name"
							href='<%="project_component.do?action=component&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&language=" + language%>'
							paramName="element" paramId="component" paramProperty="id"
							sortable="true" />
						<af:col property="metrics[0]" type="NUMBER" key="<%=currentTre%>"
							sortable="true" />
					</af:cols>
				</af:table>
			</af:form>
			<%-- 
				On affiche les boutons seulement si il y a des résultats à exporter.
				
				Il ne faut pas mettre les boutons dans le form
				sinon l'action se termine mal (null si l'on veut invoquer un nouvel export
				sans recharger la page
			--%>
			<logic:notEmpty name="topListForm" property="componentListForm">
				<h2><bean:message key="exports.title" /></h2>
				<br />
				<af:buttonBar>
					<%
						String urlExportPDF = "top.do?action=exportToPDF&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
						String urlExportExcel = "top.do?action=exportToExcel&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
					%>
					<af:button type="form" name="export.pdf"
						onclick="<%=\"javascript:xt_clic_AF_v2('T','ConsultationExpert::topPDF',null,null);location.href='\"+urlExportPDF+\"'\"%>"
						toolTipKey="toolTip.export.pdf.top" />
					<af:button type="form" name="export.excel"
						onclick="<%=\"javascript:xt_clic_AF_v2('T','ConsultationExpert::topExcel',null,null);location.href='\"+urlExportExcel+\"'\"%>"
						toolTipKey="toolTip.export.excel.top" />
				</af:buttonBar>
			</logic:notEmpty>
		</af:canvasCenter>
	</af:body>
</af:page>