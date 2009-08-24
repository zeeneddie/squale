<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="/squale" prefix="squale"%>

<%@ page import="org.squale.squaleweb.resources.WebMessages" %>
<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO"%>

<script type="text/javascript" src="theme/charte_v03_001/js/manualMark.js"></script>
<script type="text/javascript" src="theme/charte_v03_001/js/filtering.js"></script>

<script type="text/javascript" src="jslib/jquery.js"></script>
<script type="text/javascript" src="jslib/jquery-ui.js"></script>

<bean:define id="projectId" name="manualMarkForm" property="projectIdSafe" type="String" />
<bean:size id="listSize" name="manualMarkForm" property="manualPracticeList"  />
<bean:define id="localOutOfDate"name="manualMarkForm" property="outOfDate" />
	
<af:page titleKey="manualMark.title">
	<af:body canvasLeftPageInclude="/jsp/canvas/project_menu.jsp">
		<af:canvasCenter>
			<br />
			<%-- Squale cartridge --%>
			<squale:resultsHeader name="projectSummaryForm" displayComparable="false"/>
			<br />
			<br />
			<af:dropDownPanel titleKey="buttonTag.menu.aide">
				<bean:message key="manualMark.help" />
			</af:dropDownPanel>
			<%-- Error message when validation failed --%>
			<div id="errorMessage">
				<logic:messagesPresent>
					<html:messages id="msg">
						<div style="color: #f00" id="msgErr">
							<br/>
							<bean:write name="msg" filter="false" />
						</div>
					</html:messages>
				</logic:messagesPresent>
			</div>
			<af:form action="manual_mark_management.do" >
				<br/>
				<bean:message key="manualMark.search" />
				<br/>
				<input type="text" onkeyup="filter(this, event);lineStyle(<%=listSize%>);"  name="search" size="60"/>
				<br/>
				<br/>
				<div style="display:none">
					<af:field key="empty" styleId="tprValue"  property="temporValue" />
					<af:field key="empty" property="temporDate" type="DATE" dateFormatKey="date.format.simple"/>
					<af:field key="empty" styleId="editPlace" property="editLine" ></af:field>
					<af:field key="empty" styleId="tprComment" property="temporComments"></af:field>
				</div>
				<table class = "formulaire">
					<thead >
						<tr>
							<th class="check_col"/>
							<th><bean:message key="manualMark.table.name" /></th>
							<th><bean:message key="manualMark.table.mark" /></th>
							<th><bean:message key="manualMark.table.creationDate" /></th>
							<th><bean:message key="manualMark.table.comments" /></th>
							<th><bean:message key="manualMark.table.timelimitation" /></th>
							<th><bean:message key="manualMark.table.timeleft" /></th>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="elt" indexId="index" name="manualMarkForm" property="manualPracticeList" indexId="index" >
							<tr class="" id="li-<%=index%>">
								<%-- "Check" column. Display only if the user as enough rights --%>
								<td class="check_col">
									<logic:equal name="manualMarkForm" property="canModify" value="true">
										<div id="edit-<%=index%>" >
											<img border="0" src="theme/charte_v03_001/img/ico/neutre/pen.gif" onclick="edit(<%=index%>,<%=listSize%>,true)" style="cursor:pointer"/>
										</div>
										<div id="canceledit-<%=index%>" style="display:none">
											<img border="0" src="theme/charte_v03_001/img/ico/neutre/cross.gif" onclick="canceledit(<%=index%>,<%=listSize%>)" style="cursor:pointer"/>
										</div>
									</logic:equal>
								</td>
								<%-- Column name of the manual practice --%>
								<td>
									<af:write name="elt" property="name"/>
								</td>
								<%-- Column mark of the manual practice --%>
								<td>
									<div id="<%="val-" + index%>" >
										<squale:mark name="elt" mark="value" />
										<squale:picto name="elt" property="value" />
									</div>
								</td>
								<%-- Column Creation date of the manual practice --%>
								<td>
									<div id='<%="date-" + index%>'>
										<af:write name="elt" property="creationDate" dateFormatKey="date.format.simple"/>
									</div>
								</td>
								<%-- Column comments of the manual practice --%>
								<td>
									<logic:notEmpty name="elt" property="comments">
										<div id="showHideCom-<%=index%>">
											<img border="0" src="theme/charte_v03_001/img/ico/neutre/oeil.gif" onclick="showHideComments(<%= index %>)" style="cursor:pointer" title='<bean:message key="manualMark.table.eyePict.title" />'/>
										</div>
										<div id='<%="comment-" + index%>' class="manualMarkComment" style="display: none; ">
											<div class="manualMarkCommentText">
												<af:write name="elt" property="comments" />
											</div>
											<div class="manualMarkCommentBottom">
												<img border="0" src="theme/charte_v03_001/img/ico/neutre/cross.gif" onclick="showHideComments(<%= index %>)" style="cursor:pointer"/>
											</div>
										</div>
									</logic:notEmpty>
								</td>
								<%-- Column validity period of the mark --%>
								<td>
									<af:write name="elt" property="timeLimitationParse" />
								</td>
								<%-- Column time left for the validity of the mark --%>
								<td id='<%="timeleft-" + index%>'>
									<af:write name="elt" property="timeleft"  />
								</td>
							</tr>
							<%-- Row added to display the modification form --%>
							<tr class="" id="rowform-<%=index%>">
								<td></td>
								<td></td>
								<td colspan="5">
									<div id='<%="editform-" + index%>' style="display:none">
										<fieldset class="manualMarkFieldset"><legend><bean:message key="manualMark.table.form.legend" /></legend>
											<%-- champ mark --%>
											<div class="manualMarkFormLabel">
												<bean:message key="manualMark.table.form.mark"/>
											</div>
											<div class="manualMarkFormInput">
												<af:field key="empty" name="manualMarkForm" styleId='<%="editvalValue-"+index%>'
												property='<%="manualPracticeList[" + index + "].value"%>'
												 isRequired="true" writeTD="false" />
											 </div>
											 <%-- champ date --%>
											<div class="manualMarkFormLabel"> 
												<bean:message key="manualMark.table.form.creationDate"/>
											</div>
											<div class="manualMarkFormInput">
												<af:field key="empty" name="manualMarkForm" 
												property='<%="manualPracticeList[" + index + "].creationDate"%>'
												type="DATE" dateFormatKey="date.format.simple" isRequired="true" writeTD="false"  />
											</div>
											<%-- champ commentaire --%>
											<div class="manualMarkFormLabel">
												<bean:message key="manualMark.table.form.comments"/>
											</div>
											<div class="manualMarkFormInput">
												<af:field name="manualMarkForm" key="empty" styleId='<%="editComm-"+index%>'
												property='<%="manualPracticeList[" + index + "].comments"%>'
												isRequired="true" cols="30" rows="5" maxlength="4000" writeTD="false" type="TEXTAREA" />
											</div>
											<%-- Button only display if the user has enough right --%>
											<logic:equal name="manualMarkForm" property="canModify" value="true">
												<div class="manualMarkFormButton">
													<af:button type="form" callMethod="saveResult" name="valider" toolTipKey="toolTip.valider" />
												</div>
											</logic:equal>
										</fieldset>
									</div>
								</td>
							</tr>
						</logic:iterate>
						<%-- Script to do after that the table has been filled
						     This script will give the good background color for each line
						     and set in a red font the lines which contains out of date mark --%>
						<script type="text/javascript" >
							onLoadDisplay(<%=listSize%>,'<%=localOutOfDate%>');
						</script>
					</tbody>
					<tfoot>
						<tr><td colspan="7">&nbsp;</td></tr>
					</tfoot>
				</table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>