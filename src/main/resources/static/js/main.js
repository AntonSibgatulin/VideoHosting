var massive = ["exit", "repost", "send", "link", "show_all_video"];
//$admin//

var age = "max-age=" + 3600 * 3600;
String.prototype.replaceAll = function replaceAll(search, replace) {
    return this.split(search).join(replace);
}
var from_them = '<div id="tidm" class="from-them"><p>mes</p></div><div class="clear"></div>';
var from_me = '<div id="tidm" class="from-me"><p>mes</p></div><div class="clear"></div>';

//var ip = "192.168.0.105";
//var ipport = ip + ":" + 4446

var colorbuttonform = ["#8c3333", "#5959a7", "#33336c", "#5a6875", "#36434f"];
//var l = 
var socket;
var mode = 0;
var typemenugroup = 0;
var type_translate = 0;
var pursemode = 0;
var translate_but = 0;
var finance_name = "RUB";
var rashifrovka = "Russian ruble"
var message_id = 0;
var newsshow = 0;
var mylogin = "";

var myname = "";
var myfamily = "";

var my_id = 0;
var c = Array();

var menuFind = false;


//elements
var search_input, search_input_value, panelSearchElement, inputSearchA, rightPanelSearch, mePanelIcon, iconButtonCancel
    , panelSearch, videoPanel, bottomPanel, chat, menugroupe, videoview, searchvideopanel;
var panelUpLineFirst;
var LentaId = 0;
var interval = null;
var oldScrollY = 0;
var isSearching = false;
var isSearchingIndex = 0;

//json elements so preview in search , right preview
var pagePreviewInSearch, pageVideoView, pageSpinner,pageUnderVideoPreview;

var idLastVideoView = null;
var playerMain = null;
let i = 1;
for (let li of carousel.querySelectorAll('li')) {
    li.style.position = 'relative';
    li.insertAdjacentHTML('beforeend', `<span style="position:absolute;left:0;top:0">${i}</span>`);
    i++;
}

/* конфигурация */
let width = 180; // ширина картинки
let count = 1; // видимое количество изображений

let list = carousel.querySelector('ul');
let listElems = carousel.querySelectorAll('li');

let position = 0; // положение ленты прокрутки

carousel.querySelector('.prev').onclick = function () {
    // сдвиг влево
    /* position += width * count;
     // последнее передвижение влево может быть не на 3, а на 2 или 1 элемент
     position = Math.min(position, 0)
     list.style.marginLeft = position + 'px';

     */
    // getclass("chip-bar-contents")[0].scrollLeft-=50;
    getclass("chip-bar-contents")[0].scrollTo(getclass("chip-bar-contents")[0].scrollLeft - 50, 0)
};

carousel.querySelector('.next').onclick = function () {
    // сдвиг вправо
    /* position -= width * count;
     // последнее передвижение вправо может быть не на 3, а на 2 или 1 элемент
     position = Math.max(position, -width * (listElems.length - count));
     list.style.marginLeft = position + 'px';*/
    // getclass("chip-bar-contents")[0].scrollLeft+=50;
    getclass("chip-bar-contents")[0].scrollTo(getclass("chip-bar-contents")[0].scrollLeft + 50, 0)
};


processDuration = (duration) => moment
    .duration(duration)
    .format('h:mm:ss')
    .padStart(4, '0:0');

async function loadPage(url) {
    var req = await fetch(url);
    return await req.text();
}


function show_all_video(object) {
    var id = object.id.split(";")[1];
    getel("menugroupe").innerHTML = "";
    var jso = '{"videoid":"' + id + '"}';
    sendMessage("getvideochan;" + jso);
    getel("menuforuser" + id).style = "display:none;";

    getel("menubut").click();

}

function unfocus() {

    search_input.disabled = true;
    search_input.disabled = false;
}

function searchvideo() {
    unfocus();
    isSearching = true;
    isSearchingIndex = 0;

    menugroupe.innerHTML = pageSpinner.replaceAll("$id", "$loading");
    videoview.innerHTML = "";

    videoview.style = "display:none";
    var text_search = getel("search_input").value;
//	getel("search_input").value = "";
    menugroupe.style = "";


    searchvideopanel.style = "";

    var gf = '{"search":"' + text_search + '"}';

    sendMessage("searchvideo;" + gf);
    //$("#menugroupe").slideDown(500);
    menuFind = true;
    //menugroupe.innerHTML="";
    //  getel("searchvideopanel").style = "background:#6f6d6a;";
    //  getel("carousel").style = "background:#6f6d6a;";

}

function all(object) {
    menugroupe.innerHTML = pageSpinner;
    search_input.value = "";
    changeOther();
    object.classList.add("click");
}

function allNew(object) {
    menugroupe.innerHTML = "";// pageSpinner;
    LentaId = -1;
    search_input.value = "";
    changeAnother();
    object.parentElement.parentElement.classList.add("selected");

}


function allNews() {
    if (videoview.style.display != "none" && videoview.style.display != "none;") {
        videoview.innerHTML = "";
        hideVideoView();
    } else {


        menugroupe.innerHTML = "";// pageSpinner;
        LentaId = -1;

        changeAnother();
        var buttons = document.getElementsByTagName("ytm-chip-cloud-chip-renderer");
        buttons[0].classList.add("selected");
        // object.parentElement.parentElement.classList.add("selected");
    }


}


function checkButtonRun(object) {
    turnOnSearchPanel();
    search_input.value = object.innerHTML;
    changeAnother();
    //object.parentElement.classList.add("click");
    // object.parentElement.parentElement.setAttribute("parentElement","true")
    object.parentElement.parentElement.classList.add("selected");
    searchvideo();
}

function changeOther() {
    var buttons = getclass("button check");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove("click");
    }
}

function changeAnother() {
    var buttons = document.getElementsByTagName("ytm-chip-cloud-chip-renderer");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i]/*.children[0]*/.classList.remove("selected");
        //buttons[i].setAttribute("aria-selected","false");

    }
}

function isMobile() {
    var isMobile = false; //initiate as false
    // device detection
    if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i
            .test(navigator.userAgent) ||
        /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
            .test(navigator.userAgent.substr(0, 4))) {
        isMobile = true;
    }
    return isMobile;

}


