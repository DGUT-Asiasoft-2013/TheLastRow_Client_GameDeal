package com.example.z.thelastrow_client_gamedeal.fragment.api;

import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Server {

	static OkHttpClient client;
	 static User user;
	static {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		client = new OkHttpClient.Builder()
				.cookieJar(new JavaNetCookieJar(cookieManager))
				.build();
	}



    public static OkHttpClient getSharedClient() {
        return client;
    }

    //192.168.253.4  //宿舍-172.27.148.80:8080
    public static String serverAddress = "http://172.27.15.26:8080/membercenter/";
//    public static String serverAddress = "http://172.27.15.20:8080/membercenter/";
//	public static String serverAddress = "http://172.27.148.80:8080/membercenter/";

    public static Request.Builder getAllGame(){
        return (new Request.Builder().url(serverAddress + "equip/getgame"));
    }

    public static Request.Builder getAllGameService(String gamename){
        return (new Request.Builder().url(serverAddress + "equip/getgameservice/bygame/" + gamename));
    }

    public static Request.Builder getAllEquipment() {
        return (new Request.Builder().url(serverAddress + "equip/getequipment"));
    }

    public static Request.Builder saveEquipment(String gamename,String gameservicename){
        return (new Request.Builder().url(serverAddress + "equip/saveequipment/" + gamename +"/"+ gameservicename));
    }

    public static Request.Builder getEquipmentNew10() {
        return (new Request.Builder().url(serverAddress + "equip/getequipmentnew10"));
    }

    public static Request.Builder getEquipmentBySearch(String equipname) {
        return (new Request.Builder().url(serverAddress + "equip/getequipment/byequipname/" + equipname));
    }

    public static Request.Builder requestBuilderWithApi(String api) {
        return new Request.Builder()
                .url(serverAddress + "api/" + api);
    }

    public static User getUser() {

        return user;
    }
    public static void setUser(User u){
        user=u;
    }
    public  void setUser(){
        new Thread(new latestUserThread()).start();
    }
    private  class latestUserThread extends Thread{
        @Override
        public void run() {
            super.run();
            Request request = requestBuilderWithApi("me")
                    .get()
                    .build();
            getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    user = new ObjectMapper().readValue(response.body().string(), User.class);
                }
            });
        }
    }
}