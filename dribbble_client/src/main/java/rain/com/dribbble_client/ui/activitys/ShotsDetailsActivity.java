package rain.com.dribbble_client.ui.activitys;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import rain.com.dribbble_client.MainApp;
import rain.com.dribbble_client.R;
import rain.com.dribbble_client.compat.Util;
import rain.com.dribbble_client.mvp.DetailContract;
import rain.com.dribbble_client.mvp.DetailPresenter;
import rain.com.dribbble_client.mvp.model.Shots;
import rain.com.dribbble_client.utils.CommonConstant;
import rain.com.dribbble_client.utils.ImageSize;

/**
 * Created by HwanJ.Choi on 2017-5-19.
 */

public class ShotsDetailsActivity extends FragmentActivity implements DetailContract.IDetailView {

    private AppBarLayout mAppBar;

    private CollapsingToolbarLayoutState mCollapState;
    private CollapsingToolbarLayout mCollapLayout;

    private View mBtnBar;

    private String mTitle;
    private TextView mDesc, mAutorInfo, mUserName;
    private ImageView mPoster, mAvatar;

    @Inject
    DetailPresenter mPresenter;

    private enum CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERNEDIATE
    }

    @Override
    protected boolean allowDisplayHome() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.activity_shot_detial);
        ((MainApp) getApplication()).getmAppComponent().inject(this);
        getSupportActionBar().setTitle("");

        mAppBar = (AppBarLayout) findViewById(R.id.appbar_detail);
        mBtnBar = findViewById(R.id.btnbar_detail_bar);
        mCollapLayout = (CollapsingToolbarLayout) findViewById(R.id.collap_layout_detail);

        mDesc = (TextView) findViewById(R.id.tv_detail_shots_desc);
        mAutorInfo = (TextView) findViewById(R.id.tv_detail_shots_autor);
        mPoster = (ImageView) findViewById(R.id.image_detail_shots_poster);
        mAvatar = (ImageView) findViewById(R.id.image_avatar);
        mUserName = (TextView) findViewById(R.id.tv_user_name);

        setListener();
        mPresenter.setUserView(this);
        int shotId = getIntent().getExtras().getInt(CommonConstant.KEY_SHOT_ID);
//        mPoster.setTransitionName(String.valueOf(shotId));
        mPresenter.getShotsDetail(shotId);
    }

    private void setListener() {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (mCollapState != CollapsingToolbarLayoutState.EXPANDED) {
                        mCollapState = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        mCollapLayout.setTitle(mTitle);//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (mCollapState != CollapsingToolbarLayoutState.COLLAPSED) {
                        mCollapLayout.setTitle("");//设置title不显示
                        mBtnBar.setVisibility(View.VISIBLE);//隐藏播放按钮

                        mCollapState = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (mCollapState != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (mCollapState == CollapsingToolbarLayoutState.COLLAPSED) {
                            mBtnBar.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        mCollapState = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    @Override
    public Fragment creatFragment(int id) {
        return null;
    }

    @Override
    public void onError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setShotsView(Shots shot) {
        Glide.with(this).load(shot.getImages().getNormal()).fitCenter().placeholder(R.color.color_palce_holder).into(mPoster);
        Glide.with(this).load(shot.getUser().getAvatar_url()).centerCrop().override(ImageSize.AVATAR[0], ImageSize.AVATAR[1])
                .bitmapTransform(new CropCircleTransformation(this)).into(mAvatar);
        mUserName.setText(Html.fromHtml(shot.getUser().getUsername()));

        mTitle = shot.getTitle();
        mCollapLayout.setTitle(shot.getTitle());
        mDesc.setText(Util.fromHtmlCompat(shot.getDescription()));
        mAutorInfo.setText(shot.getUser().getName());

    }

}
