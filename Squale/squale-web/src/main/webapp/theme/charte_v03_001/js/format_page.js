function eraseCanvasLeft(){
	var content = document.getElementById('contenu');
	content.style.left='10px';
}

function addField(tableName,index){
        var table = document.getElementById(tableName);
        var line=table.insertRow(table.rows.length);
        for(i=0;i<table.rows[index].cells.length;i++){
	        var cell = line.insertCell(i);
	        cell.align=table.rows[index].cells[i].align;
	        cell.className=table.rows[index].cells[i].className;
	        cell.innerHTML=table.rows[index].cells[i].innerHTML;
        }
}

function hideField(checkbox,fieldName){
	var obj = document.getElementById(fieldName);
	if(checkbox.checked){
		obj.style.display='none';
	}else{
		obj.style.display='block';
	}
}

function displayField(checkbox,fieldName){
	var obj = document.getElementById(fieldName);
	if(checkbox.checked){
		obj.style.display='block';
	}else{
		obj.style.display='none';
	}
}