function translatefun() {
    var g = getel("LoginToTrans").value;

    var g1 = getel("NumToTrans").value;

    if (type_translate == 0) {
        type_translate = 1;
        sendMessage("moneyoperation;translate;" + g + ";" + g1);
        //      getel("LoginToTrans").value="";
        getel("NumToTrans").value = "";

    }
}

var chat_script = getel("chat_script");
//var d = setInterval(function(){

document.addEventListener('keydown', function (event) {
    if (event.keyCode == 13) {
        if (mode == 1) {
            login();

        } else if (mode == 2) {
            var u = getel("message_input").value;
            if (u == "" || u == null || u == undefined) {
            } else {
                getel("message_input").value = "";

                u = u.replaceAll("'", "&#039;").replaceAll('"', "&quot").replaceAll(">", "&gt;").replaceAll("<", "&lt;");

                console.log('sendmessage;{"idto":' + message_id + ',"message":"' + u + '"}');
                sendMessage('sendmessage;{"idto":' + message_id + ',"message":"' + u + '"}');
            }
        }
        //alert(event.keyCode);
    }
});

function aler() {
    alert("second");
}


function login() {
    //  alert("second");
    //  alert("second");
    var aaaa = getel("login").value;
    var bbbb = getel("password").value;
    alert(aaaa);
    if (aaaa != "" && bbbb != "") {

        // document.cookie="pass="+bbbb+"; "+age;

        sendMessage("auth;" + aaaa + ";" + bbbb);
    }

//return false;
}

function repost1(object) {
    var id = object.id.split(";");
    getel(object.id).style = "background-color: #50543e;"
    setTimeout(function () {
        getel(object.id).style = ""

    }, 100);
}

var ger = 0;

function showVideo() {
    if (videoview.style.display != "none" && videoview.style.display != "none;") {
        videoview.innerHTML = "";
        hideVideoView();
    }


    if (newsshow == 0) {
        getclass("pivot-bar-item-tab pivot-w2w")[0].setAttribute("aria-selected", true);
        getclass("pivot-bar-item-tab pivot-shorts")[0].setAttribute("aria-selected", false);
        getclass("pivot-bar-item-tab pivot-subs")[0].setAttribute("aria-selected", false);
        getclass("pivot-bar-item-tab pivot-library")[0].setAttribute("aria-selected", false);
            menugroupe.style = "";

            $("#purseinfo").slideUp(600)
            pursemode = 0;

            getel("posts").style = "";
            var f = getel("chat_script");
            f.style = "display:none;";
            $("#menuMessage").hide();
            var fg = getel("text_in");
            fg.style = "display:none;";
            getel("chat_info").style = "width:100%";
            getel("script_chat").style = "width:100%;height: calc(100% - 100px);";


            var ff = getel("name_contact");
            ff.innerHTML = "" + myname + " " + myfamily;

            newsshow = 1;
            $("#buttonVideosOrNews").html("Messager");

            getel("chat_info").style = "display:none;";

            getel("searchvideopanel").style = "";
            getel("carousel").style = "";
            $("#mymoney").html("purse");
            $("#purseinfo").slideUp(600)
            pursemode = 0;
            videoPanel.style = "";
            chat.style = "display:none;";

    }
}

function showMessager() {
    if (newsshow != 0) {


        getclass("pivot-bar-item-tab pivot-w2w")[0].setAttribute("aria-selected", false);
        getclass("pivot-bar-item-tab pivot-shorts")[0].setAttribute("aria-selected", true);
        getclass("pivot-bar-item-tab pivot-subs")[0].setAttribute("aria-selected", false);
        getclass("pivot-bar-item-tab pivot-library")[0].setAttribute("aria-selected", false);


        $("#mymoney").html("purse");
        $("#purseinfo").slideUp(600)
        pursemode = 0;

        var f = getel("chat_script");
        $("#menuMessage").hide();
        f.style = "";
        getel("script_chat").style = "";

        getel("chat_info").style = "";
        getel("script_chat").style = "";

        var fg = getel("text_in");
        fg.style = "";
        /*
        getel("menugroupe").style = "display:none;";
        getel("searchvideopanel").style = "display:none;";
        getel("posts").style = "display: none;";
        */
        //setTimeout(function(){
        getel("chat_info").style = "";
        $("#chat_info").show()
        // },700)
        var ff = getel("name_contact");
        ff.innerHTML = "" + myname + " " + myfamily;
//$("#menuMessage").slideDown(300);
//getel("script_chat").style="height:90%;";
        newsshow = 0;
        getel("searchvideopanel").style = "display:none;";
        getel("menugroupe").style = "display:none;";

        $("#buttonVideosOrNews").html("Videos");
        getel("searchvideopanel").style = "display:none;";


        getel("chat_info").style = "width:100%;";

        videoPanel.style = "display:none;";
        chat.style = "";

    }
}

function videoOrmessager() {

    if (newsshow == 0 && true == false) {
        $("#purseinfo").slideUp(600)
        pursemode = 0;

        getel("posts").style = "";
        var f = getel("chat_script");
        f.style = "display:none;";
        $("#menuMessage").hide();
        var fg = getel("text_in");
        fg.style = "display:none;";
        getel("chat_info").style = "width:100%";
        getel("script_chat").style = "width:100%;height: calc(100% - 100px);";


        var ff = getel("name_contact");
        ff.innerHTML = "" + myname + " " + myfamily;

        newsshow = 1;
        $("#buttonVideosOrNews").html("Messager");

        getel("chat_info").style = "display:none;";

        getel("searchvideopanel").style = "";
        getel("carousel").style = "";
        $("#mymoney").html("purse");
        $("#purseinfo").slideUp(600)
        pursemode = 0;
        videoPanel.style = "";
        chat.style = "display:none;";
    } else {
        $("#mymoney").html("purse");
        $("#purseinfo").slideUp(600)
        pursemode = 0;

        var f = getel("chat_script");
        $("#menuMessage").hide();
        f.style = "";
        getel("script_chat").style = "";

        getel("chat_info").style = "";
        getel("script_chat").style = "";

        var fg = getel("text_in");
        fg.style = "";
        /*
        getel("menugroupe").style = "display:none;";
        getel("searchvideopanel").style = "display:none;";
        getel("posts").style = "display: none;";
        */
        //setTimeout(function(){
        getel("chat_info").style = "";
        $("#chat_info").show()
        // },700)
        var ff = getel("name_contact");
        ff.innerHTML = "" + myname + " " + myfamily;
//$("#menuMessage").slideDown(300);
//getel("script_chat").style="height:90%;";
        newsshow = 0;
        getel("searchvideopanel").style = "display:none;";
        getel("menugroupe").style = "display:none;";

        $("#buttonVideosOrNews").html("Videos");
        getel("searchvideopanel").style = "display:none;";


        getel("chat_info").style = "width:100%;";

        videoPanel.style = "display:none;";
        chat.style = "";

    }

}

