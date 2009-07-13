// Document editor plugin.
// 
// Implementation by Rémy Bouquet
//
// Word processiong look and feel
//
// $Id: document-editor.js,v 0.1 2004/07/21 09:25:32 Rémy Exp $


var nomedit=null;
var newPage=null;
var maxsize=933;//24,7 cm
var modified=false;

//Fonction d'initialisation du Plugin
function DocumentEditor(editor,params) {
	
	nomedit=params[0];
	newPage="<div id='header'></div><div id='edit'></div><div id='footer'></div>";
	
	var cfg = editor.config;
	var toolbar = cfg.toolbar;
	var self = this;
	var i18n = DocumentEditor.I18N;
	var doc = editor._doc;
	this.range=null;
	this._statusBar=null;
	cfg.statusBar=false;
	
	
	cfg.registerButton("pageNum",i18n["Page number"], editor.imgURL("images/ico_no_page.gif"), false,
		   function(editor, id) {
			  self.btnPress(editor, id);
		   });
	cfg.registerButton("nbPage",i18n["Number of pages"], editor.imgURL("images/ico_nb_pages.gif"), false,
		   function(editor, id) {
			  self.btnPress(editor, id);
		   });
	cfg.registerButton("zoomin",i18n["Zoom in"], editor.imgURL("images/ico_zoom_in.gif"), false,zoomIn);
	cfg.registerButton("zoomout",i18n["Zoom out"], editor.imgURL("images/ico_zoom_out.gif"), false,zoomOut);
	
	cfg.registerButton("paste",HTMLArea.I18N.tooltips["paste"], editor.imgURL("images/ed_paste.gif"), false, self.btnPress);	   
	cfg.registerButton("bold",HTMLArea.I18N.tooltips["bold"], editor.imgURL("images/ed_format_bold.gif"), false, self.btnPress);	   
	cfg.registerButton("italic",HTMLArea.I18N.tooltips["italic"], editor.imgURL("images/ed_format_italic.gif"), false, self.btnPress);	   
	cfg.registerButton("underline",HTMLArea.I18N.tooltips["underline"], editor.imgURL("images/ed_format_underline.gif"), false, self.btnPress);	   
	cfg.registerButton("justifyleft",HTMLArea.I18N.tooltips["justifyleft"], editor.imgURL("images/ed_align_left.gif"), false, self.btnPress);	   
	cfg.registerButton("justifycenter",HTMLArea.I18N.tooltips["justifycenter"], editor.imgURL("images/ed_align_center.gif"), false, self.btnPress);	   
	cfg.registerButton("justifyright",HTMLArea.I18N.tooltips["justifyright"], editor.imgURL("images/ed_align_right.gif"), false, self.btnPress);	   
	cfg.registerButton("justifyfull",HTMLArea.I18N.tooltips["justifyfull"], editor.imgURL("images/ed_align_justify.gif"), false, self.btnPress);	   
	cfg.registerButton("insertorderedlist",HTMLArea.I18N.tooltips["insertorderedlist"], editor.imgURL("images/ed_list_num.gif"), false, self.btnPress);	   
	cfg.registerButton("insertunorderedlist",HTMLArea.I18N.tooltips["insertunorderedlist"], editor.imgURL("images/ed_list_bullet.gif"), false, self.btnPress);	   
	cfg.registerButton("outdent",HTMLArea.I18N.tooltips["outdent"], editor.imgURL("images/ed_indent_less.gif"), false, self.btnPress);	   
	cfg.registerButton("indent",HTMLArea.I18N.tooltips["indent"], editor.imgURL("images/ed_indent_more.gif"), false, self.btnPress);	   
	cfg.registerButton("justifyfull",HTMLArea.I18N.tooltips["justifyfull"], editor.imgURL("images/ed_align_justify.gif"), false, self.btnPress);	   
	

	cfg.fontsize = {"8":"8pt","9":"9pt","10":"10pt","11":"11pt","12":"12pt","14":"14pt","16":"16pt","18":"18pt","20":"20pt","22":"22pt","24":"24pt","26":"26pt","28":"28pt","36":"36pt","48":"48pt","72":"72pt"};
	var myfontname = {
		"Arial":	   'Arial',
		"Courier New": 'Courier New',
		"Georgia":	   'Georgia',
		"Tahoma":	   'Tahoma',
		"Times New Roman": 'Times New Roman',
		"Verdana":	   'Verdana',
		"Impact":	   'Impact',
		"WingDings":	   'Wingdings'
	};	

	
	cfg.registerDropdown({
						id	: 'myfontname',
						options	: myfontname,
						refresh  : self.comboRefresh,
						action   : self.comboChange
					});
					
	cfg.registerDropdown({
						id	: 'myfontsize',
						options	: cfg.fontsize,
						refresh  : self.comboRefresh,
						action   : self.comboChange
					});
						
	toolbar[0].unshift("myfontsize");
	toolbar[0].unshift("myfontname");
			   
	var tb=["linebreak"];
	tb.push("pageNum");
	tb.push("nbPage");
	tb.push("zoomin");
	tb.push("zoomout");
	toolbar.push(tb); 
	
	if(editor.plugins["ContextMenu"]!=null)
		editor.plugins["ContextMenu"].instance.applyTo=this;
	editor.onGenerate=function()
	{				

		
		HTMLArea._removeEvent = function(el, evname, func) {
	if (HTMLArea.is_ie) {
		el.detachEvent("on" + evname, func);
	} else {
		el.removeEventListener(evname, func, true);
	}
};
		initDocument(this);
		initStatusBar(this);
		editor._iframe.style.width=document.body.clientWidth-2;
	}
	
}; 

