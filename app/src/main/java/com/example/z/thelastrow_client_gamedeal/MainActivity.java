package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.fragment.MainTabBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.FeedsListFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.MeListFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.NoteListFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.page.SearchListFragment;

/**
 * Created by Z on 2016/12/21.
 */

public class MainActivity extends Activity {
    FeedsListFragment contentFeedList = new FeedsListFragment();
    NoteListFragment contentNoteList = new NoteListFragment();
    SearchListFragment contentSearchPage = new SearchListFragment();
    MeListFragment contentMyProfile = new MeListFragment();
   MainTabBarFragment tabbar;
    long mexitTime=-2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main );
        //mexitTime=System.currentTimeMillis();
        tabbar = (MainTabBarFragment) getFragmentManager().findFragmentById(R.id.frag_main_tab);
        tabbar.setOnTabSelectedListener(new MainTabBarFragment.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int index) {
                changeContentFragment(index);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabbar.setSelectedItem(0);
    }

    //按两次返回键退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis()-mexitTime>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出",Toast.LENGTH_SHORT).show();
                mexitTime=System.currentTimeMillis();
            }else{
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //切换Fragment
    void changeContentFragment(int index) {
        Fragment newFrag = null;

        switch (index) {
            case 0:
                newFrag = contentFeedList;
                break;
            case 1:
                newFrag = contentNoteList;
                break;
            case 2:
                newFrag = contentSearchPage;
                break;
            case 3:
                newFrag = contentMyProfile;
                break;
            default:
                break;
        }
        //tabbar.setTextColor(index);
        if (newFrag == null) return;

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_contain, newFrag)
                .commit();
    }
}
