package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/8.
 */

public class ChartsActivity extends Activity {

    private ListView listView;
    private ActionBarFragment actionBarFragment;
    private List<Equipment> equipmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        equipmentList = new ArrayList<>();
        actionBarFragment = (ActionBarFragment) getFragmentManager().findFragmentById(R.id.charts_bar);
        actionBarFragment.setTitle("排行榜");
        listView = (ListView) findViewById(R.id.charts_list);
        listView.setAdapter(listAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadCheck();
    }

    private void loadCheck() {
        Server.getSharedClient().newCall(Server.getEquipmentCheck20().get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(ChartsActivity.this).setMessage(e.getMessage())
                                .setPositiveButton("好", null).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final Page<Equipment> getData = new ObjectMapper().readValue(response.body().string(), new TypeReference<Page<Equipment>>() {
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (Equipment equipment : getData.getContent()) {
                                if (equipment.getLookcheck() != 0) {
                                    equipmentList.add(equipment);
                                    listAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(ChartsActivity.this).setMessage(e.getMessage())
                                    .setPositiveButton("好", null).show();
                        }
                    });
                }
            }
        });
    }

    BaseAdapter listAdapter = new BaseAdapter() {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charts_list_item, null);
            }

            ImageView equipmentPicture = (ImageView) convertView.findViewById(R.id.charts_list_item_image);
            TextView listNo = (TextView) convertView.findViewById(R.id.charts_list_item_no);
            TextView equipservice = (TextView) convertView.findViewById(R.id.charts_list_item_equipservice);
            TextView equipname = (TextView) convertView.findViewById(R.id.charts_list_item_equipname);
            TextView equipvalue = (TextView) convertView.findViewById(R.id.charts_list_item_equipvalue);
            TextView equipcheck = (TextView) convertView.findViewById(R.id.charts_list_item_equipcheck);

            Equipment equipment = equipmentList.get(position);

            listNo.setText(String.valueOf(position + 1));
            equipservice.setText("[" + equipment.getGameservice().getGame().getGamename() + "][" + equipment.getGameservice().getGameservicename() + "]");
            equipname.setText(equipment.getEquipname());
            equipvalue.setText(equipment.getEquipvalue());
            equipcheck.setText("" + equipment.getLookcheck() + "次");

            return convertView;
        }
    };
}
