<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>



<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.list" color="2"
			action="config_portail.do?action=list" />
		<af:menuItem key="menu.load" color="2" action="loadConfig.do" />
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true" />
</af:canvasLeft>
