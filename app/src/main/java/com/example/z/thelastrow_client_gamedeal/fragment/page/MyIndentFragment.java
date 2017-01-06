package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Payments;
import com.example.z.thelastrow_client_gamedeal.fragment.api.service.GoodService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/23.
 */

public class MyIndentFragment extends Fragment {
    ListView listView;
    Page<Payments> page_pay;
    List<Payments> data;
    Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_my_indent,null);
        listView=(ListView)view.findViewById(R.id.indent_list);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new getPaymentThread()).start();
    }
    public class getPaymentThread extends Thread{
        @Override
        public void run() {
            super.run();
            Request request= Server.requestBuilderWithApi("/user/"+Server.getUser().getId()+"/payments")
                    .get()
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    page_pay=new ObjectMapper().readValue(response.body().string(), new TypeReference<Page<Payments>>() {});
                    data=page_pay.getContent();
                }
            });

        }
    }
    BaseAdapter adapter=new BaseAdapter() {
        @Override
        public int getCount() {
            return data==null ? 0:data.size();
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
                convertView=inflater.inflate(R.layout.list_view_goods_item,null);
                final ImageView img=(ImageView)convertView.findViewById(R.id.list_good_avatar);
                final TextView title=(TextView)convertView.findViewById(R.id.list_good_text_title);
                final TextView price=(TextView)convertView.findViewById(R.id.list_good_text_price);
                final TextView equip=(TextView)convertView.findViewById(R.id.list_good_text_equip);
                TextView type=(TextView)convertView.findViewById(R.id.list_good_text_type);
                final TextView buyers=(TextView)convertView.findViewById(R.id.list_good_text_people_buy);
                final TextView state=(TextView)convertView.findViewById(R.id.list_good_pay);
                Button btn=(Button)convertView.findViewById(R.id.list_good_btn);
                btn.setVisibility(View.VISIBLE);

                if (data!=null){
                    final Payments payments=data.get(position);
                    final Bitmap bmp=new GoodService().getBmp(payments.getGood().getAvatar_img1());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bmp);
                            buyers.setText(payments.getNumber()+"");
                            state.setText(" 件");
                            title.setText(payments.getGood().getGame_name()+" "+payments.getGood().getGame_area());
                            equip.setText(payments.getGood().getGame_equip());
                            price.setText(payments.getGood().getPrice()+"");
                        }
                    });

                }


            }
            return convertView;
        }
    };
}
