var age = "max-age="+3600*3600;
String.prototype.replaceAll = function replaceAll(search, replace) { return this.split(search).join(replace); }

var ip = "192.168.1.106";
var ipport = ip+":"+4452

var colorbuttonform = ["#8c3333","#5959a7","#33336c","#5a6875","#36434f"];
//var l = 
let socket = new WebSocket("ws://"+ip+":4446");
var mode = 2;
var typemenugroup = 0;
var type_translate = 0;
var pursemode = 0;
var translate_but = 0;
var finance_name="DUR";
var rashifrovka = "double rouble"
var message_id = 0;
function searchvideo(){
	var text_search = getel("search_input").value;
	getel("search_input").value = "";
	getel("menugroupe").innerHTML="";
	sendMessage("searchvideo;"+text_search);
}
var newsshow = 1;
var mylogin = "";

var myname = "";
var myfamily = "";

var my_id = 0;
var c =Array();
function translatefun(){
    var g = getel("LoginToTrans").value;
    
    var g1 = getel("NumToTrans").value;
    
    if(type_translate==0){
        type_translate=1;
        sendMessage("moneyoperation;translate;"+g+";"+g1);
   //      getel("LoginToTrans").value="";
    getel("NumToTrans").value="";
   
    }
}
var f = document.getElementById("chat_script");
//var d = setInterval(function(){

    document.addEventListener('keydown', function(event){
if(event.keyCode==13){
    if (mode ==1){
        login();

    }else if(mode ==2){
var u = document.getElementById("message_input").value;
if(u==""|| u==null || u==undefined){}else{
document.getElementById("message_input").value="";

u = u.replaceAll("'","&#039;").replaceAll('"',"&quot").replaceAll(">","&gt;").replaceAll("<","&lt;");

console.log('sendmessage;{"idto":'+message_id+',"message":"'+u+'"}');
sendMessage('sendmessage;{"idto":'+message_id+',"message":"'+u+'"}');
}
    }
    //alert(event.keyCode);
}
} );
    function login(){
        var aaaa = document.getElementById("login").value;
        var bbbb = document.getElementById("password").value;
if(aaaa!="" && bbbb!=""){
	
	  // document.cookie="pass="+bbbb+"; "+age;
	
        sendMessage("auth;"+aaaa+";"+bbbb);
}

return false;
    }

function repost(object){
var id = object.id.split(";");
getel(object.id).style="background-color: #50543e;"
setTimeout(function (){
getel(object.id).style=""

},100);
}
var ger = 0;





function news(){

  if(newsshow == 0){
	   $("#purseinfo").slideUp(600)
       pursemode=0;
    
  getel("posts").style="";
var f = getel("chat_script");
f.style = "display:none;";
$("#menuMessage").hide();
var fg = getel("text_in");
fg.style = "display:none;";
getel("chat_info").style="width:100%";
getel("script_chat").style="width:100%;height: calc(100% - 100px);";

// getel("chat_info").style="";
    
var ff = getel("name_contact");
ff.innerHTML=""+myname+" "+myfamily;
//$("#menuMessage").slideDown(300);
//getel("script_chat").style="height:90%;";

//addPost(ger++,"hea","bo","","","")

//addPost(ger++,"hea","bo","","","")
//initvideo(ger++,"videoview_1","30,000","Вечер с Владимиром Соловьёвым",900000,0,1);
newsshow = 1;
}else{
  var f = getel("chat_script");
  $("#menuMessage").show();
f.style = "";
getel("script_chat").style="";
getel("posts").style = "display: none;";

getel("chat_info").style="";
getel("script_chat").style="";

var fg = getel("text_in");
fg.style = "";

getel("menugroupe").style="display:none;";
getel("searchvideopanel").style="display:none;";
 setTimeout(function(){
	 getel("chat_info").style="";
     $("#chat_info").show()
     },700)
var ff = getel("name_contact");
ff.innerHTML=""+myname+" "+myfamily;
$("#menuMessage").slideDown(300);
//getel("script_chat").style="height:90%;";
newsshow = 0;
getel("searchvideopanel").style="display:none;";
 getel("menugroupe").style="display:none;";

}

}
var groupnews = new Array();
//groups;addAll
var typenews  = 0;
function addNewsGroupe(name){
  if(groupnews.indexOf(login)<=-1){
    groupnews.push(login)
  //  $("#menuMessage").html("");
   $("#menugroupe").append("<button class='menus' id ='"+name+"group' onclick='getMessageHistory(this)'>&"+name+"</button>");
}

}

