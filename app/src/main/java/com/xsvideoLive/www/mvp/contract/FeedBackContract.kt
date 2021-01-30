package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface FeedBackContract {
    interface View : BaseView {
        fun feedBackSuccess()
        fun feedBackError(msg: String)
    }

    interface Presenter {
        fun  feedBack(message:String,type:String,num:String,userId:String)

    }

    interface Model {
        fun  feedBack(message:String,type:String,num:String,userId:String): HttpObservable<BaseResponse<Int>>?
    }
}