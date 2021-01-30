package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.BindBlackContract;
import com.xsvideoLive.www.mvp.model.BindBlackModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class BindBlackPresenter extends BasePresenter<BindBlackContract.View> implements BindBlackContract.Presenter{

    private BindBlackModel model = new BindBlackModel();

    @Override
    public void getCode(String phone) {
        model.getStatusCode(phone)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().codeSuccess();
                        } else {
                            getView().codeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().codeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                    }
                });
    }

    @Override
    public void bindBlackNum(String account, String bankType, String bankName, String realName, String code) {
        getView().showLoading();
        model.bindBlackNum("1",account,bankType,bankName,realName,code)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().onBindSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onBindError(msg);
                    }
                });
    }

}
