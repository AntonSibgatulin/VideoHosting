package com.AntonSibgatulin.Debug;

public class D {
	public static boolean debug = true;
	public static long now (){
		return System.currentTimeMillis();
	}
	public static void log(String str){
		if(debug)
		System.out.println(str);
		
	}
	public static void log(Long str){if(debug)
		System.out.println(str);
		
	}
	public static void log(long str){if(debug)
		System.out.println(str);
		
	}
	public static void log(Integer str){if(debug)
		System.out.println(str);
		
	}
	public static void log(int str){if(debug)
		System.out.println(str);
		
	}
	public static void log(Object str){if(debug)
		System.out.println(str.toString());
		
	}
	public static void log(byte[] str){if(debug)
		System.out.println(new String(str));
		
	}
	public static void log(char[] str){if(debug)
		System.out.println(new String(str));
		
	}
	public static void log(char str){if(debug)
		System.out.println((str));
		
	}
	public static void bugs(Object i ){
		System.out.println(i.toString());
	}
	
	public static void bugss(Object i ){
		System.err.println(i.toString());
	//	NullPointerException nullPointerException = (NullPointerException)i;
		//nullPointerException.printStackTrace();
		
	}
	public static void bugss1(Object i ){
		//System.err.println(i.toString());
	//	NullPointerException nullPointerException = (NullPointerException)i;
		//nullPointerException.printStackTrace();
		
	}
	public static void bugss2(Object i ){
		System.err.println(i.toString());
	//	NullPointerException nullPointerException = (NullPointerException)i;
		//nullPointerException.printStackTrace();
		
	}
	
}
