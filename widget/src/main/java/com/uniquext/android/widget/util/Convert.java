package com.uniquext.android.widget.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

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
 * @date 2018/11/28  13:47
 * 单位转换
 */
public final class Convert {

    private Convert() {

    }

    /**
     * 将相应单位的size转换为px
     *
     * @param unit    格式
     * @param context 上下文
     * @param size    dp/sp
     * @return px
     */
    private static float size(int unit, Context context, float size) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return TypedValue.applyDimension(unit, size, resources.getDisplayMetrics());
    }

    /**
     * 将dp转px
     *
     * @param context 上下文
     * @param size    dp
     * @return px
     */
    public static float dp(Context context, float size) {
        return size(TypedValue.COMPLEX_UNIT_DIP, context, size);
    }

    /**
     * 将sp转px
     *
     * @param context 上下文
     * @param size    sp
     * @return px
     */
    public static float sp(Context context, float size) {
        return size(TypedValue.COMPLEX_UNIT_SP, context, size);
    }
}
