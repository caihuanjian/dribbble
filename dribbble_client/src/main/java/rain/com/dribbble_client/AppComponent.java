package rain.com.dribbble_client;

import javax.inject.Singleton;

import dagger.Component;
import rain.com.dribbble_client.api.ApiModule;
import rain.com.dribbble_client.api.NetworkModule;
import rain.com.dribbble_client.ui.activitys.LoginActivity;
import rain.com.dribbble_client.ui.activitys.ShotsDetailsActivity;
import rain.com.dribbble_client.ui.fragments.ShotsFragment;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */
@Singleton
@Component(modules = {ApiModule.class, NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(LoginActivity activity);

    void inject(ShotsFragment fragment);

    void inject(ShotsDetailsActivity activity);
}
