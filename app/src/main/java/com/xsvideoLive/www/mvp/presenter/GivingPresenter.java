package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.GivingContract;
import com.xsvideoLive.www.mvp.model.GivingModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GiftRecordResult;

public class GivingPresenter extends BasePresenter<GivingContract.View> implements GivingContract.Presenter {

    private GivingModel model;

    public GivingPresenter() {
        model = new GivingModel();
    }

    @Override
    public void giftRecord(String userId) {
        model.giftRecord(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<GiftRecordResult>() {
                    @Override
                    protected void success(GiftRecordResult bean, BaseResponse<GiftRecordResult> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
