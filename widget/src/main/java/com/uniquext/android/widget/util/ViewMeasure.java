package com.uniquext.android.widget.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.Size;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author UniqueXT
 * @version 1.0
 * @date 2018/11/28  13:48
 * 测量
 */
public final class ViewMeasure {

    private ViewMeasure() {

    }

    /**
     * 获取屏幕大小
     *
     * @param manager 窗口管理器
     * @param screen  存储数组
     */
    public static void getScreenSize(WindowManager manager, @Size(2) int[] screen) {
        Point point = new Point();
        manager.getDefaultDisplay().getRealSize(point);
        screen[0] = point.x;
        screen[1] = point.y;
    }

    /**
     * 获取窗口大小
     * 可用区域
     *
     * @param manager 窗口管理器
     * @return DisplayMetrics
     */
    public static DisplayMetrics getWindowsMetrics(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 获取系统控件高度
     *
     * @param context   上下文
     * @param systemBar 控件类型
     * @return 高度
     */
    public static int getSystemBarHeight(Context context, SystemBar systemBar) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(systemBar.name, "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * .系统栏
     */
    public enum SystemBar {
        /**
         * 状态栏
         */
        Status("status_bar_height"),
        /**
         * 导航栏
         */
        Navigation("navigation_bar_height");

        /**
         * 系统栏对应ID的字符名
         */
        String name;

        SystemBar(String name) {
            this.name = name;
        }
    }

}
