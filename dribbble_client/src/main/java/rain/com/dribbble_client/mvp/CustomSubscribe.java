package rain.com.dribbble_client.mvp;

import rx.Subscriber;

/**
 * Created by HwanJ.Choi on 2017-5-24.
 */

class CustomSubscribe<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {

    }
}
