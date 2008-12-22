<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<!-- Recovery of the index value -->
<% String indexValue = (String) request.getParameter( "index" );%>

<table class="formulaire" >
	 <thead>
		<tr>
			<th class="check_col">
				<div id="<%="up"+indexValue%>">
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_U.gif" onclick='<%="move("+indexValue+",'up')"%>' style="cursor:pointer" titleKey="homepage_management.order.up"/>
				</div>
				<div id="<%="down"+indexValue%>">
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_D.gif" onclick='<%="move("+indexValue+",'down')"%>' style="cursor:pointer" titleKey="homepage_management.order.down"/>
				</div>
				<input type="hidden" id="<%="htmlText"+indexValue%>" value="introId" >
			</th>
			<th id="<%="title"+indexValue%>" class="height_col" onclick="<%="show('div_position"+indexValue+"')"%>" style="cursor:pointer">
				<bean:message key="homepage_management.intro.introTitle" />
			</th>
		</tr>
	</thead>
</table>
<div id="<%="div_position"+indexValue%>">
	<table class="formulaire">
		<tbody>
			<tr>
				<td class="check_col">
					<html:checkbox property="introCheck" styleId="introId" onclick="modifyTableOrder('introId')"/>
					<html:hidden property="positionIntro" styleId="position_introId" value="<%=indexValue%>"></html:hidden>
				</td>
				<td>
					<bean:message key="homepage_management.intro.intro"  />
				</td>
			</tr>
		</tbody>
	</table>
</div>