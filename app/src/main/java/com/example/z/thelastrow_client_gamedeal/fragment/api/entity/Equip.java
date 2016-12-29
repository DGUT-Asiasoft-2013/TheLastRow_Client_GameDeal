package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/28.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equip implements Serializable {

    String gamename = new String();
    String companyname = new String();
    String gameservice = new String();
    String thignsname = new String();
    String thingsvalue = new String();

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getGameservice() {
        return gameservice;
    }

    public void setGameservice(String gameservice) {
        this.gameservice = gameservice;
    }

    public String getThignsname() {
        return thignsname;
    }

    public void setThignsname(String thignsname) {
        this.thignsname = thignsname;
    }

    public String getThingsvalue() {
        return thingsvalue;
    }

    public void setThingsvalue(String thingsvalue) {
        this.thingsvalue = thingsvalue;
    }
}
