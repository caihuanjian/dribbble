package rain.com.dribbble_client.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import rain.com.dribbble_client.BuildConfig;
import rain.com.dribbble_client.MainApp;
import rain.com.dribbble_client.R;
import rain.com.dribbble_client.mvp.LoginContract;
import rain.com.dribbble_client.mvp.LoginPresenter;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    private static final String LOGIN_URL = "https://dribbble.com/oauth/authorize?client_id=%s";
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Inject
    LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolBar);
        ((MainApp) getApplication()).getmAppComponent().inject(this);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mPresenter.setUserView(this);
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100)
                    mProgressBar.setVisibility(View.GONE);
                else {
                    if (mProgressBar.getVisibility() != View.VISIBLE)
                        mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

        });
        mWebView.setWebViewClient(new LoginClient());
        mWebView.loadUrl(String.format(LOGIN_URL, BuildConfig.CLIENT_ID));
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailure() {
        finish();
        Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean allowDisplayHome() {
        return true;
    }

    @Override
    public void onError(String message) {

    }


    private class LoginClient extends WebViewClient {
        private boolean isCodeProvided;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            super.shouldOverrideUrlLoading(view, request);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.contains("?code=") && !isCodeProvided) {
                isCodeProvided = true;
                Uri uri = Uri.parse(url);
                String code = uri.getQueryParameter("code");
                mPresenter.getLoginToken(code);
            }
        }
    }
}
