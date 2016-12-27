package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * Created by Z on 2016/12/23.
 */

public  class ToastAndDialog {
    public static void setToastShort(Context context, String s){
        Toast.makeText(context, s,Toast.LENGTH_SHORT).show();
    }
    public static  void setDialog(Context context,String s){
        Dialog dialog=new Dialog(context);
        dialog.setTitle(s);
        dialog.setContentView(android.support.v7.appcompat.R.layout.abc_alert_dialog_button_bar_material);
        dialog.show();

    }
}
