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
				<div id='<%="up"+indexValue%>'>
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_U.gif" onclick='<%="move("+indexValue+",'up')"%>' style="cursor:pointer" titleKey="homepage_management.order.up"/>
				</div>
				<div  id='<%="down"+indexValue%>'>
					<html:img src="theme/charte_v03_001/img/picto/blanc/arTr_D.gif" onclick='<%="move("+indexValue+",'down')"%>' style="cursor:pointer" titleKey="homepage_management.order.down"/>
				</div>
				<input type="hidden" id='<%="htmlText"+indexValue%>' value="newsId" >
			</th>
			<th id='<%="title"+indexValue%>' class="height_col" onclick='<%="show('div_position"+indexValue+"')"%>' style="cursor:pointer">
				<bean:message key="homepage_management.news.newsTitle" />
			</th>
		</tr>
	</thead>
</table>
<div id='<%="div_position"+indexValue%>'>					
	<table class="formulaire" >
		<tbody >
			<tr>
				<td class="check_col">
					<html:checkbox property="newsCheck" styleId="newsId" onclick="modifyTableOrder('newsId')"/>
					<html:hidden property="positionNews" styleId="position_newsId" value='<%=indexValue%>'></html:hidden>
				</td>
				<td>
					<bean:message key="homepage_management.news.news" />
				</td>
			</tr>
		</tbody>
	</table>
</div>
