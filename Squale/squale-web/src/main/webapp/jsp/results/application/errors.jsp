<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<af:page titleKey="project.errors.title" accessKey="default" >
	<af:body canvasLeftPageInclude="/jsp/canvas/application_menu.jsp">
		<jsp:include page="/jsp/results/errors_common.jsp">
			<jsp:param name="actionErrorForm" value="application_errors.do?action=errors"/>
		</jsp:include>
	</af:body>
</af:page>