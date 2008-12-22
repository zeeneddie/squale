<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorForm"%>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm"%>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebActionUtils"%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>
	
	<af:tabbedPane name="result">
	
		<af:form action="index.do">
				<logic:iterate id="gridResult" name="homepageForm" property="list"
					indexId="index">
					<logic:notEqual name="gridResult" property="gridName" value="">
						<bean:define name="gridResult" property="gridName" id="grid"/>
						<af:tab name="<%="resultByGrid"+index %>" key="<%="ref."+grid %>">
						<af:table name="homepageForm"
							property='<%="list[" + index + "].results"%>'
							totalLabelPos="none" emptyKey="table.results.none"
							displayNavigation="false" displayFooter="false">
							<af:cols id="element">
								<bean:define id="projectId" name="element" property="id" type="String" />
								<bean:define id="currentAuditId" name="element" 
								property="currentAuditId" type="String" />
								<af:col property="applicationName" key="application.name" sortable="true"
									width="15%" 
									href="<%=\"application.do?action=summary&currentAuditId=\"+currentAuditId%>"
									paramName="element" paramId="applicationId"
									paramProperty="applicationId" />
								<af:col property="name" key="project.name" sortable="true"
									width="15%"
									href="<%=\"project.do?action=select&currentAuditId=\"+currentAuditId%>"
									paramName="element" paramId="projectId" paramProperty="id" />
								<logic:iterate id="factor" name="element" property="factors">
									<bean:define id="name" name="factor" property="name"
										type="String" />
									<bean:define id="factorId" name="factor" property="id"
										type="Long" />
									<af:col property="value" key="<%=name%>">
										<%String link = "project.do?action=factor&projectId=" + projectId + "&which=" + factorId.longValue() + "&currentAuditId=" + currentAuditId;%>
										<a href="<%=link%>" class="nobottom"> <squale:mark
											name="factor" mark="currentMark" /> &nbsp; <squale:trend
											name="factor" current="currentMark"
											predecessor="predecessorMark" /> &nbsp;&nbsp; <squale:picto
											name="factor" property="currentMark" /> </a>
									</af:col>
								</logic:iterate>
							</af:cols>
						</af:table>
						
						</af:tab>
					</logic:notEqual>
				</logic:iterate>
			</af:form>	
			
			</af:tabbedPane>
			