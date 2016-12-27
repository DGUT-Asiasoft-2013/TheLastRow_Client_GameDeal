package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2016/12/27.
 */

public class GameServiceFragment extends Fragment {

    View view;
    Spinner gameservice_gamename,gameservice_gameservice;
    TextView gameservice_companyname;
    EditText gameservice_gameid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_gameservice, null);

            gameservice_gamename = (Spinner) view.findViewById(R.id.gameservice_gamename);
            gameservice_companyname = (TextView) view.findViewById(R.id.gameservice_companyname);
            gameservice_gameid = (EditText) view.findViewById(R.id.gameservice_gamenid);
            gameservice_gameservice = (Spinner) view.findViewById(R.id.gameservice_gamenservice);

            view.findViewById(R.id.gameservice_nextstep).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goNext();
                }
            });

        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public String getGameName() {
        return gameservice_gamename.getSelectedItem().toString();
    }

    public String getCompanyName() {
        return gameservice_companyname.getText().toString();
    }
    public String getGameId() {
        return gameservice_gameid.getText().toString();
    }
    public String getGameService() {
        return gameservice_gameservice.getSelectedItem().toString();
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
