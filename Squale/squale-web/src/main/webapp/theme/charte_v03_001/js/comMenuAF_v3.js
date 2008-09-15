/* comMenuAF_v2 1.3 */
/*
08.12.2003	1.0		Creation -Tristan DANIEL (DI.NB/IC)
23.02.2004	1.1		couleurs de fond "mouseover" du type 1 (vertical) modifiees -Tristan
17.03.2004	1.1.1	suppression de tous les caracteres accentues pour compatibilite portail -Tristan
13.04.2004	1.1.2	reorganisation plus logique du code de Menu.ouvrir et de Element.survolElement -Tristan
14.04.2004	1.1.3	optim: mutualisation des getElementById dans des proprietes objetHTML pour Menu et Element, accesseur getObjetHTML -Tristan
			1.1.4	bugfix: les sous menus etaient caches par les menu parents, ils passaient derriere -Tristan
16.04.2004	1.1.5	meilleure gestion du z-index dans survolElement pour qu'un "nouveau" menu n'apparaisse pas derriere un menu deja a l'ecran: 2 pour les "barres de titres", 3 pour un menu duquel on sort, 4 pour un menu fraichement ouvert -Tristan
19.04.2004	1.1.6	bugfix: comMenuAF_v2_masquerCombos masque les Combos avant l'apparition d'un menu et inversement -Tristan
			1.1.7	Un click de souris sur la page et tous les menus disparaissent comMenuAF_v2_onclick -Tristan
22.04.2004	1.1.8	bugfix: alignement des sous menus verticaux pour ie5 et ie6 -Tristan
			1.1.9	bugfix: dans ie5, les titres du menu horizontal etaient trop petits (leur hauteur ne couvrait pas tout le bailladere) -Tristan
			1.2		release -Tristan
26.04.2004	1.2.1	Affichage des libelles longs sur plusieurs lignes (merci Frédéric Demoors) -Tristan
30.07.2004	1.2.2	bugfix: remplacement des commentaires slash-slash par slash-etoile pour compatibilite avec les mauvais-ftp-unix-windows -Tristan
					ajout des fonctions comMenu.activerAncre(index) pour activer un des titres de menu; et Element.activerElement() pour maintenir allume n'importe quel element -Tristan
30.07.2004	1.2.4	ajout de la propriete comMenuAF_v2_gestionBugCombos pour activer ou desactiver cette correction de bug apportee par la version 1.1.6 -Tristan
05.10.2004	1.2.12	Compatibilité avec comTraceurAF_v2 1.2.12 (méthode _comMenuAF_v2_listerParentsPourTraceur modifiée) -Tristan
12.10.2004	1.2.13	bugfix: la méthode _comMenuAF_v2_listerParentsPourTraceur(xx, false) ne soulignait plus aucun lien -Tristan
14.10.2004	1.2.14	bugfix: avec un seul niveau, le traceur était toujours souligné -Tristan
xx.xx.2004	1.2.14b	optim: version sans commentaires, renommage des variables "comMenuAF_v2..." vers "mv2..." -Gael
10.11.2004	1.2.14fix	bugfix: repassage de "mVx_gestionBugCombos" vers "comMenuAF_v2_gestionBugCombos" pour restaurer la compatibilité avec l'API des versions prec -Tristan
10.11.2004	1.2.15	Affichage des libelles longs sur plusieurs lignes pour les ancres du menu vertical -Tristan
02.02.2005	1.2.16	Modifs suite à la mise à niveau agence web - gael
22.04.2005	1.2.17	Detection ecran possible pour TFT 1280 et passage de la police en 12px - GL
08.09.2005	1.2.18	cursor:hand -> cursor:pointer (spec css w3c) -TD
16.09.2005	1.2.19	Les sous menus passent maintenant sur les combos sous ie5.5+ (Joe King). On masque toujours les combos en ie5 -TD
12.01.2006	1.2.20	cursor à la fois hand (pour ie5) et pointer (standard) -TD
08.03.2006	1.2.21	recherche par libelle et par action pour le traceur (recherche d'une sous-chaine plutot qu'une chaine identique) mVx_getElement -TD
21.03.2006	1.2.41	activerAncre(rech) si rech est une chaîne de caractères, recherche dans le libellé et dans l'action des éléments pour activer le titre du menu "ancre" -TD
21.03.2006	1.2.42	Compatibilité Opera et Firefox 80% -> 95% (maintenant c'est suffisant) -TD
30.03.2006	1.2.43	bugfix: alignement des textes avec flèches dans IE5.x -TD
30.03.2006	1.3		Compatibilité 99.99% Opera Firefox -TD
*/

/*********** charte V3 ***********/
/*
15/05/2006	1.4	Mise à la charte V3 - EM
07/11/2006 	1.4.1	suppr des variables Menu_Vertical_Application et Menu_Horizontal_Application etude du tab Body et remplacement nécessaires
			dans le initMenu(). initMenuV et initMenuH sont remplacées par initMenu ! - EM
19/12/2006	1.4.2	corrections de padding-left(decalage du libellé) margin-right(position de la fleche) à 16px pour les sous menu horizontaux - GL
08/01/2007 	1.5	L'affichage du menu est géré automatiquement par "addListener(window,'load',initM)" la fonction "initMenu" n'est plus nécessaire.
12/02/2007  1.5.1 -GL-correction du décalage de 24px (passé à 0px) du niveau 1 vers la droite suite modif pour ecran 1024. cet ajout de 24px engendrait une taille du div #navigation > à 1000px !
*/







/******** By EM ********/

var Menu_Horizontal_Application = false;/* Pour la gestion du menu horizontal des applications  */
						/* false : cas classique décalage */
						/* true  : pas décalage pour une application */


var TypeMenuCourrant = 0;


nomDIVGauche = "gauche";
var MenuDebug = "";
var ecritureMenuV = "";
var ecritureMenuH = "";
MenuHorizontal = 0;
MenuVertical = 1;



flecheAvant = false;



/*
Compagnie = 0;
lesMetiers = 1;
cotePratique = 2;
parcoursProfessionnel = 3;
entreNous = 4;
environnement = 5;
*/


grisClair = "#ECEAE7";
grisFonce = "#252525";
grismoyen ="#E1DDD5";

bleuFonce = "#2e5880";
bleu = "#4f7fad";
blanc = "#FFF";

CouleurFleche = bleuFonce;

CouleurCompagnie = "#0D3F96";
CouleurMetiers = "#f3ba5d";
CouleurPratique = "#0094D5";
CouleurParcours  = "#FBC100";
CouleurEntrenous = "#A3D900";
CouleurEnvironnement = "#822F82";
/***********************/


/*Position top du menu vertical Valeur donnée en px : 110 pour les sites de publication et au choix pour les Applications  PLUS UTILISE !!!! */
/*mVx_style_top_menuv = 81;*/


