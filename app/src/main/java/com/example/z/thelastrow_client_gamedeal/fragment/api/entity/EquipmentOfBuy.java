package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/3.
 */

public class EquipmentOfBuy implements Serializable {

    private User owner;
    private GameService gameservice;

    private String equipname;
    private String maxvalue;			//收购最高价
    private String minvalue;			//收购最低价

    private Integer equipnumber;

    private String[] equippicture;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public GameService getGameservice() {
        return gameservice;
    }

    public void setGameservice(GameService gameservice) {
        this.gameservice = gameservice;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public String getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(String maxvalue) {
        this.maxvalue = maxvalue;
    }

    public String getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(String minvalue) {
        this.minvalue = minvalue;
    }

    public Integer getEquipnumber() {
        return equipnumber;
    }

    public void setEquipnumber(Integer equipnumber) {
        this.equipnumber = equipnumber;
    }

    public String[] getEquippicture() {
        return equippicture;
    }

    public void setEquippicture(String[] equippicture) {
        this.equippicture = equippicture;
    }
}
