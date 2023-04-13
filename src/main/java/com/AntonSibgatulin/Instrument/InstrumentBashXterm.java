package com.AntonSibgatulin.Instrument;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AntonSibgatulin.Anonymous.Anonymous;
import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.User.User;

import parse.ParseLink;

public class InstrumentBashXterm {
    public ParseLink parseLink = new ParseLink("");

    public DatabasConnect base = null;

    public InstrumentBashXterm(DatabasConnect base) {
        this.base = base;
    }

    public void m(String str) throws UnsupportedEncodingException {
        System.out.println("we");
        List<String> urlList = new ArrayList<String>();
        Pattern urlencod = Pattern.compile("\"url_encoded_fmt_stream_map\":\"([^\"]*)\"");
        Matcher urlencodMatch = urlencod.matcher(str);
        if (urlencodMatch.find()) {

            String url_encoded_fmt_stream_map;
            url_encoded_fmt_stream_map = urlencodMatch.group(1);
            D.bugss(url_encoded_fmt_stream_map);
            Pattern encod = Pattern.compile("url=(.*)");
            Matcher encodMatch = encod.matcher(url_encoded_fmt_stream_map);
            if (encodMatch.find()) {
                String sline = encodMatch.group(1);
                String[] urlStrings = sline.split("url=");
                for (String urlString : urlStrings) {
                    String url = null;
                    urlString = StringEscapeUtils.unescapeJava(urlString);
                    Pattern link = Pattern.compile("([^&,]*)[&,]");
                    Matcher linkMatch = link.matcher(urlString);
                    if (linkMatch.find()) {
                        url = linkMatch.group(1);
                        url = URLDecoder.decode(url, "utf-8");
                    }
                    D.bugss(urlString);
                    urlList.add(url);
                }
            }
        }

        for (String str5 : urlList) {
            D.bugss(str5);
        }
    }

    public String string() {
        HttpURLConnection conn = null;
        StringBuilder contents = new StringBuilder();
        try {
            conn = (HttpURLConnection) new URL("https://www.youtube.com/watch?v=5mAGn_grZKg").openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(8000);

            InputStream is = conn.getInputStream();

            String enc = conn.getContentEncoding();

            if (enc == null) {
                Pattern p = Pattern.compile("charset=(.*)");
                Matcher m = p.matcher(conn.getHeaderField("Content-Type"));
                if (m.find()) {
                    enc = m.group(1);
                }
            }

            if (enc == null)
                enc = "UTF-8";

            BufferedReader br = new BufferedReader(new InputStreamReader(is, enc));

            String line = null;


            while ((line = br.readLine()) != null) {
                contents.append(line);
                contents.append("\n");

            }
        } catch (IOException e) {

        }

        return contents.toString();
    }

    //AIzaSyBrnrOpaybydccMOILUc7B5YUGn8aYQIvA
//AIzaSyCr5Q3lIZIlT6FiVLazl63n0jCIlKz8kAc
    String key = "AIzaSyBrnrOpaybydccMOILUc7B5YUGn8aYQIvA";

