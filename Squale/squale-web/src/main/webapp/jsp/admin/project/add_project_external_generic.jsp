<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@taglib uri="http://www.squale.org/welcom/tags-welcom" prefix="af"%>

<%@ page import="org.squale.squalecommon.enterpriselayer.businessobject.profile.ProfileBO" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.Iterator"%>

<%@ page import="org.squale.squaleweb.taskconfig.FieldInfoConfig"%>
<%@ page import="org.squale.squaleweb.taskconfig.AbstractConfigTask"%>


<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<bean:define id="projectId" name="createProjectForm"
	property="projectId" type="String" />
<bean:define id="applicationId" name="createProjectForm"
	property="applicationId" type="String" />



<%-- On va interdire l'ecriture pour les lecteurs --%>
<bean:define id="userProfile"
	name="<%=org.squale.welcom.struts.util.WConstants.USER_KEY%>"
	property="<%=\"profile(\"+applicationId+\")\"%>" />
<%-- Pour les champs --%>
<%boolean disabled = false; // Les champs seront en lecture/écriture par défaut %>
<logic:equal name="userProfile"
	value="<%=ProfileBO.READER_PROFILE_NAME%>">
	<%disabled = true; // Pour indiquer la lecture seule des champs car l'utilisateur est en lecture seule%>
</logic:equal>



<%
//variable session : NOM DE LA PAGE
//variable session : hashmap parameter( NOM DE LA PAGE_NOM PARAMETRE --> valeur )

AbstractConfigTask conf = (AbstractConfigTask)request.getAttribute("config");
String actionDo = conf.getTaskName();
actionDo = "add_project_"+actionDo.toLowerCase()+"_config.do";
String helpKey = conf.getHelpKeyTask();
Collection fields = conf.getInfoConfigTask();
%>

<br />
<af:dropDownPanel titleKey="buttonTag.menu.aide">
    <bean:message key="<%=helpKey%>" />
    <br /><br />
</af:dropDownPanel>


<af:form action="<%=actionDo%>">
	<%-- attribut projectId pour les droits d'accès --%>
	<input name="projectId" value="<%=projectId%>" type="hidden">
	<table width="100%" class="formulaire" cellpadding="0" cellspacing="0"
		border="0">

	<%Iterator it = fields.iterator();
	while (it.hasNext()){
    FieldInfoConfig field = (FieldInfoConfig)it.next();
    String fieldKey = field.getKey();
    String fieldProperty = field.getProperty();
    String fieldSize = field.getSize();
    String fieldIsRequired = field.getIsRequired();
    String fieldType = field.getType();	
	
	%>
	
	<tr>
		<af:field key="<%=fieldKey%>" type="<%=fieldType%>"
		property="<%=fieldProperty%>" size="60" isRequired="<%=fieldIsRequired%>" styleClassLabel="td1"
		disabled="<%=disabled%>" />
	</tr>
	
	<%}%>	
	
		
	</table>

	<logic:notEqual name="userProfile"
		value="<%=ProfileBO.READER_PROFILE_NAME%>">
		<jsp:include page="common_parameters_buttons.jsp" />
	</logic:notEqual>
</af:form>