package com.AntonSibgatulin.Register;


import com.AntonSibgatulin.Main.Main;
import com.AntonSibgatulin.Main.Server;
import org.java_websocket.WebSocket;

import java.util.Random;

public class Commands extends Thread{
    int id;
    Captcha captcha = new Captcha(new Random().nextInt(100)+20);
    public WebSocket socket;
    public String[] data;
    private String str;
    private long time;
    public void sendCaptcha(){
        if(socket!=null){
            if(socket.isFlushAndClose()==false){
                socket.send("game;captcha;"+id+";"+captcha.captcha.getCaptchaString());
            }else{
               Main.controller.removeCommands(id);
            }
        }else{
        	Main.controller.removeCommands(id);

        }
    }

    public void sendAllright(){
        if(socket!=null){
            if(socket.isFlushAndClose()==false){
                socket.send("game;allright;"+id+";");
               // if(Main.controller.dat.registerUser(data[0],data[1])){
              //      socket.send("game;register;true;");

              //      Main.controller.removeCommands(id);
               // }else{
                 //   socket.send("game;register;false;");
                   // Main.controller.removeCommands(id);
                //}
            }else{
            	Main.controller.removeCommands(id);
            }
        }else{
        	Main.controller.removeCommands(id);

        }
    }
    int i =0;
public void getProverka(Integer i){
    if(i!=null){
        if(i==captcha.equally){
          sendAllright();
        }else{
            if(this.i>=3){
                if(socket.isFlushAndClose()==false){
                    socket.send("game;limit");
                }
                Main.controller.removeCommands(id);
            }else {
             if(socket.isFlushAndClose()==false) {
              socket.send("game;noncaptcha");
                this.i++;
             }else{
            	 Main.controller.removeCommands(id);

             }
            }
        }
    }

}
    public Commands(int id, String cmd, long time, WebSocket socket){
        this.str=cmd;
        this.id = id;
        this.time = time;
data = str.split(";");
        this.socket = socket;
        sendCaptcha();
        super.start();
    }
    public WebSocket getSocket(){
        return socket;
    }
    public long getTime(){
        return time;
    }
    public String getCmd(){
        return str;
    }

    @Override
    public void run(){
    while(true){
        try {
            if(isInterrupted()==true) {
                Thread.sleep(1000);
                if (System.currentTimeMillis() - time > 90*1000) {
                	Main.controller.removeCommands(id);
                }
            }else{
                break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
}
