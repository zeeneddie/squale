<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>



<%@ page import="com.airfrance.squaleweb.taskconfig.AbstractConfigTask"%>
<%@ page import="com.airfrance.squaleweb.taskconfig.qc.ExtBugTrackingQCTaskConfig"%>

<script type="text/javascript" src="/theme/charte_v3_001/js/format_page.js"></script>

<%
AbstractConfigTask conf = new ExtBugTrackingQCTaskConfig();
request.setAttribute("config",conf);
%>

<jsp:include page="/jsp/admin/project/add_project_external_generic.jsp"/>