    public String SearchVideoOnYoutube(String search_name) throws Exception {
        search_name = search_name.replaceAll(" ", "+");

        String string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + search_name + "&maxResults=200&type=video&key=" + key;

        URL url = new URL(string);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String main = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            main = main + line + "\n";

        }
        return main;
    }

    public String getDuration(String... ids) throws Exception {

        String id = "";
        for (int i = 0; i < ids.length; i++) {
            id += "&id=" + ids[i];
        }
        String string = "https://youtube.googleapis.com/youtube/v3/videos?part=contentDetails&part=statistics&key=" + key + "" + id;

        URL url = new URL(string);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String main = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            main = main + line + "\n";

        }
        return main;
    }


    public String SearchVideoOnYoutube2(String search_name) throws Exception {
        search_name = search_name.replaceAll(" ", "+");

        String string = "https://www.googleapis.com/youtube/v3/channels?forUsername=" + search_name + "&maxResults=20&key=" + key;

        URL url = new URL(string);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String main = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            main = main + line + "\n";

        }
        return main;
    }


    public JSONObject getObjectJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void getVideoLoad(JSONObject json, Anonymous conn) {
        String search = conn.search;
        try {
            JSONArray jsonArray = json.getJSONArray("items");


            int length = jsonArray.length();
            length = length / 50;
            for (int i = 0; i < length; i++) {
               // System.out.println("Index: "+(i)+" length: "+jsonArray.length() );
                String[] strings = new String[50];
                int index = 0;
                for (int l = i * 50; l < i * 50 + 50; l++) {
                     if(l>=jsonArray.length())break;
                    JSONObject jsonObject = jsonArray.getJSONObject(l);
                    JSONObject id = jsonObject.getJSONObject("id");
                    strings[index] = id.getString("videoId");

                    index++;
                }
                JSONObject jsonObject = new JSONObject(getDuration(strings));
                JSONArray jsonArray1 = jsonObject.getJSONArray("items");
                for(int k = 0;k<jsonArray1.length();k++){
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(k);

                    String duration = jsonObject1.getJSONObject("contentDetails").getString("duration");
                    long like = 0;
                    if(jsonObject1.getJSONObject("statistics").has("likeCount")){
                        like = jsonObject1.getJSONObject("statistics").getLong("likeCount");
                    }
                    long view = jsonObject1.getJSONObject("statistics").getLong("viewCount");
                    long commentCount = 0;
                    if(jsonObject1.getJSONObject("statistics").has("commentCount")){
                        commentCount = jsonObject1.getJSONObject("statistics").getLong("commentCount");
                    }
                    jsonArray.getJSONObject(i*50+k).put("duration",duration);
                    jsonArray.getJSONObject(i*50+k).put("like",like);
                    jsonArray.getJSONObject(i*50+k).put("view",view);
                    jsonArray.getJSONObject(i*50+k).put("commentCount",commentCount);

                }

            }


            for (int i = 0; i < jsonArray.length(); i++) {
                VideoLoad videoLoad = null;
                JSONObject jsonmain = new JSONObject();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject id = jsonObject.getJSONObject("id");
                String videoId = id.getString("videoId");
                JSONObject snippet = jsonObject.getJSONObject("snippet");
                String title = snippet.getString("title");
                String description = snippet.getString("description");

                String channelTitle = snippet.getString("channelTitle");
                JSONObject jsonObject2 = snippet.getJSONObject("thumbnails");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("high");
                System.out.println("index: "+i);

                String duration = jsonObject.getString("duration");
                long like = jsonObject.getLong("like");
                long view = jsonObject.getLong("view");
                long commentCount = jsonObject.getLong("commentCount");

                String url = jsonObject3.getString("url");
                if (!base.have_already(videoId)) {
                    jsonmain.put("title", title);
                    //jsonmain.put("image", url);
                    jsonmain.put("channame", channelTitle);
                    jsonmain.put("duration",duration);
                    //jsonmain.put("", value)
				/*new Thread(new Runnable() {
					
					@Override
					public void run() {
				*/
                    long id1 = 0;
                    try {
                        //JSONObject jsonObject1 = new JSONObject();
                        jsonmain.put("name", title);
                        jsonmain.put("image", url);
                        jsonmain.put("info", description);
                        jsonmain.put("view",view);
							/*	 URL url3 = new URL("https://downloader.freemake.com/api/videoinfo/"+videoId);
							 url3.openConnection();
							 
							 URL url2 = new URL("https://downloader.freemake.com/api/videoinfo/"+videoId);
								 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url2.openStream()));
							 String all ="";
							 String str = "";
							 while((str = bufferedReader.readLine())!=null){
								 all = all+str;
							 }
							 JSONObject jsonObject4 = new JSONObject(all);
							 JSONArray jsonArray2 = jsonObject4.getJSONArray("qualities");
							// for(int j = 0;j<jsonArray2.length();j++){
							 if(jsonArray2.length()>0){
								 JSONObject jsonObject5 = jsonArray2.getJSONObject(0);
								 String jsonlink = jsonObject5.getString("url");
								 jsonmain.put("link", jsonlink);
						 }*/
                        //String string1 = parseLink.getloadlinkm(videoId);
                        //Thread.sleep(50);
                        if (!conn.search.equals(search)) break;
						/*	String string = parseLink.sendPost111(videoId);
							JSONObject jsonobject = new JSONObject(string).getJSONObject("data");
							String link ="";
							String high ="";
							
							if(jsonobject.has("status")){
								D.bugss(jsonobject.getInt("status"));
								if(jsonobject.getInt("status")==200){
									JSONArray jsonobjectmain  = jsonobject.getJSONObject("data").getJSONObject("videos").getJSONArray("mp4");
									for(int x = 0;x<jsonobjectmain.length();x++){
										JSONObject jsonObject4 = jsonobjectmain.getJSONObject(x);
										if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("720p")){
											high = jsonObject4.getString("url");
										}
										if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("360p")){
											link = jsonObject4.getString("url");break;
										}
									}
								}
							}
							// String string = sendPost(videoId);
							// String der = getLink(string);
							// jsonmain.put("link", string);
							*/

                        id1 = base.addElementsVideofind(title, description, "", url, channelTitle, videoId,duration,like,view,commentCount);


                        jsonmain.put("id", id1);
                        JSONObject jsoup = new JSONObject();
                        jsoup.put("find", new JSONArray().put(jsonmain));
                        if (!conn.search.equals(search)) break;
                        if (!com.AntonSibgatulin.Socket.Socket.sendbol(conn.conn, "searchvideo;" + jsoup.toString()))
                            break;
                        //	jsonmain.put("post", jsonObject1);
                        //jsonmain.put("reallink",linkOnVideo(videoId));
                        D.bugss(jsonmain.toString());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //videoLoad = new VideoLoad(id1+"", videoId, channelTitle, "", description, channelTitle, "", url, jsonmain);
                    //videolist.add(videoLoad);
                    //}
                    //}).start();

                    D.bugss("title: " + title + ";def: " + url + ";high: " + url + ";channelTitle: " + channelTitle);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //return videolist;


    }


    public String linkOnVideo(String str) throws Exception {
        String cmd = "";
        if (str.contains("https://www.youtube.com/"))
            cmd = "youtube-dl --get-url " + str;
        else cmd = "youtube-dl --get-url https://www.youtube.com/watch?v=" + str;
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();


        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        //writer.flush();
        String line = null;
        while ((line = buf.readLine()) != null) {
            return line;
        }
        return line;

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getLink(String string) {
        String link = "";
        try {
            URL url = new URL(string.split("\"")[1]);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            connection.getInputStream();
            //BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //reader.wait(1000);
            link = connection.getURL().toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return link;
    }

    private final String USER__AGENT = "Mozilla/5.0";

    private String sendPost(String string) throws Exception {


        String url = "https://getvideo.org/get_video";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER__AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Cookie", "PHPSESSID=b5d2ihb79qe4dmejji61hdnln7; _ga=GA1.2.701402167.1610870241; _ym_uid=1610200270947595097; _ym_d=1610870241; _gid=GA1.2.1995800272.1612022711; _ym_visorc=w; _ym_isad=1; _gat=1");

        String urlParameters = "url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D" + string + "&ajax=1&token=636tZeynszRt";

        //Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        String response = "";

        while ((inputLine = in.readLine()) != null) {

            if (inputLine.contains("<a href=\"https://redirector.googlevideo.com/")) {
                D.bugss(inputLine);
                response = inputLine;
            }
            // response.append(inputLine+"\n");
        }
        in.close();
	    /*    String link = "";
			
				URL url1 = new URL(response.split("\"")[1]);
				URLConnection connection = url1.openConnection();
				connection.addRequestProperty("User-Agent", 
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				connection.getInputStream();
				//BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				//reader.wait(1000);
				link = connection.getURL().toString();
			*/
        //print result

        return response;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void getVideoLoad(JSONObject json, User conn, WebSocket con) {
        String search = conn.searchlist.get(con);
        try {
            JSONArray jsonArray = json.getJSONArray("items");

            int length = jsonArray.length();
            length = length / 50;
            for (int i = 0; i < length; i++) {
               // System.out.println("Index: "+(i)+" length: "+jsonArray.length() );
                String[] strings = new String[50];
                int index = 0;
                for (int l = i * 50; l < i * 50 + 50; l++) {
                    if(l>=jsonArray.length())break;
                    JSONObject jsonObject = jsonArray.getJSONObject(l);
                    JSONObject id = jsonObject.getJSONObject("id");
                    strings[index] = id.getString("videoId");

                    index++;
                }
                JSONObject jsonObject = new JSONObject(getDuration(strings));
                JSONArray jsonArray1 = jsonObject.getJSONArray("items");
                for(int k = 0;k<jsonArray1.length();k++){
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(k);

                    String duration = jsonObject1.getJSONObject("contentDetails").getString("duration");
                    long like = 0;
                    if(jsonObject1.getJSONObject("statistics").has("likeCount")){
                        like = jsonObject1.getJSONObject("statistics").getLong("likeCount");
                    }
                    long view = jsonObject1.getJSONObject("statistics").getLong("viewCount");
                    long commentCount = 0;
                    if(jsonObject1.getJSONObject("statistics").has("commentCount")){
                        commentCount = jsonObject1.getJSONObject("statistics").getLong("commentCount");
                    }
                    jsonArray.getJSONObject(i*50+k).put("duration",duration);
                    jsonArray.getJSONObject(i*50+k).put("like",like);
                    jsonArray.getJSONObject(i*50+k).put("view",view);
                    jsonArray.getJSONObject(i*50+k).put("commentCount",commentCount);

                }

            }

            for (int i = 0; i < jsonArray.length(); i++) {
                VideoLoad videoLoad = null;
                JSONObject jsonmain = new JSONObject();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject id = jsonObject.getJSONObject("id");
                String videoId = id.getString("videoId");
                JSONObject snippet = jsonObject.getJSONObject("snippet");
                String title = snippet.getString("title");
                String description = snippet.getString("description");

                String channelTitle = snippet.getString("channelTitle");
                JSONObject jsonObject2 = snippet.getJSONObject("thumbnails");
                JSONObject jsonObject3 = jsonObject2.getJSONObject("high");
                String url = jsonObject3.getString("url");

                String duration = jsonObject.getString("duration");
                long like = jsonObject.getLong("like");
                long view = jsonObject.getLong("view");

                long commentCount = jsonObject.getLong("commentCount");




                if (!base.have_already(videoId)) {
                    jsonmain.put("title", title);
                    //jsonmain.put("image", url);
                    jsonmain.put("channame", channelTitle);
                    jsonmain.put("duration",duration);
                    //jsonmain.put("", value)
					/*new Thread(new Runnable() {
						
						@Override
						public void run() {
					*/
                    long id1 = 0;
                    try {
                        //JSONObject jsonObject1 = new JSONObject();
                        jsonmain.put("name", title);
                        jsonmain.put("image", url);
                        jsonmain.put("info", description);
                        jsonmain.put("view",view);
                        jsonmain.put("timeofpublic",0);

								/*	 URL url3 = new URL("https://downloader.freemake.com/api/videoinfo/"+videoId);
								 url3.openConnection();
								 
								 URL url2 = new URL("https://downloader.freemake.com/api/videoinfo/"+videoId);
									 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url2.openStream()));
								 String all ="";
								 String str = "";
								 while((str = bufferedReader.readLine())!=null){
									 all = all+str;
								 }
								 JSONObject jsonObject4 = new JSONObject(all);
								 JSONArray jsonArray2 = jsonObject4.getJSONArray("qualities");
								// for(int j = 0;j<jsonArray2.length();j++){
								 if(jsonArray2.length()>0){
									 JSONObject jsonObject5 = jsonArray2.getJSONObject(0);
									 String jsonlink = jsonObject5.getString("url");
									 jsonmain.put("link", jsonlink);
							 }*/
                        //String string1 = parseLink.getloadlinkm(videoId);
                        //Thread.sleep(50);
                        if (!conn.searchlist.get(con).equals(search)) break;
                        //String string = parseLink.sendPost111(videoId);
								/*String link = "";
								String high = "";
								D.bugss(new	JSONObject(string));
								JSONObject jsonobject = new JSONObject(string).getJSONObject("data");
								
								if(jsonobject.has("status")){
									D.bugss(jsonobject.getInt("status"));
									if(jsonobject.getInt("status")==200){
										JSONArray jsonobjectmain  = jsonobject.getJSONObject("data").getJSONObject("videos").getJSONArray("mp4");
										for(int x = 0;x<jsonobjectmain.length();x++){
											JSONObject jsonObject4 = jsonobjectmain.getJSONObject(x);
											if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("720p")){
												high = jsonObject4.getString("url");
											}
											if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("360p")){
												link = jsonObject4.getString("url");break;
											}
										}
									}
								}
								*/
                        // String string = sendPost(videoId);
                        // String der = getLink(string);
                        // jsonmain.put("link", string);

                        id1 = base.addElementsVideofind(title, description, "", url, channelTitle, videoId,duration,like,view,commentCount);

                        jsonmain.put("id", id1);
                        JSONObject jsoup = new JSONObject();
                        jsoup.put("find", new JSONArray().put(jsonmain));
                        if (!conn.searchlist.get(con).equals(search)) break;
                        if (!com.AntonSibgatulin.Socket.Socket.sendbol(con, "searchvideo;" + jsoup.toString())) break;
                        //	jsonmain.put("post", jsonObject1);
                        //jsonmain.put("reallink",linkOnVideo(videoId));
                        D.bugss(jsonmain.toString());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //videoLoad = new VideoLoad(id1+"", videoId, channelTitle, "", description, channelTitle, "", url, jsonmain);
                    //videolist.add(videoLoad);
                    //}
                    //}).start();
                    D.bugss("title: " + title + ";def: " + url + ";high: " + url + ";channelTitle: " + channelTitle);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //return videolist;


    }
}
