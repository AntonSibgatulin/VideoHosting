package com.AntonSibgatulin.httpproto;

public class FileSibgatulina implements FileImplementation{
String string =null;
byte[] bytes =null;
String typefile = null;
String name = null;
String base64 = null;
typefile type = null;
	public FileSibgatulina(String string,byte[] bytes,String typefile,String name,String base64,typefile type) {
		// TODO Auto-generated constructor stub
	this.string = string;
	this.bytes = bytes;
	this.type = type;
	
	this.name = name;
	this.base64 = base64;
	this.typefile = typefile;
	
	
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return string ;
	}

	@Override
	public byte[] getArrayByte() {
		// TODO Auto-generated method stub
		return bytes;
	}

	@Override
	public String getTypeFile() {
		// TODO Auto-generated method stub
		return typefile;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String Base64() {
		// TODO Auto-generated method stub
		return base64;
	}

}
