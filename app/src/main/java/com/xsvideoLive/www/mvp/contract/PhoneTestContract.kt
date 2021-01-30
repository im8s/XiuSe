package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface PhoneTestContract {
    interface View : BaseView {

        /**
         * 获取验证码成功
         */
        fun codeSuccess(code: String?)

        /**
         * 获取验证码失败
         */
        fun codeError(msg: String?)

        fun onCheckSuccess(status:String?)

        fun onError(msg:String?)

    }

    interface Presenter {

        fun getStatusCode(phone: String?)

        fun checkCode(phone: String?, code: String?)


    }

    interface Model {
        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>>

        /**
         * 检验手机号和验证码
         */
        fun checkCode(phone: String?, code: String?): HttpObservable<BaseResponse<String>>
    }
}