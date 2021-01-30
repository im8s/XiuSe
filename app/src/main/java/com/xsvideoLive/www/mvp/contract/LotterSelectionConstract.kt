package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.PrizeResult

interface LotterSelectionConstract {

    interface View : BaseView {

        fun onPrizeSuccess(mPrize: PrizeResult?)

        fun onError(msg: String?)

    }

    interface Presenter {
        /**
         * 获取奖池信息
         */
        fun getPrize(kind: Int)
    }

    interface Model {
        /**
         * 查看奖池
         */
        fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>>
    }
}