<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>


<af:page >
		<af:canvasPopup titleKey="qualimetric_element.title" >
			<br />
			<br />
			<jsp:include page="/jsp/results/project/information_common.jsp">
				<jsp:param name="expandedDescription" value="true"/>
			</jsp:include>
		</af:canvasPopup>
</af:page>