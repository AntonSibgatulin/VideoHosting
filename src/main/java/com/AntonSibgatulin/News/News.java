package com.AntonSibgatulin.News;

import java.util.ArrayList;

public class News {
    public int  id = 0;
    public String header = null;
    public String body = null;
    public String atteachment = null;
    public String link = null;
    public String photo = null;

    public News (int id ,String Header ,String body,String atteachment,String link,String photo){
        if(id < 0)return;
        this.id = id;
        this.header = header ;
        this.atteachment = atteachment;
        this.body = body;
        this.link = link;
        this.photo = photo;


    }


}
