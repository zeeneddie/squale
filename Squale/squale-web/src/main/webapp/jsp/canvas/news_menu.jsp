<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>

<af:canvasLeft>
	<af:menu>
		<af:menuItem key="menu.news.consult" color="2">
			<af:menuItem key="menu.news.all" color="2"
				action="adminNews.do?action=listNews&which=all" />
			<af:menuItem key="menu.news.old" color="2"
				action="adminNews.do?action=listNews&which=old" />
			<af:menuItem key="menu.news.current" color="2"
				action="adminNews.do?action=listNews&which=current" />
		</af:menuItem>
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true"/>
</af:canvasLeft>
