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
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Comment;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Recharge;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/23.
 */

public class MyMessageFragment extends Fragment {
    TextView [] textViews=new TextView[3];
    ListView listView;

    ImageView imageView;
    TextView text1,text2;

    int count=0;
    List<Comment> list_comment;
    List<Recharge> list_recharge;
    Comment comment;
    Recharge recharge;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_my_message,null);
        listView=(ListView)view.findViewById(R.id.message_list_view);
        listView.setAdapter(adapter);
        textViews[0]=(TextView)view.findViewById(R.id.message_text_comment);
        textViews[1]=(TextView)view.findViewById(R.id.message_text_collection);
        textViews[2]=(TextView)view.findViewById(R.id.message_text_private_msg);
        for (final TextView textView : textViews) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyMessageFragment.this.textViewsClick(v);
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

    public void  textViewsClick(View v){
        int id=v.getId();
        switch (id){
            case R.id.message_text_comment: count=1;
        }
    }

    void reload(){
        String url=null;
        if (count==0){url="me/"+Server.getUser().getId()+"/recharge/0";}
        Request request=Server.requestBuilderWithApi(url)
                .get()
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (count==0){
                    Page<Recharge> page=new ObjectMapper().readValue(response.body().string(),
                            new TypeReference<Page<Recharge>>(){});
                    list_recharge=page.getContent();
                }

//                    adapter.notifyDataSetInvalidated();
            }
        });
    }
    BaseAdapter adapter =new BaseAdapter() {
        @Override
        public int getCount() {
            if (count==0){
                return list_recharge==null ? 0 : list_recharge.size();
            }else {
                return 10;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
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
        if (count==0){
            recharge=list_recharge.get(position);
            imageView.setImageResource(R.drawable.setting_48);
            text1.setText(recharge.getMoneyrecord());
            text2.setText(recharge.getStringDate());
        }

    }
}
