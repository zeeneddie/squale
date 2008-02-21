<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>

<af:page>
	<af:body>
		<af:canvasCenter titleKey="portail.configerror.title">
			<br />
			<div style="color:#f00;">
				<html:errors/>
			</div>
		</af:canvasCenter>
	</af:body>
</af:page>

