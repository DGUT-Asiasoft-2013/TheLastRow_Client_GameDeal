package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2016/12/26.
 */

public class PictureThingsFragment extends Fragment {

    View view;
    ImageView image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_things_picture, null);

            image = (ImageView) view.findViewById(R.id.things_picture);
        }
        return view;
    }
}
