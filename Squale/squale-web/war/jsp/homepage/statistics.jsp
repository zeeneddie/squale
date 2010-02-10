<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>
<div class="homepage">
	<h2>
		<bean:message key="homepage.statistics.title"/>
	</h2>
	</br>
	<logic:empty name="homepageForm" property="volumetrie">
		<bean:message key="homepage.statistics.none" />
		<br />
	</logic:empty>
	<logic:notEmpty name="homepageForm" property="volumetrie">
		<af:table name="homepageForm" property="volumetrie" displayNavigation="false" displayFooter="false">
			<af:cols id="element">
				<af:col property="applicationName" key="homepage.statistics.application"></af:col>
				<af:col property="projectName" key="homepage.statistics.project"></af:col>
				<af:col property="nbMethods" key="homepage.statistics.nbMethods"></af:col>
				<af:col property="nbClasses" key="homepage.statistics.nbClasses"></af:col>
				<af:col property="nbLineCode" key="homepage.statistics.lineCode"></af:col>
				<af:col property="nbLineComments" key="homepage.statistics.lineComments"></af:col>
			</af:cols>
		</af:table>
	</logic:notEmpty>
</div>
<br/>
<br/>