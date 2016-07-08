/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rv.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/7/8.
 */
public class AppUtils {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources res;
        if (context == null) {
            res = Resources.getSystem();
        }
        else {
            res = context.getResources();
        }
        return res.getDisplayMetrics();
    }

    public static int dp2px(Context context, int dip) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return (int) (dip * dm.density + .5);
    }

    public static int sp2px(Context context, int sp) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return (int) (sp * dm.scaledDensity + .5);
    }
}
