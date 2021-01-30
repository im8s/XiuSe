package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomBgEntity

interface RoomBgConstract {

    interface View : BaseView {

        fun onSuccess(status:Int?,roomBg: RoomBgEntity?)

        fun onNobleSuccess(noble:Int?)

        fun onError(msg:String?)

    }

    interface Presenter {

        fun setRoomBg(roomId: String?, userId: String?, roomBg: RoomBgEntity?)

        fun getMyNoble()

    }

    interface Model {

        fun setRoomBg(roomId: String?, userId: String?,imgId: String?): HttpObservable<BaseResponse<Int>>

        fun getNoble(): HttpObservable<BaseResponse<Int>>

    }
}