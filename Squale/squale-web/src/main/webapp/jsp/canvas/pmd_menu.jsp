<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.list" color="2" action="pmd.do?action=list" />
		<af:menuItem key="menu.new" color="2" action="addPmdRuleSet.do" />
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true" />
</af:canvasLeft>
