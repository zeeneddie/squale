var req="",dbl="",Tab="",objet,mois,annee,format,c,k,n,h;
ie=false;
if((verOffset=navigator.userAgent.indexOf("MSIE"))!=-1){
	ie=true;
	ie_version=parseFloat(navigator.userAgent.substring(verOffset+5,navigator.userAgent.length));
};
ns4=(document.layers);
ie4=(document.all&&!document.getElementById);
var les_jours=new Object(),les_mois=new Object(),les_annees=new Object();
les_jours["fr"]=new Array("L","M","M","J","V","S","D");
les_mois["fr"]=new Array("Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre");
les_jours["en"]=new Array("M","T","W","T","F","S","S");
les_mois["en"]=new Array("January","February","March","April","May","June","July","August","September","October","November","December");
les_annees={pre:"",post:""};
caract_interdits=new Array(new Array("/",";",":","?","=","+"),new Array("%2f","%3b","%3a","%3f","%3d","%2b"));
var FocusChamp=false;
function Arguments(objet,mois,annee,k,n,format,c,focus){
	try{
		if(focus==null){
			this.focus=false;
		}else{
			this.focus=focus;
		};
		d=new Date();
		if(mois==null){
			mois=d.getMonth()+1;
		};
		if(annee==null){
			annee=d.getFullYear();
		};
		if(k==null){
			k=d.getFullYear()-3;
		};
		if(n==null){
			n=d.getFullYear()+3;
		};
		this.objet=objet;
		this.mois=mois;
		this.annee=annee;
		this.k=k;
		this.n=n;
		this.format=format;
		if(c!=null){
			this.c=c.substring(0,2);
		}else{
			this.c="fr";
		};
	}catch(e){
		alert("Erreur : "+e);
	};
};
function afficherCalendrier(page,o,m,a,ad,af,f,l,focus){
	if(ie&&ie_version>=5.5){
		var Oarguments=new Arguments(o,m,a,ad,af,f,l,focus);
		if(screen.width>1279){
			window.showModalDialog('theme/charte_v03_001/html/calendrierPopup.html',Oarguments,"dialogWidth:258px;	dialogHeight:375px; scroll:no; help:yes; status:yes");
		}else{
			window.showModalDialog('theme/charte_v03_001/html/calendrierPopup.html',Oarguments,"dialogWidth:258px; dialogHeight:375px; scroll:no; help:no; status:no");
		};
	}else{
		if(focus){
			if((focus==true)||(focus==false)){
				FocusChamp=focus;
			};
		}else{
			FocusChamp=false;
		};
		var ret=newShowModalDialog('theme/charte_v03_001/html/myModalPopup4.html',o,m,a,ad,af,f,l);
	};
};
function fabriquerCalendrier(){
	if(ie&&ie_version>=5.5){
		objet=window.dialogArguments.objet;
		mois=window.dialogArguments.mois;
		annee=window.dialogArguments.annee;
		format=window.dialogArguments.format;
		c=window.dialogArguments.c;
		k=window.dialogArguments.k;
		n=window.dialogArguments.n;
	}else{
		req=unescape(window.location.search.substr(1,window.location.search.length));
		dbl=req.split("&Arg=");
		Tab=dbl[1].split('|');
		mois=Tab[0];
		annee=Tab[1];
		k=Tab[2];
		n=Tab[3];
		format=Tab[4];
		c=Tab[5];
	};
	try{
		h=new Array(42);
		var aa=document.getElementById('tableCalendrier'),x,y,i=0,pair=false;
		for(y=1;y<document.getElementById('tableCalendrier').rows.length;y++){
			if(pair){document.getElementById('tableCalendrier').rows[y].className="clair";
				pair=false;
			}else{
				pair=true;
			};
			for(x=0;x<7;x++){
				h[i]=document.getElementById('tableCalendrier').rows[y].cells[x];
				i++;
			};
		};
		var option;
		for(var i=0;i<7;i++){
			document.getElementById('tableCalendrier').rows[0].cells[i].innerHTML=les_jours[c][i];
			document.getElementById('tableCalendrier').rows[0].cells[i].className="thead";
		};
		var Fin=(parseInt(n)+1),boucle=0;
		for(i=k;i<Fin;i++){
			var optTemp=new Option(i,i);
			document.getElementById('listeAnnees').options[boucle]=optTemp;
			boucle+=1;
		};
		for(i=1;i<13;i++){
			var optTemp=new Option(les_mois[c][i-1],i);
			document.getElementById('listeMois').options[i-1]=optTemp;
		};
		document.getElementById('listeMois').value=mois;document.getElementById('listeAnnees').value=annee;
	}catch(e){
		alert("Erreur : "+e);
	};
	rafraichirCalendrier();
};
function rafraichirCalendrier(){
	var g=s(mois,annee),q=r(mois,annee),ee,Tmp="",now=new Date(),JourJ=now.getDate(),month=now.getMonth(),year=now.getYear();
	if(year<999)year+=1900;
	try{
		for(i=0;i<g;i++){
			ee=h[i];ee.innerHTML="";
			ee.onmouseover="";
			ee.onmouseout="";
			ee.onclick="";
			ee.className="nodate";
		};for(i=0;i<q;i++){
			ee=h[i+g];
			ee.onmouseover=function(){
				this.className="dateover"
			};
			ee.onclick=function(){
				u(this.innerHTML)
			};
			try{
				ee.style.cursor="pointer";
			}catch(e){
				ee.style.cursor="hand";
			};
			ee.className="date";
			if((JourJ==(i+1))&&(document.getElementById('listeMois').options[document.getElementById('listeMois').selectedIndex].value==(month+1))&&(document.getElementById('listeAnnees').options[document.getElementById('listeAnnees').selectedIndex].value==(year))){ee.innerHTML=(i+1);
				ee.className="date lejour";
				ee.onmouseout=function(){
					this.className="date lejour"
				};
			}else{
				ee.innerHTML=(i+1);
				ee.onmouseout=function(){
					this.className="date"
				};
			};
		};
		for(i=q+g;i<42;i++){
			ee=h[i];
			ee.innerHTML="";
			ee.onmouseover="";
			ee.onmouseout="";
			ee.onclick="";
			ee.className="nodate";
		};
	}catch(e){
		alert("Erreur : "+e);
	};
};
function u(j){
	var x=w(j,mois,annee);
	if(ie&&ie_version>=5.5){
		objet.value=x;
		if(window.dialogArguments.focus==true){
			objet.focus();
		};
	}else{
		window.opener.modalCallBackFunction(window.self,x);
	};
	window.close();
};
function CodageFormatDate(format){
	var retour=format;
	try{
		for(i=0;i<caract_interdits.length;i++){
			if(retour.indexOf(caract_interdits[0][i])>0){
				while(retour.indexOf(caract_interdits[0][i])>0){
					retour=retour.replace(caract_interdits[0][i],caract_interdits[1][i]);
				};
			};
		};
	}catch(e){
		alert("CodageFormatDate : "+e);
	};
	return retour;
};
function w(_j,_m,_a){
	var retour=format;
	if((retour==null)||(retour==undefined)){
		retour="dd/MM/yyyy";
	}
	else{
		if(retour=="mm/jj/aaaa"){
			retour="MM/dd/yyyy";
		};
	};
	var j=(_j<10)?"0"+_j:_j,m=(_m<10)?"0"+_m:_m,a2=_a+"",a2=a2.substring(a2.length-2,a2.length);
	retour=retour.replace(/dd/g,j);
	retour=retour.replace(/d/g,_j);
	retour=retour.replace(/MM/g,m);
	retour=retour.replace(/M/g,_m);
	retour=retour.replace(/yyyy/g,_a);
	retour=retour.replace(/yy/g,a2);
	return(retour);
};
function r(m,a){
	if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
		return(31);
	}else if(m==4||m==6||m==9||m==11){return(30);}
	else{
		var y=a-1900;
		if((y==100)||(y%4==0&&y%100!=0)||(y%400==0)){
			return(29);
		}else{
			return(28);
		};
	};
};
function s(m,a){
	var g=new Date(a,m-1,1),b=g.getDay()-1;
	if(b==-1){
		b=6;
	};
	return(b);
};
function Traite_Arguments(oArg){
	var Lg="fr",stArguments="";
	try{stArguments+=oArg.mois+"|";
		stArguments+=oArg.annee+"|";
		stArguments+=oArg.k+"|";
		stArguments+=oArg.n+"|";
		if(oArg.format){
			stArguments+=CodageFormatDate(oArg.format)+"|";
		}else{
			stArguments+=CodageFormatDate("dd/MM/yyyy")+"|";
		};
		stArguments+=oArg.c;
	}catch(e){
		alert("PB Traite_Arguments : "+e);
	};
	return stArguments;
};
function newShowModalDialog(url,ref,m,a,ad,af,f,l){
	var Oarguments=new Arguments(ref,m,a,ad,af,f,l),mode="champ",windowParam="modal=yes,directories=0,menubar=0,titlebar=0,toolbar=0,width=350,height=290",reference=ref.id,winOpenned=MODAL_alreadyOpen(mode,reference);
	if(winOpenned!=null){
		winOpenned.focus();
		return;
	};
	var id=MODAL_DIALOG_LIST.length,newWin=window.open(url+'?&Arg='+Traite_Arguments(Oarguments),'modal_'+id,windowParam);MODAL_DIALOG_LIST.push(Array(newWin,mode,reference,true));
};
function modalCallBackFunction(winRef,newValue){
	if(newValue==null){
		return;
	};
	var modalObj=MODAL_findModalByWinRef(winRef);
	if(MODAL_findModalByWinRef==null){
		alert('Error : unable to found window reference for callBack procedure.');
	}else{
		var modeList=Array();
		modeList=modalObj[1].split('|');
		for(var i=0;i<modeList.length;i++){
			switch(modeList[i]){
				case'innerHTML':var tmp=document.getElementById(modalObj[2]);
					if(tmp){
						tmp.innerHTML=newValue;
					};
					break;
				case'champ':var tmp=document.getElementById(modalObj[2]);
					if(tmp){
						tmp.value=newValue;
					};
					if(FocusChamp==true)tmp.focus();
					break;
				case'value':var tmp=document.getElementById(modalObj[2]);
					if(tmp){tmp.value=newValue;};
					break;
				case'function':eval(modalObj[2]+'(\''+newValue+'\')');
					break;
				case'functionRef':modalObj[2](newValue);
					break;
				default:if(modeList[i].indexOf('style.')==0){
					try{
						eval('document.getElementById(\''+modalObj[2]+'\').'+modeList[i]+'=\''+newValue+'\';');
					}catch(e){
						;
					};
				};
				break;
			};
		};
	};
};
var MODAL_DIALOG_LIST=Array();
function MODAL_alreadyOpen(mode,reference){
	for(var i=0;i<MODAL_DIALOG_LIST.length;i++){
		if(MODAL_DIALOG_LIST[i][1]==mode&&MODAL_DIALOG_LIST[i][2]==reference&&MODAL_DIALOG_LIST[i][3]){
			if(MODAL_DIALOG_LIST[i][0].document){
				return MODAL_DIALOG_LIST[i][0];
			}else{
				MODAL_DIALOG_LIST[i][3]=false;
			};
		};
	};
	return null;
};
function MODAL_findModalByWinRef(winRef){
	for(var i=0;i<MODAL_DIALOG_LIST.length;i++){
		if(MODAL_DIALOG_LIST[i][0]==winRef&&MODAL_DIALOG_LIST[i][3]){
			if(MODAL_DIALOG_LIST[i][0].document){
				return MODAL_DIALOG_LIST[i];}else{MODAL_DIALOG_LIST[i][3]=false;
			};
		};
	};
	return null;
};
function MODAL_forceFocusOnModal(){
	for(var i=0;i<MODAL_DIALOG_LIST.length;i++){
		if(MODAL_DIALOG_LIST[i][3]&&MODAL_DIALOG_LIST[i][0].document){
			MODAL_DIALOG_LIST[i][0].focus();
		};
	};
	return;
};

if(document.addEventListener){
	document.addEventListener("focus",MODAL_forceFocusOnModal,true);
	}else if(document.attachEvent){
		document.attachEvent("onfocus",MODAL_forceFocusOnModal);
	}
