package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30.
 */

public class GameService implements Serializable {

    private Integer id;

    private Game gamename;
    private String gameservicename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGamename() {
        return gamename;
    }

    public void setGamename(Game gamename) {
        this.gamename = gamename;
    }

    public String getGameservicename() {
        return gameservicename;
    }

    public void setGameservicename(String gameservicename) {
        this.gameservicename = gameservicename;
    }
}
