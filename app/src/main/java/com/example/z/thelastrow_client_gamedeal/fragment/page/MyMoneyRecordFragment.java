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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Recharge;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ListViewFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.id.text1;

/**
 * Created by Z on 2016/12/23.
 */

public class MyMoneyRecordFragment extends Fragment {
    ListView listView;
    ImageView imageView;
    TextView text1,text2;
    Page<Recharge> page;
    List<Recharge> list;
    Recharge recharge;
    int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_my_money_record,null);
        listView=(ListView) view.findViewById(R.id.me_money_record);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        i = 0;
        Request request = Server.requestBuilderWithApi("me/" + Server.getUser().getId() + "/recharge/0")
                .get()
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                i = 1;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                page = new ObjectMapper().readValue(response.body().string(),
                        new TypeReference<Page<Recharge>>() {
                        });
                list = page.getContent();
                i = 1;
            }
        });
        while (i == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    BaseAdapter adapter =new BaseAdapter() {
        @Override
        public int getCount() {
            return list==null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                LayoutInflater inflater=LayoutInflater.from(parent.getContext());
                convertView=inflater.inflate(R.layout.list_view_item,null);
                imageView= (ImageView) convertView.findViewById(R.id.list_item_img);
                text1= (TextView) convertView.findViewById(R.id.list_item_text1);
                text2= (TextView) convertView.findViewById(R.id.list_item_text2);
                setItems(position);
            }

            return convertView;
        }
    };
    public void setItems(int position) {
        recharge=list.get(position);
        imageView.setImageResource(R.drawable.setting_48);
        text1.setText(recharge.getMoneyrecord());
        text2.setText(recharge.getStringDate());
    }

}
