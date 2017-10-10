package rain.com.dribbble_client.ui.activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import rain.com.dribbble_client.R;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.Remember;

public class SplashActivity extends BaseActivity {

    private Button mLoginBtn;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        mImage = (ImageView) findViewById(R.id.iv_splash_logo);
        checkToken();
    }

    private void checkToken() {
        String access_token = Remember.getString(CommonConstant.KEY_ACCESS_TOKEN, "");
        if (TextUtils.isEmpty(access_token)) {
            mLoginBtn.setVisibility(View.VISIBLE);
            return;
        }
        animatLogo();
    }

    private void animatLogo() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mImage, "alpha", 0, 1);
        alphaAnimator.setDuration(1000).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alphaAnimator.start();
    }

    public void showLoginUi(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected boolean allowDisplayHome() {
        return true;
    }
}
