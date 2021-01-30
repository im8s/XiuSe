package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface OnLineContract {

    interface View : BaseView {

        fun onError(msg:String?)

        /**
         * 刷新在线用户
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>)

        /**
         * 加载更多在线用户
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>)

    }

    interface Presenter {

        /**
         * 加载在线用户
         * @param refresh
         */
        fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?)

    }

    interface Model {
        /**
         * 加载在线用户
         */
        fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>>
    }

}