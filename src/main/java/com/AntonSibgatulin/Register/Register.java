package com.AntonSibgatulin.Register;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.java_websocket.WebSocket;

import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Socket.Socket;

public class Register extends Thread {
    public int i = 0;
    antiDDoS DDoS = new antiDDoS();
  public  Map<Integer, Commands> comList = new HashMap<>();
    Set<WebSocket> list = new HashSet<WebSocket>();
    public DatabasConnect database;
    public Register(DatabasConnect database)
    {
        this.database=database;
    }
    public void removenull(){
        for(WebSocket sock : list){
            if(sock==null ||sock.isFlushAndClose()==true)
                list.remove(sock);
        }
    } public boolean getHaveWeb(WebSocket conns){
        removenull();
        for(WebSocket sock:list){
            if(sock.getRemoteSocketAddress().getHostName().equals(conns)){
return true;
            }
        }
return false;
    }
public boolean isValidLogin(String cmd){
    Pattern pat = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,10})([A-Za-z0-9])$");
if(cmd.length()>3&&cmd.length()<=10)
    return pat.matcher(cmd).matches();
else
    return false;
    }
    public boolean isValidPassword(String cmd){
        Pattern pat = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,10})([A-Za-z0-9])$");
        if(cmd.length()>=6&&cmd.length()<=20)
            return pat.matcher(cmd).matches();
        else
            return false;
    }
    public void getCreated(WebSocket con){
        Iterator i = comList.entrySet().iterator();
        while(i.hasNext()){
            Map.Entry pair = (Map.Entry)i.next();
            Commands com = (Commands)pair.getValue();
            if(com.socket!=null && com.socket.isFlushAndClose()==false){
            if(com.socket.getRemoteSocketAddress().getAddress().getHostAddress().equals(con.getRemoteSocketAddress().getAddress().getHostAddress())){
           //  System.out.println(com.socket.getRemoteSocketAddress().getAddress().getHostAddress());
                comList.remove(com.id).interrupt();
                break;
            }
        }else{
                comList.remove(com.id).interrupt();
                break;
            }
        }
    }
    public void executeCommands(String commands, WebSocket con) {
        long time = System.currentTimeMillis();
        //  Commands com = new Commands(commands,time,con);

        String[] c = commands.split(";");
        if (c.length >= 3){
            if (c[0].equals("register")) {
                if (c[1] != null && isValidLogin(c[1])) {
                    if (c[2] != null && isValidPassword(c[2])) {
                        int j = i++;
                        getCreated(con);
                            Commands com = new Commands(j, c[1] + ";" + c[2], time, con);
                            comList.put(j, com);

                    } else {
                        Socket.send(con,"game;unvalid;pass");
                    }
                } else {
                    Socket.send(con,"game;unvalid;login");
                }
            }else if(c[0].equals("captcha")){
                if(c[1].equals("id")){

                    Integer i = Integer.valueOf(c[2]);
                    Commands com = comList.getOrDefault(i,null);
                    if(com!=null){
                        if(c[3]!=null){
                            Integer a = Integer.valueOf(c[3]);
                                    com.getProverka(a);
                        }
                    }else{
                        Socket.send(con,"game;outcap");
                    }

                }
            }
    }
    }




    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
