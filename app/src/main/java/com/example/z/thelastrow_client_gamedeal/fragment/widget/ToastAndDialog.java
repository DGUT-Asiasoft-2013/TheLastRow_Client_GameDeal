package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Z on 2016/12/23.
 */

public  class ToastAndDialog {
    public static void setToastShort(Context context, String s){
        Toast.makeText(context, s,Toast.LENGTH_SHORT).show();
    }
}
