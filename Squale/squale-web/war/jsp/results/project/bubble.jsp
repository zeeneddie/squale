<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.util.SqualeWebConstants" %>
<%@ page import="org.squale.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="org.squale.squaleweb.resources.WebMessages" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.component.AuditForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.component.AuditListForm" %>
<%@ page import="java.util.List" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.results.ComponentForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.results.ProjectFactorForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.results.ResultForm" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.squale.squaleweb.util.graph.GraphMaker"%>

<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />

<script type="text/javascript"
	src="theme/charte_v03_001/js/tagManagement.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>

<af:page titleKey="project.results.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">	
	
		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="ConsultationExpert::ScatterPlot" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="projectSummaryForm" />
			<br />
			<h2><bean:message key="project.results.summary.subtitle" /></h2>
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.results.scatterplot.details" />
			</af:dropDownPanel>
			<br />
			<bean:define id="srcBubble"
				name="<%=SqualeWebConstants.BUBBLE_GRAPH_MAKER%>" property="srcName"
				type="String" />
			<bean:define id="imgMapBubble"
				name="<%=SqualeWebConstants.BUBBLE_GRAPH_MAKER%>"
				property="useMapName" type="String" />
			<!-- ligne necessaire -->
			<%=((GraphMaker) request.getAttribute(SqualeWebConstants.BUBBLE_GRAPH_MAKER)).getMapDescription()%>
			<html:img src="<%=srcBubble%>" usemap="<%=imgMapBubble%>" border="0" />
			<af:buttonBar>
				<af:button type="form" name="project.results.scatterplot.return" toolTipKey="toolTip.project.results.scatterplot.return"
					onclick='<%="project.do?action=select&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>' />
			</af:buttonBar>
		</af:canvasCenter>
	</af:body>
</af:page>