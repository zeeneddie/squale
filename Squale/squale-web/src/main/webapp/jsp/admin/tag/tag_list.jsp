<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<%--
Affiche la liste des tags existants fontion administrateur
--%>

<bean:define id="callbackUrl">
	<html:rewrite
		page="//manageTags.do?action=findTagCategoryForAutocomplete" />
</bean:define>
<script type="text/javascript">
	function revealButton(divId)
	{
		var buttons = document.getElementById(divId);
		if (buttons.style.display == "none")
		{
			buttons.style.display = "";
		}
		else 
		{
			buttons.style.display = "none";
		}
	}
	
	function confirmDelTagWindow(message)
	{
		if (confirm(message)){
			window.location = "manageTags.do?action=deleteTag";
		}
	}
</script>
<af:page>
	<af:body>
		<af:canvasCenter titleKey="tag_admin.title">
			<af:form action="manageTags.do">
				<br />
				<!-- Parcours de la liste des tags et affichage de ceux-ci -->
				<div class="doubleBarre" style="z-index:10;">
					<af:table name="createTagForm" property="tags" totalLabelPos="none"
						emptyKey="table.results.none" pageLength="10">
						<af:cols id="tag" idIndex="indexTag">
							<af:colSelect property="selected"/>
							<af:col property="name" href='<%= "manageTags.do?action=selectTagToModify&index=" + indexTag %>'
								sortable="true" key="tag.name">
							</af:col>
							<af:col property="description" key="tag.description"
								sortable="true"/>
							<af:col property="categoryForm.name" key="tag.category.name"
								sortable="true"/>
						</af:cols>
						<af:buttonBar>
							<% 
								//<af:button callMethod="deleteTag" name="supprimer" />
								// On construit le lien pour le bouton supprimer
								String action = "manageTags.do?action=delTag" ;
								String href = "location.href=\'" + action + "\'";
								//onclick="<%=href% >"
								//onclick='<%= "javascript:confirmDelTagWindow('"+ messageDeleteTag + "')"% >'
							%>
							<af:button onclick="javascript:revealButton('gray');javascript:revealButton('confirmDeleteTags');" name="supprimer" />
							<font color="black">(<bean:message key="tag.message.delete" />)</font>
						</af:buttonBar>
					</af:table>
<%-- DEBUT DU CODE POUR AFFICHER LA DIV GRISEE SUR TOUT L'ECRAN ET LA DIV DE CONFIRMATION AU CENTRE --%>
<%-- LA TAILLE DU CADRE GRIS : "height:2000px; width:2000px;" est mis en dure et non en 100% de la page, car IE l'interprete très mal à ce niveau de la page--%>
					<div id="gray" style="z-index:1;position:absolute; top:0; left:0;height:2000px; width:2000px;display:none;background-color:#a0a0a0;-moz-opacity:0.38; opacity:0.38; filter: Alpha(opacity=38); -khtml-opacity:0.38;" >
					</div>
					<div id="confirmDeleteTags" style="z-index:1000;display:none;position:absolute;left:400;top:400;background-color:#e4f0fa;-moz-opacity:1; opacity:1; filter:alpha(opacity=100); -khtml-opacity:1;">
						<center>
							<table bgcolor="#e4f0fa">
								<tr bgcolor="#e4f0fa">
									<td colspan="2" align="center">
										<font color="black"><b><bean:message key="tag.message.confirmDelete" /></b></font>
									</td>
								</tr>
								<tr bgcolor="#e4f0fa">
									<af:buttonBar>
										<td>
											<af:button callMethod="deleteTag" name="yes" />
										</td>
										<td>
											<af:button onclick="javascript:revealButton('gray');javascript:revealButton('confirmDeleteTags');" name="no" />
										</td>
									</af:buttonBar>
								</tr>	
							</table>
						</center>
					</div>
