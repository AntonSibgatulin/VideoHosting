package com.AntonSibgatulin.Instrument;

import org.json.JSONObject;

public class VideoLoad {
	String link=null,id,reallink,title,Description,titlechanel,previewdef,high;
	public String toString(){
		//return ""+id+" "+title+" reallink";
		return jsonMain.toString();
	}
	public JSONObject jsonMain = null;
	
	public VideoLoad(String link,String id,String title,String reallink,String Description,String titlechanel,String previewdef,String high,JSONObject jsonObject){
		this.link = link;
		this.jsonMain = jsonObject;
		this.id = id;
		this.title = title;
		this.Description = Description;
		this.reallink = reallink;
		this.titlechanel = titlechanel;
		this.high = high;
		this.previewdef = previewdef;
		
		
	}

}
