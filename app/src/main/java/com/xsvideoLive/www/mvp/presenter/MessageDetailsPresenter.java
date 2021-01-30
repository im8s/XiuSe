package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MessageDetailsContract;
import com.xsvideoLive.www.mvp.model.MessageDetailsModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CommentResult;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class MessageDetailsPresenter extends BasePresenter<MessageDetailsContract.View> implements MessageDetailsContract.Presenter {

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    private MessageDetailsModel model;

    public MessageDetailsPresenter() {
        model = new MessageDetailsModel();
    }

    @Override
    public void getFriend(String topicId) {
        model.getFriendOne(topicId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<FriendsCircleResult>(){

                    @Override
                    protected void success(FriendsCircleResult bean, BaseResponse<FriendsCircleResult> response) {
                        getView().onFriendSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null,msg);
                    }
                });
    }

    @Override
    public void getComment(RefreshLayout refreshLayout, String topicId) {
        model.getComment(mPage + "", size + "", topicId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<CommentResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<CommentResult>> bean, BaseResponse<HomeRoomResult<List<CommentResult>>> response) {
                        List<CommentResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refresh(refreshLayout, records, bean.getTotal() + "");
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
    public void comment(String topicId, String commentContent, String commentType, String fatherId, int position) {
        model.comment(topicId, commentContent, commentType, fatherId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            if (commentType.equals("1")) {
                                getView().commentSuccess("评论成功");
                            } else if (commentType.equals("2")) {
                                getView().secondCommentSuccess(fatherId, position);
                            }
                        } else {
                            getView().onError(null, "");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }

    @Override
    public void getSecondComment(String commentId, int position) {
        model.getSecondComment(commentId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<CommentResult>() {
                    @Override
                    protected void success(CommentResult bean, BaseResponse<CommentResult> response) {
                        if (bean != null) {
                            getView().secondSuccess(bean, position);
                        } else {
                            getView().onError(null, "");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(null, msg);
                    }
                });
    }
}
