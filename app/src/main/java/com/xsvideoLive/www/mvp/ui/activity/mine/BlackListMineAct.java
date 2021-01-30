package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.mvp.contract.BlackListMineContract;
import com.xsvideoLive.www.mvp.presenter.BlackListMinePresenter;
import com.xsvideoLive.www.mvp.ui.adapter.BlackListMineAdapter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.BlackListMineBean;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.TipsDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public class BlackListMineAct extends BaseMvpActivity<BlackListMinePresenter> implements BlackListMineContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {
    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.rcy_black_list)
    RecyclerView mRcyBlackList;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private BlackListMineAdapter mAdapter = new BlackListMineAdapter();

    private UserBean mUserInfo = SPUtils.getInstance().getUserInfo();


    @Override
    public int setLayoutId() {
        return R.layout.activity_black_list_set;
    }


    @Override
    protected BlackListMinePresenter createPresenter() {
        return new BlackListMinePresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mRefresh.setOnRefreshLoadMoreListener(this);
        mTitleBar.setOnClickCallback(this);

        init();
        mPresenter.getBlackList(null, mUserInfo.getId());

    }

    private void init() {

        mRcyBlackList.setLayoutManager(new LinearLayoutManager(this));
        mRcyBlackList.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {

            TipsDialog.createDialog(this, R.layout.dialog_room_manager)
                    .setText(R.id.tv_flag, "是否移除" + mAdapter.getItem(position).getUserName())
                    .bindClick(R.id.tv_cancel, (v, dialog) -> {
                        dialog.dismiss();
                    })
                    .bindClick(R.id.tv_confirm, (v, dialog) -> {
                        //删除
                        mPresenter.removeBlackMine(mAdapter.getItem(position).getId(), position);
                        dialog.dismiss();
                    }).show();

            return true;
        });

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getBlackList(refreshLayout, mUserInfo.getId());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getBlackList(refreshLayout, mUserInfo.getId());
    }

    @Override
    public void refreshSuccess(RefreshLayout refresh, List<BlackListMineBean> roomBlack) {
        if (refresh != null) {
            refresh.finishRefresh();
        }

        if (roomBlack.size() <= 0) {
            mAdapter.setEmptyView(R.layout.empty_blacklist_mine);
            if (refresh != null) {
                refresh.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (refresh != null) {
                refresh.resetNoMoreData();
            }
        }
        mAdapter.setList(roomBlack);
    }

    @Override
    public void loadMoreSuccess(RefreshLayout refresh, List<BlackListMineBean> roomBlack) {

        if (roomBlack == null || roomBlack.size() <= 0) {
            if (refresh != null) {
                refresh.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (refresh != null) {
                refresh.finishLoadMore();
            }
            mAdapter.addData(roomBlack);
        }

    }

    @Override
    public void removeSuccess(int position, Integer status) {

        if (status == 1) {
            mAdapter.removeAt(position);
            ToastUtils.showShort("移除成功");
        } else {
            ToastUtils.showShort("移除失败");
        }
    }


    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }


}
