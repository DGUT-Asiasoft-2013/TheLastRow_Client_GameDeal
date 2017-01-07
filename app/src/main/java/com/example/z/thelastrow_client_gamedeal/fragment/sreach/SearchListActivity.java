package com.example.z.thelastrow_client_gamedeal.fragment.sreach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.GoodActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/7.
 */

public class SearchListActivity extends Activity {

    private EditText textET;
    private ListView searchList;
    private String searchText;
    private List<Equipment> equipmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreachlist);

        searchText = this.getIntent().getStringExtra("sreachtext");

        findViewById(R.id.sreachactivity_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textET = (EditText) findViewById(R.id.sreachactivity_search);
        textET.setText(searchText);
        textET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ) {

                    searchText = v.getText().toString().trim();

                    reload();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return  true;
                }
                return  false;
            }
        });

        searchList = (ListView) findViewById(R.id.sreachactivity_list);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goGoodActivity(position);
            }
        });
        searchList.setAdapter(searchListAdapter);
    }

    private void goGoodActivity(int position) {
            Equipment good=equipmentList.get(position);
            Intent intent=new Intent(SearchListActivity.this, GoodActivity.class);
            intent.putExtra("good",good);
            startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {

        Server.getSharedClient().newCall(Server.getEquipmentBySearch(searchText).get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final List<Equipment> equipmentGet = new ObjectMapper().readValue(response.body().string(),
                            new TypeReference<List<Equipment>>() {
                            });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchListActivity.this.equipmentList = equipmentGet;
                            searchListAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (final Exception e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            new AlertDialog.Builder(getActivity())
////                                    .setMessage(e.getMessage()).setNegativeButton("知道",null).show();
//                            NewFeedsListFragment.this.loadingPanelFragment.setMiss(true);
//                            NewFeedsListFragment.this.equipmentList.clear();
//                            NewFeedsListFragment.this.failurePanelFragment.setTextText(R.string.failureLink_response);
//                            NewFeedsListFragment.this.failurePanelFragment.setMiss(false);
//                        }
//                    });
                }
            }
        });
    }

    private BaseAdapter searchListAdapter = new BaseAdapter() {
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
