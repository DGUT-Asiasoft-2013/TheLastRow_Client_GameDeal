package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameServiceFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/23.
 */

public class SellActivity extends Activity {

    private ThingsFragment thingsFragment;
    private GameServiceFragment gameServiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        thingsFragment = new ThingsFragment();
        thingsFragment.setOnSubmitListener(new ThingsFragment.OnSubmitListener() {
            @Override
            public void onSubmit() {
                submit();
            }
        });

        gameServiceFragment = new GameServiceFragment();
        gameServiceFragment.setOnNextStepListener(new GameServiceFragment.OnNextStepListener() {
            @Override
            public void onNextstep() {
                nextStep();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.things_sell_content, gameServiceFragment).commit();
    }

    private void submit() {
        OkHttpClient client = Server.getSharedClient();

        //待编辑
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("game_equip", thingsFragment.getThingsName())
                .addFormDataPart("price", thingsFragment.getThingValue())
                .build();

        Request request = Server.requestBuilderWithApi("/good").post(multipartBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SellActivity.this.showAlertDialog(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            SellActivity.this.showAlertDialog(responseString);
                        } catch (Exception e) {
                            SellActivity.this.showAlertDialog(e.toString());
                        }

                    }
                });

            }
        });
    }

    private void nextStep() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_sell_content, thingsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showAlertDialog(String string) {
        new AlertDialog.Builder(SellActivity.this)
                .setMessage(string)
                .setPositiveButton("好", null).show();
    }
}
