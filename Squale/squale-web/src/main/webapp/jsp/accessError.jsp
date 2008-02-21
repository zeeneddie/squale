<%@ taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page isErrorPage="true" %>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>Erreur</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="keywords" lang="fr" content="charte, graphique, intranet">
<link rel="stylesheet" href="http://cmsintranet.airfrance.fr/charte_v03_001/css/master.css" type="text/css">
</HEAD>

<BODY id="app" class="metiers">
	<div id="header" class="ciel04">
		<hr class="bg_theme">
		<div id="visuel" style="background-image:url(http://cmsintranet.airfrance.fr/charte_v03_001/img/tetiere/ciel04_app.jpg)">
			<p>SQUALE</p>
		</div>
	</div>
	<div id="navigation" class="bg_theme"></div>
	<div id="conteneur"> 
		<div id="contenu"> 
		  	<h1><bean:message key="error.access.title"/></h1>
		 	<p class="intro"><bean:message key="error.access.details"/><br></p>
		   	<af:buttonBar>
		   		<af:button type="form" name="retour" toolTipKey="toolTip.retour.homepage" onclick='<%="index.do?"%>' />
			</af:buttonBar>
		</div>
	<div id="footer" class="bg_c12"></div>
</BODY>