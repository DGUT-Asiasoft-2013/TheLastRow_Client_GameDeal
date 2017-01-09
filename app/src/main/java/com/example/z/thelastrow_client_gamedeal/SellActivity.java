package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameIDFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameServiceFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsSellFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
    private GameIDFragment gameIDFragment;
    private GameFragment gameFragment;
    private GameServiceFragment gameServiceFragment;
    private String gamenamestring, gameservicenamestring, gameidstring, thingsname, thingsvalue, thingsnumber;
    private List<String> thingsPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);


        gameServiceFragment = new GameServiceFragment();
        gameServiceFragment.setOnGameServiceItemClick(new GameServiceFragment.OnGameServiceItemClick() {
            @Override
            public void onItemClick(View view) {
                ongameIDClick(view);
            }
        });

        gameFragment = new GameFragment();
        gameFragment.setOnGameItemClick(new GameFragment.OnGameItemClick() {
            @Override
            public void onItemClick(View view) {
                ongameserviceClick(view);
            }
        });

        thingsSellFragment = new ThingsSellFragment();
        thingsSellFragment.setOnSubmitListener(new ThingsSellFragment.OnSellSubmitListener() {
            @Override
            public void onSubmit() {
                submit();
            }
        });

        gameIDFragment = new GameIDFragment();
        gameIDFragment.setOnNextStepListener(new GameIDFragment.OnNextStepListener() {
            @Override
            public void onNextstep() {
                nextStep();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.things_sell_content, gameFragment).commit();
    }

    private void ongameIDClick(View view) {
        TextView gameservicename = (TextView) view.findViewById(R.id.gameservice_listitem_text);
        gameservicenamestring = gameservicename.getText().toString();

        Bundle data = new Bundle();
        data.putString("gameservicename", gameservicenamestring);
        data.putString("gamename", gamenamestring);

        gameIDFragment.setArguments(data);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_sell_content, gameIDFragment)
                .addToBackStack(null)
                .commit();
    }

    private void ongameserviceClick(View view) {

        TextView gamename = (TextView) view.findViewById(R.id.game_listitem_text);
        gamenamestring = gamename.getText().toString();

        Bundle data = new Bundle();
        data.putString("gamename", gamenamestring);

        gameServiceFragment.setArguments(data);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_sell_content, gameServiceFragment)
                .addToBackStack(null)
                .commit();
    }

    private void submit() {

        thingsname = thingsSellFragment.getThingsName();
        thingsvalue = thingsSellFragment.getThingValue();
        thingsnumber = thingsSellFragment.getThingNumber();
        thingsPictures = thingsSellFragment.getPictures();


        OkHttpClient client = Server.getSharedClient();

        //待编辑
        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .addFormDataPart("gamename", gamenamestring)
                .addFormDataPart("gameservicename", gameservicenamestring)
                .addFormDataPart("equipname", thingsname)
                .addFormDataPart("equipvalue", thingsvalue)
                .addFormDataPart("gameid", gameidstring)
                .addFormDataPart("equipnumber", thingsnumber)
                .addFormDataPart("isSell", "true");

        for (String picture : thingsPictures) {
            if (picture != null) {

                Bitmap bmp = BitmapFactory.decodeFile(picture);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    Calendar calendar = Calendar.getInstance();
//                    String string = "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);
                    multipartBody.addFormDataPart("pictures",
                            "_equip_picture" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND),
                            RequestBody.create(MediaType.parse("image/png"), baos.toByteArray()));
            }
        }


        Request request = Server.saveEquipment(gamenamestring, gameservicenamestring).post(multipartBody.build()).build();

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

        gameidstring = gameIDFragment.getGameId();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_sell_content, thingsSellFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showAlertDialog(String string) {
        new AlertDialog.Builder(SellActivity.this)
                .setMessage(string)
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
