package com.AntonSibgatulin.Coin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class CoinRubles implements ActionListener{
	public double price = 1.00000;
Timer timer = new Timer(200, this);
public  CoinRubles() {
	timer.start();
	// TODO Auto-generated constructor stub
}
public double getPrice(){
	return price;
}
public double a = 0;
@Override
public void actionPerformed(ActionEvent e) {
price +=a;	
}
}
