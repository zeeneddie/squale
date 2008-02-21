<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@page import="com.airfrance.squalecommon.datatransfertobject.transform.stats.StatsTransform" %>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="stats.page.title">
			<bean:define id="profiles" name="setOfStatsForm" property="listOfProfilsStatsForm" />
			<bean:define id="sites" name="setOfStatsForm" property="listOfSiteStatsForm" />
			<bean:define id="audits" name="setOfStatsForm" property="auditsStatsForm" />
			<bean:size id="nbProfiles" name="profiles" />
			<bean:size id="nbSites" name="sites" />
			<B><bean:message key="stats.byApplis"/></B>
			<br /><br />
			<table class="tblh">
				<thead>
					<tr>
						<th><bean:message key="stats.applis.audits" /></th>
						<th colspan="<%=nbSites.intValue()+1%>"><bean:message key="stats.nb" /></th>
					</tr>
				</thead>
				<tfoot>
					<tr><td colspan="<%=nbSites.intValue()+1%>">&nbsp;</td></tr>
				</tfoot>
				<tr>
					<td>
						<bean:message key="applis" />
					</td>	
					<logic:iterate name="sites" id="siteName">
						<logic:equal name="siteName" property="serveurForm.serveurId" value='<%= StatsTransform.STATS_TOTAL_ID + "" %>' >
							<td>
								<bean:write name="siteName" property="nbAppliWithAuditsSuccessful" />
							<td>
						</logic:equal>
					</logic:iterate>
				</tr>
				<tr class="fond clair">
					<td>
						<bean:message key="audits"/>
					</td>
					<td>
						<bean:write name="audits" property="nbSuccessfuls" />
					<td>
				</tr>						
			</table>
			<br />
			<B><bean:message key="stats.byProfile"/></B>
			<br /><br />
			<table class="tblh">
				<thead>
					<tr>
						<th><bean:message key="stats.profileNames"/></th>
						<logic:iterate name="profiles" id="profilesNames">
							<th align="right">
								<bean:write name="profilesNames" property="profileName"/>
							</th> 	
						</logic:iterate>
					</tr>
				</thead>
				<tfoot>
					<tr><td colspan="<%=nbProfiles.intValue()+1%>">&nbsp;</td></tr>
				</tfoot>
				<tbody>
					<tr>
						<td><bean:message key="stats.nbProjects"/></td>
						<logic:iterate name="profiles" id="profilesNb">
							<td align="right">
								<bean:write name="profilesNb" property="nbProjects"/>
							</td> 	
						</logic:iterate>
					</tr>
					<tr class="clair">
						<td> 
							<bean:message key="stats.loc"/>
						</td>
						<logic:iterate name="profiles" id="profilesLoc">
							<td align="right">
								<bean:write name="profilesLoc" property="loc"/>
							</td> 	
						</logic:iterate>
					</tr>
				</tbody>
			</table>
		</af:canvasCenter>
	</af:body>
</af:page>