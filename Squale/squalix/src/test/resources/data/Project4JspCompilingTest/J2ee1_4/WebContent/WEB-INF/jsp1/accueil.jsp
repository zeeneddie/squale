<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>



<page><head>
<script>
	function popup()
	{
		window.showModalDialog("popup.do", null, "dialogWidth:212px; dialogHeight:185px; scroll:yes; help:no; status:no");
	}
	function popup2()
	{
		window.open("popup.do", "popupDocument", "width=212, height=185,top=0,left=0,resize=true");
	}
	
</script>
</head>

<body>
<logic:equal value="false" name="accueilForm"
	property="textAccueilEdition">
	<bean:write name="accueilForm" property="textAccueil" filter="false" />
</logic:equal>
</body>
</page>