package com.AntonSibgatulin.Video;

public class VideoControllerModel {
    public String id = null;
    public String name = null;
    public long likes = 0;
    public long unlikes = 0;
    public String comment = null;
    public long view = 0;
    public String image = null;
    public String chanel = null;
    public int ban = 0;
    public VideoControllerModel(String id,String name,int likes,int unlikes,String comment,long view,String image,String chanel){
        this.id = id;
        this.name = name;
        this.likes = likes;
        this.unlikes = unlikes;
        this.comment = comment;
        this.view = view;
        this.image = image;
        this.chanel = chanel;

    }
}
