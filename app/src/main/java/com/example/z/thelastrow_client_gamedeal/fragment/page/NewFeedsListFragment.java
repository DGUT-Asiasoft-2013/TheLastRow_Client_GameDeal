package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.ClassifyActivity;
import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.SellActivity;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.FailurePanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.LoadingPanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.MainBarFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by z on 2017/1/3.
 */

public class NewFeedsListFragment extends Fragment {

    private View view, equiplisthead;
    private ImageView newFeeds_tab_image2;
    private ListView listView;
    private List<Equipment> equipmentList;
    private MainBarFragment mainBarFragment;
    private FailurePanelFragment failurePanelFragment;
    private LoadingPanelFragment loadingPanelFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_page_feeds_new, null);
            equiplisthead = inflater.inflate(R.layout.fragment_page_feeds_new_headview, null);

            if (SDKVersion.isMoreThanAPI19()) {
                mainBarFragment = (MainBarFragment) getChildFragmentManager().findFragmentById(R.id.frag_main_bar1);
                failurePanelFragment = (FailurePanelFragment) getChildFragmentManager().findFragmentById(R.id.new_feeds_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getChildFragmentManager().findFragmentById(R.id.new_feeds_loadingpanel);
            } else {
                mainBarFragment = (MainBarFragment) getFragmentManager().findFragmentById(R.id.frag_main_bar1);
                failurePanelFragment = (FailurePanelFragment) getFragmentManager().findFragmentById(R.id.new_feeds_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getFragmentManager().findFragmentById(R.id.new_feeds_loadingpanel);
            }
            mainBarFragment.setOnAddListener(new MainBarFragment.OnAddListener() {
                @Override
                public void add() {
                    if (Server.getUser() == null) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), SellActivity.class));
                    }
                }
            });

            failurePanelFragment.setOnFailureButtonClickListener(new FailurePanelFragment.OnFailureButtonClickListener() {
                @Override
                public void click() {
                    reload();
                }
            });

            newFeeds_tab_image2 = (ImageView) equiplisthead.findViewById(R.id.new_feeds_head_tab_img2);
            newFeeds_tab_image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ClassifyActivity.class));
//                    goClassifyActivity();
                }
            });

            listView = (ListView) view.findViewById(R.id.new_feeds_listview);
            listView.addHeaderView(equiplisthead);

            listView.setAdapter(listAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        equipmentList = new ArrayList<>();
        reload();
    }

    private void reload() {
        failurePanelFragment.setMiss(true);
        loadingPanelFragment.setMiss(false);

        Server.getSharedClient().newCall(Server.getEquipmentNew10().get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (getActivity() == null) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewFeedsListFragment.this.loadingPanelFragment.setMiss(true);
                        NewFeedsListFragment.this.equipmentList.clear();
                        NewFeedsListFragment.this.failurePanelFragment.setTextText(R.string.failurelink_internet);
                        NewFeedsListFragment.this.failurePanelFragment.setMiss(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final Page<Equipment> equipmentPage = new ObjectMapper().readValue(response.body().string(),
                            new TypeReference<Page<Equipment>>() {
                            });

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NewFeedsListFragment.this.loadingPanelFragment.setMiss(true);
                            NewFeedsListFragment.this.equipmentList = equipmentPage.getContent();
                            listAdapter.notifyDataSetChanged();
                            NewFeedsListFragment.this.failurePanelFragment.setMiss(true);
                        }
                    });
                } catch (final Exception e) {
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            new AlertDialog.Builder(getActivity())
//                                    .setMessage(e.getMessage()).setNegativeButton("知道",null).show();
                            NewFeedsListFragment.this.loadingPanelFragment.setMiss(true);
                            NewFeedsListFragment.this.equipmentList.clear();
                            NewFeedsListFragment.this.failurePanelFragment.setTextText(R.string.failureLink_response);
                            NewFeedsListFragment.this.failurePanelFragment.setMiss(false);
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


    public interface OnGoFeedsFragmentListener {
        void onGoFeedsFragment();
    }

    private OnGoFeedsFragmentListener onGoFeedsFragmentListener;

    public void setOnGoFeedsFragmentListene(OnGoFeedsFragmentListener onGoFeedsFragmentListener) {
        this.onGoFeedsFragmentListener = onGoFeedsFragmentListener;
    }

    private void goClassifyActivity() {
        if (onGoFeedsFragmentListener != null) {
            onGoFeedsFragmentListener.onGoFeedsFragment();
        }
    }

//    public interface IsNewFeeds {
//        boolean isNewFeeds();
//    }
//
//    private IsNewFeeds isNewFeeds;
//
//    public void setIsNewFeeds(IsNewFeeds isNewFeeds) {
//        this.isNewFeeds = isNewFeeds;
//    }
//
//    private boolean getIsNewFeeds() {
//        if (isNewFeeds != null) {
//            return isNewFeeds.isNewFeeds();
//        }
//        return false;
//    }
}
