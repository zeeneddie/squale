<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%--
Permet le parsing d'un nouveau fichier de configuration checkstyle
 --%> 
 
 
<script type="text/javascript" src="/squale/jslib/format_page.js"></script>


<af:page>
	<af:body canvasLeftPageInclude="/jsp/canvas/checkstyle_menu.jsp">
		<af:canvasCenter titleKey="rulechecking.checkstyle.list.title"
			subTitleKey="rulechecking.checkstyle.list.subtitle">
			<br />
			<div style="color: #f00">
				<html:messages id="msg" message="true">
					<bean:write name="msg" filter="false" />
					<br>
				</html:messages>
			</div>
			<af:form action="checkstyle.do" method="POST" scope="request" >
				<af:table name="CheckstyleRuleSetListForm" property="ruleSets" totalLabelPos="none"
					emptyKey="table.results.none">
					<af:cols id="element" selectable="true">
						<af:col property="name" key="rulechecking.name" sortable="true"
							/>
						<af:col property="dateOfUpdate" key="rulechecking.date" sortable="true"
							type="DATE" />
					</af:cols>
				</af:table>
				<af:buttonBar>
					<af:button type="form" name="supprimer" 
						callMethod="purge" singleSend="true" />
				</af:buttonBar>
			</af:form>           			
		</af:canvasCenter>
	</af:body>
</af:page>