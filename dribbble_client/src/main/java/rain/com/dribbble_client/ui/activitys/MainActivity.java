package rain.com.dribbble_client.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.HashMap;
import java.util.Map;

import rain.com.dribbble_client.R;
import rain.com.dribbble_client.ui.fragments.ShotsFragment;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.page_home);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected boolean allowDisplayHome() {
        return true;
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {


        private Map<Integer, Fragment> fragments = new HashMap<>();

        private static final String[] PAGE_TITLE = new String[]{"popular", "recent", "animated", "debuts"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(position);
            if (fragment != null)
                return fragment;
            fragments.put(position, ShotsFragment.newInstance(position));
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return PAGE_TITLE.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLE[position];
        }
    }

}
