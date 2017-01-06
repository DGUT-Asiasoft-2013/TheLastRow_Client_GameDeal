package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Z on 2016/12/29.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Like implements Serializable {

    public static class Key implements Serializable {
        User user;
        Good good;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Good getGood() {
            return good;
        }

        public void setGood(Good good) {
            this.good = good;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key other = (Key) obj;
                return good.getId() == other.good.getId() && user.getId() == other.user.getId();
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return good.getId();
        }
    }

    Key id;
    Date createDate;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }


}
