package com.cc.android.zcommon.support;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * RxHelper，管理Observable，避免发生持有context而导致内存泄露
 * <br/>
 * CompositeSubscription内部有一个Subscription的Set对象的集合，
 * <br/>
 * 可以调用unsubscription()方法取消集合中所有subcription的订阅。
 */
public class RxHelper {

    public static void unsubscribeIfNotNull(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static CompositeDisposable getNewCompositeSubIfUnsubscribed(
            CompositeDisposable disposable) {
        if (disposable == null || disposable.isDisposed()) {
            return new CompositeDisposable();
        }

        return disposable;
    }
}
