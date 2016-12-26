package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.inputcells.SimpleTextInputCellFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.PictureInputCellFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/22.
 */

public class RegisterActivity extends Activity {
    SimpleTextInputCellFragment fragInputCellAccount;
    SimpleTextInputCellFragment fragInputName;
    SimpleTextInputCellFragment fragInputEmailAddress;
    SimpleTextInputCellFragment fragInputCellPassword;
    SimpleTextInputCellFragment fragInputCellPasswordRepeat;
    PictureInputCellFragment fragInputAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.register_user_account);
        fragInputEmailAddress = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.register_user_email);
        fragInputName = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.register_user_name);
        fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.register_user_pwd);
        fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.register_user_pwd_repeat);
        fragInputAvatar = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragInputName.setLabelText("昵称：");
        fragInputName.setHintText("请输入昵称");
        fragInputCellAccount.setLabelText("用户名：");
        fragInputCellAccount.setHintText("请输入用户名");

        fragInputCellPassword.setLabelText("密码：");
        fragInputCellPassword.setIsPassword(true);
        fragInputCellPassword.setHintText("请输入密码");
        fragInputCellPasswordRepeat.setLabelText("重复密码：");
        fragInputCellPasswordRepeat.setIsPassword(true);
        fragInputCellPasswordRepeat.setHintText("请再次输入密码");

        fragInputEmailAddress.setLabelText("邮箱：");
        fragInputEmailAddress.setHintText("请输入邮箱");
    }

    private void submit() {
        String password = fragInputCellPassword.getText();
        String passwordRepeat = fragInputCellPasswordRepeat.getText();

        if(!password.equals(passwordRepeat)){

            new AlertDialog.Builder(RegisterActivity.this)
                    .setMessage("两次输入密码不一致")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setNegativeButton("确定", null)
                    .show();

            return;
        }

        password = MD5.getMD5(password);

        String account = fragInputCellAccount.getText();
        String name = fragInputName.getText();
        String email = fragInputEmailAddress.getText();

        OkHttpClient client = Server.getSharedClient();

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", account)
                .addFormDataPart("passwordHash", password)
                .addFormDataPart("email", email)
                .addFormDataPart("name", name);

        if(fragInputAvatar.getPngData()!=null){
            requestBodyBuilder
                    .addFormDataPart(
                            "avatar",
                            "avatar",
                            RequestBody
                                    .create(MediaType.parse("image/png"),
                                            fragInputAvatar.getPngData()));
        }

        Request request = Server.requestBuilderWithApi("register")
                .method("post", null)
                .post(requestBodyBuilder.build())
                .build();

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("正在注册");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, final Response arg1) throws IOException {
                final String responseString = arg1.body().string(); //??????????????????????е???
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                        try {
                            RegisterActivity.this.onResponse(arg0, responseString);
                        } catch (Exception e) {
                            e.printStackTrace();
                            RegisterActivity.this.onFailure(arg0, e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();

                        RegisterActivity.this.onFailure(arg0, arg1);
                    }
                });
            }
        });
    }

    void onResponse(Call arg0, String responseBody){
        new AlertDialog.Builder(this)
                .setTitle("成功")
                .setMessage(responseBody)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    void onFailure(Call arg0, Exception arg1) {
        new AlertDialog.Builder(this)
                .setTitle("失败")
                .setMessage(arg1.getLocalizedMessage())
                .setNegativeButton("确定", null)
                .show();
    }
}