<%-- FIN DU CODE POUR AFFICHER LA DIV GRISEE SUR TOUT L'ECRAN ET LA DIV DE CONFIRMATION AU CENTRE --%>
					<br />
					<logic:equal name="createTagForm" property="tagModified" value="true">
						<b><bean:message key="tag_admin.modify.tag" /></b>
					</logic:equal>
					<logic:equal name="createTagForm" property="tagModified" value="false">
						<b><bean:message key="tag_admin.add.tag" /></b>
					</logic:equal>
					<logic:equal name="createTagForm" property="pbTag" value="true">
						<bean:define id="badTagName" name="createTagForm" property="pbTagName" type="String" />
						<font color="red"><b><center><%=" \"" + badTagName + "\" " %><bean:message key="tag.message.badTagName" /></center></b></font>
					</logic:equal>
					<logic:equal name="createTagForm" property="pbTagCategory" value="true">
						<bean:define id="badTagCategory" name="createTagForm" property="pbTagCategoryName" type="String" />
						<font color="red"><b><center><%=" \"" + badTagCategory + "\" " %><bean:message key="tag.category.message.badCategory" /></center></b></font>
					</logic:equal>
					<af:buttonBar>
						<table>
							<tr class="fondClair">
								<td>
									<font color="black"><bean:message key="tag.name" />&nbsp;:</font>
									<af:field writeTD="false" key="empty" name="createTagForm" property="name"/>
								</td>
								<td>
									<font color="black"><bean:message key="tag.description" />&nbsp;:</font>
									<af:field writeTD="false" key="empty" name="createTagForm" property="description"/>
								</td>
								<td>
									<font color="black"><bean:message key="tag.category.name" />&nbsp;</font>
									(<bean:message key="tag.category.suggested" />)&nbsp;:
									<af:field writeTD="false" key="empty" name="createTagForm" property="categoryName"
										easyCompleteCallBackUrl="<%=callbackUrl%>" />
								</td>
								<td>
									<logic:equal name="createTagForm" property="tagModified" value="true">
										<af:button callMethod="modifyTag" name="modify" />
										<af:button callMethod="resetModifyTag" name="annuler" />
										<div class="hidden" style="display:none;">
											<af:field writeTD="false" key="empty" name="createTagForm" property="tagIndex"/>
										</div>
									</logic:equal>
									<logic:equal name="createTagForm" property="tagModified" value="false">
										<af:button callMethod="addTag" name="validate" />
									</logic:equal>
								</td>
							<tr>
						</table>
					</af:buttonBar>	
				</div>
				<br />
				<br />
				<!-- Parcours de la liste des catégories de tags et affichage de celles-ci -->
				<div class="doubleBarre" style="z-index:10;">
					<af:table name="createTagForm" property="tagCategories" totalLabelPos="none"
						emptyKey="table.results.none" pageLength="10">
						<af:cols id="tagCategory" idIndex="indexCat">
							<af:colSelect property="selected"/>
							<af:col property="name"
								href='<%= "manageTags.do?action=selectTagCategoryToModify&index=" + indexCat %>'
								sortable="true" key="tag.category.name" />
							<af:col property="description" key="tag.category.description"
								sortable="true"/>
						</af:cols>
						<af:buttonBar>	
							<af:button onclick="javascript:revealButton('gray2');javascript:revealButton('confirmDeleteCategories');" name="supprimer" />
							<font color="black">(<bean:message key="tag.category.message.delete" />)</font>
						</af:buttonBar>
					</af:table>
					<br />
