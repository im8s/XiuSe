package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public interface AccountContract {

    interface View extends BaseView{

        /**
         * 登录成功
         */
        void loginSuccess(LogonResult bean);

        /**
         * 获取Sig 成功
         * @param data
         * @param sig
         */
        void sigSuccess(LogonResult data, String sig);

        /**
         * 登录错误
         */
        void loginError(String msg);

    }

    interface Presenter {

        void login(String phoneNumber,String psd);

        /**
         * 通过本地userId 获取userSig
         *
         * @param data
         */
        void getUserSig(LogonResult data);

        /**
         * 登录腾讯IM
         * @param data
         * @param sig
         */
        void loginIm(LogonResult data, String sig, IUIKitCallBack callBack);
    }

    interface Model{

        HttpObservable<BaseResponse<LogonResult>> loginPsd(String phone,String password);

        /**
         * 获取userSig
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getUserSig(String userId);
    }

}
