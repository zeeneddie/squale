<%@ taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<logic:present name="newsListForm" scope="session">
	<div class="homepage">
	<h2><bean:message key="news.consult" /></h2>
	<br />
		<af:form action="news.do" scope="session" method="POST">
			<af:table name="newsListForm" property="newsList" scope="session">
				<af:cols id="cols" selectable="false">			
					<bean:define id="lang" name="cols" property="message.language" type="String" />
					
					<bean:define id="key" name="cols" property="message.key" />
						<%String link = "news.do?action=listNews&lang=" + lang + "&which=current#" + key;%>
					<af:col property="message.title" key="news.col.title" href='<%=link%>' />
					<af:col property="beginningDate" key="news.col.beginDate" href='<%=link%>' type="DATE" dateFormatKey="date.format.simple" />
				</af:cols>
			</af:table>
		</af:form>
	</div>
	<br/>
	<br/>
</logic:present>
