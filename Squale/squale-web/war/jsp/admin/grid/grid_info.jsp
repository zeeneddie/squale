<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@ page import="org.squale.squaleweb.applicationlayer.action.grid.GridCreationAction" %>



<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/grid_menu.jsp">
		<af:canvasCenter titleKey="grid.admin.info.title">
			<br />
			<br />
			<bean:message key="grid.admin.info" />
			<br />
			<br />
			<bean:define id="gridform" name="<%=GridCreationAction.GRID_CONFIRM_FORM%>" scope="session" />
			<af:form action="sendMailForGridChanges.do" method="POST" >
				<af:table name="gridform" property="grids" totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="element" idIndex="index">
						<af:col property="name" key="grid.name"	width="100px" />
						<af:col property="adminText" key="grid.adminText" >
							<af:field key="mail.adminText" size="170" type="TEXTAREA" rows="5" name="gridform" property='<%="grids["+index+"].adminText"%>' />
						</af:col>
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" callMethod="notify" name="mail.send" toolTipKey="toolTip.mail.send" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>
			