package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.GoodNumberFragment;

/**
 * Created by Z on 2016/12/28.
 */

public class GoodActivity extends Activity {
    ActionBarFragment actionBarFragment;
    GoodNumberFragment goodNumberFragment;
    TextView    text_price_buttom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.good_frag_action_bar);
        text_price_buttom=(TextView)findViewById(R.id.good_text_game_price_bottom);
        goodNumberFragment= (GoodNumberFragment) getFragmentManager().findFragmentById(R.id.good_frag_number);
        goodNumberFragment.ediTextChange("30",9,text_price_buttom);

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBarFragment.setTitle("商品详情");
    }
}
