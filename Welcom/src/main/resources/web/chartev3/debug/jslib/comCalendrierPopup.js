/*
2002 v1 DI.NB/EW
2005/06 v2 GL - detection de la resolution ecran 1280px pour utilisation comCalendrierPopupTFT.css GL
2005/09 v3 GL - detection de la resolution ecran pour mois sur 6 lignes GL
2006/01/13 v4 TD
	internationalisation plus simple (fr, nl, en, vi, ja, + extensible. http://www.loc.gov/standards/iso639-2/frenchlangn.html)
	Internationalisation - codes: 
	format de date personnalisable
	compatibilite cursor hand+pointer
2006/01/13 v5 TD
	format de date en anglais pour homogeneite avec welcome
	dictionaires en fichiers externes (fr.js, en.js, etc.)


2006/05/04 V6 EM compatibilité FireFox Opera
2006/08/10 EM pour mettre le focus dans le champ date et pouvoir ainsi faire un onBlur sur ce champ dans le formulaire appelant.
*/



var req = "";
var dbl = "";
var Tab = "";

var objet, mois, annee, format, langue, anneeDebut, anneeFin, tableJours;



ie = false;
if((verOffset= navigator.userAgent.indexOf("MSIE")) != -1) {
	ie = true;
	ie_version = parseFloat(navigator.userAgent.substring(verOffset + 5, navigator.userAgent.length));
}

ns4 = (document.layers);
ie4 = (document.all && !document.getElementById);


var les_jours = new Object();
var les_mois = new Object();
var les_annees = new Object();

les_jours["fr"]= new Array("L", "M", "M", "J", "V", "S", "D");
les_mois["fr"]= new Array("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");
les_jours["en"]= new Array("M", "T", "W", "T", "F", "S", "S");
les_mois["en"]= new Array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
les_annees= {pre: "", post: ""};

caract_interdits = new Array (
			new Array("/", ";", ":", "?", "=", "+"), 
			new Array("%2f", "%3b", "%3a", "%3f", "%3d", "%2b")
		);



// Pour FF car sous IE le Focus est géré via "arguments" !!!!!!!
var FocusChamp = false;




function Arguments(objet, mois, annee, anneeDebut, anneeFin, format, langue, focus) {
	try {
		if (focus == null) {
			this.focus = false;
		} else {
			this.focus = focus;
		}

		d = new Date();
		if (mois == null) {
			mois = d.getMonth() + 1;
		}
	
		if (annee == null) {
			annee = d.getFullYear();
		}
	
		if (anneeDebut == null) {
			anneeDebut = d.getFullYear() - 3;
		}
	
		if (anneeFin == null) {
			anneeFin = d.getFullYear() + 3;
		}
	
		this.objet = objet;
		this.mois = mois;
		this.annee = annee;
		this.anneeDebut = anneeDebut;
		this.anneeFin = anneeFin;
		this.format = format;
		
		if(langue != null) {
			this.langue = langue.substring(0, 2); /* english -> en */
		} else {
			this.langue= "fr";
		}
	}  catch(e)  { 
		alert("Erreur : " + e);
	}
}


function afficherCalendrier(page, o, m, a, ad, af, f, l, focus) {
	if(ie && ie_version >= 5.5) {
		var Oarguments = new Arguments(o, m, a, ad, af, f, l,focus);
		
		if(screen.width>1279) /* modif du 2005/09 */ {
			window.showModalDialog('{#optiflux.compression.prefix.js#}html/calendrierPopup.html', Oarguments, "dialogWidth:258px; dialogHeight:375px; scroll:no; help:yes; status:yes");
		} else { 
			window.showModalDialog('{#optiflux.compression.prefix.js#}html/calendrierPopup.html', Oarguments, "dialogWidth:258px; dialogHeight:375px; scroll:no; help:no; status:no");
		}
	} else {
		if (focus) {
			if ((focus == true) || (focus == false)) {
				FocusChamp = focus;
			}
		} else {
			FocusChamp = false;
		}
		var ret = newShowModalDialog('{#optiflux.compression.prefix.js#}html/myModalPopup4.html', o, m, a, ad, af, f, l);
	}
}


