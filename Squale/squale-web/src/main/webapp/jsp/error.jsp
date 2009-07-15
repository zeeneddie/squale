<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
 <%@ page isErrorPage="true" %>
 
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>Erreur</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="keywords" lang="fr" content="charte, graphique, intranet">
<link rel="stylesheet" href="theme/charte_v03_001/css/master.css" type="text/css">
</HEAD>

<BODY id="app" class="metiers">
	<div id="conteneur"> 
		<div id="contenu"> 
		  	<h1><bean:message key="error.page.title"/></h1>
		 	<p class="intro"><bean:message key="error.page.details"/><br></p>
			<code>
				<%=org.squale.squaleweb.util.ExceptionWrapper.getExceptionDetail(request, exception)%>
			</code>
		</div>
	</div>
	<div id="footer" class="bg_c12"></div>
</BODY>
