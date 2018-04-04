package com.cc.android.zcommon.base.lcee;


import com.cc.android.zcommon.base.RxBaseView;

import java.util.List;

/**
 * RxMVPView基础接口
 * <br/>
 * loading - content - empty - error 模式的Rx请求
 * <br/>
 * 主要用于列表加载等
 */
public interface RxLceeView<M> extends RxBaseView {
  /**
   * 加载时显示加载框
   *
   * <b>The loading view must have the id = R.id.loadingView</b>
   */
  void showLoading(boolean pullToRefresh);

  /**
   * 显示内容
   * <b>The content view must have the id = R.id.contentView</b>
   * * <br/>
   * please {@link #setData(List)} before {@link #showContent()}
   */
  void showContent();


  /**
   * 显示错误消息
   * <b>The error view must be a TextView with the id = R.id.errorView</b>
   */
  void showError(Throwable e, boolean pullToRefresh);

  /**
   * 显示内容
   * The data that should be displayed with {@link #showContent()}
   * <br/>
   * please {@link #setData(List)} before {@link #showContent()}
   */
  void setData(List<M> data);

  /**
   * 加载数据
   * Load the data. Typically invokes the presenter method to load the desired data.
   * <p>
   * <b>Should not be called from presenter</b> to prevent infinity loops. The method is declared
   * in
   * the views interface to add support for view state easily.
   * </p>
   *
   * @param pullToRefresh true, if triggered by a pull to refresh. Otherwise false.
   */
  void loadData(boolean pullToRefresh);

  /**
   * 设置空白文本内容
   *
   * @return empty_text content
   */
  String getEmptyMessage();

  /**
   * 设置空白图片
   *
   * @return empty_img resource
   */
  int getEmptyImage();
}