var groupnews = new Array();
//groups;addAll
var typenews = 0;

function addNewsGroupe(name) {
    if (groupnews.indexOf(login) <= -1) {
        groupnews.push(login)
        //  $("#menuMessage").html("");
        $("#menugroupe").append("<button class='menus' id ='" + name + "group' onclick='getMessageHistory(this)'>&" + name + "</button>");
    }

}

function cleargroupes() {
    $("#menugroupe").html("");

}

function getmypurse() {
    initBalancefunction();
    sendMessage("moneyoperation;getBalance");
    getel("menugroupe").style = "display:none;";
    getel("searchvideopanel").style = "display:none;";
}

function initBalancefunction() {
    if (mode == 2) {
        if (pursemode == 0) {
            $("#chat_info").hide();
            $("#purseinfo").slideDown(600)
            $("#mymoney").html("hide");
            pursemode = 1;
        } else {
            $("#mymoney").html("purse");
            $("#purseinfo").slideUp(600)
            pursemode = 0;
            setTimeout(function () {
                $("#chat_info").show()
            }, 700)
        }
    }
}

function addMessageMe(id, text, time) {
    $("#sel_chat").append(from_me.replace("t", id).replace("mes", text));
}


function translatefunc() {

    if (translate_but == 0) {
        translate_but = 1;
        $("#translate_form").show(500);
    } else {
        translate_but = 0;
        $("#translate_form").hide(500);

    }
}

function addPost(id, header, body, view, link, photo) {


    //😂😡🙄😳👍🏻👎🏻
    body = '<div id ="videopreview"><video style="border-radius:10px 10px 10px 10px;" width="100%" height ="80%" controls="controls" ><source src = "../videoview_1" type="video/mp4"></video></div><div id="postheader;' + id + '" class="postheader">' + header + '</div><div id="views">' + view + ' views</div>';
    var post_text = '<div id="post;' + id + '" class="post"><div id="body;' + id + '" class="bodypost">' + body + '<div id="likedislike"><div id="like">like</div><div id="dislike">dislike</div></div></div><div id="postend" class="postend"><div id="repost;' + id + '" onclick="repost(this)" class="repost">repost</div></div></div>';

    $("#posts").append(post_text);

}

