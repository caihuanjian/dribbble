package rain.com.dribbble_client.mvp;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rain.com.dribbble_client.api.DribbbleService;
import rain.com.dribbble_client.mvp.model.Shots;

/**
 * Created by HwanJ.Choi on 2017-5-24.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.IDetailView mUserView;

    @Inject
    DribbbleService mDribbbleService;

    @Inject
    public DetailPresenter() {
    }

    public void setUserView(DetailContract.IDetailView userView) {
        this.mUserView = userView;
    }

    @Override
    public void getShotsDetail(int id) {
        mDribbbleService.getShotById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Shots>() {
                    @Override
                    public void accept(@NonNull Shots shots) throws Exception {
                        mUserView.setShotsView(shots);
                    }
                });
    }
}
