package com.uniquext.android.recycleview.extra;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.uniquext.android.recycleview.core.old.CommonRecyclerAdapter;
import com.uniquext.android.recycleview.core.old.CommonRecyclerHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @date 2018/12/27  15:56
 */
public abstract class CommonChooseAdapter<T> extends CommonRecyclerAdapter<T> {

    private Set<T> mCheckedData = new HashSet<>();
    private OnItemChangedListener mOnItemChangedListener = null;

    public CommonChooseAdapter(@NonNull List<T> data, @LayoutRes int itemLayoutId) {
        super(data, itemLayoutId);
        mOnItemClickListener = this::changeChecked;
    }

    protected void changeChecked(View view, int position) {
        view.setFocusable(true);
        view.requestFocus();
        T t = mData.get(position);
        if (mCheckedData.contains(t)) {
            removeCheckedItem(t);
        } else {
            addCheckedItem(t);
        }
    }

    @Override
    protected void convert(@NonNull CommonRecyclerHolder holder, int position, T item) {
        convert(holder, position, item, mCheckedData.contains(item));
    }

    public abstract void convert(CommonRecyclerHolder holder, int position, T item, boolean isChecked);

    public void setOnItemChangedListener(OnItemChangedListener listener) {
        this.mOnItemChangedListener = listener;
        this.mOnItemChangedListener.onItemChanged(0, mCheckedData.size());
    }

    public Set<T> getDataChecked() {
        return mCheckedData;
    }

    public void setCheckable(boolean checkable) {
        setItemClickable(checkable);
    }

    public void checkAll() {
        addCheckedList(mData);
    }

    public void unCheckAll() {
        removeCheckedList(mData);
    }

    public void addCheckedList(List<T> checkList) {
        mCheckedData.addAll(checkList);
        refreshAll();
    }

    public void removeCheckedList(List<T> checkList) {
        mCheckedData.removeAll(checkList);
        refreshAll();
    }

    public void removeAllChecked() {
        mCheckedData.clear();
        refreshAll();
    }


    public void addCheckedItem(T item) {
        int index = mData.indexOf(item);
        if (index >= 0) {
            addCheckedItem(index);
        } else {
            mCheckedData.add(item);
            refreshAll();
        }
    }

    public void addCheckedItem(int position) {
        if (position >= 0 && position < mData.size()) {
            mCheckedData.add(mData.get(position));
            refreshItem(position);
        }
    }

    public void removeCheckedItem(T item) {
        removeCheckedItem(mData.indexOf(item));
    }

    public void removeCheckedItem(int position) {
        if (position >= 0 && position < mData.size()) {
            mCheckedData.remove(mData.get(position));
            refreshItem(position);
        }
    }

    public void updateItem(int position){
        if (position >= 0 && position < mData.size()) {
            mCheckedData.clear();
            mCheckedData.add(mData.get(position));
            notifyDataSetChanged();
        }
    }

    private void refreshItem(int position) {
        notifyItemChanged(position);
        if (mOnItemChangedListener != null) {
            mOnItemChangedListener.onItemChanged(position, mCheckedData.size());
        }
    }

    private void refreshAll() {
        notifyDataSetChanged();
        if (mOnItemChangedListener != null) {
            mOnItemChangedListener.onItemChanged(-1, mCheckedData.size());
        }
    }

    public interface OnItemChangedListener {
        void onItemChanged(int position, int totalChecked);
    }

}