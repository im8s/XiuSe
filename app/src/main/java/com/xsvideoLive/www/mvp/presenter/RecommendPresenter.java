package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.RecommendContract;
import com.xsvideoLive.www.mvp.model.RecommendModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class RecommendPresenter extends BasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private RecommendModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public RecommendPresenter() {
        model = new RecommendModel();
    }

    @Override
    public void getFriendsCircle(RefreshLayout refreshLayout) {
        model.getFriendsCircle(mPage + "", size + "","")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<FriendsCircleResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<FriendsCircleResult>> bean, BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> response) {
                        List<FriendsCircleResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refresh(refreshLayout, records);
                            } else {
                                getView().loadMore(refreshLayout, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout, msg);
                    }
                });
    }

    @Override
    public void getAttention(RefreshLayout refreshLayout) {
        model.getAttention(mPage + "", size + "")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<FriendsCircleResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<FriendsCircleResult>> bean, BaseResponse<HomeRoomResult<List<FriendsCircleResult>>> response) {
                        List<FriendsCircleResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refresh(refreshLayout, records);
                            } else {
                                getView().loadMore(refreshLayout, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout, msg);
                    }
                });
    }

    @Override
    public void fabulous(String topicId) {
        model.fabulous(topicId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().fabulousSuccess(bean);
                        } else {
                            getView().onError(null, response.getMessage());
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }

    @Override
    public void deleteTopic(String topicId, FriendsCircleResult result) {
        model.deleteTopic(topicId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().onDeleteSuccess(bean, result);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }

}
