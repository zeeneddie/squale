<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ page import="org.apache.struts.action.ActionMessages" %>

<%--
Permet le parsing d'un nouveau fichier de configuration PMD
 --%>


<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>


<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/pmd_menu.jsp">
		<af:canvasCenter titleKey="rulechecking.pmd.import.title"
			subTitleKey="rulechecking.pmd.import.subtitle">
			<br />
			<af:form action="parsePmdRuleset.do" method="POST"
				enctype="multipart/form-data">
				<br />
				<div style="color: #f00"><html:messages id="message"
					property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
					<bean:write name="message" />
					<br />
				</html:messages> <br />
				</div>
				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
					border="0">
					<%--Fichier à charger --%>
					<tr>
						<td class="td1"><bean:message key="rulechecking.file" /></td>
						<td><html:file property="file" /></td>
					</tr>
				</table>
				<af:buttonBar>
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="importConfiguration" singleSend="true" />
				</af:buttonBar>
			</af:form>

		</af:canvasCenter>
	</af:body>
</af:page>