/*largeur des sous menu horizontaux*/
mVx_style_width_smenuh = "160px";




mVx_style_width_menuv_app = 180;/* 180 car il y a un -20 ensuite dans la calcul et donc ca donne 160 !! */
/* Menu vertical normal !!! */
mVx_style_width_menuv = 250;





/*largeur des menu et sous menu verticaux*/
mVx_style_width_smenuv = "160px";
/* taille de la police */
mVx_style_font_size = "10px";

/* utilisez la ligne suivante si vous voulez detecter la résolution ecran 1280 et modifier la taille de la police du menu */
/*if (screen.width >= 1280) {mVx_style_font_size = "12px";}*/



mVx_style_police = new Array("font-family:Verdana,Arial,Helvetica,sans-serif;font-weight:bold;font-size:" + mVx_style_font_size + ";", "font-family:Verdana,Arial,Helvetica,sans-serif;font-weight:bold;font-size:" + mVx_style_font_size + ";");

mVx_style_couleur_separateur = new Array(blanc, "#D3D3D3");
mVx_style_couleur_ancre_element = new Array(
	new Array(blanc, blanc, blanc, blanc, blanc, blanc),
	new Array(grisFonce, grisFonce, grisFonce, grisFonce, grisFonce, grisFonce)
);


/*
mVx_style_couleur_ancre_element_survol = new Array(
	new Array(grisFonce, blanc, blanc, blanc, blanc, blanc),
	new Array(CouleurCompagnie, CouleurMetiers, CouleurPratique, CouleurParcours, CouleurEntrenous, CouleurEnvironnement)
);
*/


mVx_style_couleur_ancre_element_survol = new Array(
	new Array(grisFonce, grisFonce, grisFonce, grisFonce, grisFonce, grisFonce),
	new Array(CouleurCompagnie, CouleurMetiers, CouleurPratique, CouleurParcours, CouleurEntrenous, CouleurEnvironnement)
);


mVx_style_couleur_ancre_element_fond = new Array(
	new Array("transparent", "transparent", "transparent", "transparent", "transparent", "transparent"),
	new Array(blanc, blanc, blanc, blanc, blanc, blanc)
);

mVx_style_couleur_ancre_element_fond_survol = new Array(
	new Array(blanc, blanc, blanc, blanc, blanc, blanc),
	new Array(grisClair, grisClair, grisClair, grisClair, grisClair, grisClair)
);



mVx_style_couleur_element = new Array(
	new Array("#DDF", "#252525", "#252525", "#252525", "#252525", "#252525"),
	new Array(grisFonce, grisFonce, grisFonce, grisFonce, grisFonce, grisFonce)
);
mVx_style_couleur_element_survol= new Array(
	new Array(grismoyen, grismoyen, grismoyen, grismoyen, grismoyen, grismoyen), 
	new Array(CouleurCompagnie, CouleurMetiers, CouleurPratique, CouleurParcours, CouleurEntrenous, CouleurEnvironnement)
);

mVx_style_couleur_element_fond = new Array(
	new Array(grismoyen, grismoyen, grismoyen, grismoyen, grismoyen, grismoyen),
	new Array(grisClair, grisClair, grisClair, grisClair, grisClair, grisClair)
);

mVx_style_couleur_element_fond_survol = new Array(
	new Array(bleu, bleu, bleu, bleu, bleu, bleu),
	new Array(grisClair, grisClair, grisClair, grisClair, grisClair, grisClair)
);


/*en millisecondes */
mVx_delaiFermeture= 2 * 1000;

/*ligne suivante a mettre en commentaire si on ne veux pas utiliser cette fonction de fermeture de tous les menus sur un clic de la souris*/
document.onclick= mVx_onclick;

/*-- fin config*/

mVx_prefixeID= "_mVx_";

mVx_zIndex_bas= 10;
mVx_zIndex_moyen= 20;
mVx_zIndex_haut= 30;

mVx_combosMasquees= false;
mVx_ancres= new Array();

/* ces deux var forment une table-hash pour retrouver un objet avec son id*/
mVx_menus_ids= new Array();
mVx_menus_objets= new Array();

/* compatibilite ie5, ie6*/
mVx_decalage_sousMenu_top= -1; 

mVx_decalage_sousMenu_left= 0;/*1*/


mVx_ie= false;
if((verOffset= navigator.userAgent.indexOf("MSIE")) != -1) {
	mVx_ie= true;
	mVx_ie_version= parseFloat(navigator.userAgent.substring(verOffset + 5, navigator.userAgent.length));
	if(mVx_ie_version == 5.5) {
		/*ie5.5*/
		mVx_decalage_sousMenu_top= -2;
	}
}

mVx_ns4= (document.layers);
mVx_ie4= (document.all && !document.getElementById);
if(mVx_ie4) {
	document.getElementById= function(id) { return(document.all(id)); }
	document.getElementsByTagName= function(id) { return(document.all.tags(id)); }
}
if(mVx_ns4) {
	document.getElementById= function(id) { return(document.layers[id]); }
}


























/* - objet comMenuAF_v2 -- POUR GARDER LA COMPATIBILITE !!!! --------------------------------------------------------------------------*/
function comMenuAF_v2(_type, _idMenuAncre) {
	/* 0: horizontal ou 1: vertical*/
	this.type= _type;
	TypeMenuCourrant = this.type;
	
	
	 
	this.indexTraceur= "";
	this.menuAncre = new mVx_Menu(this, mVx_prefixer(_idMenuAncre), null, null);
	this.menuCourant= null;
	
	this.listerParentsPourTraceur= _mVx_listerParentsPourTraceur;
	this.ajouterElement= _mVx_ajouterElement;
	this.construire= _mVx_construire;
	this.activerAncre= _mVx_activerAncre;

	mVx_ancres[mVx_ancres.length]= this;
	mVx_menus_ids[mVx_menus_ids.length]= this.menuAncre.id;
	mVx_menus_objets[mVx_menus_objets.length]= this.menuAncre;
}


/* - objet comMenuAF_Vx -------------------------------------------------------------------------------------------------------------*/
function comMenuAF_Vx(_type, _idMenuAncre) {
	/* 0: horizontal ou 1: vertical*/
	this.type= _type;
	TypeMenuCourrant = this.type;
	
	
	 
	this.indexTraceur= "";
	this.menuAncre = new mVx_Menu(this, mVx_prefixer(_idMenuAncre), null, null);
	this.menuCourant= null;
	
	this.listerParentsPourTraceur= _mVx_listerParentsPourTraceur;
	this.ajouterElement= _mVx_ajouterElement;
	this.construire= _mVx_construire;
	this.activerAncre= _mVx_activerAncre;

	mVx_ancres[mVx_ancres.length]= this;
	mVx_menus_ids[mVx_menus_ids.length]= this.menuAncre.id;
	mVx_menus_objets[mVx_menus_objets.length]= this.menuAncre;
}




