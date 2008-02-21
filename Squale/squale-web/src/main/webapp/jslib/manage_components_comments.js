/*
ajout le model de commentaire sur un composant
*/
function add_comment_template(date)
{
	var justif = componentForm.justification;
	var matricule = componentForm.matricule;
	justif.value = justif.value +  date + " -- " + matricule.value + ":\n" ;
}

/*
efface les commentaires d'un composant
*/
function erase_comment()
{
	var justif = componentForm.justification;
	justif.value = "";
}


