<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="org.squale.squaleweb.applicationlayer.action.IndexAction" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<af:page titleKey="news.title" subTitleKey="news.subTitle"
	accessKey="default">
	<af:body canvasLeftPageInclude="/jsp/canvas/news_menu.jsp">
		<af:canvasCenter>
			<br>
			<br>
			<logic:present name="SelectionProblem">
				<bean:define id="problemLabel" name="SelectionProblem"
					scope="request" type="String" />
				<bean:message key="<%=problemLabel%>" />
			</logic:present>
			<logic:present name="newsListForm" scope="session">
				<af:form action="adminNews.do" scope="session" method="POST">
					<af:table name="newsListForm" property="newsList" scope="session">
						<af:cols id="cols" selectable="true">
							<af:col property="message.key" key="news.col.key" />
							<af:col property="message.title" key="news.col.title" />
							<af:col property="message.language" key="news.col.lang" />
							<af:col property="message.text" key="news.col.text" />
							<af:col property="beginningDate" key="news.col.beginDate"
								dateFormatKey="date.format.simple" type="DATE" />
							<af:col property="endDate" key="news.col.endDate"
								dateFormatKey="date.format.simple" type="DATE" />
						</af:cols>
					</af:table>
					<af:buttonBar>
						<af:button type="form" callMethod="purge" name="news.purge"
							toolTipKey="toolTip.news.purger"
							messageConfirmationKey="news_purge.confirm" accessKey="admin" />
						<af:button type="form" callMethod="checkModify" name="news.modify"
							toolTipKey="toolTip.news.modify" accessKey="admin" />
						<af:button type="form" callMethod="checkAdd" name="news.add"
							toolTipKey="toolTip.news.add" accessKey="admin" />
					</af:buttonBar>
				</af:form>
			</logic:present>
			<logic:notPresent name="newsListForm" scope="session">
				<bean:message key="news.none" />
				<br>
				<br>
				<af:form action="adminNews.do" scope="session" method="POST">
					<af:buttonBar>
						<af:button type="form" name="retour"
							toolTipKey="toolTip.retour.homepage" onclick="<%=\"location.href='index.do?'\"%>" />
						<af:button type="form" callMethod="checkAdd" name="news.add"
							toolTipKey="toolTip.news.add" accessKey="admin" />
					</af:buttonBar>
				</af:form>
			</logic:notPresent>
		</af:canvasCenter>
	</af:body>
</af:page>