/*-------- comMenu.ajouterAncreElement()*/
function _mVx_ajouterElement(_idmenu, _libelle, _theme, _action, _idsousmenu) { 	
	/*recup du menu parent de l element*/
	var strmenu= mVx_prefixer(_idmenu);
	var strsousmenu= mVx_prefixer(_idsousmenu);
	var lemenu= mVx_getMenu(strmenu);
	var mon_theme= (_theme != null)? _theme : (lemenu.theme != null)? lemenu.theme : 0;

	var mon_element=  new mVx_Element(this, lemenu, lemenu.elements.length, _libelle, mon_theme, _action, null);
	lemenu.elements[lemenu.elements.length]= mon_element;
	/*creation d un (sous) menu*/
	if(_idsousmenu != null) { 
		mVx_menus_ids[mVx_menus_ids.length]= strsousmenu;
		var mon_sousmenu =  new mVx_Menu(this, strsousmenu, mon_element, mon_theme);
		mVx_menus_objets[mVx_menus_objets.length]= mon_sousmenu;
		
		mon_element.sousMenu= mon_sousmenu;
	}
}
/*-------- comMenu.construire()*/
function _mVx_construire() {
	if (TypeMenuCourrant == 0) {
		ecritureMenuH = '<div id="zoneMenus">';
	} else {
		ecritureMenuV = '<div id="MenuV">';
	}
	
	var ma_longueur= mVx_menus_objets.length;
	for(var i = 0; i < ma_longueur; i++) {
		if(mVx_menus_objets[i].ancre == this) {
			mVx_menus_objets[i].construire();
		}
	}

	if (TypeMenuCourrant == 0) {
		ecritureMenuH += '</div>';
	} else {
		ecritureMenuV += '</div>';
	}
	
	
	

	
	/* creation des styles necessaires pour les menus . Ceci pour supprimer l'utilisation de menu.css comme auparavant */
	/*
	var zoneMenus_feuilleDeStyle= document.createStyleSheet();
	zoneMenus_feuilleDeStyle.addRule("#uni #navigation #zoneMenus","text-align:left;margin-top:0;margin-right:auto;margin-left:auto;padding-left:24px !important;_padding-left:24px;width:1000px;");
	zoneMenus_feuilleDeStyle.addRule("#maj #navigation #zoneMenus","text-align:left;margin-top:0;margin-right:auto;margin-left:auto;padding-left:24px !important;_padding-left:24px;width:1000px;");
	zoneMenus_feuilleDeStyle.addRule("#uni #gauche #MenuV","margin-top:0;margin-left:-20px;");
	zoneMenus_feuilleDeStyle.addRule("#maj #gauche #MenuV","margin-top:0;margin-left:-20px;");
	*/
}




/*-------- comMenu.activerAncre()*/
function _mVx_activerAncre(_select) {
	switch(typeof(_select)) {
	case "number":
		if(_select-1 <= this.menuAncre.elements.length) {
			this.menuAncre.elements[_select-1].activerElement();
		}
		break;
	case "string":
		var tmparbo= this.listerParentsPourTraceur(_select, true);
		if(tmparbo.length > 0) {
			mVx_getElement(tmparbo[tmparbo.length-2]).activerElement();
		}
		break;
	}
}

function _mVx_listerParentsPourTraceur(_element_rech, _action_page_courante) {
	var mon_element_original= mVx_getElement(_element_rech);
	var mon_element= mon_element_original;
	var mon_tableau= new Array();
	var i=0;
	while(mon_element != null) {
		mon_tableau[i++]= mon_element.libelle;
		mon_tableau[i++]= (!_action_page_courante && mon_element == mon_element_original)? null : mon_element.action;
		mon_element= mon_element.parent.parent;
	}
	return(mon_tableau);
}

/* - objet Menu ---------------------------------------------------------------------------------------------------------------------*/

function mVx_Menu(_ancre, _id, _elementparent, _theme) {


	this.ancre= _ancre;
	this.id= _id;
	this.objetHTML = null;
	this.iframeHTML= null;
	this.parent= _elementparent;
	this.theme= _theme;
	this.elements= new Array();
	
	this.getObjetHTML= _mVx_Menu_getObjetHTML;
	this.getIframeHTML= _mVx_Menu_getIframeHTML;
	this.ouvrir= _mVx_Menu_ouvrir;
	this.fermer= _mVx_Menu_fermer;
	this.fermerSousMenus= _mVx_Menu_fermerSousMenus;
	this.eteindreElementsR= _mVx_Menu_eteindreElementsR;
	this.construire= _mVx_Menu_construire;
}

function _mVx_Menu_getObjetHTML() {
	if(this.objetHTML == null) {
		this.objetHTML = document.getElementById(this.id);
	}

	return(this.objetHTML);
}

function _mVx_Menu_getIframeHTML() {
	if(this.iframeHTML == null) {
		this.iframeHTML= document.getElementById(this.id + "_iframe");
	}
	return(this.iframeHTML);
}



/*
	By EM utilisée dans : "_mVx_Menu_ouvrir"
*/
function fDomOffset ( oObj, sProp ) { 
	// oObj : [Object] - Objet dont on désire le positionnement 
	// sProp : [String] - Propriété désirée : offsetLeft - offsetTop 
	var iVal = 0; 
	// On boucle tant que l'on n'est pas à la racine du document 
	while (oObj && oObj.tagName != 'BODY') { 
 		eval('iVal += oObj.' + sProp + ';');
 		oObj = oObj.offsetParent; 
	} 
 	return iVal; 
} 
/* By EM */


