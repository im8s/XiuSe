package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomManagerContract
import com.xsvideoLive.www.mvp.model.RoomManagerModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomManagerEntity

class RoomManagerPresenter : BasePresenter<RoomManagerContract.View>(), RoomManagerContract.Presenter {

    private val model by lazy { RoomManagerModel() }

    override fun getRoomManager(roomId: String?) {
        model.getManager(roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<MutableList<RoomManagerEntity>>() {
                    override fun success(bean: MutableList<RoomManagerEntity>?, response: BaseResponse<MutableList<RoomManagerEntity>>?) {
                        view.onRoomManagerSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun removeRoomManager(roomId: String?, user: RoomManagerEntity?) {

        model.removeManager(roomId, user?.userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onRemoveSuccess(bean, user)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}