function fabriquerCalendrier() {
	if(ie && ie_version >= 5.5) {
		objet = window.dialogArguments.objet;
		mois = window.dialogArguments.mois;
		annee = window.dialogArguments.annee;
		format = window.dialogArguments.format;
		langue = window.dialogArguments.langue;
		anneeDebut = window.dialogArguments.anneeDebut;
		anneeFin = window.dialogArguments.anneeFin;
	} else {
		req = unescape(window.location.search.substr(1,window.location.search.length));
		dbl = req.split("&Arg=");
		Tab = dbl[1].split('|');

		mois = Tab[0];
		annee = Tab[1];
		anneeDebut = Tab[2];
		anneeFin = Tab[3];
		format = Tab[4];
		langue = Tab[5];

/*********************
mois = 10;
annee = 2006;
anneeDebut = 1998;
anneeFin = 2008;
format = "dd/mm/yyyy";
langue = "fr";
*********************/
	}

	try  {
		tableJours = new Array(42);
	
	    	var tmpObj = document.getElementById('tableCalendrier');
		var x, y;
		var i = 0;
	
	
		var pair = false;	
	
		for(y = 1; y < document.getElementById('tableCalendrier').rows.length; y++) { /* fabrique un tableau de 6x7 (tous les jours du mois) */
			if (pair) {
				document.getElementById('tableCalendrier').rows[ y ].className = "clair"
				pair = false;
			} else {
				pair = true;
			}
			for(x = 0; x < 7; x++) {
				tableJours[ i ] = document.getElementById('tableCalendrier').rows[ y ].cells[ x ];
				//alert("Objet : " + tableJours[ i ] + "  " + + tableJours[ i ].id);
				i++;
			}
		}
		var option;
	
		/* ecrit les noms des jours (titre de colonnes)*/
		for(var i= 0 ; i < 7 ; i++) {
			document.getElementById('tableCalendrier').rows[ 0 ].cells[ i ].innerHTML = les_jours[langue][ i ];
			document.getElementById('tableCalendrier').rows[ 0 ].cells[ i ].className = "thead";
		}


		var Fin = (parseInt(anneeFin) + 1);
		var boucle = 0;
		for(i = anneeDebut; i < Fin; i++) { /*fabrique la liste deroulante des annees*/
			var optTemp = new Option(i , i);		
			document.getElementById('listeAnnees').options[ boucle ] = optTemp;
			boucle += 1;
		}
	
		for(i = 1; i < 13; i++) { /*fabrique la liste deroulante des mois*/
			var optTemp = new Option(les_mois[langue][ i - 1 ], i);	
			document.getElementById('listeMois').options[ i - 1 ] = optTemp;
		}
	
		document.getElementById('listeMois').value = mois;
		document.getElementById('listeAnnees').value = annee;
	}  catch(e)  { 
		alert("Erreur : " + e);
	}
	rafraichirCalendrier();
}

function rafraichirCalendrier() {
	var premierJour = premierJourDuMois(mois, annee);
	var nombreDeJours = nombreDeJoursDuMois(mois, annee);


	var tmpTD;
	
	var Tmp = "";
	
	
	var now   = new Date();
	var JourJ = now.getDate();
	var month = now.getMonth();
	var year = now.getYear();
	if(year < 999) year+=1900;

	try {
		for(i = 0; i < premierJour; i++) {
			tmpTD = tableJours[i];
			tmpTD.innerHTML = "";
			tmpTD.onmouseover = "";
			tmpTD.onmouseout = "";
			tmpTD.onclick = "";
			tmpTD.className = "nodate";
		}
	
	
		for(i = 0; i < nombreDeJours; i++) {
			tmpTD = tableJours[i + premierJour];
	
			tmpTD.onmouseover = function() {this.className="dateover"};
			tmpTD.onclick = function() {fermerCalendrier(this.innerHTML)};
			try{ tmpTD.style.cursor= "pointer"; } catch(e) { tmpTD.style.cursor= "hand"; }
			tmpTD.className = "date";

			//tmpTD.innerHTML = (i + 1);
			if ( (JourJ == (i + 1)) && (document.getElementById('listeMois').options[document.getElementById('listeMois').selectedIndex].value == (month + 1)) && (document.getElementById('listeAnnees').options[document.getElementById('listeAnnees').selectedIndex].value == (year)) ) {
				tmpTD.innerHTML = (i + 1);
				tmpTD.className = "date lejour";
				tmpTD.onmouseout = function() {this.className="date lejour"};
			} else {
				tmpTD.innerHTML = (i + 1);
				tmpTD.onmouseout = function() {this.className="date"};
			}
		}
	
		for(i = nombreDeJours + premierJour; i < 42; i++) {
			tmpTD = tableJours[i];
			//tmpTD.innerText = "";
			tmpTD.innerHTML = "";
			tmpTD.onmouseover = "";
			tmpTD.onmouseout = "";
			tmpTD.onclick = "";
			tmpTD.className = "nodate";
		}
	}  catch(e)  { 
		alert("Erreur : " + e);
	}
}


