package rain.com.dribbble_client.ui.adatpers;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by HwanJ.Choi on 2016-12-2.
 */

public class LoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataOperator<T> {

    private RecyclerView mRecyclerView;

    protected OnLoadMoreListener mLoadMoreListener;

    private FootAdapter<T> mFootAdapter;

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    protected boolean isLoading = false;

    public LoadMoreAdapter(FootAdapter<T> adapter, RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mFootAdapter = adapter;
        init();
    }

    private void init() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int itemCount;
                int lastItemPosition;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    itemCount = layoutManager.getItemCount();
                    lastItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (!isLoading && lastItemPosition + 1 >= itemCount && dy > 0) {
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }

                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    itemCount = layoutManager.getItemCount();
                    lastItemPosition = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && lastItemPosition + 1 >= itemCount && dy > 0) {
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    itemCount = layoutManager.getItemCount();
                    int[] positon = null;
                    positon = layoutManager.findLastVisibleItemPositions(positon);

                    for (int i = 0; i < positon.length; i++) {
                        if (!isLoading && positon[i] + 1 >= itemCount && dy > 0) {
                            if (mLoadMoreListener != null) {
                                mLoadMoreListener.onLoadMore();
                            }
                            isLoading = true;
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return mFootAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mFootAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mFootAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mFootAdapter.getItemCount();
    }

    @Override
    public void updateData(List<T> list) {
        mFootAdapter.updateData(list);
        notifyDataSetChanged();
    }

    @Override
    public void appendData(List<T> list) {
        final int startPosition = mFootAdapter.getItemCount() - 1;
        mFootAdapter.appendData(list);
        notifyItemRangeChanged(startPosition, list.size());
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }
}
