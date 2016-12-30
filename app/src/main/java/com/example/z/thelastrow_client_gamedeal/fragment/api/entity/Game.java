package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30.
 */

public class Game implements Serializable {

    private Integer id;

    private Company companyname;
    private String gamename;

    public Company getCompanyname() {
        return companyname;
    }

    public void setCompanyname(Company companyname) {
        this.companyname = companyname;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
