package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.ChatBlackContract
import com.xsvideoLive.www.mvp.model.ChatBlackModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class ChatBlackPresenter:BasePresenter<ChatBlackContract.View>(),ChatBlackContract.Presenter {

   private val model by lazy { ChatBlackModel() }

    override fun isBlack(userId: String?) {
        model.isBlack(userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String> () {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onBlackSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun addBlackList(status: String?, roomId: String?, userId: String?) {
        model.addBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("1", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun removeBlackList(status: String?, roomId: String?, userId: String?) {
        model.removeBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("2", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}