/****************************************
 * Inspired from BAUSTIER Frantz's work *
 ****************************************/

var tableau = null;
var i=0;

function remove_java_application(_div, _num){
	var new_div = _div + _num;
	document.getElementById(_div).removeChild(document.getElementById(new_div));
}

function add_java_application(_url, _div, _num) {
	var url = _url; 
	var new_div = _div + _num;
	
    var params = null;
    var fonction = function(_params, _req) {
    	appendToDiv(document.getElementById(_div), new_div, _req.responseText);
    };
    commun_traiteReq(url, fonction, params);
    i++;
}

function appendToDiv(_div, _value, _content) {
    var opt;
    opt = document.createElement('div');
    opt.id = _value;
    opt.innerHTML=_content;
    _div.appendChild(opt);
}

function getNumber() {
	return i;
}


function checkId(_id){
	var cas = 0;
	if(null == tableau){
		tableau = new Array(0);
		tableau.push('0');
		cas = 1;
	}
	else if(tableau.length <= _id && tableau.length > 0) {
		tableau.push('0');
		cas = 2;
	} else {
		tableau[_id]++;
		cas = 3;
	}
	alert('id : '+_id+' -> '+tableau[_id]+' ==> cas : '+cas);
}

function getPosition(_id){
	alert(tableau);
//	return tableau[_id];
}

function getTableau(){
	return tableau;
}

function setTableau(_tableau){
	tableau = _tableau;
}	