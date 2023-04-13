package com.AntonSibgatulin.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import com.AntonSibgatulin.Coin.CoinRubles;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Register.Register;
import com.AntonSibgatulin.System.ControllerSystem;
import com.AntonSibgatulin.httpproto.Directory;
import com.AntonSibgatulin.httpproto.SocketProcessor;

public class Main {
	public static CoinRubles coinRubles = null;
	public static ControllerSystem controller = null;
	public static Register register =null;
public static Server server = null;
//public static Object controller;
public static 	Directory directory = null;
	public static void main(String[] args) throws IOException, JSONException {
	
		
		coinRubles = new CoinRubles();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("configure/socket.cfg")));
			String string ="";
			String str = "";
			while((str= bufferedReader.readLine())!=null){
				string = string+str;
			}
			JSONObject jsonObject = new JSONObject(string);
			 directory = new Directory("html",jsonObject.getJSONArray("systemfolder"));
			 
			server = new Server(InetAddress.getLocalHost().getHostAddress().toString(),jsonObject.getInt("port"));
			server.start();
			register = new Register(server.base);
			register.start();
			controller = new ControllerSystem(server, register);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
				 ServerSocket ss = null;
					try {
						//System.out.println(""+InetAddress.getLocalHost().getHostAddress().toString());
						 InetAddress addr = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress().toString());
						ss = new ServerSocket(80,1000,addr);
						System.err.println("all files loaded "+addr.toString());
						while (true) {
						         Socket s = ss.accept();
						         //System.err.println("Client accepted");
						         new Thread(new SocketProcessor(s,directory,server)).start();
						     }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
					
				}
			}).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			while(true){
				String string = scanner.nextLine();
				execute(string,directory,server);
				
			}
				
			}
		}).start();
	}
	
	
	public static void execute(String command,Directory dir,Server server){
		String[] args = command.split(" ");
		if(args.length>0){
		if(args[0].equals("reload")){
			String namefile = args[1].startsWith("/")==true?""+args[1]:"/"+args[1];
			dir.reloadfile(namefile);
			
		}else if(args[0].equals("restart")){
			if(args[1].equals("websocket")){
				for(WebSocket socket :server.conns){
					socket.close();
				}
			}
		}
		D.log(command);
		}
	
	}
	
	
	

   
	
}
