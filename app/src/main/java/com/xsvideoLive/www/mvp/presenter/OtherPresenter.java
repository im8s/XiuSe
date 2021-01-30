package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.OtherContract;
import com.xsvideoLive.www.mvp.model.OtherLoginModel;
import com.xsvideoLive.www.mvp.ui.activity.login.OtherLoginAct;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public class OtherPresenter extends BasePresenter<OtherContract.View> implements OtherContract.Presenter {

    private OtherLoginModel model;

    public OtherPresenter() {
        model = new OtherLoginModel();
    }

    @Override
    public void getLoginCode(String phoneNum) {

        model.getStatusCode(phoneNum)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().loginCodeSuccess();
                        } else {
                            getView().loginCodeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().loginCodeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                    }
                });
    }

    @Override
    public void login(String phoneNum, String code) {
        getView().showLoading();
        model.loginCode(phoneNum, code)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {

                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();
                        LogonResult data = response.getData();

                        if (data != null) {
//                            if (LoginStatus.getValue(data.getStatus()).equals(LoginStatus.SUCCESS)) {
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
    public void psdLogin() {
        getView().psdLogin();
    }

    @Override
    public void resgister() {
        getView().resgister();

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

//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserId(data.getUser().getId());
//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserSig(sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((OtherLoginAct) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack!=null) {
                    callBack.onError("登录失败",code,msg);
                }
            } else {
//                if (TUIKitConfigs.getConfigs().getGeneralConfig().isSupportAVCall()) {
//                    UserModel self = new UserModel();
//                    self.userId = data.getUser().getId();
//                    self.userSig = sig;
//                    ProfileManager.getInstance().setUserModel(self);
//                    AVCallManager.getInstance().init(((OtherLoginAct) getView()).getApplicationContext());
//                }
                callBack.onSuccess(null);
            }
        });

//        TUIKit.login(data.getUser().getId(), sig, callBack);
    }
}
