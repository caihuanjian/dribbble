package rain.com.dribbble_client.mvp;

import rain.com.dribbble_client.mvp.model.Shots;

/**
 * Created by HwanJ.Choi on 2017-5-24.
 */

public interface DetailContract {

    interface Presenter extends IPresenter {
        void getShotsDetail(int id);
    }

    interface IDetailView extends IView {
        void setShotsView(Shots shot);
    }
}
