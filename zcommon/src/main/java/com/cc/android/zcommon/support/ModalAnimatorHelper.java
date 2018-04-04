package com.cc.android.zcommon.support;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.cc.android.zcommon.R;

/**
 * 用于显示 loading 或者关闭 loading 的动画效果
 * Little helper class for animating content, error and loading view
 */
public class ModalAnimatorHelper {

  private ModalAnimatorHelper() {
  }

  /**
   * 显示modalLoadingView
   */
  public static void showLoading(@NonNull View loadingView) {
    loadingView.setVisibility(View.VISIBLE);
  }

  /**
   * 关闭显示modalLoadingView
   */
  public static void closeLoading(@NonNull final View loadingView) {
    final Resources resources = loadingView.getResources();
    // Not visible yet, so animate the view in
    AnimatorSet set = new AnimatorSet();
    ObjectAnimator in = ObjectAnimator.ofFloat(loadingView, View.ALPHA, 1f);
    ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, View.ALPHA, 0f);

    set.playTogether(in, loadingOut);
    set.setDuration(resources.getInteger(R.integer.lce_error_view_show_animation_time));

    set.addListener(new AnimatorListenerAdapter() {

      @Override
      public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
      }

      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        loadingView.setVisibility(View.GONE);
        loadingView.setAlpha(1f); // For future showLoading calls
      }
    });

    set.start();
  }
}
