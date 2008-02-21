<!-- JSP d'initialisation de la connexion LDAP -->

<!-- code à appeler dès l'entrée dans l'application :
- récupération de la configuration de connectionLdap
- Si LDAP connecté au serveur HTTP 
	Récupération du matricule passé dans la requête ou simulation WTE
- Sinon
	Simulation de la popup d'authentification
	Récupération du matricule passé dans la requête
- Appel de l'action de traitement de la connection
-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<TITLE>index.jsp</TITLE>
</HEAD>
<BODY>

<jsp:forward page="/login.do"></jsp:forward>
</BODY>
</HTML>
