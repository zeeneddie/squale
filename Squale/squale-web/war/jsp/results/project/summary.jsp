<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.welcom.struts.util.WConstants"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.LogonBean"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebConstants"%>
<%@ page import="org.squale.squaleweb.resources.WebMessages"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.results.ComponentForm"%>
<%@ page
	import="org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm"%>
<%@ page import="org.squale.squaleweb.util.graph.GraphMaker"%>
<%@ page import="org.squale.squaleweb.tagslib.HistoryTag"%>

<%
    // Récupération de l'utilisateur en session pour savoir si celui-ci est administrateur
    // SQUALE
    LogonBean sessionUser = (LogonBean) request.getSession().getAttribute( WConstants.USER_KEY );
    boolean isAdmin = sessionUser.isAdmin();
%>

<%
    // Le chemin du traceur
    String directComponentWay = (String) session.getAttribute( SqualeWebConstants.TRACKER_BOOL );
    // Le composant en session
    ComponentForm component = (ComponentForm) session.getAttribute( "componentForm" );
    // Lien vers les erreurs du projet
    String errorLink = WebMessages.getString( request, "errors.consult" );
    // Parameter indicates which tab must be selected
    String selectedTab = request.getParameter( HistoryTag.SELECTED_TAB_KEY );
    if ( selectedTab == null )
    {
        // First tab by default
        selectedTab = "factors";
    }
%>

<script type="text/javascript" src="jslib/information.js"></script>
<script type="text/javascript"
	src="theme/charte_v03_001/js/tagManagement.js"></script>
<script type="text/javascript" src="jslib/jquery.js"></script>
<script type="text/javascript" src="jslib/jquery-ui.js"></script>
<script type="text/javascript" src="theme/charte_v03_001/js/exporter.js"></script>

<bean:define id="applicationId" name="projectSummaryForm"
	property="applicationId" type="String" />
<bean:define id="projectId" name="projectSummaryForm"
	property="projectId" type="String" />
<bean:define id="currentAuditId" name="projectSummaryForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="projectSummaryForm"
	property="previousAuditId" type="String" />
<bean:define id="auditSqualeVersion" name="projectSummaryForm"
	property="auditSqualeVersion" type="String" />
<bean:define id="comparable" name="projectSummaryForm"
	property="comparableAudits" type="Boolean" />
<bean:define id="callbackUrlApp">
	<html:rewrite
		page="/add_applicationTag.do?action=findTagForAutocomplete" />
</bean:define>
<bean:define id="callbackUrlProj">
	<html:rewrite page="/add_projectTag.do?action=findTagForAutocomplete" />
</bean:define>

