<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<!-- Recovery of the index value -->
<% String indexValue = (String) request.getParameter( "index" );%>

<table class="formulaire" >
	 <thead>
		<tr>
			<th class="check_col">
				<div id="<%="up"+indexValue%>">
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_U.gif" onclick='<%="move("+indexValue+",'up')"%>' style="cursor:pointer" titleKey="homepage_management.order.up" />
				</div>
				<div id="<%="down"+indexValue%>">
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_D.gif" onclick='<%="move("+indexValue+",'down')"%>' style="cursor:pointer" titleKey="homepage_management.order.down" />
				</div>
				<input type="hidden" id="<%="htmlText"+indexValue%>" value="auditId">
			</th>
			<th id="<%="title"+indexValue%>" class="height_col" onclick="<%="show('div_position"+indexValue+"')"%>" style="cursor:pointer">
				<bean:message key="homepage_management.audits.auditTitle" />
			</th>
		</tr>
	</thead>
</table>
<div id="<%="div_position"+indexValue%>">
	<table class="formulaire">
		<tbody id="body3">
		
	<!-- display audit -->	
			<tr>
				<td class="check_col">
					<html:checkbox property="auditCheck" styleId="auditId" onclick="checkAudit()" />
					<html:hidden property="positionAudit" styleId="position_auditId" value="<%=indexValue%>"></html:hidden>
				</td>
			
				<td colspan="3">
					<bean:message key="homepage_management.audits.audits" />
				</td>
				<!-- <td></td>
				<td></td> -->
			</tr>
	
	<!-- Display audit scheduled -->
			<tr class="clair">
				<td class="check_col">
				
				</td>
				<td class="check_col">
					<html:checkbox property="auditScheduledCheck" styleId="auditScheduledId" onclick="uncheckedShowSeparately()"/>
				</td>
				<td colspan="2">
					<bean:message key="homepage_management.audits.auditScheduled" />
				</td>
				<%--<td></td>--%>
			</tr>
			
		
			<tr>
				<td class="check_col"></td>
				<td class="check_col"></td>
				<td></td>
			<!-- display audit successful -->	
				<td>
					<html:checkbox property="auditSuccessfullCheck" styleId="auditSuccessfullId"/>
					<bean:message key="homepage_management.audits.auditDone.successful" />
				</td>
			</tr>
			<tr>
				<td class="check_col"></td>
	<!-- display audit done -->
				<td class="check_col">
					<html:checkbox property="auditDoneCheck" styleId="auditDoneId" onclick="displayDone()"/>
				</td>
				<td>
					<bean:message key="homepage_management.audits.auditDone" />
				</td>
			<!-- Display partial audit -->
				<td>
					<html:checkbox property="auditPartialCheck" styleId="auditPartialId" />
					<bean:message key="homepage_management.audits.auditDone.partial" />
				</td>
			</tr>
			<tr>
				<td class="check_col"></td>
				<td class="check_col"></td>
				<td></td>
			<!-- Display audit failed -->
				<td>
					<html:checkbox property="auditFailedCheck" styleId="auditFailedId" />
					<bean:message key="homepage_management.audits.auditDone.failed" />
				</td>
			</tr>
		<!-- Nb jours -->
			<tr>
				<td class="check_col"></td>
				<td class="check_col"></td>
				<td>
					<bean:message key="homepage_management.audits.auditDone.nbJours" />
					<html:text property="auditNbJours" styleId="auditNbJoursId" size="3" maxlength="3"></html:text>
					<bean:message key="homepage_management.audits.auditDone.nbJoursFin" />
				</td>
				<td></td>
			</tr>
		<!-- Show separately -->
			<tr class="clair">
				<td class="check_col"/>
				<td class="check_col" >
					<html:checkbox property="auditShowSeparatelyCheck" styleId="auditShowSeparatelyId" onclick="auditScheduledAndDone()"/>
				</td>
				<td colspan="2">
					<bean:message key="homepage_management.audits.auditShowSeparately" />
				</td>
			</tr>
		</tbody>
	</table>
</div>