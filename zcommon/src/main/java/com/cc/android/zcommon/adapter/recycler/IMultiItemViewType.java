package com.cc.android.zcommon.adapter.recycler;

/**
 * Interface for multiple types.
 */
public interface IMultiItemViewType<T> {
    /**
     * ItemView type, a non-negative integer is better.
     *
     * @param position current position of ViewHolder
     * @param t        model item
     * @return viewType
     */
    int getItemViewType(int position, T t);

    /**
     * Layout Res
     *
     * @param viewType {@link #getItemViewType(int, Object)}
     * @return {@link android.support.annotation.LayoutRes}
     */
    int getLayoutId(int viewType);
}