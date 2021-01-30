package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HalfRoomRankResult

interface HalfRankConstract {

    interface View : BaseView {

        /**
         * 成功获取排行榜
         */
        fun rankSuccess(rank: HalfRoomRankResult?)

        fun onError(msg: String?)

    }

    interface Presenter {

        /**
         * 获取房间排行榜
         */
        fun getRoomRank(roomId: String?)

    }

    interface Model {

        fun getHalfRoomRank(roomId: String?): HttpObservable<BaseResponse<HalfRoomRankResult>>

    }
}