/*-------- Menu.ouvrir()*/
function _mVx_Menu_ouvrir(_element_declencheur) { 
	
	mVx_masquerCombos();
	if(_element_declencheur.parent.parent == null) {
		/* si on est dans la barre de menu horizontal */
		if(this.ancre.type == MenuHorizontal) { 
			/****** by EM ****** ajouté dans cette version */
			if(mVx_ie && mVx_ie_version >= 5.5) {

				if (Menu_Horizontal_Application == true) {
					/*************************************/
					/* QUE POUR LES APPLICATIONS !!!!!!! */
					this.getObjetHTML().style.left = (document.getElementById("zoneMenus").offsetLeft + _element_declencheur.getObjetHTML().offsetLeft) + "px";
					/*************************************/
				} else {
					/*************************************/
					/* Menu Horizontal normal !!! */
					/*this.getObjetHTML().style.left = (24 + document.getElementById("zoneMenus").offsetLeft + _element_declencheur.getObjetHTML().offsetLeft) + "px";*/
					/* correction le 12/02/2007 pour resoudre le décalage du niveau 1 vers la droite suite modif pour ecran 1024 */
					this.getObjetHTML().style.left = (0 + document.getElementById("zoneMenus").offsetLeft + _element_declencheur.getObjetHTML().offsetLeft) + "px";
					/*************************************/
				}
			} else {
				this.getObjetHTML().style.left = _element_declencheur.getObjetHTML().offsetLeft + "px";
			}


			var Decalage = "";
			if(mVx_ie && mVx_ie_version >= 5.5) {
				var ozoneMenus = document.getElementById("zoneMenus");
				/* Pour IE car il ne voit pas la hauteur du div !!!! */
				Decalage = (fDomOffset (ozoneMenus, 'offsetTop') + 16) + "px";
				
			} else {
				Decalage = (document.getElementById("zoneMenus").offsetTop + 16) + "px";
				
			}			
			this.getObjetHTML().style.top = Decalage;
			/********************/
			
			if(mVx_ie && mVx_ie_version >= 5.5) {
				/* V1
				this.getIframeHTML().style.left = (_element_declencheur.getObjetHTML().offsetLeft + 25) + "px";
				*/
				this.getIframeHTML().style.left = this.getObjetHTML().style.left;
				
				/* Ajouté dans cette version */
				this.getIframeHTML().style.top = this.getObjetHTML().style.top;
				/********************/
			}
		}
		/* si on est dans la barre de menu vertical niveau 1 */
		else {

			/****** by EM ******
			Cf article http://msdn.microsoft.com/library/default.asp?url=/workshop/author/css/overview/measurementandlocation.asp !!!!!
			*/
			
			var Decalage = "";
			if(mVx_ie && mVx_ie_version >= 5.5) {
				/* Pour IE car il ne voit pas la largeur de la colonne de gauche !!!! */
				var onomDIVGauche = document.getElementById(nomDIVGauche);	
				Decalage = (fDomOffset (onomDIVGauche, 'offsetLeft') + document.getElementById(nomDIVGauche).offsetWidth) + "px";
			} else {
				/* 
				modif 2008_07_21 ajout de test version FireFox/3.X
				*/
				var firefox = navigator.userAgent;
				if(firefox.match("Firefox/3.")!=null){
					Decalage = document.getElementById(nomDIVGauche).parentNode.offsetLeft + document.getElementById(nomDIVGauche).offsetWidth + "px";
				}else{
					Decalage = document.getElementById(nomDIVGauche).offsetLeft + document.getElementById(nomDIVGauche).offsetWidth + "px";
				}
			}			
			/********************/
			
			this.getObjetHTML().style.left = Decalage;


			/*
			this.getObjetHTML().style.top = (_element_declencheur.getObjetHTML().offsetTop + mVx_style_top_menuv + mVx_decalage_sousMenu_top) + "px";
			*/

			/****** by EM ********/
			var DecalageTop = "";
			if(mVx_ie && mVx_ie_version >= 5.5) {
				/* Pour IE car il ne voit pas la largeur de la colonne de gauche !!!! */
				DecalageTop = (fDomOffset (onomDIVGauche, 'offsetTop') + _element_declencheur.getObjetHTML().offsetTop - 1) + "px";
			} else {
				/* 
				modif 2008_07_21  ajout de test version FireFox/3.X
				*/
				var firefox = navigator.userAgent;
				if(firefox.match("Firefox/3.")!=null){
					DecalageTop = (document.getElementById(nomDIVGauche).parentNode.offsetTop + _element_declencheur.getObjetHTML().offsetTop) -1+ "px";
				}else{
					DecalageTop = (document.getElementById(nomDIVGauche).offsetTop + _element_declencheur.getObjetHTML().offsetTop) + "px";
				}
			}
			
			this.getObjetHTML().style.top  = DecalageTop;
			/********************/
		}
	}
	else {
		/* menu horizontal*/
		if(this.ancre.type == MenuHorizontal) {
			this.getObjetHTML().style.left = (_element_declencheur.parent.getObjetHTML().offsetLeft +_element_declencheur.parent.getObjetHTML().offsetWidth + mVx_decalage_sousMenu_left) + "px";
			this.getObjetHTML().style.top = (_element_declencheur.parent.getObjetHTML().offsetTop + _element_declencheur.getObjetHTML().offsetTop) + "px";

			if(mVx_ie && mVx_ie_version >= 5.5) {
				/* V1
				this.getIframeHTML().style.left = (_element_declencheur.parent.getObjetHTML().offsetLeft + _element_declencheur.parent.getObjetHTML().offsetWidth + mVx_decalage_sousMenu_left) + "px";
				*/
				this.getIframeHTML().style.left = this.getObjetHTML().style.left;
				/* V1
				this.getIframeHTML().style.top = (_element_declencheur.parent.getObjetHTML().offsetTop+_element_declencheur.getObjetHTML().offsetTop) + "px";
				*/
				this.getIframeHTML().style.top = this.getObjetHTML().style.top;
			}
		}
		/* Sous menu vertical niveau 2 et plus */
		else {
			//alert(_element_declencheur.parent.id + " : " + this.getObjetHTML().style.left + " <--- " + _element_declencheur.parent.getObjetHTML().offsetLeft + " " + _element_declencheur.parent.getObjetHTML().offsetWidth + "  " + mVx_decalage_sousMenu_left);
			this.getObjetHTML().style.left = (_element_declencheur.parent.getObjetHTML().offsetLeft + (_element_declencheur.parent.getObjetHTML().offsetWidth) + mVx_decalage_sousMenu_left) + "px";
			this.getObjetHTML().style.top = (_element_declencheur.parent.getObjetHTML().offsetTop + _element_declencheur.getObjetHTML().offsetTop + mVx_decalage_sousMenu_top) + "px";
		}
	}



	/* apparition du sous menu*/
	this.getObjetHTML().style.zIndex= mVx_zIndex_haut;
	this.getObjetHTML().style.visibility= "visible";
	
	/* La frame devient visible et masque la listBox que pour IE !!!!! */
	if(mVx_ie && mVx_ie_version >= 5.5) {
		this.getIframeHTML().style.left = this.getObjetHTML().style.left;
		this.getIframeHTML().style.top = this.getObjetHTML().style.top;
		this.getIframeHTML().style.visibility= "visible";
		this.getIframeHTML().style.zIndex = mVx_zIndex_haut - 1;
		this.getIframeHTML().style.width = this.getObjetHTML().offsetWidth;
		this.getIframeHTML().style.height = this.getObjetHTML().offsetHeight;
	}
}



