package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GiftAllResult

interface ChatContract {

    interface View : BaseView {

        fun onError(msg: String?)

        fun onGiftSuccess(roomGift: GiftAllResult?)

        fun onNobleSuccess(noble: Int?)
    }

    interface Presenter {

        fun getGift()

        fun getSelfNoble()

    }

    interface Model {
        /**
         * 获取房间礼物
         */
        fun getGift(): HttpObservable<BaseResponse<GiftAllResult>>

        fun getNoble(): HttpObservable<BaseResponse<Int>>

    }
}