function persist()
{
	eval(nomedit)._textArea.value = eval(nomedit).getHTML();	
}

//ajoute une page quand la taille est dépassée.
function appendPage()
{
		var editor=eval(nomedit);
		var doc=editor._doc;
		var nvlPage=doc.createElement("<div>");
		doc.body.appendChild(nvlPage);
		nvlPage.id='page';
		nvlPage.innerHTML=newPage;
		initPage(nvlPage);
		updateHeadFoot();
		return nvlPage;
};

//enleve la dernière page quand elle est vide.
function removelastPage()
{
		var editor=eval(nomedit);
		var doc=editor._doc;
		var pages=doc.all.page;
		var i;
		if (pages!=null)
			if(pages.length!=null)
				{	
					doc.body.removeChild(pages[pages.length-1]);
				}		
		updateHeadFoot();
};

//réajuste les paragraphses quand un ligne à été supprimée.
function rearange(div)
{		
	var nextPage=getNextPage(div.parentElement);
	if (nextPage!=null)
	{
		var zoom=parseInt(eval(nomedit)._doc.body.style.zoom.substring(0,eval(nomedit)._doc.body.style.zoom.length-1))/100;
		var lastP=getLastParagraph(div);
		var firstP=getFirstParagraph(nextPage.all.edit);
		if (firstP!=null)
		{
			var height=(lastP.offsetTop+lastP.offsetHeight+firstP.offsetHeight);
			var tailleMax=Math.ceil(maxsize*zoom);
			if (height<tailleMax)
				{
					div.appendChild(firstP);//elève aussi le paragraphe de la page suivante					
				}
			rearange(nextPage.all.edit);
		}
		else
		{
			removelastPage();
		}
	}	
}

