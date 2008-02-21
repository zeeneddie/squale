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
05/07/2006 EM Mise en gras du jour.
*/

les_jours= new Array();
les_mois= new Array();
les_annees= new Object();

function Arguments(o, m, a, ad, af, f, l) {
	if (m == null) {
		d = new Date();
		m = d.getMonth() + 1;
	}
	if (a == null) {
		d = new Date();
		a = d.getFullYear();
	}
	if (ad == null) {
		d = new Date();
		ad = d.getFullYear() - 3;
	}
	if (af == null) {
		d = new Date();
		af = d.getFullYear() + 3;
	}
	this.objet = o;
	this.mois = m;
	this.annee = a;
	this.anneeDebut = ad;
	this.anneeFin = af;
	this.format = f;
	if(l != null) {
		this.langue= l.substring(0, 2); /* english -> en */
	}
	else {
		this.langue= "fr";
	}
	inclureDico(this.langue + ".js");
	this.les_jours= les_jours;
	this.les_mois= les_mois;
	this.les_annees= les_annees;
}

function afficherCalendrier(page, o, m, a, ad, af, f, l) {
    var arguments = new Arguments(o, m, a, ad, af, f, l);
	if(screen.width>1279) /* modif du 2005/09 */
		{ window.showModalDialog(page, arguments, "dialogWidth:212px; dialogHeight:220px; scroll:no; help:no; status:no") }
	else 
		{ window.showModalDialog(page, arguments, "dialogWidth:212px; dialogHeight:190px; scroll:no; help:no; status:no") }
}

var objet, mois, annee, format, langue, anneeDebut, anneeFin, tableJours;

function fabriquerCalendrier() {
	objet = window.dialogArguments.objet;
	mois = window.dialogArguments.mois;
	annee = window.dialogArguments.annee;
	format = window.dialogArguments.format;
	langue = window.dialogArguments.langue;
	anneeDebut = window.dialogArguments.anneeDebut;
	anneeFin = window.dialogArguments.anneeFin;
	les_jours= window.dialogArguments.les_jours;
	les_mois= window.dialogArguments.les_mois;
	les_annees= window.dialogArguments.les_annees;
	tableJours = new Array(42);
	
    var tmpObj = document.all.tableCalendrier;
	var tmpLigne, tmpCellule, x, y;
	var i =0;
	for(y = 1; y < 7; y++) { /*fabrique un tableau de 6x7 (tous les jours du mois)*/
		tmpLigne = tmpObj.rows(y)
		for(x = 0; x < 7; x++) {
			tableJours[i] = tmpLigne.cells(x);
			i++;
		}
	}
	var option;
	/*ecrit les noms des jours (titre de colonnes)*/
	for(var i= 0 ; i < 7 ; i++) {
		document.all.tableCalendrier.rows(0).cells(i).innerText = les_jours[i];
	}
	
	for(i = anneeDebut; i < anneeFin+1; i++) { /*fabrique la liste deroulante des annees*/
		option = document.createElement("OPTION");
		option.text = les_annees["pre"] + i + les_annees["post"];
		option.value = i;
		document.all.listeAnnees.add(option);
	}
	for(i = 1; i < 13; i++) { /*fabrique la liste deroulante des mois*/
		option = document.createElement("OPTION");
		option.text = les_mois[i-1];
		option.value = i;
		document.all.listeMois.add(option);
	}
	
	document.all.listeMois.value = mois;
	document.all.listeAnnees.value = annee;
	rafraichirCalendrier();
}

function rafraichirCalendrier() {
	var premierJour = premierJourDuMois(mois, annee);
	var nombreDeJours = nombreDeJoursDuMois(mois, annee);


	var Tmp = "";
	
	
	var now   = new Date();
	var JourJ = now.getDate();
	var month = now.getMonth();
	var year = now.getYear();
	if(year < 999) year+=1900;



	var tmpTD;

	for(i = 0; i < premierJour; i++) {
		tmpTD = tableJours[i];
		tmpTD.innerText = "";
		tmpTD.onmouseover = "";
		tmpTD.onmouseout = "";
		tmpTD.onclick = "";
		tmpTD.className = "nodate";
	}
	for(i = 0; i < nombreDeJours; i++) {
		tmpTD = tableJours[i + premierJour];
		// Modif le 05/07/2006
		//tmpTD.innerText = i + 1;
		if ( (JourJ == (i + 1)) && (document.forms[0].listeMois.options[document.forms[0].listeMois.selectedIndex].value == (month + 1)) && (document.forms[0].listeAnnees.options[document.forms[0].listeAnnees.selectedIndex].value == (year)) ) {
			tmpTD.innerHTML = "<b>" + (i + 1) + "</b>";
		} else {
			tmpTD.innerHTML = (i + 1);
		}


		tmpTD.onmouseover = function() {this.className="dateover"};
		tmpTD.onmouseout = function() {this.className="date"};
		tmpTD.onclick = function() {fermerCalendrier(this.innerText)};
		try{ tmpTD.style.cursor= "pointer"; } catch(e) { tmpTD.style.cursor= "hand"; }
		tmpTD.className = "date";
	}
	for(i = nombreDeJours + premierJour; i < 42; i++) {
		tmpTD = tableJours[i];
		tmpTD.innerText = "";
		tmpTD.onmouseover = "";
		tmpTD.onmouseout = "";
		tmpTD.onclick = "";
		tmpTD.className = "nodate";
	}
}

function fermerCalendrier(j) {
	var chaineDate = formaterDate(j, mois, annee);
	objet.value = chaineDate;
	window.close();
}

function formaterDate(_j, _m, _a) {
	var retour= format;
	if(retour == null) {
		retour= "dd/MM/yyyy";
	}
	else if(retour == "mm/jj/aaaa") {
		retour= "MM/dd/yyyy"; /*compatibilite avec les anciennes versions*/
	}
	var j= (_j<10)? "0" + _j : _j;
	var m= (_m<10)? "0" + _m : _m;
	var a2= _a + "";
	var a2= a2.substring(a2.length - 2, a2.length);
	retour= retour.replace(/dd/g, j);
	retour= retour.replace(/d/g, _j);
	retour= retour.replace(/MM/g, m);
	retour= retour.replace(/M/g, _m);
	retour= retour.replace(/yyyy/g, _a);
	retour= retour.replace(/yy/g, a2);
	
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

function inclureDico(dico) {
	var s= document.getElementsByTagName("script");
	var ln= s.length;
	var chemin= null;
	for(var i= 0 ; i < ln ; i++) {
		if(s[i].src.indexOf("comCalendrierPopup.js") != -1) {
			chemin= s[i].src.substring(0, s[i].src.lastIndexOf("/") + 1);
			break;
		}
	}
	if(chemin == null) {
		alert("comCalendrierPopup.js introuvable\nVous ne devez pas renommer comCalendrierPopup.js!");
	}
	var head= document.getElementsByTagName("head")[0];
    var script= document.createElement("script");
    script.type= "text/javascript";
    script.src= chemin + dico;
    head.appendChild(script);
}
