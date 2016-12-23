package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ListViewFragment;

/**
 * Created by Z on 2016/12/21.
 */

public class MeListFragment extends Fragment {
    Handler handler;
    int[] drawableArray;
    String[] text1Array;
    String[] text2Array;

    TextView txt_goto_login;
    ListViewFragment listViewFragment =new ListViewFragment();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_me,null);
        listViewFragment= (ListViewFragment) getFragmentManager().findFragmentById(R.id.page_me_frag);
        txt_goto_login=(TextView) view.findViewById(R.id.text_login_or_register);
        txt_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
        handler=new Handler();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setListView();
        getAccountInformation();
    }

//    获取用户信息
    private void getAccountInformation() {
    }

    private void setListView() {
        drawableArray = new int[]{R.drawable.my_wallet, R.drawable.my_wallet,R.drawable.my_wallet,R.drawable.my_wallet};
        text1Array = new String[]{"我的订单","我的收藏", "消费记录","我的消息"};
        text2Array = new String[]{">", ">",">",">"};
        ListViewFragment.setArrays(drawableArray, text1Array, text2Array);
    }
}
