<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

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
		</af:dropDownPanel>
		<br/>
		<af:form action="sharedRepositoryImport.do">
		<div class="frame_border">
			<br/>
			<af:buttonBar>
				<af:button name="impor"></af:button>
			</af:buttonBar>
			<br/>
		</div>
		</af:form>
		<br/>
		</af:canvasCenter>
	</af:body>
</af:page>