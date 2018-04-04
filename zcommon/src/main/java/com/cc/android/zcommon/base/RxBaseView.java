package com.cc.android.zcommon.base;

/**
 * RxMVPView基础接口
 * <br/>
 * 主要用于loading - showError 模式的Rx请求，表现为部分内容需要Rx请求，并不是整个页面渲染都需要Rx请求
 * <br/>
 * 比如：提交表单，或者局部数据请求
 */
public interface RxBaseView extends BaseView {
  /**
   * 表单提交时，显示模态加载框
   *
   * <b>The modal loading view must have the id = R.id.modalLoadingView</b>
   */
  void showModalLoading();

  void showModalLoading(String txt);

  void showModalLoading(String txt, boolean cancelable);

  void closeModalLoading();

  /**
   * 获取Context对象
   */
  RxBaseActivity getRxContext();
}
