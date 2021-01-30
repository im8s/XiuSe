package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;


public interface RegisterAndLoginContract {
    interface View extends BaseView{

        /**
         * 登录成功
         */
        void registerSuccess(String bean);

        /**
         * 登录错误
         */
        void registerError(String msg);

        /**
         * 获取验证码成功
         */
        void codeSuccess();

        /**
         * 获取验证码失败
         */
        void codeError(String msg);

    }

    interface Presenter {
        /**
         * 获取验证码
         */
        void getCode(String phone);

        /**
         * 注册
         */
        void register(String phone,String code);
    }

    interface Model {
        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        HttpObservable<BaseResponse<String>> getStatusCode(String phone);

        /**
         * 校验手机号与验证码
         * @param phone
         * @param code
         * @return
         */
        HttpObservable<BaseResponse<String>> checkCode(String phone,String code);
    }
}
