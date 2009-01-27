
// This script is link to the page : homepageManagement.jsp
var agt=navigator.userAgent.toLowerCase();
var isIE = (agt.indexOf("msie") != -1);
var position = 1;
var listDiv = new Array();

/* This function is called on a click on the checkbox auditCheck.
 This function manage the accessibility to the checkbox under auditCheck*/ 
function displayAudit()
{
	var cbAudit = document.getElementById("auditId");
	var cbAuditScheduled = document.getElementById("auditScheduledId");
	var cbAuditDone = document.getElementById("auditDoneId");
	var cbAuditShowSeparately = document.getElementById("auditShowSeparatelyId");
	var cbAuditSuccessfull = document.getElementById("auditSuccessfullId");
	var cbAuditPartial = document.getElementById("auditPartialId");
	var cbAuditFailed = document.getElementById("auditFailedId");
	var fieldAuditNbJours = document.getElementById("auditNbJoursId");
	
	/*If the checkbox auditCheck is checked then the other checkbox under auditCheck should be enabled.
	Else they should be disabled*/
	if(cbAudit.checked)
	{
		cbAuditScheduled.disabled=false;
		cbAuditDone.disabled=false;
		cbAuditShowSeparately.disabled=false;
		//If the checkox auditDoneCheck is checked then the checkbox under auditDoneCheck should be enabled 
		if (cbAuditDone.checked)
		{
			cbAuditSuccessfull.disabled=false;
			cbAuditPartial.disabled=false;
			cbAuditFailed.disabled=false;
			fieldAuditNbJours.disabled=false;
		}
		else
		{
			cbAuditSuccessfull.disabled=true;
			cbAuditPartial.disabled=true;
			cbAuditFailed.disabled=true;
			fieldAuditNbJours.disabled=true;
		}
	}
	else
	{
		cbAuditScheduled.disabled=true;
		cbAuditDone.disabled=true;
		cbAuditShowSeparately.disabled=true;
		cbAuditSuccessfull.disabled=true;
		cbAuditPartial.disabled=true;
		cbAuditFailed.disabled=true;
		fieldAuditNbJours.disabled=true;
	}
}

/* This function is called on a click on the checkbox auditDoneCheck.
 This function manage the accessibility to the checkbox under auditDoneCheck*/ 
function displayDone()
{
	var cbAuditDone = document.getElementById("auditDoneId");
	var cbAuditSuccessfull = document.getElementById("auditSuccessfullId");
	var cbAuditPartial = document.getElementById("auditPartialId");
	var cbAuditFailed = document.getElementById("auditFailedId");
	var fieldAuditNbJours = document.getElementById("auditNbJoursId");

	/*If the checkbox auditDoneCheck is checked then the other checkbox under auditDoneCheck should be enabled.
	Else they should be disabled*/
	if(cbAuditDone.checked)
	{
		cbAuditSuccessfull.disabled=false;
		cbAuditPartial.disabled=false;
		cbAuditFailed.disabled=false;
		fieldAuditNbJours.disabled=false;
	}
	else
	{
		cbAuditSuccessfull.disabled=true;
		cbAuditPartial.disabled=true;
		cbAuditFailed.disabled=true;
		fieldAuditNbJours.disabled=true;
	}
	uncheckedShowSeparately();
}

/* This function is called on a click on the checkbox resultCheck.
 This function manage the accessibility to the checkbox under resultCheck*/ 
function displayResult()
{
	var cbResult = document.getElementById("resultId");
	var cbResultByGrid = document.getElementById("resultByGridId");
	var cbResultKiviatId = document.getElementById("resultKiviatId");
	var cbResultKiviatAllFactorsId = document.getElementById("resultKiviatAllFactorsId");
	var fieldKiviatWidth = document.getElementById("kiviatWidthId");
	
	/*If the checkbox resultCheck is checked then the other checkbox under resultCheck should be enabled.
	Else they should be disabled*/
	if(cbResult.checked)
	{
		cbResultByGrid.disabled=false;
		cbResultKiviatId.disabled=false;
		cbResultKiviatAllFactorsId.disabled=false;
		displayKiviat();
	}
	else
	{
		cbResultByGrid.disabled=true;
		cbResultKiviatId.disabled=true;
		cbResultKiviatAllFactorsId.disabled=true;
		fieldKiviatWidth.disabled=true;
	}	
}

