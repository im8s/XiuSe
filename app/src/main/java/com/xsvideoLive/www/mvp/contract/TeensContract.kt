package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface TeensContract {

    interface View : BaseView {

        /**
         * 获取青少年保护状态
         */
        fun teenSuccess(status:String)

        /**
         * 接口请求失败
         */
        fun onError(msg:String)
    }

    interface Presenter {

        /**
         * 查询青少年保护开启状态
         */
        fun getTeenStatus(userId: String)

    }

    interface Model {

        /**
         * 获取青少年保护是否开启
         */
        fun getTeenStatus(userId: String): HttpObservable<BaseResponse<String>>

    }

}