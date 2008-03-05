<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>
<%@ page import="com.airfrance.squaleweb.util.graph.GraphMaker"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm"%>

<%
            // Le toolTip pour le pieChart
            String imageDetails = WebMessages.getString( request, "image.piechart" );
%>

<bean:define id="applicationName" name="resultListForm"
	property="applicationName" type="String" />
<bean:define id="currentAuditId" name="resultListForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="resultListForm"
	property="previousAuditId" type="String" />


<af:page titleKey="application.results.title"
	titleKeyArg0="<%=applicationName%>">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/application_menu.jsp">

		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="Consultation::Application" />
		</jsp:include>

		<af:canvasCenter>
			<br />
			<br />
			<squale:resultsHeader name="resultListForm" displayComparable="true" />
			<br />
			<h2><bean:message key="application.results.summary.subtitle" /></h2>
			<br />
			<af:tabbedPane name="applicationsummary">
				<%-- Piechart --%>
				<af:tab key="application.results.volumetry.tab" name="piechart"
					lazyLoading="false">
					<br />
					<bean:define id="srcPieChart" name="resultListForm"
						property="pieChart.srcName" type="String" />
					<bean:define id="imgMapPieChart" name="resultListForm"
						property="pieChart.useMapName" type="String" />
					<%-- ligne necessaire --%>
					<%=( (GraphMaker) ( (ResultListForm) ( request.getSession().getAttribute( "resultListForm" ) ) ).getPieChart() ).getMapDescription()%>
					<html:img src="<%=srcPieChart%>" usemap="<%=imgMapPieChart%>"
						border="0" />
					<br />
					<b><bean:message key="image.legend" /></b>
					<br />
					<bean:message key="application.results.piechart" />

				</af:tab>
				<%-- Factors --%>
				<af:tab key="application.results.factors.tab" name="factors"
					lazyLoading="false">
					<af:dropDownPanel titleKey="buttonTag.menu.aide">
						<bean:message key="application.results.factors" />
						<br />
						<br />
					</af:dropDownPanel>
					<logic:iterate id="gridResult" name="resultListForm"
						property="list">
						<%-- Si la grille n'a pas de nom, on affiche pas le projet car cela veut dire
							qu'il n'y a pas de résultats --%>
						<logic:notEqual name="gridResult" property="gridName" value="">
							<center>
							<h3><bean:message key="project_creation.field.quality_grid" />
							<bean:write name="gridResult" property="gridName" /></h3>
							</center>
							<af:table name="gridResult" property="results" scope="page"
								totalLabelPos="none" emptyKey="table.results.none"
								displayNavigation="false">
								<%
								String paramsLink = "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
								%>
								<%
								String projectLink = "project.do?action=select" + paramsLink;
								%>
								<%
								String factorLink = "project.do?action=factor" + paramsLink;
								%>
								<af:cols id="element">
									<af:col property="name" key="project.name" sortable="false"
										href="<%=projectLink%>" paramName="element"
										paramId="projectId" paramProperty="id" width="15%" />
									<logic:iterate id="factor" name="element" property="factors"
										indexId="index">
										<bean:define id="factorName" name="factor" property="name"
											type="String" />
										<bean:define id="factorId" name="factor" property="id" />
										<bean:define id="projectId" name="element" property="id" />
										<af:col property='<%="factors[" + index + "].currentMark"%>'
											key="<%=factorName%>">
											<%
											String link = factorLink + "&projectId=" + projectId.toString() + "&which=" + factorId.toString();
											%>
											<a href="<%=link%>" class="nobottom"> <squale:mark
												name="factor" mark="currentMark" /> <squale:trend
												name="factor" current="currentMark"
												predecessor="predecessorMark" /> <squale:picto
												name="factor" property="currentMark" /> </a>
										</af:col>
									</logic:iterate>
								</af:cols>
							</af:table>
						</logic:notEqual>
					</logic:iterate>
				</af:tab>
				<%-- Kiviat --%>
				<af:tab key="application.results.kiviat.tab" name="kiviat">
					<bean:define id="srcKiviat" name="resultListForm"
						property="kiviat.srcName" type="String" />
					<bean:define id="imgMapKiviat" name="resultListForm"
						property="kiviat.useMapName" type="String" />
					<%-- ligne necessaire --%>
					<bean:define id="description" name="resultListForm"
						property="kiviat.mapDescription" type="String" />
					<%=( (GraphMaker) ( (ResultListForm) ( request.getSession().getAttribute( "resultListForm" ) ) ).getKiviat() ).getMapDescription()%>
					<html:img src="<%=srcKiviat%>" usemap="<%=imgMapKiviat%>"
						border="0" />
					<br />
					<b><bean:message key="image.legend" /></b>
					<br />
					<bean:message key="application.results.kiviat" />
				</af:tab>
			</af:tabbedPane>
			<br />
			<bean:size name="resultListForm" property="list" id="resultsSize" />
			<%-- 
				On exporte pas les résultats si il n'y a pas de résultats même si les 
				graphiques sont présents car l'export PDF donnera une page blanche dans ce cas.
			--%>
			<logic:greaterThan name="resultsSize" value="0">
				<h2><bean:message key="exports.title" /></h2>
				<br />
				<af:buttonBar>
					<af:button type="form" name="export.pdf"
						onclick='<%="application.do?action=exportPDF&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'
						toolTipKey="toolTip.export.pdf.application.result" />
				</af:buttonBar>
			</logic:greaterThan>
		</af:canvasCenter>
	</af:body>
</af:page>