/*-------- Menu.fermer()*/
function _mVx_Menu_fermer() { 
	/* si on a un menu parent et donc, on n est pas dans le menu de premier niveau*/
	if(this.parent.parent != null) { 
		this.getObjetHTML().style.visibility= "hidden";
		if(mVx_ie && mVx_ie_version >= 5.5) {
			this.getIframeHTML().style.visibility= "hidden";
		}
	}
	this.fermerSousMenus();
}
/*-------- Menu.fermerSousMenus()*/
function _mVx_Menu_fermerSousMenus() { 
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		if(this.elements[i].sousMenu != null) {
			this.elements[i].sousMenu.fermer();
		}
	}
}
/*-------- Menu.eteindreElementsR() (R= recursif)*/
function _mVx_Menu_eteindreElementsR() { 
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		this.elements[i].eteindreElement();
		if(this.elements[i].sousMenu != null) {
			this.elements[i].sousMenu.eteindreElementsR();
		}
	}
}
/*-------- Menu.construire()*/
function _mVx_Menu_construire() { 
	/* menu horizontal*/	
	ecritureV = "";
	ecritureH = "";
	if(this.ancre.type == MenuHorizontal) {
		/* barre de titre niveau 1(ancre) */
		if(this.parent == null) { 
			/* V1
			ecriture += "<div class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";position:absolute;height:16px;left:25px;top:40px;margin:0px;padding:0px;'>";
			*/
			ecritureH += "<div class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";height:16px;margin:0px;padding:0px;'>";
		}
		/* sous menu de niveau 2 et plus */
		else { 
			if(mVx_ie && mVx_ie_version >= 5.5) {
				ecritureH += "<iframe id='" + this.id + "_iframe' src='javascript:false;' scrolling='no' frameborder='0' style='z-index:" + (mVx_zIndex_haut - 1) + ";position:absolute;top:56px;left:0px;visibility:hidden;'></iframe>";
			}
			ecritureH += "<div class='SsNiveau' id='" + this.id + "' style='z-index:" + mVx_zIndex_haut + ";overflow:hidden;position:absolute;left:0px;top:56px;margin:0px;padding:0px;visibility:hidden;width:" + mVx_style_width_smenuh + ";'>";
		}
	}
	/* menu vertical */
	else if(this.ancre.type == MenuVertical) {
		/* barre de titre (ancre) */
		if(this.parent == null) {
			/* Pas de top et de left car positionné par les <div> nomDIVGauche et MenuV de Menu.css !!!!*/
			//var mv2mv_quirks = "<div name='id_quirks_Niveau1' id='idNiveau1' class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";position:absolute;margin:0;padding:0;width:" + (mVx_style_width_menuv + 20) + "px;'>";
			//var    mv2mv_css = "<div name='id_css_Niveau1' id='idNiveau1' class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";position:absolute;margin:0;padding:0;width:" + mVx_style_width_menuv + "px;'>";


			var mv2mv_quirks = "<div name='id_quirks_Niveau1' id='idNiveau1' class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";position:absolute;margin:0;padding:0;width:#LARGEUR+20#px;'>";
			var    mv2mv_css = "<div name='id_css_Niveau1' id='idNiveau1' class='Niveau1' style='z-index:" + mVx_zIndex_bas + ";position:absolute;margin:0;padding:0;width:#LARGEUR#px;'>";

			if(!document.compatMode) { /*IE5*/
				ecritureV += mv2mv_quirks;
			}
			else {
				switch(document.compatMode) {
					case "CSS1Compat": /*FF IE6 OPERA*/
						ecritureV += mv2mv_css;
						break;
					case "QuirksMode": /*OPERA doctype absent ou faux*/
						ecritureV += mv2mv_css;
						break;
					case "BackCompat": /*FF IE6 doctype absent ou faux*/
						ecritureV += mVx_ie? mv2mv_quirks : mv2mv_css;
						break;
					default:
						ecritureV += mv2mv_quirks;
				}
			}
		}
		/* sous menu*/
		else { 
			if(mVx_ie && mVx_ie_version >= 5.5) {
				//ecritureV += "<iframe id='" + this.id + "_iframe' src='javascript:false;' scrolling='no' frameborder='0' style='z-index:" + (mVx_zIndex_haut - 1) + ";position:absolute;left:" + (mVx_style_width_menuv + 100) + "px;visibility:hidden;'></iframe>";
				ecritureV += "<iframe id='" + this.id + "_iframe' src='javascript:false;' scrolling='no' frameborder='0' style='z-index:" + (mVx_zIndex_haut - 1) + ";position:absolute;left:#LARGEUR+100#px;visibility:hidden;'></iframe>";
			}
			//ecritureV += "<div class='SsNiveau' id='" + this.id + "' style='z-index:" + mVx_zIndex_haut + ";overflow:hidden;position:absolute;left:"+ (mVx_style_width_menuv + 100)+ "px;margin:0;padding:0;visibility:hidden;width:" + mVx_style_width_smenuv + ";'>";
			ecritureV += "<div class='SsNiveau' id='" + this.id + "' style='z-index:" + mVx_zIndex_haut + ";overflow:hidden;position:absolute;left:#LARGEUR+100#px;margin:0;padding:0;visibility:hidden;width:" + mVx_style_width_smenuv + ";'>";
			ecritureV += "<div style='overflow:hidden;height:1px;width:100%;background-color:" + mVx_style_couleur_separateur[this.ancre.type] + "'></div>";
		}
	}

	if(this.ancre.type == MenuHorizontal) { 
		ecritureMenuH  += ecritureH;
	} else {
		ecritureMenuV  += ecritureV;
	}
	
	
	/* By EM pour ecriture en une seule fois !!!!
	document.write(ecriture);
	*/
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		this.elements[i].construire();
	}	

	if(this.ancre.type == MenuHorizontal) { 
		ecritureMenuH  += "</div>";
	} else {
		ecritureMenuV  += "</div>";
	}
	/* By EM pour ecriture en une seule fois !!!!
	document.write(ecriture);
	*/	
}

/* - objet Element ------------------------------------------------------------------------------------------------------------------*/

function mVx_Element(_ancre, _menuparent, _indice, _libelle, _theme, _action, _sousmenu) {
	this.ancre= _ancre;
	this.id = _menuparent.id + "_elmt_" + _indice;
	this.objetHTML= null;
	this.parent= _menuparent;
	
	this.libelle= _libelle;
	this.theme= _theme;
	this.toujoursActif= false;
	this.action= _action; 
	this.sousMenu= _sousmenu;
	
	this.getObjetHTML= _mVx_Element_getObjetHTML;
	this.survolElement= _mVx_Element_survolElement;
	this.allumerElement= _mVx_Element_allumerElement;
	this.eteindreElement= _mVx_Element_eteindreElement;
	this.activerElement= _mVx_Element_activerElement;
	this.construire= _mVx_Element_construire;
}

function _mVx_Element_getObjetHTML() {
	if(this.objetHTML == null) {
		this.objetHTML= document.getElementById(this.id);
	}
	return(this.objetHTML);
}