function cleargroupes(){
 $("#menugroupe").html("");
  
}

function getmypurse(){
    initBalancefunction();
    sendMessage("moneyoperation;getBalance");
}
function initBalancefunction(){
    if(mode==2){
        if(pursemode==0){
      $("#chat_info").hide();
        $("#purseinfo").slideDown(600)
        $("#mymoney").html("hide");
        pursemode =1;
    }else{
    	 $("#mymoney").html("purse");
        $("#purseinfo").slideUp(600)
            pursemode=0;
    setTimeout(function(){
        $("#chat_info").show()
        },700)
    }
    }
}
var from_them = '<div id="tidm" class="from-them"><p>mes</p></div><div class="clear"></div>';
var from_me = '<div id="tidm" class="from-me"><p>mes</p></div><div class="clear"></div>';

function addMessageMe(id,text,time){
    $("#sel_chat").append(from_me.replace("t",id).replace("mes",text));
}


function translatefunc(){
   
     if(translate_but==0){
          translate_but = 1;
     $("#translate_form").show(500);
 }else{
      translate_but = 0;
     $("#translate_form").hide(500);

 }
}

function addPost(id,header,body,view,link,photo){
	
	 
	//😂😡🙄😳👍🏻👎🏻
body = '<div id ="videopreview"><video style="border-radius:10px 10px 10px 10px;" width="100%" height ="80%" controls="controls" ><source src = "../videoview_1" type="video/mp4"></video></div><div id="views">'+view+' views</div>';
var post_text ='<div id="post;'+id+'" class="post"><div id="postheader;'+id+'" class="postheader">'+header+'</div><div id="body;'+id+'" class="bodypost">'+body+'<div id="likedislike"><div id="like">like</div><div id="dislike">dislike</div></div></div><div id="postend" class="postend"><div id="repost;'+id+'" onclick="repost(this)" class="repost">repost</div></div></div>';

$("#posts").append(post_text);

}
function initvideo(id,link,view,header,like1,dislike1,type,image){
	var like = "like";
	var dislike = "dislike";
	var cl = "like";
	var cd = "dislike";
	if(type!=0){
		cl = like1;
		cd = dislike1;
	}
	if(type ==1){like =like+"d";dislike = dislike+"u";}else if(type==2){dislike = dislike+"d";like = like+"u";}
	var body = '<div id ="videopreview"><video style="border-radius:10px 10px 10px 10px;" width="100%" height ="80%" controls="controls" ><source src = "'+link+'" type="video/mp4"></video></div><div id="views">'+view+' views</div>';
	var post_text ='<div  id="post;'+id+'" class="post"><div id="postheader;'+id+'" class="postheader">'+header+'</div><div id="body;'+id+'" class="bodypost">'+body+'<div id="likedislike"><div class = '+like+' id="'+id+'">'+cl+'</div><div class = '+dislike+' id="'+id+'">'+cd+'</div></div></div><div id="postend" class="postend"><div id="repost;'+id+'" onclick="repost(this)" class="repost">repost</div></div></div>';
	$("#posts").prepend(post_text);
	
}
function addMessageThem(id,text,time){
    $("#sel_chat").append(from_them.replace("t",id).replace("mes",text));
}
function get_cookie ( cookie_name )
{
  var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
 
  if ( results )
    return ( unescape ( results[2] ) );
  else
    return "";
}

var jsondebug = "";
function getMessageHistory(object){
var id = object.id.split("user");
message_id = id[0];
sendMessage("gethistorymessage;"+id[0])
}






////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



