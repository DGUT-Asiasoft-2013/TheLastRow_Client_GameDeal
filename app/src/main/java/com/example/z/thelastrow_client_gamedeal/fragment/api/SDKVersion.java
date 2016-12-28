package com.example.z.thelastrow_client_gamedeal.fragment.api;

import android.os.Build;

/**
 * Created by Administrator on 2016/12/27.
 */

public class SDKVersion {
    private final static boolean MoreThanAPI19True = true;
    private final static boolean LessThanAPI19True = false;

    public static boolean isMoreThanAPI19() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            return MoreThanAPI19True;
        } else {
            return LessThanAPI19True;
        }
    }
}
