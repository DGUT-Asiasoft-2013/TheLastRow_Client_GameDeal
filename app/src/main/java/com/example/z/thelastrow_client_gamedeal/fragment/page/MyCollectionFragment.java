package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.ActivityMeListViewJump;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Good;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Like;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Page;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Payments;
import com.example.z.thelastrow_client_gamedeal.fragment.api.service.GoodService;
import com.example.z.thelastrow_client_gamedeal.fragment.api.service.LikeService;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
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

public class MyCollectionFragment extends Fragment {
    LinearLayout linearLayout;
    ListView listView;
    Page<Like> page_pay;
    Like like;
    List<Like> data;
    Handler handler=new Handler();
    int position1=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_my_collection,null);
        linearLayout=(LinearLayout)view.findViewById(R.id.collection_linearLayout);
        listView=(ListView)view.findViewById(R.id.collection_list);
        listView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        data=null;
        ProgressDialog pdlg=new ProgressDialog(getActivity());
        pdlg.setMessage("请稍后");
        pdlg.setCanceledOnTouchOutside(false);
        pdlg.show();
        data=new LikeService().getLikesByUserID(Server.getUser(),pdlg);
//        if (data==null){
//            linearLayout.removeView(listView);
//            TextView textView=new TextView(getActivity());
//            textView.setWidth(linearLayout.getWidth());
//            textView.setHeight(linearLayout.getHeight());
//            textView.setText("空空如也，赶紧去收藏吧！");
//            textView.setTextSize(20);
//            linearLayout.addView(textView);
//
//        }
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
        public View getView( int position, View convertView, ViewGroup parent) {
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
                final Button btn=(Button)convertView.findViewById(R.id.list_good_btn);
                btn.setVisibility(View.VISIBLE);


                if (data!=null){
                    position1=position;
                   like =data.get(position);
                    final Bitmap bmp=new GoodService().getBmp(like.getId().getGood().getAvatar_img1());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(bmp);
                            buyers.setText("");
                            state.setText("");
                            title.setText(like.getId().getGood().getGame_name()+" "+like.getId().getGood().getGame_area());
                            equip.setText(like.getId().getGood().getGame_equip());
                            price.setText(like.getId().getGood().getPrice()+"");
                            btn.setText("取消");
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnClick();
                                }
                            });
                        }
                    });
                }
            }
            return convertView;
        }
    };

    private void btnClick() {
        ToastAndDialog.setDialog(getActivity(), "取消收藏？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean s = new LikeService().postLike(data.get(position1).getId().getGood(), false);

                ToastAndDialog.setToastShort(getActivity(), "取消成功");
                data.remove(position1);
                adapter.notifyDataSetChanged();

            }
        });
    }
}
