package com.uniquext.android.recycleview;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
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
 * @date 2018/12/11  13:36
 */
public class LoadingFootHelper {

    private View mLoadingView;
    private AnimationDrawable mAnimationDrawable;

    public LoadingFootHelper(@NonNull final RecyclerView parent, @LayoutRes int layout) {
        this.mLoadingView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        this.mAnimationDrawable = (AnimationDrawable) ((AppCompatImageView) mLoadingView).getDrawable();
        this.bindRecyclerView(parent);
    }

    private void bindRecyclerView(@NonNull final RecyclerView parent) {
        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (parent.getAdapter() != null && parent.getAdapter().getItemCount() == position + 1)
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    return 1;
                }
            });
        }
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public void loading() {
        setVisibility(true);
        mAnimationDrawable.start();
    }

    public void complete() {
        mAnimationDrawable.stop();
        setVisibility(false);
    }

    private void setVisibility(boolean visibility) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) mLoadingView.getLayoutParams();
        if (visibility) {
            param.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            param.width = RecyclerView.LayoutParams.MATCH_PARENT;
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        mLoadingView.setLayoutParams(param);
    }

}