function initvideo(id, link, view, header, like1, dislike1, type, image, youtube, f, chanel) {
    var like = "like";
    var dislike = "dislike";
    var cl = "like";
    var cd = "dislike";
    var allaction = like1 + dislike1;
    var wid1 = 0;
    var wid2 = 0;
    if (allaction != 0) {
        console.log(allaction);
        wid1 = like1 * 100 / allaction;
        wid2 = dislike1 * 100 / allaction;
        console.log(wid1);
        console.log(wid2);
    }
    if (wid1 < 20) wid1 = 40;
    if (wid2 < 20) wid2 = 40;
    var clickL = 'onClick="setlike(this)"';
    var clickD = 'onClick="setdislike(this)"';

    if (type != 0) {
        clickL = "";
        clickD = "";

        cl = like1;
        cd = dislike1;
    }
    if (type == 1) {
        like = like + "d";
        dislike = dislike + "u";
    } else if (type == 2) {
        dislike = dislike + "d";
        like = like + "u";
    }
    var body = null;
    var viewElement = null;
    //viewElement='<div id="views">  <ytd-video-owner-renderer watch-metadata-refresh="" class="style-scope ytd-watch-metadata" modern-metapanel="" style=" display: flex; flex-direction: row; "><!--css-build:shady--><a class="yt-simple-endpoint style-scope ytd-video-owner-renderer" tabindex="-1" href="/channel/uchevscwm-my8ha19tjqco3a" style=" display: block; margin-left: var(--yt-img-margin-left,auto); margin-right: var(--yt-img-margin-right,auto); max-height: var(--yt-img-max-height,none); max-width: var(--yt-img-max-width,100%); border-radius: 25px; "><yt-img-shadow id="avatar" class="style-scope ytd-video-owner-renderer no-transition" style="background-color: transparent;" loaded=""><!--css-build:shady--><img id="img" draggable="false" class="style-scope yt-img-shadow" alt="" width="40" src="data:image/gif;base64,ivborw0kggoaaaansuheugaaaaeaaaabcaqaaac1hawcaaaac0leqvr42mn8xa8aaksbzg7lphyaaaaasuvork5cyii=" style=" border-radius: 25px; "></yt-img-shadow></a><div id="upload-info" class="style-scope ytd-video-owner-renderer" style=" margin-left: 5px; "><ytd-channel-name id="channel-name" class="style-scope ytd-video-owner-renderer" style=" display: flex; flex-direction: row; "><!--css-build:shady--><div id="container" class="style-scope ytd-channel-name" style=" display: inline-block; overflow: hidden; max-width: 100%; ">  <tp-yt-paper-tooltip fit-to-visible-bounds="" class="style-scope ytd-channel-name" role="tooltip" tabindex="-1" style="inset: -11.5px auto auto 46.7422px;"><!--css-build:shady--><div id="tooltip" class="style-scope tp-yt-paper-tooltip hidden" style-target="tooltip">  carrapicho – тема  </div> </tp-yt-paper-tooltip> </div> <ytd-badge-supported-renderer class="style-scope ytd-channel-name" disable-upgrade="" hidden=""> </ytd-badge-supported-renderer> </ytd-channel-name><yt-formatted-string id="owner-sub-count" class="style-scope ytd-video-owner-renderer" aria-label="6,35 тысячи подписчиков">6,35&nbsp;тыс. подписчиков</yt-formatted-string></div><div id="purchase-button" class="style-scope ytd-video-owner-renderer" hidden=""></div><div id="sponsor-button" class="style-scope ytd-video-owner-renderer"></div><div id="analytics-button" class="style-scope ytd-video-owner-renderer"></div></ytd-video-owner-renderer> <div class="view" style=" margin-left: 10px; display: flex; align-items: center; justify-content: center; "><a class="button" style=" padding: 10px 30px; background-color: #f2f2f2; border-radius: 25px; color: black; border: 1px solid; ">6,8 billion views</a>  <a class="button" style=" margin-left: 10px; padding: 10px 30px; background-color: #1c1e21; border-radius: 25px; color: white; border: 1px solid; ">subscribe</a></div> </div>';
    viewElement = '<div id="views" >  <ytd-video-owner-renderer watch-metadata-refresh="" class="style-scope ytd-watch-metadata" modern-metapanel="" style=" display: flex; flex-direction: row; "><!--css-build:shady--><a class="yt-simple-endpoint style-scope ytd-video-owner-renderer" tabindex="-1" href="/channel/uchevscwm-my8ha19tjqco3a" style=" display: block;  max-height: var(--yt-img-max-height,none); max-width: var(--yt-img-max-width,100%); border-radius: 25px; "><yt-img-shadow id="avatar" class="style-scope ytd-video-owner-renderer no-transition" style="background-color: transparent;" loaded=""><!--css-build:shady--><img id="img" draggable="false" class="style-scope yt-img-shadow" alt="" width="40" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mN8XA8AAksBZG7LpHYAAAAASUVORK5CYII=" style=" border-radius: 25px; "></yt-img-shadow></a><div id="upload-info" class="style-scope ytd-video-owner-renderer" style=" margin-left: 5px; "><ytd-channel-name id="channel-name" class="style-scope ytd-video-owner-renderer" style=" display: flex; flex-direction: row; "><!--css-build:shady--><div id="container" class="style-scope ytd-channel-name" style=" display: inline-block; overflow: hidden; max-width: 100%; ">  <tp-yt-paper-tooltip fit-to-visible-bounds="" class="style-scope ytd-channel-name" role="tooltip" tabindex="-1" style="inset: -11.5px auto auto 46.7422px;"><!--css-build:shady--><div id="tooltip" class="style-scope tp-yt-paper-tooltip hidden" style-target="tooltip">' + chanel + '</div> </tp-yt-paper-tooltip> </div> <ytd-badge-supported-renderer class="style-scope ytd-channel-name" disable-upgrade="" hidden=""> </ytd-badge-supported-renderer> </ytd-channel-name><yt-formatted-string id="owner-sub-count" class="style-scope ytd-video-owner-renderer" aria-label="6,35 тысячи подписчиков">0 подписчиков</yt-formatted-string></div><div id="purchase-button" class="style-scope ytd-video-owner-renderer" hidden=""></div><div id="sponsor-button" class="style-scope ytd-video-owner-renderer"></div><div id="analytics-button" class="style-scope ytd-video-owner-renderer"></div></ytd-video-owner-renderer> <div class="view" style=" margin-left: 10px; display: flex; align-items: center; justify-content: center; "><a class="buttons" style=" padding: 10px 30px; background-color: #f2f2f2; border-radius: 25px; color: black; border: 1px solid; ">' + view + ' views</a>  <a class="buttons" style=" margin-left: 10px; padding: 10px 30px; background-color: #1c1e21; border-radius: 25px; color: white; border: 1px solid; ">subscribe</a></div> </div>';

    if (youtube == false) {

        body = '<div id ="videopreview"><video style="border-radius:10px 10px 10px 10px;max-height: 350px;border: 3px solid;" width="100%" height ="80%" controls="controls" ><source src = "' + link + '" type="video/mp4"></video></div><div id="postheader;' + id + '" class="postheader"><div style="margin-left: 10px;margin-right: 10px;">' + header + '</div><div class="menushka" id="menushka;' + id + '"></div></div>' + viewElement;//+'<div id="views">'+view+' views</div>';
    } else {
        body = '<iframe width="100%" height="80%" src="https://www.youtube-nocookie.com/embed/' + link + '" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>';
    }
    if (f != 0) body = "Видео было заблокированно за нарушение правил";

    var post_text = '<div  id="post;' + id + '" class="post"><div id="body;' + id + '" class="bodypost">' + body + '<div class="classLikeOrDislike"><div style="display: contents;" class="MarginTop10px" id="likedislike;' + id + '"><div class = "' + like + '" style="margin-top:5px;" id="like;' + id + ';" ' + clickL + ' >' + cl + '</div><div class = "' + dislike + '" style="" id="dislike;' + id + '" ' + clickD + '>' + cd + '</div></div></div></div><div id="postend" class="postend"><div id="repost;' + id + '" onclick="repost1(this)" class="repost">repost</div><div id="info;' + id + '" onclick="getinformenu(this)" style="margin-left:10px;" class="repost">info</div><div class="menuforuser" id="menuforuser' + id + '" style = "display:none"></div></div></div>';
    $("#posts").prepend(post_text);
    for (var g = 0; g < massive.length; g++) {
        addMenuForVideoPost(id, g);

    }
}

function setlikeposition(id, type, like1, dislike1) {


    var like = "like";
    var dislike = "dislike";
    var cl = "like";
    var cd = "dislike";
    cl = like1;
    cd = dislike1;

    if (type == 1) {
        like = like + "d";
        dislike = dislike + "u";
    } else if (type == 2) {
        dislike = dislike + "d";
        like = like + "u";
    }
    var allaction = like1 + dislike1;
    var wid1 = 0;
    var wid2 = 0;
    if (allaction != 0) {
        console.log(allaction);
        wid1 = like1 * 100 / allaction;
        wid2 = dislike1 * 100 / allaction;
        console.log(wid1);
        console.log(wid2);
    }
    if (wid1 < 20) wid1 = 40;
    if (wid2 < 20) wid2 = 40;
    var clickL = 'onClick="setlike(this)"';
    var clickD = 'onClick="setdislike(this)"';


    var body = '<div class = "' + like + '" style="width: ' + wid1 + '%;margin-top:5px;" id="like;' + id + '" ' + clickL + ' >' + cl + '</div><div class = "' + dislike + '" style="width:' + wid2 + '%;" id="dislike;' + id + ';" ' + clickD + '>' + cd + '</div>';

    getel("likedislike;" + id).innerHTML = body;
}

