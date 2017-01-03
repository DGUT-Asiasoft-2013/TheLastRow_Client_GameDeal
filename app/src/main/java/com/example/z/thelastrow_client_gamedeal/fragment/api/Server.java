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
    public static String serverAddress = "http://172.27.15.20:8080/membercenter/";
//	public static String serverAddress = "http://172.27.148.80:8080/membercenter/";


    public static Request.Builder requestBuilderWithApi(String api) {
        return new Request.Builder()
                .url(serverAddress + "api/" + api);
    }

    public static User getUser() {
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
        return user;
    }
    public static void setUser(User u){
        user=u;
    }
}