
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants"%>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils"%>
<%@ page
	import="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm"%>

<script type="text/javascript" src="jslib/information.js"></script>

<bean:define id="projectId" name="resultListForm" property="projectId"
	type="String" />
<bean:define id="currentAuditId" name="resultListForm"
	property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="resultListForm"
	property="previousAuditId" type="String" />


<af:page titleKey="project.practices.list.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">

		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="ConsultationExpert::Resume" />
		</jsp:include>

		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="resultListForm" displayComparable="true" />
			<br />
			<h2><bean:message key="project.results.title" /></h2>
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="project.practices.list.details" />
			</af:dropDownPanel>
			<br />
			<logic:notEmpty name="resultListForm" property="list">
				<bean:define id="list" name="resultListForm" />
				<af:table name="list" property="list" displayNavigation="false">
					<af:cols id="practice">
						<af:col property="name" width="50px">
							<squale:info name="practice" practiceName="name" ruleId="id" />
						</af:col>
						<af:col property="name" key="project.result.practice.name">
							<%
							                String link =
							                "project.do?action=practice&projectId=" + projectId + "&currentAuditId=" + currentAuditId
							                    + "&previousAuditId=" + previousAuditId + "&which=" + ( (ResultForm) practice ).getId();
							%>
							<a href="<%=link%>"> <bean:message name="practice"
								property="name" /> </a>
						</af:col>
						<af:col property="predecessorMark" width="100px"
							key="project.result.practice.value.previous">
							<squale:mark name="practice" mark="predecessorMark" />
						</af:col>
						<af:col property="currentMark" width="100px"
							key="project.result.practice.value" sortable="true" type="Number">
							<squale:mark name="practice" mark="currentMark" />
						</af:col>
						<af:col property="currentMark" width="100px"
							key="project.result.factor.tendance">
							<squale:trend name="practice" current="currentMark"
								predecessor="predecessorMark" />
						</af:col>
						<af:col property="currentMark" width="50px">
							<squale:picto name="practice" property="currentMark" />
						</af:col>
						<%-- Racourci vers l'historique des pratiques --%>
						<af:col property="name" width="30px">
							<bean:define name="practice" property="id" id="id" type="String" />
							<squale:history projectId="<%=projectId%>"
								ruleId="<%=(String) id%>" kind="result"
								auditId="<%=currentAuditId%>"
								previousAuditId="<%=previousAuditId%>" />
						</af:col>
					</af:cols>
				</af:table>
			</logic:notEmpty>
			<logic:empty name="resultListForm" property="list">
				<bean:message key="project.result.no.value" />
			</logic:empty>
		</af:canvasCenter>
	</af:body>
</af:page>