package com.AS.client;

import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.PasswordAuthentication;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.AntonSibgatulin.Debug.D;

public class LoginGUI {
	public JTextField login_text = new JTextField(20);
	JPasswordField password_text = new JPasswordField(20);
	public int w,h;
	public String login_button = "login_button";
	public JButton button = new JButton("Вход");
	public JLabel login_label = new JLabel("Логин:");
	public JLabel password_label = new JLabel("Пароль:");
	public LoginGUI(int w,int h){
		this.w = w;
		this.h = h;
	}
	public void initObjectButton(JPanel panel){
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(login_text.getText().length()>=4 && password_text.getText().length()>=6){
				String str = "auth;"+login_text.getText()+";"+password_text.getText();
				Main.mainController.window.client.send(str);
				login_text.setText("");
				password_text.setText("");
				}else{
					login_text.setText("");
					password_text.setText("");
				}
			}
		});
		login_text.setBounds((int)(w*52.5-w*40/2), h*35-h*10/2,w*40, h*10);
		password_text.setBounds((int)(w*52.5-w*40/2), h*55-h*10/2,w*40, h*10);
		password_label.setBounds((int)(w*52.5-w*40/2), h*55-h*20,80, h*25);
		login_label.setBounds((int)(w*52.5-w*40/2), h*35-h*20,100,h*25);
		button.setBounds((int)(w*52.5+w*40/2-w*10), h*80-h*16,w*10, h*10);
		panel.add(button);
		panel.add(login_label);
		panel.add(password_label);
		panel.add(password_text);
		panel.add(login_text);
		panel.repaint();
	}
}
