<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>

<%@ page import="com.airfrance.squaleweb.resources.WebMessages"%>

<%
            // Le tooltip de l'image
            String imageDetails = WebMessages.getString( request, "image.roi.graph" );
%>

<af:page titleKey="roi.title">
	<af:body>
		<af:canvasCenter>

			<af:form action="roi.do" method="POST">

				<br />
				<div style="color: #f00;"><html:messages id="msg"
					message="true">
					<bean:write name="msg" />
					<br>
				</html:messages></div>
				<br />
				<center><%-- Le tableau d'informations --%>
				<table width="100%" class="tblh" cellpadding="0" cellspacing="0"
					border="0">
					<THEAD>
						<tr>
							<th colspan="2"><bean:message key="roi.results" /></th>
						</tr>
					</THEAD>
					<%-- Le ROI total sur 1 an (t-1an) en mhi --%>
					<tr class="fondClair">
						<td class="td1"><bean:message key="roi.total_in_unit" /></td>
						<td width="75%"><bean:write name="roiForm" property="total" />
						</td>
					</tr>
					<%-- Le ROI total sur 1 an (t-1an) en K€ --%>
					<tr>
						<td class="td1"><bean:message key="roi.total_in_keuros" /></td>
						<td width="85%"><bean:write name="roiForm"
							property="totalInKEuros" /></td>
					</tr>
				</table>

				<br />
				<br />

				<%-- On affiche le graphe si une formule a été définie, sinon on affiche un message pour le signaler--%>
				<logic:empty name="roiForm" property="formula">
					<bean:message key="roi.noformula" />
				</logic:empty> <logic:notEmpty name="roiForm" property="formula">
					<img src="roi.do?action=viewRoiChart" title="<%=imageDetails%>" />
				</logic:notEmpty> <br />
				<br />
				<br />

				</center>

				<%-- Les tableaux de configuration --%>

				<%-- Liste des applications --%>
				<table width="100%" class="formulaire" cellpadding="0"
					cellspacing="0" border="0">
					<THEAD>
						<tr>
							<th colspan="3"><bean:message key="roi.configuration" /></th>
						</tr>
					</THEAD>
					<tr>
						<td class="td1"><bean:message key="roi.application_filter" /></td>
						<td valign="middle"><af:select property="roiApplicationId">
							<af:option key="roi.all_applications" value="-1" />
							<logic:present
								name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
								scope="session">
								<logic:iterate id="appli"
									name="<%=com.airfrance.welcom.struts.util.WConstants.USER_KEY%>"
									property="applicationsList"
									type="com.airfrance.squaleweb.applicationlayer.formbean.component.ApplicationForm"
									scope="session">
									<%
									String id = "" + appli.getId();
									%>
									<af:option value="<%=id%>"><%=appli.getApplicationName()%></af:option>
								</logic:iterate>
							</logic:present>
						</af:select></td>
						<td><%-- 
								html et non tag welcom car sinon ne mais pas la bonne classe au bouton 
								et le bouton est décalé à droite
							--%> <input title="Valider la sélection" class="btn left white "
							type="button" name="valider"
							style="BACKGROUND-IMAGE: url(theme/charte_v03_001/img/ico/neutre/ok.gif)"
							onclick="execSubmit('roiForm','changeApplication',this);"
							value="Valider" /></td>
					</tr>

					<tr>
						<af:field key="roi.unit_in_keuros" property="KEuros" size="10"
							styleClassLabel="td1" />
						<td><af:button name="recalculate" callMethod="setKEuros"
							toolTipKey="toolTip.roi.recalculate" /></td>
					</tr>

					<tr>
						<af:field key="roi.formula" property="formula"
							styleClassLabel="td1" size="40" />
						<td><af:button name="modify" callMethod="updateFormula"
							toolTipKey="toolTip.roi.modify.formula" /></td>
					</tr>
				</table>
			</af:form>
		</af:canvasCenter>
	</af:body>
</af:page>