package com.AntonSibgatulin.Money;

import java.io.Serializable;
import java.net.Authenticator.RequestorType;

import javax.imageio.IIOException;
import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;
import org.json.JSONObject;

import com.AntonSibgatulin.Main.Main;
import com.AntonSibgatulin.Main.Server;
import ru.brightstudiogamedev.serversidesnapshot.ServerSideSnapshotApplication;

@Table(name = "coin",schema = "public")
public class PurseModel implements Serializable {

    public String login = null;
    public double coinRubles = 0.00000;
    public boolean Operation = false;

    @Column(name = "id_purse")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "money")
    public double getCoinRubles() {
        return coinRubles;
    }

    public void setCoinRubles(double coinRubles) {
        this.coinRubles = coinRubles;
    }

    public boolean isOperation() {
        return Operation;
    }

    public void setOperation(boolean operation) {
        Operation = operation;
    }



    public boolean transform(double price) {
        double i = coinRubles;
        if (Operation) {
            if (i - price >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean translate(boolean bool, String login, int price) {

        if (bool == false) {
            return false;

        } else {
            this.coinRubles -= price;
            JSONObject jsonObject = ServerSideSnapshotApplication.server.base.getUserId(login);
            if (jsonObject.has("login")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public PurseModel(String login, double money) throws Exception {
        if (login != null) {
            this.login = login;
        } else throw new Exception("Warning! String login can't be NULL!");
        this.coinRubles = money;
        System.out.println("Money "+money);
    }

    public PurseModel(String login) {

    }
}
