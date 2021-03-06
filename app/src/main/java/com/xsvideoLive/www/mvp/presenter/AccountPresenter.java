package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.AccountContract;
import com.xsvideoLive.www.mvp.model.AccountModel;
import com.xsvideoLive.www.mvp.ui.activity.login.AccountLoginAct;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public class AccountPresenter extends BasePresenter<AccountContract.View> implements AccountContract.Presenter {

    private AccountModel model;

    public AccountPresenter() {
        model = new AccountModel();
    }

    @Override
    public void login(String phoneNumber, String psd) {
        getView().showLoading();
        model.loginPsd(phoneNumber, psd)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {

                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();
                        LogonResult data = response.getData();
                        if (data != null) {

//                            if (LoginStatus.getValue(data.getStatus()).equals(LoginStatus.SUCCESS)){
//                                String strUser = new Gson().toJson(data.getUser());
//                                SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
//                                SPUtils.getInstance().put(SpConstant.APP_TOKEN, data.getToken());
//                            }

                            getView().loginSuccess(data);
                        } else {
                            getView().loginError(((Context) getView()).getResources().getString(R.string.login_error));
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().loginError(msg);

                    }
                });
    }

    @Override
    public void getUserSig(LogonResult data) {
        getView().showLoading();
        model.getUserSig(data.getUser().getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().sigSuccess(data, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().loginError(msg);
                    }
                });
    }

    @Override
    public void loginIm(LogonResult data, String sig, IUIKitCallBack callBack) {

        SPUtils.getInstance().put(SpConstant.APP_SIG, sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((AccountLoginAct) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack != null) {
                    callBack.onError("????????????", code, msg);
                }
            } else {
                callBack.onSuccess(null);
            }
        });
    }
}