//fait glisser le texte d'une page à l'autre lors d'un retour chariot, rajoute une page si besoin
function performPageBreak(div)
{
		var zoom=parseInt(eval(nomedit)._doc.body.style.zoom.substring(0,eval(nomedit)._doc.body.style.zoom.length-1))/100;
		var lastp=getLastParagraph(div);
		var taille=0;
		if (lastp!=null)
			taille=lastp.offsetTop+lastp.offsetHeight;
			
		var tailleMax=Math.ceil(maxsize*zoom);
		while (taille>tailleMax)
		{
			var page=div.parentElement;
			var editor=eval(nomedit); 
			var focus=false;
			var nvlPage=false;
			var nextpage;
			var texte=lastp.outerHTML;
				
			if (editor.getParentElement()==lastp)
				focus=true;

			if (isLastPage(page))
			{
				nextpage=appendPage();
				if (focus)
					nextpage.all.edit.focus();
				addFirstParagraph(nextpage.all.edit,texte.trim());
				removeLastParagraph(div);
			}
			else
			{
				nextpage=getNextPage(page);
				addFirstParagraph(nextpage.all.edit,texte.trim());
				if (focus)
					nextpage.all.edit.focus();
				removeLastParagraph(div);
				performPageBreak(nextpage.all.edit);	
			}
			
			lastp=getLastParagraph(div);
			taille=lastp.offsetTop+lastp.offsetHeight;	
		}
}

//retourne le numero d'une page
function getPageNumber(page,editor)
{
	if (editor==null)
		editor=eval(nomedit);
	var pages=editor._doc.body.all.page;
	if (pages!=null)
		if (pages.length!=null)
		{
			for (i=0;i<pages.length;i++)
			{
				if (page==pages[i])
					return i;
			}			
		}
	
	return 0;
}

//retourne le nombre de pages du document
function getNbPages(editor)
{
	if (editor==null)	
		editor=eval(nomedit);
	var pages=editor._doc.body.all.page;
	if (pages!=null)
		if (pages.length!=null)
			return pages.length;
	return 1;		
}

//retourne la page portant le numero i
function getPage(i)
{
	var editor=eval(nomedit);
	var pages=editor._doc.body.all.page;
	if (pages!=null)
		if (pages.length!=null)
		{
			if (i<pages.length)
				return pages[i];
		}
	return null;	
}

//retourne la page suivante
function getNextPage(page)
{
	return getPage(getPageNumber(page)+1);
}

//retourne la page précédente
function getPreviousPage(page)
{
	return getPage(getPageNumber(page)-1);
}

//retourne le dernier paragraphe d'un édit
function getLastParagraph(edit)
{
	var ps=edit.children;
	if (ps!=null)
		if (ps.length!=null )
			{
				if (ps.length==0)
					return null;
				var type=ps[0].tagName;
				var i=ps.length-1
				while (!type.match(/p|h[0-9]/gi) && i>0)
				{
					i--;
					type=ps[i].tagName;
				}
				return ps[i];
			}	
	if (ps.tagName!= null && ps.tagName.match(/p|h[0-9]/gi))		
		return ps;	
	return null;	
}

//retourne le premier paragraphe d'un édit
function getFirstParagraph(edit)
{
	var ps=edit.children;
	if (ps!=null)
		if (ps.length!=null)
			{
				if (ps.length==0)
					return null;
				var type=ps[0].tagName;
				var i=0
				while (!type.match(/p|h[0-9]/gi) && i<ps.length-1 && ps[i].innerHTML=="")
				{
					if (ps[i].innerHTML=="")	
		 				edit.removeChild(ps[i]);
		 			else
		 				i++;
					type=ps[i].tagName;
				}
				return ps[i];	
			}
	if (ps.tagName!= null && ps.tagName.match(/p|h[0-9]/gi))
	 return ps;	
	return null; 
	}

//retire le dernier paragraphe d'une page
function removeLastParagraph(edit)
{
	var ps=edit.children;
	if (ps!=null)
		if (ps.length!=null)
		{	
			var type=ps[0].tagName;
			var i=ps.length-1
			while (!type.match(/p|h[0-9]/gi) && i>0)
				{
					i--;
					type=ps[i].tagName;
				}
			if (i!=-1)
	 			edit.removeChild(ps[i]);
		}
		else
		{
			if (ps.tagName.match(/p|h[0-9]/gi))
				edit.removeChild(ps);
		}
}

