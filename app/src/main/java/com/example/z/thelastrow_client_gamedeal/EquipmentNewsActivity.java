package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2016/12/23.
 */

public class EquipmentNewsActivity extends Activity {

    private TextView companyname, recharge;
    private ListView list;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipmentnews);

        String companynameGet = "获取失败";
        int logoGet = -1;

        Intent intent = getIntent();
        if (intent != null) {
            companynameGet = intent.getStringExtra("companyname");
            logoGet = intent.getIntExtra("logo",-1);
        }

        companyname = (TextView) findViewById(R.id.equipmentnews_companyname);
        companyname.setText(companynameGet);

        logo = (ImageView) findViewById(R.id.equipmentnews_companylogo);
        logo.setImageBitmap(BitmapFactory.decodeResource(getResources(),logoGet));

        recharge = (TextView) findViewById(R.id.equipmentnews_recharge);
        list = (ListView) findViewById(R.id.equipmentnews_list);

        findViewById(R.id.equipmentnews_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Bitmap readLogo(String companyname) {
        File filePath = new File(Environment.getExternalStorageDirectory(), "GameDeal");
        if (!filePath.exists()) {
            return null;
        } else {
            File fileLogo = new File(filePath , companyname);
            if (fileLogo.exists()) {
                try {
                    Log.d("Logo","已存在本地文件中");
                    FileInputStream fis = new FileInputStream(fileLogo);
                    Bitmap bmp = BitmapFactory.decodeStream(fis);
                    fis.close();
                    return bmp;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return null;
    }
}
