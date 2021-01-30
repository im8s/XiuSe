package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.RegisterAndLoginContract;
import com.xsvideoLive.www.mvp.model.RegisterAndLoginModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class RegisteAndLoginPresenter extends BasePresenter<RegisterAndLoginContract.View> implements RegisterAndLoginContract.Presenter {

    private RegisterAndLoginModel model;

    public RegisteAndLoginPresenter() {
        model = new RegisterAndLoginModel();
    }

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
    public void register(String phone, String code) {
        getView().showLoading();
        model.checkCode(phone, code)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();

                        //	1验证码正确 5验证码错误 6手机号为空 8验证码为空 9验证码超时

                        switch (bean) {
                            case Constant.CODE_SUCCESS:
                                getView().registerSuccess(bean);
                                break;
                            case Constant.CODE_ERROR:

                                getView().registerError(((Context) getView()).getResources().getString(R.string.code_error));
                                break;
                            case Constant.CODE_TIME_OUT:
                                getView().registerError(((Context) getView()).getResources().getString(R.string.code_out_time));
                                break;
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().registerError(msg);
                    }
                });
    }
}
