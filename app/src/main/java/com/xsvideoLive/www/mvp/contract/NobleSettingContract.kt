package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface NobleSettingContract {

    interface View:BaseView {

        fun onStatusSuccess(status:String?,result:String?)

        fun onError(msg:String?)


    }

    interface Presenter {

        fun setRoomStatus(userId: String?,status: String?)

    }

    interface Model {

        fun enterRoomStatus(userId: String?,status: String?): HttpObservable<BaseResponse<String>>

    }
}