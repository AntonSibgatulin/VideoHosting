var massive = ["exit","repost","send","link","show_all_video"];


var age = "max-age="+3600*3600;

String.prototype.replaceAll = function replaceAll(search, replace) { return this.split(search).join(replace); }

//var ip = "192.168.0.105";



//var l =
let socket = new WebSocket("ws://"+ip+":4446");
var mode = 0;


var my_id = 0;
var c =Array();



function login(){
    var aaaa = document.getElementById("login").value;
    var bbbb = document.getElementById("password").value;
    alert(aaaa);
    if(aaaa!="" && bbbb!=""){
        sendMessage("auth;"+aaaa+";"+bbbb);
    }

}

function get_cookie ( cookie_name )
{
    var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );

    if ( results )
        return ( unescape ( results[2] ) );
    else
        return "";
}



socket.onmessage = function(event) {

    console.log(event.data);
    var c = event.data.split(";");
    if(c[0]=="auth"){
        if(c[1]=="true"){

            document.cookie="db="+c[6]+"; "+age;
            document.cookie="auth="+c[3]+"; "+age;
            document.cookie="authif="+c[4]+"; "+age;
            document.cookie="decode="+c[5]+"; "+age;

            socket.close();
            window.location.href="/"
           // alert("You just logged in");
        }else if(c[1]=="false"){
            alert("You not logged in");
        }
    }

};

socket.onclose = function(event) {

    socket = new WebSocket("ws://"+ip+":4446");
    // alert(`[close] Соединение закрыто чисто, код=${event.code} причина=${event.reason}`);

};

socket.onerror = function(error) {
    socket = new WebSocket("ws://"+ip+":4446");
};

function sendMessage(mes){
    socket.send(mes);
}

socket.onopen = function(e) {


};

