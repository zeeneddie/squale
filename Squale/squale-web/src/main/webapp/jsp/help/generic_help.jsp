<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>



<af:page titleKey="help.title"
	subTitleKey="<%=\"help.\"+request.getParameter(\"title\")+\".title\"%>" >
	<af:body canvasLeftPageInclude="/jsp/canvas/help_menu.jsp">
		<af:canvasCenter>
			<br/>
			<bean:message key="<%=\"help.\"+request.getParameter(\"title\")+\".description\"%>" />
		</af:canvasCenter>
	</af:body>
</af:page>
