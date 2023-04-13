package com.AntonSibgatulin.Database;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import com.AntonSibgatulin.Video.*;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.AntonSibgatulin.Anonymous.Anonymous;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Groupe.Groupe;
import com.AntonSibgatulin.Instrument.InstrumentBashXterm;
import com.AntonSibgatulin.Money.PurseModel;
import com.AntonSibgatulin.Socket.Socket;
import com.AntonSibgatulin.User.TypeUser;
import com.AntonSibgatulin.User.User;

public class DatabasConnect {
    InstrumentBashXterm instrument = new InstrumentBashXterm(this);
    //  public Object userList;
    /**
     * @author AntonSibgatulin
     */

    ResultSet resultSet;
    int res;
    Connection connection = null;
    Statement statement = null;

    public DatabaseConnection databaseConnection = null;




    public DatabasConnect(DatabaseConnection databaseConnection){
            this.databaseConnection = databaseConnection;
        System.out.println("Registering JDBC driver...");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {

        }
        try {
            System.out.println("Creating database connection...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Executing statement...");
            statement = connection.createStatement();
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }


    public DatabasConnect() {
        System.out.println("Registering JDBC driver...");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Creating database connection...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            System.out.println("Executing statement...");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * JDBC Driver and database url
     */
//com.mysql.cj.jdbc.Driver
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://127.0.0.1/base?characterEncoding=utf8&serverTimezone=UTC";

    /**
     * User and Password
     */
    static final String USER = "root";
    static final String PASSWORD = "Dert869$$";

    public boolean banIp(WebSocket sock, String login) {
        String cmd = "INSERT INTO `blackip` (`id`, `ip`) VALUES (NULL, '" + sock.getRemoteSocketAddress().getAddress().getHostAddress() + "');";

        try {
            res = statement.executeUpdate(cmd);
            cmd = "UPDATE `users` SET `ban` = '2' WHERE `users`.`login` = '" + login + "';";
            res = statement.executeUpdate(cmd);

            return true;
        } catch (SQLException throwables) {
            return false;
        }


    }

    public ArrayList<Groupe> getGroupe(int id) {
        String string = "SELECT * FROM `groupe` WHERE `id` = " + id + ";";

        return getGroupeSQL(string);
    }

    public ArrayList<Groupe> getGroupe(String string) {
        String str = "SELECT * FROM `groupe` WHERE `name` LIKE '%" + string + "%';";
        return getGroupeSQL(str);
    }

    public ArrayList<Groupe> getGroupeSQL(String sql) {
        ArrayList<Groupe> groupe_list = new ArrayList<>();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // System.out.println(resultSet.getString("login"));
                int id = resultSet.getInt("id");
                D.log(id);
                String creator = resultSet.getString("creater");
                D.log(creator);
                String[] admin = resultSet.getString("admin").split(";");
                D.log("admin massiv array");
                String tema = resultSet.getString("tema");
                D.log(tema);
                String name = resultSet.getString("name");
                D.log(name);
                String[] participant = resultSet.getString("participant").split(";");
                D.log("participant");
                Groupe groupe = new Groupe(id, creator, admin, name, tema, participant);
                groupe_list.add(groupe);
            }
        } catch (SQLException e) {
        }

        // System.out.println("Retrieving data from database...");
        // System.out.println("\nusers:");

        return groupe_list;
    }


    public boolean addMoneyFundcuzGetUser(User us, int money) {
        String login = us.login, password = us.password;
        //System.out.println("main.Server: "+login+" ... "+password);
        JSONObject obj = getUserInfo(login, password);
        if (obj.has("log_in")) {
            //   System.out.println("main.Server: has");
            try {

                if (obj.getString("log_in").equals("allok")) {
                    int moneyUser = obj.getInt("money");
                    //  System.out.println("main.Server: "+moneyUser);
                    if (moneyUser - money >= 0) {
                        int mon = moneyUser + money;
                        String cmd = "UPDATE `users` SET `money` = '" + mon + "' WHERE `users`.`login` = '" + login + "';";
                        //    System.out.println("main.Server: "+cmd);
                        try {
                            res = statement.executeUpdate(cmd);
                            return true;
                        } catch (SQLException throwables) {

                            System.out.println("main.Server: " + throwables);
                            return false;

                        }

                    } else {
                        return false;

                    }
                } else {
                    return true;
                }
            } catch (JSONException e) {
                return false;
            }
        } else {
            return false;
        }
    }


