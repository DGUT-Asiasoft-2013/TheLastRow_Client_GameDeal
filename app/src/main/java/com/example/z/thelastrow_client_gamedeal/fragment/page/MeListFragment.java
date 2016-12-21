package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ListViewFragment;

/**
 * Created by Z on 2016/12/21.
 */

public class MeListFragment extends Fragment {
    int[] drawableArray;
    String[] text1Array;
    String[] text2Array;

    ListViewFragment listViewFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_me,null);
        listViewFragment= (ListViewFragment) getFragmentManager().findFragmentById(R.id.page_me_frag);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        drawableArray=new int[]{R.drawable.my_wallet,R.drawable.my_message};
        text1Array=new  String[]{"我的钱包","我的消息"};
        text2Array=new String[]{"余额：0.00",">"};
        //listViewFragment.setArrays(drawableArray,text1Array,text2Array);
    }
}
