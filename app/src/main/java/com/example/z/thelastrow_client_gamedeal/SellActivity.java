package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.GameServiceFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.buyorsell.ThingsFragment;

/**
 * Created by Administrator on 2016/12/23.
 */

public class SellActivity extends Activity {

    private ThingsFragment thingsFragment;
    private GameServiceFragment gameServiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        thingsFragment = new ThingsFragment();
        thingsFragment.setOnSubmitListener(new ThingsFragment.OnSubmitListener() {
            @Override
            public void onSubmit() {
                submit();
            }
        });

        gameServiceFragment = new GameServiceFragment();
        gameServiceFragment.setOnNextStepListener(new GameServiceFragment.OnNextStepListener() {
            @Override
            public void onNextstep() {
                nextStep();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.things_sell_content,gameServiceFragment).commit();
    }

    private void submit() {
        finish();
    }

    private void nextStep() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.things_sell_content,thingsFragment)
                .addToBackStack(null)
                .commit();
    }
}
