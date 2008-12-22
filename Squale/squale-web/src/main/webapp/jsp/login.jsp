<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@taglib uri="http://www.airfrance.fr/welcom/tags-welcom" prefix="af"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<HEAD>
<TITLE>Login</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="theme/charte_v03_001/css/master.css" type="text/css" rel="stylesheet">
<script language="javascript" src="jslib/behaviour-1.1.js"></script>
<script language="javascript" src="jslib/welcom.js"></script>
</HEAD>
<BODY id="app" class="metiers">
	<div id="header" class="ciel04">
		<!-- <hr class="bg_theme"/> -->
		<div id="visuel" style="float:left;width:29%;background-image: url(theme/charte_v03_001/img/tetiere/squale-banner-left.gif)"></div>
		<div id="visuel" style="float:right;width:70%;background: right no-repeat;background-image: url(theme/charte_v03_001/img/tetiere/squale-banner-right.gif)"></div> 
	</div>
	<div id="navigation" class="bg_theme"></div>
		<br/>
		<af:canvasLeft>
			<div class="menu_action"></div>
		</af:canvasLeft>
		<af:canvasCenter titleKey="authentication.title" subTitleKey="authentication.details">
		
		<!-- Message to show only if we come from a failed authentication-->
			<logic:messagesPresent>
				<html:messages id="failed">
				<div style="color: #f00">
					<bean:write name="failed"/>
				</div>	
				</html:messages>
			</logic:messagesPresent>
			<br/>	
			<af:form action="/ident.do" method="post" focus="login" >		
				<table class="formulaire" align="center">
					<tr>
						<af:field key="authentication.field.log" property="login" type="TEXT" styleClassLabel="td1" size="60" isRequired="true" ></af:field>
					</tr>
					<tr/>
					<tr>
						<af:field key="authentication.field.pwd" property="pass" type="PASSWORD" styleClassLabel="td1" size="60" isRequired="true"></af:field>
					</tr>
				</table>
				<br/>
				<af:buttonBar>
					<af:button type="form" name="valider" singleSend="true" styleId="td1"/>
				</af:buttonBar>
			</af:form>
		</af:canvasCenter>
		<br/>
		<br/>
	</div>
	<div id="footer" class="bg_theme"></div>
</BODY>