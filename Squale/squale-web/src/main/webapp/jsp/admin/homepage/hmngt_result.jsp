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
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_D.gif" onclick='<%="move("+indexValue+",'down')"%>' style="cursor:pointer" titleKey="homepage_management.order.down"/>
				</div>
				<input type="hidden" id="<%="htmlText"+indexValue%>" value="resultId">
			</th>
			<th id="<%="title"+indexValue%>" class="height_col" onclick="<%="show('div_position"+indexValue+"')"%>" style="cursor:pointer">
				<bean:message key="homepage_management.results.resultTitle" />
			</th>
		</tr>
	</thead>
</table>
<div id="<%="div_position"+indexValue%>">
	<table class="formulaire">
		<tbody>
			<tr>
				<td class="check_col">
					<html:checkbox property="resultCheck" styleId="resultId" onclick="checkResult()"/>
					<html:hidden property="positionResult" styleId="position_resultId" value="<%=indexValue%>" ></html:hidden>
				</td>
				<td colspan="2">
					<bean:message key="homepage_management.results.results" />
				</td>
			</tr>
			<tr class="clair">
				<td class="check_col">
					
				</td>
				<td class="check_col">
					<html:checkbox property="resultByGridCheck" styleId="resultByGridId"/>
				</td>
				<td>
					<bean:message key="homepage_management.results.byGrid" />
				</td>
			</tr>
			<tr>
				<td class="check_col"/>
				<td class="check_col">
					<html:checkbox property="resultKiviatCheck" styleId="resultKiviatId" onclick="displayKiviat()"/>
				</td>
				<td>
					<bean:message key="homepage_management.results.kiviat" />
				</td>
			</tr>
			<tr>
				<td colspan="2"/>
				<td>
					<bean:message key="homepage_management.results.kiviatWidth" />
					<html:text property="kiviatWidth" styleId="kiviatWidthId" size="4" maxlength="4"></html:text>
				</td>
			</tr>
		</tbody>
	</table>
</div>