//retire le premier paragraphe d'une page
function removeFirstParagraph(edit)
{
	var ps=edit.children;
	if (ps!=null)
		if (ps.length!=null)
		{	
			var type=ps[0].tagName;
			var i=0
			while (!type.match(/p|h[0-9]/gi) && i<ps.length-1)
				{
					i++;
					type=ps[i].tagName;
				}
			if (i!=ps.length)
	 			edit.removeChild(ps[i]);
		}
		else
		{
			if (ps.tagName.match(/p|h[0-9]/gi))
				edit.removeChild(ps);
		}
}

//ajoute un paragraphe au début d'un edit
function addFirstParagraph(edit,texte)
{
	edit.innerHTML=texte+edit.innerHTML;
}

// retourne vrai si la page passée en paramètre est la dernière page du document.
function isLastPage(page)
{
	var editor=eval(nomedit);
	if (page==getLastPage(editor))
		return true;
	return false;	
}

//sauvegarde la range de l'éditeur
function saveRange(event)
{
	var editor=eval(nomedit);
	editor.plugins["DocumentEditor"].instance.range=editor._doc.selection.createRange();
}

//initialise une page
function initPage(page)
{
		if(page!=getFirstPage(eval(nomedit)))
			setHeaderFooterText(page);
		
		page.contentEditable=false;
		page.all.footer.contentEditable=false;
		page.all.header.contentEditable=false;
		page.all.edit.contentEditable=true;

		page.all.edit.attachEvent("oncontextmenu",function (event) {
				if (HTMLArea.is_ie)
					event=document.activeElement.contentWindow.event;
					event.returnValue=false;
			    return eval(nomedit).plugins["ContextMenu"].instance.popupMenu(event);
			    
		    });		
		page.all.edit.attachEvent("onblur",saveRange);		
		page.all.edit.attachEvent("onfocus",updateStatusBar);		
		initHeaderFooter(page);
};

//fonction d'initialisation des Headers et des footers.
function initHeaderFooter(page)
{
	page.all.header.attachEvent("onblur",updateHeadFoot);
	page.all.footer.attachEvent("onblur",updateHeadFoot);
};

//initialise la première page
function initFirstPage(editor)
{
		var page=getFirstPage(editor);
		
		page.all.header.attachEvent("onblur",saveRange);	
		page.all.footer.attachEvent("onblur",saveRange);
		
		page.all.header.contentEditable=true;
		page.all.footer.contentEditable=true;		
		page.all.edit.focus();
};

function updateStatusBar()
{
	var event=document.activeElement.contentWindow.event;
	var div=event.srcElement;
	var stbar=eval(nomedit).plugins["DocumentEditor"]._statusBar;
	if(stbar!=null)
		stbar.all.numpage.innerText=getPageNumber(div.parentElement)+1;
}

//Fonction d'initialisation du document page par page
function initDocument(editor)
{
		var pages=editor._doc.body.all("page");
		
		editor._doc.attachEvent("onselectionchange",saveRange);
		if (pages==null)
		{
			pages=editor._doc.createElement("<div ID='page'>");
			editor._doc.body.appendChild(pages);
			pages.innerHTML=newPage;
		}
		editor._doc.body.contentEditable=false;
		
		if(pages!=null)
			if (pages.length!=null)
			{
				for (i=0;i<pages.length;i++) 
					{
						initPage(pages[i]);
					}
			}
			else
			{
				initPage(pages);
			}	
		initFirstPage(editor);	
		updateHeadFoot();	
		editor._doc.body.style.zoom='100%';	
};

function initStatusBar(editor)
 {
	
	var statusbar = document.createElement("div");
	statusbar.className = "statusBar";
	editor._htmlArea.appendChild(statusbar);
	editor.plugins["DocumentEditor"]._statusBar = statusbar;
	var zoomText=document.createElement("<SPAN>");
	statusbar.appendChild(zoomText);
	zoomText.innerText="Zoom : ";
	var zoom = document.createElement("<SPAN ID='zoom'>");
	statusbar.appendChild(zoom);
	zoom.innerText=editor._doc.body.style.zoom;
	var pagestext=document.createElement("<SPAN>");
	statusbar.appendChild(pagestext);
	pagestext.innerText=" | Page : ";
	var numpage = document.createElement("<SPAN ID='numpage'>");
	statusbar.appendChild(numpage);
	numpage.innerText="1";
	var slash=document.createElement("<SPAN>");
	statusbar.appendChild(slash);
	slash.innerText="/";
	var nbpages = document.createElement("<SPAN ID='nbpages'>");
	statusbar.appendChild(nbpages);
	nbpages.innerText=getNbPages();
	//var pageheight = document.createElement("<SPAN id='pageheight'>");
	//statusbar.appendChild(pageheight);
	//pageheight.innerText="height";
};


