
/*
ouvre une popup
*/
function display_popup(nom_de_la_page, nom_interne_de_la_fenetre)
{
window.open(nom_de_la_page, nom_interne_de_la_fenetre, config='height=400, width=800, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=yes, directories=no, status=no').focus();
}

function VisualisationClic(test) {

    // Recuperation du formulaire ayant pour nom form et modification de son target.
    // C'est ce qui va permettre d'afficher le résulatat de l'action dans une autre fenetre portant le nom _visu
    // Création de la fenetre _visu si elle n'existe pas
    visualisationWindow = window.open(test, '_visu' , 'width=980, height=720, scrollbars=1, resize=1');
    // Dépalcement de la fenetre _visu afin de la décaler de la fenetre courante
    visualisationWindow.moveTo(150,100);
    
    return "";
}