package rain.com.dribbble_client.ui.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by HwanJ.Choi on 2017-5-19.
 */

public abstract class FragmentActivity extends BaseActivity {

    private final String TAG_PREFIX = "fragment_";

    public void addFragment(int id, int containerId) {

        String fragmentTag = new StringBuilder().append(TAG_PREFIX).append(id).toString();
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment fragment : manager.getFragments()) {
            if (!fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
        Fragment newFragment = manager.findFragmentByTag(fragmentTag);

        if (newFragment == null) {
            newFragment = creatFragment(id);
            transaction.add(containerId, newFragment, fragmentTag);
        } else {
            transaction.show(newFragment);
        }
        transaction.commit();
    }

    public abstract Fragment creatFragment(int id);

}
