package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomGiftResult

interface RoomMyGiftConstract {

    interface View : BaseView {

        fun onGiftSuccess(giftList: MutableList<RoomGiftResult>)

        fun onGoldSuccess(gold: Double?)

        fun onChiliSuccess(chili: Double?)

        fun onError(msg: String?)

        fun onTotalSuccess(status:Int?)

        fun onTotalError(msg:String?)

    }

    interface Presenter {

        fun getPackageGift()

        /**
         * 获取金币数量
         */
        fun getMyGoldNum()

        /**
         * 获取我的辣椒总量
         */
        fun getMyChili()

        /**
         * 获取礼物总价值
         */
        fun getTotalValue();

    }

    interface Model {

        fun getPackageGift(): HttpObservable<BaseResponse<MutableList<RoomGiftResult>>>

        /**
         * 获取金币总量
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>?

        /**
         * 获取辣椒
         * @return
         */
        fun getMyChili(): HttpObservable<BaseResponse<Double?>?>?

        /**
         * 获取背包总价值
         */
        fun getTotalValue(): HttpObservable<BaseResponse<Int?>?>?



    }
}