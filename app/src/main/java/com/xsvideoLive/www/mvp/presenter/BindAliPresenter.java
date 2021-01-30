package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.BindAliContract;
import com.xsvideoLive.www.mvp.model.BindAliModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class BindAliPresenter extends BasePresenter<BindAliContract.View> implements BindAliContract.Presenter {

    private BindAliModel model = new BindAliModel();

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
    public void bindWeChat(String account, String realName, String code) {
        model.bindWeChat("2",account,realName,code)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onWeChatSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onWeChatError(msg);
                    }
                });
    }


}
