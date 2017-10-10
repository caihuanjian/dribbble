package rain.com.dribbble_client.mvp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rain.com.dribbble_client.api.DribbbleService;
import rain.com.dribbble_client.mvp.model.Shots;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by HwanJ.Choi on 2017-5-17.
 */

public class ShotsPresenter implements ShotsContract.Presenter {

    public static final int POPULAR = 0;

    public static final int RECENT = 1;

    public static final int ANIMATED = 2;

    public static final int DEBUTS = 3;

    @Inject
    DribbbleService dribbbleService;

    private ShotsContract.ShotsView mView;

    private int mPage = 1;

    public void setView(ShotsContract.ShotsView mView) {
        this.mView = mView;
    }

    @Inject
    public ShotsPresenter() {
    }

    @Override
    public void getShots(int index, boolean isRefresh) {
        Map<String, String> params = new HashMap<>();
        switch (index) {
            case POPULAR:
                params.put("sort", "popular");
                break;
            case RECENT:
                params.put("sort", "recent");
                break;
            case ANIMATED:
                params.put("list", "animated");
                break;
            case DEBUTS:
                params.put("list", "debuts");
                break;
        }
        loadShots(params, isRefresh);
    }

    private void loadShots(Map<String, String> params, boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
            refreshShots(params);
            return;
        }
        loadShotsByPage(++mPage, params);
    }

    private void refreshShots(Map<String, String> params) {
        mView.setRefreshIndicator(true);
        final Observable<List<Shots>> observable = dribbbleService.getShotsList(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomSubscribe<List<Shots>>() {
                    @Override
                    public void onNext(List<Shots> shots) {
                        mView.setRefreshIndicator(false);
                        mView.onUpdate(shots, true);
                    }
                });
    }

    private void loadShotsByPage(int page, Map<String, String> params) {
        params.put("page", String.valueOf(page));
        final Observable<List<Shots>> observable = dribbbleService.getShotsList(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomSubscribe<List<Shots>>() {
                    @Override
                    public void onNext(List<Shots> shots) {
                        mView.onUpdate(shots, false);
                    }
                });
    }

    class CustomSubscribe<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            mView.setRefreshIndicator(false);
            mView.onError(e.getMessage());
        }

        @Override
        public void onNext(T t) {

        }
    }
}
