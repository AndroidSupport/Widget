package com.uniquext.android.recycleview.core;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

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
 * @date 2018/12/7  15:50
 */
public class CommonRecyclerHolder extends RecyclerView.ViewHolder {

    /**
     * 控件集
     */
    private SparseArray<View> mViews = new SparseArray<>();

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
    }

    public void setVisibility(@IdRes int id, int visibility) {
        getView(id).setVisibility(visibility);
    }

    /**
     * TextView 设置文本
     *
     * @param id      id
     * @param content content
     */
    public void setText(@IdRes int id, CharSequence content) {
        ((TextView) getView(id)).setText(content);
    }

    /**
     * TextView 设置文本
     *
     * @param id      id
     * @param content content
     */
    public void setText(@IdRes int id, @StringRes int content) {
        ((TextView) getView(id)).setText(content);
    }

    /**
     * TextView 设置文本颜色
     *
     * @param id    id
     * @param color color
     */
    public void setTextColor(@IdRes int id, @ColorInt int color) {
        ((TextView) getView(id)).setTextColor(color);
    }


    /**
     * ImageView 设置图片资源
     *
     * @param id       id
     * @param drawable drawable
     */
    public void setImageResource(@IdRes int id, @DrawableRes int drawable) {
        ((ImageView) getView(id)).setImageResource(drawable);
    }

    /**
     * ImageView 设置图片
     *
     * @param id       id
     * @param drawable drawable
     */
    public void setImageDrawable(@IdRes int id, Drawable drawable) {
        ((ImageView) getView(id)).setImageDrawable(drawable);
    }

    public void setItemOnClickListener(@IdRes int id, View.OnClickListener listener) {
        getView(id).setOnClickListener(listener);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param id  控件ID
     * @param <T> 控件类型
     * @return 控件
     */
    public <T extends View> T getView(@IdRes int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

}