function updateHeadFoot()
{
	var pages=eval(nomedit)._doc.all.page
	if (pages!=null)
		if(pages.length!=null)
			for(i=1;i<pages.length;i++)
				setHeaderFooterText(pages[i]);
	updatePageNum();
};

function updatePageNum()
{
	var editor=eval(nomedit);
	var pages=editor._doc.body.all.page;
	if (pages!=null)
		if (pages.length!=null)
		{
			for (i=0;i<pages.length;i++)
			{
				var num=pages[i].all.num
				if (num!=null)
					if(num.length!=null)
					{
						for(j=0;j<num.length;i++)
							setTextFirstNode(num[j],""+(i+1));
					}
					else
					{	
						setTextFirstNode(num,""+(i+1));
					}
			}			
		}
		
	var nb=	editor._doc.body.all.nb
	if (nb!=null)
		if(nb.length!=null)
		{	
			for(i=0;i<nb.length;i++)
				nb[i].innerHTML=getNbPages();
		}
		else
		{
			nb.innerHTML=getNbPages();
		}
		var stbar=editor.plugins["DocumentEditor"]._statusBar;
	if (stbar!=null)	
		stbar.all.nbpages.innerText=getNbPages();
}

//assigne du texte à un div
function setTextFirstNode(div,texte)
{
	if(div.hasChildNodes())
	{
		var i=0;
		var node=div.childNodes[0];
		while(node.nodeType!=3 && i<div.childNodes.length)
		{
			i++;
			node=div.childNodes[i];
		}
		if (i!=div.childNodes.length)
			node.data=texte;
		else
			div.insertAdjascentText("afterBegin",texte);	
	}
	else
	{
		div.innerHTML=tetxe;
	}
}
//retourne la dernière page du document
function getLastPage(editor)
{
	var pages=editor._doc.body.all("page");
	if (pages.length!=null)
		return pages[pages.length-1];
	return pages;	
};

//retourne la première page du document.
function getFirstPage(editor)
{
	var pages=editor._doc.body.all("page");
	if (pages.length!=null)
		return pages[0];
	return pages;	
};

//retourne le contenu du header.
function getHeaderHTML()
{
	return getFirstPage(eval(nomedit)).all("header").innerHTML;
}

//retourne le contenu du Footer
function getFooterHTML()
{
	return getFirstPage(eval(nomedit)).all("footer").innerHTML;
}

//assigne le texte du header et du footer sur une page
function setHeaderFooterText(page)
{	
	page.all.header.innerHTML=new String(getHeaderHTML());
	page.all.footer.innerHTML=new String(getFooterHTML());
}


DocumentEditor._pluginInfo = {
	name          : "DocumentEditor",
	version       : "0.1",
	developer     : "Rémy Bouquet",
	developer_url : "pfff",
	c_owner       : "Squale",
	sponsor       : "pfff",
	sponsor_url   : "pfffff",
	license       : "pfffffffff"
};

/////////////////////////////////////////////////////////////
// Callbacks des boutons.
/////////////////////////////////////////////////////////////


DocumentEditor.prototype.execCommand=function(id)
{
	var editor=eval(nomedit);
	this.btnPress(editor,id);
}

