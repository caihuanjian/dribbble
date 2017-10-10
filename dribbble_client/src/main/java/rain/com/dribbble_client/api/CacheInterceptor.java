package rain.com.dribbble_client.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HwanJ.Choi on 2017-5-22.
 */

public class CacheInterceptor implements Interceptor {
    /*
    *
    * 1、有网络的时候设置max-age=0或者no-cache，每次要使用缓存的时候都会去服务器验证缓存的新鲜度
    * 2、没网络时缓存策略设置成only-if-cache，只拿本地的缓存数据，没有则返回504
    *
    * */

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Cache-Control", "public,max-age=0")//缓存重新验证
                .removeHeader("Pragma")
                .build();
        final Headers headers = request.headers();
        for (int i = 0; i < headers.size(); i++) {
            Log.e("chj header", headers.name(i) + "," + headers.value(i));
        }
        Log.e("chj request", request.cacheControl().toString());
        Response response = chain.proceed(request);
        response = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,max-age=" + 30 * 24 * 3600).build();//缓存有效期30天，岂不是每一次都命中缓存 而不会从服务器取数据？
        Log.e("chj respone", response.cacheControl().toString());                  //request 指定了缓存过期策略，所以缓存有效但是并没卵用？
        return response;
    }
}
