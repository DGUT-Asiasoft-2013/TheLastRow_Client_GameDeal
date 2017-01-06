package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
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

    private View view, listhead;
    private ImageView newFeeds_tab_image2;
    private TextView newFeeds_newstext;
    private ListView listView;
    private List<Equipment> equipmentList;
    private RelativeLayout loadingPanel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_page_feeds_new, null);
            listhead = inflater.inflate(R.layout.fragment_page_feeds_new_headview, null);

            loadingPanel = (RelativeLayout) view.findViewById(R.id.new_feeds_loadingPanel);

            newFeeds_newstext = (TextView) view.findViewById(R.id.new_feeds_newstext);

            newFeeds_tab_image2 = (ImageView) listhead.findViewById(R.id.new_feeds_head_tab_img2);
            newFeeds_tab_image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goFeedsFragment();
                }
            });

            listView = (ListView) view.findViewById(R.id.new_feeds_listview);
            listView.addHeaderView(listhead);

            listView.setAdapter(listAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        newFeeds_newstext.setVisibility(View.GONE);
        equipmentList = new ArrayList<>();
        reload();
    }

    private void reload() {
        loadingPanel.setVisibility(View.VISIBLE);

        Server.getSharedClient().newCall(Server.getEquipmentNew10().get().build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (getActivity() == null) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NewFeedsListFragment.this.loadingPanel.setVisibility(View.GONE);
                        NewFeedsListFragment.this.equipmentList.clear();
                        NewFeedsListFragment.this.newFeeds_newstext.setText(R.string.failurelink_internet);
                        NewFeedsListFragment.this.newFeeds_newstext.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                loadingPanel.setVisibility(View.GONE);
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
                            NewFeedsListFragment.this.equipmentList = equipmentPage.getContent();
                            listAdapter.notifyDataSetChanged();
                            NewFeedsListFragment.this.newFeeds_newstext.setVisibility(View.GONE);
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
                            NewFeedsListFragment.this.equipmentList.clear();
                            NewFeedsListFragment.this.newFeeds_newstext.setText(R.string.failureLink_response);
                            NewFeedsListFragment.this.newFeeds_newstext.setVisibility(View.VISIBLE);
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

    private void goFeedsFragment() {
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
