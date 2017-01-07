package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2017/1/6.
 */

public class LoadingPanelFragment extends Fragment {

    private View view;
    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_loading, null);


            relativeLayout = (RelativeLayout) view.findViewById(R.id.loadingpanel_layout);
        }
        return view;
    }

    public void setMiss(boolean miss) {
        if (miss) {
            relativeLayout.setVisibility(View.GONE);
        }
        if (!miss) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}
