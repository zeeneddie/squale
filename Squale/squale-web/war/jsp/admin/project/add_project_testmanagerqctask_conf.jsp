<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>



<%@ page import="org.squale.squaleweb.taskconfig.AbstractConfigTask"%>
<%@ page import="org.squale.squaleweb.taskconfig.qc.TestManagerQCTaskConfig"%>

<script type="text/javascript" src="/squale/jslib/format_page.js"></script>

<%
AbstractConfigTask conf = new TestManagerQCTaskConfig();
request.setAttribute("config",conf);
%>

<jsp:include page="/jsp/admin/project/add_project_external_generic.jsp"/>