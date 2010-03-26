 function edit(index)
 {	
 	// and we show the test form for the date and the mark
 	$("#moduleform-" + index).show("slide", { direction: "down" });
 	document.getElementById("canceledit-"+index).style.display="";
 	document.getElementById("edit-"+index).style.display="none";
 }
 
 function canceledit (index)
 {	
 	//We hide the test form for the date and the mark
 	$("#moduleform-" + index).hide("slide", { direction: "down" });
 	document.getElementById("canceledit-"+index).style.display="none";
 	document.getElementById("edit-"+index).style.display="";
 }
 
 function display()
 {
	 parentModule = document.getElementById("parentModule").value;
	 if( parentModule != "" )
	 {
		 edit(parentModule);
	 }
 }