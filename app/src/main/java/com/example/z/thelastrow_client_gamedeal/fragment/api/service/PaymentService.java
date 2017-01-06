package com.example.z.thelastrow_client_gamedeal.fragment.api.service;

import android.os.Handler;
import android.widget.ListView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Payments;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by z on 2017/1/6.
 */

public class PaymentService {
    int i;
    ListView listView;
    Page<Payments> page_pay;
    List<Payments> data;
    Handler handler=new Handler();

    public List<Payments> getPayment(){
        i=0;
        new Thread(new getPaymentThread()).start();
        while (i == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
    private class getPaymentThread extends Thread{
        @Override
        public void run() {
            super.run();
            Request request= Server.requestBuilderWithApi("/user/"+Server.getUser().getId()+"/payments")
                    .get()
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    i=1;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    page_pay=new ObjectMapper().readValue(response.body().string(), new TypeReference<Page<Payments>>() {});
                    data=page_pay.getContent();
                    i=1;
                }
            });

        }
    }
}
