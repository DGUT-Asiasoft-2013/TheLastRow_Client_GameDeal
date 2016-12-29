package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.InputThingsFragment;

/**
 * Created by Administrator on 2016/12/29.
 */

public class ThingsBuyFragment extends Fragment {

    private View view;
    private EditText things_number;
    private InputThingsFragment things_name,things_value;
    private CheckBox ishavepicture;
    private GridLayout gridLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_buy_things,null);

            things_number = (EditText) view.findViewById(R.id.things_buy_number);
            if (SDKVersion.isMoreThanAPI19()) {
                things_name = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_buy_name);
                things_value = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_buy_value);
            } else {
                things_name = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_buy_name);
                things_value = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_buy_value);
            }

            ishavepicture = (CheckBox) view.findViewById(R.id.things_buy_ishavepicture);
            gridLayout = (GridLayout) view.findViewById(R.id.things_buy_gridpicture);

            ishavepicture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        gridLayout.setVisibility(View.VISIBLE);
                    } else {
                        gridLayout.setVisibility(View.GONE);
                    }
                }
            });

            view.findViewById(R.id.things_buy_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            view.findViewById(R.id.things_buy_plus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(things_number.getText().toString());
                    number++;
                    things_number.setText("" + number);
                }
            });

            view.findViewById(R.id.things_buy_minus).setOnClickListener(new View.OnClickListener() {
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

            view.findViewById(R.id.things_buy_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goSubmit();
                }
            });

        }
        return  view;
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

        if (ishavepicture.isChecked()) {
            gridLayout.setVisibility(View.VISIBLE);
        } else {
            gridLayout.setVisibility(View.GONE);
        }
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

    public interface OnBuySubmitListener {
        void onSubmit();
    }

    private OnBuySubmitListener onBuySubmitListener;

    public void setOnBuySubmitListener(OnBuySubmitListener onBuySubmitListener) {
        this.onBuySubmitListener = onBuySubmitListener;
    }

    private void goSubmit() {
        if (onBuySubmitListener != null) {
            onBuySubmitListener.onSubmit();
        }
    }
}
