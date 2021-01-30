package com.xsvideoLive.www.mvp.ui.activity.square;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.mvp.contract.FamilyRecordContract;
import com.xsvideoLive.www.mvp.presenter.FamilyRecordPresenter;
import com.xsvideoLive.www.mvp.ui.adapter.FamilyRecordAdapter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.RecordResult;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class FamilyApplicationRecordAct extends BaseMvpActivity<FamilyRecordPresenter> implements FamilyRecordContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.title)
    TitleBarView title;
    @BindView(R.id.rl_record)
    RecyclerView mRlRecord;

    private FamilyRecordAdapter mAdapter;

    private UserBean userInfo;

    @Override
    public int setLayoutId() {
        return R.layout.activity_family_application_record;
    }

    @Override
    protected FamilyRecordPresenter createPresenter() {
        return new FamilyRecordPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        userInfo = SPUtils.getInstance().getUserInfo();
        mPresenter.attachView(this);
        title.setOnClickCallback(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mAdapter = new FamilyRecordAdapter();
        mRlRecord.setLayoutManager(new LinearLayoutManager(this));
        mRlRecord.setAdapter(mAdapter);
        mPresenter.getRecord(null, userInfo.getId());

    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void refresh(RefreshLayout refreshLayout, List<RecordResult> recordResults) {
        if (refreshLayout != null)
            refreshLayout.finishRefresh();

        mAdapter.setList(recordResults);

        if (recordResults.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.resetNoMoreData();
        }
    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, List<RecordResult> results) {
        if (results.size() <= 0) {
            if (refreshLayout != null)
                refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            if (refreshLayout != null)
                refreshLayout.finishLoadMore();
        }

        mAdapter.addData(results);

    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getRecord(refreshLayout, userInfo.getId());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getRecord(refreshLayout, userInfo.getId());
    }
}
