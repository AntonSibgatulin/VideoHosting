package com.AntonSibgatulin.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.java_websocket.WebSocket;

import com.AntonSibgatulin.AuthKey.Auth;
import com.AntonSibgatulin.Debug.D;
import com.AntonSibgatulin.Device.Device;
import com.AntonSibgatulin.Main.Main;
import com.AntonSibgatulin.Money.PurseModel;
import com.AntonSibgatulin.Socket.Socket;
import ru.brightstudiogamedev.serversidesnapshot.ServerSideSnapshotApplication;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "public")
public class User implements Serializable {

    public PurseModel purseModel = null;

    public Device device = null;
    public String name, surname;
    public String login;

    public String groupes = null;
    public String city = null;
    public String mail = null;
    public Integer ban = 0;
    public Long id = 0L;
    public Integer typeuser = 0;
    public TypeUser typeUser = null;
    public String password;

    public HashMap<WebSocket, String> searchlist = new HashMap<>();
    public String setting = null;

    public Integer type=0;

    public HashMap<String, User> auth_key = new HashMap<>();
    public String decode = "";
    public String Auth = "";

    public Long date = 0L;

    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Column(name="date_register")
    public Long getDate_register() {
        return date_register;
    }

    public void setDate_register(Long date_register) {
        this.date_register = date_register;
    }

    public Long date_register = 0L;
    public String[] groupe_split = null;
    private String code;

    private Integer countOfSubscribed =0;



    @Column(name = "countOfSubscribed")
    public Integer getCountOfSubscribed() {
        return countOfSubscribed;
    }

    public void setCountOfSubscribed(Integer countOfSubscribed) {
        this.countOfSubscribed = countOfSubscribed;
    }




    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name = "groupes")
    public String getGroupes() {
        return groupes;
    }

    public void setGroupes(String groupes) {
        this.groupes = groupes;
    }


    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "decode")
    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    @Column(name = "auth")
    public String getAuth() {
        return Auth;
    }

    public void setAuth(String auth) {
        Auth = auth;
    }


    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "Email")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "ban")
    public Integer getBan() {
        return ban;
    }

    public void setBan(Integer ban) {
        this.ban = ban;
    }



    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

   public void setId(Long id) {
        this.id =id;
    }

/*
    public void setId(Integer id) {
        this.id = (long)id;
    }

 */

    @Column(name = "setting")
    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }


    public void set_purse_base() {
        D.log("why dont working?");
        this.purseModel = ServerSideSnapshotApplication.server.base.getPurseUser(this, login);
    }


    public void set_purse(PurseModel purseModel) {
        this.purseModel = purseModel;
    }


    public void getGroupe(WebSocket conn) {
        Socket.send(conn, "groups;addAll;" + groupes);
    }

    public User() {

    }

    public User(Long id, String login, String passw) {
        //D.log(id);
        this.login = login;
        this.id = id;
        this.password = passw;
        this.set_purse_base();
    }

    public User(int i, String string, Object passw) {
        this.id = id;
        this.login = string;
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "[USER] ID: "+getId()+" Login: "+getLogin()+" Password: "+getPassword()+" E-mail: "+getMail()+" City: "+getCity()+" Name: "
                +getName()+" Surename: "+getSurname()+" "+getType();
    }
}
