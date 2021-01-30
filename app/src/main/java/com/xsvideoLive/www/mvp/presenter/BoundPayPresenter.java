package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.BoundPayContract;
import com.xsvideoLive.www.mvp.model.BoundPayModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BindEntity;

public class BoundPayPresenter extends BasePresenter<BoundPayContract.View> implements BoundPayContract.Presenter {

    private BoundPayModel model = new BoundPayModel();

    @Override
    public void getBoundPay() {
        model.getBoundPay()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<BindEntity>(){

                    @Override
                    protected void success(BindEntity bean, BaseResponse<BindEntity> response) {
                        getView().onBoundSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onBoundError(msg);
                    }
                });
    }
}
