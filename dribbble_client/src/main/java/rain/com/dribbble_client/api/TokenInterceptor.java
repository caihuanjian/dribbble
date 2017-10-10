package rain.com.dribbble_client.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.Remember;

/**
 * Created by HwanJ.Choi on 2017-5-17.
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request original = chain.request();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Remember.getString(CommonConstant.KEY_TOKEN_TYPE, ""))
                .append(" ")
                .append(Remember.getString(CommonConstant.KEY_ACCESS_TOKEN, ""));
        Request request = original.newBuilder().addHeader("Authorization", stringBuilder.toString()).build();
        return chain.proceed(request);
    }
}
