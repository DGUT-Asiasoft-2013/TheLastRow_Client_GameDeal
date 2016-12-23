package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Z on 2016/12/22.
 */

public class ActionBarFragment extends Fragment {
    ImageView imageView;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_action_bar,null);
            imageView=(ImageView)view.findViewById(R.id.action_bar_back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
            textView=(TextView)view.findViewById(R.id.action_bar_text);
            textView.setText("");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (setTextClick!=null) setTextClick.setTextClickLisener();
                }
            });
        return view;
    }
    public void setText(String s){
        textView.setText(s);
    }


    public interface SetTextClick{
        public void setTextClickLisener();
    }
    SetTextClick setTextClick;

    public void setField(SetTextClick lisener){
        this.setTextClick=lisener;
    }

}
