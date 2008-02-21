<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="com.airfrance.squalecommon.datatransfertobject.result.PracticeEvolutionDTO" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.EvolutionForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentListForm" %>
<%@ page import="com.airfrance.squaleweb.resources.WebMessages" %>
<%@ page import="com.airfrance.squaleweb.applicationlayer.action.results.project.EvolutionAction" %>

<bean:define id="projectId" name="evolutionForm" property="projectId" type="String" />
<bean:define id="currentAuditId" name="evolutionForm" property="currentAuditId" type="String" />
<bean:define id="previousAuditId" name="evolutionForm" property="previousAuditId" type="String" />

<bean:define id="results" name="evolutionForm" property="results" type="java.util.Map"/>
<%if(results.size() == 0){%>
	<bean:define id="emptyList" value="true" />
<%}%>

<%
// On récupère la valeur indiquant si le panel du filtre est ouvert ou non
String expandedFilter = (String) request.getParameter("filterPanel");
if (null == expandedFilter) {
    expandedFilter = "false";
}
%>
<af:page titleKey="evolution.components.title">
	<af:head>
		<%-- inclusion pour le marquage XITI --%>
		<jsp:include page="/jsp/xiti/xiti_header_common.jsp" />
		<script type="text/javascript" src="/squale/jslib/manage_tab.js"></script>
	</af:head>
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">	
	
		<%-- inclusion pour le marquage XITI spécifique à la page--%>
		<jsp:include page="/jsp/xiti/xiti_body_common.jsp">
			<jsp:param name="page" value="ConsultationExpert::Evolution" />
		</jsp:include>
		
		<af:canvasCenter>
			<br />
			<squale:resultsHeader name="evolutionForm" />
			<br/>
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="evolution.components.help" />
			</af:dropDownPanel>
			<br/>
			<af:form action="evolution.do" scope="session">
				<%-- les variables cachées pour les droits --%>
				<html:hidden name="evolutionForm" property="projectId"/>
				<html:hidden name="evolutionForm" property="currentAuditId"/>
				<html:hidden name="evolutionForm" property="previousAuditId"/>
				<input type="hidden" name="<%="filterPanel"%>"
					id="<%="filterPanel"%>" value="<%=expandedFilter%>" />
				<af:dropDownPanel titleKey="reference.filter" 
					expanded="<%=Boolean.valueOf(expandedFilter).booleanValue()%>"
					onExpand="changeVarValue('filterPanel', 'true');"
					onCollapse="changeVarValue('filterPanel', 'false');">
					<table width="50%" class="formulaire" cellpadding="0"
						cellspacing="0" border="0" align="center">
						<tr class="fondClair">
							<td><table><tr>
								<af:field key="empty" type="checkbox" 
											property='<%="filters["+PracticeEvolutionDTO.ONLY_UP_OR_DOWN_ID+"]"%>' />
								<td class="td1">
									<af:select property="filterOnlyUpOrDown">
										<af:option key="evolution.filter_up" 
											value='<%=PracticeEvolutionDTO.ONLY_UP%>' />
										<af:option key="evolution.filter_down" 
											value='<%=PracticeEvolutionDTO.ONLY_DOWN%>'/>
										<af:option key="evolution.filter_deleted" 
											value='<%=PracticeEvolutionDTO.DELETED%>'/>
									</af:select>
								</td>
							</tr></table></td>
						</tr>
						<tr class="fondClair">
							<td><table><tr>
								<af:field key="empty" type="checkbox" 
											property='<%="filters["+PracticeEvolutionDTO.THRESHOLD_ID+"]"%>'/>
								<td class="td1">
									<bean:message key="evolution.filter_threshold"/>&nbsp;
									<af:select property="comparisonSign">
										<af:option key="&lt;" value="&lt;" />
										<af:option key="&gt;" value="&gt;"/>
										<af:option key="=" value="="/>
									</af:select>
								</td>
								<af:field key="empty" property="threshold" size="10" isRequired="true"/>
							</tr></table></td>
						</tr>
						<tr class="fondClair">
							<td><table><tr>
								<af:field key="empty" type="checkbox" 
											property='<%="filters["+PracticeEvolutionDTO.ONLY_PRACTICES_ID+"]"%>' />
								<td class="td1">
									<bean:message key="evolution.filter_practices"/>
								</td>
							</tr></table></td>
						</tr>
						<tr class="fondClair">
							<td><table><tr><td>
									<af:select property="practices" multiple="true" 
												isRequired="true">
										<logic:iterate name="evolutionForm" property="availablePractices" id="practiceId" type="String">
											<af:option key="<%=practiceId%>" value='<%=practiceId.replaceFirst("rule.", "")%>' />
										</logic:iterate>
									</af:select>
									<%-- Champs caché juste pour conserver les données 
										dans le cas d'un wValidate --%>
									<af:select property="availablePractices" 
												style="display:none">
										<af:options property="availablePractices"/>
									</af:select>
							</td></tr></table></td>
						</tr>
					</table>
					<af:buttonBar>
						<af:button type="form" name="evolution.filter.button.validate" callMethod="list" toolTipKey="evolution.filter.toolTip.validate" />
					</af:buttonBar>
				</af:dropDownPanel>
				<br/>
				<bean:message key="evolution.details"/>
				<br/>
				<br/>
			
				<%-- Liste déroulante pour choisir le tri --%>
				<af:select property="sortType" onchange="form.action.value='list';form.submit()">
					<af:option key="evolution.group_by_components" value="<%=EvolutionForm.COMPONENT_FOR_KEY%>" />
					<af:option key="evolution.group_by_practices" value="<%=EvolutionForm.PRACTICE_FOR_KEY%>" />
				</af:select>
			
				<logic:notPresent name="emptyList">
					
					<%-- Message dans le cas où la limite des résultats affichables est atteinte--%>
					<logic:equal name="<%=EvolutionAction.DISPLAY_RESULTS_MSG%>" value="<%=Boolean.TRUE.toString()%>">
						<div class="exergue">
							<br/>
							<bean:message key="evolution.info.limit" arg0='<%=""+EvolutionAction.MAX_RESULTS%>'/>
							<br/><br/>
						</div>
					</logic:equal>
				
					<logic:equal name="evolutionForm" property="sortType" value="<%=EvolutionForm.COMPONENT_FOR_KEY%>">
						<%-- on veut un tri par composant --%>
						<af:table name="evolutionForm" property="keys" 
									emptyKey="table.results.none" pageLength="30">
							<af:cols id="component" idIndex="componentId">
								<bean:define id="compForm" name="component" type="com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm" />
								<bean:define id="currentName" name="component" property="name" type="String" />
								<bean:define id="type" name="component" property="type" type="String" />
								<af:col property="name" sortable="true" key="project.component.name">
									<%
										String titleKey = currentName + "(" + WebMessages.getString(request,type) + ")";
										// On récupère la valeur indiquant si le panel est ouvert ou non
										String expanded = (String) request.getParameter(titleKey + "Panel");
										if (null == expanded) {
    										expanded = "false";
										}
									%>
									<input type="hidden" name='<%=titleKey + "Panel"%>'
										id='<%=titleKey + "Panel"%>' value="<%=expanded%>" />
									<af:dropDownPanel titleKey="<%=titleKey%>"
														expanded="<%=Boolean.valueOf(expanded).booleanValue()%>"
														onExpand="<%=\"changeVarValue('\"+titleKey+\"Panel', 'true');\"%>"
														onCollapse="<%=\"changeVarValue('\"+titleKey+\"Panel', 'false');\"%>">
										<% ResultListForm practices = (ResultListForm)results.get(compForm); 
										   // On met comme nom practices + id pour pouvoir différencier les tableaux
										   // pour le tri welcom.		
										   pageContext.setAttribute("practices" + componentId, practices);
										%>
										<af:table name='<%="practices" + componentId%>' property="list" emptyKey="table.results.none" totalLabelKey="empty">
											<af:cols id="practice">
												<bean:define id="practiceName" name="practice" property="name" type="String" />
												<af:col property="name" sortable="true" key="practice.name">
													<html:link href='<%="/squale/project.do?action=practice&projectId="+projectId
																    +"&currentAuditId="+currentAuditId+"&previousAuditId="+previousAuditId%>' 
																    paramId="which" paramProperty="id" paramName="practice">
									    				<bean:message key="<%=practiceName%>" />
									    			</html:link>
												</af:col>
												<af:col property="predecessorMark" key="project.result.practice.value.previous" 
														sortable="true" type="Number">
													<squale:mark name="practice" mark="predecessorMark"/>
												</af:col>
												<af:col property="currentMark" key="project.result.practice.value" 
														sortable="true" type="Number">
													<squale:mark name="practice" mark="currentMark"/>
												</af:col>
												<af:col property="currentMark" width="100px" key="project.result.factor.tendance" >
													<squale:trend name="practice" current="currentMark" predecessor="predecessorMark" />
												</af:col> 
											</af:cols>
										</af:table>
									</af:dropDownPanel>
								</af:col>
							</af:cols>
						</af:table>
					</logic:equal>
					
					
					<logic:equal name="evolutionForm" property="sortType" value="<%=EvolutionForm.PRACTICE_FOR_KEY%>">
						<%-- on veut un tri par practiques --%>
						<af:table name="evolutionForm" property="keys" 
									emptyKey="table.results.none" pageLength="30">
							<af:cols id="practice" idIndex="idPractice">
								<bean:define id="resultForm" name="practice" type="com.airfrance.squaleweb.applicationlayer.formbean.results.ResultForm" />
								<bean:define id="currentName" name="practice" property="name" type="String" />
								<af:col property="name" sortable="true" key="practice.name">
									<%
										// On récupère la valeur indiquant si le panel est ouvert ou non
										String expanded = (String) request.getParameter(currentName + "Panel");
										if (null == expanded) {
    										expanded = "false";
										}
									%>
									<input type="hidden" name='<%=currentName + "Panel"%>'
										id='<%=currentName + "Panel"%>' value="<%=expanded%>" />
									<af:dropDownPanel titleKey="<%=currentName%>"
														expanded="<%=Boolean.valueOf(expanded).booleanValue()%>"
														onExpand="<%=\"changeVarValue('\"+currentName+\"Panel', 'true');\"%>"
														onCollapse="<%=\"changeVarValue('\"+currentName+\"Panel', 'false');\"%>">
										<% ComponentListForm components = (ComponentListForm)results.get(resultForm); 
										   // On met comme nom components + id pour pouvoir différencier les tableaux
										   // pour le tri welcom.		
										   pageContext.setAttribute("components" + idPractice, components);
										%>
										<af:table name='<%="components" + idPractice%>' property="list" emptyKey="table.results.none" totalLabelKey="empty">
											<af:cols id="component">
												<bean:define id="type" name="component" property="type" type="String" />
												<af:col property="name" key="project.component.name" sortable="true"
														href='<%="/squale/project_component.do?action=component&projectId="+projectId
																+"&currentAuditId="+currentAuditId+"&previousAuditId="+previousAuditId%>' 
																paramId="component" paramName="component" paramProperty="id"/>
												<af:col property="type" key="project.component.type" sortable="true">
													<bean:message key="<%=type%>"/>
												</af:col>
												<af:col property="practices.list[0].predecessorMark" 
														key="project.result.practice.value.previous" sortable="true" 
														type="Number">
													<squale:mark name="component" mark="practices.list[0].predecessorMark"/>
												</af:col>
												<af:col property="practices.list[0].currentMark" key="project.result.practice.value" 
														sortable="true" type="Number">
													<squale:mark name="component" mark="practices.list[0].currentMark"/>
												</af:col>
												<af:col property="practices.list[0].currentMark" width="100px" key="project.result.factor.tendance" >
													<squale:trend name="component" current="practices.list[0].currentMark" predecessor="practices.list[0].predecessorMark" />
												</af:col>
											</af:cols>
										</af:table>
									</af:dropDownPanel>
								</af:col>
							</af:cols>
						</af:table>
					</logic:equal>
				</logic:notPresent>
			</af:form>
			<%-- On exporte les résultats que si il y en a --%>
			<logic:notPresent name="emptyList">
				<h2><bean:message key="exports.title" /></h2><br/>
				<%-- 
					Il ne faut pas mettre les boutons dans le form
					sinon l'action se termine mal (null si l'on veut invoquer un nouvel export
					sans recharger la page
				--%>
				<af:buttonBar>
					<af:button type="form" name="export.pdf" 
							onclick='<%="evolution.do?action=exportPDF&projectId="
									+projectId+"&currentAuditId="
									+currentAuditId+"&previousAuditId="
									+previousAuditId%>'  
							toolTipKey="toolTip.export.pdf.project.evolution"/>
				</af:buttonBar>
			</logic:notPresent>
			<logic:present name="emptyList">
				<table>
					<tr>
						<td><bean:message key="table.results.none"/></td>
					<tr>
				</table>
			</logic:present>
		</af:canvasCenter>
	</af:body>
</af:page>