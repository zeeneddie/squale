var helpWindow;
var agt=navigator.userAgent.toLowerCase();
var isIE	= ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));

function errorMsg(field, property,msg,arg0)
{
	//document.images[property + "required"].src="{#optiflux.compression.prefix.img#}{#chartev2.field.ast#}";
	if (msg!="")
	{	
		msg=WI18N[msg];
		if (arg0) {
			msg=msg.replace(/\{0\}/g,arg0);
		}
		alert(msg);
		field.focus();
	}
}

function checkValue(field, property, mtype, required, majuscule, premiereMajuscule, accent, datePattern) {
	if (field.value!="") {
		//document.images[property + "required"].src="{#optiflux.compression.prefix.img#}{#chartev2.field.clearpixel#}";
		if (mtype=="NUMBER" && !isNumber(field.value)) 
		{
			errorMsg(field, property,'msgNombre');
		}			
		if ((mtype=="DATE" || mtype=="DATEHEURE") && !isDate(field.value,datePattern))
		{
			errorMsg(field, property,'msgDate',datePattern);
			return 1;
		}
		if (mtype=="HEURE" && !isHeure(field.value,datePattern))
		{
			errorMsg(field, property,'msgHeure',datePattern);
			return 1;
		}
		if (mtype=="EMAIL" && !isEmail(field.value))
		{
			errorMsg(field, property,'msgMail');
		}
		//remplace les lettres accentuées par des lettres non accentuées
		if(accent == false) {
			field.value=field.value.replace(/[âäàáåæÄÂÃÀÁÆ]/gi,'a');
			field.value=field.value.replace(/[éêëèÊËÈÉ]/gi,'e');
			field.value=field.value.replace(/[ïîÏÎÌÍ]/gi,'i');
			field.value=field.value.replace(/[ôöòóÔÖÒÓÕ]/gi,'o');
			field.value=field.value.replace(/[üùúûÜÛÙÚ]/gi,'u');
			field.value=field.value.replace(/[çÇ]/gi,'c');
			field.value=field.value.replace(/[ñÑ]/gi,'n');
			field.value=field.value.replace(/[ÿýÝ]/gi,'y');
		}
		if(premiereMajuscule) {
			prem= field.value.substring(0,1);
			field.value=prem.toUpperCase()+field.value.substring(1,field.value.length);
		}
		if(majuscule==true) field.value = field.value.toUpperCase();
	} else {	
		if (required)
		{ 
			errorMsg(field, property,'');
		}
	}
} 

