package com.AntonSibgatulin.httpproto;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import javax.annotation.processing.Filer;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;

public class Directory {
	public HashMap<String ,FileSibgatulina> geforce = new HashMap<>();
	public HashMap<String ,FileSibgatulina> system = new HashMap<>();
	
	public void reloadfile(String name1){
		geforce.remove(mainDerictory+name1);
		File file = new File(mainDerictory+name1);
		if(file.isFile()){
			final String names = file.getName();
			System.out.println(names.split("\\.")[1]);
			FileSibgatulina fileSibgatulina = null;
			String path = file.getPath();
			String[] typem = file.getName().split("\\.");
			String type = typem.length>0?typem[typem.length-1]:"";
			String fileinfo = null;
			String base = null;
			byte[] bytes = null;
			String name = file.getName();
			typefile typefiles = gettype(type);
			FileReader fileReader = null;
			BufferedReader bufferedReader=null;
			try {
				 bufferedReader = new BufferedReader(fileReader= new FileReader(file));
		
			} catch (IOException ignore) {
				// TODO Auto-generated catch block
				//ignore
				System.err.println("File "+file.getName()+" didn't been loaded");
			}
			
			
			
			if(typefiles==typefiles.PAGE||typefiles==typefiles.JS||typefiles==typefiles.CSS){
				try {
				String r,s = "";
					while((r = bufferedReader.readLine())!=null){
						s = s +r+"\n";
					}
				
					fileinfo = s;
				} catch (IOException ignore) {
					// TODO Auto-generated catch block
					//ignore
					System.err.println("File "+file.getName()+" didn't been loaded");
				}               
				
			}else{
				
		            try {
						bytes = Files.readAllBytes(file.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		          
				
				
			}
			fileSibgatulina = new FileSibgatulina(fileinfo, bytes, type, name, base,typefiles);
		System.out.println(file.getPath());
geforce.put(path, fileSibgatulina);

		}else{
			//files.put(key, value)
		//	new Thread(new Runnable() {
				
			//	@Override
			//	public void run() {
				//	reloadfile(mainDerictory+name+"/"+file.getName());
					//System.err.println(dir+file.getName());
					
			//	}
			//}).start();
			getFiles(geforce, mainDerictory+name1);
		
		}
		
	}
	
	public String[] imagetype = ("jpg;jpeg;JPEG;JPG;PNG;png;BMP;bmp;ico;ICO;GIF;gif").split(";");
	public String[] htmlfile = ("html;htm;html5;php;php5").split(";");
	public String[] cssfile = ("css;css3;css5").split(";");
	public String[] fontfile = ("ttf;woff;woff2;otf").split(";");
	public String[] jsfile = ("js;map").split(";");
	String mainDerictory = null;
	public JSONArray systemfolder = null;
	
	public Directory(String mainDirectory,JSONArray jsonArray){
		this.mainDerictory = mainDirectory;
		this.systemfolder = jsonArray;
		
		getFiles(geforce,mainDirectory);
	}
	
	public typefile gettype(String name){
		for(String str:imagetype){
			if(name.equals(str)){
				
				return typefile.IMAGE;
				
			}
		}
		for(String str:this.htmlfile){
			if(name.equals(str)){
				
				return typefile.PAGE;
				
			}
		}
		
		for(String str:this.cssfile){
			if(name.equals(str)){
				
				return typefile.CSS;
				
			}
		}
		for(String str:this.jsfile){
			if(name.equals(str)){
				
				return typefile.JS;
				
			}
		}
		for(String str:this.fontfile){
			if(name.equals(str)){
				
				return typefile.FONT;
				
			}
		}
		return typefile.UNKNOWFILE;
		
		 
	}
	
	public void getFiles(HashMap<String,FileSibgatulina> files,String dir){
		
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles!=null ){
		for(File file :listOfFiles){
			if(file.isFile()){
				final String names = file.getName();
				System.out.println(names.split("\\.")[1]);
				FileSibgatulina fileSibgatulina = null;
				String path = file.getPath();
				String[] typem = file.getName().split("\\.");
				String type = typem.length>0?typem[typem.length-1]:"";
				String fileinfo = null;
				String base = null;
				byte[] bytes = null;
				String name = file.getName();
				typefile typefiles = gettype(type);
				FileReader fileReader = null;
				BufferedReader bufferedReader=null;
				try {
					 bufferedReader = new BufferedReader(fileReader= new FileReader(file));
			
				} catch (IOException ignore) {
					// TODO Auto-generated catch block
					//ignore
					System.err.println("File "+file.getName()+" didn't been loaded");
				}
				
				
				
				if(typefiles==typefiles.PAGE||typefiles==typefiles.JS||typefiles==typefiles.CSS){
					try {
					String r,s = "";
						while((r = bufferedReader.readLine())!=null){
							s = s +r+"\n";
						}
					
						fileinfo = s;
					} catch (IOException ignore) {
						// TODO Auto-generated catch block
						//ignore
						System.err.println("File "+file.getName()+" didn't been loaded");
					}               
					
				}else{
					      try {
							bytes = Files.readAllBytes(file.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			          
					
					
				}
				fileSibgatulina = new FileSibgatulina(fileinfo, bytes, type, name, base,typefiles);
			System.out.println(file.getPath());
files.put(path, fileSibgatulina);

			}else{
				//files.put(key, value)
			//	new Thread(new Runnable() {
					
				//	@Override
				//	public void run() {
for(int i = 0;i<systemfolder.length();i++){
	try {
		if(file.getName().equals(systemfolder.get(i))){
			
			getFiles(system,dir+"/"+file.getName(),true);
			
		
		
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		return;
	}
	
	      
}
						getFiles(files,dir+"/"+file.getName());
						//System.err.println(dir+file.getName());
						
				//	}
				//}).start();
			
			}
		}
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void getFiles(HashMap<String,FileSibgatulina> files,String dir,boolean bool){
		
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles!=null ){
		for(File file :listOfFiles){
			if(file.isFile()){
				final String names = file.getName();
				System.out.println(names.split("\\.")[1]);
				FileSibgatulina fileSibgatulina = null;
				String path = file.getPath();
				String[] typem = file.getName().split("\\.");
				String type = typem.length>0?typem[typem.length-1]:"";
				String fileinfo = null;
				String base = null;
				byte[] bytes = null;
				String name = file.getName();
				typefile typefiles = gettype(type);
				FileReader fileReader = null;
				BufferedReader bufferedReader=null;
				try {
					 bufferedReader = new BufferedReader(fileReader= new FileReader(file));
			
				} catch (IOException ignore) {
					// TODO Auto-generated catch block
					//ignore
					System.err.println("File "+file.getName()+" didn't been loaded");
				}
				
				
				
				if(typefiles==typefiles.PAGE||typefiles==typefiles.JS||typefiles==typefiles.CSS){
					try {
					String r,s = "";
						while((r = bufferedReader.readLine())!=null){
							s = s +r+"\n";
						}
					
						fileinfo = s;
					} catch (IOException ignore) {
						// TODO Auto-generated catch block
						//ignore
						System.err.println("File "+file.getName()+" didn't been loaded");
					}               
					
				}else{
					      try {
							bytes = Files.readAllBytes(file.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			          
					
					
				}
				fileSibgatulina = new FileSibgatulina(fileinfo, bytes, type, name, base,typefiles);
			System.out.println(file.getPath());
files.put(path, fileSibgatulina);

			}else{
				//files.put(key, value)
			//	new Thread(new Runnable() {
					
				//	@Override
				//	public void run() {
for(int i = 0;i<systemfolder.length();i++){
	try {
		if(file.getName().equals(systemfolder.get(i)))return;
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		return;
	}
	
	      
}
						getFiles(files,dir+"/"+file.getName());
						//System.err.println(dir+file.getName());
						
				//	}
				//}).start();
			
			}
		}
		}
		
	}
	
	
	
	
	
	
	
}
