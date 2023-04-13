package com.AntonSibgatulin.Message;

import java.util.Set;

import org.java_websocket.WebSocket;

import com.AntonSibgatulin.Device.Device;
import com.AntonSibgatulin.Main.Main;
import com.AntonSibgatulin.Socket.Socket;
import com.AntonSibgatulin.User.User;

public class Messager {
public Messager (){
	
	
	
}
public void send(long toid,long fromid,Long idmessage,String message){
	
}
public String getMessageHistory(){
	return "";
}

public void push(long id,String message){
	User user = Main.controller.web.IDUser.get(id);
	if(user!=null){
		Device device = user.device;
		Set<WebSocket> push = device.list;
		for(WebSocket sock:push){
			Socket.send(sock, "");
		}
	}
}

}
