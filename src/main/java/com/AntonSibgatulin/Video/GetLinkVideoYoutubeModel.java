package com.AntonSibgatulin.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.io.IOException;

public class GetLinkVideoYoutubeModel {
    public static String getText(String id) throws IOException, JSONException {
        URL url = new URL("https://ssyoutube.com/api/convert");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("authority", "ssyoutube.com");
        httpConn.setRequestProperty("sec-ch-ua", "\"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Microsoft Edge\";v=\"92\"");
        httpConn.setRequestProperty("accept", "application/json, text/plain, */*");
        httpConn.setRequestProperty("x-requested-with", "XMLHttpRequest");
        httpConn.setRequestProperty("sec-ch-ua-mobile", "?1");
        httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Mobile Safari/537.36 Edg/92.0.902.67");
        httpConn.setRequestProperty("content-type", "application/json");
        httpConn.setRequestProperty("origin", "https://ssyoutube.com");
        httpConn.setRequestProperty("sec-fetch-site", "same-origin");
        httpConn.setRequestProperty("sec-fetch-mode", "cors");
        httpConn.setRequestProperty("sec-fetch-dest", "empty");
        httpConn.setRequestProperty("referer", "https://ssyoutube.com/en334/");
        httpConn.setRequestProperty("accept-language", "ru,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,zh-CN;q=0.6,zh;q=0.5");
        httpConn.setRequestProperty("cookie", "uid=33ad980bb9ae18db; _ga=GA1.2.746086548.1667057630; push=5; outputStats=99; clickAds=51; laravel_session=eyJpdiI6IlBmamFVVjBsU1p0NklBUlZ1UU9peHc9PSIsInZhbHVlIjoiU25EQnBwY1dveE1kN3FHWUljRXNXa0ZUcW52cGhMbG1DMVQvcFRPYXZ3Z2pRU2JIbEZRM2MxaTdqcmJROEVlamZsR3pvR3BLL1QyR3RKQ2dZSXB5MkxsWWFtVGJJWmJvdEZDTy9BYS90aUVSOFdtNFJPRmJKN2R5UDdGd0MxMHkiLCJtYWMiOiI1ZTI1NTYxNDk4NGJlMmE0ZTFkMTdjMGY2ODQ4MGZlNzQ5YjdmYTNlZjgxNjE5Njc2ZTU0ZjhiNDUxNTUxZWEyIiwidGFnIjoiIn0%3D; _gid=GA1.2.572821982.1668769812; _gat_gtag_UA_125802910_4=1; televzrLanding=34");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\"url\":\"https://m.youtube.com/watch?v="+id+"\"}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("url");
        JSONObject mainJsonObject = new JSONObject();
        mainJsonObject.put("videoindex",jsonArray);
        //System.out.println(response);
        return mainJsonObject.toString();
    }
    public static void main(String[]args) throws Exception {
        long timestart = System.currentTimeMillis();
        getText("5uWFsQYqgAM");
       // System.out.println(System.currentTimeMillis()-timestart);
    }

}
