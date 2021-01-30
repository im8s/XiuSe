package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import retrofit2.http.Field

interface ChatBlackContract {

    interface View : BaseView {

        fun onBlackSuccess(status: String?)

        fun onError(msg:String?)

        fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?)
    }

    interface Presenter {

        fun isBlack(userId: String?)

        /**
         * 添加或者移除黑名单
         */
        fun addBlackList(status: String?, roomId: String?, userId: String?)

        /**
         * 移除黑名单
         */
        fun removeBlackList(status: String?, roomId: String?, userId: String?)
    }

    interface Model {

        fun isBlack(@Field("userId") userId: String?): HttpObservable<BaseResponse<String>>
        /**
         * 添加和移除黑名单
         */
        fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除黑名单
         */
        fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

    }
}