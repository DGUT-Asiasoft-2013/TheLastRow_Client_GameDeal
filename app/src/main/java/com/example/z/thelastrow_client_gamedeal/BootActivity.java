package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BootActivity extends Activity {
//Handler handler;

    private Button pass;
    private int passtime;
    private Timer timer;
    private ImageView advertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
//        handler=new Handler();
        pass = (Button)findViewById(R.id.boot_pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

        advertisement = (ImageView) findViewById(R.id.boot_imageView);

        Server.getUser();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(BootActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },1000);
//    }

    @Override
    protected void onResume() {
        super.onResume();


        int random = (new Random()).nextInt()%100 + 1;
        if (random < 50) {
            advertisement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.advertisement2));
        } else {
            advertisement.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.advertisement));
        }



        passtime = 5;
        timer = new Timer();

        if (pass == null) {
            pass = (Button)findViewById(R.id.boot_pass);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (passtime <= 0) {
                            startMainActivity();
                        } else {
                            pass.setText("跳过" + passtime);
                            passtime--;
                        }
                    }
                });
            }
        },0,1000);



    }

    private void startMainActivity() {
        if (timer != null) {
            timer.cancel();
        }
        startActivity(new Intent(BootActivity.this,MainActivity.class));
        finish();
    }
}
