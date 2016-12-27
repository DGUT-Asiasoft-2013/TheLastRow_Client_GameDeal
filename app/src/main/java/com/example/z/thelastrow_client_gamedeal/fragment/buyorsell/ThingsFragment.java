package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.InputThingsFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.PictureThingsFragment;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ThingsFragment extends Fragment {

    View view;
    InputThingsFragment things_name,things_value;
    PictureThingsFragment things_picture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_things, null);

            things_name = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_name);
            things_value = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_value);

            things_picture = (PictureThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_picture);

        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        things_name.setThingsInputItemname("装备名称：");
        things_name.setThingsInputItemHint("请输入装备名");

        things_value.setThingsInputItemname("价格:");
        things_value.setThingsInputItemHint("请输入价格");
    }
}
