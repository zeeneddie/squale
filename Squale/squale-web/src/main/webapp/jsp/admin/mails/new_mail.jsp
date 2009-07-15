<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.mails.MailForm" %>
<%@ page import="org.apache.struts.Globals" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<af:page titleKey="mail.send.title" subTitleKey="mail.send.subtitle">
	<af:body>
		<af:canvasCenter>
			<bean:size id="nbApplis" name="mailForm"
				property="applicationFormsList.list" />
			<logic:equal name="nbApplis" value="0">
				<bean:message key="appli.none" />
			</logic:equal>
			<logic:greaterThan name="nbApplis" value="0">
				<af:form action="sendMail.do">
					<table width="100%" class="formulaire" cellpadding="0"
						cellspacing="0" border="0">
						<tr>
							<td class="td1"><bean:message key="mail.select.appliDest" /></td>
							<td><af:select name="mailForm" property="appliName">
								<af:option value="<%=MailForm.ALL_APPLICATIONS%>">
									<bean:message key="mail.all.applications" />
								</af:option>
								<logic:iterate id="list" name="mailForm"
									property="applicationFormsList.list" type="ApplicationForm">
									<af:option key="Application"
										value="<%=list.getApplicationName()%>" />
								</logic:iterate>
							</af:select></td>
						</tr>
						<tr>
							<af:field key="mail.object" property="object" isRequired="true"
								styleClassLabel="td1" size="150" />
						</tr>
						<tr>
							<af:field key="mail.content" type="TEXTAREA" rows="10"
								name="mailForm" isRequired="true" property="content"
								styleClassLabel="td1" size="150" />
						</tr>
					</table>
					<af:buttonBar>
						<af:button type="form" name="valider" toolTipKey="toolTip.valider"
							callMethod="sendMail" />
					</af:buttonBar>
					<br />
					<br />
					<ul>
						<html:messages id="message" message="true" property="listEmailsMsg">
							<bean:write name="message" filter="false"/>
						</html:messages>
					</ul>
					<html:messages id="messageTitle" message="true" property="errorTitleMsg">
						<bean:message key="mail.admin.no.sent" />
					</html:messages>
					<ul>
						<html:messages id="message" message="true" property="errorMsg">
							<li><bean:write name="message" /></li>
						</html:messages>
					</ul>
					<html:messages id="successMessage" message="true" property="successMsg">
						<bean:write name="successMessage" />
					</html:messages>
				</af:form>
			</logic:greaterThan>
		</af:canvasCenter>
	</af:body>
</af:page>