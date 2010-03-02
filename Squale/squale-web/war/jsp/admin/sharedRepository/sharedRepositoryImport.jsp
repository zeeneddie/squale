<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="shared_repository.reference.title">
		<br/>
		<div class="frame_border">
			<h3><bean:message key="shared_repository.reference.current_version.title"/></h3>
			<br/>
			<logic:notEmpty name="sharedRepositoryImportForm" property="currentReferenceVersion">
				<bean:message key="shared_repository.reference.current_version"/>
				<bean:write name="sharedRepositoryImportForm" property="currentReferenceVersion" />
			</logic:notEmpty>
			<logic:empty name="sharedRepositoryImportForm" property="currentReferenceVersion">
				<bean:message key="shared_repository.reference.no_version"/>
			</logic:empty>
			<br/>
			<br/>
		</div>
		<br/>
		<div class="frame_border">
			<h3><bean:message key="shared_repository.reference.new_reference.title"/></h3>
			<br/>
			<div style="color: #f00">
				<html:messages id="msg" message="true">
				<bean:write name="msg" />
				<br>
				</html:messages>
			</div>
			<af:form action="sharedRepositoryImport.do" method="POST" enctype="multipart/form-data">
				<table width="100%" class="formulaire" cellpadding="0" cellspacing="0" border="0">
					<tbody>
					<%--File to download --%>
					<tr>
						<td class="td1" width="45%"><bean:message key="shared_repository.reference.file"/></td>
						<td><html:file property="file"/></td>
					</tr>
					</tbody>
				</table>
				<af:buttonBar>
					<af:button name="import" callMethod="impor"></af:button>
				</af:buttonBar>
			</af:form>
		</div>
		<br/>
		</af:canvasCenter>
	</af:body>
</af:page>