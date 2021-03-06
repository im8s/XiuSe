package com.xsvideoLive.www.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.callback.FriendFabulousCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.RecommendContract;
import com.xsvideoLive.www.mvp.presenter.RecommendPresenter;
import com.xsvideoLive.www.mvp.ui.activity.mine.HomePageAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyBuddyAct;
import com.xsvideoLive.www.mvp.ui.activity.msg.ReportAct;
import com.xsvideoLive.www.mvp.ui.activity.square.MessageDetailsAct;
import com.xsvideoLive.www.mvp.ui.adapter.FriendsCircleAdapter;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.TipsDialog;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.PhotoCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class AttentionFragment extends BaseMvpFragment<RecommendPresenter> implements RecommendContract.View, OnRefreshLoadMoreListener, PhotoCallback, OnItemClickListener, FriendFabulousCallback {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_view)
    RecyclerView rlView;

    private FriendsCircleAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected RecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);


    }

    @Override
    protected void onLazyLoad() {

        mAdapter = new FriendsCircleAdapter(getActivity(), this);
        mAdapter.setFabulousCallback(this);
        rlView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlView.setAdapter(mAdapter);
        refreshLayout.autoRefresh();

    }

    @Override
    public void refresh(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        refreshLayout.finishRefresh();
        mAdapter.setList(roomResults);


        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.resetNoMoreData();
        }

    }

    @Override
    public void loadMore(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults) {
        mAdapter.addData(roomResults);

        if (roomResults.size() <= 0) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onError(RefreshLayout refreshLayout, String msg) {

    }

    @Override
    public void fabulousSuccess(String msg) {

    }

    @Override
    public void onDeleteSuccess(String status, FriendsCircleResult result) {
        if (status.equals("1")) {
            mAdapter.remove(result);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.mPage = mPresenter.initPage;
        mPresenter.getAttention(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getAttention(refreshLayout);
    }


    @Override
    public void onItemPhotoClick(int index, List<ImageInfo> imageInfo) {

        ArrayList<String> imgs = new ArrayList<>();

        for (ImageInfo info : imageInfo) {
            imgs.add(info.getBigImageUrl());
        }

        photoPreviewWrapper(index, imgs);
    }


    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getActivity())
                .saveImgDir(new File(Constant.FILE_PATH)); // ????????????????????????????????? null??????????????????????????????

        if (imageInfo.size() == 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // ???????????????????????????
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        ToastUtils.showShort("position:" + position);
    }

    @Override
    public void onClickFabulous(FriendsCircleResult result, int position) {
        int hasLike = result.getHasLike();//????????????
        int likeNumber = result.getLikeNumber();//?????????
        if (hasLike == 1) {
            result.setHasLike(0);
            result.setLikeNumber(likeNumber - 1);
        } else if (hasLike == 0) {
            result.setHasLike(1);
            result.setLikeNumber(likeNumber + 1);
        }
        mAdapter.notifyDataSetChanged();
        mPresenter.fabulous(result.getId());
    }

    @Override
    public void onClickComment(FriendsCircleResult result, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.FRIEND_CIRCLE_RESULT, result);
        ActStartUtils.startAct(getActivity(), MessageDetailsAct.class, bundle);
//        ToastUtils.showShort("??????");
    }

    @Override
    public void onClickShare(FriendsCircleResult result, int position) {
        TipsDialog.createDialog(getActivity(), R.layout.dialog_share)
                .bindClick(R.id.tv_cancel, (v, dialog) -> {
                    dialog.dismiss();
                })
                .bindClick(R.id.tv_share_buddy, (v, dialog) -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BUDDY_KEY, Constant.TOPIC);
                    bundle.putSerializable(Constant.TOPIC, result);
                    ActStartUtils.startAct(getActivity(), MyBuddyAct.class, bundle);
                })
                .show();
//        ToastUtils.showShort("??????");
    }

    @Override
    public void onClickAvater(FriendsCircleResult result, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_ID, result.getUserId());
        ActStartUtils.startAct(getActivity(), HomePageAct.class, bundle);
    }

    @Override
    public void onClickMore(FriendsCircleResult result, int position) {
        more(result, SPUtils.getInstance().getUserInfo().getId());
    }

    /**
     * ??????myUserId ???????????????????????????????????????
     *
     * @param result
     * @param myUserId
     */
    private void more(FriendsCircleResult result, String myUserId) {

        String userId = result.getUserId();

        TipsDialog tipsDialog = TipsDialog.createDialog(getActivity(), R.layout.dialog_select_camera)
                .setTextColor(R.id.tv_camera, R.color.color_black)
                .setTextColor(R.id.tv_photo, R.color.color_aaaaaa)
                .setText(R.id.tv_photo, "??????")
                .bindClick(R.id.tv_camera, (v, dialog) -> {
                    dialog.dismiss();
                    if (userId.equals(myUserId)) {      //??????
//                        ToastUtils.showShort("??????");
                        mPresenter.deleteTopic(result.getId(),result);

                    } else {         //??????
//                        ToastUtils.showShort("??????");
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.REPOTR_TYPE,Constant.REPORT_TYPE_USER);
                        bundle.putString(Constant.REPORT_ID,result.getUserId());
                        ActStartUtils.startAct(getActivity(), ReportAct.class,bundle);
                        dialog.dismiss();
                    }
                })
                .bindClick(R.id.tv_photo, (v, dialog) -> {
                    dialog.dismiss();

                });


        if (userId.equals(myUserId)) {

            tipsDialog.setText(R.id.tv_camera, "??????");
        } else {
            tipsDialog.setText(R.id.tv_camera, "??????");
        }

        tipsDialog.show();
    }
}
