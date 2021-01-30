package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MyGoldContract;
import com.xsvideoLive.www.mvp.model.MyGoldModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MyGoldPresenter extends BasePresenter<MyGoldContract.View> implements MyGoldContract.Presenter{

    private MyGoldModel model;

    public MyGoldPresenter() {
        model = new MyGoldModel();
    }

    @Override
    public void getMyGoldNum() {
        model.getMyGoldNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        if (bean!=null) {
                            getView().onSuccess(bean);
                        } else {
                            getView().onError("请求错误");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