    public boolean getwin(User us) {
        String login = us.login, password = us.password;
        //System.out.println("main.Server: "+login+" ... "+password);
        JSONObject obj = getUserInfo(login, password);
        if (obj.has("log_in")) {
            //   System.out.println("main.Server: has");
            try {

                if (obj.getString("log_in").equals("allok")) {
                    //   int moneyUser = obj.getInt("money");
                    //  System.out.println("main.Server: "+moneyUser);
                    if (0 == 0) {
                        //int mon = moneyUser+money;
                        String cmd = "UPDATE `users` SET `win` = `win`+" + 1 + " WHERE `users`.`login` = '" + login + "';";
                        //    System.out.println("main.Server: "+cmd);
                        try {
                            res = statement.executeUpdate(cmd);
                            return true;
                        } catch (SQLException throwables) {

                            System.out.println("main.Server: " + throwables);
                            return false;

                        }

                    } else {
                        return false;

                    }
                } else {
                    return true;
                }
            } catch (JSONException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean drawU(User us) {
        String login = us.login, password = us.password;
        //System.out.println("main.Server: "+login+" ... "+password);
        JSONObject obj = getUserInfo(login, password);
        if (obj.has("log_in")) {
            //   System.out.println("main.Server: has");
            try {

                if (obj.getString("log_in").equals("allok")) {
                    // int moneyUser = obj.getInt("money");
                    //  System.out.println("main.Server: "+moneyUser);
                    if (0 == 0) {
                        //int mon = moneyUser+money;
                        String cmd = "UPDATE `users` SET `statics` =`statics`+ " + 1 + " WHERE `users`.`login` = '" + login + "';";
                        //    System.out.println("main.Server: "+cmd);
                        try {
                            res = statement.executeUpdate(cmd);
                            return true;
                        } catch (SQLException throwables) {

                            System.out.println("main.Server: " + throwables);
                            return false;

                        }

                    } else {
                        return false;

                    }
                } else {
                    return true;
                }
            } catch (JSONException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public User getUserMes(Integer id) {
        User user = null;
        JSONObject jsonObject = null;
        jsonObject = this.getUserId(id);
        if (jsonObject.has("login")) {
            try {
                user = new User(id, jsonObject.getString("login"), "");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return user;

    }

    public boolean getloser(User us) {
        String login = us.login, password = us.password;
        //System.out.println("main.Server: "+login+" ... "+password);
        JSONObject obj = getUserInfo(login, password);
        if (obj.has("log_in")) {
            //   System.out.println("main.Server: has");
            try {

                if (obj.getString("log_in").equals("allok")) {
                    int moneyUser = obj.getInt("money");
                    //  System.out.println("main.Server: "+moneyUser);
                    if (0 == 0) {
                        //int mon = moneyUser+money;
                        String cmd = "UPDATE `users` SET `over` = `over`+ " + 1 + " WHERE `users`.`login` = '" + login + "';";
                        //    System.out.println("main.Server: "+cmd);
                        try {
                            res = statement.executeUpdate(cmd);
                            return true;
                        } catch (SQLException throwables) {

                            System.out.println("main.Server: " + throwables);
                            return false;

                        }

                    } else {
                        return false;

                    }
                } else {
                    return true;
                }
            } catch (JSONException e) {
                return false;
            }
        } else {
            return false;
        }
    }



    public User getUser(String login,String password){
        User user = databaseConnection.getUser(login,password);
        if(user!=null){

                if (user.getType() == 4) {
                    user.typeUser = TypeUser.ADMIN;
                } else if (user.getType() == 3) {
                    user.typeUser = TypeUser.MODERATOR;
                } else if (user.getType() == 2) {
                    user.typeUser = TypeUser.TESTER;
                } else {
                    user.typeUser = TypeUser.DEFAULT;
                }
            return user;
        }
        return  null;
    }

   /* public User getUser(String login, String pass) {
        D.bugss(login + ":" + pass);
        JSONObject obj = getUserInfo(login, pass);
        D.log(obj);
        if (obj != null && obj.has("log_in")) {
            try {

                //System.out.println(obj.toString()+"");
                if (obj.getString("log_in").equals("allok")) {
                    User user = new User(obj.getInt("id"), obj.getString("login"), obj.getString("pass"));
                    user.city = obj.getString("city");
                    user.groupe_split = obj.getString("gr").split(";");
                    user.groupes = obj.getString("gr");
                    user.mail = obj.getString("email");
                    user.date = obj.getLong("date");
                    user.date_register = obj.getLong("date_register");
                    user.city = obj.getString("city");
                    user.name = obj.getString("name");
                    user.surname = obj.getString("surname");
                    user.typeuser = obj.getInt("i");
                    user.setting = obj.getString("setting");
                    if (obj.has("i")) {
                        if (obj.getInt("i") == 4) {
                            user.typeUser = TypeUser.ADMIN;
                        } else if (obj.getInt("i") == 3) {
                            user.typeUser = TypeUser.MODERATOR;
                        } else if (obj.getInt("i") == 2) {
                            user.typeUser = TypeUser.TESTER;
                        } else {
                            user.typeUser = TypeUser.DEFAULT;
                        }
                    }

                    return user;
                } else {
                    return null;
                }
            } catch (JSONException e) {

                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    */


/*
  public static void main(String[]args){
 
	DatabasConnect base = new DatabasConnect();
	//base.registerUser("AntonioSibgatulin", "password", "89879203004a@gmail.com", "Syzran", System.currentTimeMillis(), "Anton", "Sibgatulino");
	base.getTables(1);
}
*/


    public boolean registerUser(String login, String pass, String mail, String city, long date, String name, String surname) {
        String log_in = "SELECT * FROM `users` WHERE `login` = '" + login + "';";
        try {
            resultSet = statement.executeQuery(log_in);
            if (resultSet.absolute(1)) {

                return false;
            } else {
                log_in = "INSERT INTO `users` (`id`, `login`, `password`, `City`, `date`, `date_register`, `Email`, `Setting`, `money`, `ban`, `type`,`name`,`surname`) VALUES (NULL, '" + login + "', '" + pass + "', '" + city + "', " + date + ", " + System.currentTimeMillis() + ", '" + mail + "', '1;1;1;1', '0', '0', '0','" + name + "','" + surname + "')";
                D.log(log_in);
                res = statement.executeUpdate(log_in);
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //    System.out.println(throwables);
            return false;
        }

    }


    public long registerMessage(long idfrom, long idto, String Message) {
        String log_in = "SELECT * FROM `message` WHERE `idfrom` = " + idfrom + " AND `idto`=" + idto + " AND `messageid`>0 OR `idfrom`=" + idto + " AND `idto`=" + idfrom + " AND `messageid`>0;";
        D.log(log_in);
        long message_id = 0;

        try {
            ResultSet resultSet = statement.executeQuery(log_in);
            if (resultSet.absolute(1)) {
                if (resultSet.last()) {
                    message_id = resultSet.getLong("messageid");
                } else {
                    while (resultSet.next()) {
                        message_id = resultSet.getLong("messageid");
                    }
                }
                log_in = "INSERT INTO `message` (`idfrom`, `idto`, `time`, `message`, `messageid`, `delete`, `viewed`) VALUES (" + idfrom + ", " + idto + ", " + D.now() + ", ?, " + (message_id + 1) + ", " + 0 + "," + 0 + ");";

                try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
                    statement.setString(1, Message);
                    D.bugss(statement.executeUpdate());
//	statement.executeQuery();

                }

                return message_id;

                //(ResultSet.)
            } else {
                log_in = "INSERT INTO `message` (`idfrom`, `idto`, `time`, `message`, `messageid`, `delete`, `viewed`) VALUES (" + idfrom + ", " + idto + ", " + D.now() + ", ?, " + 1 + ", " + 0 + "," + 0 + ");";
                D.log(log_in);
                try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
                    statement.setString(1, Message);
                    statement.executeUpdate();

                }

                return message_id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //    System.out.println(throwables);
            return -90;
        }

    }

    public boolean updatemoney(User me, User user, Double mon) {
        D.log("allright");


        try {
            String com = "INSERT INTO `action_coin` (`id`,`loginTo`,`loginFrom`,`money`,`time`,`message`) VALUES (NULL,'" + user.login + "','" + me.login + "'," + mon + "," + System.currentTimeMillis() + ",'you get money from " + me.login + "');";
            D.log(com);
            res = statement.executeUpdate(com);

            String cmd = "UPDATE `coin` SET `money` = `money` - " + mon + " WHERE `id_purse` = '" + me.login + "'; ";


            //cmd = "UPDATE `coin` SET `money` = `money` + "+mon+" , `money` = `money` - "+mon+"    WHERE `id_purse`IN('"+user.login+"','"+me.login+"');";
            D.log(cmd);

            res = statement.executeUpdate(cmd);
            cmd = "UPDATE `coin` SET `money` = `money` + " + mon + " WHERE `id_purse` = '" + user.login + "'; ";
            res = statement.executeUpdate(cmd);
				
	/*		 cmd = "UPDATE `coin` SET `money` = `money` - "+mon+" WHERE `id_purse` = '"+me.login+"'; ";
   		 D.log(cmd);
   	    	
			res = statement.executeUpdate(cmd);
			
			
			*/

            me.purseModel.coinRubles -= mon;
            //if(user.password==null){
            for (WebSocket sock : me.device.list) {
                Socket.send(sock, "translate;allright;" + user.login + ";" + mon + ";" + me.purseModel.coinRubles);

            }

            if (user.password != null) {
                user.purseModel.coinRubles += mon;
                for (WebSocket sock : user.device.list) {
                    Socket.send(sock, "translate;new;" + me.login + ";" + mon + ";" + user.purseModel.coinRubles);
                }

                //}user.purse

                user.purseModel.Operation = false;

            }

            me.purseModel.Operation = false;
            return true;
        } catch (SQLException e) {
            D.log(e);
            // TODO Auto-generated catch block
            return false;
        }


    }

    public JSONObject getMessageHistory(long idfrom, long idto) {
        String log_in = "SELECT MAX(`messageid`) FROM `message` WHERE `idfrom` = " + idfrom + " AND `idto`= " + idto + " OR `idfrom` = " + idto + " AND `idto`=" + idfrom + ";";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        D.log(log_in);
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        int max_message = 0;
        try {


            resultSet = statement.executeQuery(log_in);
            if (resultSet.absolute(1)) {
                max_message = resultSet.getInt("MAX(`messageid`)");
                D.log(max_message);
            }


            log_in = "SELECT * FROM `message` WHERE `idfrom` = " + idfrom + " AND `idto`=" + idto + " AND `messageid`>0 OR `idfrom`=" + idto + " AND `idto`=" + idfrom + " AND `messageid`>0;";

            resultSet = statement.executeQuery(log_in);
            if (resultSet.absolute(1)) {
                long col = 0;
                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                //message_id = resultSet.getLong("messageid");
                JSONObject jsonObject2 = null;
                if (resultSet.getInt("delete") == 0) {
                    col++;
                    jsonObject2 = new JSONObject();
                    jsonObject2.put("idfrom", resultSet.getString("idfrom"));
                    jsonObject2.put("idto", resultSet.getString("idto"));
                    jsonObject2.put("time", resultSet.getString("time"));
                    jsonObject2.put("message", resultSet.getString("message"));
                    jsonObject2.put("messageid", resultSet.getString("messageid"));
                }
                if (jsonObject2 != null)
                    jsonArray.put(jsonObject2);

                while (resultSet.next()) {

                    //message_id = resultSet.getLong("messageid");
                    JSONObject jsonObject21 = null;
                    if (resultSet.getInt("delete") == 0) {
                        jsonObject21 = new JSONObject();
                        col++;
                        jsonObject21.put("idfrom", resultSet.getString("idfrom"));
                        jsonObject21.put("idto", resultSet.getString("idto"));
                        jsonObject21.put("time", resultSet.getString("time"));
                        jsonObject21.put("message", resultSet.getString("message"));
                        jsonObject21.put("messageid", resultSet.getString("messageid"));

                    }
                    if (jsonObject21 != null)
                        jsonArray.put(jsonObject21);

                }
                jsonObject.put("message", jsonArray);
                jsonObject.put("all", col);

            } else {
                jsonObject = new JSONObject();

                jsonObject.put("all", 0);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //    System.out.println(throwables);
            return null;
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }


    public HashMap<String, String> getString() {
        String log_in = "SELECT * FROM `advertising` WHERE `stop` > " + System.currentTimeMillis();
        HashMap<String, String> list = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(log_in)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                }
                //return resultSet.next();
            }
        } catch (SQLException sql) {
            sql.printStackTrace();

        }
        return list;

    }


    public JSONObject getTables(long id) {
        JSONObject jsonObject = new JSONObject();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String log_in = "SELECT * FROM `message` WHERE `idfrom` = " + id + " AND `messageid`=1 OR `idto`=" + id + " AND `messageid`=1;";
        JSONArray jsonArray = new JSONArray();

        try {
            resultSet = statement.executeQuery(log_in);
            //if(resultSet.absolute(1)){


            while (resultSet.next()) {
                long info = 0;
                long idto = resultSet.getLong("idto");
                long idfrom = resultSet.getLong("idfrom");
                if (idto == id)
                    info = idfrom;
                else info = idto;
                JSONObject jsonObject2 = new JSONObject();
                String se = resultSet.getString("message");
                //	D.log(se);
                //	this.getUserId(info);

                try {
                    JSONObject jsonObject3 = this.getUserId(info);

                    jsonObject2.put("idtable", info);
                    jsonObject2.put("message", se);

                    jsonObject2.put("login", "@" + jsonObject3.getString("login"));
                    D.log(jsonObject3.getString("login"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                jsonArray.put(jsonObject2);

            }
            try {
                jsonObject.put("key", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //}else{

            // }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        D.log(jsonObject);
        return jsonObject;
    }

    public JSONObject getActionWithVideo(String login, String id) {
        JSONObject json = null;
        String log_in = "SELECT * FROM `like_video` WHERE `login`= ? AND `id` = ?";

        try (PreparedStatement statement = connection.prepareStatement(log_in)) {
            statement.setString(1, login);
            statement.setString(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    json = new JSONObject();
                    json.put("type", resultSet.getInt("type"));
                }
                //return resultSet.next();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (SQLException sql) {
            sql.printStackTrace();

        }

        // System.out.println("Retrieving data from database...");
        // System.out.println("\nusers:");
        return json;
        // if(resultSet.getRow()>0)

    }

    public boolean addBoardGame(String his) {
        String log_in = "INSERT INTO `boardsgame` (`id`, `game`) VALUES (NULL, '" + his + "');";

        try {

            res = statement.executeUpdate(log_in);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public void registrationView(String login, String id, JSONObject json) {
        if (json == null) {
            insertvideo(login, id);
            updatevideoview(id);
        } else {
            updatevideoview(login, id);
            updatevideoview(id);
        }

    }

    public void NotAuthificationDevice(String id, String iddevice) {
        String string = "";
    }

    public void setLikeUpdateLike(String login, String id, int i) {

        String string = "UPDATE `like_video` SET `type` = " + i + " WHERE `login` = ? AND `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, login);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void insertvideo(String login, String id) {
        String string = "INSERT INTO `like_video` (`id`, `login`, `type`, `View`) VALUES (?,?,0,1)";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, id);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatevideoview(String id) {
        String string = "UPDATE `video_table` SET `view` = `view` + 1 WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void banvideo(String id) {
        String string = "UPDATE `video_table` SET `ban` =  1 WHERE `id`= ?";
        D.bugss2(string);

        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unbanvideo(String id) {
        String string = "UPDATE `video_table` SET `ban` =  0 WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatevideoview(String login, String id) {
        String string = "UPDATE `like_video` SET `View` = `View` + 1 WHERE `login` = ? AND `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, login);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getUserInfo(String login, String password) {
        //if(login.length()<=2||password.length()<=0)return null;
        JSONObject obj = new JSONObject();
        String log_in = "SELECT * FROM `users` WHERE `login`= ? AND `password` = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            // System.out.println("Retrieving data from database...");
            // System.out.println("\nusers:");
            try {
                // if(resultSet.getRow()>0)
                while (resultSet.next()) {
                    // System.out.println(resultSet.getString("login"));
                    int id = resultSet.getInt("id");
                    //  System.out.println(resultSet.getInt("id"));

                    //  int win= resultSet.getInt("win");
                    // String gameId= resultSet.getString("games");
                    // D.log(i);
                    // System.out.println(resultSet.getInt("type"));
                    //   System.out.println(resultSet.getString("password"));
                    //  int over = resultSet.getInt("over");
                    String logins = resultSet.getString("login");
                    String surname = resultSet.getString("surname");

                    String pass = resultSet.getString("password");
                    //  D.log(name);
                    // D.log(pass);
                    String city = resultSet.getString("City");
                    //D.log(city);
                    long date = resultSet.getLong("date");
                    int i = resultSet.getInt("type");

                    //D.log(date);
                    long date_register = resultSet.getLong("date_register");
                    //D.log(date_register);
                    String Email = resultSet.getString("Email");
                    String setting = resultSet.getString("Setting");
                    int mon = resultSet.getInt("money");
                    int ban = resultSet.getInt("ban");
                    String name = resultSet.getString("name");
                    // System.out.println(resultSet.getInt("type"));


                    // String json = resultSet.getString("GunShop");
                    /// int salary = resultSet.getInt("salary");
                    //   resultSet = statement.executeQuery(log_in);
                    String groupes = resultSet.getString("groupes");

                    if (logins.equals(login) && pass.equals(password) && id > 0) {
                        obj.put("login", login);
                        obj.put("gr", groupes);
                        obj.put("name", name);
                        obj.put("surname", surname);
                        obj.put("id", id);
                        obj.put("i", i);
                        obj.put("ban", ban);
                        obj.put("log_in", "allok");
                        obj.put("money", mon);
                        obj.put("pass", password);
                        obj.put("date", date);
                        obj.put("email", Email);
                        obj.put("date_register", date_register);
                        obj.put("city", city);
                        obj.put("setting", setting);
                 /*  obj.put("gameId",gameId);
                   obj.put("win",win);
                   obj.put("over",over);
                   obj.put("json",json);
                   *
                   */
                    } else {
                        obj.put("log_in", "error");

                    }
                    //System.out.println("id: " + id);
                    //System.out.println("login: " + name);
                    //System.out.println("password: " + pass);
                    //  System.out.println("Salary: $" + salary);
                }
            } catch
            (JSONException e) {

            }


            return obj;
        } catch (SQLException e) {

            JSONObject object = new JSONObject();
            try {
                object.put("log_in", "error");
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            //System.out.println(obj.toString());

            return object;
        }
    }

    public void exitRes() {
        try {
            resultSet.close();
        } catch (SQLException e) {
        }
    }

    public void exit() {
        System.out.println("Closing connection and releasing resources...");
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
        }
    }
    /*public static void main(String[] args) {

DatabasConnect c = new DatabasConnect();
c.getUser("Anton","Dert869$$");
c.exit();


    }
    */


    public JSONObject getUserId(long id) {
        JSONObject obj = new JSONObject();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String log_in = "SELECT * FROM `users` WHERE `id`=" + id + ";";
        try {
            resultSet = statement.executeQuery(log_in);

            // System.out.println("Retrieving data from database...");
            // System.out.println("\nusers:");
            try {
                while (resultSet.next()) {
                    // System.out.println(resultSet.getString("login"));
                    int ids = resultSet.getInt("id");
                    //  System.out.println(resultSet.getInt("id"));

                    //  int win= resultSet.getInt("win");
                    // String gameId= resultSet.getString("games");
                    int i = resultSet.getInt("type");
                    // D.log(i);
                    // System.out.println(resultSet.getInt("type"));
                    //   System.out.println(resultSet.getString("password"));
                    //  int over = resultSet.getInt("over");
                    //  String logins = resultSet.getString("login");
                    String surname = resultSet.getString("surname");

                    String pass = resultSet.getString("password");
                    String login = resultSet.getString("login");
                    //  D.log(name);
                    // D.log(pass);
                    String city = resultSet.getString("City");
                    //D.log(city);
                    long date = resultSet.getLong("date");

                    //D.log(date);
                    long date_register = resultSet.getLong("date_register");
                    //D.log(date_register);
                    String Email = resultSet.getString("Email");
                    String setting = resultSet.getString("Setting");
                    int mon = resultSet.getInt("money");
                    int ban = resultSet.getInt("ban");
                    String name = resultSet.getString("name");
                    // System.out.println(resultSet.getInt("type"));

                    String groupes = resultSet.getString("groupes");
                    obj.put("gr", groupes);
                    // String json = resultSet.getString("GunShop");
                    /// int salary = resultSet.getInt("salary");
                    //   resultSet = statement.executeQuery(log_in);

                    obj.put("login", login);
                    obj.put("name", name);
                    obj.put("surname", surname);
                    obj.put("id", ids);
                    obj.put("i", i);

                    obj.put("ban", ban);
                    obj.put("log_in", "allok");
                    obj.put("money", mon);
                    obj.put("setting", setting);
                    //obj.put("pass",password);
                    obj.put("date", date);
                    obj.put("email", Email);
                    obj.put("date_register", date_register);
                    obj.put("city", city);
             /*  obj.put("gameId",gameId);
               obj.put("win",win);
               obj.put("over",over);
               obj.put("json",json);
               *
               */

                    //System.out.println("id: " + id);
                    //System.out.println("login: " + name);
                    //System.out.println("password: " + pass);
                    //  System.out.println("Salary: $" + salary);
                }
            } catch
            (JSONException e) {

            }


            return obj;
        } catch (SQLException e) {

            JSONObject object = new JSONObject();
            try {
                object.put("log_in", "error");
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            //System.out.println(obj.toString());

            return object;
        }
    }


    public JSONObject getUserId(String id) {
        JSONObject obj = new JSONObject();
        ResultSet resultSet = null;

        String log_in = "SELECT * FROM `users` WHERE `login`= ?;";
        try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            try {

                // System.out.println("Retrieving data from database...");
                // System.out.println("\nusers:");
                try {
                    while (resultSet.next()) {
                        // System.out.println(resultSet.getString("login"));
                        int ids = resultSet.getInt("id");
                        //  System.out.println(resultSet.getInt("id"));

                        //  int win= resultSet.getInt("win");
                        // String gameId= resultSet.getString("games");
                        int i = resultSet.getInt("type");
                        // D.log(i);
                        // System.out.println(resultSet.getInt("type"));
                        //   System.out.println(resultSet.getString("password"));
                        //  int over = resultSet.getInt("over");
                        //  String logins = resultSet.getString("login");
                        String surname = resultSet.getString("surname");

                        String pass = resultSet.getString("password");
                        String login = resultSet.getString("login");
                        //  D.log(name);
                        // D.log(pass);
                        String groupes = resultSet.getString("groupes");
                        String city = resultSet.getString("City");
                        //D.log(city);
                        long date = resultSet.getLong("date");

                        //D.log(date);
                        long date_register = resultSet.getLong("date_register");
                        //D.log(date_register);
                        String Email = resultSet.getString("Email");
                        String setting = resultSet.getString("Setting");
                        int mon = resultSet.getInt("money");
                        int ban = resultSet.getInt("ban");
                        String name = resultSet.getString("name");
                        // System.out.println(resultSet.getInt("type"));


                        // String json = resultSet.getString("GunShop");
                        /// int salary = resultSet.getInt("salary");
                        //   resultSet = statement.executeQuery(log_in);
                        obj.put("login", login);
                        obj.put("name", name);
                        obj.put("surname", surname);
                        obj.put("id", ids);
                        obj.put("i", i);
                        obj.put("ban", ban);
                        obj.put("log_in", "allok");
                        obj.put("money", mon);
                        obj.put("setting", setting);
                        //obj.put("pass",password);
                        obj.put("date", date);
                        obj.put("email", Email);
                        obj.put("date_register", date_register);
                        obj.put("city", city);
                        obj.put("gr", groupes);
             /*  obj.put("gameId",gameId);
               obj.put("win",win);
               obj.put("over",over);
               obj.put("json",json);
               *
               */

                        //System.out.println("id: " + id);
                        //System.out.println("login: " + name);
                        //System.out.println("password: " + pass);
                        //  System.out.println("Salary: $" + salary);
                    }
                } catch
                (JSONException e) {
                    e.printStackTrace();
                }


                return obj;
            } catch (SQLException e) {

                JSONObject object = new JSONObject();
                try {
                    object.put("log_in", "error");
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                //System.out.println(obj.toString());

                return object;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject object = new JSONObject();
            try {
                object.put("log_in", "error");
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            //System.out.println(obj.toString());

            return object;
        }

    }


    public PurseModel getPurseUser(User user1, String id) {
        PurseModel purseModel = null;
        ResultSet resultSet = null;

        String log_in = "SELECT * FROM `coin` WHERE `id_purse`= ?;";
        D.log(log_in);
        try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
            statement.setString(1, id);
            D.bugss(statement.toString());
            resultSet = statement.executeQuery();


            while (resultSet.next()) {
                // D.log("user get prse "+user.login);
                String purse = resultSet.getString("id_purse");
                double money = resultSet.getDouble("money");
                double accept = resultSet.getDouble("accept");
                // D.log("dfolkmf "+purse);

                try {
                    purseModel = new PurseModel(purse, money);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
            // D.log(e);

        }
        return purseModel;
    }


    public VideoModel getVideModel(String ids) {

        String sql = "SELECT * FROM `video` WHERE `id` = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, ids);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String files = resultSet.getString("files");
                String link = resultSet.getString("link");
                VideoModel videoModel = new VideoModel(id, name, files, link);
                return videoModel;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }


    @SuppressWarnings("resource")
    public VideoThread getVideoThread(String name, VideoServerController controller) {
        VideoThread videoThread = null;
        File file = null;
        ResultSet resultSet = null;


        String log_in = "SELECT * FROM `videomain` WHERE `id` =  " + name + ";";
        try (PreparedStatement statement = this.connection.prepareStatement(log_in)) {
            //statement.setString(1, name);
            resultSet = statement.executeQuery(log_in);


            // System.out.println("Retrieving data from database...");
            // System.out.println("\nusers:");

            while (resultSet.next()) {
                String namefile = resultSet.getString("files");
                String name1 = resultSet.getString("name");
                String info = resultSet.getString("comment");
                long like = resultSet.getLong("likes");
                long dislike = resultSet.getLong("unlikes");
                long View = resultSet.getLong("view");
                D.log("videofiles/" + namefile);
                File file2 = new File("videofiles/" + namefile);
//		    	D.log(file2.length());
                long lengthfile = file2.length();
                int chunk = lengthfile > (1024 * 1024 * 120) ? (1024 * 1024 * 40) : (1024 * 1024 * 8) / 2;
                if (lengthfile <= (1024 * 1024 * 8)) {
                    //chunk = (int)lengthfile/2;
                }
                @SuppressWarnings("resource")
                FileChannel binaryFileChannel = new RandomAccessFile(file2, "r").getChannel();

                double l = lengthfile / (chunk);
                int l1 = (int) (Math.ceil(l));
                int allright = l1 > 4 ? 4 : l1;
                ArrayList<byte[]> gArrayList = new ArrayList<>();
                for (int i = 0; i < allright; i++) {
                    int start = i * (chunk);
                    int size = chunk;
                    if (start + size > lengthfile)
                        size = (int) lengthfile - start;
                    D.log("Now all okey's");
                    long timenow = System.currentTimeMillis();
                    ByteBuffer buf = getByteBuffer(binaryFileChannel, start, size);
                    D.log("Now all okey's");
                    gArrayList.add(getByteArray(buf));
                    D.log("time one frame " + (System.currentTimeMillis() - timenow));


                }
                videoThread = new VideoThread(name, gArrayList, like, dislike, info, namefile, lengthfile, (int) chunk, 0);


                if (allright > 15) {

                    loaderFileFilmAll(chunk, allright, lengthfile, binaryFileChannel, l1, videoThread);
                }

                //ByteBuffer buf = getByteBuffer(binaryFileChannel, 0, 1024*1024*100);
                //	D.log("D "+buffer.length);
		    	
		    	
		    	/*FileInputStream inputStream  = new FileInputStream(new File("videofiles/trees.mp4"));
		    	
		    	inputStream.read(buffer, 0, buffer.length);
		    	*/


            }


        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return videoThread;
        // TODO Auto-generated method stub

    }

    VideoThread videoTest = null;

    public void addEl(VideoThread videoThread, byte[] bytes) {
        videoThread.ar.add(bytes);
    }

    byte[] getByteArray(ByteBuffer bb) {
        byte[] ba = new byte[bb.limit()];
        bb.get(ba);
        return ba;
    }

    public void loaderFileFilmAll(int chunk, int allright, long lengthfile, FileChannel binaryFileChannel, int l1, VideoThread videoThread) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                //	int all = (int)(Math.ceil(l1/15.00));
                //int allright = 0;

                for (int i = allright; i < l1; i++) {
                    D.bugss("Start");
                    int start = i * (chunk);
                    int size = chunk;
                    if (start + size > lengthfile)
                        size = (int) lengthfile - start;
                    D.log("Now all okey's");
                    long timenow = System.currentTimeMillis();
                    ByteBuffer buf = null;
                    try {
                        buf = getByteBuffer(binaryFileChannel, start, size);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    D.log("Now all okey's");
                    // gArrayList.add(getByteArray(buf));
                    videoThread.ar.add(getByteArray(buf));
                    D.bugss("time one frame " + (System.currentTimeMillis() - timenow));


                    //addEl(videoThread,getByteArray(buf));
                    //videoThread

                }

            }
        });
        //statement.close();
        D.bugss("MAINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN " + videoThread.name);
        //return videoThread;

    }


    public JSONObject getVideoPost(String name) {
        return null;
    }
    /*
    public JSONObject JSONSearchVideo(String word) {
        JSONObject main = new JSONObject();
        String string = "SELECT * FROM `video_table` WHERE MATCH (name,comment) AGAINST (? IN BOOLEAN MODE)  ORDER BY `video_table`.`files` DESC;";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, "+" + word);
            try (ResultSet resultSet = statement.executeQuery()) {
                JSONArray jsonArray = new JSONArray();
                while (resultSet.next()) {
                    String title = resultSet.getString("name");
                    String description = resultSet.getString("comment");
                    String id = resultSet.getString("id");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("name", title);
                    jsonObject.put("comment", description);
                    jsonArray.put(jsonObject);
                }
                if (jsonArray.length() <= 80) {
                    JSONObject json = instrument.getObjectJson(instrument.SearchVideoOnYoutube(word));
                    //instrument.getJSONInfo


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }


     */
    public ByteBuffer getByteBuffer(FileChannel binaryFileChannel, int start, int size) throws IOException {


        return binaryFileChannel.map(FileChannel.MapMode.READ_ONLY, start, size);
    }


    public long addElementsVideofind(String title, String descred, String reallink, String image, String chanel, String videoId, String duration, long like, long view, long commentCount) {
        long timeAgo = System.currentTimeMillis();
        if(videoId.length()>0){
            timeAgo =0 ;
        }
        long m = System.currentTimeMillis();
        String main = "INSERT INTO `video_table`(`id`, `name`, `likes`, `unlikes`, `comment`, `view`, `files`, `image`,`chanel`,`idver`,`ban`,`verif`,`duration`,`commentCount`,`timeofcreate`,`timeofpublic`) VALUES ('" + m + "',?,"+like+",0,?,"+view+",'',?,?,?,0,0,?,"+commentCount+","+timeAgo+","+timeAgo+")";
        try (PreparedStatement statement = this.connection.prepareStatement(main)) {
            statement.setString(1, title);
            statement.setString(2, descred);

            statement.setString(3, image);
            statement.setString(4, chanel);
            //statement.setString(5, link1);
            statement.setString(5, videoId);
            statement.setString(6,duration);
            System.out.println(statement);
            //statement.executeLargeUpdate();
            statement.executeUpdate();
        System.out.println("Is adding ... "+m);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return m;
    }


    public long addElementsVideofind(String title, String descred, String reallink, String image, String chanel, String der, String link1, String videoId) {
        long m = System.currentTimeMillis();
        String main = "INSERT INTO `video_table`(`video_id`,`id`, `name`, `likes`, `unlikes`, `comment`, `view`, `files`, `link`,`image`,`chanel`,`link1`,`idver`) VALUES (NULL,'" + m + "',?,0,0,?,0,'',?,?,?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(main)) {
            statement.setString(1, title);
            statement.setString(2, descred);
            statement.setString(3, der);
            statement.setString(4, image);
            statement.setString(5, chanel);
            statement.setString(6, link1);
            statement.setString(7, videoId);

            statement.executeLargeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return m;
    }

    public void updatelink(String link, String id) {
        String string = "UPDATE `video_table` SET `link` = ? WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            statement.setString(1, link);
            statement.setString(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {

        }

    }


    public void updatelike(String id, int i) {
        String type1 = "`likes` = `likes` + 1 ";
        String type2 = "`unlikes` = `unlikes` + 1 ";
        String main = i == 1 ? type1 : type2;
        String string = "UPDATE `video_table`  SET " + main + " WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss1(e);
        }

    }

    public void addDISLIKEORlike(String id, int i, int l) {
        String type1 = "`likes` = `likes` +  " + l;
        String type2 = "`unlikes` = `unlikes` + " + l;
        String main = i == 1 ? type1 : type2;
        String string = "UPDATE `video_table`  SET " + main + " WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss1(e);
        }

    }


    public void addviewonvideo(String id, int i, int l) {
        //String type1 = "`likes` = `likes` +  "+l;
        //String type2 = "`unlikes` = `unlikes` + "+l;
        //String main = i==1?type1:type2;
        String string = "UPDATE `video_table`  SET `view` = `view` +" + l + " WHERE `id`= ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss1(e);
        }

    }


    public int[] getLikes(String id) {
        int[] c = new int[2];
        String string = "SELECT * FROM `video_table` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    c[0] = set.getInt("likes");
                    c[1] = set.getInt("unlikes");

                }
            }
            //statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss1(e);
        }
        return c;

    }


    public boolean have_already(String id) {
        boolean test = false;
        String string = "SELECT * FROM `video_table` WHERE `idver` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    test = true;

                }
            }
            //statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss1(e);
        }
        return test;

    }


    public String getNameChannel(String id) {
        String name = null;
        String string = "SELECT * FROM `video_table` WHERE `id` = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(string)) {
            //statement.setString(1, link);
            statement.setString(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    name = set.getString("chanel");

                }
            }
            //statement.executeUpdate();

        } catch (SQLException e) {
            D.bugss2(e);
        }
        return name;

    }

    public JSONObject getVideoForSiteInfo(String videoid, User user) {
        JSONObject jsonObject = new JSONObject();
        String arg0 = "SELECT * FROM `video_table` WHERE `id` = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            statement.setString(1, videoid);
            D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("check result");
                    String link = "", link2 = "";
                    int main = resultSet.getInt("ban");
                    //String none = "None";

                    String link_youtube = resultSet.getString("idver");
                    if (main == 0 || (user != null && main != 0 && (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.ADMIN)))
                        System.out.println("link youtube "+link_youtube);
                        if (link_youtube!=null && link_youtube.isEmpty()==false) {

                            String string = GetLinkVideoYoutubeModel.getText(link_youtube);//getlinks(link_youtube).replaceAll("<meta charset =\"utf-8\">", "");


                            System.out.println(string);
                            JSONObject sj = new JSONObject(string);

                            D.bugss1("json:" + string);
                            //if(){

                            //}else if(main==0)
                            if (sj.isNull("videoinfo") == false) {
                                JSONArray jsonArray = sj.getJSONArray("videoinfo");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    //if(i==0)
                                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                    if (jsonObject2.has("url")) {
                                        String url = jsonObject2.getString("url");
                                        if (i == 0)
                                            link = url;
                                        else
                                            link2 = url;
                                    }
                                }
                            } else if (sj.has("videoindex") && sj.isNull("videoindex") == false) {
                                if (sj.get("videoindex") instanceof JSONArray) {
                                    //JSONObject jsonObject2 = sj.getJSONObject("videoindex");
                                    JSONArray jsonArray = sj.getJSONArray("videoindex");
                                    int u = 0;
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                                        boolean aud = true;
                                        boolean no_aud = true;


                                        if (jsonObject3.has("audio")) {
                                            aud = jsonObject3.getBoolean("audio");
                                        }


                                        if (jsonObject3.has("no_audio")) {
                                            no_aud = jsonObject3.getBoolean("no_audio");
                                        }

                                        if (aud == false && no_aud == false) {
                                            if (jsonObject3.has("type") && jsonObject3.getString("type").equals("mp4")) {
                                                //link2 = jsonObject3.getString("url");
                                                //link = jsonObject3.getString("url");
                                            }
                                            if (jsonObject3.has("downloadable") && jsonObject3.getBoolean("downloadable")) {
                                                link2 = jsonObject3.getString("url");
                                                link = jsonObject3.getString("url");

                                            }
                                            //u++;
                                        } else {
                                            if (aud && no_aud) {
                                                link2 = jsonObject3.getString("url");
                                                link = jsonObject3.getString("url");

                                            }
                                        }
                                        if (jsonObject3.has("downloadable") && jsonObject3.getBoolean("downloadable")) {
                                            link2 = jsonObject3.getString("url");
                                            link = jsonObject3.getString("url");

                                        }
                                        if((link.isEmpty() || link.length()<1) && jsonObject3.has("type") && jsonObject3.getString("type").equals("mp4")){
                                            link2 = jsonObject3.getString("url");
                                            link = jsonObject3.getString("url");
                                        }
                                        // "audio": false,
                                        //  "no_audio": false,
                                    }
                                }
                            }
					
					/*else{
					String string1 = instrument.parseLink.sendPost111(link_youtube);
					//String high = "";
					D.bugss2(new	JSONObject(string1));
					JSONObject jsonobject = new JSONObject(string1).getJSONObject("data");
					
					if(jsonobject.has("status")){
						D.bugss(jsonobject.getInt("status"));
						if(jsonobject.getInt("status")==200){
							JSONArray jsonobjectmain  = jsonobject.getJSONObject("data").getJSONObject("videos").getJSONArray("mp4");
							for(int x = 0;x<jsonobjectmain.length();x++){
								JSONObject jsonObject4 = jsonobjectmain.getJSONObject(x);
								if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("720p")){
									link = jsonObject4.getString("url");
								}
								if(jsonObject4.getBoolean("has_audio")==true && jsonObject4.getString("quality").equals("360p")){
									link2 = jsonObject4.getString("url");break;
								}
							}
						}
					}
					}
					*/
                            if (user != null && !(user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.ADMIN))
                                if ((main) != 0) {
                                    //link = "";
                                    //link2  = "";
                                }


                        }
                    jsonObject.put("link", link);

                    jsonObject.put("link1", link2);
                    if (false == true && !(link_youtube.length() <= 4)) {
				/*	none = link_youtube;
		
			JSONArray jsonArray = sj.getJSONArray("videoinfo");
			String link =null,link2=null;
			for(int i=0;i<jsonArray.length();i++){
				//if(i==0)
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				String url = jsonObject2.getString("url");
				if(i==0)
					link=url;
				else
					link2=url;
					*/

                    }


                    //String link = resultSet.getString("link");
			/*if(link.length()<=3)link = "NULL";
			jsonObject.put("link", link);
			
			jsonObject.put("link1", link2);
			*/
                    //}
                    String name = resultSet.getString("name");
                    String chanel = resultSet.getString("chanel");
                    //	if( resultSet.getString("files")==null ||  resultSet.getString("files").length()<4)
                    //	if(link==null||link.length()<4){
                    //		this.instrument.parseLink.sendPost111(string)
                    //	}
                    String filesn = resultSet.getString("files");
                    if (main != 0) {
                        filesn = "";
                    }
                    jsonObject.put("ban", main);
                    jsonObject.put("files", filesn);
                    jsonObject.put("name", name);
                    jsonObject.put("info", resultSet.getString("comment"));
                    jsonObject.put("like", resultSet.getInt("likes"));
                    jsonObject.put("dislike", resultSet.getInt("unlikes"));
                    jsonObject.put("filename", resultSet.getString("files"));
                    jsonObject.put("id", resultSet.getString("id"));
                    jsonObject.put("img", resultSet.getString("image"));
                    jsonObject.put("view", resultSet.getLong("view"));
                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));
                    jsonObject.put("countOfSubscribed",getCountOfSubscribe(resultSet.getString("chanel")));
                    jsonObject.put("countOfComment",resultSet.getString("commentCount"));

                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                    jsonObject.put("chanel", resultSet.getString("chanel"));
                    //	jsonObject.put("link1", resultSet.getString("link1"));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return jsonObject;
    }

    public String getCountOfSubscribe(String login){

        String sql = "SELECT * FROM `users` WHERE `login` = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                long count = resultSet.getLong("countOfSubscribed");
                return ""+count;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "None";
        }
        return "None";
    }


    public static void main(String... args) {
        DatabasConnect base = new DatabasConnect();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            if (string != null) {
                String main = "Ясновидец 2 сезон " + string + " серия";
                String string1 = scanner.nextLine();
                if (string1 != null) {
                    String ger = "" + string1;
                    long t = base.addElementsVideofind1(main, main, ger, "https://i.mycdn.me/image?id=908121350859&t=50&plc=WEB&tkn=*zLZgYYhGs2E_t-Eq1wvQBDQLRSg&fn=external_7", "alex sibgat", "");
                    //D.bugss(base.getsearchvideo("nothin%nig"));
                    System.out.println(t);
                }
            }
        }
    }


    public JSONObject setLentaForMe(String login, User user, WebSocket con) throws SQLException {
        JSONObject jsonObject = null;
        String arg0 = "SELECT * FROM `like_video` WHERE `View` > 1 AND `type` = 1";
        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            int index = 0;
            //statement.setString(1, "%"+findvideo+"%");
            ArrayList<String> list = new ArrayList<>();
            //D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    index++;


                    list.add(resultSet.getString("id"));


                    if (index >= 25) break;
                }

                String str = list.get(new Random().nextInt(list.size() - 1));

                String gr = this.getNameChannel(str);
                if (gr != null) {

                    jsonObject = getvideofromchan(gr, user, con);

                }
            }
        }
        return jsonObject;
    }


    public void getTrends(User user, WebSocket conn) throws SQLException {
        String arg0 = "SELECT * FROM `trend` ";
        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            int index = 0;
            //statement.setString(1, "%"+findvideo+"%");
            //D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String idvideo = resultSet.getString("idvideo");


                    JSONObject jsonObject = getVideoForSiteInfo(idvideo, user);
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
                        jsonObject2 = getActionWithVideo(user.login, idvideo);
                        registrationView(user.login, idvideo, jsonObject2);
                        if (jsonObject2 != null) {
                            try {
                                jsonObject.put("typelike", jsonObject2.getInt("type"));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    }


                    Socket.send(conn, "addlenta;" + jsonObject.toString());


                }
            }
        }
    }


    public long addElementsVideofind1(String title, String descred, String reallink, String image, String chanel, String videoId) {
        long m = System.currentTimeMillis();
        String main = "INSERT INTO `video_table`(`video_id`,`id`, `name`, `likes`, `unlikes`, `comment`, `view`, `files`, `image`,`chanel`,`idver`,`ban`,`verif`) VALUES (NULL,'" + m + "',?,0,0,?,0, ? ,?,?,?,0,0)";

        try (PreparedStatement statement = this.connection.prepareStatement(main)) {
            statement.setString(1, title);
            statement.setString(2, descred);
            statement.setString(3, reallink);

            statement.setString(4, image);
            statement.setString(5, chanel);
            //statement.setString(5, link1);
            statement.setString(6, videoId);

            statement.executeLargeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return m;
    }

    public JSONObject getLenta(int lenta,WebSocket conn){
        int limit = 30;

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        String sql="SELECT * FROM `video_table` ORDER BY `video_table`.`id` ASC LIMIT "+limit+" OFFSET "+(limit*lenta);

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getInt("ban") == 0) {
                    JSONObject jsonObject = new JSONObject();
                    //	String link = resultSet.getString("link");
                    //if(link.length()<=3)link = "NULL";
                    String name = resultSet.getString("name");
                    //jsonObject.put("link", link);
                    jsonObject.put("image", resultSet.getString("image"));
                    jsonObject.put("files", resultSet.getString("files"));
                    jsonObject.put("name", name.replaceAll("\"","&quot;").replaceAll("'","&#039;"));
                    jsonObject.put("duration",resultSet.getString("duration"));
                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                    jsonObject.put("channame", resultSet.getString("chanel"));
                    jsonObject.put("view",resultSet.getString("view"));
                    jsonObject.put("info", resultSet.getString("comment").replaceAll("\"","&quot;").replaceAll("'","&#039;"));
                    jsonObject.put("like", resultSet.getInt("likes"));
                    jsonObject.put("dislike", resultSet.getInt("unlikes"));
                    jsonObject.put("filename", resultSet.getString("files"));
                    jsonObject.put("id", resultSet.getString("id"));
                    jsonArray.put(jsonObject);
                }
            }
            if(jsonArray.length()<=0){
                return getLenta(lenta+1,conn);
            }
        }catch (Exception o){
            o.printStackTrace();
        }
        if (jsonArray.length() > 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("find", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            com.AntonSibgatulin.Socket.Socket.send(conn,"searchvideo;" + json.toString());

            return json;
        }

        return null;
    }


    public List<VideoControllerModel> getLenta(int lenta){
        int limit = 30;
        List<VideoControllerModel> videoControllerModels = new ArrayList<>();

        String sql="SELECT * FROM `video_table` ORDER BY `video_table`.`id` ASC LIMIT "+limit+" OFFSET "+(limit*lenta);

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getInt("ban") == 0) {
                    JSONObject jsonObject = new JSONObject();
                    //	String link = resultSet.getString("link");
                    //if(link.length()<=3)link = "NULL";
                    String name = resultSet.getString("name");
                    //jsonObject.put("link", link);
                    jsonObject.put("image", resultSet.getString("image"));
                    jsonObject.put("files", resultSet.getString("files"));
                    jsonObject.put("name", name);
                    jsonObject.put("duration",resultSet.getString("duration"));
                    jsonObject.put("view",resultSet.getLong("view"));

                    jsonObject.put("channame", resultSet.getString("chanel"));
                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                    jsonObject.put("info", resultSet.getString("comment"));
                    jsonObject.put("like", resultSet.getInt("likes"));
                    jsonObject.put("dislike", resultSet.getInt("unlikes"));
                    jsonObject.put("filename", resultSet.getString("files"));
                    jsonObject.put("id", resultSet.getString("id"));
                    VideoControllerModel videoControllerModel = new VideoControllerModel(resultSet.getString("id"),name,resultSet.getInt("likes"),
                            resultSet.getInt("unlikes"),resultSet.getString("comment"),0,resultSet.getString("image"),resultSet.getString("chanel"));
                    videoControllerModels.add(videoControllerModel);
                  //  jsonArray.put(jsonObject);
                }
            }
            /*if(jsonArray.length()<=0){
                return getLenta(lenta+1,conn);
            }

             */
        }catch (Exception o){
            o.printStackTrace();
        }
       /* if (jsonArray.length() > 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("find", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            com.AntonSibgatulin.Socket.Socket.send(conn,"searchvideo;" + json.toString());

            return json;
        }

        */

        return videoControllerModels;
    }


    public JSONObject getsearchvideo(String findvideo, Anonymous conn) {
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String arg0 = "SELECT * FROM `video_table` WHERE MATCH (name,comment) AGAINST (? IN BOOLEAN MODE)  ORDER BY `video_table`.`files` DESC;";
//String arg1 = "SELECT * FROM `video_table` WHERE `name` LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            statement.setString(1, "+" + findvideo);
            //statement.setString(1, "%"+findvideo+"%");

            //D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getInt("ban") == 0) {
                        JSONObject jsonObject = new JSONObject();
                        //	String link = resultSet.getString("link");
                        //if(link.length()<=3)link = "NULL";
                        String name = resultSet.getString("name");
                        //jsonObject.put("link", link);
                        jsonObject.put("image", resultSet.getString("image"));
                        jsonObject.put("files", resultSet.getString("files"));
                        jsonObject.put("name", name);

                        jsonObject.put("duration",resultSet.getString("duration"));
                        jsonObject.put("channame", resultSet.getString("chanel"));
                        jsonObject.put("view",resultSet.getLong("view"));
                        jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                        jsonObject.put("info", resultSet.getString("comment"));
                        jsonObject.put("like", resultSet.getInt("likes"));
                        jsonObject.put("dislike", resultSet.getInt("unlikes"));
                        jsonObject.put("filename", resultSet.getString("files"));
                        jsonObject.put("id", resultSet.getString("id"));
                        jsonArray.put(jsonObject);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }/*catch(JSONException e){
	e.printStackTrace();
}*/
        boolean main = true;
        if (jsonArray.length() < 30) {
            StringBuffer buffer = new StringBuffer(findvideo);
            String[] ms = null;
            String s = null;
            if ((ms = findvideo.split(" ")) != null && ms.length > 0)
                s = buffer.substring(ms[0].length());
            try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
                statement.setString(1, "+" + s);
                //statement.setString(1, "%"+findvideo+"%");

                //D.bugss(statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        JSONObject jsonObject = new JSONObject();
                        //String link = resultSet.getString("link");
                        //if(link.length()<=3)link = "NULL";
                        String name = resultSet.getString("name");
                        //jsonObject.put("link", link);
                        jsonObject.put("image", resultSet.getString("image"));
                        jsonObject.put("files", resultSet.getString("files"));
                        jsonObject.put("name", name);

                        jsonObject.put("duration",resultSet.getString("duration"));
                        jsonObject.put("channame", resultSet.getString("chanel"));
                        jsonObject.put("view",resultSet.getLong("view"));
                        jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                        jsonObject.put("info", resultSet.getString("comment"));
                        jsonObject.put("like", resultSet.getInt("likes"));
                        jsonObject.put("dislike", resultSet.getInt("unlikes"));
                        jsonObject.put("filename", resultSet.getString("files"));
                        jsonObject.put("id", resultSet.getString("id"));
                        if (resultSet.getInt("ban") == 0) {

                            jsonArray.put(jsonObject);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }/*catch(JSONException e){
		e.printStackTrace();
	}*/
        }

        if (jsonArray.length() > 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("find", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            main = Socket.sendbol(conn.conn, "searchvideo;" + json.toString());
        }
        D.bugss("------------------------------------------length---------------" + jsonArray.length());
        jsonArray = new JSONArray();
           if (jsonArray.length() <= 50 && main) {
            try {
                String findjsonString = instrument.SearchVideoOnYoutube(findvideo);
                JSONObject jsonObject = new JSONObject(findjsonString);
                instrument.getVideoLoad(jsonObject, conn);
		/*for(VideoLoad videoLoad:video){
			jsonArray.put(videoLoad.jsonMain);
		}
		*/


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//	jsonObject1 =  
        }
        return jsonObject1;
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public JSONObject getsearchvideo(String findvideo, User conn, WebSocket con) {
        conn.searchlist.put(con, findvideo);
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String arg0 = "SELECT * FROM `video_table` WHERE MATCH (name,comment) AGAINST (? IN BOOLEAN MODE)  ORDER BY `video_table`.`files` DESC;";
//String arg1 = "SELECT * FROM `video_table` WHERE `name` LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            statement.setString(1, "+" + findvideo);
            //statement.setString(1, "%"+findvideo+"%");

            //D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    JSONObject jsonObject = new JSONObject();
                    //String link = resultSet.getString("link");
                    //if(link.length()<=3)link = "NULL";
                    String name = resultSet.getString("name");
                    //jsonObject.put("link", link);
                    jsonObject.put("image", resultSet.getString("image"));
                    jsonObject.put("files", resultSet.getString("files"));
                    jsonObject.put("name", name);

                    jsonObject.put("duration",resultSet.getString("duration"));
                    jsonObject.put("channame", resultSet.getString("chanel"));
                    jsonObject.put("view",resultSet.getLong("view"));

                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                    jsonObject.put("info", resultSet.getString("comment"));
                    jsonObject.put("like", resultSet.getInt("likes"));
                    jsonObject.put("dislike", resultSet.getInt("unlikes"));
                    jsonObject.put("filename", resultSet.getString("files"));
                    jsonObject.put("id", resultSet.getString("id"));
                    if (resultSet.getInt("ban") == 0) {

                        jsonArray.put(jsonObject);
                    } else if (resultSet.getInt("ban") != 0 && (conn.typeUser == TypeUser.ADMIN || conn.typeUser == TypeUser.MODERATOR)) {

                        jsonArray.put(jsonObject);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }/*catch(JSONException e){
	e.printStackTrace();
}*/
        boolean main = true;
        if (jsonArray.length() < 30) {
            StringBuffer buffer = new StringBuffer(findvideo);
            String[] ms = null;
            String s = null;
            if ((ms = findvideo.split(" ")) != null && ms.length > 0)
                s = buffer.substring(ms[0].length());
            try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
                statement.setString(1, "+" + s);
                //statement.setString(1, "%"+findvideo+"%");

                //D.bugss(statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        JSONObject jsonObject = new JSONObject();
                        //String link = resultSet.getString("link");
                        //if(link.length()<=3)link = "NULL";
                        String name = resultSet.getString("name");
                        //jsonObject.put("link", link);
                        jsonObject.put("image", resultSet.getString("image"));
                        jsonObject.put("files", resultSet.getString("files"));
                        jsonObject.put("name", name);
                        jsonObject.put("duration",resultSet.getString("duration"));
                        jsonObject.put("view",resultSet.getLong("view"));

                        jsonObject.put("channame", resultSet.getString("chanel"));

                        jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                        jsonObject.put("info", resultSet.getString("comment"));
                        jsonObject.put("like", resultSet.getInt("likes"));
                        jsonObject.put("dislike", resultSet.getInt("unlikes"));
                        jsonObject.put("filename", resultSet.getString("files"));
                        jsonObject.put("id", resultSet.getString("id"));
                        if (resultSet.getInt("ban") == 0) {

                            jsonArray.put(jsonObject);
                        } else if (resultSet.getInt("ban") != 0 && (conn.typeUser == TypeUser.ADMIN || conn.typeUser == TypeUser.MODERATOR)) {

                            jsonArray.put(jsonObject);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }/*catch(JSONException e){
		e.printStackTrace();
	}*/
        }
        if (jsonArray.length() > 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("find", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            main = Socket.sendbol(con, "searchvideo;" + json.toString());
        }
        D.bugss("------------------------------------------length---------------" + jsonArray.length());
        jsonArray = new JSONArray();
        if (jsonArray.length() <= 50 && main) {
            try {
                String findjsonString = instrument.SearchVideoOnYoutube(findvideo);
                JSONObject jsonObject = new JSONObject(findjsonString);
                instrument.getVideoLoad(jsonObject, conn, con);
		/*for(VideoLoad videoLoad:video){
			jsonArray.put(videoLoad.jsonMain);
		}
		*/


            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//	jsonObject1 =  
        }
        return jsonObject1;
    }

    private final String USER__AGENT = "Mozilla/5.0";

    public String getlinks(String string) throws Exception {
        URL obj = new URL("http://299097.simplecloud.ru/test.php?v=" + string);
        D.bugss("start");
	/*	HttpsURLConnection con =(HttpsURLConnection) obj.openConnection();
     //add reuqest header
      con.setRequestProperty("User-Agent", USER__AGENT);
      con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
     D.bugss("start");
    */
        BufferedReader in = new BufferedReader(
                new InputStreamReader(obj.openStream()));
        String inputLine;
        String response = "";
        while ((inputLine = in.readLine()) != null) {
            response = response + inputLine;
            //if(inputLine.contains("<a href=\"https://redirector.googlevideo.com/")){
            //	return inputLine;
            //}
            // response.append(inputLine+"\n");
        }
        in.close();
        // con.disconnect();

        //print result

        return response;
    }


//////////////////////////////////////////////////////////////////

    public JSONObject getvideofromchan(String channame, User user, WebSocket con) {

        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String arg0 = "SELECT * FROM `video_table` WHERE `chanel` = ?";
//String arg1 = "SELECT * FROM `video_table` WHERE `name` LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(arg0)) {
            statement.setString(1, channame);
            //statement.setString(1, "%"+findvideo+"%");

            //D.bugss(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    JSONObject jsonObject = new JSONObject();
                    //String link = resultSet.getString("link");
                    //if(link.length()<=3)link = "NULL";
                    String name = resultSet.getString("name");
                    //jsonObject.put("link", link);
                    jsonObject.put("image", resultSet.getString("image"));
                    jsonObject.put("files", resultSet.getString("files"));
                    jsonObject.put("name", name);
                    jsonObject.put("duration",resultSet.getString("duration"));

                    jsonObject.put("view",resultSet.getLong("view"));

                    jsonObject.put("channame", resultSet.getString("chanel"));

                    jsonObject.put("timeofpublic",resultSet.getString("timeofpublic"));

                    jsonObject.put("info", resultSet.getString("comment"));
                    jsonObject.put("like", resultSet.getInt("likes"));
                    jsonObject.put("dislike", resultSet.getInt("unlikes"));
                    jsonObject.put("filename", resultSet.getString("files"));
                    jsonObject.put("id", resultSet.getString("id"));
                    if (resultSet.getInt("ban") == 0) {

                        jsonArray.put(jsonObject);
                    } else if (user != null && resultSet.getInt("ban") != 0 && (user.typeUser == TypeUser.ADMIN || user.typeUser == TypeUser.MODERATOR)) {

                        jsonArray.put(jsonObject);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }/*catch(JSONException e){
	e.printStackTrace();
}*/

        if (jsonArray.length() > 0) {
            JSONObject json = new JSONObject();
            try {
                json.put("find", jsonArray);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Socket.send(con, "searchvideo;" + json.toString());
        }

        return jsonObject1;
    }

}
