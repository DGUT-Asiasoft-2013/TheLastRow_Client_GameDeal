package com.example.z.thelastrow_client_gamedeal.fragment.sreach;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;

import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 */

public class SreachListActivity extends Activity {

    private ImageView backBT;
    private EditText textET;
    private ListView sreachList;
    private String sreachText;
    private List<Equipment> equipmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreachlist);

        sreachText = this.getIntent().getStringExtra("sreachtext");

        backBT = (ImageView) findViewById(R.id.sreachactivity_back);
        textET = (EditText) findViewById(R.id.sreachactivity_search);
        textET.setText(sreachText);
        sreachList = (ListView) findViewById(R.id.sreachactivity_list);
        sreachList.setAdapter(sreachListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {

//        Server.getSharedClient().newCall()
    }

    private BaseAdapter sreachListAdapter = new BaseAdapter() {
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

            ImageView equipmentPicture = (ImageView) convertView.findViewById(R.id.list_good_avatar);
            TextView gamenameAndService = (TextView) convertView.findViewById(R.id.list_good_text_title);
            TextView equipname = (TextView) convertView.findViewById(R.id.list_good_text_equip);
            TextView equipvalue = (TextView) convertView.findViewById(R.id.list_good_text_price);
            TextView buyNumber = (TextView) convertView.findViewById(R.id.list_good_text_people_buy);

            Equipment equipment = equipmentList.get(position);

            gamenameAndService.setText("[" + equipment.getGameservice().getGame().getGamename() + "][" + equipment.getGameservice().getGameservicename() + "]");
            equipname.setText(equipment.getEquipname());
            equipvalue.setText(equipment.getEquipvalue());

            return convertView;
        }
    };
}
