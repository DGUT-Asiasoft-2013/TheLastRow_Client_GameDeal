package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/30.
 */

public class Company implements Serializable {

    private Integer id;
    private String companyname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
}
