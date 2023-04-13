package com.AntonSibgatulin.Groupe;

import com.AntonSibgatulin.Database.DatabasConnect;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Groupe {
public int id = 0;
public String creater = null;
public String[] Admin = null;
public String type = null;
public String[] participant = null;
public String name = null;
    public Groupe(int id ,String creater,String[] Admin,String name ,String type,String [] participant ){
    this.id = id ;
    this.creater = creater;
    this.Admin = Admin;
    this.type = type;
    this.name = name;
    this.participant = participant;

    }
    public String getMass(){
        JSONObject  json = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i =0 ;i<participant.length;i++)
                jsonArray.put(participant[i]);

            json.put("m",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }


    public String getAdmin(){
        JSONObject  json = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for(int i =0 ;i<Admin.length;i++)
                jsonArray.put(Admin[i]);

            json.put("m",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

}
