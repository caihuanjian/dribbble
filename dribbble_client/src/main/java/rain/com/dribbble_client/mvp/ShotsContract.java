package rain.com.dribbble_client.mvp;

import java.util.List;

import rain.com.dribbble_client.mvp.model.Shots;

/**
 * Created by HwanJ.Choi on 2017-5-17.
 */

public interface ShotsContract {

    interface ShotsView extends IView {
        void onUpdate(List<Shots> shots, boolean isForceRefresh);

        void setRefreshIndicator(boolean show);

        void onError(String message);
    }

    interface Presenter extends IPresenter {
        void getShots(int index, boolean isRefresh);
    }
}
