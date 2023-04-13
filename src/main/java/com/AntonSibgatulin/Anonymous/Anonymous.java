package com.AntonSibgatulin.Anonymous;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.VersionInfo;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

import com.AntonSibgatulin.Debug.D;
import com.fasterxml.jackson.databind.ObjectReader;

public class Anonymous {
	public String device = "";
	
	double[] coordinate = null;
	double[] ipcoordinate = null;
	String name = null;String surname = null;
	String[] searched = null;
	String login = null;
	public Anonymous(String ip,WebSocket sock){
		this.ip = ip;
		this.conn = sock;
	}
	public String search = "";
	
	public String ip = "";
	public String country = "Russia";
	public String ipcountry = "Russia";
	public String city = "Syzran",ipcity ="Syzran";
	public WebSocket conn = null;
	
	public  void getipposition(String ip){
		try {
			
			URL url = new URL("http://free.ipwhois.io/json/"+ip);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputline = "";
		
			
			String json = "";
			
			while((inputline = bufferedReader.readLine())!=null){
				json = json+inputline;
			}
			bufferedReader.close();
			
			JSONObject object  = new JSONObject(json);
			ipcountry = object.getString("country");
			
			ipcity = object.getString("city");
			setIpcoordinate(new double[]{object.getDouble("latitude"),object.getDouble("longitude")});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setIpcoordinate(double... coordinate){
		this.ipcoordinate = coordinate;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setSurname(String name){
		this.surname = name;
	}
	public void setCoordinate(double...ds ){
		this.coordinate = ds;
		
	}
}
