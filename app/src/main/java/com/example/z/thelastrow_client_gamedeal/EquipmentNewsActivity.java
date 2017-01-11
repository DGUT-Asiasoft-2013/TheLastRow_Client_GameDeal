package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.fragment.api.ImageDownload;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/23.
 */

public class EquipmentNewsActivity extends Activity {

    private TextView companyname, recharge;
    private ListView list;
    private List<Equipment> equipmentList;
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
            logoGet = intent.getIntExtra("logo", -1);
        }

        companyname = (TextView) findViewById(R.id.equipmentnews_companyname);
        companyname.setText(companynameGet);

        logo = (ImageView) findViewById(R.id.equipmentnews_companylogo);
        logo.setImageBitmap(BitmapFactory.decodeResource(getResources(), logoGet));

        recharge = (TextView) findViewById(R.id.equipmentnews_recharge);

        equipmentList = new ArrayList<>();
        list = (ListView) findViewById(R.id.equipmentnews_list);
        list.setAdapter(listAdapter);

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

        reload();
    }

    private void reload() {

        Server.getSharedClient().newCall(
                Server.getEquipmentByCompany(companyname.getText().toString().trim()).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EquipmentNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final List<Equipment> dataGet = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Equipment>>() {
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            equipmentList = dataGet;
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EquipmentNewsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private BaseAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (equipmentList == null ? 0 : equipmentList.size());
        }

        @Override
        public Object getItem(int position) {
            return equipmentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_goods_item, null);
            }

            final ImageView equipmentPicture = (ImageView) convertView.findViewById(R.id.list_good_avatar);
            TextView gamenameAndService = (TextView) convertView.findViewById(R.id.list_good_text_title);
            TextView equipname = (TextView) convertView.findViewById(R.id.list_good_text_equip);
            TextView equipvalue = (TextView) convertView.findViewById(R.id.list_good_text_price);

            final Equipment equipment = equipmentList.get(position);

            if (equipment != null) {

                gamenameAndService.setText("[" + equipment.getGameservice().getGame().getGamename() + "][" + equipment.getGameservice().getGameservicename() + "]");
                equipname.setText(equipment.getEquipname());
                equipvalue.setText(equipment.getEquipvalue());

            }

            if (equipment != null && equipment.getEquippicture() != null) {
                new ImageDownload(parent.getContext()).download(equipmentPicture, equipment.getEquippicture()[0],
                        new ImageDownload.ImageDownloadBack() {
                            @Override
                            public void onBack(ImageView imageView, Bitmap bmp) {
                                imageView.setImageBitmap(bmp);
                            }
                        }, 0);
            } else {
                equipmentPicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_50));
            }

            return convertView;
        }
    };


//    private Bitmap readLogo(String companyname) {
//        File filePath = new File(Environment.getExternalStorageDirectory(), "GameDeal");
//        if (!filePath.exists()) {
//            return null;
//        } else {
//            File fileLogo = new File(filePath , companyname);
//            if (fileLogo.exists()) {
//                try {
//                    Log.d("Logo","已存在本地文件中");
//                    FileInputStream fis = new FileInputStream(fileLogo);
//                    Bitmap bmp = BitmapFactory.decodeStream(fis);
//                    fis.close();
//                    return bmp;
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                return null;
//            }
//        }
//        return null;
//    }
}
