package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomGiftContract
import com.xsvideoLive.www.mvp.model.RoomGiftModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomGiftPresenter : BasePresenter<RoomGiftContract.View>(), RoomGiftContract.Presetner {

    val model: RoomGiftModel by lazy { RoomGiftModel() }

    override fun getMyGoldNum() {
        model.getMyGoldNum()
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double?>() {
                    override fun success(bean: Double?, response: BaseResponse<Double?>?) {
                        if (bean != null) {
                            view.onGoldSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })

    }

    override fun getMyChili() {
        model.getMyChili()
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double>() {
                    override fun success(bean: Double?, response: BaseResponse<Double>?) {
                        if (bean != null) {
                            view.onChiliSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}