package rain.com.dribbble_client.mvp;

import javax.inject.Inject;

import rain.com.dribbble_client.BuildConfig;
import rain.com.dribbble_client.api.DribbbleService;
import rain.com.dribbble_client.mvp.model.UserToken;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.Remember;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @Inject
    DribbbleService mDribbbleService;

    private LoginContract.LoginView mLoginView;

    @Inject
    public LoginPresenter() {
    }

    public void setUserView(LoginContract.LoginView userView) {
        if (userView == null) {
            throw new NullPointerException("view can not be null");
        }
        mLoginView = userView;
    }

    @Override
    public void getLoginToken(String code) {

        Observable<UserToken> observable = mDribbbleService.getUserToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SelfDefineSubscriber<UserToken>() {

                    @Override
                    public void onNext(UserToken userToken) {
                        super.onNext(userToken);
                        Remember.putString(CommonConstant.KEY_ACCESS_TOKEN, userToken.getAccessToken());
                        Remember.putString(CommonConstant.KEY_TOKEN_TYPE, userToken.getTokenType());
                        mLoginView.loginSuccess();
                    }
                })
        ;

    }

    class SelfDefineSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
//            mView.onComplete();
        }

        @Override
        public void onError(Throwable e) {
//            CLogger.e(e);
            mLoginView.loginFailure();
        }

        @Override
        public void onNext(T t) {

        }
    }
}
