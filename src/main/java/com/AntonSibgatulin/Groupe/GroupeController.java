package com.AntonSibgatulin.Groupe;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Socket.Socket;
import org.java_websocket.WebSocket;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Random;

public class GroupeController {
    public DatabasConnect base =null;
    public GroupeController(DatabasConnect base ){
        this.base = base;
    }
    public void  loadGroupe(int id, WebSocket sock)  {
        ArrayList<Groupe> groupes = base.getGroupe(id);

        sendGroupe(groupes,sock);
    }


    public void  loadGroupe(String groupeid,WebSocket socket){
        ArrayList<Groupe> groupes = base.getGroupe(groupeid);

        sendGroupe(groupes,socket);
    }






    public void sendGroupe(ArrayList<Groupe> groupes,WebSocket conn){
        for(Groupe groupe:groupes) {
            try {
                Thread.sleep(200 + new Random().nextInt(500 + new Random().nextInt(400)));

                Socket.send(conn,"group;add;"+groupe.id+";"+groupe.name+";"+groupe.getMass()+";"+groupe.creater+";"+groupe.getAdmin());

            }catch (InterruptedException e){}


        }
        D.log("you got it");
    }
}
