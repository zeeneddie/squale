<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="org.apache.struts.action.ActionMessages" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm" %>
<%--
Permet la création d'un nouveau projet
 --%>
<% 
	// Réinitialisation du form de création de projet
   CreateApplicationForm form = (CreateApplicationForm)request.getSession().getAttribute("createApplicationForm");
   if (form!=null) {
	   form.resetFields();
   }
%>
<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
 

 
<af:page>
	<af:body>
		<af:canvasCenter titleKey="application_creation.title"
			subTitleKey="application_creation.subtitle.verify_unicity">
			<br />
			<br />
			<bean:message key="application_creation.details_verify_unicity" />
			
			<br />
			<br />
			<div style="color:#f00">
				<html:messages id="message"
					property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
					<bean:write name="message" />
					<br />
				</html:messages>
				<br />
			</div>

			<af:form action="new_application.do">
				<input type="hidden"
					name="<%=org.squale.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM%>"
					value="true">
				<af:field key="application_creation.field.application_name" property="applicationName"
					isRequired="true"></af:field>
				<br />
				<br />
				<af:buttonBar>
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="create" singleSend="true" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>