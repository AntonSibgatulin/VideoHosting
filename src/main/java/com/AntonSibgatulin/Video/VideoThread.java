package com.AntonSibgatulin.Video;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.AntonSibgatulin.Debug.D;

public class VideoThread {
	int si = 1024*3*1024;

public String name = null;
public long like ,dislike;
public String info = null;
public String file = null; 
public String filetype = null;
public ArrayList<byte[]> ar = null;
public long length = 0;
public int chunk = 0;
public long minimal = 0;

	public VideoThread(String name ,ArrayList<byte[]> ar,long like,long dislike,String info,String file ,long length,int chunk,long  minimal) {
		//this.bytes = bytes;
		this.name = name;
		this.minimal = minimal;
		
		this.info = info;
		this.length = length;
		this.chunk = chunk;
		this.file = file; 
		this.ar = ar;
		this.like = like;
		this.dislike = dislike;
		filetype = file.split("\\.")[file.split("\\.").length-1];
		if(filetype.equals("avi")){
			filetype="x-msvideo";
		}
		
		
	}
	public byte[] getByteMassive(long from ){
		if (from <0)return null;
		
		double getting = Math.ceil(from/chunk);
		D.bugs("getting: "+getting+"   bytes from: "+from+"   bytes chunk:  "+chunk);
		if(getting!=0 && getting >=ar.size()){
			getting = ar.size()-1;
		}
			
		byte[] u = this.ar.get((int)getting);
		D.bugs(u.length+" :length");
		return u;
	}
	public int getInt(long from ){
		if (from <0)return 0;
		
		double getting = Math.ceil(from/chunk);
		D.bugs("getting: "+getting+"   bytes from: "+from+"   bytes chunk:  "+chunk);
		return (int)(getting);
	}
	HashMap<String,Long> showing = new HashMap<String, Long>();
	public VideoThread(File file,String name,String info,int like,int dislike){
		
		this.name = name;
		this.like = like;
		this.dislike = dislike;
	}

}