//Function call on the load of the homepageManagement page
function display()
{
	position=1;
	divList();
	displayAudit();
	displayResult();
	setPosition();
	maskElement();
}

// Function call when we add suppress an element to display 
function modifyTableOrder( idElement )
{
	var partPosition = parseInt(document.getElementById("position_"+idElement).value);
	var toAdd = document.getElementById(idElement);
	
	if (toAdd.checked)
	{
		for( index = partPosition; index > position; index = index - 1)
		{
			move(index,'up');
		}
		if (isIE)
		{
			document.getElementById('div_position'+partPosition).style.display = "none";
			document.getElementById('div_position'+position).style.display = "block";
		}
		position=position+1;
		if(position > 2)
		{
			document.getElementById("down"+(position-2)).style.display="block";
			document.getElementById("up"+(position-1)).style.display="block";
		}
		
	}
	else
	{
		//when the block is in position 5 he is already at the last position. So we should stop when index= 5   
		for( index = partPosition; index < 5 ; index = index + 1)
		{
			move(index,'down');
		}
		position = position-1;
		if (isIE)
		{
			document.getElementById('div_position'+position).style.display = "none";
		}
		// For the display of the arrow	
		if(position>1)
		{
			document.getElementById("down"+(position-1)).style.display="none";
		}
		document.getElementById("up"+(position)).style.display="none";
		document.getElementById("div_position5").style.display="none";
	}
}


// Function call on a click on the checkbox auditId
function checkAudit()
{
	displayAudit();
	modifyTableOrder( "auditId" );
}

// Function call on a click on the checkbox resultId
function checkResult()
{
	displayResult();
	modifyTableOrder( "resultId" );
}

// This function is call to move lines in the table order 
// When click on the up / down picto
function move (currentPosition, direction)
{
	var suivant
	
	if (direction.match("down"))
	{
		suivant = parseInt(currentPosition)+1;
	}
	else
	{
		suivant = parseInt(currentPosition)-1;
	}
	
	
	var div = document.getElementById( "hp_mngt" );
	var listChild = new Array();
	listChild = div.childNodes;
	
	if (isIE)
	{
		var div = document.getElementById('div_position'+currentPosition);
		var next_div= document.getElementById('div_position'+suivant);
		temporInnner = div.innerHTML;
		div.innerHTML=next_div.innerHTML;
		next_div.innerHTML=temporInnner
	}
	else
	{
		//Block line n
		var child = listChild[listDiv[currentPosition-1]];
		var childClone = child.cloneNode('true');
		
		//Block line n+1 or n-1
		var next_child =  listChild[listDiv[suivant-1]];
		var next_ChildClone = next_child.cloneNode('true');
		
		//Exchange of the block
		div.replaceChild(next_ChildClone,child);
		div.replaceChild(childClone,next_child);
		
		next_ChildClone.id="div_position"+currentPosition;
		childClone.id="div_position"+suivant;
	}
	
	//element line n
	var htmlText = document.getElementById("htmlText"+currentPosition);
	var order = document.getElementById("position_"+htmlText.value);
	var title = document.getElementById( "title"+currentPosition );
	
	
	//Element line n+1 or n-1
	var next_htmlText = document.getElementById("htmlText"+suivant);
	var next_order = document.getElementById("position_"+next_htmlText.value);
	var next_title = document.getElementById( "title"+suivant );
	
	//Tempor
	var tempor_htmlText = htmlText.value;
	var tempor_title = title.innerHTML;
	var temporOrder = order.value;
	
	//Exchange of the value
	htmlText.value=next_htmlText.value;
	next_htmlText.value=tempor_htmlText;
	title.innerHTML=next_title.innerHTML;
	next_title.innerHTML=tempor_title;
	order.value = next_order.value;
	next_order.value=temporOrder;
}

