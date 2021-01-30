package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.GoldExchangeContract;
import com.xsvideoLive.www.mvp.model.GoldExchangeModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class GoldExchangePresenter extends BasePresenter<GoldExchangeContract.View> implements GoldExchangeContract.Presenter {

    private GoldExchangeModel model;

    public GoldExchangePresenter() {
        model = new GoldExchangeModel();
    }

    @Override
    public void getMyGoldNum() {
        model.getMyGoldNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        if (bean != null) {
                            getView().myGoldSuccess(bean);
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

    @Override
    public void getMyDiamondNum() {
        model.getMyDiamondNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        if (bean!=null) {
                        getView().onDiamondSuccess(bean);
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

    @Override
    public void goldChange(String jbNum) {
        model.goldChange(jbNum)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onChangeSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
