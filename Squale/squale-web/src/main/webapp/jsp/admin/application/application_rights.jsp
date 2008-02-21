<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>

<%@ page import="java.util.Arrays" %>
<%@ page import="com.airfrance.squaleweb.util.SqualeWebConstants" %>
<%@ page import="org.apache.struts.action.ActionMessages" %>

<script type="text/javascript" src="/squale/jslib/format_page.js" ></script>

<%--
Configure les autorisations associées à l'application
--%>

<%
// On récupère la liste des profils possibles
pageContext.setAttribute("myRightProfiles", Arrays.asList(SqualeWebConstants.PROFILES));
// On construit le titre de la page en fonction de la création ou de la modification
//  de l'application
String modification = (java.lang.String) request.getParameter("modification");
String title = "application_creation.title";
if (modification != null) {
    // On modifie l'application
    pageContext.setAttribute("modification", modification);
    title = "application_modification.title";
}
%>

<bean:define id="applicationId" name="createApplicationForm"
	property="applicationId" type="String" />


<af:page>
	<af:body>
		<af:canvasCenter titleKey="<%=title%>"
			subTitleKey="application_creation.subtitle.rights">
			<bean:message key="application_creation.rights.details" />

			<div style="color: #f00"><html:messages id="message"
				property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
				message="true">
				<bean:write name="message" />
				<br />
			</html:messages> <br />
			</div>

			<af:form action="add_user.do">
				<input type="hidden"
					name="<%=com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction.DO_NOT_RESET_FORM%>"
					value="true">
				<table id="rights" width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<THEAD>
						<tr>
							<th width="180px" align="center"><bean:message
								key="application_creation.rights.matricule" /></th>
							<th><bean:message key="application_creation.rights.profile" /></th>
						</tr>
					</THEAD>
					<%-- ligne en double non affiché pour permettre la reproduction d'une ligne vide --%>
					<tr style="display: none">
						<td align="center"><af:field key="empty" property="matricule"
							writeTD="false" value="" /></td>
						<td><af:select property="rightProfile" lazyLoading="false">
							<logic:iterate name="myRightProfiles" id="myRightProfile"
								type="java.lang.String">
								<af:option value="<%=myRightProfile%>">
									<bean:message key="<%=myRightProfile%>" />
								</af:option>
							</logic:iterate>
						</af:select></td>
					</tr>
					<%-- On itére sur les utilisateurs de l'application --%>
					<logic:iterate name="createApplicationForm" id="user"
						property="rights">
						<bean:define name="user" property="key" id="userMatricule"
							type="String" />
						<tr>
							<td align="center"><af:field key="empty" property="matricule"
								writeTD="false" value="<%=userMatricule%>" /></td>
							<td><bean:define name="user" property="value" id="right"
								type="String" /> <af:select property="rightProfile"
								lazyLoading="false">
								<logic:iterate name="myRightProfiles" id="myRightProfile"
									type="java.lang.String">
									<option value="<%=myRightProfile%>"
										<%if (myRightProfile.equals(right)) {
    out.print("selected");
}%>>
									<bean:message key="<%=myRightProfile%>" /></option>
								</logic:iterate>
							</af:select></td>
						</tr>
					</logic:iterate>
					<%-- Si il n'y a pas d'utilisateurs, on met un champ manager obligatoire --%>
					<bean:size name="createApplicationForm" property="rights" id="nbUsers" />
					<logic:equal name="nbUsers" value="0">
						<tr>
							<td align="right"><af:field key="empty" property="matricule"
								writeTD="false" value="" /></td>
							<td><af:select property="rightProfile" lazyLoading="false">
								<logic:iterate name="myRightProfiles" id="myRightProfile"
									type="java.lang.String">
									<af:option value="<%=myRightProfile%>">
										<bean:message key="<%=myRightProfile%>" />
									</af:option>
								</logic:iterate>
							</af:select></td>
						</tr>
					</logic:equal>
				</table>
				<af:buttonBar>
					<input type="hidden" name="applicationId"
						value="<%=applicationId%>">
					<logic:equal name="modification" value="true">
						<input type="hidden" name="modification" value="<%=modification%>">
					</logic:equal>
					<af:button type="form" name="retour" toolTipKey="toolTip.retour"
						onclick="<%=\"location.href='utilLink.do?action=configApplication&applicationId=\"+applicationId+\"&modification=\"+modification+\"'\"%>" />

					<af:button type="form" name="add.user"
						toolTipKey="toolTip.add.user" onclick="addField('rights', 1);"
						singleSend="false" />
					<af:button type="form" name="valider" toolTipKey="toolTip.valider"
						callMethod="addRights" />
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>