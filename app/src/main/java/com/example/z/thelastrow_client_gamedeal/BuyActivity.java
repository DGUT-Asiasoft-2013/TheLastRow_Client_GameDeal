package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameServiceFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsBuyFragment;

/**
 * Created by Administrator on 2016/12/23.
 */

public class BuyActivity extends Activity {

    private GameServiceFragment gameServiceFragment;
    private ThingsBuyFragment thingsBuyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        gameServiceFragment = new GameServiceFragment();
        gameServiceFragment.setOnNextStepListener(new GameServiceFragment.OnNextStepListener() {
            @Override
            public void onNextstep() {
                nextStep();
            }
        });

        thingsBuyFragment = new ThingsBuyFragment();
        thingsBuyFragment.setOnBuySubmitListener(new ThingsBuyFragment.OnBuySubmitListener() {
            @Override
            public void onSubmit() {
                submit();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.things_buy_content,gameServiceFragment).commit();
    }

    private void submit() {

    }

    private void nextStep() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_buy_content, thingsBuyFragment)
                .addToBackStack(null)
                .commit();

    }
}
