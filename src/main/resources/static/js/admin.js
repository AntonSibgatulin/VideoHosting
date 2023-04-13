alert("connect with admin panel but if you don't Admin you just will can't doing cuz you can't oooh");
var massive = ["exit","repost","send","link","show_all_video","ban","unban_video","addlike","adddislike","addview"];

function ban(object){
var id = object.id.split(";")[1];
sendMessage("ban_video;"+id);
getel("menuforuser"+id).style="display:none;";
}
function ban_channel(object){
var id = object.id.split(";")[1];
sendMessage("ban_chan;"+id);
getel("menuforuser"+id).style="display:none;";
}
function addview(object){
var id = object.id.split(";")[1];
var a = prompt ("Score for view");
sendMessage("addview;"+id+";"+a);
getel("menuforuser"+id).style="display:none;";
}
function ban_user(object){
var id = object.id.split(";")[1];
sendMessage("ban_user;"+id);
getel("menuforuser"+id).style="display:none;";
}
function adddislike(object){
var id = object.id.split(";")[1];
var a = prompt ("Score for dislike");
sendMessage("adddislike;"+id+";"+a);
getel("menuforuser"+id).style="display:none;";
}
function addlike(object){
var id = object.id.split(";")[1];
var a = prompt ("Score for like");
sendMessage("addlike;"+id+";"+a);
getel("menuforuser"+id).style="display:none;";
}
function unban_video(object){
var id = object.id.split(";")[1];
sendMessage("unban_video;"+id);
getel("menuforuser"+id).style="display:none;";
}






