<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ParamReviewForm" %>
<%@ page import="com.airfrance.squaleweb.util.graph.GraphMaker"%>

<%String imageDetails = WebMessages.getString(request, "image.review");
String evolution = WebMessages.getString(request, "tracker.mark.history");
%>

<bean:define id="applicationName" name="paramReviewForm"
	property="applicationName" type="String" />
<bean:define id="projectName" name="paramReviewForm"
	property="projectName" type="String" />
<bean:define id="projectId" name="paramReviewForm" property="projectId"
	type="String" />
<bean:define id="currentAuditId" name="paramReviewForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="paramReviewForm"
	property="previousAuditId" type="String" />
<bean:define id="componentName" name="paramReviewForm"
	property="componentName" type="String" />
<bean:define id="componentType" name="paramReviewForm"
	property="componentType" type="String" />
<bean:define id="tre" name="paramReviewForm" property="tre"
	type="String" />
<bean:define id="oldAction"
	value='<%=java.net.URLDecoder.decode(request.getParameter("oldAction"), "UTF-8")%>'
	type="String" />

<af:page titleKey="review.title" subTitleKey="review.subtitle"
	subTitleKeyArg0="<%=componentName%>"
	subTitleKeyArg1="<%=WebMessages.getString(request, componentType)%>">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/application_menu.jsp">

		<%-- une autre valeur que true indique qu'on est passé par une autre vue que celle composant directement --%>
		<squale:tracker
			directWay="<%=(String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL)%>"
			componentName="<%=evolution%>" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>" />
	
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="ConsultationExpert::historique" />
		</jsp:include>

		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="paramReviewForm" />
			<br />
			<%
// On récupère le graohe historique
GraphMaker histoGraph = (GraphMaker) ((ParamReviewForm) (request.getSession().getAttribute("paramReviewForm"))).getReviewGraph();
// On l'affiche seulement si il n'est pas nul
if (null == histoGraph) {
    pageContext.setAttribute("showGraph", "false");
} else {
    pageContext.setAttribute("showGraph", "true");
}%>

			<af:form action="review.do">
				<%-- Si il n'y a pas de résultats pour la période en cours on affiche un message --%>
				<logic:equal name="showGraph" value="false">
					<bean:message key="review.no_tre"
						arg0="<%=WebMessages.getString(request, tre)%>" />
					<br />
					<br />
				</logic:equal>
				<%-- Sinon on affiche le graphe --%>
				<logic:equal name="showGraph" value="true">
					<af:dropDownPanel titleKey="buttonTag.menu.aide">
						<bean:message key="review.graph.help" />
					</af:dropDownPanel>
					<br />
					<bean:define id="srcHistoChart" name="paramReviewForm"
						property="reviewGraph.srcName" type="String" />
					<bean:define id="mapName" name="paramReviewForm"
						property="reviewGraph.useMapName" type="String" />
					<%-- ligne necessaire --%>
					<%=histoGraph.getMapDescription()%>
					<html:img src="<%=srcHistoChart%>" usemap="<%=mapName%>" border="0"
						title="<%=imageDetails%>" />
				</logic:equal>
				<%-- on passe le paramètre concernant le projet et les audits en caché --%>
				<input name="projectId" value="<%=projectId%>" type="hidden">
				<input name="currentAuditId" value="<%=currentAuditId%>"
					type="hidden">
				<input name="previousAuditId" value="<%=previousAuditId%>"
					type="hidden">
				<input name="oldAction" value="<%=oldAction%>" type="hidden" />
				<af:field key="review.field.nbdays" property="nbDays" type="NUMBER"
					size="5" maxlength="4" />
				<br />
				<af:buttonBar>
					<af:button type="form" name="retour" toolTipKey="toolTip.retour"
						onclick="<%=\"location.href='\"+oldAction +\"'\"%>"
						singleSend="true" />
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="changeDays" singleSend="true" causesValidation="true" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>