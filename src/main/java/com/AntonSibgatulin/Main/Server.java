package com.AntonSibgatulin.Main;

import java.awt.SecondaryLoop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;
import javax.management.monitor.MonitorSettingException;
import javax.swing.JOptionPane;
import javax.swing.plaf.synth.Region;

import com.AntonSibgatulin.Database.DatabaseConnection;
import com.AntonSibgatulin.Groupe.GroupeController;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.AntonSibgatulin.Anonymous.Anonymous;
import com.AntonSibgatulin.AuthKey.GenerateAuthKey;
import com.AntonSibgatulin.Database.DatabasConnect;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Device.Device;
import com.AntonSibgatulin.Money.Money;
import com.AntonSibgatulin.Money.PurseModel;
import com.AntonSibgatulin.User.TypeUser;
import com.AntonSibgatulin.User.User;
import com.AntonSibgatulin.Video.VideoServerController;

public class Server extends
        WebSocketServer/* implements com.AntonSibgatulin.Socket.Socket */ {


    public GenerateAuthKey generateAuthKey = new GenerateAuthKey();
    public DatabaseConnection databaseConnection = new DatabaseConnection();
    public DatabasConnect base = new DatabasConnect(databaseConnection);

    public VideoServerController controller = new VideoServerController(base, this);

    public User authification(String name, String auth, String authification, String decode) {

        User user = keyuser.get(name);
        if (user != null) {
            D.log("auth cookie");
            if (user.auth_key.get(authification) != null && user.decode.equals(decode) && user.Auth.equals(auth)) {

            } else
                user = null;
        }
        return user;
    }

    String code = "fDertMertnsdf2sd";
    public GroupeController groupeController = new GroupeController(base);
    public Map<Device, User> DeviceList = new HashMap<>();
    public Map<String, Device> SocketList = new HashMap<>();
    public Map<String, User> UserList = new HashMap<>();
    public Map<Long, User> IDUser = new HashMap<>();
    public Map<String, User> online = new HashMap<>();
    public Map<String, PurseModel> purseWork = new HashMap<>();
    public Map<String, User> keyuser = new HashMap<>();
    public Map<WebSocket, Anonymous> anonymous = new HashMap<>();

    Server webserverLists = this;
    public Money money = new Money(this);
    // public DatabasConnect connectionDB = new DatabasConnect();
    private static int TCP_PORT = 4445;
    int j = 0;

    public boolean Auth() {

        // webserverLists.setSsl(tru;)
        return false;

    }

    // Game g = new Game();
    public void move() {

    }

    /*
     * public static void main(String[]args) throws
     * InterruptedException,IOException{ Scanner scan = new Scanner(System.in);
     * BufferedReader bufferedReader = new BufferedReader(new FileReader(new
     * File("configure/price.cfg"))); String string = ""; String gString = "";
     * while((gString = bufferedReader.readLine())!=null){ string
     * =string+gString; } bufferedReader.close(); int price =
     * Integer.parseInt(string); BufferedWriter bufferedWriter = new
     * BufferedWriter(new FileWriter(new File("configure/price.cfg")));
     *
     * D.log("Write line command without sudo"); String line = scan.nextLine();
     * if(line.equals("start")){ D.log("Взлом ЖКХ Ульяновской области"); for(int
     * i =0;i<=100;i++){ if(i<70) Thread.sleep(55+new Random().nextInt(50));
     * D.log("Взлом ЖКХ Ульяновской области - "+i+"%"); }
     * JOptionPane.showConfirmDialog(null,
     * "цена на газ за куб.мт. в Ульяновской области "+price+" рублей"); String
     * str = JOptionPane.
     * showInputDialog("введите желаемую цену на газ в Ульяновской области?");
     * JOptionPane.showConfirmDialog(null,
     * "цена в "+str+" рублей за куб.мт.газа сохранена"); for(int j =
     * 0;j<=100;j++){ Thread.sleep(100);
     * D.log("Сохранение цены ЖКХ в Ульяновской области "+j+"%");
     *
     * } for(int j = 0;j<=100;j++){ Thread.sleep(350+new Random().nextInt(250));
     * D.log("Загрузка цены ЖКХ в Ульяновской области "+j+"%");
     *
     * } JOptionPane.showConfirmDialog(null,
     * "Зугрузка цены  в "+str+" рублей за куб.мт.газа завершена");
     * JOptionPane.showConfirmDialog(null,
     * "В ближайшее время абоненты получат новые квитанции!!!");
     * bufferedWriter.write(str); bufferedWriter.close(); }
     *
     * }
     */
    public Set<WebSocket> conns;

    // public Map<String,User> userList = new HashMap<>();
    // List<Table> tableList = new ArrayList<Table>();
    // public Map<String,Table> boardList = new HashMap<String, Table>();
    public Server(String str, int i) throws UnknownHostException {

        super(new InetSocketAddress(InetAddress.getByName(str), i));

        conns = new HashSet<WebSocket>();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

        String info = conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                + conn.getRemoteSocketAddress().getPort();
        Device device = SocketList.get(info);
        this.anonymous.remove(conn);
        if (device != null) {
            device.list.remove(conn);
            SocketList.remove(info);

        } else {

        }
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        D.bugss1(message);
        // System.out.println(message);
        String info = conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                + conn.getRemoteSocketAddress().getPort();
        new Thread(new Runnable() {
            @Override
            public void run() {
                D.log("message: " + message);
                String[] str = message.split(";");
                if (str[0].equals("authcookie")) {
                    if (str.length >= 5) {
                    } else {
                        D.bugss("error3");
                        com.AntonSibgatulin.Socket.Socket.send(conn, "authcookie;false");
                        return;

                    }
                    D.log("auth cookie");
                    String auth = str[2];
                    String authk = str[3];
                    String deoced = str[4];

                    String login = str[1];
                    User user = keyuser.get(login);
                    if (user != null) {
                        D.log("auth cookie");
                        if (user.auth_key.get(authk) != null && user.decode.equals(deoced) && user.Auth.equals(auth)) {
                            D.log("auth cookie");
                            com.AntonSibgatulin.AuthKey.Auth auth1 = Server.this.generateAuthKey.GenerateAuthKey();

                            com.AntonSibgatulin.Socket.Socket.send(conn, "Экзэмпляр уже был создан!");
                            user.device.list.add(conn);
                            // device.list.add(conn);


                            anonymous.remove(conn);

                            com.AntonSibgatulin.Socket.Socket.send(conn,
                                    "auth;true;" + user.id + ";" + user.Auth + ";" + authk + ";" + user.decode + ";"
                                            + user.login + ";" + user.name + ";" + user.surname);
                            JSONObject jsonObject1 = base.getTables(user.id);
                            com.AntonSibgatulin.Socket.Socket.send(conn, "dilogid;" + jsonObject1.toString());


                            SocketList.put(info, user.device);


                        } else {
                            D.bugss("error1");
                            com.AntonSibgatulin.Socket.Socket.send(conn, "authcookie;false");
                        }
                    } else {
                        D.bugss("error2");
                        com.AntonSibgatulin.Socket.Socket.send(conn, "authcookie;false");
                    }
                }

                if (str[0].equals("auth") && str.length >= 3) {
                    if (str[1] != null && str[2] != null) {
                        User user = base.getUser(str[1], str[2]);

                        if (user == null) {
                            com.AntonSibgatulin.Socket.Socket.send(conn, "auth;false");

                        } else {
                            User t = null;
                            com.AntonSibgatulin.AuthKey.Auth auth = Server.this.generateAuthKey.GenerateAuthKey();

                            if ((t = UserList.get(user.login + ";" + user.password)) == null) {
                                com.AntonSibgatulin.Socket.Socket.send(conn, "Экземпляр создаётся.");
                                Device device = new Device();
                                device.list.add(conn);
                                user.device = device;
                                user.auth_key.put(auth.Authificationkey, user);
                                DeviceList.put(device, user);
                                SocketList.put(info, device);
                                UserList.put(user.login + ";" + user.password, user);
                                IDUser.put(user.id, user);
                                user.Auth = auth.Auth;
                                user.decode = auth.decodekey;

                                online.put(user.login, user);
                                keyuser.put(user.login, user);

                            } else {
                                com.AntonSibgatulin.Socket.Socket.send(conn, "Экзэмпляр уже был создан!");
                                User suUser = IDUser.get(t.id);
                                suUser.device.list.add(conn);
                                // device.list.add(conn);
                                user.auth_key.put(auth.Authificationkey, user);
                                SocketList.put(info, suUser.device);

                            }
                            com.AntonSibgatulin.Socket.Socket.send(conn,
                                    "auth;true;" + user.id + ";" + user.Auth + ";" + auth.Authificationkey + ";"
                                            + user.decode + ";" + user.login + ";" + user.name + ";" + user.surname);
                            JSONObject jsonObject = base.getTables(user.id);
                            com.AntonSibgatulin.Socket.Socket.send(conn, "dilogid;" + jsonObject.toString());

                            anonymous.remove(conn);

                        }
                    } else {
                        com.AntonSibgatulin.Socket.Socket.send(conn, "Unknow error!");
                    }
                } else if (str[0].equals("register")) {
                    Main.controller.executeCommands(message, conn);
                } else if (str[0].equals("captcha")) {
                    Main.controller.executeCommands(message, conn);

                } else if (str[0].equals("lenta")) {
                    Integer value = Integer.valueOf(str[1]);
                    base.getLenta(value, (conn));
                }
                else if (str[0].equals("searchvideo")) {
                    User user = DeviceList
                            .get(SocketList.get(conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                                    + conn.getRemoteSocketAddress().getPort()));

                    Anonymous any = anonymous.get(conn);

                    JSONObject ob = null;
                    String findvideo = null;
                    try {
                        ob = new JSONObject(message.replaceFirst("searchvideo;", ""));
                        findvideo = ob.getString("search");

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (findvideo != null) {
                        base.databaseConnection.saveSearching(findvideo, user == null ? -1 : user.getId());
                        if (!findvideo.startsWith("$")) {
                            if (!findvideo.equals(findvideo.replace("'", "").replace('"', ' ').replace(".", ""))) {
                                conn.close();
                                return;
                            }

                            if (any != null) {
                                JSONObject info = base.getsearchvideo(findvideo, any);
                            } else if (user != null) {
                                if (findvideo == null || findvideo.isEmpty()) {
                                    findvideo = "Занимательная математика!";
                                }
                                JSONObject info = base.getsearchvideo(findvideo, user, conn);
                            }
                        } else {

                            base.getvideofromchan(findvideo.replaceFirst("$", ""), user, conn);

                        }

                    }

                }
                else if (str[0].equals("getvideochan")) {
                    User user = DeviceList
                            .get(SocketList.get(conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                                    + conn.getRemoteSocketAddress().getPort()));

                    String stringarg = message.replaceFirst("getvideochan;", "");
                    JSONObject json = null;
                    try {
                        D.bugss2(stringarg);
                        json = new JSONObject(stringarg);
                        D.bugss2(json);
                        String getname = base.getNameChannel(json.getString("videoid"));
                        if (getname != null) {
                            D.bugss2(getname);
                            base.getvideofromchan(getname, user, conn);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                else if (str[0].equals("getvideo")) {
                    // registrationView
                    String string = str[1];
                    User user = DeviceList
                            .get(SocketList.get(conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                                    + conn.getRemoteSocketAddress().getPort()));

                    JSONObject jsonObject = base.getVideoForSiteInfo(string, user);
                    System.out.println(jsonObject.toString());
                    if (jsonObject != null) {
                        try {
                            jsonObject.put("typelike", 0);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    JSONObject jsonObject2 = null;
                    if (jsonObject.has("link") && user != null) {
                        jsonObject2 = base.getActionWithVideo(user.login, string);
                        base.registrationView(user.login, string, jsonObject2);
                        if (jsonObject2 != null) {
                            try {
                                jsonObject.put("typelike", jsonObject2.getInt("type"));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    }

                    com.AntonSibgatulin.Socket.Socket.send(conn, "addlenta;" + jsonObject.toString());

                }
                else if (str[0].equals("getTrend")) {
                    // registrationView

                    User user = DeviceList
                            .get(SocketList.get(conn.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                                    + conn.getRemoteSocketAddress().getPort()));
                    try {
                        base.getTrends(user, conn);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                else {
                    if (str[0].equals("exit")) {
                        System.exit(0);
                    }
                    D.log(info);
                    Device device = SocketList.get(info);

                    if (device != null) {
                        D.log("allright");
                        User user = DeviceList.get(device);
                        if (user != null) {
                            if (str[0].equals("getmyprofile")) {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("myid", user.id);
                                    jsonObject.put("name", user.name);
                                    jsonObject.put("surname", user.surname);
                                    jsonObject.put("city", user.city);
                                    jsonObject.put("email", user.mail);
                                    jsonObject.put("date", user.date);
                                    jsonObject.put("typeuser", user.typeuser);
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                com.AntonSibgatulin.Socket.Socket.send(conn, "setmyprofile;" + jsonObject.toString());
                            } else if (str[0].equals("videosearch")) {

                            } else if (str[0].equals("like_video")) {
                                if (user != null) {
                                    try {
                                        JSONObject ma = base.getActionWithVideo(user.login, str[2]);
                                        if (ma != null && ma.getInt("type") == 0) {
                                            base.setLikeUpdateLike(user.login, str[2], 1);
                                            base.updatelike(str[2], 1);
                                            int[] lll = base.getLikes(str[2]);
                                            com.AntonSibgatulin.Socket.Socket.send(conn,
                                                    "setlike;1;" + lll[0] + ";" + lll[1] + ";" + str[2]);

                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            } else if (str[0].equals("dislike_video")) {
                                if (user != null) {
                                    D.bugss1(str[2]);
                                    try {
                                        JSONObject ma = base.getActionWithVideo(user.login, str[2]);
                                        if (ma != null && ma.getInt("type") == 0) {
                                            base.setLikeUpdateLike(user.login, str[2], 2);
                                            base.updatelike(str[2], 2);
                                            int[] lll = base.getLikes(str[2]);
                                            com.AntonSibgatulin.Socket.Socket.send(conn,
                                                    "setlike;2;" + lll[0] + ";" + lll[1] + ";" + str[2]);
                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            } else if (str[0].equals("getprofile")) {
                                if (str[1].matches("[-+]?\\d+")) {
                                    D.log(str[1] + " get profile");
                                    ///

                                    User user_get = null;// IDUser.get(Integer.parseInt(str[1]));
                                    if (user_get != null) {

                                    } else {
                                        JSONObject object = base.getUserId(Integer.parseInt(str[1]));
                                        D.log("obj " + object.toString());
                                        JSONObject jsonObject = new JSONObject();
                                        String[] setting = null;
                                        try {
                                            setting = object.getString("setting").split(";");
                                        } catch (JSONException e1) {
                                            // TODO Auto-generated catch block
                                            e1.printStackTrace();
                                        }
                                        try {
                                            jsonObject.put("id", object.get("id"));
                                            jsonObject.put("name", object.get("name"));
                                            jsonObject.put("surname", object.get("surname"));
                                            if (Integer.parseInt(setting[0]) == 1)
                                                jsonObject.put("city", object.get("city"));
                                            else {
                                                jsonObject.put("city", "error access");

                                            }
                                            if (Integer.parseInt(setting[1]) == 1)
                                                jsonObject.put("email", object.get("email"));
                                            else {
                                                jsonObject.put("email", "error access");

                                            }
                                            if (Integer.parseInt(setting[2]) == 1)
                                                jsonObject.put("date", object.get("date"));
                                            else {
                                                jsonObject.put("date", "error access");

                                            }
                                            jsonObject.put("login", "@" + object.getString("login"));
                                            jsonObject.put("typeuser", object.get("i"));
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        com.AntonSibgatulin.Socket.Socket.send(conn,
                                                "answerprofile;" + jsonObject.toString());

                                    }

                                }

                                ///
                            } else if (str[0].equals("findgroupe")) {
                                if (str[1] != null) {
                                    if (str[1].matches("[-+]?\\d+")) {
                                        int i = Integer.getInteger(str[1]);
                                        groupeController.loadGroupe(i, conn);
                                    } else {
                                        groupeController.loadGroupe(str[1], conn);
                                    }
                                } else {
                                    conn.close();
                                }
                            } else if (str[0].equals("News")) {
                                user.getGroupe(conn);
                            } else if (str[0].equals("ban_video")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {
                                    D.bugss2("Admin ban on video " + str[1]);
                                    base.banvideo(str[1]);
                                }

                            } else if (str[0].equals("unban_video")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {
                                    base.unbanvideo(str[1]);
                                }

                            } else if (str[0].equals("adddislike")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {
                                    if (str[2].matches("[-+]?\\d+"))
                                        base.addDISLIKEORlike(str[1], 2, Integer.parseInt(str[2]));
                                }

                            } else if (str[0].equals("getvideochan")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {

                                    // base.addDISLIKEORlike(str[1],1,Integer.parseInt(str[2]));
                                }

                            } else if (str[0].equals("addlike")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {
                                    if (str[2].matches("[-+]?\\d+"))
                                        base.addDISLIKEORlike(str[1], 1, Integer.parseInt(str[2]));
                                }

                            } else if (str[0].equals("addview")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {
                                    if (str[2].matches("[-+]?\\d+"))
                                        base.addviewonvideo(str[1], 1, Integer.parseInt(str[2]));
                                }

                            } else if (str[0].equals("ban_chan")) {

                                if (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR) {

                                }

                            } else if (str[0].equals("sendmessage")) {
                                if (str[1].matches("[-+]?\\d+")) {

                                }
                                String mess = "";
                                // String mess = "";
                                Integer idto = 0;
                                try {

                                    D.log(message);
                                    String string = message.replace("sendmessage;", "");
                                    if (string.length() >= 4025)
                                        return;
                                    string = string.replaceAll("'", "&#039;").replaceAll("<", "&lt;").replaceAll(">",
                                            "&gt;");
                                    D.log(string);

                                    JSONObject jsonObject = new JSONObject(string);
                                    if (jsonObject.has("idto") && jsonObject.has("message")) {
                                        D.log(jsonObject.getString("message"));
                                        mess = jsonObject.getString("message");
                                        idto = jsonObject.getInt("idto");
                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                long time = System.currentTimeMillis();
                                if (idto > 0 && mess != null && mess.length() > 0) {
                                    User user2 = IDUser.get(idto);
                                    if (user2 != null) {

                                        long mesid = base.registerMessage(user.id, user2.id, mess);
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("message", mess);
                                            jsonObject.put("idfrom", user.id);
                                            jsonObject.put("time", time);
                                            jsonObject.put("messageid", mesid);
                                            jsonObject.put("idto", user2.id);
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }

                                        for (WebSocket s : user2.device.list) {
                                            com.AntonSibgatulin.Socket.Socket.send(s,
                                                    "newmessage;" + jsonObject.toString());
                                        }
                                        if (user.id != user2.id)
                                            for (WebSocket s : user.device.list) {
                                                com.AntonSibgatulin.Socket.Socket.send(s,
                                                        "newmessage;" + jsonObject.toString());
                                            }
                                    } else {
                                        if (base.getUserMes(idto) != null) {
                                            long mesid = base.registerMessage(user.id, idto, mess);
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("message", mess);
                                                jsonObject.put("idfrom", user.id);
                                                jsonObject.put("idto", idto);
                                                jsonObject.put("time", time);
                                                jsonObject.put("messageid", mesid);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }
                                            for (WebSocket s : user.device.list) {
                                                com.AntonSibgatulin.Socket.Socket.send(s,
                                                        "newmessage;" + "" + jsonObject.toString());
                                            }
                                        } else {
                                            // com.AntonSibgatulin.Socket.Socket.send(conn,
                                            // "ты чо сука ,самый умный что ли
                                            // педераст?");

                                        }
                                    }
                                }

                            } else if (str[0].equals("gethistorymessage") && str[1] != null && str[1] != "") {
                                if (str[1].matches("[-+]?\\d+")) {
                                    long i = Integer.parseInt(str[1]);

                                    try {
                                        JSONObject jsonObject1 = base.getUserId(i);
                                        if ((IDUser.get(i)) != null
                                                || jsonObject1 != null && jsonObject1.getLong("id") == i) {
                                            if (jsonObject1.has("login")) {
                                                JSONObject jsonObject = base.getMessageHistory(user.id, i);
                                                // jsonObject.put("loginto",
                                                // jsonObject1.getString("login"));
                                                com.AntonSibgatulin.Socket.Socket.send(conn,
                                                        "history;" + i + ";" + jsonObject1.getString("login") + ";"
                                                                + code + "" + jsonObject.toString());
                                            }
                                        }
                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }
                                }
                            } else if (str[0].equals("searchlogin") && str.length > 1) {
                                String name = str[1];
                                if (str[1].toCharArray()[0] == '@') {
                                    name = str[1].replace("@", "");
                                }
                                System.out.println(message);

                                JSONObject jsonObject = base.getUserId(name);
                                JSONObject jsonObject2 = new JSONObject();
                                System.out.println(jsonObject);
                                D.log(jsonObject);
                                if (jsonObject.has("login")) {
                                    try {
                                        jsonObject2.put("id", jsonObject.getInt("id"));
                                        jsonObject2.put("login", "@" + jsonObject.getString("login"));

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    com.AntonSibgatulin.Socket.Socket.send(conn, "addDilogId;" + jsonObject2);
                                } else {
                                    com.AntonSibgatulin.Socket.Socket.send(conn, "errorfound;0");

                                }
                            } else if (str[0].equals("moneyoperation") && str.length > 1) {

                                money.execute(user, str);
                            }
                        }
                    }
                }

                /*
                 * for(WebSocket sock:conns){ sock.send(message); }
                 */

            }

        }).start();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }

        System.out.println("ERROR from " + ex + "  " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onOpen(WebSocket arg0, ClientHandshake arg1) {
        conns.add(arg0);
        Anonymous anonymous = new Anonymous(arg0.getRemoteSocketAddress().getAddress().getHostAddress(), arg0);
        this.anonymous.put(arg0, anonymous);
        // getTable(conn);
        System.out.println("New connection from " + arg0.getRemoteSocketAddress().getAddress().getHostAddress() + ":"
                + arg0.getRemoteSocketAddress().getPort());

    }

}
