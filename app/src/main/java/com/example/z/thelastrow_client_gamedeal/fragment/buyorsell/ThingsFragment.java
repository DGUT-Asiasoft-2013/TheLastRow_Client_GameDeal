package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
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

            if (SDKVersion.isMoreThanAPI19()) {
                things_name = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_name);
                things_value = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_value);

                things_picture = (PictureThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_picture);
            } else {
                things_name = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_name);
                things_value = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_value);

                things_picture = (PictureThingsFragment) getFragmentManager().findFragmentById(R.id.things_picture);
            }
            view.findViewById(R.id.things_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goSubmit();
                }
            });

            view.findViewById(R.id.things_back).setOnClickListener(new View.OnClickListener() {
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

        things_name.setThingsInputItemname("装备名称：");
        things_name.setThingsInputItemHint("请输入装备名");

        things_value.setThingsInputItemname("价格:");
        things_value.setThingsInputItemHint("请输入价格");
        things_value.setThingsInputItemImeOption(EditorInfo.IME_ACTION_DONE);
    }

    public String getThingsName() {
        return  things_name.getThingsInputItemtext();
    }

    public String getThingValue() {
        return things_value.getThingsInputItemtext();
    }

    public interface OnSubmitListener {
        void onSubmit();
    }

    private OnSubmitListener onSubmitListener;

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    private void goSubmit() {
        if (onSubmitListener != null) {
            onSubmitListener.onSubmit();
        }
    }
}