socket.onmessage = function(event) {
    console.log(event.data);
var c = event.data.split(";");
if(c[0]=="auth"){
    if(c[1]=="true"){
    	if(newsshow==0){
    	       
        document.getElementById("wrap-login100").style ="display:none";
                document.getElementById("chat").style ="";
    	}
                mode = 2;
                my_id = Number(c[2]);
                mylogin = c[6];
                    myname = c[7];
    myfamily = c[8];
    document.cookie="db="+c[6]+"; "+age;
    document.cookie="auth="+c[3]+"; "+age;
    document.cookie="authif="+c[4]+"; "+age;
    document.cookie="decode="+c[5]+"; "+age;
    
    
        //alert("all okey");
    }
 // alert(`[message] Данные получены с сервера: ${event.data}`);
}else if(c[0]=="searchvideo"){
	jsondebug = JSON.parse(replacAll(event.data,"searchvideo;",""));
var find = jsondebug.find;
for(var  i = 0;i<find.length;i++){

var g = find[i];
var id = g.id;
var name = g.name;

var channame = g.channame;
var image = g.image;
var element ='<div class="addpost" id="$id" onClick="addlentaVideo($id)"> <img src="$img" style="border-radius:10px 10px 10px 10px;height:100%;min-width:150px;" class="imageforserach" ><div class="textname">$name<br>$titlechanel</div><div>';
var el = element.replaceAll("$id",id).replaceAll("$img",image).replaceAll("$name",name).replaceAll("titlechanel",channame);
$("#menugroupe").append(el); 
}
	console.log(c[1]);
}
else if(c[0]=="authcookie"){
	if(c[1]=="false"){
		//sendMessage("ann")
	//	sendMessage("auth;"+get_cookie("db")+";"+get_cookie("pass"))
	}
}
else if(c[0] =="group"){
   if(c[1]=="add"){
    
  }
}else if(c[0]=="groups") {
  if(c[1]=="addAll"){
    cleargroupes();
    for(var y = 1;y<=c.length;y++){
      addNewsGroupe(c[y])
    }
  }
}else if(c[0]=="addlenta"){
	var ret = event.data.replaceAll("addlenta;","");
	
	var jsoup = JSON.parse(ret);
	var name = jsoup.name;
	var link = jsoup.link;
	var file = jsoup.filename;
	var id = jsoup.id;
	var like = jsoup.like;
	var dislike = jsoup.dislike;
	var view = jsoup.view;
	var image = jsoup.img;
	
	var link1 =jsoup.link1;
	if(link=="NULL")link = link1;
	document.getElementById(id).remove();
	if(link=="NULL")
		initvideo(id,file,view,name,like,dislike,1,image);
	else 
		initvideo(id,link,view,name,like,dislike,1,image);
	
		//initvideo(ger++,"videoview_1","30,000","Вечер с Владимиром Соловьёвым",900000,0,1);
}


else if(c[0]=="dilogid"){
    var json = JSON.parse(c[1]);
    var mas = json['key'];
for(var y = 0;y<mas.length;y++){
    addDilog(mas[y].idtable,mas[y].login)
}
//buttonmenus
document.getElementsByClassName("menus")[0].click();
//$("#translate_form").hide();
//document.getElementsByClassName("buttonmenus")[0].click();

}else if(c[0]=="translate"){
    type_translate = 0;
    if(c[1]=="error"){
            if(c[2]=="operation"){
                //wallet is  temporarily busy,please try again later
alert("wallet is  temporarily busy,please try again later")
            }else if(c[2]=="moneyhaven't"){
//Your account doesn't have enough coin or funds or money
alert("Your account doesn't have enough funds");
            }else if(c[2]=="money"){
alert("amount less than nought")
            }
    }else if(c[1]=="allright"){
                var userTO = c[2];
                var mon = c[3];
                var balance = c[4];
                getel("balancemoney").innerHTML = ""+balance;

    }else if(c[1]=="new"){
            var loginFrom = c[2];
            var mon = c[3];
            var allmoney = c[4];
            getel("balancemoney").innerHTML=""+allmoney;
    }
}
else if(c[0]== "errorfound"){
    alert("Пользователь не найден!")
}else if(c[0]== "addDilogId"){
    var js = JSON.parse(c[1])
    addDilog(js.id,js.login)
   // alert("Пользователь не найден!")
}else if(c[0]=="setbalance"){
    var i = (c[1]);
    getel("balancemoney").innerHTML=""+c[1]+" "+finance_name;
    getel("name").innerHTML=""+c[2];
    getel("surname").innerHTML=""+c[3];

}
else if(c[0] == "history"){
    var id = Number(c[1]);

dd = event.data.split("fDertMertnsdf2sd");
var g = JSON.parse(dd[1]);
$("#sel_chat").html("");
document.getElementById("name_contact").innerHTML=""+c[2];
var sin = g.message;

for(var x = 0;x<sin.length;x++){
    if(sin[x].idfrom==id){
        addMessageThem(sin[x].messageid,sin[x].message,sin[x].time);
    }else{
           addMessageMe(sin[x].messageid,sin[x].message,sin[x].time);
     
    }
}
//f.scrollTop+=100*g.all;
//f.scrollTop=f.scrollHeight;

}else if(c[0]=="newmessage"){
	
    var fdd = event.data.replace("newmessage;","");
    console.log(fdd);
    var h = JSON.parse(fdd);
    if(message_id == h.idto && my_id == h.idfrom){
        addMessageMe(h.messageid,h.message,h.time);
      //  if(f.scrollHeight!=undefined && f.scrollHeight !=null)
        f.scrollTop=f.scrollHeight

    }
    else if(message_id==h.idfrom && my_id==h.idto){
 addMessageThem(h.messageid,h.message,h.time);
 f.scrollTop=f.scrollHeight

    }
}
};
var message_element_by_code = '<div id="idmes" class="messagemy">text</div>' ;
function addDilog(id,login){
if(c.indexOf(login)<=-1){
    c.push(login)
  //  $("#menuMessage").html("");
    $("#menuMessage").append("<button class='menus' id ='"+id+"user' onclick='getMessageHistory(this)'>"+login+"</button>");
}
}
socket.onclose = function(event) {
  if (event.wasClean) {
   // alert(`[close] Соединение закрыто чисто, код=${event.code} причина=${event.reason}`);
  } else {
    // например, сервер убил процесс или сеть недоступна
    // обычно в этом случае event.code 1006
    //alert('[close] Соединение прервано');
  }
};

