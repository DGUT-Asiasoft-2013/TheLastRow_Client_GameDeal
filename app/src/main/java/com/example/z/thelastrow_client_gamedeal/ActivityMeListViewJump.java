package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.page.MyCollectionFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.MyIndentFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.MyMessageFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.MyMoneyRecordFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;

/**
 * Created by Z on 2016/12/23.
 */

public class ActivityMeListViewJump extends Activity {

    ActionBarFragment actionBarFragment;
    MyIndentFragment myIndentFragment=new MyIndentFragment();
    MyCollectionFragment myCollectionFragment=new MyCollectionFragment();
    MyMoneyRecordFragment myMoneyRecordFragment=new MyMoneyRecordFragment();
    MyMessageFragment myMessageFragment=new MyMessageFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_me_listview_jump);
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.me_listview_jump_frag_action_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeFragment();

    }

    private void changeFragment() {
        String title=getIntent().getStringExtra("listViewItem");
        Fragment newFrag = null;

        actionBarFragment.setTitle(title);
        switch (title){
            default: break;
            case "我的订单": newFrag=myIndentFragment; break;
            case "我的收藏": newFrag=myCollectionFragment; break;
            case "消费记录": newFrag=myMoneyRecordFragment; break;
            case "我的消息": newFrag=myMessageFragment; break;
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.me_listview_jump_contain,newFrag)
                .commit();
    }
}
