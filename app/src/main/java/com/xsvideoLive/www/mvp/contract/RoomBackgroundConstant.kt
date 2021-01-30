package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.RoomBgEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface RoomBackgroundConstant {

    interface View : BaseView {

        /**
         * 请求黑名单列表成功
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>)

        /**
         * 请求黑名单列表加载成功
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>)


        fun onError(msg: String?)


    }

    interface Presenter {

        fun getRoomBg(refreshLayout: RefreshLayout?)

    }

    interface Model {

        fun getRoomBg(current:String?,size:String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBgEntity>>>>

    }

}