function fermerCalendrier(j) {
	var chaineDate = formaterDate(j, mois, annee);

	if(ie && ie_version >= 5.5) {
		objet.value = chaineDate;
		if (window.dialogArguments.focus == true) {
			objet.focus();
		}
		
	} else {
		window.opener.modalCallBackFunction(window.self,chaineDate);
	}
	window.close();
}




function CodageFormatDate(format) {	
	var retour = format;
	try {
		for (i=0; i < caract_interdits.length; i++) {
			if (retour.indexOf(caract_interdits[0][i]) > 0) {
				while (retour.indexOf(caract_interdits[0][i]) > 0) {
					retour = retour.replace(caract_interdits[0][i],caract_interdits[1][i]);
				}
			}
		}
	} catch (e) { alert("CodageFormatDate : " + e); }
	return retour;
}







function formaterDate(_j, _m, _a) {
	var retour= format;
	if ((retour == null) || (retour == undefined)) {
		retour= "dd/MM/yyyy";
	}
	else {
		if(retour == "mm/jj/aaaa") {
			retour= "MM/dd/yyyy"; /*compatibilite avec les anciennes versions*/
		}
	}
	var j = (_j<10)? "0" + _j : _j;
	var m = (_m<10)? "0" + _m : _m;
	var a2 = _a + "";
	var a2 = a2.substring(a2.length - 2, a2.length);
	retour = retour.replace(/dd/g, j);
	retour = retour.replace(/d/g, _j);
	retour = retour.replace(/MM/g, m);
	retour = retour.replace(/M/g, _m);
	retour = retour.replace(/yyyy/g, _a);
	retour = retour.replace(/yy/g, a2);
	
	return(retour);
}

function nombreDeJoursDuMois(m, a) { /*indique le nombre de jours du mois m de l'annee a*/
	if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
		return(31);
	} else if(m == 4 || m == 6 || m == 9 || m == 11) {
		return(30);
	} else { /*donc m = 2 (fevrier) et nombre de jours variable en fonction de l'annee a*/
		var test = a - 1900;
		if( (test == 100) || (test % 4 == 0 && test % 100 != 0) || (test % 400 == 0) ) {
			return(29);
		} else {
			return(28);
		}
	}
}

function premierJourDuMois(m, a) { /*indique le premier jours du mois m de l'annee a*/

	var premierJour = new Date(a, m - 1, 1);
	var reponse = premierJour.getDay() - 1;
	if(reponse == -1) {
		reponse = 6;
	}
	return(reponse); /*0:lundi 1:mardi 2:mercredi 3:jeudi 4:vendredi 5:samedi 6:dimanche*/
}

/**********************************************/
/*   Modal Dialog cross broswer               */
/**********************************************/


function Traite_Arguments(oArg) {
	var Lg = "fr";
	var stArguments = "";
	try {
		stArguments += oArg.mois + "|";
		stArguments += oArg.annee + "|";
		stArguments += oArg.anneeDebut + "|";
		stArguments += oArg.anneeFin + "|";		
		if (oArg.format) {
			stArguments += CodageFormatDate(oArg.format) + "|";
		} else {
			stArguments += CodageFormatDate("dd/MM/yyyy") + "|";
		}
		
		stArguments += oArg.langue;
	} catch(e) { alert("PB Traite_Arguments : " + e); }
	//alert("stArguments " + stArguments);
	return stArguments;
}




//function: myShowModalDialog(url,mode,reference)
//  arguments:
//   -url : url to open in the popup
//   -mode : innerHTML | value | function | functionRef | style.<JS_style_property>
//   -reference :
//    	avec le mode 'innerHTML' ou 'value' il s'agit de l'id de l'element dans
//    	le DOM qu'il faudra mettre à jour
//    	en mode 'function' il s'agit du nom de la fonction sous forme de chaine
//      alors qu'avec 'functionRef' il s'agit de la function elle même (pointeur)
//   -windowParam: troisiement argument de window.open


//function newShowModalDialog(url,mode,ref,windowParam) {
function newShowModalDialog(url, ref, m, a, ad, af, f, l) {
	var Oarguments = new Arguments(ref, m, a, ad, af, f, l);
/************************************
	Oarguments.mois = 10;
	Oarguments.annee = 2006;
	Oarguments.anneeDebut = 2000;
	Oarguments.anneeFin = 2008;
	Oarguments.format = "fr";
************************************/


	var mode = "champ";
	var windowParam = "modal=yes,directories=0,menubar=0,titlebar=0,toolbar=0,width=350,height=290";
	var reference = ref.id;
	
	
	
	var winOpenned=MODAL_alreadyOpen(mode,reference);
	if(winOpenned!=null) { winOpenned.focus(); return; }
	var id = MODAL_DIALOG_LIST.length;
	//var newWin = window.open(url,'modal_'+id,windowParam);
	//alert("Oarguments : " + Oarguments.mois);

	var newWin = window.open(url + '?&Arg=' + Traite_Arguments(Oarguments),'modal_' + id, windowParam);
	MODAL_DIALOG_LIST.push(Array(newWin,mode,reference,true));  //format win, mode, referecene , isOpen
}

