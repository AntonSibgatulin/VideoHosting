package com.AntonSibgatulin.Video;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_table",schema = "public")
public class VideoModel {


    public String id = null;
    public String name = null;
    public Integer likes = 0;
    public Integer unlikes = 0;
    public String comment = null;
    public Long view = 0L;

    @Id
    @Column(name = "video_id")
    public Long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Long video_id) {
        this.video_id = video_id;
    }

    public Long video_id = 0L;
    public String files = null;
    public String image = null;
    public String chanel = null;
    public String idver= null;
    public Integer ban = 0;
    public Integer verif = 0;
    public String duration = null;
    public Long commentCount = 0L;
    public Long timeofcreate = 0L;

    public Long timeofpublic = 0L;



    public String link = null;



    public VideoModel(){

    }
    public VideoModel(String id,String name,String files,String link){

        this.id = id;
        this.name = name;
        this.files = files;
        this.link = link;

    }


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFiles() {
        return files;
    }

    public void setFiles(String namefile) {
        this.files = namefile;
    }


    @Column(name = "likes")
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Column(name = "unlikes")
    public Integer getUnlikes() {
        return unlikes;
    }

    public void setUnlikes(Integer unlikes) {
        this.unlikes = unlikes;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "view")
    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "chanel")
    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    @Column(name = "idver")
    public String getIdver() {
        return idver;
    }

    public void setIdver(String idver) {
        this.idver = idver;
    }

    @Column(name = "ban")
    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }

    @Column(name = "verif")
    public Integer getVerif() {
        return verif;
    }

    public void setVerif(Integer verif) {
        this.verif = verif;
    }

    @Column(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Column(name = "commentCount")
    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    @Column(name = "timeofcreate")
    public Long getTimeofcreate() {
        return timeofcreate;
    }

    public void setTimeofcreate(Long timeofcreate) {
        this.timeofcreate = timeofcreate;
    }

    @Column(name = "timeofpublic")
    public Long getTimeofpublic() {
        return timeofpublic;
    }

    public void setTimeofpublic(Long timeofpublic) {
        this.timeofpublic = timeofpublic;
    }



    @Override
    public String toString(){
        return "[VIDEO] ID: "+getVideo_id()+" Q: "+getId()+" Name: "+getName()+" DURATION: "+getDuration()+" AUTHOR: "+getChanel()+" ";
    }

}
