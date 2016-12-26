package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.inputcells.SimpleTextInputCellFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/22.
 */

public class LoginActivity extends Activity {
    ActionBarFragment actionBarFragment;
    SimpleTextInputCellFragment userName;
    SimpleTextInputCellFragment pwd;
    Button btn;
    ProgressDialog pdlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.login_frag_action_bar);
        userName=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.login_user_name);
        pwd=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.login_user_pwd);
        btn=(Button)findViewById(R.id.login_button);

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar();
        login();
    }

//    登录按钮出发的事件
    private void login() {
        userName.setLabelText("用户名：");
        userName.setHintText("请输入用户名");
        pwd.setLabelText("密码：");
        pwd.setHintText("请输入密码");
        pwd.setIsPassword(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account=userName.getText();
                final String passward=pwd.getText();
                //判断是否为空
                if(account.contentEquals("") || passward.contentEquals("")){
                    Toast.makeText(LoginActivity.this,"不能为空 ",Toast.LENGTH_SHORT).show();
                }else {
                    pdlg =new ProgressDialog(LoginActivity.this);
                    pdlg.setCanceledOnTouchOutside(true);
                    pdlg.setMessage("正在登陆");
                    pdlg.show();
                    MultipartBody requestBody=new MultipartBody.Builder()
                            .addFormDataPart("account",account)
                            .addFormDataPart("passwordHash",MD5.getMD5(passward))
                            .build();
                    Request request=Server.requestBuilderWithApi("login")
                            .post(requestBody)
                            .build();
                    Server.getSharedClient().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pdlg.setMessage(e.toString());
//                                try {
//                                    Thread.sleep(300);
//
//                                } catch (InterruptedException ee) {
//                                    ee.printStackTrace();
//                                }
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final User user=new ObjectMapper().readValue(response.body().string(),User.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pdlg.setMessage(response.toString());
                                    try {
                                        if(user!=null){
                                            Thread.sleep(200);
                                            pdlg.dismiss();
                                            Server.getUser();
                                            finish();

                                            Toast.makeText(LoginActivity.this,"欢迎回来 "+account ,Toast.LENGTH_SHORT).show();
                                        }else{
                                            ToastAndDialog.setToastShort(LoginActivity.this,"密码错误");
                                        }

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }
            }
        });
    }

//    ActionBar触发的事件
    private void actionBar() {
        actionBarFragment.setText("注册");
        actionBarFragment.setTitle("登录");
        actionBarFragment.setField(new ActionBarFragment.SetTextClick() {
            @Override
            public void setTextClickLisener() {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
