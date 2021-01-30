package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.ChatContract
import com.xsvideoLive.www.mvp.model.ChatModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GiftAllResult

class ChatPresenter : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

    val mode by lazy { ChatModel() }

    override fun getGift() {
        mode.getGift()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<GiftAllResult>() {
                    override fun success(bean: GiftAllResult?, response: BaseResponse<GiftAllResult>?) {
                        view.onGiftSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getSelfNoble() {
        mode.getNoble()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onNobleSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}