package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.ImageDownload;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.FailurePanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.LoadingPanelFragment;
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

    private FailurePanelFragment failurePanelFragment;
    private LoadingPanelFragment loadingPanelFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

            failurePanelFragment = (FailurePanelFragment) getFragmentManager().findFragmentById(R.id.charts_failurepanel);
            loadingPanelFragment = (LoadingPanelFragment) getFragmentManager().findFragmentById(R.id.charts_loadingpanel);

        failurePanelFragment.setOnFailureButtonClickListener(new FailurePanelFragment.OnFailureButtonClickListener() {
            @Override
            public void click() {
                loadCheck();
            }
        });


        equipmentList = new ArrayList<>();
        actionBarFragment = (ActionBarFragment) getFragmentManager().findFragmentById(R.id.charts_bar);
        actionBarFragment.setTitle("排行榜");
        listView = (ListView) findViewById(R.id.charts_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goGoodActivity(position);
            }
        });
        listView.setAdapter(listAdapter);

    }

    private void goGoodActivity(int position) {
        Equipment good = equipmentList.get(position);
        Intent intent = new Intent(ChartsActivity.this, GoodActivity.class);
        intent.putExtra("good", good);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        equipmentList.clear();
        loadCheck();
    }

    private void loadCheck() {
        Server.getSharedClient().newCall(Server.getEquipmentCheck20().get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingPanelFragment.setMiss(true);
                        equipmentList.clear();
                        failurePanelFragment.setTextText(R.string.failurelink_internet);
                        failurePanelFragment.setMiss(false);
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
                            loadingPanelFragment.setMiss(true);
                            equipmentList = getData.getContent();

                            failurePanelFragment.setMiss(true);

                        }
                    });
                } catch (final Exception e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            new AlertDialog.Builder(getActivity())
//                                    .setMessage(e.getMessage()).setNegativeButton("知道",null).show();
                            loadingPanelFragment.setMiss(true);
                            equipmentList.clear();
                            failurePanelFragment.setTextText(R.string.failureLink_response);
                            failurePanelFragment.setMiss(false);
                        }
                    });
                }
            }
        });
        listAdapter.notifyDataSetChanged();
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
        public View getView(int position, View convertView, final ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charts_list_item, null);
            }

            final ImageView equipmentPicture = (ImageView) convertView.findViewById(R.id.charts_list_item_image);
            TextView listNo = (TextView) convertView.findViewById(R.id.charts_list_item_no);
            TextView equipservice = (TextView) convertView.findViewById(R.id.charts_list_item_equipservice);
            TextView equipname = (TextView) convertView.findViewById(R.id.charts_list_item_equipname);
            TextView equipvalue = (TextView) convertView.findViewById(R.id.charts_list_item_equipvalue);
            TextView equipcheck = (TextView) convertView.findViewById(R.id.charts_list_item_equipcheck);

            final Equipment equipment = equipmentList.get(position);

            if (equipment != null) {
                listNo.setText(String.valueOf(position + 1));
                equipservice.setText("[" + equipment.getGameservice().getGame().getGamename() + "][" + equipment.getGameservice().getGameservicename() + "]");
                equipname.setText(equipment.getEquipname());
                equipvalue.setText(equipment.getEquipvalue());
                equipcheck.setText("" + equipment.getLookcheck() + "次");
            }

            if (equipment != null && equipment.getEquippicture() != null) {
//                ImageDownload imageDownload = new ImageDownload(parent.getContext());
//                long time = 0;
//                if (position % 2 == 0) {
//                    time = 0;
//                } else {
//                    time = 2000;
//                }
                new ImageDownload(parent.getContext()).download(equipmentPicture, equipment.getEquippicture()[0],
                        new ImageDownload.ImageDownloadBack() {
                            @Override
                            public void onBack(ImageView imageView, Bitmap bmp) {
                                imageView.setImageBitmap(bmp);
                            }
                        } , 0);
            } else {
                equipmentPicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_50));
            }


            return convertView;
        }
    };
}
