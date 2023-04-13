package parse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.DefaultHttpClient;

import com.AntonSibgatulin.Debug.D;
/*import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

 */

public class ParseLink {
	   String url = "https://www.videosolo.com/downloader/";
       URL obj =  null;
       HttpsURLConnection con = null;

    
	public ParseLink(String link){
		
		try {
			obj = new URL(url);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String searchQuery = "Iphone 6s" ;
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			Thread.sleep(12500);
		String searchUrl = "https://presaver.com/C1pxq10H5xE/download/18?title=Ford%20F-250%20Diesel%20Super%20Duty%20Mega%20Raptor%3A%20When%20Half-Ton%20Raptor%20Just%20Won%27t%20Do!%20(360p)";
				HtmlPage page = client.getPage(searchUrl);
			/*	List<DomElement> lists = page.getElementsById("result-download-list");
				Thread.sleep(2500);
				D.bugss(lists.get(0).asXml());*/
			//	Thread.sleep(1500);
				/*HtmlForm form = page.getForms().get(0);
				//HtmlElement htmlElement = form.getElementsByTagName("input");
				List<DomElement> h= page.getElementsById("input-string");
				D.bugss(h.size());
				h.get(0).setAttribute("","https://youtu.be/5mAGn_grZKg");
				List<DomElement> h1= page.getElementsById("button-search");
				h1.get(0).click();
				D.bugss(h1.size());
				Thread.sleep(2500);
				List<DomElement> h11= page.getElementsById("result-download-list");
				//h11.get(0).click();
				D.bugss(h11.size());
				
				*/
				//  textField.setValueAttribute("my post code");
				//HtmlTextInput field = form.get
				//HtmlElement element = (HtmlElement) form.get("input-string");
				//element.setNodeValue("https://youtube.com/v/5mAGn_grZKg");
			
				/*
				HtmlElement element1 = (HtmlElement) page.getElementsById("button-search");
				element1.click();
			*/
				//	page.executeJavaScript(" <script>document.getElementById('input-string').value='https://youtube.com/v/5mAGn_grZKg'; document.getElementById('button-search').click(); </script>");
		//Thread.sleep(1500);
		//page.executeJavaScript(" <script>document.getElementsByClassName('list__item-link')[0].click(); </script>");
		/*		D.bugss(page.getHead());
		}catch(Exception e){
		e.printStackTrace();
		}
		*/
		/*
		try {
			URL url = new URL("https://redirector.googlevideo.com/videoplayback?expire=1610841615&ei=rykDYPaBAdDs7QS4up-wBQ&ip=37.44.198.201&id=o-AEWPqprH1n8PkoMEuboOyi_5vaTspOb5Q_M57h7bHxG1&itag=22&source=youtube&requiressl=yes&mh=OO&mm=31%2C26&mn=sn-n8v7knes%2Csn-c0q7lnsl&ms=au%2Conr&mv=m&mvi=14&pl=24&initcwndbps=195000&vprv=1&mime=video%2Fmp4&ns=ujmAKKjGjIZIs0pmF3o1McYF&ratebypass=yes&dur=1290.774&lmt=1545513926312591&mt=1610819558&fvip=2&c=WEB&txp=5532432&n=eUlXaAu-JrXHzW&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgDcugEMzjb-O4477cKiIVVkKdmF5qfeRF0gDXePslh4ECIQC5oqC3p9HfmYjj0iGacgAt1YyajGsOm4r-AyhDIiFBjA%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIgSCBBX4OcSLDaAoHE1XDVoJrFV7lnxOC5OyLmjALHTtYCIQCMNDS_ZEiRRioHvAzO7joJonNG0CtDNC23FHyjmj5Wig%3D%3D&title=Ford_F-250_Diesel_Super_Duty_Mega_Raptor:_When_Half-Ton_Raptor_Just_Wont_Do!");
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			connection.getInputStream();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//reader.wait(1000);
			D.bugss(connection.getURL());
		} catch (IOException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
		
		
	}
	
	public static void main(String... args) throws Exception{
		ParseLink parseLink =new ParseLink("");
		parseLink.sendPost111("omX0asoaazc");

		//String string = parseLink.getloadlinkm("omX0asoaazc");
//parseLink.er("a74xOwULiBU");
		/*String string = parseLink.sendPost("a74xOwULiBU");
		try {
			URL url = new URL(string.split("\"")[1]);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", 
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			connection.getInputStream();
			//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//reader.wait(1000);
			D.bugss(connection.getURL());
		} catch (IOException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	private final String USER__AGENT = "Mozilla/5.0";
	  private String  sendPost(String string) throws Exception {
		
		  
	        String url = "https://getvideo.org/get_video";
	        URL obj = new URL(url);
	        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

	       //add reuqest header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER__AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        con.setRequestProperty("Cookie", "PHPSESSID=fremljq04kuuouhfh14a3qctk5; _ga=GA1.2.1011548975.1610828547; _gid=GA1.2.1727434328.1610828547; _gat=1; _ym_uid=1610828548867669699; _ym_d=1610828548; _ym_visorc=w; _ym_isad=2");
	    
	        String urlParameters = "url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"&ajax=1&token=636tZeynszRt";

	       //Send post request
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();

	        int responseCode = con.getResponseCode();
	        System.out.println("\nSending 'POST' request to URL : " + url);
	        System.out.println("Post parameters : " + urlParameters);
	        System.out.println("Response Code : " + responseCode);

	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {

	        	if(inputLine.contains("<a href=\"https://redirector.googlevideo.com/")){
	        		return inputLine;
	        	}
	           // response.append(inputLine+"\n");
	        }
	        in.close();

	       //print result
	        
return response.toString();
	    }
		/*
	  public void er(String string ){

	      try {

	    	  
	    	
	    	
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	      
	      //  StringEntity input1 = new StringEntity("{\"url\":\"https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"\",\"ajax\":\"1\"}");
		      StringEntity input1 = new StringEntity("url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"&ajax=1&token=636tZeynszRt");
	    	  HttpPost httpPost = new HttpPost("https://getvideo.org/get_video");
	    	Header header = new Header();
	    	
	    		httpPost.addHeader("Cookie","PHPSESSID=fremljq04kuuouhfh14a3qctk5; _ga=GA1.2.1011548975.1610828547; _gid=GA1.2.1727434328.1610828547; _gat=1; _ym_uid=1610828548867669699; _ym_d=1610828548; _ym_visorc=w; _ym_isad=2");
	    		httpPost.setEntity(input1 );	// execute method and handle any error responses.
	    			
D.bugss(httpPost.toString());
	     
	        HttpResponse response = httpClient.execute(httpPost);

	        if (response.getStatusLine().getStatusCode() != 201) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                + response.getStatusLine().getStatusCode());
	        }

	        BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

	        String output;
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	        }
	        

	      } catch (MalformedURLException e) {

	        e.printStackTrace();

	      } catch (IOException e) {

	        e.printStackTrace();

	      }

	  }
	  
	  
	  
	  
	  public void er1(String string ){
			WebClient client = new WebClient();
			client.getOptions().setCssEnabled(false);
			client.getOptions().setJavaScriptEnabled(false);
		
			String searchUrl = "https://presaver.com/"+string+"/download/18?title=Chevrolet+Camaro.ПУГАЮЩИЙ+СВОЕЙ+НЕОБУЗДАННОЙ+МОЩНОСТЬЮ.&oq=Chevrolet+Camaro.ПУГАЮЩИЙ+СВОЕЙ+НЕОБУЗДАННОЙ+МОЩНОСТЬЮ.+(360p)";
			try {
				HtmlPage page = client.getPage(searchUrl);
				Thread.sleep(50);
				client.close();
				D.bugss(page.getUrl().toString());
			} catch (FailingHttpStatusCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
	    	
	    	
	  }
	  
	  


		 */
	  
	  
	  public String  getloadlinkm(String  string){
		  String main = "";
		  
		  URL url;
		try {
			url = new URL("https://presaver.com/"+string+"/download/18?title=Chevrolet+Camaro.ПУГАЮЩИЙ+СВОЕЙ+НЕОБУЗДАННОЙ+МОЩНОСТЬЮ.&oq=Chevrolet+Camaro.ПУГАЮЩИЙ+СВОЕЙ+НЕОБУЗДАННОЙ+МОЩНОСТЬЮ.+(360p)");
			D.bugss(url.toString());
			HttpsURLConnection con = null;
			try {
				con = (HttpsURLConnection) url.openConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  con.setRequestProperty("User-Agent", USER__AGENT);
		        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		        Thread.sleep(1000);
		        BufferedReader in = new BufferedReader(
		                new InputStreamReader(con.getInputStream()));
		     //   String inputLine;
		        //StringBuffer response = new StringBuffer();

		      //  while ((inputLine = in.readLine()) != null) {
//System.out.println(inputLine);
		        	//if(inputLine.contains("<a href=\"https://redirector.googlevideo.com/")){
		        	//	return ;
		        //	}
		        	
		           // response.append(inputLine+"\n");
		       // }
		        Thread.sleep(5000);
		        in.close();
		       main = con.getURL().toString();
		       D.bugss(main);
		        
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return main;
	        
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  /*
	  
	  public void er11(String string ){

	      try {

	    	  
	    	
	    	
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	      
	      //  StringEntity input1 = new StringEntity("{\"url\":\"https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"\",\"ajax\":\"1\"}");
		      StringEntity input1 = new StringEntity("url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"&ajax=1&format=web");
	    	  HttpPost httpPost = new HttpPost("https://www.videosolo.com/downloader/");
	    	Header header = new Header();
	    	
	    		httpPost.addHeader("Cookie","__cfduid=d59cc599b4be054f82f2f82e721ecef551610874773; _ga=GA1.2.2090292999.1610874775; _gid=GA1.2.48348357.1610874775; _ym_uid=1610874777849242093; _ym_d=1610874777; _ym_isad=2; _hjTLDTest=1; _hjid=b1a8aab2-7c56-4203-9d97-a64b02a7cc3c; _gat_UA-83005578-6=1; _hjAbsoluteSessionInProgress=1");
	    		httpPost.setEntity(input1 );	// execute method and handle any error responses.
	    			
D.bugss(httpPost.toString());
	     
	        HttpResponse response = httpClient.execute(httpPost);

	        if (response.getStatusLine().getStatusCode() != 201) {
	            throw new RuntimeException("Failed : HTTP error code : "
	                + response.getStatusLine().getStatusCode());
	        }

	        BufferedReader br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

	        String output;
	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	            System.out.println(output);
	        }
	        

	      } catch (MalformedURLException e) {

	        e.printStackTrace();

	      } catch (IOException e) {

	        e.printStackTrace();

	      }

	  }
	  


	   */
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  public String  sendPost111(String string) throws Exception {
			D.bugss("start");
		  con =(HttpsURLConnection) obj.openConnection();
	       //add reuqest header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER__AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        con.setRequestProperty("Cookie", "PHPSESSID=b5d2ihb79qe4dmejji61hdnln7; _ga=GA1.2.701402167.1610870241; _ym_uid=1610200270947595097; _ym_d=1610870241; _gid=GA1.2.1995800272.1612022711; _ym_visorc=w; _ym_isad=1; _gat=1");
	    	D.bugss("start");
	        String urlParameters = "url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+string+"&format=web";

	       //Send post request
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();
	    	D.bugss("start");
	       
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	      String response = "";
	        while ((inputLine = in.readLine()) != null) {
	        	response = response+inputLine;
	        	//if(inputLine.contains("<a href=\"https://redirector.googlevideo.com/")){
	        	//	return inputLine;
	        	//}
	           // response.append(inputLine+"\n");
	        }
	        in.close();

	       //print result
	        
return response;
	    }
	  
	  
	  
	  
	  
	  
	  public String  getlinks(String string) throws Exception {
		  URL obj = new URL("https://lookweather.000webhostapp.com/test1.php?v="+string);
			D.bugss("start");
		  con =(HttpsURLConnection) obj.openConnection();
	       //add reuqest header
	        con.setRequestProperty("User-Agent", USER__AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	       D.bugss("start");
	      
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	      String response = "";
	        while ((inputLine = in.readLine()) != null) {
	        	response = response+inputLine;
	        	//if(inputLine.contains("<a href=\"https://redirector.googlevideo.com/")){
	        	//	return inputLine;
	        	//}
	           // response.append(inputLine+"\n");
	        }
	        in.close();
	        con.disconnect();

	       //print result
	        
return response;
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	
}
