package com.cc.android.zcommon.api;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于对网络请求的Observable做转换.
 * <br/>
 * 声明在IO线程运行, 在main线程接收.
 */
public class ResponseTransformer<T> implements FlowableTransformer<T, T> {

  private ObservableTransformer<T, T> transformer;

  public ResponseTransformer() {
  }

  @Override
  public Publisher<T> apply(Flowable<T> upstream) {
    if (transformer != null) {
      return upstream
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread());
    } else {
      return upstream.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
  }

  public ResponseTransformer(ObservableTransformer<T, T> t) {
    transformer = t;
  }
}
