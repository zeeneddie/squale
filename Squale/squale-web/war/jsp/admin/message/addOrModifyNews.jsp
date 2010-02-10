<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="org.squale.squaleweb.applicationlayer.action.IndexAction" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<%String titleKey = "news.modify.title";%>
<logic:notPresent name="modify">
	<%titleKey = "news.add.title";%>
</logic:notPresent>

<af:page titleKey='<%=titleKey%>' accessKey="default">
	<af:body canvasLeftPageInclude="/jsp/canvas/news_menu.jsp">
		<af:canvasCenter>
			<div style="color: #f00"><html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages> <br />
			</div>
			<af:form action="addOrModifyNews.do">
				<bean:define id="form" name="newsForm" />
				<table id="newsTable" width="100%" class="formulaire"
					cellpadding="0" cellspacing="0" border="0">
					<%-- dans le cas d'une modification, on ne peut pas changer ni la clé ni la langue
				car l'ensemble constitue la clé primaire de la table --%>
					<logic:present name="modify">
						<tr>
							<af:field key="news.new.key" disabled="true" name="form"
								property="key" isRequired="false" styleClassLabel="td1"
								size="150" />
						</tr>
						<tr>
							<af:field key="news.select.lang" disabled="true" name="form"
								property="message.language" isRequired="false"
								styleClassLabel="td1" size="150" />
						</tr>
					</logic:present>
					<%-- dans le cas d'un ajout,tout doit etre renseignée --%>
					<logic:notPresent name="modify">
						<tr>
							<af:field key="news.new.key" name="form" property="key"
								isRequired="true" styleClassLabel="td1" size="150" />
						</tr>
						<tr>
							<td class="td1"><bean:message key="news.select.lang" /></td>
							<td align="left"><af:select isRequired="true"
								property="message.language">
								<logic:iterate id="langSet" name="form" property="langSet"
									type="String">
									<af:option key="Lang" value="<%=langSet%>" />
								</logic:iterate>
							</af:select></td>
						</tr>
					</logic:notPresent>
					<tr>
						<af:field key="news.new.title" name="form"
							property="message.title" isRequired="true" styleClassLabel="td1"
							size="150" />
					</tr>
					<tr>
						<af:field key="news.new.text" type="TEXTAREA" rows="10"
							name="form" property="message.text" isRequired="true"
							styleClassLabel="td1" size="150" />
					</tr>
					<tr>
						<af:field key="news.new.beginningDate" name="form"
							property="beginningDate" isRequired="true" styleClassLabel="td1"
							size="60" dateFormatKey="date.format.simple" type="DATE" />
					</tr>
					<tr>
						<af:field key="news.new.endDate" name="form" property="endDate"
							isRequired="true" styleClassLabel="td1" size="60"
							dateFormatKey="date.format.simple" type="DATE" />
					</tr>
					<logic:present name="modify">
						<af:buttonBar>
							<af:button type="form" name="retour" toolTipKey="toolTip.retour"
								onclick="location.href='adminNews.do?action=listNews&which=current'" />
							<af:button type="form" callMethod="modify" name="news.modify"
								toolTipKey="toolTip.news.modify" accessKey="admin"
								messageConfirmationKey="news_modify.confirm" />
						</af:buttonBar>
					</logic:present>
					<logic:notPresent name="modify">
						<af:buttonBar>
							<af:button type="form" name="retour" toolTipKey="toolTip.retour"
								onclick="location.href='adminNews.do?action=listNews&which=current'" />
							<af:button type="form" callMethod="add" name="news.add"
								toolTipKey="toolTip.news.add" accessKey="admin"
								messageConfirmationKey="news_add.confirm" />
						</af:buttonBar>
					</logic:notPresent>
				</table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>