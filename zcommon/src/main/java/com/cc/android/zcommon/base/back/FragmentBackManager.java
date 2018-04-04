package com.cc.android.zcommon.base.back;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 *Fragment back manager for dealing with the back press event within a fragment.
 *
 * @Author:LiuLiWei
 */
public class FragmentBackManager {

    /**
     * Handle back press for the fragment belongs to a AppCompatActivity.
     * @param appCompatActivity
     * @return
     */
    public static boolean handleBackPress(AppCompatActivity appCompatActivity) {
        return handleBackPress(appCompatActivity.getSupportFragmentManager());
    }

    /**
     * Handle back press for the fragment belongs to it's parent fragment..
     * @param fragment
     * @return
     */
    public static boolean handleBackPress(Fragment fragment) {
        return handleBackPress(fragment.getChildFragmentManager());
    }

    /**
     * Handle back press for a fragment.
     * @param fragmentManager
     * @return
     */
    public static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null) return false;

        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment child = fragments.get(i);

            if (isFragmentBackHandled(child)) {
                return true;
            }
        }
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    /**
     * Handle the back press for the fragment belongs to a ViewPager.
     * @param viewPager
     * @return
     */
    public static boolean handleBackPress(ViewPager viewPager) {
        if (viewPager == null) return false;

        PagerAdapter adapter = viewPager.getAdapter();

        if (adapter == null) return false;

        int currentItem = viewPager.getCurrentItem();
        Fragment fragment;
        if (adapter instanceof FragmentPagerAdapter) {
            fragment = ((FragmentPagerAdapter) adapter).getItem(currentItem);
        } else if (adapter instanceof FragmentStatePagerAdapter) {
            fragment = ((FragmentStatePagerAdapter) adapter).getItem(currentItem);
        } else {
            fragment = null;
        }
        return isFragmentBackHandled(fragment);
    }

    /**
     * Invoke the fragment's onFragmentBackPressed or not.
     * @param fragment
     * @return
     */
    public static boolean isFragmentBackHandled(Fragment fragment) {
        if(fragment != null
                && fragment.isVisible()
                && fragment.getUserVisibleHint() //for ViewPager
                && fragment instanceof IFragmentBackPressed) {
            ((IFragmentBackPressed) fragment).onFragmentBackPressed();
            return true;
        }
        return false;
    }
}
