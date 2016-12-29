package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.PictureInputCellFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/27.
 */

public class TestActivity extends Activity {
    PictureInputCellFragment pictureInputCellFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        pictureInputCellFragment=(PictureInputCellFragment)getFragmentManager().findFragmentById(R.id.input_avatar_test);
        Button btn=(Button)findViewById(R.id.test_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    public void submit(){
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("game_equip", "雷诺")
                .addFormDataPart("price", "100")
                .addFormDataPart("game_name", "QQ飞车")
                .addFormDataPart("game_account", "10086")
                .addFormDataPart("game_area", "电信一区")
                .addFormDataPart("game_company", "腾讯");


        if(pictureInputCellFragment.getPngData()!=null){
            requestBodyBuilder
                    .addFormDataPart(
                            "avatar_img1",
                            "avatar_img1",
                            RequestBody
                                    .create(MediaType.parse("image/png"),
                                            pictureInputCellFragment.getPngData()));
        }

        Request request = Server.requestBuilderWithApi("Good")
                .method("post", null)
                .post(requestBodyBuilder.build())
                .build();

        final ProgressDialog progressDialog = new ProgressDialog(TestActivity.this);
        progressDialog.setMessage("正在注册");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Server.getSharedClient().newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, final Response arg1) throws IOException {
                final String responseString = arg1.body().string(); //??????????????????????е???
                runOnUiThread(new Runnable() {
                    public void run() {
//                        progressDialog.dismiss();
                        progressDialog.setMessage(responseString);
                    }
                });
            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                runOnUiThread(new Runnable() {
                    public void run() {
//                        progressDialog.dismiss();
                        progressDialog.setMessage("shibai");

                    }
                });
            }
        });
    }
}
