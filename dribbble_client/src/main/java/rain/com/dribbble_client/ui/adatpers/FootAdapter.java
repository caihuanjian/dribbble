package rain.com.dribbble_client.ui.adatpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import rain.com.dribbble_client.R;

/**
 * Created by HwanJ.Choi on 2017-5-18.
 */

public class FootAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataOperator<T> {

    enum ITEM_TYPE {
        NOMAL, FOOTER
    }

    private BaseAdatper<T> mAdapter;
    private View mFootView;

    public FootAdapter(BaseAdatper adatper) {
        mAdapter = adatper;
    }

    public View setFooterView(int res) {
        FrameLayout container = new FrameLayout(mAdapter.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(mAdapter.getContext()).inflate(res, container);
        final View loading = container.findViewById(R.id.loading_shots_list);
        if (loading != null && loading instanceof AVLoadingIndicatorView && getItemCount() <= 1) {
            ((AVLoadingIndicatorView) loading).hide();
        }
        mFootView = container;
        return mFootView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.FOOTER.ordinal()) {
            return new BaseAdatper.VH(mFootView);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            return;
        }
        mAdapter.onBindViewHolder((BaseAdatper.VH) holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE.FOOTER.ordinal();
        } else {
            return ITEM_TYPE.NOMAL.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public void updateData(List<T> list) {
        mAdapter.updateData(list);
        notifyDataSetChanged();
    }

    @Override
    public void appendData(List<T> list) {
        final int startPosition = mAdapter.getItemCount();
        mAdapter.appendData(list);
        notifyItemRangeChanged(startPosition, list.size());
    }
}
