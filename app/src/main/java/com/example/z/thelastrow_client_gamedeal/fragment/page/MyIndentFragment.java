package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.GoodActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Payments;
import com.example.z.thelastrow_client_gamedeal.fragment.api.service.GoodService;
import com.example.z.thelastrow_client_gamedeal.fragment.api.service.PaymentService;
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
    int i;
    ListView listView;
    List<Payments> data;
    Handler handler = new Handler();
    final String[] stateArray = {"发货", "等待收货", "等待评价", "交易成功","等待发货", "收货", "评价"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_my_indent,null);
        listView=(ListView)view.findViewById(R.id.indent_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), GoodActivity.class);
                intent.putExtra("good",data.get(position).getGood());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        data=new PaymentService().getPayment();
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
                final TextView type=(TextView)convertView.findViewById(R.id.list_good_text_type);
                final TextView buyers=(TextView)convertView.findViewById(R.id.list_good_text_people_buy);
                final TextView state=(TextView)convertView.findViewById(R.id.list_good_pay);
                final Button btn=(Button)convertView.findViewById(R.id.list_good_btn);
                btn.setVisibility(View.VISIBLE);

                if (data!=null){
                    final Payments payments=data.get(position);
                    final Bitmap bmp=new GoodService().getBmp(payments.getGood().getAvatar_img1());
                    final boolean buyType=payments.getUser().getId().equals(Server.getUser().getId());
                    final boolean sellType=payments.getGood().getAuthor().getId().equals(Server.getUser().getId());
                    final int good_state=payments.getState();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            buyers.setText(payments.getNumber()+"");
                            state.setText(" 件");
                            title.setText(payments.getGood().getGame_name()+" "+payments.getGood().getGame_area());
                            equip.setText(payments.getGood().getGame_equip());
                            price.setText(payments.getGood().getPrice()+"");
                            setBtnText(buyType,sellType,good_state,type,btn);
                            img.setImageBitmap(bmp);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setBtnClick(btn);
                                }
                            });
                        }
                    });
                }
            }
            return convertView;
        }
    };

    void setBtnText(boolean buyType,boolean sellType,int good_state,TextView type,Button btn){
        if (buyType){
            type.setText("买入");
            switch(good_state){
                case 0:btn.setText(stateArray[4]);break;
                case 1:btn.setText(stateArray[5]);break;
                case 2:btn.setText(stateArray[6]);break;
                case 3:btn.setText(stateArray[3]);break;
                default:break;
            }
        }else {
            if (sellType){
                type.setText("售出");
                switch(good_state){
                    case 0:btn.setText(stateArray[0]);break;
                    case 1:btn.setText(stateArray[1]);break;
                    case 2:btn.setText(stateArray[2]);break;
                    case 3:btn.setText(stateArray[3]);break;
                    default:break;
                }
            }
        }

    }
    void setBtnClick(Button btn){
        String btn_text=btn.getText().toString();
        int postState=0;
        switch (btn_text){
            case stateArray[0]: postState=1;break;
        }


    }
}
