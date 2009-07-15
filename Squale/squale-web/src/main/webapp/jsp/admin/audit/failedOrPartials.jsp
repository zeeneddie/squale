<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@ page import="org.squale.squaleweb.util.SqualeWebConstants" %>


<af:page titleKey="administration.failed_audits.title" accessKey="default">
	<af:body>
		<af:canvasCenter>
			<div>
            <html:messages id="msg" message="true" property="informations">
             <b><bean:write name="msg"/></b><br>
            </html:messages>
            </div>
			<br />
			<bean:message key="administration.failed_audits.details"/>
			<br/><br/>
			<af:form action="auditsFailed.do" scope="session" method="POST">
				<af:table name="splitAuditsListForm" property="failedOrPartialAudits"
					totalLabelPos="none" emptyKey="table.results.none">
					<af:cols id="audit" idIndex="index" selectable="true">
						<af:col property="stringStatus" key="audit.status" sortable="true" >
							<bean:message name="audit" property="stringStatus" />
						</af:col>
						<af:col property="applicationName" key="audit.application_name" sortable="true" 
								type="string" editable="false"/>
						<af:col key="audit.date" property="date" type="DATE" dateFormatKey="datetime.format" sortable="true" />
					</af:cols>
				</af:table>
			<af:buttonBar>
					<af:button type="form" callMethod="changeStatus" name="modifyStatus" toolTipKey="toolTip.modify.status" />
			</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>