<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="org.squale.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<bean:define id="applicationId" name="createApplicationForm" property="applicationId" type="String" />

<af:page>
	<af:body>
		<af:canvasCenter titleKey="application_purge.subtitle">
			<b><bean:message key="application.creation.field.application_name" /> <bean:write
				scope="session" name="createApplicationForm" property="applicationName" /></b>
			<br />
			<br />
			<bean:message key="application_creation.config.site" />
			<bean:write scope="session" name="createApplicationForm" property="serveurForm.name" />

			
			<af:form action="manageApplication.do">
				<INPUT type="hidden" name="applicationId" value="<%=applicationId%>"/>
				<af:buttonBar>
					<af:button singleSend="true" callMethod="selectApplicationToConfig" name="annuler" type="form" />
					<logic:empty name="hide">
						<af:button singleSend="true" callMethod="purge" name="valider" type="form" messageConfirmationKey="application_purge.confirm"/>
					</logic:empty>
                    <logic:notEmpty name="hide">
                        <af:button singleSend="true" callMethod="deleteApplication" name="valider" type="form"/>
                    </logic:notEmpty>
				</af:buttonBar>
			</af:form>
			
		</af:canvasCenter>
	</af:body>
</af:page>