<%-- DEBUT DU CODE POUR AFFICHER LA DIV GRISEE SUR TOUT L'ECRAN ET LA DIV DE CONFIRMATION AU CENTRE --%>
<%-- LA TAILLE DU CADRE GRIS : "height:2000px; width:2000px;" est mis en dure et non en 100% de la page, car IE l'interprete très mal à ce niveau de la page--%>
					<div id="gray2" style="z-index:1;position:absolute; top:0; left:0;height:2000px; width:2000px;display:none;background-color:#a0a0a0;-moz-opacity:0.38; opacity:0.38; filter: Alpha(opacity=38); -khtml-opacity:0.38;" >
					</div>
					<div id="confirmDeleteCategories" style="z-index:100000;display:none;position:absolute;left:400;top:400;background-color:#e4f0fa;-moz-opacity:1; opacity:1; filter:alpha(opacity=100); -khtml-opacity:1;">
						<center>
							<table bgcolor="#e4f0fa">
								<tr bgcolor="#e4f0fa">
									<td colspan="2" align="center">
										<font color="black"><b><bean:message key="tag.category.message.confirmDelete" /></b></font>
									</td>
								</tr>
								<tr bgcolor="#e4f0fa">
									<af:buttonBar>
										<td>
											<af:button onclick="javascript:revealButton('confirmDeleteCategories');javascript:revealButton('deleteTagsButtons');" name="yes" />
										</td>
										<td>
											<af:button onclick="javascript:revealButton('gray2');javascript:revealButton('confirmDeleteCategories');" name="no" />
										</td>
									</af:buttonBar>
								</tr>	
							</table>
						</center>
					</div>
					<div id="deleteTagsButtons" style="z-index:100000;display:none;position:absolute;left:400;top:400;background-color:#e4f0fa;-moz-opacity:1; opacity:1; filter:alpha(opacity=100); -khtml-opacity:1;">
					<center>
						<table bgcolor="#e4f0fa">
							<tr bgcolor="#e4f0fa">
								<td colspan="2" align="center">
									<b><bean:message key="tag.category.message.deleteAllTags" /></b>
								<td>
							</tr>
							<tr bgcolor="#e4f0fa">
								<af:buttonBar>
									<td align="right">
										<af:button callMethod="deleteTagCategoryAndTags" name="yes" />
									</td>
									<td align="left">
										<af:button callMethod="deleteTagCategoryNotTags" name="no" />
									</td>
								</af:buttonBar>
							</tr>
						</table>
					</center>
					</div>
<%-- FIN DU CODE POUR AFFICHER LA DIV GRISEE SUR TOUT L'ECRAN ET LA DIV DE CONFIRMATION AU CENTRE --%>
					<logic:equal name="createTagForm" property="tagCategoryModified" value="true">
						<b><bean:message key="tag_admin.modify.tagCategory" /></b>
					</logic:equal>
					<logic:equal name="createTagForm" property="tagCategoryModified" value="false">
						<b><bean:message key="tag_admin.add.tagCategory" /></b>
					</logic:equal>
					<logic:equal name="createTagForm" property="pbCategory" value="true">
						<bean:define id="badCategoryName" name="createTagForm" property="pbCategoryName" type="String" />
						<font color="red"><b><center><%=" \"" + badCategoryName + "\" " %><bean:message key="tag.category.message.badCategoryName" /></center></b></font>
					</logic:equal>
					<af:buttonBar>
						<table>
							<tr class="fondClair">
								<td>
									<font color="black"><bean:message key="tag.category.name" />&nbsp;:</font>
									<af:field writeTD="false" key="empty" name="createTagForm" property="tagCatName"/>
								</td>
								<td>
									<font color="black"><bean:message key="tag.category.description" />&nbsp;:</font>
									<af:field writeTD="false" key="empty" name="createTagForm" property="tagCatDescription"/>
								</td>
								<td>
									<logic:equal name="createTagForm" property="tagCategoryModified" value="true">
										<af:button callMethod="modifyTagCategory" name="modify" />
										<af:button callMethod="resetModifyTagCategory" name="annuler" />
										<div class="hidden" style="display:none;">
											<af:field writeTD="false" key="empty" name="createTagForm" property="tagIndex"/>
										</div>
									</logic:equal>
									<logic:equal name="createTagForm" property="tagCategoryModified" value="false">
										<af:button callMethod="addTagCategory" name="validate" />
									</logic:equal>
								</td>
							<tr>
						</table>
					</af:buttonBar>
				</div>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>