package rain.com.dribbble_client.mvp;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public interface LoginContract {

    interface LoginView extends IView {
        void loginSuccess();

        void loginFailure();
    }

    interface Presenter extends IPresenter {
        void getLoginToken(String code);
    }
}
