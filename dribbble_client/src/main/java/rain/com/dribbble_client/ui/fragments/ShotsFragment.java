package rain.com.dribbble_client.ui.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.inject.Inject;

import rain.com.dribbble_client.MainApp;
import rain.com.dribbble_client.R;
import rain.com.dribbble_client.mvp.ShotsContract;
import rain.com.dribbble_client.mvp.ShotsPresenter;
import rain.com.dribbble_client.mvp.model.Shots;
import rain.com.dribbble_client.ui.activitys.ShotsDetailsActivity;
import rain.com.dribbble_client.ui.adatpers.BaseAdatper;
import rain.com.dribbble_client.ui.adatpers.FootAdatpter;
import rain.com.dribbble_client.ui.adatpers.LoadMoreAdapter;
import rain.com.dribbble_client.ui.adatpers.ShotsAdapter;
import rain.com.dribbble_client.utils.CommonConstant;

/**
 * Created by HwanJ.Choi on 2017-5-17.
 */

public class ShotsFragment extends BaseFragment implements ShotsContract.ShotsView {

    @Inject
    ShotsPresenter mPresenter;

    private LoadMoreAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPageIndex;
    private View mRootView;
    private boolean flag;

    public static ShotsFragment newInstance(int pageIndex) {

        Bundle args = new Bundle();
        args.putInt(KEY_ARGS, pageIndex);
        ShotsFragment fragment = new ShotsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            final Application application = ((Activity) context).getApplication();
            ((MainApp) application).getmAppComponent().inject(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        }
        final View view = inflater.inflate(R.layout.fragment_shots_list, container, false);
        mRootView = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (flag)
            return;
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_shots_list);
        setUpRecycleView(recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout_shots_list);
        setUpRefreshLayout(mSwipeRefreshLayout);
        mPresenter.setView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (flag)
            return;
        mPageIndex = getArguments().getInt(KEY_ARGS);
        mPresenter.getShots(mPageIndex, true);
        flag = true;
    }

    private void setUpRecycleView(final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ShotsAdapter shotsAdapter = new ShotsAdapter(getActivity());
        shotsAdapter.setOnItemClickListener(new BaseAdatper.OnItemClickListener<Shots>() {
            @Override
            public void onItemClick(View view, Shots shots, int position) {
                Intent intent = new Intent(getActivity(), ShotsDetailsActivity.class);
                final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        view.findViewById(R.id.iv_item_shots_image), String.valueOf(shots.getId()));
                intent.putExtra(CommonConstant.KEY_SHOT_ID, shots.getId());
                startActivity(intent, options.toBundle());
            }
        });
        FootAdatpter footAdatpter = new FootAdatpter(shotsAdapter);
        footAdatpter.setFooterView(R.layout.view_loading);
        mAdapter = new LoadMoreAdapter(footAdatpter, recyclerView);
        mAdapter.setOnLoadMoreListener(new LoadMoreAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final View loading = recyclerView.findViewById(R.id.loading_shots_list);
                if (loading != null && loading instanceof AVLoadingIndicatorView) {
                    ((AVLoadingIndicatorView) loading).show();
                }
                mPresenter.getShots(mPageIndex, false);
                mAdapter.setLoading(true);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    private void setUpRefreshLayout(SwipeRefreshLayout swipeLayout) {
        swipeLayout.setColorSchemeResources(R.color.color_refresh_1,
                R.color.color_refresh_2,
                R.color.color_refresh_3,
                R.color.colorPrimary);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        //设置下拉刷新的监听
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getShots(mPageIndex, true);
            }
        });
    }

    @Override
    public void onUpdate(List<Shots> shots, boolean isForceRefresh) {
        if (isForceRefresh) {
            mAdapter.updateData(shots);
        } else {
            mAdapter.appendData(shots);
            mAdapter.setLoading(false);
        }
    }

    @Override
    public void setRefreshIndicator(boolean show) {
        mSwipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            final ViewGroup parent = (ViewGroup) mRootView.getParent();
            parent.removeView(mRootView);
        }
    }
}