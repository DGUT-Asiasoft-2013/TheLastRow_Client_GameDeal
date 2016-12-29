package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameServiceFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsSellFragment;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/23.
 */

public class SellActivity extends Activity {

    private ThingsSellFragment thingsSellFragment;
    private GameServiceFragment gameServiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        thingsSellFragment = new ThingsSellFragment();
        thingsSellFragment.setOnSubmitListener(new ThingsSellFragment.OnSellSubmitListener() {
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
        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .addFormDataPart("game_equip", thingsSellFragment.getThingsName())
                .addFormDataPart("price", thingsSellFragment.getThingValue())
                .addFormDataPart("game_name",gameServiceFragment.getGameName())
                .addFormDataPart("game_company",gameServiceFragment.getCompanyName())
                .addFormDataPart("game_account",gameServiceFragment.getGameId())
                .addFormDataPart("game_area",gameServiceFragment.getGameService());

        if (thingsSellFragment.getThingPicture() != null) {
            Calendar calendar = Calendar.getInstance();
            String string = "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);
            multipartBody.addFormDataPart("avatar_img1", "equip_picture" + string + System.currentTimeMillis(), RequestBody.create(MediaType.parse("image/png"), thingsSellFragment.getThingPicture()));
        }


        Request request = Server.requestBuilderWithApi("/Good").post(multipartBody.build()).build();

        final ProgressDialog progressDialog = new ProgressDialog(SellActivity.this);
        progressDialog.setMessage("请稍等");
        //点屏幕和物理返回键退出进度对话框
        progressDialog.setCancelable(false);
        progressDialog.show();
        //物理返回键可以退出进度框，点屏幕无效
        progressDialog.setCanceledOnTouchOutside(false);


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
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

                        progressDialog.dismiss();
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
                .replace(R.id.things_sell_content, thingsSellFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showAlertDialog(String string) {
        new AlertDialog.Builder(SellActivity.this)
                .setMessage(string)
                .setPositiveButton("好", null).show();
    }
}
