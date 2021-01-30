package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

interface RoomNotiyContract {

    interface View : BaseView {
        /**
         * 请求成功
         */
        fun onSuccess(status:Int?)

        /**
         * 请求接口失败
         */
        fun onError(msg:String?)

    }

    interface Presenter {

        /**
         * 请求设置房间公告接口
         */
        fun editRoomNotify(roomId: String?,noticeTitle: String?,noticeComment: String?)

    }

    interface Model {
        fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?): HttpObservable<BaseResponse<Int>>
    }

}