function getinformenu(object) {
    var id = object.id.split(";")[1];
    getel("menuforuser" + id).style = "";

}

function addMenuForVideoPost(id, position) {

    var element = '<div id="' + massive[position] + ';' + id + '" class="elementmenuforuser" onClick="' + massive[position] + '(this)">' + massive[position] + '</div>';
    $("#menuforuser" + id).append(element);
}

function exit(object) {
    var id = object.id.split(";")[1];
    setTimeout(function () {
        getel("menuforuser" + id).style = "display:none;";
    }, 250);

}

function setlike(object) {
    sendMessage("like_video;" + object.id);

}

function setdislike(object) {
    sendMessage("dislike_video;" + object.id);
}

function getinfopost(object) {


}

function addMessageThem(id, text, time) {
    $("#sel_chat").append(from_them.replace("t", id).replace("mes", text));
}

function get_cookie(cookie_name) {
    var results = document.cookie.match('(^|;) ?' + cookie_name + '=([^;]*)(;|$)');

    if (results)
        return (unescape(results[2]));
    else
        return "";
}

var jsondebug = "";

function getMessageHistory(object) {
    var id = object.id.split("user");
    message_id = id[0];
    sendMessage("gethistorymessage;" + id[0])
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var message_element_by_code = '<div id="idmes" class="messagemy">text</div>';

function addDilog(id, login) {
    if (c.indexOf(login) <= -1) {
        c.push(login)
        //  $("#menuMessage").html("");
        $("#menuMessage").append("<button class='menus' id ='" + id + "user' onclick='getMessageHistory(this)'>" + login + "</button>");
    }
}



function sendMessage(mes) {
    socket.send(mes);
}

function find() {
    var a = prompt("Enter search login?")
    if (a != null && a != undefined && a != '' && a.length >= 2) {
        //  alert(a);
        sendMessage("searchlogin;" + a)
    }
}

/*(function ($) {
    "use strict";

    
    /*==================================================================
    [ Validate ]*/
/*  var input = $('.validate-input .input100');

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
*/

/*  $('.validate-form .input100').each(function(){
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
*/
var open_close = 0;

function hide() {
    var configWidth = isMobile() == true ? 70 : 30;
    if (window.innerWidth > 720) {

        configWidth = 30;
    } else {

        if (window.innerWidth < 720) {

            configWidth = 70;
        }
    }
    if (newsshow == 0) {
        if (open_close == 0) {
            if (isMobile()) {
                $("#menuMessage").show(500)
            } else {
                $("#menuMessage").show(1)
            }
            if (isMobile()) {
                getel("menuMessage").style = "width:" + configWidth + "%";
            }
            // setTimeout(function(){
            getel("chat_info").style = "left:" + configWidth + "%";
            getel("script_chat").style = "left:" + configWidth + "%";
            //getel("menubut").innerHTML = "e";
            getel("text_in").style = "left:" + configWidth + "%";
            getel("posts").style = "display: none;";

            // },500)

            getel("chat_script").style = "";
            getel("menugroupe").style = "display:none;";
            getel("searchvideopanel").style = "display:none;";
            //up-line
            //mailmes
            // getel("text_in").style="width:100%";
            //
            //  $("#mainsearch").hide(500)
            // $("#mailmes").show(500)

            open_close = 1;
        } else {
            getel("menugroupe").style = "display:none;";
            getel("searchvideopanel").style = "display:none;";
            getel("chat_info").style = "";
            getel("script_chat").style = "";
            getel("text_in").style = "";
            getel("posts").style = "display: none;";

            getel("chat_script").style = "";

            open_close = 0;
            //  $("#mainsearch").show(500)
//$("#mailmes").hide(500)
            if (isMobile()) {
                $("#menuMessage").hide(500)
            } else {
                $("#menuMessage").hide(1)
            }
            // setTimeout(function(){
            // getel("menubut").innerHTML = "menuMessage";

            // },800)

        }
    } else {
        if (typemenugroup == 0) {
            typemenugroup = 1;
            getel("menugroupe").style = "";
            // getel("searchvideopanel").style="";
            // getel("chat_info").style="width:0%";
            getel("script_chat").style = "width:0%";

        } else {
            typemenugroup = 0;
            getel("menugroupe").style = "display:none;";
            //   getel("searchvideopanel").style="display:none;";
            // getel("chat_info").style="width:100%";
            getel("script_chat").style = "width:100%";

        }
    }
}


function menuOpenOrClose() {
    if (menuFind) {

        $("#menugroupe").slideUp(500);
        setTimeout(function () {
            getel("searchvideopanel").style = "";
            getel("carousel").style = "";
        }, 500);

    } else {
        $("#menugroupe").slideDown(500);
        getel("searchvideopanel").style = "background:#6f6d6a;";
        getel("carousel").style = "background:#6f6d6a;";

    }
    menuFind = !menuFind;
}


function addlentaVideo(id) {
    sendMessage("getvideo;" + id);
    closeMenu();
}

function closeMenu() {
    $("#menugroupe").slideUp(500);
    menuFind = false;
    getel("searchvideopanel").style = "";
    getel("carousel").style = "";

}

function getel(c) {
    return document.getElementById(c);
}

function getclass(c) {
    return document.getElementsByClassName(c)
}

function init() {
    initSocket();
    menugroupe = getel("menugroupe");
    search_input = getel("search_input");
    bottomPanel = getel("bottomPanel")
    videoPanel = getel("videoPanel");
    chat = getel("chat");
    inputSearchA = getclass("input-search-a")[0];
    rightPanelSearch = getclass("right-panel-search")[0];
    mePanelIcon = getclass("me-panel-icon")[2];
    iconButtonCancel = getclass("icon-button")[0];
    panelSearch = getel("panel-search");
    videoview = getel("videoview");
    searchvideopanel = getel("searchvideopanel")
    panelSearchElement = getel("search-panel-elements");
    panelUpLineFirst = getclass("up-line")[0]

    search_input.oninput = function () {
        search_input_value = search_input.value;
        if (search_input_value.length > 0) {
            initSearchPanelElements();
        }
    }

    search_input.addEventListener("keydown", function (event) {
        var keyCode = event.keyCode;
        if (keyCode == 13) {
            searchvideo();
        }


    });
    interval = setInterval(function () {
        if (socket.readyState == 3) {
            //socket = new WebSocket("ws://" + ip + ":4446");
            initSocket();
        }
        if (isSearching == false && menugroupe.style.display != "none" && menugroupe.style.display != "none;" && menugroupe.style.display != "none" && menugroupe.style.display != "none;" && videoPanel.style.display != "none;" && videoPanel.style.display != "none") {
            if ((menugroupe.scrollHeight - menugroupe.offsetHeight - oldScrollY) <= menugroupe.offsetHeight) {

                LentaId += 1;
                //console.log("lenta " + LentaId)
                sendMessage("lenta;" + LentaId);

            }
        }


        if (isSearching == false && getel("menugroupe_undervideo") != null) {
            var menugroupe_undervideo = getel("menugroupe_undervideo");
            if (isSearching == false && videoPanel.innerHTML.length > 0 && videoPanel.style.display != "none;" && videoPanel.style.display != "none") {
                if (window.innerWidth > 931) {
                    if ((menugroupe_undervideo.scrollHeight - menugroupe_undervideo.offsetHeight - menugroupe_undervideo.scrollTop) <= menugroupe_undervideo.offsetHeight) {

                        LentaId += 1;
                        sendMessage("lenta;" + LentaId);
                        //console.log("lenta " + LentaId)

                    }
                }else{

                    if ((menugroupe_undervideo.scrollHeight /* - menugroupe_undervideo.offsetHeight */ - oldScrollY) <= innerHeight) {

                        LentaId += 1;
                        //console.log("lenta " + LentaId)
                        sendMessage("lenta;" + LentaId);

                    }

                }
            }
        }
        if (videoview.style != "display:none") {

        }


    }, 500)

}

function hideSearchVideoPanel() {
    searchvideopanel.style = "display:none";

}


function showSearchVideoPanel() {
    searchvideopanel.style = "";

}


function showVideoView(id) {
    backSearchPanel()
    sendMessage("getvideo;" + id);
    menugroupe.style = "display:none";
    videoview.style = ""
    searchvideopanel.style = "display:none"
    videoview.innerHTML = pageSpinner;
    idLastVideoView = id;

}

function hideVideoView() {
    menugroupe.style = "";
    videoview.style = "display:none"
    searchvideopanel.style = ""
}


function initSearchPanelElements() {

    if (iconButtonCancel.style != "") {
        //panelSearchElement.style = "";
        iconButtonCancel.style = "";
        rightPanelSearch.classList.add("right-panel-search-non-empty");
        inputSearchA.classList.add("input-search-a-non-empty");
        mePanelIcon.style = "display:none;";
    } else {


    }

}

function deactivatePanelElements() {
    iconButtonCancel.style = "display:none;";
    rightPanelSearch.classList.remove("right-panel-search-non-empty");
    inputSearchA.classList.remove("input-search-a-non-empty");
    mePanelIcon.style = "";
}

function deactivatePanelElementsWithTextInInput(text) {
    iconButtonCancel.style = "display:none;";
    rightPanelSearch.classList.remove("right-panel-search-non-empty");
    inputSearchA.classList.remove("input-search-a-non-empty");
    mePanelIcon.style = "";
    //add text in search
    search_input.value = text;
}

function clearSearch() {
    search_input.value = "";
    deactivatePanelElements();
}

function focusOnSearchInput() {
    if (search_input.value.length == 0) {
        search_input.focus();
    } else {

        searchvideo();
    }

}

//$("#mailmes").hide(500)
function backSearchPanel() {
    if (document.querySelector(':focus') == search_input) {
        deactivatePanelElements();
    } else {
        deactivatePanelElementsWithTextInInput("");
        turnOffSearchPanel();
    }

}

function turnOffSearchPanel() {
    panelSearch.style = "display:none";
}

function turnOnSearchPanel() {
    panelSearch.style = "";
    search_input.focus();
}

function toMessager() {
    changeLoc("/messager", "Messager")

}

function changeLoc(url, title) {

    document.title = title;
    history.pushState(null, null, url);

}


window.onscroll = function (event) {
    var scrolled = window.pageYOffset || document.documentElement.scrollTop;
    var dY = scrolled - oldScrollY;
    if (getel("menugroupe_undervideo") == null) {
        if (oldScrollY > 150) {
            console.log(oldScrollY)
            if (dY > 0) {
                panelUpLineFirst.className = "up-line animate-of-top top-hide-animation";
                searchvideopanel.className = "searchvideopanel animate-of-top top-hide-animation";

            } else {
                panelUpLineFirst.className = "up-line animate-of-top top-show-animation";
                searchvideopanel.className = "searchvideopanel animate-of-top top-show-animation-searchpanel";
            }
        }
    }

    oldScrollY = scrolled;
}

function PlayerjsEvents(event, id, info) {
    console.log(event,id);
    if(event=="loaderror"){
        showVideoView(idLastVideoView);
    }

}


function initSocket() {
    socket = new WebSocket("ws://" + ip + ":4446");




    socket.onmessage = async function (event) {
//alert(event.data)
        console.log(event.data);
        var c = event.data.split(";");
        if (c[0] == "auth") {
            if (c[1] == "true") {


                if (getel("wrap-login100") != null) {
                    getel("wrap-login100").style = "display:none";
                }


                //getel("chat").style = "";

                mode = 2;

                my_id = Number(c[2]);
                mylogin = c[6];
                myname = c[7];
                myfamily = c[8];
                document.cookie = "db=" + c[6] + "; " + age;
                document.cookie = "auth=" + c[3] + "; " + age;
                document.cookie = "authif=" + c[4] + "; " + age;
                document.cookie = "decode=" + c[5] + "; " + age;


                //alert("all okey");
            }
            // alert(`[message] Данные получены с сервера: ${event.data}`);
        } else if (c[0] == "searchvideo") {
            /* if(typeof String.prototype.replaceAll === "undefined") {
                String.prototype.replaceAll = function(match, replace) {
                   return this.replace(new RegExp(match, 'g'), () => replace);
                }
            }

            String.prototype.replaceAll = function (target, payload) {
            let regex = new RegExp(target, 'g')
             return this.valueOf().replace(regex, payload)

             };

             */
            //menugroupe.style="";
            try {
//alert(String.prototype.replaceAll)
                var data = String(event.data).replaceAll('searchvideo;', '');
                jsondebug = JSON.parse(data);
            } catch (e) {
                alert(e);
                sendMessage("error;" + e)
            }
//	alert("search video is started");
            var find = jsondebug.find;
            for (var i = 0; i < find.length; i++) {

                var g = find[i];
                var id = g.id;
                var name = g.name;
                var view = formatNumber(g.view);
                var timeofpublic = formatDate(g.timeofpublic);

                var videoDuration = processDuration(g.duration);//g.duration.replaceAll("PT", "").replaceAll("M", ":").replaceAll("S", "").replaceAll("H", ":");
                var channame = g.channame;
                var image = g.image;
//alert(id);
                //var element = '<div class="addpost" id="$id" onClick="addlentaVideo($id)"> <div style=" min-width: 210px; height: calc(100%); background: url($img); background-repeat-x: no-repeat; background-repeat-y: no-repeat; background-size: cover; background-position-y: 50%; border-radius: 10px; max-width: 280px; width: 20%; "></div><div class="textname">$name<br>$titlechanel</div><div>';
                //var el = element.replaceAll("$id", id).replaceAll("$img", image).replaceAll("$name", name).replaceAll("titlechanel", channame);
                var element = pagePreviewInSearch;
                var el = String(element).replaceAll("$id", id).replaceAll("$img", image).replaceAll("$name", name).replaceAll("titlechanel", channame).replaceAll("VideoDuration", videoDuration).replaceAll("CountOfView",view+"").replaceAll("Time",timeofpublic);
                console.log("Element: " + i);
                if (menugroupe.innerHTML == pageSpinner) {
                    menugroupe.innerHTML = "";
                }
                $("#menugroupe").append(el);
                if (isSearching) {
                    if (getel("$loading") != null) {
                        getel("$loading").remove();
                    }
                    isSearchingIndex++;
                    if (isSearchingIndex >= 15) {
                        isSearching = false;
                    }
                } else {
                    if (getel("menugroupe_undervideo") != null) {
                        var element = pageUnderVideoPreview;
                        var el = String(element).replaceAll("$id", id).replaceAll("$img", image).replaceAll("$name", name).replaceAll("$channel", channame).replaceAll("VideoDuration", videoDuration).replaceAll("CountOfView",view+"").replaceAll("Time",timeofpublic);

                        $("#menugroupe_undervideo_lazy").append(el);
                        // getel("menugroupe_undervideo").innerHTML = menugroupe.innerHTML;
                    }
                }
            }
            //alert(c[1]);
        } else if (c[0] == "authcookie") {
            if (c[1] == "false") {
                //sendMessage("ann")
                //	sendMessage("auth;"+get_cookie("db")+";"+get_cookie("pass"))
            }
        } else if (c[0] == "group") {
            if (c[1] == "add") {

            }
        } else if (c[0] == "groups") {
            if (c[1] == "addAll") {
                cleargroupes();
                for (var y = 1; y <= c.length; y++) {
                    addNewsGroupe(c[y])
                }
            }
        } else if (c[0] == "setlike") {

            setlikeposition(c[4], Number(c[1]), Number(c[2]), Number(c[3]));

        } else if (c[0] == "addlenta") {
            var ret = event.data.replaceAll("addlenta;", "");

            var jsoup = JSON.parse(ret);
            console.log(jsoup)
            var name = jsoup.name;
            var link = jsoup.link;
            var file = jsoup.filename;
            var id = jsoup.id;
            var timeofpublic = formatDate(jsoup.timeofpublic);
            var countOfSubscribed = jsoup.countOfSubscribed;
            if(countOfSubscribed=="None"){
                countOfSubscribed="";
            }else{
                countOfSubscribed=formatNumber(countOfSubscribed);
            }
            var countOfComment = jsoup.countOfComment;
            var like = formatNumber(jsoup.like);
            var dislike = formatNumber(jsoup.dislike);
            var view = formatNumber(jsoup.view);
            var image = jsoup.img;
            var ids = jsoup.youtube_link;
            var typelike = jsoup.typelike;
            var link1 = jsoup.link1;
            var chanel = jsoup.chanel;
            var f = jsoup.ban;
            if (getel(id) != null)
                getel(id).remove();

            if (file.length > 0) {
                //initvideo(id, file, view, name, like, dislike, typelike, image, false, f, chanel);
                // return;
            }

            if (link == "NULL") {
                link = link1;
            }
            ;
            if (file.length > 0) {
                link = file;
                var url = "" + file;
                let blob = await fetch(url).then(r => r.blob());
                var urls = URL.createObjectURL(blob)
                link = urls;
            }
            videoview.innerHTML = pageVideoView.replaceAll("$img", image).replaceAll("$name", name).replaceAll("$countOfView", view).replaceAll("$countOfLike", like).replaceAll("channelname", chanel)
                .replaceAll("$longago",timeofpublic)
                .replaceAll("$countOfSubscribers",countOfSubscribed)
                .replaceAll("$countOfComment",countOfComment);




            getel("menugroupe_undervideo_lazy").innerHTML = "";//menugroupe.innerHTML;
            //videoplayer
             playerMain = new Playerjs({id: "videoplayer", file: "" + link, poster: image});
            /*
            if (link == "NULL") {
                initvideo(id, file, view, name, like, dislike, typelike, image, false, f, chanel);
            } else {
                initvideo(id, link, view, name, like, dislike, typelike, image, false, f, chanel);

            }
            */


            //initvideo(ger++,"videoview_1","30,000","Вечер с Владимиром Соловьёвым",900000,0,1);
        } else if (c[0] == "dilogid") {
            var json = JSON.parse(c[1]);
            var mas = json['key'];
            for (var y = 0; y < mas.length; y++) {
                addDilog(mas[y].idtable, mas[y].login)
            }
//buttonmenus
            document.getElementsByClassName("menus")[0].click();
//$("#translate_form").hide();
//document.getElementsByClassName("buttonmenus")[0].click();

        } else if (c[0] == "translate") {
            type_translate = 0;
            if (c[1] == "error") {
                if (c[2] == "operation") {
                    //wallet is  temporarily busy,please try again later
                    alert("wallet is  temporarily busy,please try again later")
                } else if (c[2] == "moneyhaven't") {
//Your account doesn't have enough coin or funds or money
                    alert("Your account doesn't have enough funds");
                } else if (c[2] == "money") {
                    alert("amount less than nought")
                }
            } else if (c[1] == "allright") {
                var userTO = c[2];
                var mon = c[3];
                var balance = c[4];
                getel("balancemoney").innerHTML = "" + balance;

            } else if (c[1] == "new") {
                var loginFrom = c[2];
                var mon = c[3];
                var allmoney = c[4];
                getel("balancemoney").innerHTML = "" + allmoney;
            }
        } else if (c[0] == "errorfound") {
            alert("Пользователь не найден!")
        } else if (c[0] == "addDilogId") {
            var js = JSON.parse(c[1])
            addDilog(js.id, js.login)
            // alert("Пользователь не найден!")
        } else if (c[0] == "setbalance") {
            var i = (c[1]);
            getel("balancemoney").innerHTML = "" + c[1] + " " + finance_name;
            getel("name").innerHTML = "" + c[2];
            getel("surname").innerHTML = "" + c[3];

        } else if (c[0] == "history") {
            var id = Number(c[1]);

            dd = event.data.split("fDertMertnsdf2sd");
            var g = JSON.parse(dd[1]);
            $("#sel_chat").html("");
            getel("name_contact").innerHTML = "" + c[2];
            var sin = g.message;

            for (var x = 0; x < sin.length; x++) {
                if (sin[x].idfrom == id) {
                    addMessageThem(sin[x].messageid, sin[x].message, sin[x].time);
                } else {
                    addMessageMe(sin[x].messageid, sin[x].message, sin[x].time);

                }
                var doc = getel("chat_script");
                doc.scrollTop = doc.scrollHeight;

            }


//f.scrollTop+=100*g.all;
//f.scrollTop=f.scrollHeight;

        } else if (c[0] == "newmessage") {

            var fdd = event.data.replace("newmessage;", "");
            console.log(fdd);
            var h = JSON.parse(fdd);
            if (message_id == h.idto && my_id == h.idfrom) {
                addMessageMe(h.messageid, h.message, h.time);
                //  if(f.scrollHeight!=undefined && f.scrollHeight !=null)
                chat_script.scrollTop = chat_script.scrollHeight

            } else if (message_id == h.idfrom && my_id == h.idto) {
                addMessageThem(h.messageid, h.message, h.time);
                chat_script.scrollTop = chat_script.scrollHeight

            }
        }
    };




    socket.onopen = function (e) {
        if (get_cookie("db") != null)
            setTimeout(function () {
                sendMessage("b")
                sendMessage("authcookie;" + get_cookie("db") + ";" + get_cookie("auth") + ";" + get_cookie("authif") + ";" + get_cookie("decode"));

            }, 300);

//sendMessage("getTrend;");


    };

    socket.onclose = function (event) {

        //socket = new WebSocket("ws://" + ip + ":4446");
        // alert(`[close] Соединение закрыто чисто, код=${event.code} причина=${event.reason}`);

    };

    socket.onerror = function (error) {
        socket = new WebSocket("ws://" + ip + ":4446");
    };

}


const ranges = [{
    divider: 1E3,
    suffix: 'K'
}, {
    divider: 1E6,
    suffix: ' млн'
}, {
    divider: 1E9,
    suffix: ' млрд'
}];

function formatNumber(input) {
    for (let index = ranges.length - 1; index >= 0; index--) {
        if (input > ranges[index].divider) {
            let quotient = input / ranges[index].divider;

            if (quotient < 10) {
                quotient = Math.floor(quotient * 10) / 10;
            } else {
                quotient = Math.floor(quotient);
            }

            return quotient.toString() + ranges[index].suffix;
        }
    }

    return input.toString();
}

function formatDate(timeStampUnix) {
    if(timeStampUnix){
        return "Exclusive";
    }
    var timeStampDate = new Date(timeStampUnix);
    var timeStamp = timeStampDate.getTime();
    var now = new Date(),
        secondsPast = (now.getTime() - timeStamp) / 1000;
    if (secondsPast < 60) {
        return parseInt(secondsPast) + 's';
    }
    if (secondsPast < 3600) {
        return parseInt(secondsPast / 60) + ' minutes';
    }
    if (secondsPast <= 86400) {
        return parseInt(secondsPast / 3600) + ' hours';
    }
    if(secondsPast <=604800){
            return parseInt(secondsPast/86400)+" days ago"
    }
    if(secondsPast <=2678400){
        return parseInt(secondsPast/604800)+" week ago"
    }
    if(secondsPast <= 32140800){
        return parseInt(secondsPast/2678400)+" months ago"
    }
    if(secondsPast <= 32140800){
        return parseInt(secondsPast/2678400)+" months ago"
    }

    if (secondsPast > 32140800) {
      // day = timeStampDate.getDate();
       // month = timeStampDate.toDateString().match(/ [a-zA-Z]*/)[0].replace(" ", "");
       // year = timeStampDate.getFullYear() == now.getFullYear() ? "" : " " + timeStampDate.getFullYear();

        //return day + " " + month + year;
        return parseInt(secondsPast/32140800)+" years ago";
    }
}