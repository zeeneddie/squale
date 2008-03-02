<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>


<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>

<%org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors) pageContext.getAttribute(org.apache.struts.Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);%>


<%if (errors == null || errors.isEmpty()) {%>
<bean:message key="welcom.internal.error.emptylist" />
<%} else {
    org.apache.struts.action.ActionError ae = ((org.apache.struts.action.ActionError) errors.get().next());%>

<B>Message: </B>
<%=org.apache.struts.util.RequestUtils.message(pageContext, org.apache.struts.Globals.MESSAGES_KEY, "", ae.getKey(), ae.getValues())%>
<%}%>