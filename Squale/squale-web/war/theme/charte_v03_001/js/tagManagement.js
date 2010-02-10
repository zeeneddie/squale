
// This script is link to the page : homepageManagement.jsp
var agt=navigator.userAgent.toLowerCase();
var isIE = (agt.indexOf("msie") != -1);
var position = 1;
var listDiv = new Array();


/* The 4 following functions are called by the +/- buttons used to add/delete tags */
function addAppTagButtonClicked() 
{
	var element = document.getElementById( 'appTagAddition' );
	if ( isVisible( element ) )
	{
		/* the user cliked on the + to hide the field */
		enableAllButtons();
	}
	else
	{
		/* the user cliked on the + to add a tag */
		hideAllFields();
		disableAllButtons();
		alignField( 'appTagAddition', 'tag-button-app' );
	}
	showEnabledPlus( document.getElementById( 'tagPlusApp' ) );
	switchVisibility( element );
	$(":text[name='tagSupp']")[0].focus();
}
function addProjTagButtonClicked() 
{
	var element = document.getElementById( 'projTagAddition' );
	if ( isVisible( element ) )
	{
		/* the user cliked on the + to hide the field */
		enableAllButtons();
	}
	else
	{
		/* the user cliked on the + to add a tag */
		hideAllFields();
		disableAllButtons();
		alignField( 'projTagAddition', 'tag-button-proj' );
	}
	showEnabledPlus( document.getElementById( 'tagPlusPro' ) );
	switchVisibility( element );
	$(":text[name='tagSupp']")[1].focus();
}
function delAppTagButtonClicked() 
{
	var element = document.getElementById( 'appTagRemoval' );
	if ( isVisible( element ) )
	{
		/* the user cliked on the - to hide the combo box */
		enableAllButtons();
	}
	else
	{
		/* the user cliked on the - to remove a tag */
		hideAllFields();
		disableAllButtons();
		alignField( 'appTagRemoval', 'tag-button-app' );
	}
	showEnabledMinus( document.getElementById( 'tagMinusApp' ) );
	switchVisibility( element );
	$(":input[name='tagDelAppli']").focus();
}
function delProjTagButtonClicked() 
{
	var element = document.getElementById( 'projTagRemoval' );
	if ( isVisible( element ) )
	{
		/* the user clicked on the - to hide the combo box */
		enableAllButtons();
	}
	else
	{
		/* the user cliked on the - to remove a tag */
		hideAllFields();
		disableAllButtons();
		alignField( 'projTagRemoval', 'tag-button-proj' );
	}
	showEnabledMinus( document.getElementById( 'tagMinusPro' ) );
	switchVisibility( element );
	$(":input[name='tagDel']").focus();
}

/* Aligns the field right next to the buttons */
function alignField( fieldId, buttonsId ) 
{
	// we use jQuery functions here
	var field = $( "#" + fieldId );
	var buttons = $( "#" + buttonsId );
	var buttonDivTop = buttons.offset().top;
	var buttonDivLeft = buttons.offset().left;
	var buttonDivWidth = buttons.width();
	var newTop = buttonDivTop;
	var newLeft = buttonDivLeft+buttonDivWidth;
	if ( !jQuery.support.boxModel )
	{
		// this is IE
		newTop = newTop - $("#contenu").offset().top;
		newLeft = newLeft - $("#contenu").offset().left + 15;
	}
    field.css( { position: "absolute", top: newTop, left: newLeft } );
}

/* Sets the icon of the element to a disabled minus */
function showDisabledMinus( element ) 
{
	if ( element != null && element.firstChild != null )
	{ 
		element.firstChild.firstChild.src = "theme/charte_v03_001/img/ico/disabled/minus.gif";
	}
}

/* Sets the icon of the element to a enabled minus */
function showEnabledMinus( element ) 
{
	if ( element != null && element.firstChild != null )
	{
		element.firstChild.firstChild.src = "theme/charte_v03_001/img/ico/enabled/minus.gif";
	}
}

/* Sets the icon of the element to a disabled plus */
function showDisabledPlus( element ) 
{
	if ( element != null && element.firstChild != null )
	{
		element.firstChild.firstChild.src = "theme/charte_v03_001/img/ico/disabled/plus.gif";
	}
}

/* Sets the icon of the element to a enabled plus */
function showEnabledPlus( element ) 
{
	if ( element != null && element.firstChild != null )
	{
		element.firstChild.firstChild.src = "theme/charte_v03_001/img/ico/enabled/plus.gif";
	}
}

/* Enables all +/- buttons */
function enableAllButtons() 
{
	showEnabledPlus( document.getElementById( 'tagPlusApp' ) );
	showEnabledPlus( document.getElementById( 'tagPlusPro' ) );
	showEnabledMinus( document.getElementById( 'tagMinusApp' ) );
	showEnabledMinus( document.getElementById( 'tagMinusPro' ) );
}

/* Disables all +/- buttons */
function disableAllButtons() 
{
	showDisabledPlus( document.getElementById( 'tagPlusApp' ) );
	showDisabledPlus( document.getElementById( 'tagPlusPro' ) );
	showDisabledMinus( document.getElementById( 'tagMinusApp' ) );
	showDisabledMinus( document.getElementById( 'tagMinusPro' ) );
}

/* Hides all the fields at the same time */
function hideAllFields()
{
	visibilityOff( document.getElementById( 'appTagAddition' ) );
	visibilityOff( document.getElementById( 'projTagAddition' ) );
	visibilityOff( document.getElementById( 'appTagRemoval' ) );
	visibilityOff( document.getElementById( 'projTagRemoval' ) );
}

/* Switches en element visibility between visible and hidden */
function switchVisibility( element )
{
	if ( element != null )
	{
		if ( isVisible( element ) ) 
		{
			visibilityOff( element );
		}
		else
		{
			visibilityOn( element );
		}
	}
}

/* This function tells if an element is visible */
function isVisible( element )
{
	return element.style.visibility != "hidden";
}

/* This function sets the visibility option of an element to "visible" */ 
function visibilityOn( element )
{
	if ( element != null )
	{
		element.style.visibility = "visible";
	}
}

/* This function sets the visibility option of an element to "hidden" */ 
function visibilityOff( element )
{
	if ( element != null )
	{
		element.style.visibility = "hidden";
	}
}

/* This function sets the display option of an element to "" */  
function displayOn( element )
{
	if ( element != null )
	{
		element.style.display = "";
	}
}

/* This function sets the display option of an element to "none" */  
function displayOff( element )
{
	if ( element != null )
	{
		element.style.display = "none";
	}
}