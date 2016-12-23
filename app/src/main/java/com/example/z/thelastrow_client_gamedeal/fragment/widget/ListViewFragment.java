package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.ActivityMeListViewJump;
import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Z on 2016/12/21.
 */

public class ListViewFragment extends Fragment {
    ListView listView;
    ImageView imageView;
    TextView text1,text2;

     int imgArray[];
     String text1Array[];
     String text2Array[];
    Handler handler;

    int position=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list,null);
        listView= (ListView) view.findViewById(R.id.frag_listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ActivityMeListViewJump.class);
                intent.putExtra("listViewItem",text1Array[position]);
                startActivity(intent);
            }
        });
        handler=new Handler();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onReload();
    }

    public void onReload() {

    }

    BaseAdapter listAdapter =new BaseAdapter() {
        @Override
        public int getCount() {
            if (imgArray==null){
                return 4;
            }else {
                int a=Math.max(imgArray.length,text1Array.length);
                a=Math.max(a,text2Array.length);
                return a;
            }
        }

        @Override
        public Object getItem(int position) {
            return text1Array[position];
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
    public  void setArrays(int[] imgArray1, String[] textArray1,String[] textArray2){
        imgArray=imgArray1;
        text1Array=textArray1;
        text2Array=textArray2;
    }

    public void setItems(int position) {
        if (imgArray != null && imgArray.length>position) {
            imageView.setImageResource(imgArray[position]);
        }
        if(text1Array!=null && text1Array.length>position){
            text1.setText(text1Array[position]);
        }
        if(text2Array!=null && text2Array.length>position){
            text2.setText(text2Array[position]);
        }
//        else {
//            imageView.setImageResource(R.drawable.setting_48);
//            text1.setText("我的测试" + position);
//            text2.setText(">");
//        }
    }


}