DocumentEditor.prototype.btnPress = function(editor,id) {

	switch (id) {
	    case "btnPageBreak":
		 			var doc=editor._doc;
		 			doc.print();
					break; 
		case "pageNum":
					var range=editor.plugins["DocumentEditor"].instance.range;
					range.collapse(false);
					var parent=range.parentElement();
					while(parent && parent.tagName.toLowerCase()!="body" && parent.id!="page")
						parent=parent.parentElement;
					if(parent && parent.tagName.toLowerCase()!="body")	
					{
						range.pasteHTML("<span id='num'>"+(getPageNumber(parent)+1)+"</span>");	
						nums=range.parentElement().all.num;
							if (nums.length!=null)
							{
								for(i=0;i<nums.length;i++)
									nums[i].contentEditable=false;
							}
							else
							{
								nums.contentEditable=false;
							}
					}
					break;
		case "nbPage":
					var range=editor.plugins["DocumentEditor"].instance.range;
					range.collapse(false);
					var parent=range.parentElement();
					if(parent!=null && parent.tagName.toLowerCase()!="body")
					{	range.pasteHTML("<span id='nb'>"+getNbPages()+"</span>");
						nbs=range.parentElement().all.nb;
							if (nbs.length!=null)
							{
								for(i=0;i<nbs.length;i++)
									nbs[i].contentEditable=false;
							}
							else
							{
								nbs.contentEditable=false;
							}
					}			
					break;
		case "paste":			
					paste();					
					break;
			
		default : 
				var edRange=editor.plugins["DocumentEditor"].instance.range;
				if (edRange!=null && edRange.parentElement().tagName!='BODY')
					edRange.execCommand(id);
					break;
					
	}
};
DocumentEditor.prototype.comboChange = function (editor) {
	var id=this.id;
	var range=editor.plugins["DocumentEditor"].instance.range;
	switch (id) {
		case 'myfontname' : 
		case 'myfontsize' : changeFont(range,id);break;
		default: break;
	}
}

DocumentEditor.prototype.comboRefresh = function (editor) {
	var id=this.id;
	var options =editor.config[id.substring(2,id.length)];
	var range = editor._createRange(editor._getSelection());
	if (range!=null && range.length==null)
	{
		var parent=range.parentElement();
		tag=parent.tagName.toLowerCase();
		var val="";
		if (id=='myfontname')
		{	val=parent.style.fontFamily;
		}else
		{val=parent.style.fontSize;}
	
		while(tag!="body" && val=="")
		{
			parent=parent.parentElement;
			tag=parent.tagName.toLowerCase();
			if (id=='myfontname')
			{	val=parent.style.fontFamily;
			}else
			{val=parent.style.fontSize;}
		}
		if (tag!="body")
		{
				var k = 0;
				var value="";
				if (id=='myfontname')
				{
					value=parent.style.fontFamily.toLowerCase();
				}
				else
				{
					value=parent.style.fontSize.toLowerCase();
				}
				var tbobj = editor._toolbarObjects[id];
				for (var j in options) {
					if ((j.toLowerCase() == value) ||
				    (options[j].substr(0, value.length).toLowerCase() == value)) {
						if (id=='myfontname')
						{
							tbobj.element.selectedIndex = k-1;
						}else
						{
							tbobj.element.selectedIndex = k;
						}
						break;
					}
					++k;
				}
		}
		else
		{
			if (id=='myfontname')
			{
				editor._toolbarObjects[id].element.selectedIndex = 0;
			}else
			{
				editor._toolbarObjects[id].element.selectedIndex = 4;
			}
		}	
	}
	else
	{
		if (id=='myfontname')
		{
			editor._toolbarObjects[id].element.selectedIndex = 0;
		}else
		{
			editor._toolbarObjects[id].element.selectedIndex = 4;
		}
	}	
}

