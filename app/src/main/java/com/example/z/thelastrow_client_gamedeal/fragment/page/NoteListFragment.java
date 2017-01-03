package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.GoodActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Good;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/21.
 */

public class NoteListFragment extends Fragment {

    private View view;
    private TextView notes_sell,notes_buy;
    private ListView notes_list;

    private List<Good> equipList;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_page_notes, null);
            notes_list = (ListView) view.findViewById(R.id.notes_list);
            notes_list.setAdapter(baseAdapter);
            notes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listClick(position);
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {
        Request request = Server.requestBuilderWithApi("/goodfeeds").get().build();

        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAlertDialog(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final Page<Good> data = new ObjectMapper()
                            .readValue(response.body().string(), new TypeReference<Page<Good>>() {});

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NoteListFragment.this.equipList = data.getContent();
                            NoteListFragment.this.page = data.getNumber();

                            baseAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (final Exception ex) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            showAlertDialog(ex.getMessage());
//                        }
//                    });

                }
            }
        });

    }

    public void   listClick(int position){
        Good good=equipList.get(position);
        Intent intent=new Intent(getActivity(), GoodActivity.class);
        intent.putExtra("good",good);
        startActivity(intent);
    }

    BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (equipList == null ? 0 : equipList.size());
        }

        @Override
        public Object getItem(int position) {
            return equipList.get(position);
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


            TextView text_type=(TextView)convertView.findViewById(R.id.list_good_text_type);
            TextView notes_listitem_thingsname = (TextView) convertView.findViewById(R.id.list_good_text_title);
            TextView notes_listitem_thingsvalue = (TextView) convertView.findViewById(R.id.list_good_text_price);
            TextView text_equip=(TextView)convertView.findViewById(R.id.list_good_text_equip);
            ImageView notes_listitem_thingspicture = (ImageView) convertView.findViewById(R.id.list_good_avatar);

            Good equip = equipList.get(position);

            notes_listitem_thingsname.setText(""
                    +"["+equip.getGame_name()+"]"
                    +"["+equip.getGame_area()+"]"
            );
            text_equip.setText(equip.getGame_equip());

            notes_listitem_thingsvalue.setText("" +equip.getPrice());

            return convertView;
        }
    };

    private void showAlertDialog(String text) {
        new AlertDialog.Builder(getActivity())
                .setMessage(text).setNegativeButton("知道",null).show();

    }
}
