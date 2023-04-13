package com.AS.client.AUTH;

import java.awt.Component;
import java.net.SocketAddress;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.java_websocket.client.WebSocketClient;

import com.AntonSibgatulin.Debug.D;

public class Window extends JFrame{

	public  JPanel panel_auth = new JPanel();
	public  JPanel panel_reg = new JPanel();
	public  JPanel panel_mess = new JPanel();
	public HashMap<String,Component> array = new HashMap<>();
	public JPanel main = null;
	public void set_auth_panel(){
		main=panel_auth;
		main.setLayout(null);
	}
	public WebSocketClient client =null;
	public Window(WebSocketClient client ){
		
		super("client");
		this.client = client;
		panel_auth.setLayout(null);
		panel_reg.setLayout(null);
		panel_mess.setLayout(null);
		set_auth_panel();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,350);
		super.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(true);
		getContentPane().add(main);
		pack();
		super.setVisible(true);
		
	
		
			
	}
	public int h(){
		return getContentPane().getHeight()/100;
	}
	public int w(){
		return getContentPane().getWidth()/100;
	}
	public void f(){
		D.log(super.getContentPane().getHeight());
		D.log(super.getContentPane().getWidth());
	}
	public void addElements(String name,Component comp){
		array.put(name, comp);
		main.add(comp);
	}
}