package com.github.ratrofitwithmvc.wegates;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DensityUtils {

    public static int dip2px(Context context, float var1) {
        return density2px(context, var1, false);
    }

    public static int px2dip(Context context, float var1) {
        return px2density(context, var1, false);
    }

    public static int sp2px(Context context, float var1) {
        return density2px(context, var1, true);
    }

    public static int px2sp(Context context, float var1) {
        return px2density(context, var1, true);
    }

    private static int density2px(Context context, float var1, boolean isScaled) {
        return XXtoXX(var1, context, isScaled, true);
    }

    private static int px2density(Context context, float var1, boolean isScaled) {
        return XXtoXX(var1, context, isScaled, false);
    }

    private static int XXtoXX(float var1, Context context, boolean isScaled, boolean toPx) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float var2 = isScaled ? metrics.scaledDensity : metrics.density;
        if (toPx)
            return (int) (var1 * var2 + 0.5F);
        return (int) (var1 / var2 + 0.5F);
    }


    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


}
