package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.UserRankResult

interface RoomListConstart {

    interface View : BaseView {

        fun userRankSuccess(userRankList: MutableList<UserRankResult>?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取房内榜 1贡献榜 日 2贡献榜 周 3魅力榜 日 4魅力榜 周
         */
        fun getUserRank(roomId: String?, type: Int?)
    }

    interface Model {

        fun getUserRank(roomId: String?, type: Int?): HttpObservable<BaseResponse<MutableList<UserRankResult>>>
    }

}