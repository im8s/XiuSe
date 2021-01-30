package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.SystemNotic
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface SystemNoticContract {

    interface View:BaseView {

        /**
         * 刷新在线用户
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<SystemNotic>?)

        /**
         * 加载更多在线用户
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<SystemNotic>?)

        fun onError(msg: String?)
    }

    interface Presenter {

        fun getSystemNoic(refresh: RefreshLayout?)

    }

    interface Model {
        fun getSystemNotic(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<SystemNotic>>>>
    }
}