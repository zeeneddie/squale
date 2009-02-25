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
 	for (index =0; index <listSize; index = index+1)
 	{
 		if (document.getElementById("li-"+index).style.display!="none")
 		{
			document.getElementById("li-"+index).className=lineColor;
	 		if (lineColor=="clair")
	 		{
	 			lineColor="";	
	 		}
	 		else
	 		{
	 			lineColor="clair";
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
 	
 	//We hide the value and the creation date information of the edited mark ... 
 	document.getElementById("val-"+index).style.display="none";
 	document.getElementById("date-"+index).style.display="none";
 	// and we show the value and the creation date field for this edited mark
 	document.getElementById("editval-"+index).style.display="";
 	document.getElementById("editdate-"+index).style.display="";
 	
 	// When we edited a mark by clicking the edition pic, then the paramter new edition is equal to true. 
 	// Else if we come back from an incorrect validation of the value then it's not a new edition,
 	// so the parameter newedition is set to false. 
 	if(newedition)
 	{
 		//If it's a new edition then we keep which line is edited in a hidden field.
 		document.getElementById("editPlace").value=index;
 		
 		//We keep the previous value in hidden field
 		document.getElementById("tprValue").value = document.getElementById("editvalValue-"+index).value;
 		document.getElementById("temporDateWDate").value = document.getElementById("manualPracticeList"+index+"creationDateWDate").value;
 	}
 	else
 	{
 		//Exchange of the value between tempotValue and the field (line and creation date) of the edited line
 		tempVal = document.getElementById("editvalValue-"+editPlace).value;
 		tempdate = document.getElementById("manualPracticeList"+editPlace+"creationDateWDate").value;
 		document.getElementById("editvalValue-"+editPlace).value = document.getElementById("tprValue").value;
 		document.getElementById("manualPracticeList"+editPlace+"creationDateWDate").value = document.getElementById("temporDateWDate").value;
 		document.getElementById("tprValue").value = tempVal;
 		document.getElementById("temporDateWDate").value = tempdate;
 		
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
 	
 	//We hide the value and the creation date field for this edited mark ...
 	document.getElementById("editval-"+index).style.display="none";
 	document.getElementById("editdate-"+index).style.display="none";
 	//We show the value and the creation date information of the edited mark ... 
 	document.getElementById("val-"+index).style.display="";
 	document.getElementById("date-"+index).style.display="";
 	// We set in the value and in the creation date field of this edited mark there first value
 	document.getElementById("editvalValue-"+index).value = document.getElementById("tprValue").value; 
 	document.getElementById("manualPracticeList"+index+"creationDateWDate").value = document.getElementById("temporDateWDate").value;
 	
 	// We set the editPlace field to -1. That means there is no line edited.
 	document.getElementById("editPlace").value=-1;
 	
 	// We hide the error message
 	document.getElementById("errorMessage").style.display="none";
 	
 }	
 
 