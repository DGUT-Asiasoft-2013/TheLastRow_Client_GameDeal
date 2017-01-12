package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2016/12/26.
 */

public class InputThingsFragment extends Fragment {

    private View view;
    private TextView things_input_itemname;
    private EditText things_input_itemtext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {

        view = inflater.inflate(R.layout.fragment_things_input, null);

        things_input_itemname = (TextView) view.findViewById(R.id.things_input_itemname);
        things_input_itemtext = (EditText) view.findViewById(R.id.things_input_itemtext);

        }
        return view;
    }

    public void setThingsInputItemname(String s){
        things_input_itemname.setText(s);
    }

    public void setThingsInputItemHint(String s) {
        things_input_itemtext.setHint(s);
    }

    public void setThingsInputItemHintColor(int color) {
        things_input_itemtext.setHintTextColor(ColorStateList.valueOf(color));
    }

    public void setThingsInputItemImeOption(int itemImeOption) {
        things_input_itemtext.setImeOptions(itemImeOption);
    }

    public void setThingsInputItemtypeText() {
        things_input_itemtext.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public String getThingsInputItemtext() {
        return things_input_itemtext.getText().toString();
    }
}
