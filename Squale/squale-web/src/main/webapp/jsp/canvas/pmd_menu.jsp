<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>

<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.list" color="2"
			action="/squale/pmd.do?action=list" />
		<af:menuItem key="menu.new" color="2"
			action="/squale/addPmdRuleSet.do" />
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true"/>
</af:canvasLeft>
