package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Good;

import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Like;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Payments;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.GoodNumberFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/28.
 */

public class GoodActivity extends Activity {
    ActionBarFragment actionBarFragment;
    GoodNumberFragment goodNumberFragment;
    TextView    text_price_bottom,text_title,text_price,text_name,text_area;
    Button btn_buy,btn_like,btn_comment;

    Handler handler;
    Good good;
    Integer like_number=0;
    Boolean like_state=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        handler=new Handler();
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.good_frag_action_bar);
        actionBarFragment.setTitle("商品详情");

        text_price_bottom=(TextView)findViewById(R.id.good_text_game_price_bottom);
        text_title=(TextView)findViewById(R.id.good_text_title);
        text_price=(TextView)findViewById(R.id.good_text_game_price);
        text_name=(TextView)findViewById(R.id.good_text_game_name);
        text_area=(TextView)findViewById(R.id.good_text_game_area);

        goodNumberFragment= (GoodNumberFragment) getFragmentManager().findFragmentById(R.id.good_frag_number);


        btn_like= (Button) findViewById(R.id.good_btn_like);
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLikeLisener();
            }
        });
        btn_comment= (Button) findViewById(R.id.good_btn_see_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCommentLisener();
            }
        });
        btn_buy= (Button) findViewById(R.id.good_btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBuyLisener();
            }
        });

        good= (Good) getIntent().getSerializableExtra("good");

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            countLike();
            Thread.sleep(500);
            checkLike();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        text_title.setText(good.getGame_equip());
        text_name.setText(good.getGame_name());
        text_area.setText(good.getGame_area());
        text_price.setText("￥"+good.getPrice());
        text_price_bottom.setText(""+good.getPrice());
        goodNumberFragment.ediTextChange(good.getPrice()+"",9,text_price_bottom);
    }

    public void btnLikeLisener() {
        // 判断是否登录
        if (Server.getUser()==null){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {

            boolean state=true;
            String s=btn_like.getText().toString();
            if(like_state){
                state=false;
                like_state=false;
            }else {
                state=true;
                like_state=true;
            }
            MultipartBody requestBody=new MultipartBody.Builder()
                    .addFormDataPart("likes",String.valueOf(state))
                    .build();
            Request request=Server.requestBuilderWithApi("good/"+good.getId()+"/likes")
                    .post(requestBody)
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(like_state){
                                btn_like.setText("已收藏("+countLike()+")");
                            }else {
                                btn_like.setText("收藏("+countLike()+")");
                            }

                        }
                    });

                }
            });
        }

    }

    public boolean checkLike(){
        Request request=Server.requestBuilderWithApi("good/"+good.getId()+"/isliked")
                .get()
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                like_state=new ObjectMapper().readValue(response.body().string(),Boolean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (like_state){
                            btn_like.setText("已收藏("+like_number+")");
                        }
                    }
                });
            }
        });
        return like_state;
    }

    public int countLike(){

        final Request request=Server.requestBuilderWithApi("good/"+good.getId()+"/likes")
                .get()
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                like_number= new ObjectMapper().readValue(response.body().string(),Integer.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(like_state){
                            btn_like.setText("已收藏("+like_number+")");
                        }else {
                            btn_like.setText("收藏("+like_number+")");
                        }
                    }
                });

            }
        });

        return like_number;
    }


    public void btnCommentLisener() {
    }
    public void btnBuyLisener() {
        // 判断是否登录
        if (Server.getUser()==null){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        // 判断余额是否足够
        else {
            int userMoney= Server.getUser().getMoney();
            int itemMoney=Integer.parseInt(text_price_bottom.getText().toString());
            if (itemMoney>userMoney){
                ToastAndDialog.setToastShort(getApplicationContext(),"账户余额不足");
            }else{
                        new  AlertDialog.Builder(GoodActivity.this)
                                .setMessage("确定购买？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        buy();
                                    }
                                })
                                .setNegativeButton("取消",null)
                                .create()
                                .show();

                    }

            }


    }

    public void  buy(){
        MultipartBody requestBody=new MultipartBody.Builder()
                .addFormDataPart("good_number",String.valueOf(goodNumberFragment.getEdiTextNumber()))
                .build();
        Request request=Server.requestBuilderWithApi("user/"+good.getId()+"/payments")
                .post(requestBody)
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final Payments payments=new ObjectMapper().readValue(response.body().string(),Payments.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastAndDialog.setToastShort(GoodActivity.this,payments.getUser().getName()+"购买成功");
                    }
                });
            }
        });

    }
}