//This function mask arrow and bloc of parameter on the load of the page
function maskElement()
{	
	if (position <3)
	{
		for (var index = 1 ; index < 6; index = index +1 )
		{
			document.getElementById("down"+index).style.display="none";
			document.getElementById("up"+index).style.display="none";
			if (index >= position)
			{	
				document.getElementById("div_position"+index).style.display="none";
			}
		}
	}
	else
	{
		document.getElementById("up1").style.display="none";
		document.getElementById("down"+(position-1)).style.display="none";
		
		for (var index = position ; index < 6; index = index +1 )
		{
			document.getElementById("down"+index).style.display="none";
			document.getElementById("up"+index).style.display="none";	
			document.getElementById("div_position"+index).style.display="none";
		}
	}
}

// Function call on the load of the page for set the element to display in the good order
function setPosition()
{
	var index=1;
	var elementCheck = true;
	while(index < 6 && elementCheck)
	{
		elementName = document.getElementById('htmlText'+index).value;
		elementCheck = document.getElementById(elementName).checked;
		if(elementCheck)
		{
			position = position + 1;
		}
		index=index+1;
	}
	
}

// This function is call on a clic in the page. 
// When we save the configuration a message of good recording appear
// This function hide this message
function maskMsg()
{
	var elt = document.getElementById("msgId");
	if(elt != null)
	{
		elt.style.visibility="hidden";
	}	
}

// This fucntion is call on a click on the checkbox auditShowSeparately
function auditScheduledAndDone()
{
	var ShowSeparately=document.getElementById("auditShowSeparatelyId");
	var displayAuditDone = document.getElementById("auditDoneId");
	var displayScheduledAudit = document.getElementById("auditScheduledId");
	if (ShowSeparately.checked)
	{
		displayAuditDone.checked=true;
		displayScheduledAudit.checked=true;
		displayDone();	
	}
}

// This function is calles on a click on the chechbox auditDone auditScheduled
function uncheckedShowSeparately()
{
	var ShowSeparately=document.getElementById("auditShowSeparatelyId");
	var displayAuditDone = document.getElementById("auditDoneId");
	var displayScheduledAudit = document.getElementById("auditScheduledId");
	if (displayAuditDone.checked==false || displayScheduledAudit.checked==false)
	{
		ShowSeparately.checked=false;	
	}
}

// this fucntion is call on a click on the header of a bloc
function show(body)
{
	var bodyElt = document.getElementById(body);
	if(bodyElt.style.display == "none")
	{
		bodyElt.style.display="block";
	}
	else
	{
		bodyElt.style.display="none";
	}	
}

// This function is call on the load of the page. 
function divList()
{
	// this function create a table of position in the node tree of each div name div_position
	var div = document.getElementById( "hp_mngt" );
	var divChild = new Array();
	list = div.childNodes;
	var element;
	for (index = 0; index < list.length; index =index +1)
	{
		element = list[index];
		if(element.tagName == "DIV")
		{
			listDiv.push(index);
		}
	}
}

// This function is call on a click on checkbox displyKiviat
function displayKiviat()
{
	// According to the state of the checkbox display kiviat, the field kiviat width is unabbled or disabled  
	if(document.getElementById("resultKiviatId").checked)
	{
		document.getElementById("kiviatWidthId").disabled=false;
		document.getElementById("resultKiviatAllFactorsId").disabled=false;
	}	
	else
	{
		document.getElementById("kiviatWidthId").disabled=true;
		document.getElementById("resultKiviatAllFactorsId").disabled=true;
	}
}