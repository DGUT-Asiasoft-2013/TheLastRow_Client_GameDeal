package com.example.z.thelastrow_client_gamedeal.fragment.api.service;

import android.app.ProgressDialog;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Good;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Like;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by z on 2017/1/5.
 */

public class LikeService {
    boolean like_state;
    List<Like> list_like;
    Good good;
    User user;
    boolean s;
    int i=0;
    public boolean postLike(Good good,boolean like_state){
        this.good=good;

        MultipartBody requestBody=new MultipartBody.Builder()
                .addFormDataPart("likes",String.valueOf(like_state))
                .build();
        Request request=Server.requestBuilderWithApi("good/"+good.getId()+"/likes")
                .post(requestBody)
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                s=false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                s=true;
            }
        });
        return s;
    }
    public boolean checkLike(Good good){
        this.good=good;
        new Thread(new checkLikeThread()).start();
        return like_state;
    }
    public int countLike(){
        int i=0;
        return i;
    }

    public List<Like> getLikesByUserID(User user, ProgressDialog pdlg){
        this.user=user;
        i=0;
        new Thread(new getLikesThread()).start();
        while (i==0){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pdlg.dismiss();
        return list_like;
    }

    private class  checkLikeThread extends Thread{
        @Override
        public void run() {
            super.run();
            Request request= Server.requestBuilderWithApi("good/"+good.getId()+"/isliked")
                    .get()
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    like_state=new ObjectMapper().readValue(response.body().string(),Boolean.class);
                }
            });
        }
    }

    private  class  getLikesThread extends Thread{
        @Override
        public void run() {
            super.run();
            final Request request= Server.requestBuilderWithApi("user/"+user.getId()+"/likes")
                    .get()
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    i=1;
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    Page<Like> page=new ObjectMapper().readValue(response.body().string(), new TypeReference< Page<Like>>(){});
                    list_like=page.getContent();
                    i=1;
                }
            });
        }
    }
}
