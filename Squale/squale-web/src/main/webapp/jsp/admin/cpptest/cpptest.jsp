<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Permet l'importation d'un fichier de règles CppTest
--%>

<script type="text/javascript" src="/squale/jslib/format_page.js">
</script>



<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/cpptest_menu.jsp">
		<af:canvasCenter titleKey="rulechecking.cpptest.list.title"
			subTitleKey="rulechecking.cpptest.list.subtitle">
			<br />
			<br />
			<div style="color: #f00"><html:messages id="msg" message="true">
				<bean:write name="msg" filter="false"/>
				<br>
			</html:messages></div>
			<af:form action="cppTest.do" method="POST" scope="request">
				<af:table name="cppTestConfigForm" property="ruleSets"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="element" selectable="true">
						<af:col property="name" key="rulechecking.name" sortable="true"
							width="250px" />
						<af:col property="dateOfUpdate" key="rulechecking.date"
							sortable="true" type="DATE" width="250px" />
						<af:col property="cppTestName" key="rulechecking.cpptestname"
							sortable="true" width="250px" />
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" name="supprimer" callMethod="purge"
						singleSend="true" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>