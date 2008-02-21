<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.action.grid.GridCreationAction" %>



<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/grid_menu.jsp">
		<af:canvasCenter titleKey="grid_import_confirm.title">
			<br />
			<br />
			<bean:message key="grid_import_confirm.details" />
			<br />
			<br />
			<div style="color:#f00;">
            <html:messages id="msg" message="true">
             <bean:write name="msg"/><br>
            </html:messages>
			</div>
			<af:form action="createGrid.do" scope="session" method="POST">
				<bean:define id="gridform" name="<%=GridCreationAction.GRID_CONFIRM_FORM%>" scope="session" />
				<af:table name="gridform" property="grids" totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="element" idIndex="index">
						<af:col property="name" key="grid.name"	width="250px" />
					</af:cols>
				</af:table>
			<af:buttonBar>
					<af:button type="form" callMethod="cancelGrid" name="annuler" toolTipKey="toolTip.annuler" />
					<af:button type="form" callMethod="confirmGrid" name="valider" toolTipKey="toolTip.valider" />
			</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>