package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.AlertDialog;
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


    public static  void setDialog(Context context, String msg){
        AlertDialog.Builder dialog= new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setPositiveButton("确定",null);
        dialog.show();
    }


    public static  void setDialog(Context context, String msg, DialogInterface.OnClickListener listener){
        AlertDialog.Builder dialog= new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setPositiveButton("确定",listener);
        dialog.setNegativeButton("取消",null);
        dialog.show();
    }


}