function _mVx_Element_construire() {
	var ecritureElement= "";
	var onclick= "";
	var style_cursor= "default";
	if(this.action != null) {
		style_cursor= "pointer;cursor:hand";
		onclick= "onClick=\"" + this.action + "\"";
	}
	else {
		style_cursor= "default";
	}
	
	/* elements de menu normal */
	if(this.parent.parent != null) {
		/* menu horizontal */
		if(this.ancre.type == MenuHorizontal) { 
			/* V1
			styleElement = mVx_style_police[this.ancre.type] + "overflow:hidden;width:100%;border-bottom:1px solid " + mVx_style_couleur_separateur[this.ancre.type] + ";padding: 1px 8px 2px 18px;background-color:" + mVx_style_couleur_element_fond[this.ancre.type][this.theme] + ";color:" + mVx_style_couleur_element[this.ancre.type][this.theme] + ";cursor:" + style_cursor + ";";
			*/
			styleElement = mVx_style_police[this.ancre.type] + "overflow:hidden;width:100%;border-bottom:1px solid " + mVx_style_couleur_separateur[this.ancre.type] + ";padding-left:16px;padding-top:1px;padding-bottom:2px;background-color:" + mVx_style_couleur_element_fond[this.ancre.type][this.theme] + ";color:" + mVx_style_couleur_element[this.ancre.type][this.theme] + ";cursor:" + style_cursor + ";";
			flecheSousMenu= "";

			if(this.sousMenu != null) {
				styleElement += "padding-right:8px;";
				if(mVx_ie) {
					/*flecheSousMenu= "<span style='font-family:Webdings;font-size:12px;font-weight:normal;color:white;height:14px;overflow:hidden;position:absolute;left:0;margin-top:-3px;'>4</span>";*/
					if (flecheAvant) {
						flecheSousMenu = "<span style='font-family:Webdings;font-size:12px;font-weight:normal;color:" + CouleurFleche + ";position:absolute;left:0;margin-top:-3px;'>4</span>";
					} else {
						flecheSousMenu = "<span style='font-family:Webdings;font-size:12px;font-weight:normal;color:" + CouleurFleche + ";float:right;padding:0;margin:0px 16px -5px 0;'>4</span>";
					}
				}
				else {
					if (flecheAvant) {
						flecheSousMenu = "<span style='font-size:22px;font-weight:normal;color:"+ CouleurFleche + ";position:absolute;left:0;margin:-9px 0 0 3px;'>&#8227;</span>";
					} else {
						flecheSousMenu = "<span style='font-size:22px;font-weight:normal;color:" + CouleurFleche + ";float:right;overflow:hidden;padding:0;margin:-9px 16px -5px 0;'>&#8227;</span>";
					}
				}
			}
			else {
				styleElement += "padding-right:1px;";
			}




			ecritureElement= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);'";
			ecritureElement += " style='" + styleElement + "'>";
			ecritureElement += flecheSousMenu + this.libelle;
			ecritureElement += "</div>";
			ecritureMenuH += ecritureElement;
			
		}
		/* menu vertical Elements de niveau 2 et plus */
		else { 
			styleElement= mVx_style_police[this.ancre.type] + "overflow:hidden;width:100%;border-bottom:1px solid " + mVx_style_couleur_separateur[this.ancre.type] + ";padding-left:1px;padding-top:1px;padding-bottom:2px;background-color:" + mVx_style_couleur_element_fond[this.ancre.type][this.theme] + ";color:" + mVx_style_couleur_element[this.ancre.type][this.theme] + ";cursor:" + style_cursor + ";";
			
			flecheSousMenu= "";
			if(this.sousMenu != null) {
				styleElement += "padding-right:8px;";
				if(mVx_ie) {
					flecheSousMenu = "<span style='font-family:Webdings;font-size:12px;font-weight:normal;float:right;width:10px;height:14px;overflow:hidden;margin:-3px " + ((document.compatMode && document.compatMode=="CSS1Compat")?"3":"-6") + "px 0 0;'>4</span>";
				}
				else {
					flecheSousMenu = "<span style='font-size:22px;font-weight:normal;float:right;overflow:hidden;padding:0;margin:-9px 3px -5px 0;'>&#8227;</span>";
				}
			}
			else {
				styleElement += "padding-right:1px;";
			}

			ecritureElement= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);'";
			ecritureElement += " style='" + styleElement + "'>";
			ecritureElement += flecheSousMenu + this.libelle;
			ecritureElement += "</div>";
			ecritureMenuV += ecritureElement;
		}
	
	} else { /* element de la barre de titre (ancre)*/
		/* menu horizontal*/
		if(this.ancre.type == MenuHorizontal) { 
			/* V1
			var mv2mh_quirks= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:1px 10px 0 10px;margin:0 25px 0 0;cursor:" + style_cursor + ";height:16px;float:left;'>";
			var    mv2mh_css= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:1px 10px 0 10px;margin:0 25px 0 0;cursor:" + style_cursor + ";height:15px;float:left;'>";
			*/
			/*
			var mv2mh_quirks= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:0px 7.5px 0px 7.5px;margin:0;cursor:" + style_cursor + ";height:16px;float:left;'>";
			var    mv2mh_css= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:0px 7.5px 0px 7.5px;margin:0;cursor:" + style_cursor + ";height:16px;float:left;'>";
			*/
			var mv2mh_quirks= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:0px 15px 0px 15px;margin:0;cursor:" + style_cursor + ";height:16px;float:left;'>";
			var    mv2mh_css= "<div id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";padding:0px 15px 0px 15px;margin:0;cursor:" + style_cursor + ";height:16px;float:left;'>";

			if(!document.compatMode || (mVx_ie && document.compatMode == "BackCompat")) { /* IE sans doctype */
				ecritureElement= mv2mh_quirks;
			}
			else {
				ecritureElement= mv2mh_css;
			}
			ecritureElement += this.libelle;
			ecritureElement += "</div>";
			ecritureMenuH += ecritureElement;
		}
		/* menu vertical*/
		else { 
			ecritureElement= "<div name='niveau1' id='" + this.id + "' " + onclick + " onmouseover='mVx_survol(true,this);' onmouseout='mVx_survol(false,this);' style='" + mVx_style_police[this.ancre.type] + "overflow:hidden;width:#LARGEUR-20#px;padding-left:#PADDINGLEFT#;padding-right:0px;padding-top:2px;padding-bottom:2px;margin:0;margin-left:0px;border-bottom:1px solid " + mVx_style_couleur_separateur[this.ancre.type] + ";background-color:" + mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme] + ";color:" + mVx_style_couleur_ancre_element[this.ancre.type][this.theme] + ";cursor:" + style_cursor + ";'>";

			if(this.sousMenu != null) {
				if(mVx_ie) {
					ecritureElement += "<span style='font-family:Webdings;font-size:12px;font-weight:normal;float:right;width:10px;height:14px;overflow:hidden;margin:-3px 2px 0 0;'>4</span>";
				}
				else {
					ecritureElement += "<span style='font-size:22px;font-weight:normal;float:right;overflow:hidden;padding:0;margin:-9px 3px -5px 0;'>&#8227;</span>";
				}
			}
			ecritureElement += this.libelle;
			ecritureElement += "</div>";
			ecritureMenuV += ecritureElement;
		}
	}
}
function _mVx_Element_survolElement(_inout) {
	/*si on sort d un element on efface tout dans une seconde (parametrable)*/
	if(!_inout) { 
		this.ancre.menuCourant= null;
		setTimeout("mVx_fermerTousMenus('"+this.ancre.menuAncre.id+"')", mVx_delaiFermeture);

		 /*on fait passer le menu, ses parents et le sous-menu, en second plan*/
		if(this.sousMenu != null) {
			this.sousMenu.getObjetHTML().style.zIndex= mVx_zIndex_moyen;
			if(mVx_ie && mVx_ie_version >= 5.5) {
				this.sousMenu.getIframeHTML().style.zIndex= mVx_zIndex_moyen - 1;
			}
		}
		var mon_menu = this.parent;
		while(mon_menu != null && mon_menu != this.ancre.menuAncre) {
			mon_menu.getObjetHTML().style.zIndex= mVx_zIndex_moyen;
			if(mVx_ie && mVx_ie_version >= 5.5) {
				mon_menu.getIframeHTML().style.zIndex= mVx_zIndex_moyen - 1;
			}
			mon_menu= mon_menu.parent.parent;
		}
	}
	/*si on entre dans un element: on efface les sous menus du menu courant et on affiche eventuellement un sous-menu (et on fait pareil avec les couleurs d element)*/
	else { 
		/*on remet la couleur de base a tous les elements*/
		mVx_eteindreTousElements(this.ancre.menuAncre);
		/*on allume cet Element et tous les elements "parents"*/
		var mon_element= this;
		while(mon_element != null) {
			mon_element.allumerElement();
			mon_element= mon_element.parent.parent;
		}
		this.ancre.menuCourant= this.parent;
		/*on efface les sous menus du menu de l element*/
		this.parent.fermerSousMenus();
		/*on affiche eventuellement un sous menu*/
		if(this.sousMenu != null) {
			this.sousMenu.ouvrir(this);
		}
	}
}

