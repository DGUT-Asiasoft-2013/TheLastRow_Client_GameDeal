package com.example.z.thelastrow_client_gamedeal.fragment.api.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.MD5;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by z on 2017/1/6.
 */

public class LoginService {

    public void Login(final Context context, final String account, String passward) {
        MultipartBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("account", account)
                .addFormDataPart("passwordHash", MD5.getMD5(passward))
                .build();
        Request request = Server.requestBuilderWithApi("login")
                .post(requestBody)
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final User user = new ObjectMapper().readValue(response.body().string(), User.class);
                Server.setUser(user);
            }

        });

    }
}
