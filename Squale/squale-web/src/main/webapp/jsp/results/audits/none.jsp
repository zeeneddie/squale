<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<bean:define id="projectName" name="projectSummaryForm" property="projectName" type="String"/> 

<af:page titleKey="audits.none.title">
	<af:body > 
		<af:canvasCenter>
			<br />
			<b><bean:message key="audits.none.text" arg0="<%=projectName%>" /></b>
			<br /><br />
			<af:buttonBar>
				<af:button name="retour" toolTipKey="toolTip.retour.homepage" onclick='<%="index.do?"%>' />
			</af:buttonBar>
		</af:canvasCenter>
	</af:body>
</af:page>