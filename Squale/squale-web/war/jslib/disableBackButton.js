

function disable_backs(){
	while(window.history.next){
		window.history.go(1);
	}
}