<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic"	prefix="logic"%>
<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>

<div class="homepage">
	<h2><bean:message key="homepage.result.title" /></h2>
	<br>
	<logic:empty name="homepageForm" property="list">
		<logic:empty name="homepageForm" property="graphMakerMap">
			<bean:message key="homepage.result.none" />
		</logic:empty>
	</logic:empty>	
	<logic:notEmpty name="homepageForm" property="list">
		<jsp:include page="resultByGrid.jsp"/>
		<br>
	</logic:notEmpty>
	<logic:notEmpty name="homepageForm" property="graphMakerMap">
		<af:tabbedPane name="kiviat">
			<af:tab name="kiviat" key="homepage.result.kiviatTitle" >
				<jsp:include page="resultKiviat.jsp"/>
			</af:tab>
		</af:tabbedPane>
	</logic:notEmpty>
</div>
<br/>
<br/>