function isEmail(value){
	return value.match(/^[a-zA-Z][\w\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$/);
}

// Vérifie qu'un caractère spécial n'a pas été saisi.
function checkCaractereSpecial(field, property) {
	if (field.value!="") {
		if (specialCaractere(field.value))
		{
			errorMsg(field, property,'msgSpeChar');
		}
	} 
} 

// retourne true si contient un caractere special
function specialCaractere(value)
{
	   return value.match(/'|"|>|<|&|=/);
}

// Return true if value is a number
function isNumber(value) {
	return value.match(/^[+-]?\ *(\d+([,\.]\d*)?|[,\.]\d+)([eE][+-]?\d+)?$/);
}	


function isDate(pdate,pattern){
	if(pattern==null){
		pattern="dd/MM/yyyy";
	}
	pattern=pattern.replace(/dd/g,'((0[1-9])|([1-2][0-9])|30|31)');
	pattern=pattern.replace(/MM/g,'((0[1-9])|10|11|12)');
	pattern=pattern.replace(/yy/g,'([0-9]{2})');
	pattern=pattern.replace(/\//g,'\/');
    var myregexp= new RegExp(pattern,'gi');
    var	mois=[31,28,31,30,31,30,31,31,30,31,30,31];
	if (pdate.match(myregexp)){
/*		j=d.substring(0,2);m=d.substring(3,5);a=d.substring(6,10);
		if ((a%4==0 || a%100==0) && a%400!=0)mois[1]=29;
		if (mois[m-1]>=j)*/
			return true;
	}
	return false;
}


function isHeure(pValue,pattern)
{	
	if(pattern==null){
		pattern="hh:mm";
	}
	pattern=pattern.replace(/HH/g,'(([0-1][0-9])|2[0-3])');
	pattern=pattern.replace(/hh/g,'(([0][0-9])|1[0-2])');
	pattern=pattern.replace(/((mm)|(ss))/g,'([0-5][0-9])');
	pattern=pattern.replace(/\//g,'\/');
    var myregexp= new RegExp(pattern,'gi');
	if (pValue.match(myregexp)){
		return true;
	}
	return false;
}

function execSubmit(form,action,me)
{
	var currentForm=document.forms[form];
	currentForm.action.value = action ;
	// me==window => c'est un href
	if ((me==null || me==window || me.type=='button') && currentForm.onsubmit()){
		sendUncheckbox(form);
   		document.forms[form].submit();
  	} else {
		sendUncheckbox(form);
  	}
}

// Pour les listes
function Mouseover(ob)
{
	ob.style.cursor='hand'; 
}

// Pour les listes
function Mouseout(ob)
{
	ob.style.cursor='default'; 
}

// Pour les listes
// Et la sélection des lignes par checkbox
function check(o)
{	
	if (o.checked)
	{
		o.parentNode.parentNode.className=o.parentNode.getAttribute('classSelect');
		if(	o.className!='normal')
			o.className=o.parentNode.getAttribute('classSelect');
	}
	else
	{
		o.parentNode.parentNode.className=o.parentNode.getAttribute('classDefault');
		if(	o.className!='normal')
			o.className=o.parentNode.getAttribute('classDefault');
	}
}

// Pour les listes
// Et la sélection des lignes par boutons radio
function checkSingle(o)
{	
	var mTable = o.parentNode;
	for (;mTable && mTable.tagName!="TABLE";mTable = mTable.parentNode);
	
	if (o.checked)
	{
		o.parentNode.parentNode.className=o.parentNode.getAttribute('classSelect');
		if(	o.className!='normal')
			o.className=o.parentNode.getAttribute('classSelect');
			
		if (mTable.getAttribute("oldSel") && mTable.getAttribute("oldSel") != "" )
		{
			old=document.getElementsByName(mTable.getAttribute("oldSel"))[0];
			old.parentNode.parentNode.className=old.parentNode.getAttribute('classDefault');
			if(	old.className!='normal')
				old.className=old.parentNode.getAttribute('classDefault');
			old.checked=false;
		} 
		mTable.setAttribute("oldSel",o.name);
	}
	else
	{
		o.parentNode.parentNode.className=o.parentNode.getAttribute('classDefault');
		if(	o.className!='normal')
			o.className=o.parentNode.getAttribute('classDefault');
		mTable.setAttribute("oldSel","");
	}
}

// Pour les listes
// Et la sélection des lignes par boutons radio
function checkRadioSingle(o)
{	
	var mTable = o.parentNode;
	for (;mTable && mTable.tagName!="TABLE";mTable = mTable.parentNode);
	
	if (o.checked)
	{
	
		if (mTable.getAttribute("oldSel") && mTable.getAttribute("oldSel") != "" )
		{
				var old=document.getElementById(mTable.getAttribute("oldSel"));
				if(o!=old){
					setClassDefault(old);
				}
		} 
		else
		{
			var sameNameRadios = document.getElementsByName(o.name);
			for(var i=0;i<sameNameRadios.length;i++){
				var old = sameNameRadios[i];
				setClassDefault(old);
			}
		}
	
		o.parentNode.parentNode.className=o.parentNode.getAttribute('classSelect');
		if(	o.className!='normal')
			o.className=o.parentNode.getAttribute('classSelect');
			

		mTable.setAttribute("oldSel",o.id);

	}
	else
	{
	
		setClassDefault(o);
		
		mTable.setAttribute("oldSel","");
	}
}	
//Fonction qui remet le style par défaut
function setClassDefault(ochk)
{
	ochk.parentNode.parentNode.className=ochk.parentNode.getAttribute('classDefault');
	if(	ochk.className!='normal')
	ochk.className=ochk.parentNode.getAttribute('classDefault');
}
// Selectionne toutes les cases a coché de la liste
function selectAll(pname,po){	
	var chk=document.getElementsByTagName('INPUT');
	for(var i=0;i<chk.length;i++){
		if(chk[i].type.toUpperCase('CHECKBOX') && chk[i].name.indexOf(pname)>=0 && chk[i].name.indexOf('selected')>=0){
			if(po.checked){
				chk[i].checked=true;
			}else{
				chk[i].checked=false;
			}
			check(chk[i]);
		}
	}
}

//Affichage et masquage du dropdownpanel
function vis(o)
{
	var load=o.getAttribute('load');
	if (load!=null && load=='true')
		updateDropDownPanel(o);		
	var pic=document.getElementById("pic"+o.id)
	var cl=pic.className;	
	cl=cl.substring(cl.length-1,cl.length);
	if (o.style.display=='none')
	{
		o.style.display='block';
		pic.className="down"+cl;		
		if(o.onExpand!=null)
			eval(o.onExpand);
	}
	else
	{
		if(o.onCollapse!=null)
			eval(o.onCollapse);
		o.style.display='none';
		pic.className="up"+cl;
	}	
}

var ddp=null;

function fixPos(ddpN)
{
	var dropDown=document.getElementById(ddpN);
	if (document.body.clientWidth<(dropDown.clientLeft+dropDown.offsetLeft+dropDown.clientWidth))
		dropDown.style.pixelRight=0;
	if(ddp!=null && ddp!=dropDown)
		vis(ddp);
	ddp=dropDown;	
}
/////////////////////////////////////////////////////////////////////////
//								ONGLETS								   //
/////////////////////////////////////////////////////////////////////////
// Version pour la charte v2.002
function F_OngletSelectionner3( pBoite, pOnglet, pBouton, pToggle )
{
  var onglet=document.getElementById(pOnglet);
  var load=onglet.getAttribute('load');
  
  if(load!= null && load=='true')
	updateTab(pBoite,pOnglet);
		
  var lBoite = document.getElementById( pBoite );
  document.getElementsByName(pBoite+".selectedtab")[0].value=pOnglet;
  if ( pBouton != null )
  {
    for( var i = 0; i < pBouton.parentNode.childNodes.length; i++ )
    {
     	pBouton.parentNode.childNodes[ i ].className = "";
    }  
  }

  for( var i = 0; i < lBoite.childNodes.length; i++ )
  {
    if ( lBoite.childNodes[ i ].tagName == "DIV" && lBoite.childNodes[ i ].className!="onglet")
    {
      if ( lBoite.childNodes[ i ].id == pOnglet &&
	   ( !pToggle || lBoite.childNodes[ i ].style.display != "" ) )
      {
        lBoite.childNodes[ i ].style.display = "";

		if ( pBouton )
		   {	
	   			pBouton.className = "on";
	   		
		   }
      }
      else
      {
        lBoite.childNodes[ i ].style.display = "none";
      }
    }
  }
}



// Version pour la charte v2.002
function F_OngletSelectionner2( pBoite, pOnglet, pBouton, pToggle )
{
  var onglet=document.getElementById(pOnglet);
  var load=onglet.getAttribute('load');
  
  if(load!= null && load=='true')
	updateTab(pBoite,pOnglet);
		
  lBoite = document.getElementById( pBoite );
  
  document.getElementsByName(pBoite+".selectedtab")[0].value=pOnglet;

  if ( pBouton != null )
  {
    for( var i = 0; i < pBouton.parentNode.childNodes.length; i++ )
    {
     	pBouton.parentNode.childNodes[ i ].className = "OngletOff";
    }  
  }

  for( var i = 0; i < lBoite.childNodes.length; i++ )
  {
    if ( lBoite.childNodes[ i ].tagName == "DIV" )
    {
      if ( lBoite.childNodes[ i ].id == pOnglet &&
	   ( !pToggle || lBoite.childNodes[ i ].style.display != "" ) )
      {
        lBoite.childNodes[ i ].style.display = "";

		if ( pBouton )
		   {	
	   			pBouton.className = "OngletOn";
	   		
		   }
      }
      else
      {
        lBoite.childNodes[ i ].style.display = "none";
      }
    }
  }
}
// Version pour la charte 2.001
function F_OngletSelectionner( pBoite, pOnglet, pBoutonAv,pBouton,pBoutonAp, pToggle )
{
  var onglet=document.getElementById(pOnglet);
  var load=onglet.getAttribute('load');
  if(load!= null && load=='true')
		updateTab(pBoite,pOnglet);

  lBoite = document.getElementById( pBoite );
  document.getElementsByName(pBoite+".selectedtab")[0].value=pOnglet;

  if ( pBouton != null )
  {
    for( var i = 0; i < pBouton.parentNode.childNodes.length; i++ )
    {
     	pBouton.parentNode.childNodes[ i ].className = "o_off";
        if ((i+1)%3==0)
        	pBouton.parentNode.childNodes[ i ].className = "o_off_bord";
    }  
  }

  for( var i = 0; i < lBoite.childNodes.length; i++ )
  {
    if ( lBoite.childNodes[ i ].tagName == "DIV" )
    {
      if ( lBoite.childNodes[ i ].id == pOnglet &&
	   ( !pToggle || lBoite.childNodes[ i ].style.display != "" ) )
      {
        lBoite.childNodes[ i ].style.display = "";

	if ( pBouton )
	   {	
	   		pBoutonAv.className = "o_on";
	   		pBouton.className = "o_on";	   		
	   }
	   if ( pBoutonAp )
	   {	
	   		pBoutonAp.className = "o_on_bord";
  		
	   }
      }
      else
      {
        lBoite.childNodes[ i ].style.display = "none";
      }
    }
  }
  
}

function setCursor(obj){
	try{
		obj.style.cursor='pointer';
	}catch(ex){
		obj.style.cursor='hand';
	}
}

/////////////////////////////////////////////////////////////////////////
//							IMPRESSION								   //
/////////////////////////////////////////////////////////////////////////

function submitApercuPDFExterne(page)
{
// Ouverture de l'aperçu avant impression dans un autre navigateur
var lien="printforward.do?wforward=apercuPDFExterne&src="+ escape(page);
w = window.open(lien, null,
"status=no,toolbar=no,menubar=no,location=no,titlebar=no,scrollbars=no");
}

function submitPDFPrint(page)
{
// Impression du PDF
document.location="printforward.do?wforward=preparationImpressionPDF&src=" + escape(page);
}

function submitApercuExcel(page)
{
// Ouverture de l'export Excel
document.location = "printforward.do?wforward=apercuExcel"+"&src="+ escape(page);
}

function submitApercuPDF(page)
{
// Ouverture de l'aperçu avant impression
document.location = "printforward.do?wforward=apercuPDF"+"&src="+ escape(page);
}

function tableForward(formName,url)
{
	form = document.forms[formName];
	if (form!=null )
	{
		try {
			form.removeChild(form.action);	
		} catch(e){
			// lorsque un bourrin clique plein de fois sur un tri
		}
		oldAction = form.getAttribute('action');
		// Pour compatibilité Opera
		var compatOperaUrl=document.location.protocol+"\/\/"+document.location.host+"\/";
		if (oldAction.indexOf(compatOperaUrl,0)==0){
			oldAction=oldAction.substring(compatOperaUrl.length-1,oldAction.length);
		}
		form.setAttribute('action',url + "&oldAction="+oldAction);
   		sendUncheckbox(formName);
   		if (form.onsubmit()) {
			form.submit();
   		}
	}
}

//Lazy Loading, Moteur Ajax
var http = getHTTPObject(); 

function updateDropDownPanel(o)
{
  var p = lazyLoading("DROPDOWNPANEL",o.id);
  if (p!="timeout")
  {
     o.innerHTML= p;
	 o.setAttribute('load','false');		
  }
  else
  {
	 o.innerHTML= "";
	 window.location="lazyLoading.do?action=timeout";
  }
}
function getAttribute(opt,attName){
	if(opt!=null && opt.attributes!=null){
		var i;
		for(i=0;i<opt.attributes.length;i++){
			if (opt.attributes[i].nodeName==attName)
				return opt.attributes[i].value;
		}
	}
}
function updateCombo(o)
{
  var load=o.getAttribute('load');
  if(load!=null && load=='true')
  	{	
        var ch=lazyLoading("COMBO",o.name,'xml');
	    if (http.responseText!='timeout')
		{
			while (o.options.length!=0){
				o.remove(0);
			}
		    var select=ch.getElementsByTagName("select")[0];
		    var i;
		    if (select!=null){
			    for(i=0;i<select.childNodes.length;i++){		    	
			    	var opt=select.childNodes[i];
			    	if(opt.nodeName=='option'){
				    	var option=document.createElement('option');
				    	option.value=getAttribute(opt,'value');
				    	var sel=getAttribute(opt,'selected');
				    	if(opt.text!=null){
					    	option.text=opt.text;   					//IE
					    }else if (opt.textContent!=null){
					    	option.text=opt.textContent;   				// FireFox
					    }else{
					    	option.text=opt.childNodes[0].nodeValue; 	// Opera safari	  						    
					    }
				    	try {
				  			o.add(option); // IE only
						}
						catch(ex) {
						    o.add(option, null); // standards compliant; doesn't work in IE
						}
				    	if (sel!=null)	// Spécifie le selectionné apres car ne marche pas avec Opera
				    		option.selected=sel;
					}
			    }		    
			    o.setAttribute('load','false')
			}
		    
		}
		else
		{
			 window.location="lazyLoading.do?action=timeout";
   	 	}
	}
}

function updateTab(parentName,name) {
  var o=document.getElementById(name);
  var p =lazyLoading("ONGLET",parentName+"."+name+".content");
  if (p!="timeout")
  {
  	 if ("{#charte#}"=="v3") {
	     o.innerHTML= "<table style=\"width:100%\"><tr><td>" +p + "</td></tr></table>";
     } else {
	     o.innerHTML= p;
     }
     o.setAttribute('load','false'); 
     Behaviour.apply();
  }
  else
  {
	 o.innerHTML= "";
	 window.location="lazyLoading.do?action=timeout";
  }
}

function lazyLoading(mtype,key,returnType)
{
  return httpGetSyncronizedURL("lazyLoading.do?action=get&type="+mtype+"&key="+key ,returnType);
}

function httpGetSyncronizedURL(url,returnType)
{
  http.open("GET", url , false);  
  http.send(null);  
  if (returnType==null){  		
	  return http.responseText;
  }else{	 
  	return http.responseXML;
  }
}

function httpPostSyncronizedURL(url,data,returnType)
{
  http.open("POST", url , false);
  http.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); 
  http.send(data);  
  if (returnType==null){  		
	  return http.responseText;
  }else{	 
  	return http.responseXML;
  }
}

function getHTTPObject() {
  var xmlhttp;
  if (typeof ActiveXObject != 'undefined') {
	  try {
	    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	  } catch (e) {
	     try {
	        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	     } catch (E) {
	        xmlhttp = false;
	     }
	  }
  }

  if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
    try {
      xmlhttp = new XMLHttpRequest();
    } catch (e) {
      xmlhttp = false;
    }
  }

  return xmlhttp;
}

/* Ajoute les uncheckBox dans la request */
function sendUncheckbox(fName)
{
	//if ({#optiflux.autoreset.checkbox#}==true)
	//{
		var f =document.forms[fName];
		var ch = f.getElementsByTagName("INPUT");
		for (i=0;i<ch.length;i++)
		{
			if (ch[i].getAttribute("type").toUpperCase()=="CHECKBOX" && !ch[i].checked)
			{
				var input = document.createElement("INPUT");				
				input.setAttribute('name',ch[i].name);	// safari
				input.setAttribute('type',"hidden");    // safari
				input.name=ch[i].name;
				input.type="hidden";
				input.value=(ch[i].getAttribute("unselectvalue")!=undefined?ch[i].getAttribute("unselectvalue"):notCheckBox(ch[i].getAttribute("value")));
				if (ch[i].getAttribute("unselectvalue")==undefined&&(ch[i].getAttribute("value")=="true"||ch[i].getAttribute("value")=="on"||ch[i].getAttribute("value")!="yes")){				
					f.appendChild(input);
				}
			}
		}	
	//}
}

/* not check box*/
function notCheckBox(s){
	if (s=="true"){
		return "false";
	} else if (s=="on"){
		return "off";
	} else if (s=="yes"){
		return "no";
	} else{
		return "";
	}
}

/*fonction d'appel d'un message de confirmation dynamique*/
function getRemoteMessage(url){
	var f =document.forms[0];
	var ch = f.getElementsByTagName("INPUT");
	if(!url.match(/\?/))
		url+="?";
	var datapost="";
	for (i=0;i<ch.length;i++)
	{	
		if (ch[i].type.toLowerCase()=="checkbox" && !ch[i].getAttribute("checked")) {
			datapost+="&"+escape(ch[i].name)+"="+escape((ch[i].getAttribute("unselectvalue")!=undefined?ch[i].getAttribute("unselectvalue"):notCheckBox(ch[i].getAttribute("value"))));
		}
		else {
			datapost+="&"+escape(ch[i].name)+"="+escape(ch[i].value);
		}
	}	
	var rep=httpPostSyncronizedURL(url,datapost,"xml");
	
	try{
		var messageTag=rep.getElementsByTagName("confirm")[0].childNodes[0];
		return messageTag.text;
	}catch(e){
		return null;
	}
}

var mIFrame=null;

function montrer(id) {
	var sm = document.getElementById(id);
	if (sm && sm.style) {
		var s = sm.style.display;
		if (s!="block") {
			sm.style.display='block';
			if (isIE){
				if (mIFrame==null) {
					mIFrame=document.createElement('IFRAME');
					mIFrame.style.position = "absolute";       
					mIFrame.frameborder='no';
					mIFrame.scrolling="no";
					mIFrame.src="javascript:false";
				}
				mIFrame.style.left = sm.offsetLeft;	
				mIFrame.style.top =sm.offsetTop;
				mIFrame.width =sm.offsetWidth;
				mIFrame.height =sm.offsetHeight;
				mIFrame.style.zIndex = 1000;
				mIFrame.style.display='block';
				sm.parentNode.insertBefore ( mIFrame, sm);
				sm.style.zIndex=mIFrame.style.zIndex+1;
				sm.height=sm.offsetHeight;
			}
		}
	}
}
function cacher(id) {
	var sm = document.getElementById(id);
	if (sm ) {
		sm.style.display='none';
		if (mIFrame){
		    mIFrame.style.display='none';
	    }
	}
}

function loadzone(pzonename,pdivid) {
  var o=document.getElementById(pdivid);
  var p =lazyLoading("ZONE",pzonename);
  if (p!="timeout")
  {
	if(o == null) { // on suppose que pdiv est une fonction
		eval(pdivid+"(p)");
	} else {
	 o.innerHTML= p;
	}
  }
  else
  {
	 o.innerHTML= "";
	 window.location="lazyLoading.do?action=timeout";
  }
}

function displayMutilingueField(oldfield,newfield)
{
	if (oldfield!="null")
	{
		document.getElementById(oldfield).style.display="none";
	}		
	if (document.getElementById(newfield)!=null)
	{
		document.getElementById(newfield).style.display="";
		document.getElementById(newfield).focus();
	}
}



// Chercher les champs a verifier pour l'orthographe
function spellCheck(){	
		var mParams='?op=1';
		var mAll = getAllChildren(document);
		var x=0;
		for (j=0;j<mAll.length;j++) {
			var mInput=mAll[j];
			if (mInput.tagName && (mInput.tagName.toUpperCase()=="INPUT" || mInput.tagName.toUpperCase()=="TEXTAREA")) {
				var mSpell = mInput.spell;
				if (!mSpell) {
					mSpell=getAttribute(mInput,"spell");
				}
				if (mSpell) {
			        mParams = mParams + '&fields[' + (x++) + '].name=forms['+getFormsNumber( mInput.form)+'].' + mInput.name;
				}
			}
		}
	    openCenteredWindow( 'wSpellWait.do' + mParams, 400, 250 );
	}
		
// Recherche le numero de formulaire;
function getFormsNumber( form )
{
    var forms = document.forms;
    for( var x = 0; x < forms[x].length; x++ )
    {
        if( forms[x] == form )
            return x;
    }
    
    return -1; // Form not found
}
    
/*
 * Function to open a window in the middle of the current window.
 *
 */
function openCenteredWindow( url, width, height )
{
    var left = 0;
    var top = 0;

    if( document.all )
    {
      top = window.top.screenTop + (window.top.document.body.clientHeight/2) - (height/2);
      left = window.top.screenLeft + (window.top.document.body.clientWidth/2) - (width/2);
    }
    else
    {
      top = window.top.screenY + (window.outerWidth/2) - (height/2);
      left = window.top.screenX + (window.outerHeight/2) - (width/2);
    }

    var newWin = window.open( url,"","height=" + (height) + ",width=" + (width) +",left=" +left+",top="+top+",location=no,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=yes,toolbar=no");
    return newWin;
}

var myrules = {
	'input[className|=boutonFormulaire]' : function(element){
		element.onmouseover = function(){
			element.style.backgroundColor= "#CC0000";
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.backgroundColor= "#6d76ac"; 
			element.style.cursor = "default";
		};	
	
	},
	'input[className|=btn]' : function(element){
		element.onmouseover = function(){
			element.style.backgroundColor= "#c00";
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.backgroundColor= "#2e5880"; 
			element.style.cursor = "default";
		};	
	},
	'input[className~=white]' : function(element){
		element.onmouseover = function(){
			element.style.backgroundColor= "#c00";
			element.style.color= "#fff";
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.backgroundColor= "#f0eeec"; 
			element.style.color= ""; 
			element.style.cursor = "default";
		}; 
	},
	'input.btn2' : function(element){
		element.onmouseover = function(){
			element.style.backgroundColor= "#C7CDE7";
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.backgroundColor= "#E7EBFF"; 
			element.style.cursor = "default";
		};	
	},
	'span[className|=href]' : function(element){
		element.onmouseover = function(){
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.cursor = "default";
		};	
	},
	'span[className|=hrefred]' : function(element){
		element.onmouseover = function(){
			element.style.color="red";
			setCursor(element);
		};
		element.onmouseout = function(){
			element.style.color="";
			element.style.cursor = "default";
		};	
	}};


		
		
Behaviour.register(myrules);

function addListener(elmnt,baseName,handler) {
	if (elmnt.addEventListener) {
		elmnt.addEventListener(baseName,handler,false);
	} else if (elmnt.attachEvent){
		elmnt.attachEvent('on' + baseName,handler);
	}
}


addListener(window,'load',Behaviour.apply);


function poll(pId,pHref,pTime){
	if(pTime<5000) {
		pTime=5000;
	}
	document.getElementById(pId).innerHTML = httpGetSyncronizedURL(pHref);
	window.setTimeout("poll('"+pId+"','"+pHref+"',"+pTime+")", pTime);
};