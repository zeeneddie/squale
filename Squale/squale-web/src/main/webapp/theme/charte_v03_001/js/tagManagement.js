
// This script is link to the page : homepageManagement.jsp
var agt=navigator.userAgent.toLowerCase();
var isIE = (agt.indexOf("msie") != -1);
var position = 1;
var listDiv = new Array();

/* This function sets the visibility option of a div to "visible" */ 
function visibilityOn( divId )
{
	var addTagElement = document.getElementById(divId);
	addTagElement.style.visibility = "visible";
}

/* This function sets the visibility option of a div to "hidden" */ 
function visibilityOff( divId )
{
	var addTagElement = document.getElementById(divId);
	addTagElement.style.visibility = "hidden";
}

/* This function sets the display option of a div to "" */  
function displayOn( divId )
{
	var addTagElement = document.getElementById(divId);
	addTagElement.style.display = "";
}

/* This function sets the display option of a div to "none" */  
function displayOff( divId )
{
	var addTagElement = document.getElementById(divId);
	addTagElement.style.display = "none";
}

/* This function shows the "+" or "-" signs next to the tags, if they are prensent */
function showButton( divId ){
	if (document.getElementById(divId)){
		var plus = document.getElementById(divId);
		plus.style.display = "";
	}	
}