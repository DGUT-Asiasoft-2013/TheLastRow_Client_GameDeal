package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsFragment;

/**
 * Created by Administrator on 2016/12/23.
 */

public class SellActivity extends Activity {

    ThingsFragment thingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        thingsFragment = new ThingsFragment();

        getFragmentManager().beginTransaction().replace(R.id.things_sell_content,thingsFragment).commit();
    }
}
