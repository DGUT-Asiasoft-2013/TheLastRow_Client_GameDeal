package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by z on 2017/1/3.
 */

public class MainBarFragment extends Fragment {
    ImageView search,add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_main_action_bar,null);
        search=(ImageView)view.findViewById(R.id.main_bar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClick();
            }
        });
        add=(ImageView)view.findViewById(R.id.main_bar_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClick();
            }
        });
        return view;
    }

    private void addClick() {
        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_45);
        search.setAnimation(animation);
    }

    private void searchClick() {
    }
}
