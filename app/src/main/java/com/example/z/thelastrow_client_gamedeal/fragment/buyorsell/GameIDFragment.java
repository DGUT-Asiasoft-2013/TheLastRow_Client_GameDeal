package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2016/12/27.
 */

public class GameIDFragment extends Fragment {

    private View view;
    private TextView gameid_gamename,gameid_gameservice;
    private EditText gameid_gameid;
    private String gamename,gameservicename;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_gameid, null);

            gameid_gamename = (TextView) view.findViewById(R.id.gameid_gamename);
            gameid_gameid = (EditText) view.findViewById(R.id.gameid_gamenid);
            gameid_gameservice = (TextView) view.findViewById(R.id.gameid_gamenservice);

            view.findViewById(R.id.gameid_nextstep).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goNext();
                }
            });

            view.findViewById(R.id.gameid_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null) {
            gamename = getArguments().getString("gamename");
            gameservicename = getArguments().getString("gameservicename");
        }

            gameid_gamename.setText(gamename);
            gameid_gameservice.setText(gameservicename);



    }

    public String getGameId() {
        return gameid_gameid.getText().toString();
    }

    public void setGameIdTextHint(String text) {
        gameid_gameid.setHintTextColor(ColorStateList.valueOf(Color.RED));
        gameid_gameid.setHint(text);
    }

    public interface OnNextStepListener {
        void onNextstep();
    }

    private OnNextStepListener onNextStepListener;

    public void setOnNextStepListener(OnNextStepListener onNextStepListener) {
        this.onNextStepListener = onNextStepListener;
    }

    private void goNext() {
        if (onNextStepListener != null) {
            onNextStepListener.onNextstep();
        }
    }
}
