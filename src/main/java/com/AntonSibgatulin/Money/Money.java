package com.AntonSibgatulin.Money;

import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import com.AntonSibgatulin.Coin.CoinRubles;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Main.Main;
import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.Socket.Socket;
import com.AntonSibgatulin.User.User;

public class Money {
	public static final int MAX_VOID = 10;
	public Server server = null;
	public Money(Server server){
		this.server = server;
		
	}
	
	public CoinRubles coinRubles = Main.coinRubles;
	
	public void execute(User user,String[]str){
	if(user==null ){
			return;
			
			
		}else{
			if(user.purseModel==null){
			
			return;
			}
		}
		if(str.length>=2&& str[1]!=null){
			if(str[1].equals("getBalance")){
				for(WebSocket socket:user.device.list){
					Socket.send(socket,"setbalance;"+user.purseModel.coinRubles+";"+user.name+";"+user.surname);
					
				}
			}else if(str[1].equals("translate")){
				if(str[2]!=null && str[3]!=null){
				String loginTo = str[2];
				if(user.login.equals(loginTo))return;
				if(str[3].matches("-?\\d+(\\.\\d+)?")){
					str[3] = str[3].replaceAll(",",".");
				            Double summa = Double.valueOf(str[3]);




					D.log(summa);
					summa = (double) Math.round(summa * 100) / 100;
					D.log(summa);
				            if(summa<=0){
				            	for(WebSocket sock:user.device.list){
				            		Socket.send(sock, "translate;error;money");
				            		
				            	}
				            	return;
				            }
				            User userTo =server.online.get(loginTo);//Server.
				            if(userTo==null){
				            	JSONObject jsonObject = server.base.getUserId(loginTo);
				            	if(jsonObject.has("login")){
				            		translate(user,jsonObject, summa,0);
				            	}else{
				            		for(WebSocket sock:user.device.list){
					            		Socket.send(sock, "translate;error;found");
					            		
					            	}	
				            	}
				            }else{
				            	if(server.IDUser.get(userTo.id)!=null){
				            	translate(user,userTo, summa,0);
				            	}
				            	
				            }
				            
				           // if(summa>0)
				            
				}
				}
			}else if(str[1].equals("buymoney")){
				
			}else if(str[1].equals("sell")){
				
			}
		}
		
		
	}
	public void translate(User me,User user,double summa,int i){
		if(user==null)return;
		if(me==null)return;
		if(me.purseModel==null)return;
		
		if(user.purseModel!=null && me.purseModel !=null){
			if(user.purseModel.Operation==false && me.purseModel.Operation==false){
				
				user.purseModel.Operation=!user.purseModel.Operation;
				me.purseModel.Operation=!me.purseModel.Operation;
				
				if(me.purseModel.transform(summa)){
					server.base.updatemoney(me,user,summa);
				
				}else{

					for(WebSocket sock:me.device.list){
	            		Socket.send(sock, "translate;error;monenyn't");
	            	}

				}
				
				
			}else{
						if(i>=MAX_VOID){
				for(WebSocket sock:me.device.list){
            		Socket.send(sock, "translate;error;operation");
            	}
						}else{
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		
		            		translate(me, user, summa, i++);
								}
			}
		}else return;
		
		
		
		
	}
	
	public void translate(User me,JSONObject jsonObject ,double summa,int i ){
		if(jsonObject==null)return;
		if(me.purseModel!=null){
			if(me.purseModel.Operation==false){
				
				me.purseModel.Operation=!me.purseModel.Operation;
				
				if(me.purseModel.transform(summa)){
					try {
						server.base.updatemoney(me,new User(1, jsonObject.getString("login"),null),summa);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}else{

					for(WebSocket sock:me.device.list){
	            		Socket.send(sock, "translate;error;moneyhaven't");
	            	}

				}
				
				
			}else{
				
						if(i>=MAX_VOID){
				for(WebSocket sock:me.device.list){
            		Socket.send(sock, "translate;error;operation");
            	}
            		
            	}else{
            		try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		translate(me, jsonObject, summa, i++);
						}
			}
		
	}
	}
	//811d41e18e6e17553144da66a9163428

}
