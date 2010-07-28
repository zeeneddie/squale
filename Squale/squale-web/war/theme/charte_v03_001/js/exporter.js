
/*
 * Defined which div to show
 */
function showDiv()
{
	schedduled = document.getElementById('isSchedduled').value;
	modify =  document.getElementById('modify').value;
	if(modify == 'true')
	{
		document.getElementById('choiceDiv').style.display = '';
		document.getElementById('buttonSave').style.display='';
		document.getElementById('buttonCancel').style.display='';
	}
	else if( schedduled == 'true')
	{
		document.getElementById('listDiv').style.display = '';
	}
	else
	{
		document.getElementById('choiceDiv').style.display = '';
		document.getElementById('buttonExport').style.display='';
	}
}

/*
 * This method check all the checkbox in the selection div
 * --> All the application are selected 
 */
function selectAllCB()
{
	var check = document.getElementById("check-" + 0);
	var index = 0;
	while (check != null) 
	{
		check.checked='checked';
		// go next
		index++;
		check = document.getElementById("check-" + index);
	}
}

/*
 * This method uncheck all the checkbox in the selection
 * --> No application selected
 */
function unSelectAllCB()
{
	var check = document.getElementById("check-" + 0);
	var index = 0;
	while (check != null) {
		check.checked='';
		// go next
		index++;
		check = document.getElementById("check-" + index);
	}
}

/* This method set the good css style for each line*/
function lineStyle()
{
	lineColor ="";
	var line = document.getElementById("li-" + 0);
	var index = 0;

	while (line != null) 
	{
		if (line.style.display!="none")
		{
			line.className=lineColor;
			// set the good className (style) for the next line
	 		if (lineColor=="clair")
	 		{
	 			lineColor="";
	 			
	 		}
	 		else
	 		{
	 			lineColor="clair";
	 		}
		}
		index++;
		line = document.getElementById("li-" + index);
	}
}

$(document).ready(function(){
	$('li.exportheadlink').hover(
		function() { $('ul', this).css('display', 'block'); },
		function() { $('ul', this).css('display', 'none'); });
	$('li').hover(
		function() { $(this).css('background-color', '#F3BA5D');  },
		function() { $(this).css('background-color', '#2E5880');  });
});