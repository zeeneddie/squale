<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>



<%@ page import="org.squale.squaleweb.taskconfig.AbstractConfigTask"%>
<%@ page import="org.squale.squaleweb.taskconfig.qc.ExtBugTrackingQCTaskConfig"%>

<script type="text/javascript" src="theme/charte_v03_001/js/format_page.js"></script>

<%
AbstractConfigTask conf = new ExtBugTrackingQCTaskConfig();
request.setAttribute("config",conf);
%>

<jsp:include page="/jsp/admin/project/add_project_external_generic.jsp"/>