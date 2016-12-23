package com.example.z.thelastrow_client_gamedeal.fragment.api;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {
	static OkHttpClient client;
	
	static {
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		client = new OkHttpClient.Builder()
				.cookieJar(new JavaNetCookieJar(cookieManager))
				.build();
	}
	
	public static OkHttpClient getSharedClient(){
		return client;
	}
	//192.168.253.4  //宿舍-172.27.148.80:8080
	public static String serverAddress = "http://192.168.253.4:8080/membercenter/";
	
	public static Request.Builder requestBuilderWithApi(String api){
		return new Request.Builder()
		.url(serverAddress+"api/"+api);
	}
}
