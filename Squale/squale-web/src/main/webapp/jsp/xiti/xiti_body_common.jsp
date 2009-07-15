<%@ page import="org.squale.squaleweb.resources.WebMessages" %>
<%-- Le marquage XITI de la page (à placer dans le body de la page) --%>
<%
	// Cette page sera vide pour le dev et la recette afin de ne pas avoir de marqueur dans ses environnements
	String useXITI = WebMessages.getString(request, "xiti.use");
	if(useXITI.equals("true")) {
%>
<%
	/* On récupère les paramètres nécessaire aux marquages */
	// Le paramètre spécifique à la page passé en paramètre dans le jsp:include
	String pageName = (String) request.getParameter("page");
	// L'id de niveau 1
	String firstId = WebMessages.getString(request, "xiti.configuration.id.first_level");
	// L'id de niveau 2
	String secondId = WebMessages.getString(request, "xiti.configuration.id.second_level");
%>
<script type="text/javascript">xt_page_AF_v2(<%=firstId%>, <%=secondId%>, "<%=pageName%>");</script>
<%
	}
%>