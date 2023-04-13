package com.AntonSibgatulin.Crypt;

//1j24W825EY419G97
//1604320823456
/*
 * @author AntonSibgatulin
 */
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class CryptInformationAntonSibgatulin extends AbstractEncrypt{
	
	  public static String encodeToString(BufferedImage image, String type) {
	        String base64String = null;
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        try {
	            ImageIO.write(image, type, bos);
	           
	           // BASE64Encoder encoder = new BASE64Encoder();
	            base64String = Base64.getEncoder().encodeToString(bos.toByteArray());
	            bos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return base64String;
	    }
	
	public void  CryptoInfo(String information,long time,String key)throws Exception{
		
	}
	public CryptInformationAntonSibgatulin(){
		super();
	}
	public static void main(String[]args){
		 final String password = "2dd9cffc51838d46";
	        String textToEncrypt = "Hello";
	      //  for (int i = 0; i < 10; i++) {
	            String salt = KeyGenerators.string().generateKey();
	            TextEncryptor encryptor = Encryptors.text(password, salt);
	            System.out.println(salt);
	            //eadf99ba83eb248fe417d91d79ab14c140b165fab6fe92a0fae080ac7bcdf231
	            String cipherText = /*"872fb50361783a86525a69e48742225623d9c1e5e6b07efa62e1544489da4ec3";*/encryptor.encrypt(textToEncrypt);
	            String decryptedText = encryptor.decrypt(cipherText);
	            System.out.println("Src: " + textToEncrypt);
	            System.out.println("Cipher: " + cipherText);
	            System.out.println("Decrypted: " + decryptedText);
	            System.out.println("__________________");
	       // }
	}

}
