package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2016/12/22.
 */

public class CompanyEntity extends Fragment {

    private TextView companyname, companyslogan;
    private ImageView smalllog, biglog;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_page_feeds_companyentity, null);

            companyname = (TextView) view.findViewById(R.id.companyentity_companyname);
            companyslogan = (TextView) view.findViewById(R.id.companyentity_companyslogan);
            smalllog = (ImageView) view.findViewById(R.id.companyentity_companylog_small);
            biglog = (ImageView) view.findViewById(R.id.companyentity_companylog_big);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goEqipmentNewsActivity();
                }
            });

        }


        return view;
    }

    private void goEqipmentNewsActivity() {
        if (onCompanyEntityListener != null) {
            onCompanyEntityListener.onCampantEnyityClick();
        }
    }

    public void setCompanyNameText(String string) {
        companyname.setText(string);
    }

    public void setCompanyNameColor(int a, int r, int g, int b) {
        companyname.setTextColor(Color.argb(a, r, g, b));
    }

    public void setCompanySloganText(String string) {
        companyslogan.setText(string);
    }

    public void setCompanySloganColor(int a, int r, int g, int b) {
        companyslogan.setTextColor(Color.argb(a, r, g, b));
    }

    public void setSmalllogDrawnable(Bitmap bitmap) {
        smalllog.setImageBitmap(bitmap);
    }

    public void setBiglogDrawnable(Bitmap bitmap) {
        biglog.setImageBitmap(bitmap);
    }

    public String getCompanyName() {
        return companyname.getText().toString();
    }

    public Bitmap getSmalllog() {
        return ((BitmapDrawable) smalllog.getDrawable()).getBitmap();
    }


    public static interface OnCompanyEntityListener{
        void onCampantEnyityClick();
    };

    private OnCompanyEntityListener onCompanyEntityListener;

    public void setOnCompanyEntityListener(OnCompanyEntityListener onCompanyEntityListener){
        this.onCompanyEntityListener = onCompanyEntityListener;
    }

}
