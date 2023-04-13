package ru.brightstudiogamedev.serversidesnapshot;

import com.AntonSibgatulin.Coin.CoinRubles;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Main.Server;
import com.AntonSibgatulin.Register.Register;
import com.AntonSibgatulin.System.ControllerSystem;
import com.AntonSibgatulin.httpproto.Directory;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

@SpringBootApplication
public class ServerSideSnapshotApplication  /*extends SpringBootServletInitializer */{
   /* private static final Logger log = Logger
            .getLogger(SpringBootServletInitializer.class);
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        log.trace("Initializing the application");
        return builder.sources(ServerSideSnapshotApplication .class);
    }

    */

    public static String ip  =null;
    public static CoinRubles coinRubles = null;
    public static ControllerSystem controller = null;
    public static Register register = null;
    public static Server server = null;
    //public static Object controller;
    public static Directory directory = null;

    public static void main(String[] args) {

        //Session session = HibernateUtil.getSessionFactory().openSession();

        coinRubles = new CoinRubles();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("configure/socket.cfg")));
            String string = "";
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                string = string + str;
            }
            JSONObject jsonObject = new JSONObject(string);
            //directory = new Directory("html",jsonObject.getJSONArray("systemfolder"));
            ip = InetAddress.getLocalHost().getHostAddress().toString();
            server = new Server(ip, jsonObject.getInt("port"));
            server.start();
            register = new Register(server.base);
            register.start();
            controller = new ControllerSystem(server, register);
			/* new Thread(new Runnable() {

				@Override
				public void run() {
					ServerSocket ss = null;
					try {
						//System.out.println(""+InetAddress.getLocalHost().getHostAddress().toString());
						InetAddress addr = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress().toString());
						ss = new ServerSocket(80,1000,addr);
						System.err.println("all files loaded "+addr.toString());
						while (true) {
							Socket s = ss.accept();
							//System.err.println("Client accepted");
							new Thread(new SocketProcessor(s,directory,server)).start();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}).start();

			 */
        } catch (UnknownHostException | JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(ServerSideSnapshotApplication.class, args);


        System.out.println("Server was started on "+ip+":"+server.getPort());
        Scanner scanner = new Scanner(System.in);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    String string = scanner.nextLine();
                    execute(string, directory, server);

                }

            }
        }).start();

    }

    public static void execute(String command, Directory dir, Server server) {
        String[] args = command.split(" ");
        if (args.length > 0) {
            if (args[0].equals("reload")) {
                String namefile = args[1].startsWith("/") == true ? "" + args[1] : "/" + args[1];
                dir.reloadfile(namefile);

            } else if (args[0].equals("restart")) {
                if (args[1].equals("websocket")) {
                    for (WebSocket socket : server.conns) {
                        socket.close();
                    }
                }
            }
            D.log(command);
        }

    }
}
