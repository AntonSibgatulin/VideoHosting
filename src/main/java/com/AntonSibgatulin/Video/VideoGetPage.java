package com.AntonSibgatulin.Video;

import org.apache.http.cookie.Cookie;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.httpproto.Directory;

public class VideoGetPage {
	public DatabasConnect base = null;
	public Directory directory = null;
	public VideoGetPage(DatabasConnect  base,Directory directory){
		this.directory = directory;
		this.base = base;
	
	}
	public boolean  page(String[] cookie ){
		return false;
		
	}

}
