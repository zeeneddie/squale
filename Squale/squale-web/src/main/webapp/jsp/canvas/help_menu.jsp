<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>


<af:canvasLeft>
	<af:menu>
		<af:menuItem key="help.qualimetry.title" color="2"
			action="utilLink.do?action=goodPractice&title=qualimetry" />
		<af:menuItem key="help.portal.title" color="2"
			action="utilLink.do?action=goodPractice&title=portal" />
		<af:menuItem key="help.demarche.title" color="2"
			action="utilLink.do?action=goodPractice&title=demarche" />
		<af:menuItem key="help.factor.title" color="2" 
			action="utilLink.do?action=goodPractice&title=factor" />
		<af:menuItem key="help.criterium.title" color="2"
			action="utilLink.do?action=goodPractice&title=criterium" />
		<af:menuItem key="help.practice.title" color="2" 
			action="utilLink.do?action=goodPractice&title=practice" />
		<af:menuItem key="help.metric.title" color="2"
			action="utilLink.do?action=goodPractice&title=metric" />		
	</af:menu>
	<jsp:include page="left_common.jsp" flush="true"/>
</af:canvasLeft>
