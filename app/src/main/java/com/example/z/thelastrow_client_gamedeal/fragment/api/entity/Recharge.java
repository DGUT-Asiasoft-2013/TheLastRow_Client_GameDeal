package com.example.z.thelastrow_client_gamedeal.fragment.api.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recharge  implements Serializable {
	User user;
	String money;
	String moneyrecord;
	Date createDate;
	Date editDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMoneyrecord() {
		return moneyrecord;
	}

	public void setMoneyrecord(String moneyrecord) {
		this.moneyrecord = moneyrecord;
	}

	public String getStringDate(){
		return android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss",createDate).toString();
	}
	}

	
	

