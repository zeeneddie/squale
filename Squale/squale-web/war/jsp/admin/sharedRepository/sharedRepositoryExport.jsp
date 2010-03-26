<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<script type="text/javascript" src="theme/charte_v03_001/js/filter.js"></script>
<script type="text/javascript" src="theme/charte_v03_001/js/exporter.js"></script>

<af:page>
	<af:body onload="showDiv();lineStyle();">
		<af:canvasCenter titleKey="shared_repository.export.title">
		<br/>
		<af:dropDownPanel titleKey="buttonTag.menu.aide">
			<br/>
				<bean:message key="shared_repository.export.help" />
			<br/>
		</af:dropDownPanel>
		<br/>
		<logic:notEqual name="sharedRepositoryExportForm" property="inProgressJob" value="true">
			<html:hidden styleId="isSchedduled" name="sharedRepositoryExportForm" property="scheduledJob" />
			<af:form action="sharedRepositoryExport.do">
				<html:hidden styleId="modify" name="sharedRepositoryExportForm" property="modify"/>
				<div class="frame_border" style="display:none" id="choiceDiv">
					<h3><bean:message key="shared_repository.export.chooseAppliToExport.title"/></h3>
					<br/>
				<bean:message key="manualMark.search" />
				<br/>
				<input type="text" onkeyup="filter(this, event);lineStyle();"  name="search" size="60"/>
				<br/>
				<br/>
					<div class="export_application">
					<table class = "formulaire">	
						<thead>
							<tr>
								<th></th>
								<th><bean:message key="shared_repository.export.application_name" /></th>
								<th><bean:message key="shared_repository.export.last_export" /></th>
							</tr>
						</thead>
						<tbody>
							<logic:iterate id="elt" indexId="index" name="sharedRepositoryExportForm" property="listApp">
								<tr class="" id="li-<%=index%>">
									<%-- "Check" column. Display only if the user as enough rights --%>
									<td class="check_col">								
										<html:checkbox styleId='<%="check-"+index%>' name="sharedRepositoryExportForm" property='<%="listApp["+index+"].selected"%>' onclick="check(this);"></html:checkbox>
									</td>
									<td><af:write name="elt" property="applicationName"/></td>
									<td><af:write name="elt" property="lastExportDate" dateFormatKey="date.format.simple"/></td>
								</tr>
							</logic:iterate>
						</tbody>
					</table>
					<br/>
					<af:button name="sharedrepository.export.selectAll" onclick="selectAllCB();"></af:button>
					<af:button name="sharedrepository.export.unSelectAll" onclick="unSelectAllCB();"></af:button>
					</div>	
					<br/>
					<af:buttonBar>
						<div id="buttonSave" style="display:none" ><af:button name="sharedrepository.export.save" callMethod="export"></af:button></div>
						<div id="buttonExport" style="display:none" ><af:button name="sharedrepository.export.export" callMethod="export"></af:button></div>
						<div id="buttonCancel" style="display:none" ><af:button name="sharedrepository.export.cancel" callMethod="export" onclick="unSelectAllCB();"></af:button></div>
					</af:buttonBar>
					<br/>
				</div>
				<br/>
				<div class="frame_border" style="display:none" id="listDiv">
					<h3><bean:message key="shared_repository.export.listAppliToExport.title"/></h3>
					<bean:size id="listSize" name="sharedRepositoryExportForm" property="selectedApp"/>
					<div class="export_application">
						<logic:greaterThan name="listSize" value="0">
							<ul>
								<logic:iterate id="app" name="sharedRepositoryExportForm" property="selectedApp" >
									<li>
										<bean:write name="app"/>
									</li>
								</logic:iterate>
							</ul>
						</logic:greaterThan>
					</div>
					<af:buttonBar>
						<af:button name="sharedrepository.export.change" callMethod="modify"></af:button>
					</af:buttonBar>
					<br/>
				</div>
			</af:form>
			<div class="frame_border">
				<h3><bean:message key="shared_repository.export.status.title"/></h3>
				<logic:notEmpty name="sharedRepositoryExportForm" property="failedJob">
					<bean:write name="sharedRepositoryExportForm" property="failedJob.jobDate"/>
					<bean:message key="shared_repository.export.failed"/>
					<br/>
				</logic:notEmpty>	
				<br/>
				<logic:notEmpty name="sharedRepositoryExportForm" property="nothingToExportJob">
					<bean:write name="sharedRepositoryExportForm" property="nothingToExportJob.jobDate"/>
					<bean:message key="shared_repository.export.nothingToExport"/>
					<br/>
				</logic:notEmpty>	
				<br/>
				<logic:notEmpty name="sharedRepositoryExportForm" property="successfulJob">
					<bean:message key="shared_repository.export.successful"/>
					<bean:write name="sharedRepositoryExportForm" property="lastSuccessfulDate"/>
					<logic:notEmpty name="sharedRepositoryExportForm" property="exportFilePath" >
						<html:link indexId="exportFile" href="sharedRepositoryExport.do?action=downloadExportFile"><bean:message key="shared_repository.export.download"/></html:link>
					</logic:notEmpty>
				</logic:notEmpty>
				<logic:empty name="sharedRepositoryExportForm" property="successfulJob">
					<bean:message key="shared_repository.export.noSuccessfulJob"/>
				</logic:empty>
				<br/>
				<br/>
			</div>
			<br/>
		</logic:notEqual>
		<logic:equal name="sharedRepositoryExportForm" property="inProgressJob" value="true">
			<br/>
			<div class="frame_border">
				<br/>
				<bean:message key="shared_repository.export.in_progress" />
				<br/>
				<br/>
			</div>
			<br/>
		</logic:equal>
		</af:canvasCenter>
	</af:body>
</af:page>