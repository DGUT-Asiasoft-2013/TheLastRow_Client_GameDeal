package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Z on 2016/12/28.
 */

public class GoodNumberFragment extends Fragment {
    Button btn_reduce,btn_add;
    EditText edi;
    String money1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_good_number,null);
        edi= (EditText) view.findViewById(R.id.good_number_edi);

        edi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edi.setFocusable(true);
            }
        });
        btn_reduce= (Button) view.findViewById(R.id.good_number_btn_reduce);
        btn_reduce.setFocusable(true);
        btn_reduce.requestFocus();
        btn_reduce.setFocusableInTouchMode(true);
        btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(edi.getText().toString());
                if(number>=2){
                    number=number-1;
                    edi.setText(number+"");

                }
            }
        });
        btn_add= (Button) view.findViewById(R.id.good_number_btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(edi.getText().toString());
                number=number+1;
                edi.setText(number+"");
            }
        });
        return view;
    }

    public String ediTextChange(final String money, final int maxNumber, final TextView targetChangTextView){
        money1=money;
        edi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().contentEquals("")){
                    targetChangTextView.setText("0");
                }else {
                    int number = Integer.parseInt(s.toString());
                    if (number > maxNumber) {
                        number = maxNumber;
                        edi.setText(number+"");
                    }
                    if (number < 1) {
                        number = 1;
                        edi.setText(number+"");
                    }
                    money1 = (Integer.parseInt(money) * number) + "";
                    targetChangTextView.setText(money1);
                }

            }
        });
        return money1;
    }
}
