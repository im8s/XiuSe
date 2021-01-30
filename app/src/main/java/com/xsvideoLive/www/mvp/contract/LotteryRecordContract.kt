package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.AwardsEntity
import com.xsvideoLive.www.net.bean.BaseResponse

interface LotteryRecordContract {

    interface View : BaseView {

        fun onRecordSuccess(record: MutableList<AwardsEntity>?)

        fun onError(msg: String?)

    }

    interface Presenter {

        fun getLotterRecord(kind: Int)
    }

    interface Model {

        fun getLotterRecord(kind: Int): HttpObservable<BaseResponse<MutableList<AwardsEntity>>>
    }

}