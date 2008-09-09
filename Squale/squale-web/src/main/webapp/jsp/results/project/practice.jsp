<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm" %>
<%@ page import="com.airfrance.squaleweb.util.graph.GraphMaker"%>

<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />


<bean:define id="form" name="projectSummaryForm" property="results" />
<bean:define id="practiceName" name="form" property="name" type="String" />
<bean:define id="repartition" name="form" property="intRepartition"
	type="double[]" />
<bean:define id="treId" name="form" property="id" type="String" />
<bean:define id="infoForm" name="form" property="infoForm" />
<%
// On met le form dans la requête pour que l'inclusion
// de la page jsp puisse trouver le bean dans le scope request
request.setAttribute("practiceInformationForm", infoForm);
%>

<af:page titleKey="project.results.title">
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
				<jsp:param name="page" value="Consultation::Pratique" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="projectSummaryForm" displayComparable="true"/>
			<br />
			<br />
			<h2><bean:message key="project.results.practice.subtitle"
				arg0="<%=WebMessages.getString(request, practiceName)%>" /></h2>
			<br />
			<table class="tblh" style="width: 20%">
				<thead>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th><bean:message key="project.result.practice.value" /></th>
						<td><squale:mark mark="currentMark" name="form" /> <squale:trend
							name="form" current="currentMark" predecessor="predecessorMark" />
						<squale:picto name="form" property="currentMark" /></td>
					</tr>
				</tbody>
			</table>
			<br />
			<h3><bean:message key="project.results.practice.graphes.title" /></h3>
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.result.practice.intro" />
			</af:dropDownPanel>
			<br />
			<br />
			<table class="tblh" style="width: 50%">
				<THEAD>
					<tr>
						<th class="sort" colspan="6">&nbsp;</th>
					</tr>
				</THEAD>
				<TBODY>
					<tr>
						<th><bean:message key="project.result.practice.value" /></th>
						<logic:iterate id="repartitionId" name="repartition"
							indexId="counter">
							<!-- c'est pour l'affichage du nombre d'éléments des indexes 0,1,2,3 ou 4 -->
							<logic:greaterThan name="repartitionId" value="0">
								<logic:notEmpty name="form" property="parentId">
									<bean:define id="parentId" name="form" property="parentId"
										type="String" />
									<td>
									<div align="center"><A
										HREF='<%="mark.do?action=mark&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&tre=" + treId + "&factorParent=" + parentId + "&currentMark=" + counter%>'
										class="nobottom"> <squale:picto name="" property=""
										mark="<%=counter.toString()%>" /> </A></div>
									</td>
								</logic:notEmpty>
								<logic:empty name="form" property="parentId">
									<td>
									<div align="center"><A
										HREF='<%="mark.do?action=mark&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&tre=" + treId + "&currentMark=" + counter%>'
										class="nobottom"> <squale:picto name="" property=""
										mark="<%=counter.toString()%>" /> </A></div>
									</td>
								</logic:empty>
							</logic:greaterThan>
							<logic:equal name="repartitionId" value="0">
								<td>
								<div align="center"><squale:picto name="" property=""
									mark="<%=counter.toString()%>" /></div>
								</td>
							</logic:equal>
						</logic:iterate>
					</tr>
					<tr>
						<th><bean:message key="number_of_components" /></th>
						<logic:iterate id="repartitionId" name="repartition"
							indexId="counter" type="Double">
							<logic:greaterThan name="repartitionId" value="0">
								<logic:notEmpty name="form" property="parentId">
									<bean:define id="parentId" name="form" property="parentId"
										type="String" />
									<td>
									<div align="center"><A
										HREF='<%="mark.do?action=mark&projectId="
    + projectId
    + "&currentAuditId="
    + currentAuditId
    + "&previousAuditId="
    + previousAuditId
    + "&tre="
    + treId
    + "&factorParent="
    + parentId
    + "&currentMark="
    + counter.intValue()%>'
										class="nobottom"> <%=(int) (repartitionId.doubleValue())%> </A></div>
									</td>
								</logic:notEmpty>
								<logic:empty name="form" property="parentId">
									<td>
									<div align="center"><A
										HREF='<%="mark.do?action=mark&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId + "&tre=" + treId + "&currentMark=" + counter.intValue()%>'
										class="nobottom"> <%=(int) (repartitionId.doubleValue())%> </A></div>
									</td>
								</logic:empty>
							</logic:greaterThan>
							<logic:equal name="repartitionId" value="0">
								<td>
								<div align="center">0</div>
								</td>
							</logic:equal>
						</logic:iterate>
					</tr>
				</TBODY>
			</table>
			<br />
			<br />
			<br />
			<bean:define id="imgBar" name="projectSummaryForm"
				property="barGraph.srcName" type="String" />
			<bean:define id="mapBar" name="projectSummaryForm"
				property="barGraph.useMapName" type="String" />
			<%-- ligne necessaire --%>
			<%=((GraphMaker) ((ProjectSummaryForm) (request.getSession().getAttribute("projectSummaryForm"))).getBarGraph()).getMapDescription()%>
			<img src="<%=imgBar%>" usemap="<%=mapBar%>" border="0" />
			<br />
			<br />
			<br />
			<!-- dans le cas d'une répartition par pas de 0,1 on affiche également l'histogramme -->
			<logic:notEmpty name="projectSummaryForm" property="histoBarGraph">
				<bean:define id="img" name="projectSummaryForm"
					property="histoBarGraph.srcName" type="String" />
				<bean:define id="map" name="projectSummaryForm"
					property="histoBarGraph.useMapName" type="String" />
				<%-- ligne necessaire --%>
				<%=((GraphMaker) ((ProjectSummaryForm) (request.getSession().getAttribute("projectSummaryForm"))).getHistoBarGraph()).getMapDescription()%>
				<img src="<%=img%>" usemap="<%=map%>" border="0" />
			</logic:notEmpty>
			<br />

			<%-- On affiche la description de la pratique --%>
			<fieldset><legend><b><bean:message key="qualimetric_element.title" /></b></legend>
			<br />

			<jsp:include page="/jsp/results/project/information_common.jsp">
				<jsp:param name="expandedDescription" value="false" />
			</jsp:include> <br />
			</fieldset>

		</af:canvasCenter>
	</af:body>
</af:page>