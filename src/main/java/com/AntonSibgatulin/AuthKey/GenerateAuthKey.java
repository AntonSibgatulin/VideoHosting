package com.AntonSibgatulin.AuthKey;

import java.util.Random;

import org.springframework.security.crypto.keygen.KeyGenerators;

public class GenerateAuthKey {
	public Random random = new Random();
public String[] letter = {"A","B","C","D","E","a","b","c","d","e","F","G","H","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","f","g","h","j","k","l","m","o","p","q","r","s","t","u","v","w","x","y","z"};
	public int[] number ={1,2,3,4,5,6,7,8,9,0};
	public GenerateAuthKey(){
		
	}
	public  String  GenerateAuthificationKey(int size,int col){
		String key ="";
		for(int i = 0;i<size;i++){
			int c  = random.nextInt(5);
					if(c!=1){
						if(col==1)
						key=key+letter[random.nextInt(letter.length)];
						else{
							key=key+number[random.nextInt(number.length)];
							
						}
						
					}else{
						if(col==1)
							key=key+number[random.nextInt(number.length)];
						
						else 
							key=key+letter[random.nextInt(letter.length)];
						
								
					}
		}
		return key;
	}
public  String DecodeKey(){
		
		return KeyGenerators.string().generateKey();
	}
public  String DecodePasswordKey(){
	
	return GenerateAuthificationKey(32, 1);
}

public Auth GenerateAuthKey(){
	Auth auth = new Auth();
	auth.Auth = this.GenerateAuthificationKey(96, 1);
	auth.Authificationkey = this.DecodePasswordKey();
	auth.decodekey = this.DecodeKey();
	
	return auth;
}

}
