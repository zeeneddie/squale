/* comMenuAF_v2 1.3.1 */
/*Position top du menu vertical Valeur donnée en px : 110 pour les sites de publication et au choix pour les Applications*/
mv2_style_top_menuv = 81;
/*largeur des sous menu horizontaux*/
mv2_style_width_smenuh = "140px";
/*largeur des menu et sous menu verticaux*/
mv2_style_width_menuv = 140;
mv2_style_width_smenuv = "160px";
/* taille de la police */
mv2_style_font_size = "10px";
/* utilisez la ligne suivante si vous voulez detecter la résolution ecran 1280 et modifier la taille de la police du menu */
/*if (screen.width >= 1280) {mv2_style_font_size = "12px";}*/
mv2_style_police= new Array("font-family:Verdana,Arial,Helvetica,sans-serif;font-weight:bold;font-size:"+mv2_style_font_size+";", "font-family:Verdana,Arial,Helvetica,sans-serif;font-weight:bold;font-size:"+mv2_style_font_size+";");
mv2_style_couleur_separateur= new Array("#FFFFFF", "#D3D3D3");
mv2_style_couleur_ancre_element= new Array(new Array("#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"), new Array("#989898", "#989898", "#989898", "#989898", "#989898", "#989898"));
mv2_style_couleur_ancre_element_survol= new Array(new Array("#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"), new Array("#383D90", "#DD0426", "#008CE5", "#FF9900", "#00B87A", "#9D2B57"));
mv2_style_couleur_ancre_element_fond= new Array(new Array("#989898", "#989898", "#989898", "#989898", "#989898", "#989898"), new Array("#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"));
mv2_style_couleur_ancre_element_fond_survol= new Array(new Array("#383D90", "#DD0426", "#008CE5", "#FF9900", "#00B87A", "#9D2B57"), new Array("#EFEFFF", "#FFEFEF", "#D7E9FF", "#FFF3E5", "#EFFFFF", "#FFEFFF"));
mv2_style_couleur_element= new Array(new Array("#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F"), new Array("#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F", "#7F7F7F"));
mv2_style_couleur_element_survol= new Array(new Array("#383D90", "#DD0426", "#008CE5", "#FF9900", "#00B87A", "#9D2B57"), new Array("#383D90", "#DD0426", "#008CE5", "#FF9900", "#00B87A", "#9D2B57"));
mv2_style_couleur_element_fond= new Array(new Array("#D3D3D3", "#D3D3D3", "#D3D3D3", "#D3D3D3", "#D3D3D3", "#D3D3D3"), new Array("#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF", "#FFFFFF"));
mv2_style_couleur_element_fond_survol= new Array(new Array("#E1E1E1", "#E1E1E1", "#E1E1E1", "#E1E1E1", "#E1E1E1", "#E1E1E1"), new Array("#EFEFFF", "#FFEFEF", "#D7E9FF", "#FFF3E5", "#EFFFFF", "#FFEFFF"));
/*en millisecondes */
mv2_delaiFermeture= 2 * 1000;
/*ligne suivante a mettre en commentaire si on ne veux pas utiliser cette fonction de fermeture de tous les menus sur un clic de la souris*/
document.onclick= mv2_onclick;
/*-- fin config*/
mv2_prefixeID= "_mv2_";
mv2_zIndex_bas= 10;
mv2_zIndex_moyen= 20;
mv2_zIndex_haut= 30;
mv2_combosMasquees= false;
mv2_ancres= new Array();
mv2_menus_ids= new Array();
mv2_menus_objets= new Array();
mv2_decalage_sousMenu_top= -1; 
mv2_decalage_sousMenu_left= 1; 
mv2_safari= (navigator.userAgent.indexOf("Safari") != -1);
mv2_ie= false;
if((verOffset= navigator.userAgent.indexOf("MSIE")) != -1) {
	mv2_ie= true;
	mv2_ie_version= parseFloat(navigator.userAgent.substring(verOffset + 5, navigator.userAgent.length));
	if(mv2_ie_version == 5.5) {
		mv2_decalage_sousMenu_top= -2;
	}
}
mv2_ns4= (document.layers);
mv2_ie4= (document.all && !document.getElementById);
if(mv2_ie4) {
	document.getElementById= function(id) { return(document.all(id)); }
	document.getElementsByTagName= function(id) { return(document.all.tags(id)); }
}
if(mv2_ns4) {
	document.getElementById= function(id) { return(document.layers[id]); }
}
function comMenuAF_v2(_type, _idMenuAncre) {
	this.type= _type; 
	this.indexTraceur= "";
	this.menuAncre= new mv2_Menu(this, mv2_prefixer(_idMenuAncre), null, null);
	this.menuCourant= null;
	this.listerParentsPourTraceur= _mv2_listerParentsPourTraceur;
	this.ajouterElement= _mv2_ajouterElement;
	this.construire= _mv2_construire;
	this.activerAncre= _mv2_activerAncre;
	mv2_ancres[mv2_ancres.length]= this;
	mv2_menus_ids[mv2_menus_ids.length]= this.menuAncre.id;
	mv2_menus_objets[mv2_menus_objets.length]= this.menuAncre;

}
function _mv2_ajouterElement(_idmenu, _libelle, _theme, _action, _idsousmenu) { 
	var strmenu= mv2_prefixer(_idmenu);
	var strsousmenu= mv2_prefixer(_idsousmenu);
	var lemenu= mv2_getMenu(strmenu);
	var mon_theme= (_theme != null)? _theme : (lemenu.theme != null)? lemenu.theme : 0;
	var mon_element=  new mv2_Element(this, lemenu, lemenu.elements.length, _libelle, mon_theme, _action, null);
	lemenu.elements[lemenu.elements.length]= mon_element;
	if(_idsousmenu != null) { 
		mv2_menus_ids[mv2_menus_ids.length]= strsousmenu;
		var mon_sousmenu=  new mv2_Menu(this, strsousmenu, mon_element, mon_theme);
		mv2_menus_objets[mv2_menus_objets.length]= mon_sousmenu;
		mon_element.sousMenu= mon_sousmenu;
	}
}
function _mv2_construire() { 
	var ma_longueur= mv2_menus_objets.length;
	for(var i= 0; i < ma_longueur; i++) {
		if(mv2_menus_objets[i].ancre == this) {
			mv2_menus_objets[i].construire();
		}
	}
}
function _mv2_activerAncre(_select) {
	switch(typeof(_select)) {
	case "number":
		if(_select-1 <= this.menuAncre.elements.length) {
			this.menuAncre.elements[_select-1].activerElement();
		}
		break;
	case "string":
		var tmparbo= this.listerParentsPourTraceur(_select, true);
		if(tmparbo.length > 0) {
			mv2_getElement(tmparbo[tmparbo.length-2]).activerElement();
		}
		break;
	}
}
function _mv2_listerParentsPourTraceur(_element_rech, _action_page_courante) {
	var mon_element_original= mv2_getElement(_element_rech);
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
function mv2_Menu(_ancre, _id, _elementparent, _theme) {
	this.ancre= _ancre;
	this.id= _id;
	this.objetHTML= null;
	this.iframeHTML= null;
	this.parent= _elementparent;
	this.theme= _theme;
	this.elements= new Array();
	this.getObjetHTML= _mv2_Menu_getObjetHTML;
	this.getIframeHTML= _mv2_Menu_getIframeHTML;
	this.ouvrir= _mv2_Menu_ouvrir;
	this.fermer= _mv2_Menu_fermer;
	this.fermerSousMenus= _mv2_Menu_fermerSousMenus;
	this.eteindreElementsR= _mv2_Menu_eteindreElementsR;
	this.construire= _mv2_Menu_construire;
}
function _mv2_Menu_getObjetHTML() {
	if(this.objetHTML == null) {
		this.objetHTML= document.getElementById(this.id);
	}
	return(this.objetHTML);
}
function _mv2_Menu_getIframeHTML() {
	if(this.iframeHTML == null) {
		this.iframeHTML= document.getElementById(this.id + "_iframe");
	}
	return(this.iframeHTML);
}
function _mv2_Menu_ouvrir(_element_declencheur) { 
	mv2_masquerCombos();
	if(_element_declencheur.parent.parent == null) {
		if(this.ancre.type == 0) { 
			this.getObjetHTML().style.left= (_element_declencheur.getObjetHTML().offsetLeft + 25) + "px";
			if(mv2_ie && mv2_ie_version >= 5.5) {
				this.getIframeHTML().style.left= (_element_declencheur.getObjetHTML().offsetLeft + 25) + "px";
			}
		}
		else {
			this.getObjetHTML().style.left= (_element_declencheur.getObjetHTML().offsetLeft + 140 + 20 + mv2_decalage_sousMenu_left) + "px";
			this.getObjetHTML().style.top= (_element_declencheur.getObjetHTML().offsetTop + mv2_style_top_menuv + mv2_decalage_sousMenu_top) + "px";
			if(mv2_ie && mv2_ie_version >= 5.5) {
				this.getIframeHTML().style.left= (_element_declencheur.getObjetHTML().offsetLeft + 140 + 20 + mv2_decalage_sousMenu_left) + "px";
				this.getIframeHTML().style.top= (_element_declencheur.getObjetHTML().offsetTop + mv2_style_top_menuv + mv2_decalage_sousMenu_top) + "px";
			}
		}
	}
	else {
		if(this.ancre.type == 0) { 
			this.getObjetHTML().style.left= (_element_declencheur.parent.getObjetHTML().offsetLeft+_element_declencheur.parent.getObjetHTML().offsetWidth + mv2_decalage_sousMenu_left) + "px";
			this.getObjetHTML().style.top= (_element_declencheur.parent.getObjetHTML().offsetTop+_element_declencheur.getObjetHTML().offsetTop) + "px";
			if(mv2_ie && mv2_ie_version >= 5.5) {
				this.getIframeHTML().style.left= (_element_declencheur.parent.getObjetHTML().offsetLeft+_element_declencheur.parent.getObjetHTML().offsetWidth + mv2_decalage_sousMenu_left) + "px";
				this.getIframeHTML().style.top= (_element_declencheur.parent.getObjetHTML().offsetTop+_element_declencheur.getObjetHTML().offsetTop) + "px";
			}
		}
		else {
			this.getObjetHTML().style.left= (_element_declencheur.parent.getObjetHTML().offsetLeft+_element_declencheur.parent.getObjetHTML().offsetWidth + mv2_decalage_sousMenu_left) + "px";
			this.getObjetHTML().style.top= (_element_declencheur.parent.getObjetHTML().offsetTop+_element_declencheur.getObjetHTML().offsetTop + mv2_decalage_sousMenu_top) + "px";
			if(mv2_ie && mv2_ie_version >= 5.5) {
				this.getIframeHTML().style.left= (_element_declencheur.parent.getObjetHTML().offsetLeft+_element_declencheur.parent.getObjetHTML().offsetWidth + mv2_decalage_sousMenu_left) + "px";
				this.getIframeHTML().style.top= (_element_declencheur.parent.getObjetHTML().offsetTop+_element_declencheur.getObjetHTML().offsetTop + mv2_decalage_sousMenu_top) + "px";
			}
		}
	}
	this.getObjetHTML().style.zIndex= mv2_zIndex_haut;
	this.getObjetHTML().style.visibility= "visible";
	if(mv2_ie && mv2_ie_version >= 5.5) {
		this.getIframeHTML().style.visibility= "visible";
		this.getIframeHTML().style.zIndex= mv2_zIndex_haut - 1;
		this.getIframeHTML().style.width= this.getObjetHTML().offsetWidth
		this.getIframeHTML().style.height= this.getObjetHTML().offsetHeight;
	}
}
function _mv2_Menu_fermer() { 
	if(this.parent.parent != null) { 
		this.getObjetHTML().style.visibility= "hidden";
		if(mv2_ie && mv2_ie_version >= 5.5) {
			this.getIframeHTML().style.visibility= "hidden";
		}
	}
	this.fermerSousMenus();
}
function _mv2_Menu_fermerSousMenus() { 
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		if(this.elements[i].sousMenu != null) {
			this.elements[i].sousMenu.fermer();
		}
	}
}
function _mv2_Menu_eteindreElementsR() { 
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		this.elements[i].eteindreElement();
		if(this.elements[i].sousMenu != null) {
			this.elements[i].sousMenu.eteindreElementsR();
		}
	}
}
function _mv2_Menu_construire() { 
	var ecriture= "";
	if(this.ancre.type == 0) { 
		if(this.parent == null) { 
			ecriture += "<div style='z-index:"+mv2_zIndex_bas+";position:absolute;height:16px;left:25px;top:40px;margin:0px;padding:0px;'>";
		}
		else { 
			if(mv2_ie && mv2_ie_version >= 5.5) {
				ecriture += "<iframe id='"+this.id+"_iframe' src='javascript:false;' scrolling='no' frameborder='0' style='z-index:"+(mv2_zIndex_haut-1)+";position:absolute;top:56px;left:0px;visibility:hidden;'></iframe>";
			}
			ecriture += "<div id='"+this.id+"' style='z-index:"+mv2_zIndex_haut+";overflow:hidden;position:absolute;left:0px;top:56px;margin:0px;padding:0px;visibility:hidden;width:"+mv2_style_width_smenuh+";'>";
		}
	}
	else if(this.ancre.type == 1) { 
		if(this.parent == null) {
			var mv2mv_quirks= "<div style='z-index:"+mv2_zIndex_bas+";position:absolute;left:0px;top:"+mv2_style_top_menuv+"px;margin:0px;padding:0px;width:"+(mv2_style_width_menuv+20)+"px;'>";
			var    mv2mv_css= "<div style='z-index:"+mv2_zIndex_bas+";position:absolute;left:0px;top:"+mv2_style_top_menuv+"px;margin:0px;padding:0px;width:"+mv2_style_width_menuv+"px;'>";
			if(!document.compatMode) {
				if(mv2_safari) {
					ecriture += mv2mv_css;
				}
				else {
					ecriture += mv2mv_quirks;
				}
			}
			else {
				switch(document.compatMode) {
					case "CSS1Compat":
						ecriture += mv2mv_css;
						break;
					case "QuirksMode":
						ecriture += mv2mv_css;
						break;
					case "BackCompat":
						ecriture += mv2_ie? mv2mv_quirks : mv2mv_css;
						break;
					default:
						ecriture += mv2mv_quirks;
				}
			}
		}
		else { 
			if(mv2_ie && mv2_ie_version >= 5.5) {
				ecriture += "<iframe id='"+this.id+"_iframe' src='javascript:false;' scrolling='no' frameborder='0' style='z-index:"+(mv2_zIndex_haut-1)+";position:absolute;top:0px;left:0px;visibility:hidden;'></iframe>";
			}
			ecriture += "<div id='"+this.id+"' style='z-index:"+mv2_zIndex_haut+";overflow:hidden;position:absolute;left:0px;margin:0px;padding:0px;visibility:hidden;width:"+mv2_style_width_smenuv+";'>";
			ecriture += "<div style='overflow:hidden;height:1px;width:100%;background-color:"+mv2_style_couleur_separateur[this.ancre.type]+"'></div>";
		}
	}
	document.write(ecriture);
	var ma_longueur= this.elements.length;
	for(var i= 0; i < ma_longueur; i++) {
		this.elements[i].construire();
	}
	ecriture= "</div>";
	document.write(ecriture);
}
function mv2_Element(_ancre, _menuparent, _indice, _libelle, _theme, _action, _sousmenu) {
	this.ancre= _ancre;
	this.id= _menuparent.id + "_elmt_" + _indice;
	this.objetHTML= null;
	this.parent= _menuparent;
	this.libelle= _libelle;
	this.theme= _theme;
	this.toujoursActif= false;
	this.action= _action; 
	this.sousMenu= _sousmenu;
	this.getObjetHTML= _mv2_Element_getObjetHTML;
	this.survolElement= _mv2_Element_survolElement;
	this.allumerElement= _mv2_Element_allumerElement;
	this.eteindreElement= _mv2_Element_eteindreElement;
	this.activerElement= _mv2_Element_activerElement;
	this.construire= _mv2_Element_construire;
}
function _mv2_Element_getObjetHTML() {
	if(this.objetHTML == null) {
		this.objetHTML= document.getElementById(this.id);
	}
	return(this.objetHTML);
}
function _mv2_Element_construire() {
	var ecritureElement= "";
	var onclick= "";
	var style_cursor= "default";
	if(this.action != null) {
		style_cursor= "pointer;cursor:hand";
		onclick= "onClick=\""+this.action+"\"";
	}
	else {
		style_cursor= "default";
	}
	if(this.parent.parent != null) {
		if(this.ancre.type == 0) { 
			styleElement= mv2_style_police[this.ancre.type]+"overflow:hidden;width:100%;border-bottom:1px solid "+mv2_style_couleur_separateur[this.ancre.type]+";padding: 1px 8px 2px 18px;background-color:"+mv2_style_couleur_element_fond[this.ancre.type][this.theme]+";color:"+mv2_style_couleur_element[this.ancre.type][this.theme]+";cursor:"+style_cursor+";";
			if(mv2_ie) {
				flecheSousMenu= "<span style='font-family:Webdings;font-size:12px;font-weight:normal;color:white;height:14px;overflow:hidden;position:absolute;left:0;margin-top:-3px;'>4</span>";
			}
			else {
				flecheSousMenu= "<span style='font-size:22px;font-weight:normal;color:white;position:absolute;left:0;margin:-9px 0 0 3px;'>&#8227;</span>";
			}
			ecritureElement= "<div id='"+this.id+"' "+onclick+" onmouseover='mv2_survol(true,this);' onmouseout='mv2_survol(false,this);'";
			ecritureElement += " style='"+styleElement+"'>";
			ecritureElement += flecheSousMenu + this.libelle;
			ecritureElement += "</div>";
		}
		else { 
			styleElement= mv2_style_police[this.ancre.type]+"overflow:hidden;width:100%;border-bottom:1px solid "+mv2_style_couleur_separateur[this.ancre.type]+";padding-left:1px;padding-top:1px;padding-bottom:2px;background-color:"+mv2_style_couleur_element_fond[this.ancre.type][this.theme]+";color:"+mv2_style_couleur_element[this.ancre.type][this.theme]+";cursor:"+style_cursor+";";
			flecheSousMenu= "";
			if(this.sousMenu != null) {
				styleElement += "padding-right:8px;";
				if(mv2_ie) {
					flecheSousMenu= "<span style='font-family:Webdings;font-size:12px;font-weight:normal;float:right;width:10px;height:14px;overflow:hidden;position:relative;margin:-3px "+((document.compatMode && document.compatMode=="CSS1Compat")?"3":"-6")+"px 0 0;'>4</span>";
				}
				else {
					flecheSousMenu= "<span style='font-size:22px;font-weight:normal;float:right;overflow:hidden;padding:0;margin:-9px 3px -5px 0;position:relative;'>&#8227;</span>";
				}
			}
			else {
				styleElement += "padding-right:1px;";
			}
			ecritureElement= "<div id='"+this.id+"' "+onclick+" onmouseover='mv2_survol(true,this);' onmouseout='mv2_survol(false,this);'";
			ecritureElement += " style='"+styleElement+"'>";
			ecritureElement += flecheSousMenu + this.libelle;
			ecritureElement += "</div>";
		}
	}
	else {
		if(this.ancre.type == 0) { 
			var mv2mh_quirks= "<div id='"+this.id+"' "+onclick+" onmouseover='mv2_survol(true,this);' onmouseout='mv2_survol(false,this);' style='"+mv2_style_police[this.ancre.type]+"color:"+mv2_style_couleur_ancre_element[this.ancre.type][this.theme]+";background-color:"+mv2_style_couleur_ancre_element_fond[this.ancre.type][this.theme]+";padding:1px 10px 0 10px;margin:0 25px 0 0;cursor:"+style_cursor+";height:16px;float:left;'>";
			var    mv2mh_css= "<div id='"+this.id+"' "+onclick+" onmouseover='mv2_survol(true,this);' onmouseout='mv2_survol(false,this);' style='"+mv2_style_police[this.ancre.type]+"color:"+mv2_style_couleur_ancre_element[this.ancre.type][this.theme]+";background-color:"+mv2_style_couleur_ancre_element_fond[this.ancre.type][this.theme]+";padding:1px 10px 0 10px;margin:0 25px 0 0;cursor:"+style_cursor+";height:15px;float:left;'>";
			if((!document.compatMode && !mv2_safari) || (mv2_ie && document.compatMode == "BackCompat")) { /*IE sans doctype*/
				ecritureElement= mv2mh_quirks;
			}
			else {
				ecritureElement= mv2mh_css;
			}
			ecritureElement += this.libelle;
			ecritureElement += "</div>";
		}
		else { 
			ecritureElement= "<div id='"+this.id+"' "+onclick+" onmouseover='mv2_survol(true,this);' onmouseout='mv2_survol(false,this);' style='"+mv2_style_police[this.ancre.type]+"overflow:hidden;width:100%;padding-left:20px;padding-top:1px;padding-bottom:2px;margin:0px;border-bottom:1px solid "+mv2_style_couleur_separateur[this.ancre.type]+";background-color:"+mv2_style_couleur_ancre_element_fond[this.ancre.type][this.theme]+";color:"+mv2_style_couleur_ancre_element[this.ancre.type][this.theme]+";cursor:"+style_cursor+";'>";
			if(this.sousMenu != null) {
				if(mv2_ie) {
					ecritureElement += "<span style='font-family:Webdings;font-size:12px;font-weight:normal;float:right;width:10px;height:14px;overflow:hidden;position:relative;margin:-3px 2px 0 0;'>4</span>";
				}
				else {
					ecritureElement += "<span style='font-size:22px;font-weight:normal;float:right;overflow:hidden;padding:0;margin:-9px 3px -5px 0;position:relative;'>&#8227;</span>";
				}
			}
			ecritureElement += this.libelle;
			ecritureElement += "</div>";
		}
	}
	document.write(ecritureElement);
}
function _mv2_Element_survolElement(_inout) {
	if(!_inout) { 
		this.ancre.menuCourant= null;
		setTimeout("mv2_fermerTousMenus('"+this.ancre.menuAncre.id+"')", mv2_delaiFermeture);
		if(this.sousMenu != null) {
			this.sousMenu.getObjetHTML().style.zIndex= mv2_zIndex_moyen;
			if(mv2_ie && mv2_ie_version >= 5.5) {
				this.sousMenu.getIframeHTML().style.zIndex= mv2_zIndex_moyen - 1;
			}
		}
		var mon_menu= this.parent;
		while(mon_menu != null && mon_menu != this.ancre.menuAncre) {
			mon_menu.getObjetHTML().style.zIndex= mv2_zIndex_moyen;
			if(mv2_ie && mv2_ie_version >= 5.5) {
				mon_menu.getIframeHTML().style.zIndex= mv2_zIndex_moyen - 1;
			}
			mon_menu= mon_menu.parent.parent;
		}
	}
	else { 
		mv2_eteindreTousElements(this.ancre.menuAncre);
		var mon_element= this;
		while(mon_element != null) {
			mon_element.allumerElement();
			mon_element= mon_element.parent.parent;
		}
		this.ancre.menuCourant= this.parent;
		this.parent.fermerSousMenus();
		if(this.sousMenu != null) {
			this.sousMenu.ouvrir(this);
		}
	}
}
function _mv2_Element_allumerElement() {
	if(this.ancre.type == 0) { 
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mv2_style_couleur_element_fond[this.ancre.type][this.theme] : mv2_style_couleur_ancre_element_fond_survol[this.ancre.type][this.theme];
		if(this.parent.parent != null) {
			this.getObjetHTML().firstChild.style.color= mv2_style_couleur_element_survol[this.ancre.type][this.theme]; 
			this.getObjetHTML().style.color= mv2_style_couleur_element_survol[this.ancre.type][this.theme]; 
		}
		else {
			this.getObjetHTML().style.color=  mv2_style_couleur_ancre_element_survol[this.ancre.type][this.theme];
		}
	}
	else {
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mv2_style_couleur_element_fond_survol[this.ancre.type][this.theme] : mv2_style_couleur_ancre_element_fond_survol[this.ancre.type][this.theme];
		this.getObjetHTML().style.color= (this.parent.parent != null)? mv2_style_couleur_element_survol[this.ancre.type][this.theme] : mv2_style_couleur_ancre_element_survol[this.ancre.type][this.theme];
	}
}
function _mv2_Element_eteindreElement() {
	if(!this.toujoursActif) {
		this.getObjetHTML().style.backgroundColor= (this.parent.parent != null)? mv2_style_couleur_element_fond[this.ancre.type][this.theme] : mv2_style_couleur_ancre_element_fond[this.ancre.type][this.theme];
		if(this.parent.parent != null) { 
			if(this.ancre.type == 0) { 
				this.getObjetHTML().firstChild.style.color= "#FFFFFF";
				this.getObjetHTML().style.color= "#7f7f7f";
			}
			else { 
				this.getObjetHTML().style.color= mv2_style_couleur_element[this.ancre.type][this.theme];
			}
		}
		else { 
			this.getObjetHTML().style.color=  mv2_style_couleur_ancre_element[this.ancre.type][this.theme];
		}
	}
}
function _mv2_Element_activerElement() {
	this.allumerElement();
	this.toujoursActif= true;
}
function mv2_survol(_inout, _tagElement) { 
	var tmp= _tagElement.id.split("_elmt_");
	mv2_getMenu(tmp[0]).elements[tmp[1]].survolElement(_inout);
}
function mv2_fermerTousMenus(_idmenuancre) { 
	var mon_menuancre= mv2_getMenu(_idmenuancre);
	if(mon_menuancre.ancre.menuCourant == null) {
		mv2_eteindreTousElements(mon_menuancre);
		mon_menuancre.fermerSousMenus(); 
		mv2_masquerCombos();
	}
}
function mv2_eteindreTousElements(_menuancre) { 
	if(_menuancre.ancre.menuCourant == null) {
		_menuancre.eteindreElementsR();
	}
}
function mv2_position(_table, _recherche) {
	var ma_longueur= _table.length;
	for(var i= 0; i < ma_longueur; i++) {
		if(_table[i] == _recherche) {
			return(i);
		}
	}
	return(-1);
}
function mv2_prefixer(_id_sans_prefixe) {
	return(mv2_prefixeID+_id_sans_prefixe);
}
function mv2_dePrefixer(_id_avec_prefixe) {
	return(_id_avec_prefixe.substring(mv2_prefixeID.length));
}
function mv2_getMenu(_id) {
	var mapos= mv2_position(mv2_menus_ids, _id);
	if(mapos == -1) {
		return(null);
	}
	else {
		return(mv2_menus_objets[mapos]);
	}
}
function mv2_getElement(_rech) {
	var ma_longueur= mv2_menus_objets.length;
	for(var i= 0; i < ma_longueur; i++) {
		var mon_menu= mv2_menus_objets[i];
		var longueur_elements= mon_menu.elements.length;
		for(var j= 0; j < longueur_elements; j++) {
			if(mon_menu.elements[j].libelle.indexOf(_rech) != -1 || (mon_menu.elements[j].action != null && mon_menu.elements[j].action.indexOf(_rech) != -1)) {
				return(mon_menu.elements[j]);
			}
		}
	}
}
function mv2_masquerCombos() {
	if(mv2_ie && mv2_ie_version < 5.5) {
		var masquer= false;
		var longueur_ancres= mv2_ancres.length;
		for(var i= 0; i < longueur_ancres; i++) {
			if(mv2_ancres[i].menuCourant != null) {
				masquer= true;
				break;
			}
		}
		if( (mv2_combosMasquees && !masquer) || (!mv2_combosMasquees && masquer) ) {
			var SELECTs= document.getElementsByTagName("SELECT");
			var nbSELECTs= SELECTs.length;
			var ma_visibilite= (masquer)? "hidden" : "visible";
			for(var i= 0; i < nbSELECTs; i++) {
				SELECTs[i].style.visibility= ma_visibilite;
			}
			mv2_combosMasquees= masquer;
		}
	}
}
function mv2_onclick() {
	var longueur_ancres= mv2_ancres.length;
	for(var i= 0; i < longueur_ancres; i++) {
		mv2_fermerTousMenus(mv2_ancres[i].menuAncre.id);
	}
}