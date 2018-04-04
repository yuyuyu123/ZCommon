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
 * 用于显示 loading - content - error 模式的动画效果
 * Little helper class for animating content, error and loading view
 */
public class LceeAnimatorHelper {

  private LceeAnimatorHelper() {
  }

  /**
   * Show the loading view. No animations, because sometimes loading things is pretty fast (i.e.
   * retrieve data from memory com.ccclubs.common.cache).
   */
  public static void showLoading(@NonNull final View loadingView, @NonNull final View contentView,
                                 @NonNull final View errorView, @NonNull final View emptyView) {

    contentView.setVisibility(View.GONE);
    emptyView.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    loadingView.setVisibility(View.VISIBLE);
  }

  /**
   * Shows the error view instead of the loading view
   */
  public static void showErrorView(@NonNull final View loadingView, @NonNull final View contentView,
                                   @NonNull final View errorView, @NonNull final View emptyView) {

    contentView.setVisibility(View.GONE);
    emptyView.setVisibility(View.GONE);

    final Resources resources = loadingView.getResources();
    // Not visible yet, so animate the view in
    AnimatorSet set = new AnimatorSet();
    ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
    ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0f);

    set.playTogether(in, loadingOut);
    set.setDuration(resources.getInteger(R.integer.lce_error_view_show_animation_time));

    set.addListener(new AnimatorListenerAdapter() {

      @Override
      public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        errorView.setVisibility(View.VISIBLE);
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

  /**
   * Display the content instead of the loadingView
   */
  public static void showContent(@NonNull final View loadingView, @NonNull final View contentView,
                                 @NonNull final View errorView, @NonNull final View emptyView) {

    if (contentView.getVisibility() == View.VISIBLE) {
      // No Changing needed, because contentView is already visible
      emptyView.setVisibility(View.GONE);
      errorView.setVisibility(View.GONE);
      loadingView.setVisibility(View.GONE);
    } else {
      errorView.setVisibility(View.GONE);
      emptyView.setVisibility(View.GONE);

      final Resources resources = loadingView.getResources();
      final int translateInPixels =
          resources.getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y);
      // Not visible yet, so animate the view in
      AnimatorSet set = new AnimatorSet();
      ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView, "alpha", 0f, 1f);
      ObjectAnimator contentTranslateIn =
          ObjectAnimator.ofFloat(contentView, "translationY", translateInPixels, 0);

      ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView, "alpha", 1f, 0f);
      ObjectAnimator loadingTranslateOut =
          ObjectAnimator.ofFloat(loadingView, "translationY", 0, -translateInPixels);

      set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut, loadingTranslateOut);
      set.setDuration(resources.getInteger(R.integer.lce_content_view_show_animation_time));

      set.addListener(new AnimatorListenerAdapter() {

        @Override
        public void onAnimationStart(Animator animation) {
          contentView.setTranslationY(0);
          loadingView.setTranslationY(0);
          contentView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
          loadingView.setVisibility(View.GONE);
          loadingView.setAlpha(1.f); // For future showLoading calls
          contentView.setTranslationY(0);
          loadingView.setTranslationY(0);
        }
      });

      set.start();
    }
  }
}
