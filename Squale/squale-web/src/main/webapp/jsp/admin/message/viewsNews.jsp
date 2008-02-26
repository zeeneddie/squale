<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.action.IndexAction" %>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js">
</script>

<af:page titleKey="news.title" subTitleKey="news.subTitle">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/news_menu.jsp">
	
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="Divers::Actu" />
		</jsp:include>
		
		<af:canvasCenter>
			<logic:present name="newsListForm" scope="session">
				<%--On place une ancre sur le sommaire des news--%>
				<a name="index"></a>
				<ul>
					<logic:iterate name="newsListForm" property="newsList"
						scope="session" id="news">
						<bean:define id="key" name="news" property="message.key" />
						<li><a href='<%="#" + key%>'> <bean:write name="news"
							property="message.title" /> </a></li>
					</logic:iterate>
				</ul>
				<af:form action="news.do" scope="session" method="POST">
					<logic:iterate name="newsListForm" property="newsList"
						scope="session" id="news">
						<div style="height:1px; overflow: hidden; background-color: #F78704;"></div>
						<br />
						<bean:define id="key2" name="news" property="message.key" />
						<a name="<%=key2%>"></a>
						<p class="surtitre"><af:field key="empty" name="news"
							property="beginningDate" type="DATE"
							dateFormatKey="date.format.simple" disabled="true" /> <bean:write
							name="news" property="message.title" /></p>
						<div style="font-size:1.3em;"><pre><bean:write name="news" property="message.text"
							filter="false" /></pre></div>
						<a href="#index" class="picto"><bean:message key="news.return" /></a>
					</logic:iterate>
				</af:form>
			</logic:present>
			<logic:notPresent name="newsListForm" scope="session">
				<bean:message key="news.none" />
				<br>
				<br>
			</logic:notPresent>
			<af:buttonBar>
				<af:button type="form" name="retour"
					toolTipKey="toolTip.retour.homepage" onclick='<%="index.do?"%>' />
			</af:buttonBar>
		</af:canvasCenter>
	</af:body>
</af:page>