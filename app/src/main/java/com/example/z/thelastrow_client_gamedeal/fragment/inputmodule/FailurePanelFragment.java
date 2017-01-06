package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2017/1/6.
 */

public class FailurePanelFragment extends Fragment {

    private View view;
    private TextView text;
    private RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_failurepanel, null);


            text = (TextView) view.findViewById(R.id.failurepanel_text);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.failurepanel_layout);
            view.findViewById(R.id.failurepanel_linkagain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Loading();
                }
            });
        }
        return view;
    }

    public void setTextText(int res) {
        text.setText(res);
    }

    public void setMiss(boolean miss) {
        if (miss) {
            relativeLayout.setVisibility(View.GONE);
        }
        if (!miss) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }


    private void Loading() {
        if (onFailureButtonClickListener != null) {
            onFailureButtonClickListener.click();
        }
    }

    public interface OnFailureButtonClickListener {
        void click();
    }

    private OnFailureButtonClickListener onFailureButtonClickListener;

    public void setOnFailureButtonClickListener(OnFailureButtonClickListener onFailureButtonClickListener) {
        this.onFailureButtonClickListener = onFailureButtonClickListener;
    }


}
