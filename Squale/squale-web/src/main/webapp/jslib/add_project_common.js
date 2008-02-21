/******************************
 * Author : BAUSTIER Frantz   *
 * Modified by BERSIN Vincent *
 ******************************/

function commun_traiteReq(_url, _function, _params) {
    if (window.XMLHttpRequest) { // Non-IE browsers
        var req = new XMLHttpRequest();
        req.onreadystatechange = function() {
                if (req.readyState == 4) { // Complete
                        if (req.status == 200) { // OK response
                                _function(_params, req);
                        } else {
                                alert("Problem: " + req.statusText);
                        }
                }
                traitement=0;
        }
        try {
                req.open("GET", _url, true);
        } catch(e) {
                alert(e);
        }
        req.send(null);
    } else if (window.ActiveXObject) { // IE
        req = new ActiveXObject("Microsoft.XMLHTTP");
        if (req) {
                req.onreadystatechange = function() {
                        if (req.readyState == 4) { // Complete
                                if (req.status == 200) { // OK response
                                        _function(_params, req);
                                } else {
                                        alert("Problem: " + req.statusText);
                                }
                        }
                }
                req.open("GET", _url, true);
        req.send(null);
        }
    }
}