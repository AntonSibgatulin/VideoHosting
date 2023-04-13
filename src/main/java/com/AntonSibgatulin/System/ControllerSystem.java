package com.AntonSibgatulin.System;

import org.java_websocket.WebSocket;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.Register.Register;

public class ControllerSystem {
    //public  Map<String, Maps> mapsMap = new HashMap<>();

    public void executeCommands(String str, WebSocket conn){
        reg.executeCommands(str,conn);
    }
    Register reg ;
    public int i =0;
    public Server web;


    public void removeCommands(Integer id){
     //   reg.comList.remove(id).interrupt();

    }
  /*  public void remove(Table  table){

      //  System.out.println("controller yes");

        if(table!=null) {
            table.t.stop();
            if(table.isInterrupted()==false)
            table.interrupt();
         //   System.out.println("don't == null or 0 or false");
            web.tableListTest.remove(table);
            web.exit(table.id);
        }
    }

   */
    public DatabasConnect dat;
    public ControllerSystem(Server web, Register reg){
this.web = web;
this.reg = reg;
this.dat = web.base;
    }
}
