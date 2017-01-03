package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/30.
 */

public class Equipment implements Serializable {

    private Integer id;

    private Date createDate;
    private Date editDate;

    private User owner;
    private GameService gameservice;

    private String equipname;
    private String equipvalue;

    private Integer equipnumber;

    private String[] equippicture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

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

    public String getEquipvalue() {
        return equipvalue;
    }

    public void setEquipvalue(String equipvalue) {
        this.equipvalue = equipvalue;
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
