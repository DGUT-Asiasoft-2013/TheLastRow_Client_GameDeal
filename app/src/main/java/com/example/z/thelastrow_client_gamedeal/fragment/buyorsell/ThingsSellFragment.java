package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.InputThingsFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.PictureThingsFragment;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ThingsSellFragment extends Fragment {

    private View view;
    private InputThingsFragment things_name,things_value;
    private PictureThingsFragment things_picture;
    private EditText things_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sell_things, null);

            if (SDKVersion.isMoreThanAPI19()) {
                things_name = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_name);
                things_value = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_value);

                things_picture = (PictureThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_picture);
            } else {
                things_name = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_name);
                things_value = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_value);

                things_picture = (PictureThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_picture);
            }

            things_number = (EditText) view.findViewById(R.id.things_sell_number);

            view.findViewById(R.id.things_sell_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goSubmit();
                }
            });

            view.findViewById(R.id.things_sell_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            view.findViewById(R.id.things_sell_plus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(things_number.getText().toString());
                    number++;
                    things_number.setText("" + number);
                }
            });

            view.findViewById(R.id.things_sell_minus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(things_number.getText().toString());
                    if (number == 0) {
                        return;
                    } else {
                        number--;
                        things_number.setText("" + number);
                    }
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
        things_name.setThingsInputItemtypeText();

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

    public String getThingNumber() {
        return things_number.getText().toString();
    }

    public byte[] getThingPicture() {
        return things_picture.getData();
    }

    public interface OnSellSubmitListener {
        void onSubmit();
    }

    private OnSellSubmitListener onSellSubmitListener;

    public void setOnSubmitListener(OnSellSubmitListener onSellSubmitListener) {
        this.onSellSubmitListener = onSellSubmitListener;
    }

    private void goSubmit() {
        if (onSellSubmitListener != null) {
            onSellSubmitListener.onSubmit();
        }
    }
}