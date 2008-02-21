<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<af:page titleKey="project.errors.title" accessKey="default" >
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<jsp:include page="/jsp/results/errors_common.jsp">
			<jsp:param name="actionErrorForm" value="project_errors.do?action=errors"/>
		</jsp:include>
	</af:body>
</af:page>