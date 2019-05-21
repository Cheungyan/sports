package me.zy.sports.utils;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class StateThemeUtil {


    public static void uesState(Activity activity){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS // 透明状态栏
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//导航栏
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏隐藏
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //视图延伸至导航栏区域，导航栏上浮于视图之上
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);//视图延伸至状态栏区域，状态栏上浮于视图之上
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //获取样式中的属性值
            TypedValue typedValue = new TypedValue();
            activity.getTheme().resolveAttribute(android.R.attr.colorPrimary, typedValue, true);
            int[] attribute = new int[]{android.R.attr.colorPrimary};
            TypedArray array = activity.obtainStyledAttributes(typedValue.resourceId, attribute);
            int color = array.getColor(0, Color.TRANSPARENT);
            array.recycle();

            window.setStatusBarColor(color);
        }
    }
}
