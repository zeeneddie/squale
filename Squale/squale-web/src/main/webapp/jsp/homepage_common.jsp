<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>



<%
	// On récupère les paramètres nécessaire à la construction de la page
	String audits_param = (String) request.getParameter("property");
	String h3_title = (String) request.getParameter("h3_title");
%>
<bean:size name="splitAuditsListForm" property="<%=audits_param%>"
	id="nbAudits" />

<p class="surtitre"><bean:message key="homepage.audits" /></p>
<h3><bean:message key="<%=h3_title%>" /></h3>
<br />
<logic:equal name="nbAudits" value="0">
	<bean:message key="audits.none" />
	<br />
</logic:equal>
<logic:greaterThan name="nbAudits" value="0">
	<af:table name="splitAuditsListForm" property="<%=audits_param%>"
		totalLabelPos="none" scope="session" emptyKey="table.results.none">
		<af:cols id="element">
			<%-- la première colonne:la date --%>
			<logic:equal name="element" property="status" value="0">
				<af:col property="date" key="audit.date" sortable="true"
					paramName="element" paramId="currentAuditId" paramProperty="id"
					dateFormatKey="date.format" type="DATE" width="250px" />
			</logic:equal>
			<logic:equal name="element" property="status" value="1">
				<af:col property="realDate" key="audit.date" sortable="true"
					paramName="element" paramId="currentAuditId" paramProperty="id"
					dateFormatKey="date.format" type="DATE" width="250px"
					href="/squale/audits.do?action=select" />
			</logic:equal>
			<logic:equal name="element" property="status" value="2">
				<af:col property="realDate" key="audit.date" sortable="true"
					paramName="element" paramId="currentAuditId" paramProperty="id"
					dateFormatKey="date.format" type="DATE" width="250px"
					href="/squale/audits.do?action=select&kind=failed" />
			</logic:equal>
			<logic:equal name="element" property="status" value="4">
				<af:col property="realDate" key="audit.date" sortable="true"
					paramName="element" paramId="currentAuditId" paramProperty="id"
					dateFormatKey="date.format" type="DATE" width="250px"
					href="/squale/audits.do?action=select&kind=partial" />
			</logic:equal>
			<logic:equal name="element" property="status" value="5">
				<af:col property="realDate" key="audit.date" sortable="true"
					dateFormatKey="date.format" type="DATE" width="250px" />
			</logic:equal>
			<%-- la deuxième colonne: le status de l'audit --%>
			<logic:equal name="element" property="status" value="0">
				<af:col property="applicationName" key="audit.application_name"
					sortable="true" paramName="element" paramId="currentAuditId"
					paramProperty="id" width="250px" />
			</logic:equal>
			<logic:equal name="element" property="status" value="1">
				<af:col property="applicationName" key="audit.application_name"
					sortable="true" paramName="element" paramId="currentAuditId"
					paramProperty="id" width="250px"
					href="/squale/audits.do?action=select" />
			</logic:equal>
			<logic:equal name="element" property="status" value="2">
				<af:col property="applicationName" key="audit.application_name"
					sortable="true" paramName="element" paramId="currentAuditId"
					paramProperty="id" width="250px"
					href="/squale/audits.do?action=select&kind=failed" />
			</logic:equal>
			<logic:equal name="element" property="status" value="4">
				<af:col property="applicationName" key="audit.application_name"
					sortable="true" paramName="element" paramId="currentAuditId"
					paramProperty="id" width="250px"
					href="/squale/audits.do?action=select&kind=partial" />
			</logic:equal>
			<logic:equal name="element" property="status" value="5">
				<af:col property="applicationName" key="audit.application_name"
					sortable="true" width="250px" />
			</logic:equal>
			<%-- la troisième colonne: le type de l'audit qui n'est pas cliquable--%>
			<af:col property="type" key="audit.type" sortable="true"
				paramName="element" paramId="currentAuditId" paramProperty="id"
				width="250px">
				<bean:message name="element" property="type" />
			</af:col>
			<af:col property="name" key="audit.label" sortable="true"
				paramName="element" paramId="currentAuditId" paramProperty="id"
				width="250px">
				<logic:equal name="element" property="type"
					value="audit.type.milestone">
					<bean:write name="element" property="name" />
				</logic:equal>
				<logic:notEqual name="element" property="type"
					value="audit.type.milestone">
									-
								</logic:notEqual>
			</af:col>
			<%-- la quatrième colonne: le type de l'audit--%>
			<af:col property="stringStatus" key="audit.status" sortable="true"
				paramName="element" paramId="currentAuditId" paramProperty="id"
				width="250px">
				<bean:message name="element" property="stringStatus" />
			</af:col>
		</af:cols>
	</af:table>
</logic:greaterThan>