<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>Erreur</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="keywords" lang="fr" content="charte, graphique, intranet">
<link rel="stylesheet" href="theme/charte_v03_001/css/master.css" type="text/css">
</HEAD>

<BODY id="app" class="metiers">
	<div id="header" class="ciel04">
		<hr class="bg_theme">
		<div id="visuel" style="background-image:url(theme/charte_v03_001/img/tetiere/ciel04_app.jpg)">
			<p>SQUALE</p>
		</div>
	</div>
	<div id="navigation" class="bg_theme"></div>
	<div id="conteneur"> 
		<div id="contenu"> 
		  	<h1><bean:message key="error.page.title"/></h1>
		 	<p class="intro"><bean:message key="error.page.details"/><br></p>
			<div style="color:#f00">
				<html:messages id="message"
					property="<%=org.apache.struts.action.ActionMessages.GLOBAL_MESSAGE%>"
					message="true">
						<bean:write name="message" />
						<br />
				</html:messages>
				<br />
			</div>
  			<code>
				<%=org.squale.squaleweb.util.ExceptionWrapper.getExceptionDetail(request)%>
  			</code>
		</div>
	<div id="footer" class="bg_c12"></div>
</BODY>