<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Affiche la liste des applications sur lesquelles l'utilisateur est
administrateur
--%>

<script type="text/javascript" src="/squale/jslib/format_page.js">
</script>



<af:page accessKey="default">
	<af:body canvasLeftPageInclude="/jsp/canvas/grid_menu.jsp">
		<af:canvasCenter titleKey="grids_admin.title">
			<br />
			<br />
			<bean:message key="grids_admin.details" />
			<br />
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
			</html:messages></div>
			<%-- Affichage d'un message d'erreur si des grilles ne sont liées à aucun profil ni aucun audit --%>
			<bean:define id="unlinkedGridsSize" name="gridListForm" property="unlinkedGrids" />
			<logic:greaterThan name="unlinkedGridsSize" value="0">
				<div style="color: #f00">
					<bean:message key="grids_admin.existing.unlinkedGrids" />
					<ul>
					<logic:iterate name="gridListForm" property="unlinkedGrids" id="gridName">
						<li><%=gridName%></li>
					</logic:iterate>
					</ul>
				</div>
			</logic:greaterThan>
			<af:form action="grid.do" scope="session" method="POST">
				<af:table name="gridListForm" property="grids" totalLabelPos="none"
					emptyKey="table.results.none">
					<af:cols id="element" selectable="true">
						<af:col property="name" key="grid.name" sortable="true"
							href="/squale/grid.do?action=detail" paramName="element"
							paramId="gridId" paramProperty="id" width="250px" />
						<af:col property="updateDate" key="grid.updateDate"
							sortable="true" type="DATE" dateFormatKey="datetime.format.grid"
							width="200px" />
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" callMethod="purge" name="audits.purger"
						toolTipKey="toolTip.audit.purger"
						messageConfirmationKey="grids_purge.confirm" accessKey="manager" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>