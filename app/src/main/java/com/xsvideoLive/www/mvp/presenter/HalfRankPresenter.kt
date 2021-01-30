package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.HalfRankConstract
import com.xsvideoLive.www.mvp.model.HalfRankModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HalfRoomRankResult

class HalfRankPresenter : BasePresenter<HalfRankConstract.View>(), HalfRankConstract.Presenter {

    private val model: HalfRankModel by lazy { HalfRankModel() }

    override fun getRoomRank(roomId: String?) {
        model.getHalfRoomRank(roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HalfRoomRankResult>() {
                    override fun success(bean: HalfRoomRankResult?, response: BaseResponse<HalfRoomRankResult>?) {
                        view.rankSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}