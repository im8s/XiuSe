package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.LotteryPirzeResult
import com.xsvideoLive.www.net.bean.PrizeResult

interface LotteryContract {

    interface View : BaseView {


        fun onPrizeSuccess(mPrize: PrizeResult?)

        fun onGoldSuccess(balance: Double)

        fun onLotterySuccess(result: LotteryPirzeResult?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取奖池信息
         */
        fun getPrize(kind: Int)

        /**
         * 获取我的金币余额
         */
        fun getMyGold()

        /**
         * 抽奖
         */
        fun lottery(type: Int, kind: Int)
    }

    interface Model {
        /**
         * 查看奖池
         */
        fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>>

        /**
         * 获取我的金币余额
         */
        fun getMyGoldNum(): HttpObservable<BaseResponse<Double>>

        /**
         * 抽奖
         */
        fun lottery(type: Int, kind: Int): HttpObservable<BaseResponse<LotteryPirzeResult>>
    }


}