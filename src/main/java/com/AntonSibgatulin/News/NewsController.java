package com.AntonSibgatulin.News;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.User.User;
import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class NewsController {
    DatabasConnect link_on = null;

   public News createNews(int id ,String header ,String body,String atteachment,String link ,String photo){

       News news = new News (id ,header,body,atteachment,link,photo);

       return news;

   }
    public NewsController(DatabasConnect link_on){
       this.link_on = link_on;

    }

    public void getNews(WebSocket sock,int id ){

    }

    public void getNews(News news ,WebSocket sock){

    }
    public void getLenta(User socket){
        ArrayList<News> news = new ArrayList<>();

    }
}
