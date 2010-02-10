<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<div class="homepage">
	<h2><bean:message key="homepage.audits.audits_list" /></h2>
	<br>
	<af:tabbedPane name="audit">
		<af:tab name="auditDone" key="homepage.audits.my_audits">
			<jsp:include page="audits_common.jsp">
				<jsp:param name="property" value="audits" />
			</jsp:include>
		</af:tab>
		<logic:notEmpty name="homepageForm" property="scheduledAudits">
			<af:tab name="auditScheduled" key="homepage.audits.scheduled_audits">
				<jsp:include page="audits_common.jsp">
					<jsp:param name="property" value="scheduledAudits" />
				</jsp:include>
			</af:tab>
		</logic:notEmpty>
	</af:tabbedPane>
</div>
<br/>
<br/>