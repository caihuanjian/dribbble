package rain.com.dribbble_client.mvp;

import javax.inject.Inject;

import rain.com.dribbble_client.api.DribbbleService;
import rain.com.dribbble_client.mvp.model.Shots;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        final Observable<Shots> obserble = mDribbbleService.getShotById(id);
        obserble.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomSubscribe<Shots>() {
                    @Override
                    public void onNext(Shots shots) {
                        super.onNext(shots);
                        mUserView.setShotsView(shots);
                    }
                });
    }


}
