package com.uniquext.android.recycleview.extra;

import androidx.annotation.NonNull;

import com.uniquext.android.recycleview.core.CommonRecyclerAdapter;
import com.uniquext.android.recycleview.core.CommonRecyclerHolder;

import java.util.List;

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
 * @date 2019/5/24  11:04
 */
public abstract class CommonRadioAdapter<T> extends CommonRecyclerAdapter<T> {

    private T checkedItem;
    private OnItemClickListener mOnItemRadioListener;

    public CommonRadioAdapter(@NonNull List<T> data, int... itemLayoutIds) {
        super(data, itemLayoutIds);
        this.mOnItemClickListener = (view, position) -> {
            int oldIndex = checkedItem == null ? 0 : mData.indexOf(checkedItem);
            checkedItem = mData.get(position);
            notifyItemChanged(oldIndex);
            notifyItemChanged(position);
            if (mOnItemRadioListener != null) {
                mOnItemRadioListener.onItemClick(view, position);
            }
        };
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemRadioListener = onItemClickListener;
    }

    @Override
    protected void convert(@NonNull CommonRecyclerHolder holder, int position, T item) {
        convert(holder, position, item, checkedItem != null && checkedItem.equals(item));
    }

    public abstract void convert(CommonRecyclerHolder holder, int position, T item, boolean isChecked);
}
