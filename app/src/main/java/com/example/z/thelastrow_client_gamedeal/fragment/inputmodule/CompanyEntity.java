package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Fragment;
import android.content.res.ColorStateList;
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

    private TextView companyname,companyslogan;
    private ImageView smalllog,biglog;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {

        view = inflater.inflate(R.layout.fragment_page_feeds_companyentity, null);

        companyname = (TextView) view.findViewById(R.id.companyentity_companyname);
        companyslogan = (TextView) view.findViewById(R.id.companyentity_companyslogan);
        smalllog = (ImageView) view.findViewById(R.id.companyentity_companylog_small);
        biglog = (ImageView) view.findViewById(R.id.companyentity_companylog_big);

        }


        return view;
    }

    public void setCompanyNameText(String string) {
        companyname.setText(string);
    }

    public void setCompanyNameColor(ColorStateList color) {
        companyname.setTextColor(color);
    }

    public void setCompanySloganText(String string) {
        companyslogan.setText(string);
    }

    public void setCompanySloganColor() {
        companyslogan.setTextColor(Color.parseColor("#ff0099cc"));
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
        return ((BitmapDrawable)smalllog.getDrawable()).getBitmap();
    }
}
