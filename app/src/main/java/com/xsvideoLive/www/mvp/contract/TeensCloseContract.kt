package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface TeensCloseContract {


    interface View : BaseView {

        fun onSuccess(status: String)

        fun onError(msg: String)

    }

    interface Presenter {

        /**
         * 关闭青少年模式
         */
        fun closeTeens(uesrId: String, password: String)

    }

    interface Model {
        /**
         * 开启青少年模式
         */
        fun startTeens(userId: String, password: String, status: String): HttpObservable<BaseResponse<String>>

    }

}