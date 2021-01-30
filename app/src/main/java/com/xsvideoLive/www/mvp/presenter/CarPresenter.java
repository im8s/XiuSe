package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.CarContract;
import com.xsvideoLive.www.mvp.model.CarModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;

public class CarPresenter extends BasePresenter<CarContract.View> implements CarContract.Presenter {

    private CarModel model;

    public CarPresenter() {
        model = new CarModel();
    }

    @Override
    public void carRecord(String userId) {
        model.carRecord(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<DeckResult>() {
                    @Override
                    protected void success(DeckResult bean, BaseResponse<DeckResult> response) {
                        getView().carSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
