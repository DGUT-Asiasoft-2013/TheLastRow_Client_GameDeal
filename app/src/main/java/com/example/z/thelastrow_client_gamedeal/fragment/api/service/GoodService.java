package com.example.z.thelastrow_client_gamedeal.fragment.api.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by z on 2017/1/6.
 */

public class GoodService {
    private Bitmap bmp;
    private String imgUrl;

    public Bitmap getBmp(String urlWithoutMemberCenter) {
        imgUrl = urlWithoutMemberCenter;

        new imgThread().start();

        return bmp;
    }

    private class imgThread extends Thread {
        @Override
        public void run() {
            super.run();

            Request request = new Request.Builder()
                    .url(Server.serverAddress + imgUrl)
                    .get()
                    .build();

            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call arg0, Response arg1) throws IOException {

                    byte[] bytes = arg1.body().bytes();
                    try {
                        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    } catch (Exception e) {
                        bmp = null;
                    }
                }

                @Override
                public void onFailure(Call arg0, IOException arg1) {
                    bmp = null;
                }
            });
        }
    }
}
