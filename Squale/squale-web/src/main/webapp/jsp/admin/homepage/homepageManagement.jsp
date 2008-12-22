<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.homepagemanagement.HomepageManagementForm" %>
 
<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
<script type="text/javascript" src="theme/charte_v03_001/js/homepageManagement.js"></script>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="homepage_management.title" subTitleKey="homepage_management.subtitle">
			<br/>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<br/>
				<p>
					<bean:message key="homepage_management.help.l1" />
				</p>
				<br/>
				<p>
					<bean:message key="homepage_management.help.l2" />
				</p>
				<br/>
				<p>
					<bean:message key="homepage_management.help.l3" />
				</p>
				<br/>
				<p>
					<bean:message key="homepage_management.help.l4" />
				</p>
				<br/>
				<p>
					<bean:message key="homepage_management.help.l5" />
				</p>
				<br/>
			</af:dropDownPanel>
			<logic:messagesPresent message="true">
				<html:messages id="msg" message="true">
					<div style="color: #f00" id="msgId">
						<br/>
						<bean:write name="msg" filter="false" />
					</div>
				</html:messages>
			</logic:messagesPresent>
			<logic:messagesPresent>
				<html:messages id="msg">
					<div style="color: #f00" id="msgErr">
						<br/>
						<bean:write name="msg" filter="false" />
					</div>
				</html:messages>
			</logic:messagesPresent>
			<br>
			<af:form action="/homepage_management.do">
				<div onclick="maskMsg()">
					<div id="hp_mngt">
						<logic:iterate id="jspToDisplay" indexId="indexValue" property="jspOrder" name="HomepageManagementForm" offset="1">
							<jsp:include  page="<%=jspToDisplay.toString()%>">
								<jsp:param name="index" value="<%=indexValue.toString()%>"/>
							</jsp:include>
							<br>
							<br>
						</logic:iterate>
					</div>
					<af:buttonBar>
						<af:button name="valider" toolTipKey="toolTip.valider" callMethod="record"></af:button>
					</af:buttonBar>
				</div>
			</af:form>
		</af:canvasCenter>
	</af:body>
	<script type="text/javascript" >
	display();
</script>
</af:page>