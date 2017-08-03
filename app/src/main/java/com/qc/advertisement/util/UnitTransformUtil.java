package com.qc.advertisement.util;

import android.content.Context;

/**
 * 将dp与px的相互转换
 * Created by Administrator on 2017/4/8.
 */
public class UnitTransformUtil {
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale + 0.5f);
    }
}
