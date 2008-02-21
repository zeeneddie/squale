/*comTraceurAF_v2
0.8		19/11/2003	Première version -Tristan DANIEL
1.2.12	04/10/2004	Ajout de la méthode comTraceurAF_v2_parMenuActionPageCourante (comMenuAF_v2 1.2.12 et plus) -TD
1.2.13	05/10/2005	Amélioration du tag écrit <a href="javascript:_action"> -> <a href="javascript:void();" onclick="_action;return(false);"> -TD (à la de demande de Julien REVERT)
1.3		05/10/2005	Possibilité d'appeler simplement une page avec le nouveau paramètre href
					Le script affiche, dans la barre d'état du navigateur, l'action qui sera effectuée
					Testé sous Op8, IE6, FF1, NS7 -Tristan
1.3.2	10/04/2006	pb ie5 : new RegExp(/x/g) -> /x/g -Tristan
*/
function comTraceurAF_v2_ajouter(_libelle, _action, _href) {
	var mon_lien= "";
	var le_href= (_href != null)? _href : "javascript:void(0);";

	if(document.getElementById("traceur") == null) {
		document.write("<div id='traceur'></div>");
	}
	if(document.getElementById("traceur").innerHTML != "") {
		mon_lien += " &gt; ";
	}
	if(_action == null) {
		if(_href != null) {
			mon_lien += "<a href=\""+le_href+"\">"+_libelle+"</a>";
		}
		else {
			mon_lien += _libelle;
		}
	}
	else {
		var barre_etat= "onmouseover=\"window.status='"+_action.replace(/\'/g, "\\'").replace(/\"/g, '\\"')+"';return(true);\" onmouseout=\"window.status='';return(true);\"";
		switch(typeof(_action)) {
			case "number":
				mon_lien += "<a href=\""+le_href+"\" onclick=\"history.go("+_action+");return(false);\" "+barre_etat+">"+_libelle+"</a>";
				break;
			case "string":
				mon_lien += "<a href=\""+le_href+"\" onclick=\""+_action+";return(false);\" "+barre_etat+">"+_libelle+"</a>";
				break;
			default:
				mon_lien += _libelle;
		}
	}
	document.getElementById("traceur").innerHTML += mon_lien;
}
function comTraceurAF_v2_parMenu(_menu, _page) {
	comTraceurAF_v2_parMenu_util(_menu, _page, false);
}
function comTraceurAF_v2_parMenuActionPageCourante(_menu, _page) {
	comTraceurAF_v2_parMenu_util(_menu, _page, true);
}
function comTraceurAF_v2_parMenu_util(_menu, _page, _action_page_courante) {
	var mon_tableau= _menu.listerParentsPourTraceur(_page, _action_page_courante);
	for(var i= mon_tableau.length - 2; i >= 0; i= i - 2) {
		comTraceurAF_v2_ajouter(mon_tableau[i], mon_tableau[i+1]);
	}
}
