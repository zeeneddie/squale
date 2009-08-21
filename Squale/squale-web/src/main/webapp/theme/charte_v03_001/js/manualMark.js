/*This function Script to do after that the table has been filled
This script will give the good background color for each line
and set in a red font the lines which contains out of date mark*/  
 function onLoadDisplay(listSize,outOfDate)
 {
 	// Search all the line where the timeleft is set to "Out of date" (depends on language) 
 	// For each line found the font color is set to red (#f00) 
 	for (iter =0; iter <listSize; iter = iter+1)
 	{
		elt = document.getElementById("timeleft-"+iter);
 		if (elt.innerHTML.match(outOfDate) )
 		{
 			document.getElementById("li-"+iter).style.color="#f00";	
 		}
 	}
 	//Set the good line style
 	lineStyle(listSize);
 	
 	//If we come back from a failed validation, then there is already a line edited.
 	//In this case we call edit function
 	editPlace=document.getElementById("editPlace").value;
 	if(editPlace!=-1)
 	{
 		edit(editPlace,listSize,false);
 	}
 }
 
 /* This method set the good css style for each line*/
 function lineStyle(listSize)
 {
 	lineColor ="";
 	lineFormColor =""; 
 	for (index =0; index <listSize; index = index+1)
 	{
 		if (document.getElementById("li-"+index).style.display!="none")
 		{
			document.getElementById("li-"+index).className=lineColor;
			//for the form row
			document.getElementById("rowform-"+index).className=lineFormColor;
	 		if (lineColor=="clair")
	 		{
	 			lineColor="";
	 			lineFormColor="";
	 		}
	 		else
	 		{
	 			lineColor="clair";
	 			lineFormColor="clair";
	 		}
 		}
 	}
 }
 
 /* Function call when we edit a line */
 function edit(index, listSize, newedition)
 {
 	//For each line of mark we hide the edition pic 
 	for (iter=0; iter < listSize; iter=iter+1)
 	{
		document.getElementById("edit-"+iter).style.display="none";
 	}
 	//We show the cancel edition pic of the edited mark
 	document.getElementById("canceledit-"+index).style.display="";
 	
 	// and we show the row where is the form
 	document.getElementById("rowform-"+index).style.display="";
 	
 	// and we show the test form for the date and the mark
 	$("#editform-" + index).show("slide", { direction: "down" });
 		
 	// When we edited a mark by clicking the edition pic, then the parameter new edition is equal to true. 
 	// Else if we come back from an incorrect validation of the value then it's not a new edition,
 	// so the parameter newedition is set to false. 
 	if(newedition)
 	{
 		//If it's a new edition then we keep which line is edited in a hidden field.
 		document.getElementById("editPlace").value=index;
 		
 		//We keep the previous value in hidden field
 		document.getElementById("tprValue").value = document.getElementById("editvalValue-"+index).value;
 		document.getElementById("temporDateWDate").value = document.getElementById("manualPracticeList"+index+"creationDateWDate").value;
 		document.getElementById("tprComment").value = document.getElementById("manualPracticeList"+index+"comments").value;
 	}
 	else
 	{
 		//Exchange of the value between tempotValue and the field (line and creation date) of the edited line
 		tempVal = document.getElementById("editvalValue-"+editPlace).value;
 		tempdate = document.getElementById("manualPracticeList"+editPlace+"creationDateWDate").value;
 		tempComments = document.getElementById("editComm-"+editPlace).value;
 		document.getElementById("editvalValue-"+editPlace).value = document.getElementById("tprValue").value;
 		document.getElementById("manualPracticeList"+editPlace+"creationDateWDate").value = document.getElementById("temporDateWDate").value;
 		document.getElementById("editComm-"+editPlace).value = document.getElementById("tprComment").value;
 		document.getElementById("tprValue").value = tempVal;
 		document.getElementById("temporDateWDate").value = tempdate;
 		document.getElementById("tprComment").value = tempComments;
 	}
 }
 
 /* Function call when we cancel an edition */
 function canceledit (index, listSize)
 {
 	//We hide the cancel edition pic of the edited line 
 	document.getElementById("canceledit-"+index).style.display="none";
 	
 	//We show all the edition pic 
 	for (iter=0; iter < listSize; iter=iter+1)
 	{
		document.getElementById("edit-"+iter).style.display="";
 	}
 	
 	//We hide the test form for the date and the mark
 	$("#editform-" + index).hide("slide", { direction: "down" });
 	
 	// We set in the value and in the creation date field of this edited mark there first value
 	document.getElementById("editvalValue-"+index).value = document.getElementById("tprValue").value; 
 	document.getElementById("manualPracticeList"+index+"creationDateWDate").value = document.getElementById("temporDateWDate").value;
 	document.getElementById("editComm-"+index).value = document.getElementById("tprComment").value;
 	
 	// We set the editPlace field to -1. That means there is no line edited.
 	document.getElementById("editPlace").value=-1;
 	
 	// We hide the error message
 	document.getElementById("errorMessage").style.display="none";
 	
 }	
/* This function is used to show or hide the manual mark comment div */
 function showHideComments ( id )
 {
	if ( $("#comment-"+id).css("display")=="none" )
 	{
 		$("#comment-"+id).css("display", "");
 	}
 	else
 	{
 		$("#comment-"+id).css("display", "none");
 	}
 }