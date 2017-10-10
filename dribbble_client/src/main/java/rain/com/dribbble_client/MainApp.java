package rain.com.dribbble_client;

import android.app.Application;
import android.content.Context;

import rain.com.dribbble_client.api.ApiModule;
import rain.com.dribbble_client.api.NetworkModule;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.Remember;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class MainApp extends Application {

    private Context mContext;
    private AppComponent mAppComponent;

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Remember.init(mContext, CommonConstant.SP_NAME);
        initInjector();
    }

    private void initInjector() {

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(mContext))
                .apiModule(new ApiModule())
                .networkModule(new NetworkModule())
                .build();

    }
}
