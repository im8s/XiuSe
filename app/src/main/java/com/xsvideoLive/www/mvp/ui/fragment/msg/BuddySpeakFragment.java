package com.xsvideoLive.www.mvp.ui.fragment.msg;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.BuddyContract;
import com.xsvideoLive.www.mvp.presenter.BuddyPresenter;
import com.xsvideoLive.www.mvp.ui.activity.msg.SpeakAct;
import com.xsvideoLive.www.mvp.ui.adapter.BuddySpeakAdapter;
import com.xsvideoLive.www.net.bean.MyFollowResult;
import com.xsvideoLive.www.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BuddySpeakFragment extends BaseMvpFragment<BuddyPresenter> implements BuddyContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rcy_friend)
    RecyclerView rcyFriend;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private BuddySpeakAdapter mAdapter;

    public static BuddySpeakFragment newInstance() {
        return new BuddySpeakFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_buddy;
    }


    @Override
    protected BuddyPresenter createPresenter() {
        return new BuddyPresenter();
    }

    @Override
    protected void onLazyLoad() {
        mAdapter = new BuddySpeakAdapter();
        rcyFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcyFriend.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();

                if (position == 0) {
                    outRect.set(0, 15, 0, 8);
                } else {
                    outRect.set(0, 8, 0, 8);
                }
            }
        });
        rcyFriend.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {


                MyFollowResult item = mAdapter.getItem(position);

                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(V2TIMConversation.V2TIM_C2C);
                chatInfo.setId(item.getUserId());
                chatInfo.setChatName(item.getUserName());
                List<Object> list = new ArrayList<>();
                list.add(item.getUserPictureUrl());
                chatInfo.setIconUrlList(list);

                Intent intent = new Intent(getActivity(), SpeakAct.class);
                intent.putExtra(Constant.CHAT_INFO, chatInfo);
                getActivity().startActivity(intent);

            }
        });

        mPresenter.getMyFriends(null);

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();

    }

    private void init() {
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getMyFriends(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMyFriends(refreshLayout);
    }


    @Override
    public void refreshSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (refresh != null)
            refresh.finishRefresh();

        mAdapter.setList(myFollows);

        if (myFollows.size() <= 0) {
            mAdapter.setEmptyView(R.layout.buddy_empty);
            if (refresh != null) {
                refresh.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (refresh != null) {
                refresh.resetNoMoreData();
            }
        }
    }

    @Override
    public void loadMoreSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows) {
        if (myFollows.size() <= 0) {

            if (refresh != null)
                refresh.finishLoadMoreWithNoMoreData();
        } else {
            if (refresh != null)
                refresh.finishLoadMore();
            mAdapter.addData(myFollows);
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}
