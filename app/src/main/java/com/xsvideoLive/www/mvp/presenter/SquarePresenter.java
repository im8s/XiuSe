package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.SquareConstract;
import com.xsvideoLive.www.mvp.model.SquareModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.TopicMsg;

import java.util.List;

public class SquarePresenter extends BasePresenter<SquareConstract.View> implements SquareConstract.Presenter {

    private SquareModel model;

    public SquarePresenter() {
        model = new SquareModel();
    }

    @Override
    public void getTopicMsg(String userId) {
        model.getTopicMsg("1","1",userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<HomeRoomResult<List<TopicMsg>>>(){

                    @Override
                    protected void success(HomeRoomResult<List<TopicMsg>> bean, BaseResponse<HomeRoomResult<List<TopicMsg>>> response) {
                        getView().onTopicMsgSuccess(bean.getRecords());
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }
}