<af:page titleKey="project.results.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp" onload="lineStyle();">
		
		<%-- une autre valeur que "true" indique qu'on est passé par une autre vue 
			que celle composant directement --%>
		<%--<bean:define id="compoName" type="String" property="name"
			value="<%=component%> " />
		<squale:tracker
			directWay="<%=(String) session.getAttribute(SqualeWebConstants.TRACKER_BOOL)%>"
			componentName="<%=compoName%>" projectId="<%=projectId%>"
			currentAuditId="<%=currentAuditId%>"
			previousAuditId="<%=previousAuditId%>"/>--%>
		<af:canvasCenter>
			<%-- inclusion pour le marquage XITI spécifique à la page--%>
			<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
				<jsp:param name="page" value="Consultation::Projet" />
			</jsp:include>

			<br />
			<squale:resultsHeader name="projectSummaryForm"
				displayComparable="true">
				<%-- FINISH THIS UP ! https://project.squale.org/ticket/140
				<logic:present name="unexistingTag" scope="request">
					<div id="unexistingTagBox" title="Tag does not exist">
						<bean:write name="unexistingTag"/>
					</div>
					<script>
						showErrorModalBox("unexistingTagBox");
					</script>
				</logic:present>--%>
				
			</squale:resultsHeader>
			<br />
			<div style="color: #f00"><html:errors property="exportIDE" />
			<br />
			<br />
			</div>
			<logic:equal name="projectSummaryForm" property="haveErrors"
				scope="session" value="true">
				<img src="images/pictos/warning.png" alt="warning_image" />
				<a
					href='<%="project_errors.do?action=errors&projectId=" + projectId
                                + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'>
				<B><U><%=errorLink%></U></B> </a>
				<br />
				<br />
			</logic:equal>
			<h2><bean:message key="project.results.summary.subtitle" /></h2>
			<br />
			<af:tabbedPane name="projectsummary">
				<af:tab key="project.results.factors.tab" name="factors" lazyLoading="false" isTabSelected='<%=""+selectedTab.equals("factors")%>'>
					<af:dropDownPanel titleKey="buttonTag.menu.aide">
						<bean:message key="project.results.factors.details" />
					</af:dropDownPanel>
					<br/>
					<af:form action="project.do?action=summary&selected=factors">
						<table class = "tblh">	
						<thead>
							<tr>
								<th rowspan="2" width="25%"><bean:message key="project.result.factor.name" /></th>
								<th rowspan="2" width="25%"><bean:message key="project.result.factor.value" /></th>
								<th rowspan="2" width="25%"><bean:message key="project.result.factor.tendance" /></th>
								<logic:notEmpty name="userSqualeSessionContext" property="importReferenceVersion" scope="session">
									<th colspan="4" width="25%" style="border-width:0px"><bean:message key="project.result.factor.reference" /></th>
								</logic:notEmpty>
							</tr>
							<logic:notEmpty name="userSqualeSessionContext" property="importReferenceVersion" scope="session">
							<tr>
								<th ><bean:message key="project.result.factor.reference.mean"/></th>
								<th ><bean:message key="project.result.factor.reference.max"/></th>
								<th ><bean:message key="project.result.factor.reference.min"/></th>
								<th ><bean:message key="project.result.factor.reference.deviation"/></th>
							</tr>
							</logic:notEmpty>
						</thead>
						<tbody>
							<logic:iterate id="factor" indexId="index" name="projectSummaryForm" property="factors.factors" scope="session" >
								<tr class="" id="li-<%=index%>">
									<td class="height_col">								
										<html:link
										href='<%="project.do?action=factor&projectId=" + projectId + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId%>'
										paramId="which" paramProperty="id" paramName="factor">
											<bean:message name="factor" property="name" />
										</html:link>
									</td>
									<td class="weatherInfo" style="font-weight: bold;" >
										<squale:mark name="factor" mark="currentMark"/>
										<squale:picto name="factor" property="currentMark"/>
									</td>
									<td>
										<squale:trend name="factor" current="currentMark" predecessor="predecessorMark" />
									</td>
									<logic:notEmpty name="userSqualeSessionContext" property="importReferenceVersion" scope="session">
										<logic:notEmpty name="factor" property="statForm" >
											<bean:define id="stat" name="factor" property="statForm"/>
											<td width="50px">	
												<af:write name="stat" property="referenceMean"/>
											</td>
											<td width="50px">		
												<af:write name="stat" property="referenceMax"/>
											</td>
											<td width="50px">		
												<af:write name="stat" property="referenceMin"/>
											</td>
											<td width="50px">		
												<af:write name="stat" property="referenceDeviation"/>
											</td>
										</logic:notEmpty>
										<logic:empty name="factor" property="statForm">
											<td colspan="4"></td>
										</logic:empty>
									</logic:notEmpty>
								</tr>
							</logic:iterate>
						</tbody>
					</table>
					</af:form>
				</af:tab>
				<%-- Kiviat --%>
				<af:tab key="project.results.kiviat.tab" name="kiviat"
					lazyLoading="false"
					isTabSelected='<%=""+selectedTab.equals("kiviat")%>'>
					<%
					    String imageDetails1 = WebMessages.getString( request, "image.project.factors" );
					%>
					<bean:define id="srcKiviat" name="projectSummaryForm"
						property="kiviat.srcName" type="String" />
					<bean:define id="imgMapKiviat" name="projectSummaryForm"
						property="kiviat.useMapName" type="String" />
					<%-- ligne necessaire --%>
					<%=( (GraphMaker) ( (ProjectSummaryForm) ( request.getSession().getAttribute( "projectSummaryForm" ) ) ).getKiviat() ).getMapDescription()%>
					<html:img src="<%=srcKiviat%>" usemap="<%=imgMapKiviat%>"
						border="0" />
					<br />
					<b><bean:message key="image.legend" /></b>
					<br />
					<bean:message key="project.results.kiviat.details" />
					<br />
					<af:form action="project.do?action=select" scope="session"
						method="POST">
						<%-- on passe le paramètre projectId en caché --%>
						<input name="projectId" value="<%=projectId%>" type="hidden">
						<input name="currentAuditId" value="<%=currentAuditId%>"
							type="hidden">
						<input name="previousAuditId" value="<%=previousAuditId%>"
							type="hidden">
						<logic:equal name="projectSummaryForm"
							property="displayCheckBoxFactors" scope="session" value="true">
							<table class="formulaire" cellpadding="0" cellspacing="0"
								border="0" width="100px">
								<tr>
									<%-- nom du tab à afficher (kiviat) après soumission de la requête --%>
									<input type="hidden" name="<%=HistoryTag.SELECTED_TAB_KEY%>"
										value="kiviat" />
									<af:field key="project.results.allFactors"
										name="projectSummaryForm" property="allFactors"
										type="CHECKBOX" styleClassLabel="td1" styleClass="normal" />
								</tr>
							</table>
							<af:buttonBar>
								<af:button type="form" name="valider" />
							</af:buttonBar>
						</logic:equal>
					</af:form>
				</af:tab>
				<%-- Volumétrie --%>
				<af:tab key="project.results.volumetry.tab" name="volumetry"
					lazyLoading="false"
					isTabSelected='<%=""+selectedTab.equals("volumetry")%>'>
					<af:dropDownPanel titleKey="buttonTag.menu.aide">
						<bean:message key="project.results.volumetry.details" />
					</af:dropDownPanel>
					<af:table name="projectSummaryForm" property="volumetry.list"
						scope="session" emptyKey="table.results.none">
						<af:cols id="element">
							<af:col property="name" key="measure.name">
								<bean:message name="element" property="name" />
							</af:col>
							<af:col property="currentMark" key="measure.value" />
							<%-- Racourci vers l'historique de la volumétrie --%>
							<af:col property="name" width="30px">
								<bean:define name="element" property="name" id="name"
									type="String" />
								<squale:history projectId="<%=projectId%>"
									auditId="<%=currentAuditId%>" ruleId="<%=name%>" kind="metric"
									previousAuditId="<%=previousAuditId%>"
									toolTip="tooltips.history.metric" selectedTab="volumetry" />
							</af:col>
						</af:cols>
					</af:table>
				</af:tab>
			</af:tabbedPane>
			<h2><bean:message key="exports.title" /></h2>
			<br />
			
			<%-- The export tool bar --%>
			<%
			    String urlExportPDF = "project.do?action=exportPDF&projectId=" + projectId + "&currentAuditId=" 
			                    + currentAuditId + "&previousAuditId=" + previousAuditId;
				String urlExportActionPlan = "project.do?action=exportPDFActionPlan&projectId=" + projectId
				                + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
			    String urlExportDetailedActionPlan = "project.do?action=exportPDFDetailedActionPlan&projectId=" + projectId
			                    + "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;
			    String urlExportPpt = "param_audit_report.do?action=param&applicationId=" + applicationId + "&currentAuditId=" 
			                    + currentAuditId + "&previousAuditId=" + previousAuditId + "&comparable=" + comparable.toString();
			 	String remediationRisk = "project.do?action=exportRemediationByRisk&projectId=" + projectId 
			 	                + "&currentAuditId="+ currentAuditId;
			 	String urlExportIde = "project.do?action=exportIDE&projectId=" + projectId + "&currentAuditId=" 
			 	                + currentAuditId + "&previousAuditId=" + previousAuditId;
			%>
			<div class="moduleexporttoolbar" >
				<div class="moduleexporttoolbarbackground" >
				</div>
				<ul id="cssdropdown">
					<li class="exportheadlink" >
						<img src="theme/charte_v03_001/img/ico/doctype/pdf.gif" >
						<a href="<%= urlExportPDF %>" >
							<bean:message key="export.button.pdf.project" />  
						</a>
					</li>
					<li class="exportheadlink" style="width: 200px;">
						<img src="theme/charte_v03_001/img/ico/blanc/multi.png" >
						<a>
							<bean:message key="export.button.pdf.plan" />
						</a>
					    <ul>
							<li>
								<img src="theme/charte_v03_001/img/ico/doctype/pdf.gif" >
								<a href="<%= urlExportActionPlan %>" >
									<bean:message key="export.button.pdf.plan.by_practice"/>
								</a>
							</li>
						  	<li>
						  		<img src="theme/charte_v03_001/img/ico/doctype/pdf.gif" >
						  		<a href="<%= urlExportDetailedActionPlan %>" >
						  			<bean:message key="export.button.pdf.plan.by_practice.detailed"/>
						  		</a>
						  	</li>
						  	<li>
						  		<img src="theme/charte_v03_001/img/ico/doctype/pdf.gif" >
						  		<a href="<%=remediationRisk %>" >
						  			<bean:message key="export.button.pdf.plan.by_component"/>
						  		</a>
						  	</li>
						</ul>
					</li>
					<%
			    		if ( isAdmin )
			            {
					%>
						<li class="exportheadlink">
							<img src="theme/charte_v03_001/img/ico/doctype/ppt.gif" >
							<a href="<%= urlExportPpt %>" >
								<bean:message key="export.button.ppt.audit_report"/>
							</a>
						</li>
					<%
			    		} //isAdmin
					%>
					<logic:greaterEqual name="auditSqualeVersion" value="3.2">
						<logic:equal name="projectSummaryForm" property="exportIDE"	value="true">
				  			<li class="exportheadlink">
				  				<img src="theme/charte_v03_001/img/ico/doctype/fichier.gif" >
				  				<a href="<%= urlExportIde %>" >
				  					<bean:message key="export.button.xml.ide" />
				  				</a>
							</li>
						</logic:equal>
					</logic:greaterEqual>
				</ul>
			</div>
			<%-- END of the export tool bar --%>
		</af:canvasCenter>
	</af:body>
	
</af:page>