function changeFont(range,id)
{
	var len=range.text.length;
	var tbobj = eval(nomedit)._toolbarObjects[id];
	var startpoint=range.duplicate();
	startpoint.collapse();
	var tag=getParent(startpoint);
	if (tag==null)
		return;
	if (tag!=null && tag.tagName.toLowerCase()=='div' && tag.id=='edit')
	{
		applyFont(tag,id,tbobj);	
	}
	else
	{if ((len>0 && range.htmlText.toUpperCase().indexOf("<P")<0 && range.htmlText.toUpperCase().indexOf("<LI")<0))
		{	
			var html=range.text;
			if (id=='myfontname')
			{
				range.pasteHTML("<span style=\"font-family:"+tbobj.element.value+"\">"+html+"</span>");
			}else
			{
				range.pasteHTML("<span style=\"font-size:"+tbobj.element.value+"\">"+html+"</span>");
			}
		}else
		{
			
			var endpoint=range.duplicate();
			endpoint.collapse(false);
			var endTag=getParent(endpoint);		
			while (tag!=null && tag!=endTag)
			{
				applyFont(tag,id,tbobj);		
				tag=tag.nextSibling;
			}
			applyFont(endTag,id,tbobj);		
		}	
	}
}
function applyFont(tag,id,tbobj)
{
	if (tag.tagName.toLowerCase()=='li')
	{
		if (id=='myfontname')
		{
			tag.innerHTML="<span style=\"font-family:"+tbobj.element.value+"\">"+tag.innerHTML+"</span>";
		}else
		{
			tag.innerHTML="<span style=\"font-size:"+tbobj.element.value+"\">"+tag.innerHTML+"</span>";
		}
	}
	if (tag.hasChildNodes)
	{	
		var i;
		for (i=0;i<tag.children.length;i++)
		{
			if (tag.children[i].tagName.toUpperCase()=="SPAN" || tag.children[i].tagName.toUpperCase()=="LI")
			{
				applyFont(tag.children[i],id,tbobj);
			}
		}
	}
	if (id=='myfontname')
	{
		tag.style.fontFamily=tbobj.element.value;
	}else
	{
		tag.style.fontSize=tbobj.element.value;
	}
}

function getParent(range)
{
	var parent=range.parentElement();
	var tag=parent.tagName.toLowerCase();
	while(!tag.match(/body|p|div|li|span/gi))
	{
		parent=parent.parentElement;
		tag=parent.tagName.toLowerCase();
	}
	if ((tag=="body"))
		return null;
	return parent;	
}
function paste()
{
	var edRange=eval(nomedit).plugins["DocumentEditor"].instance.range;
	
	if (edRange!=null)
	{	
		if (edRange.parentElement().id!='page' && edRange.parentElement().tagName!='BODY')
		{
			edRange.execCommand('paste');
			wordClean();
		}		
	}
}

//pagine et netoie les éventuelles trace d'un html un peu trop microsoftien.
function wordClean() {
	
	var editor=eval(nomedit);
	var pages=editor._doc.all.page;
	if (pages!=null)
	{
		
		
		
		if (pages.length!=null)
			{	
				var i; 	
				for(i=0;i<pages.length;i++)
				{
					performPageBreak(pages[i].all.edit);					
				}
			}
			else
			{
				performPageBreak(pages.all.edit);					
			}
			
			
			editor._wordClean();			
			nukeWordShityQuote();
			turnTags();
			nukeHTags();			
			editor.updateToolbar();
	}
};

function nukeWordShityQuote()
{
	var text=eval(nomedit)._doc.body.innerHTML;
	
	text=text.replace(/’/gi,'\'');
	eval(nomedit)._doc.body.innerHTML=text;
}
function nukeHTags()
{
	var text=eval(nomedit)._doc.body.innerHTML;
	text=text.replace(/<h[0-9]>/gi,'<p>');
	text=text.replace(/<\/h[0-9]>/gi,'</p>');
	eval(nomedit)._doc.body.innerHTML=text;
}

