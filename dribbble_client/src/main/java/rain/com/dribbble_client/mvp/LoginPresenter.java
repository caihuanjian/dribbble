package rain.com.dribbble_client.mvp;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rain.com.dribbble_client.BuildConfig;
import rain.com.dribbble_client.api.DribbbleService;
import rain.com.dribbble_client.mvp.model.UserToken;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.Remember;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class LoginPresenter implements LoginContract.Presenter {

    @Inject
    DribbbleService mDribbbleService;

    private LoginContract.LoginView mLoginView;

    private Disposable mDisposable;

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

        mDisposable = mDribbbleService.getUserToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserToken>() {
                    @Override
                    public void accept(@NonNull UserToken userToken) throws Exception {
                        Remember.putString(CommonConstant.KEY_ACCESS_TOKEN, userToken.getAccessToken());
                        Remember.putString(CommonConstant.KEY_TOKEN_TYPE, userToken.getTokenType());
                        mLoginView.loginSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        mLoginView.loginFailure();
                    }
                });
    }

    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
