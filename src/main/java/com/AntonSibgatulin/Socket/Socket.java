package com.AntonSibgatulin.Socket;

import org.java_websocket.WebSocket;

public class Socket {
	 public static void send(WebSocket conn,String message){
		 if(conn==null)return;
		 conn.send(message);
	 }
	 public static boolean  sendbol(WebSocket conn,String message){
		 if(conn==null || conn.isClosed() || conn.isConnecting() ||conn.isFlushAndClose())return false;
		 conn.send(message);
		 return true;
	 }
}