function _mVx_Element_allumerElement() {
	/* menu horizontal*/
	if(this.ancre.type == MenuHorizontal) { 
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mVx_style_couleur_element_fond_survol[this.ancre.type][this.theme] : mVx_style_couleur_ancre_element_fond_survol[this.ancre.type][this.theme];
		  /*this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mVx_style_couleur_element_fond[this.ancre.type][this.theme] : mVx_style_couleur_ancre_element_fond_survol[this.ancre.type][this.theme];*/
		if(this.parent.parent != null) {
			/* lastChild: le span de la fleche est placé APRES le texte */
			/* firstChild: le span de la fleche est placé AVANT le texte */
			/* V1 au cas ou la fleche est tout le temps présente
			this.getObjetHTML().firstChild.style.color = mVx_style_couleur_element_survol[ this.ancre.type ][ this.theme ];
			*/
			this.getObjetHTML().style.color= mVx_style_couleur_element_survol[this.ancre.type][this.theme]; 
		}
		else {
			this.getObjetHTML().style.color=  mVx_style_couleur_ancre_element_survol[this.ancre.type][this.theme];
		}
	}
	else { /* menu vertical*/
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mVx_style_couleur_element_fond_survol[this.ancre.type][this.theme] : mVx_style_couleur_ancre_element_fond_survol[this.ancre.type][this.theme];
		this.getObjetHTML().style.color= (this.parent.parent != null)? mVx_style_couleur_element_survol[this.ancre.type][this.theme] : mVx_style_couleur_ancre_element_survol[this.ancre.type][this.theme];
	}
}

function _mVx_Element_eteindreElement() {
	if(!this.toujoursActif) {
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mVx_style_couleur_element_fond[this.ancre.type][this.theme] : mVx_style_couleur_ancre_element_fond[this.ancre.type][this.theme];
		/*sous menu*/
		if(this.parent.parent != null) { 
			/* menu horizontal*/
			if(this.ancre.type == MenuHorizontal) { 
				/* lastChild: le span de la fleche est placé APRES le texte */
				/* firstChild: le span de la fleche est placé AVANT le texte */
		
				/* V1 au cas ou la fleche est tout le temps présente
				this.getObjetHTML().firstChild.style.color= blanc;
				*/
				this.getObjetHTML().style.color= bleuFonce;
			}
			/* menu vertical*/
			else { 
				this.getObjetHTML().style.color = mVx_style_couleur_element[this.ancre.type][this.theme];
			}
		}
		/* ancre */
		else { 
			this.getObjetHTML().style.color =  mVx_style_couleur_ancre_element[this.ancre.type][this.theme];
		}
	}
}

function _mVx_Element_activerElement() {
	this.allumerElement();
	this.toujoursActif= true;
}

/* ----------------------------------------------------------------------------------------------------------------------------------*/
/* cette fonction va chercher la methode survolElement du bon objet Element javascript (grace a l iD)*/
function mVx_survol(_inout, _tagElement) { 
	var tmp= _tagElement.id.split("_elmt_");
	mVx_getMenu(tmp[0]).elements[tmp[1]].survolElement(_inout);
}
/* masque tous les sous-menus*/
function mVx_fermerTousMenus(_idmenuancre) { 
	var mon_menuancre= mVx_getMenu(_idmenuancre);
	if(mon_menuancre.ancre.menuCourant == null) {
		mVx_eteindreTousElements(mon_menuancre);
		/* je ferme tous les menus, par ricochet, a partir de l ancre*/
		mon_menuancre.fermerSousMenus(); 
		mVx_masquerCombos();
	}
}
/* eteind tous les elements d un menu ou de tous les menus si non specifie*/
function mVx_eteindreTousElements(_menuancre) { 
	if(_menuancre.ancre.menuCourant == null) {
		_menuancre.eteindreElementsR();
	}
}

function mVx_position(_table, _recherche) {
	var ma_longueur= _table.length;
	for(var i= 0; i < ma_longueur; i++) {
		if(_table[i] == _recherche) {
			return(i);
		}
	}
	return(-1);
}

function mVx_prefixer(_id_sans_prefixe) {
	return(mVx_prefixeID+_id_sans_prefixe);
}

function mVx_dePrefixer(_id_avec_prefixe) {
	return(_id_avec_prefixe.substring(mVx_prefixeID.length));
}

