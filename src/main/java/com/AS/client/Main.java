package com.AS.client;

import java.awt.BorderLayout;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.AS.client.AUTH.Client;
import com.AS.client.AUTH.Window;

public class Main {
public static JFrame frame = null;
public static JPanel panel_auth = new JPanel();
public static JPanel panel_reg = new JPanel();
public static JPanel panel_mess = new JPanel();
public static MainController mainController =null;
	
	public static void main(String[]args) throws InterruptedException, URISyntaxException{
	/*	Client client = new Client();
		
		Window winndow = new Window(client);
		mainController = new MainController(winndow);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				client.run();
				
				
			}
		}).start();
		
		Thread.sleep(200);
		
		int w = winndow.w();
		int h = winndow.h();
		LoginGUI guilogin = new LoginGUI(w, h);
		guilogin.initObjectButton(winndow.main);	
		*/
		System.out.println(48/10);
	}
}
