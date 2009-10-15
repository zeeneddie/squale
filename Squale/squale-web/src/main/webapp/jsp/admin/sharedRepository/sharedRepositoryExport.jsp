<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<af:page>
	<af:body>
		<af:canvasCenter titleKey="shared_repository.export.title" subTitleKey="shared_repository.export.subtitle">
		<br/>
		<af:dropDownPanel titleKey="buttonTag.menu.aide">
			<br/>
				<bean:message key="shared_repository.export.help" />
			<br/>
		</af:dropDownPanel>
		<br/>
		<af:form action="sharedRepositoryExport.do">
			<div class="frame_border">
				<af:table name="sharedRepositoryExportForm" property="listApp" displayFooter="false" totalLabelPos="bottom">
					<af:cols id="application_elt">
						<af:colSelect/>
						<af:col property="applicationName" key="shared_repository.export.application_name"></af:col>
						<af:col property="lastExportDate" emptyKey="-" key="shared_repository.export.last_export"></af:col>
					</af:cols>
				</af:table>
				<br/>
				<af:buttonBar>
					<af:button name="export" callMethod="export"></af:button>
					<af:button name="export_all" callMethod="exportAll"></af:button>
				</af:buttonBar>
				<br/>
			</div>
		</af:form>
		<br/>
		</af:canvasCenter>
	</af:body>
</af:page>