function mVx_getMenu(_id) {
	var mapos= mVx_position(mVx_menus_ids, _id);
	if(mapos == -1) {
		return(null);
	}
	else {
		return(mVx_menus_objets[mapos]);
	}
}

function mVx_getElement(_rech) {
	var ma_longueur= mVx_menus_objets.length;
	for(var i= 0; i < ma_longueur; i++) {
		var mon_menu= mVx_menus_objets[i];
		var longueur_elements= mon_menu.elements.length;
		for(var j= 0; j < longueur_elements; j++) {
			if(mon_menu.elements[j].libelle.indexOf(_rech) != -1 || (mon_menu.elements[j].action != null && mon_menu.elements[j].action.indexOf(_rech) != -1)) {
				return(mon_menu.elements[j]);
			}
		}
	}
}

function mVx_masquerCombos() {
	if(mVx_ie && mVx_ie_version < 5.5) {
		var masquer= false;
	
		var longueur_ancres= mVx_ancres.length;
		for(var i= 0; i < longueur_ancres; i++) {
			if(mVx_ancres[i].menuCourant != null) {
				masquer= true;
				break;
			}
		}
	
		if( (mVx_combosMasquees && !masquer) || (!mVx_combosMasquees && masquer) ) {
			var SELECTs= document.getElementsByTagName("SELECT");
			var nbSELECTs= SELECTs.length;
			var ma_visibilite= (masquer)? "hidden" : "visible";
			for(var i= 0; i < nbSELECTs; i++) {
				SELECTs[i].style.visibility= ma_visibilite;
			}
			mVx_combosMasquees= masquer;
		}
	}
}

function mVx_onclick() {
	var longueur_ancres= mVx_ancres.length;
	for(var i= 0; i < longueur_ancres; i++) {
		mVx_fermerTousMenus(mVx_ancres[i].menuAncre.id);
	}
}


function initMenu() {
	return;
	var TypeSite = document.getElementsByTagName("body")[0].id.toUpperCase();
	if (ecritureMenuV.length > 0) {
		var d = document.getElementById("gauche");


		//alert(TypeSite);
		if (TypeSite == "APP") {
			ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"0px").
			replace(/#LARGEUR+20#/gi,(mVx_style_width_menuv_app + 20)).
			replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv_app + 100)).
			replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv_app - 20)).
			replace(/#LARGEUR#/gi,mVx_style_width_menuv_app);
		} else {
			ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"20px").
			replace(/#LARGEUR+20#/gi,(mVx_style_width_menuv + 20)).
			replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv + 100)).
			replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv - 20)).
			replace(/#LARGEUR#/gi,mVx_style_width_menuv);
			
		}
		//alert(ecritureMenuV);


		d.innerHTML = ecritureMenuV + document.getElementById("gauche").innerHTML;
	
		// rajoute le "px" à la fin sinon ca plante avec FF !!!!!
		document.getElementById("MenuV").style.height = (document.getElementById("idNiveau1").offsetHeight + 13) + "px";
	}
	
	if (ecritureMenuH.length > 0 ) {
		var h = document.getElementById("navigation");

		if (TypeSite == "APP") {
			Menu_Horizontal_Application = true;
		}
		h.innerHTML = ecritureMenuH + document.getElementById("navigation").innerHTML;
	}
	
}

function initMenuH() {
	return;
	var h = document.getElementById("navigation");
	var TypeSite = document.getElementsByTagName("body")[0].id.toUpperCase();
	if (TypeSite == "APP") {
		Menu_Horizontal_Application = true;
	}
	h.innerHTML = ecritureMenuH + document.getElementById("navigation").innerHTML;
}

function initMenuV() {
	return;
	var d = document.getElementById("gauche");
	var TypeSite = document.getElementsByTagName("body")[0].id.toUpperCase();


	//alert(TypeSite);
	if (TypeSite == "APP") {
		ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"0px").
		replace(/#LARGEUR+20#/gi,(mVx_style_width_menuv_app + 20)).
		replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv_app + 100)).
		replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv_app - 20)).
		replace(/#LARGEUR#/gi,mVx_style_width_menuv_app);
	} else {
		ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"20px");
		ecritureMenuV = ecritureMenuV.replace(/#LARGEUR+20#/gi,(mVx_style_width_menuv + 20));
		ecritureMenuV = ecritureMenuV.replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv + 100));
		ecritureMenuV = ecritureMenuV.replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv - 20));
		ecritureMenuV = ecritureMenuV.replace(/#LARGEUR#/gi,mVx_style_width_menuv);
		
	}
	//alert(ecritureMenuV);


	
	d.innerHTML = ecritureMenuV + document.getElementById("gauche").innerHTML;
	
	// rajoute le "px" à la fin sinon ca plante avec FF !!!!!
	document.getElementById("MenuV").style.height = (document.getElementById("idNiveau1").offsetHeight + 13) + "px";
}



function initM() {
	var TypeSite = document.getElementsByTagName("body")[0].id.toUpperCase();
	if (ecritureMenuV.length > 0) {
		var d = document.getElementById("gauche");


		//alert(TypeSite);
		if (TypeSite == "APP") {
			ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"0px").
			replace(/#LARGEUR+20#/gi,(mVx_style_width_menuv_app + 20)).
			replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv_app + 100)).
			replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv_app - 20)).
			replace(/#LARGEUR"/gi,mVx_style_width_menuv_app);
		} else {
			ecritureMenuV = ecritureMenuV.replace(/#PADDINGLEFT#/gi,"20px").
			replace(/#LARGEUR+20#"/gi,(mVx_style_width_menuv + 20)).
			replace(/#LARGEUR+100#/gi,(mVx_style_width_menuv + 100)).
			replace(/#LARGEUR-20#/gi,(mVx_style_width_menuv - 20)).
			replace(/"LARGEUR#/gi,mVx_style_width_menuv);
			
		}
		//alert(ecritureMenuV);


		d.innerHTML = ecritureMenuV + document.getElementById("gauche").innerHTML;
	
		// rajoute le "px" à la fin sinon ca plante avec FF !!!!!
		document.getElementById("MenuV").style.height = (document.getElementById("idNiveau1").offsetHeight + 13) + "px";
	}
	
	if (ecritureMenuH.length > 0 ) {
		var h = document.getElementById("navigation");

		if (TypeSite == "APP") {
			Menu_Horizontal_Application = true;
		}
		h.innerHTML = ecritureMenuH + document.getElementById("navigation").innerHTML;
	}
	
}



function addListener(element,baseName,handler) {
	if (element.addEventListener) {
		element.addEventListener(baseName,handler,false);
	} else if (element.attachEvent){
		element.attachEvent('on' + baseName,handler);
	}
}


addListener(window,'load',initM);