//Fonction que doit appeler la fenetre modale pour mettre transmettre la nouvelel valeur
// Exemple de code a placer dans votre fenetre modale :
//   window.opener.modalCallBackFunction(window.self,'MA VALEUR');
function modalCallBackFunction(winRef,newValue) {
	if(newValue==null) { return; } //pas de valeur renvoyé
	
  	var modalObj=MODAL_findModalByWinRef(winRef);
	if(MODAL_findModalByWinRef==null)  //impossible de trouver la modal correspondante
	{
		alert('Error : unable to found window reference for callBack procedure.');
	} else {
		var modeList=Array();
		modeList=modalObj[1].split('|');
		for(var i=0 ; i<modeList.length ; i++) {
			switch(modeList[i]) {
				case 'innerHTML' :
					var tmp=document.getElementById(modalObj[2]);
					if(tmp) { tmp.innerHTML=newValue; }
					break;
				case 'champ' :
					var tmp = document.getElementById(modalObj[2]);
					if(tmp) { tmp.value = newValue; }
					if (FocusChamp == true) tmp.focus();
					break;
				case 'value' :
					var tmp=document.getElementById(modalObj[2]);
					if(tmp) { tmp.value=newValue; }
					break;
				case 'function' :
					eval(modalObj[2]+'(\''+newValue+'\')');
					break;
				case 'functionRef' :
					modalObj[2](newValue);
					break;
				default :
					if(modeList[i].indexOf('style.')==0)  //exemple style.backgroundColor
					{
						//ATTENTION newValue doit être correctement formatte,
						//a vous de faire en sorte que votre fenetre modale renvoie la bonne valeur avec le bon formatage
						// exemeple qu'une couleur soit au format '#ff0033' ou rgb(255,0,85) etc...
						try
						{
							eval('document.getElementById(\''+modalObj[2]+'\').'+modeList[i]+'=\''+newValue+'\';');
						} catch(e) { ; }
					}
					break;
			}
		}
	}
}

//---- Inutile de regarder plus bas, seul la fonction newShowModalDialog est a appeler pour construire  une fenetre modal --//

// Variables globales du script
var MODAL_DIALOG_LIST=Array();

//méthode globales du script
function MODAL_alreadyOpen(mode,reference) {
	for(var i=0 ; i<MODAL_DIALOG_LIST.length ; i++) {
		if(MODAL_DIALOG_LIST[i][1]==mode && MODAL_DIALOG_LIST[i][2]==reference && MODAL_DIALOG_LIST[i][3]) {
		  //on test si elle est encore ouverte
		  if(MODAL_DIALOG_LIST[i][0].document) { return MODAL_DIALOG_LIST[i][0];
		  } else { //la fenetre a été fermée sans qu'elle est pu prevenir la fenetre mere, on corrige donc le flag isOpen
		  	MODAL_DIALOG_LIST[i][3]=false;
		  }
		}
	}
	return null;
}

function MODAL_findModalByWinRef(winRef) {
	for(var i=0 ; i<MODAL_DIALOG_LIST.length ; i++) {
		if(MODAL_DIALOG_LIST[i][0] == winRef && MODAL_DIALOG_LIST[i][3]) {
		  //on test si elle est encore ouverte
		  if(MODAL_DIALOG_LIST[i][0].document) { return MODAL_DIALOG_LIST[i];
		  } else {   //la fenetre a été fermée sans qu'elle est pu prevenir la fenetre mere, on corrige donc le flag isOpen
		  	MODAL_DIALOG_LIST[i][3]=false;
		  }
		}
	}
	return null;
}

//force le focus sur les modal encore ouverte (en thoerie il ne peut y en avoir qu'une seule encore d'ouverte)
function MODAL_forceFocusOnModal() {
	for(var i=0 ; i<MODAL_DIALOG_LIST.length ; i++) {
		if(MODAL_DIALOG_LIST[i][3] && MODAL_DIALOG_LIST[i][0].document) { MODAL_DIALOG_LIST[i][0].focus(); }
	}
	return;
}

//ajout d'un handler sur la reprise du focus, permettant d'imediatement redonner le focus a une eventuelle modal encore ouverte
if (document.addEventListener) { document.addEventListener("focus", MODAL_forceFocusOnModal, true); }
else if (document.attachEvent) { document.attachEvent("onfocus", MODAL_forceFocusOnModal); }
