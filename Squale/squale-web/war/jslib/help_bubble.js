
// Définition "globale" de la position de l'élement bulle
var x = 0;
var y = 0;

/*
Calcule la position du curseur
*/
function position(e){
	x = (navigator.appName.substring(0,3) == "Net") ? e.pageX : event.x+document.body.scrollLeft;
	y = (navigator.appName.substring(0,3) == "Net") ? e.pageY : event.y+document.body.scrollTop;
}

/*
Affiche une bulle d'aide à l'emplacement pointé par la souris
*/
function displayHelp(message){
		var bubble = document.getElementById('help_bubble');
		bubble.innerHTML = message;
		// verifie que c'est pas en dehors de la fenetre sinon c'est moche
		if (x - document.body.scrollLeft + 60 >= document.body.clientWidth)
		{
			bubble.style.left = x - 55;
			bubble.style.top = y + 15;
		}
		else 
		{
			bubble.style.left = x + 15;
			bubble.style.top = y;
		}
		bubble.style.visibility = 'visible';
}

function hideHelp(){
	var bubble = document.getElementById('help_bubble');
	bubble.style.visibility = 'hidden';
}

// Initialise l'objet bulle
document.write('<span id="help_bubble" style="border:solid 1px #000;padding-left:3px;padding-right:3px;background-color:#ffffcc;color:#000;position:absolute;visibility:hidden;z-index:100;"></span>');
document.onmousemove = position;