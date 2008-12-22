<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>

<logic:iterate id="result" property="graphMakerMap" name="homepageForm">
	<bean:define id="auditId" name="result" property="key"/>
	<a href="audits.do?action=select&currentAuditId=<%=auditId%>">
	<div class="ongKiviat">
		<bean:define id="srcKiviat" name="result" property="value.srcName" type="String" />
		<bean:define id="imgMapKiviat" name="result" property="value.useMapName" type="String" />
		<html:img src="<%=srcKiviat%>" usemap="<%=imgMapKiviat%>" border="0"/>
	</div>
	</a>
</logic:iterate>