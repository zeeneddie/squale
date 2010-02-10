<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>



<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.list" color="2" action="grid.do?action=list" />
		<af:menuItem key="menu.new" color="2" action="addGrid.do" />
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true" />
</af:canvasLeft>

