<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<bean:message key="qualimetric_element.name" />
<bean:write name="practiceInformationForm" property="name" />
<br />
<br />
<af:dropDownPanel titleKey="qualimetric_element.description"
	expanded='<%=new Boolean(request.getParameter("expandedDescription")).booleanValue()%>'>
	<bean:write name="practiceInformationForm" property="description"
		filter="false" />
</af:dropDownPanel>
<br />
<br />
<af:dropDownPanel titleKey="qualimetric_element.formula">
	<b><bean:message key="qualityRule.trigger" /></b>
	<bean:write name="practiceInformationForm" property="trigger" />
	<br />
	<b><bean:message key="qualityRule.formula" /></b>
	<logic:empty name="practiceInformationForm" property="formula">
		<logic:iterate id="condition" name="practiceInformationForm"
			property="formulaCondition">
			<table>
				<tr>
					<td></td>
					<td><bean:write name="condition" /></td>
				</tr>
			</table>
		</logic:iterate>
	</logic:empty>
	<logic:notEmpty name="practiceInformationForm" property="formula">
		<bean:write name="practiceInformationForm" property="formula" />
	</logic:notEmpty>
	<logic:notEmpty name="practiceInformationForm" property="usedTres">
		<br />
		<af:dropDownPanel titleKey="qualityRule.usedTres">
			<logic:iterate name="practiceInformationForm" property="usedTres"
				id="tre" type="String">
				<i><%="\t" + tre.replaceFirst("\\.[A-Za-z]+", "")%></i> = <bean:message
					key="<%=tre%>" />
				<br />
			</logic:iterate>
		</af:dropDownPanel>
	</logic:notEmpty>
</af:dropDownPanel>
<br />
<br />
<af:dropDownPanel titleKey="qualimetric_element.correction">
	<bean:write name="practiceInformationForm" property="correction"
		filter="false" />
</af:dropDownPanel>