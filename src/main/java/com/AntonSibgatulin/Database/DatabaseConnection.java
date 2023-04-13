package com.AntonSibgatulin.Database;

import com.AntonSibgatulin.User.User;
import com.AntonSibgatulin.Video.VideoModel;
import com.AntonSibgatulin.Video.methods.VideoMethodModel;
import com.AntonSibgatulin.searching.GetSearchedVideoModel;
import com.AntonSibgatulin.searching.SearchingModel;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.File;

public class DatabaseConnection {



    public DatabaseConnection(){
        Session session = HibernateUtils.getSessionFactory().openSession();
    }
    public User getUser(String login,String password){
        Session session = HibernateUtils.getSessionFactory().openSession();

        User user = null;
        session.beginTransaction();
        Query query = session.createQuery("from User U WHERE U.login = :login AND U.password = :password");
        query.setString("login",login);
        query.setString("password",password);
        user = (User) query.uniqueResult();
        session.getTransaction().commit();
       return user;
    }


    public VideoModel getVideoModel(String id){
        Session session = HibernateUtils.getSessionFactory().openSession();

        VideoModel videoModel = null;
        session.beginTransaction();
        Query query = session.createQuery("from VideoModel v WHERE id=:id");
        query.setString("id",id);
        videoModel = (VideoModel) query.uniqueResult();

        session.getTransaction().commit();


        return videoModel;
    }


    public void saveOrUpdateVideoModel(VideoModel videoModel){
        if(videoModel==null){
            return;
        }
        //save in the database
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(videoModel);
        session.getTransaction().commit();

    }

    public void saveSearching(String header,Long id){
        SearchingModel searchingModel = new SearchingModel();
        searchingModel.setHeader(header);
        searchingModel.setId_user(id);
        searchingModel.setId(0L);

        //save in the database
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
               session.saveOrUpdate(searchingModel);
        session.getTransaction().commit();

    }
    public void saveVideoViewed(Long video_id,Long user_id){
        Long time = System.currentTimeMillis();
        GetSearchedVideoModel getSearchedVideoModel = new GetSearchedVideoModel();
        getSearchedVideoModel.setId(0L);
        getSearchedVideoModel.setVideo_id(video_id);
        getSearchedVideoModel.setUser_id(user_id);
        getSearchedVideoModel.setDate(time);



        //save in the database
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        session.saveOrUpdate(getSearchedVideoModel);
        session.getTransaction().commit();
    }


    public void saveLikeOrDislikeVideo(Long video_id,Long user_id,Integer type){

        VideoMethodModel videoMethodModel = new VideoMethodModel();
        videoMethodModel.setId(System.currentTimeMillis());
        Long time = System.currentTimeMillis();
        videoMethodModel.setId(time);
        videoMethodModel.setVideo_id(video_id);
        videoMethodModel.setUser_id(user_id);
        videoMethodModel.setType(type);
        videoMethodModel.setDate(time);

        //save in the database
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(videoMethodModel);
        session.getTransaction().commit();

    }

    public static void main(String...args){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        //databaseConnection.saveLikeOrDislikeVideo(100L,1L,1);
       // databaseConnection.saveOrUpdateGetSearchedVideoModel(1L,2L);
        //databaseConnection.saveOrUpdateSearchingModel("Математика",-1L);
        //System.out.println(databaseConnection.getVideoModel("500000").toString());
    }
}