socket.onerror = function(error) {
  //alert(`[error] ${error.message}`);
};

function sendMessage(mes){
    socket.send(mes);
}
function find(){
    var a = prompt ("Enter search login?")
    if(a!=null && a!=undefined && a!='' &&a.length>=2){
  //  alert(a);
        sendMessage("searchlogin;"+a)
    }
}
(function ($) {
    "use strict";

    
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;
login();
        for(var i=0; i<input.length; i++) {
         //   if(validate(input[i]) == false){
              //  showValidate(input[i]);
             //   check=false;
          //  }
        }

        return false;//check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    

})(jQuery);
var open_close = 0;
function hide(){
	if(newsshow==0){
    if(open_close==0){
    $("#menuMessage").hide(500)
    setTimeout(function(){
   getel("chat_info").style="width:100%";
   getel("script_chat").style="width:100%";
   getel("menubut").innerHTML = "e";
   getel("text_in").style="width:100%";
  getel("posts").style="display: none;";

    },500)

 getel("chat_script").style =  "";
    getel("menugroupe").style="display:none;";
    getel("searchvideopanel").style="display:none;";
 //up-line
   //mailmes
   // getel("text_in").style="width:100%";
  //
    //  $("#mainsearch").hide(500)
 // $("#mailmes").show(500)

   open_close =1;
}else{
   getel("menugroupe").style="display:none;";
   getel("searchvideopanel").style="display:none;";
    getel("chat_info").style="";
       getel("script_chat").style="";
       getel("text_in").style="";
       getel("posts").style="display: none;";

  getel("chat_script").style =  "";

       open_close=0;
 //  $("#mainsearch").show(500)
//$("#mailmes").hide(500)
     $("#menuMessage").show(500)
     setTimeout(function(){
         getel("menubut").innerHTML = "menuMessage";
    
     },800)
 
}
	}else {
	if(typemenugroup==0){
	   typemenugroup = 1;
		getel("menugroupe").style="";
	    getel("searchvideopanel").style="";
		// getel("chat_info").style="width:0%";
		   getel("script_chat").style="width:0%";
		  
	}else{
	   typemenugroup = 0;
		getel("menugroupe").style="display:none;";
	    getel("searchvideopanel").style="display:none;";
		// getel("chat_info").style="width:100%";
		   getel("script_chat").style="width:100%";
		  
	}	
	}
}




socket.onopen = function(e) {
if(get_cookie("db")!=null)
	setTimeout(function(){
		sendMessage("b")
		sendMessage("authcookie;"+get_cookie("db")+";"+get_cookie("auth")+";"+get_cookie("authif")+";"+get_cookie("decode"));

	},300);
	
	
	};


function addlentaVideo(id){
	sendMessage("getvideo;"+id);
	
}
function getel(c){
return document.getElementById(c);
}
window.onerror = function(e, url, line){
	  sendMessage('onerror: ' + e + ' URL:' + url + ' Line:' + line);
	  console.error('\nLine ' + line + ':');
	 //mainly useful in content scripts for extensions, 
	  return true; 
	}
function replacAll(text, strToBeRemoved,to) {
	  return text.replace(new RegExp(strToBeRemoved, 'g'), to);
	}
//$("#mailmes").hide(500)
