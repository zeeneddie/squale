
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ page import="com.airfrance.squaleweb.comparator.ComponentComparator" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectListForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm" %>

<%-- 
	On peut venir de plusieurs actions dont les formulaires sont soit de type projectSummaryForm ou errorListForm
	On affecte donc le bon formulaire pour récupérer les informations
--%>
<logic:notEmpty name="projectSummaryForm">
	<bean:define id="formForThisPage" name="projectSummaryForm" />
</logic:notEmpty>
<logic:empty name="projectSummaryForm">
	<bean:define id="formForThisPage" name="errorListForm" />
</logic:empty>
<bean:define id="applicationId" name="formForThisPage" property="applicationId" type="String" />
<bean:define id="projectId" name="formForThisPage" property="projectId" type="String" />
<bean:define id="children" name="formForThisPage" property="numberOfChildren" type="String" />
<bean:define id="currentAuditId" name="formForThisPage" property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="formForThisPage" property="previousAuditId" />
<bean:define id="comparisonAvailable" name="formForThisPage" property="comparableAudits" />
	
<% String paramsLink = "&currentAuditId=" + currentAuditId +"&previousAuditId=" + previousAuditId ; %>	
	
<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.project.summary" color="2"
			action='<%="project.do?action=summary&projectId="+projectId+paramsLink%>' />
		<af:menuItem key="menu.project.components" color="2">
			<af:menuItem key="menu.project.components.all" action='<%="project_component.do?action=root&which=components.all&projectId="+projectId+paramsLink%>' color="2"/>
			<af:menuItem key="menu.project.components.excluded" action='<%="project_component.do?action=root&which=components.excluded&projectId="+projectId+paramsLink%>' color="2"/>
		</af:menuItem>
		<af:menuItem key="menu.application.audits" color="2">
			<af:menuItem key="menu.application.audits.terminated" color="2"
				action='<%="audits.do?action=list&kind=terminated&applicationId="+applicationId+paramsLink%>' />
			<af:menuItem key="menu.application.audits.failed" color="2"
				action='<%="audits.do?action=list&kind=failed&applicationId="+applicationId+paramsLink%>' />
			<af:menuItem key="menu.application.audits.partial" color="2"
				action='<%="audits.do?action=list&kind=partial&applicationId="+applicationId+paramsLink%>' />
		</af:menuItem>
		
		<af:menuItem key="menu.histo" color="2" />

		<af:menuItem key="menu.project.top" color="2">
			<logic:iterate name="<%=SqualeWebConstants.TOP_KEY%>" scope="session" id="kind" >
			   <bean:define id="kindKey" name="kind" property="key" type="String"/>
		       <af:menuItem key="<%=kindKey%>" color="2">
		       		<logic:iterate id="kindValue" name="kind" property="value" type="String">
						<af:menuItem key="<%=kindValue%>" color="2" action="<%=\"top.do?action=display&componenttype=\"+kindKey+\"&tre=\"+kindValue+\"&projectId=\"+projectId+paramsLink%>"/>
		       		</logic:iterate>
		       </af:menuItem>
			</logic:iterate>
			<af:menuItem key="practices.meteo" action="<%=\"meteo.do?action=meteo&projectId=\"+projectId+paramsLink%>" color="2" />
			<af:menuItem key="bubbleGraph"  color="2" action="<%=\"top.do?action=displayBubble&projectId=\"+projectId+paramsLink%>"/> 
		</af:menuItem>
		
		
		<logic:notEqual name="<%=children.toString()%>" value="1" scope="session">
		<logic:present name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY%>" scope="session">
			<%
List list = ((ProjectListForm)session.getAttribute(com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY)).getList();
Collections.sort(list, new ComponentComparator());%>
			<af:menuItem key="menu.application.projects" color="2">
				<logic:iterate name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY%>" scope="session" id="element"
					type="ProjectForm" property="list">
					<af:menuItem key="<%=SqualeWebActionUtils.formatStringForMenuItem(element.getProjectName())%>" color="2"
						action="<%=\"project.do?action=select&projectId=\"+element.getId()+paramsLink%>" />
				</logic:iterate>
			</af:menuItem>
		</logic:present>
		</logic:notEqual>
		<%-- Entry for the manual mark page --%>
		<af:menuItem key="menu.administration.manualMark" color="2" action="<%=\"manual_mark_management.do?action=list&projectId=\"+projectId%>" />
		
		<%-- 
			On propose la comparaison des audits seulement si elle est disponible
			c'est-à-dire si l'audit a été fait avec la même grille qualité
		--%>
		<logic:equal name="comparisonAvailable" value="true">
			<af:menuItem key="menu.project.evolution" color="2"
						action="<%=\"evolution.do?action=list&projectId=\"+projectId+paramsLink%>"/>
		</logic:equal>
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true"/>
</af:canvasLeft>
