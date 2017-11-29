package rain.com.dribbble_client.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public DribbbleService getDribbbleService(OkHttpClient client) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DribbbleService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(DribbbleService.class);
    }
}
