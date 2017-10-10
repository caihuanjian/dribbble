package rain.com.dribbble_client.api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(new CacheInterceptor())
                .cache(cache)
                .build();
        return client;
    }
}
