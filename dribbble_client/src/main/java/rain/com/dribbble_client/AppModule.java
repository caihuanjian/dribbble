package rain.com.dribbble_client;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */
@Module
public class AppModule {

    private static final int CACHE_SIZE = 10 * 1024 * 1024;

    private static final String CACHE_RAIN = "rain_cache";

    private final Context mContext;

    public AppModule(Context c) {
        mContext = c;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public Cache provideCache() {
        File cacheDir = new File(mContext.getCacheDir().getPath() + File.separator + CACHE_RAIN);
        Cache cache = new Cache(cacheDir, CACHE_SIZE);
        return cache;
    }
}
