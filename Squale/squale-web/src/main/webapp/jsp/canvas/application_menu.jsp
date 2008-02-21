<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.airfrance.squaleweb.comparator.ComponentComparator" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectListForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm" %>

<%--

 Suivant d'où on vient il peut y avoir 3 types de form différents 
 Attention l'ordre des form est important, le splitAuditsListForm est celui qui peut ne pas contenir les informations
 sur l'application ou le projet (ex:pas d'accueil) donc on le met en premier, si les autres sont présents alors c'est 
 que les informations et ce sont ces forms qui les contiennent

--%>

<%String formName = "";%>
<logic:present name="splitAuditsListForm">
	<%formName = "splitAuditsListForm";%>
</logic:present>
<logic:present name="resultListForm">
	<%formName = "resultListForm";%>
</logic:present>
<logic:present name="applicationErrorForm">
	<%formName = "applicationErrorForm";%>
</logic:present>



<bean:define id="children" name="<%=formName%>"
	property="numberOfChildren" type="String" />
<bean:define id="applicationId" name="<%=formName%>"
	property="applicationId" type="String" />
<bean:define id="previousAuditId" name="<%=formName%>"
	property="previousAuditId" type="String" />
<bean:define id="currentAuditId" name="<%=formName%>"
	property="currentAuditId" type="String" />

<%String paramsLink = "&currentAuditId=" + currentAuditId + "&previousAuditId=" + previousAuditId;%>

<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.application.summary" color="2"
			action='<%="/squale/application.do?action=summary&applicationId=" + applicationId + paramsLink%>' />
		<logic:notEqual name="<%=children.toString()%>" value="1"
			scope="session">
			<logic:present
				name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY%>"
				scope="session">
				<%List list = ((ProjectListForm) session.getAttribute(com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY)).getList();
Collections.sort(list, new ComponentComparator());
%>
				<af:menuItem key="menu.application.projects" color="2">
					<logic:iterate
						name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.PROJECTS_KEY%>"
						scope="session" id="element" type="ProjectForm" property="list">
						<af:menuItem key="<%=SqualeWebActionUtils.formatStringForMenuItem(element.getProjectName())%>" color="2" 
							action="<%=\"/squale/project.do?action=select&projectId=\"+element.getId() + paramsLink%>" />
					</logic:iterate>
				</af:menuItem>
			</logic:present>
		</logic:notEqual>

		<af:menuItem key="menu.application.audits" color="2">
			<af:menuItem key="menu.application.audits.terminated" color="2"
				action='<%="/squale/audits.do?action=list&kind=terminated&applicationId=" + applicationId + paramsLink%>' />
			<af:menuItem key="menu.application.audits.failed" color="2"
				action='<%="/squale/audits.do?action=list&kind=failed&applicationId=" + applicationId + paramsLink%>' />
			<af:menuItem key="menu.application.audits.partial" color="2"
				action='<%="/squale/audits.do?action=list&kind=partial&applicationId=" + applicationId + paramsLink%>' />
		</af:menuItem>
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true" />
</af:canvasLeft>