function turnTags()
{
	var text=eval(nomedit)._doc.body.innerHTML;
	text=text.replace(/«Qualité»/gi,'#CIVILITE#');
	text=text.replace(/«Prénom»/gi,'#PRENOM#');
	text=text.replace(/«Nom»/gi,'#NOM#');
	text=text.replace(/«Adresse»/gi,'#ADRESSE#');
	text=text.replace(/«date_edition»/gi,'#DATE_JOUR#');
	text=text.replace(/«RefOafi»/gi,'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
	text=text.replace(/«dtdebstg_cour»,/gi,'#DATEDEBUTSTAGE#');
	text=text.replace(/«Lib_Cen»,/gi,'#LIEUSTAGE#');
	text=text.replace(/«Dtcourrier»,/gi,'#DATECOURRIER#');
	text=text.replace(/«Grade»,/gi,'#GRADE#');
	text=text.replace(/«date_début_conv»,/gi,'#DEBUT_CONV#');
	text=text.replace(/«date_fin_conv_Armee»,/gi,'#FIN_CONV#');
	text=text.replace(/«Date_début_RFT»,/gi,'#DATEDEBUT_RFT#');
	text=text.replace(/«RFT_TRR_PQT»,/gi,'#REMU_RFTTRRPQT#');
	text=text.replace(/«Ref_OAFI_CDI»,/gi,'');
	text=text.replace(/«CDI»,/gi,'#REMU_CDI#');
	text=text.replace(/«DtCour_Cour»,/gi,'#DATECOURRIER#');
	

	eval(nomedit)._doc.body.innerHTML=text;
}
function zoomIn(editor) {
	 var zoom=parseInt(editor._doc.body.style.zoom)	
	 var newZoom;
	 if (zoom<200)
	    newZoom= zoom+10+'%';
	 else	 
    	newZoom= '200%';

      editor._doc.body.style.zoom =newZoom;
      editor.plugins["DocumentEditor"]._statusBar.all.zoom.innerText=newZoom+'';
	 
  } 
function zoomOut(editor) {
 	var zoom=parseInt(editor._doc.body.style.zoom)	
	 var newZoom;
	 if (zoom>10)
	    newZoom= zoom-10+'%';
	 else	 
    	newZoom= '10%';
  
      editor._doc.body.style.zoom =newZoom;
      editor.plugins["DocumentEditor"]._statusBar.all.zoom.innerText=newZoom+'';
  }
  
DocumentEditor.prototype.onKeyPress= function ()
{
	var ev=document.activeElement.contentWindow.event;
	var editor=eval(nomedit);
	var div=ev.srcElement;	
	modified=true;
	switch (ev.keyCode)
	{	
		case 9://tabulation
				eval(nomedit).insertHTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");		
				ev.returnValue=false;
				break;
		case 8://backspace
		case 46://suppr
			if (div.id=='edit')
			{	rearange(div);
				ev.cancelBubble=true;
			}
				break;
		case 13:var curP=editor.getParentElement();
			if(curP.tagName.toLowerCase()=="p" && curP.innerHTML=="")
			{	curP.innerHTML="&nbsp;";
			}
			break;
		
	}
	if (ev.ctrlKey && !ev.altKey) {
		var sel = null;
		var range = null;
		
		var key = String.fromCharCode(HTMLArea.is_ie ? ev.keyCode : ev.charCode).toLowerCase();
		var cmd = null;
		var value = null;
		switch (key) {
			// simple key commands follow

		    case 'b': cmd = "bold"; break;
		    case 'i': cmd = "italic"; break;
		    case 'u': cmd = "underline"; break;
		    case 's': cmd = "strikethrough"; break;
		    case 'l': cmd = "justifyleft"; break;
		    case 'e': cmd = "justifycenter"; break;
		    case 'r': cmd = "justifyright"; break;
		    case 'j': cmd = "justifyfull"; break;
		    case 'z': cmd = "undo"; break;
		    case 'y': cmd = "redo"; break;
		    case 'v': paste();HTMLArea._stopEvent(ev);break;
		    case 'n': cmd = "formatblock"; value = HTMLArea.is_ie ? "<p>" : "p"; break;

		    case '0': cmd = "killword"; break;
		}
		
		
	if (cmd) {
			// execute simple command
			editor.execCommand(cmd, false, value);
			HTMLArea._stopEvent(ev);
		}
	}
	
	if(ev.shiftKey || ev.shiftLeft)
	{
		switch(ev.keyCode){
			case 45 :paste();HTMLArea._stopEvent(ev);break;
		}
	}	
	
	if (div.id=='edit')
		performPageBreak(div);	
		
}
  