package com.cc.android.zcommon.support;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

/**
 * Finish activity manager for managing all of the activities' life circle.
 *
 * @Author:LiuLiWei
 */
public class ActivityManagerHelper {
    /**
     *
     */
    private static ActivityManagerHelper sManager;
    /**
     *
     */
    private Stack<WeakReference<Activity>> mActivityStack;

    /**
     * Private constructor.
     */
    private ActivityManagerHelper() {
    }

    /**
     * Get the manager's instance.
     *
     * @return
     */
    public static ActivityManagerHelper getManager() {
        if (sManager == null) {
            synchronized (ActivityManagerHelper.class) {
                if (sManager == null) {
                    sManager = new ActivityManagerHelper();
                }
            }
        }
        return sManager;
    }

    /**
     * Add an activity to activity stack.
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(new WeakReference<>(activity));
    }

    /**
     * Delete an activity from the stack.
     *
     * @param activity
     * @return
     */
    public boolean deleteActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove the element from the stack, when it's weak reference's released..
     */
    public void checkWeakReference() {
        if (mActivityStack != null) {
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity temp = activityReference.get();
                if (temp == null) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Get current activity.
     *
     * @return
     */
    public Activity currentActivity() {
        checkWeakReference();
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.lastElement().get();
        }
        return null;
    }

    /**
     * Finish current activity.
     */
    public void finishCurrentActivity() {
        Activity activity = currentActivity();
        if (activity != null) {
            finishSpecificActivity(activity);
        }
    }

    /**
     * Close a specific activity.
     *
     * @param activity
     */
    public void finishSpecificActivity(Activity activity) {
        if (deleteActivity(activity)) {
            activity.finish();
        }
    }

    /**
     * Close all the activities with it's class name.
     *
     * @param cls
     */
    public void finishSpecificActivity(Class<?> cls) {
        if (mActivityStack != null) {
            // Safely remove the activity with java's iterator.
            for (Iterator<WeakReference<Activity>> it = mActivityStack.iterator(); it.hasNext(); ) {
                WeakReference<Activity> activityReference = it.next();
                Activity activity = activityReference.get();
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }

    /**
     * Finish all the activities in the stack.
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
            for (WeakReference<Activity> activityReference : mActivityStack) {
                Activity activity = activityReference.get();
                if (activity != null) {
                    activity.finish();
                }
            }
            mActivityStack.clear();
        }
    }

    /**
     * Exit the application.
     */
    public void exitApp() {
        try {
            finishAllActivity();
            /**
             * Exit jvm and release all the resources occupied the memory.
             * The zero means exiting the application normally.
             */
            System.exit(0);
            //Kill the application's process.
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
