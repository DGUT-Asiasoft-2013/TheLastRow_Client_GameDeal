package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.GoodNumberFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;

/**
 * Created by Z on 2016/12/28.
 */

public class GoodActivity extends Activity {
    ActionBarFragment actionBarFragment;
    GoodNumberFragment goodNumberFragment;
    TextView    text_price_bottom;
    Button btn_buy,btn_like,btn_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.good_frag_action_bar);
        actionBarFragment.setTitle("商品详情");

        text_price_bottom=(TextView)findViewById(R.id.good_text_game_price_bottom);
        goodNumberFragment= (GoodNumberFragment) getFragmentManager().findFragmentById(R.id.good_frag_number);
        goodNumberFragment.ediTextChange("30",9,text_price_bottom);

        btn_like= (Button) findViewById(R.id.good_btn_like);
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLikeLisener();
            }
        });
        btn_comment= (Button) findViewById(R.id.good_btn_see_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCommentLisener();
            }
        });
        btn_buy= (Button) findViewById(R.id.good_btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBuyLisener();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void btnLikeLisener() {

    }
    public void btnCommentLisener() {
    }
    public void btnBuyLisener() {
        // 判断是否登录
        if (Server.getUser()==null){
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        // 判断余额是否足够
        else {
            int userMoney= Server.getUser().getMoney();
            int itemMoney=Integer.parseInt(text_price_bottom.getText().toString());
            if (itemMoney>userMoney){
                ToastAndDialog.setToastShort(getApplicationContext(),"账户余额不足");
            }else{

            }
        }

    }
}
