package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomSettingContract
import com.xsvideoLive.www.mvp.model.RoomSettingModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomSettingPresenter : BasePresenter<RoomSettingContract.View>(), RoomSettingContract.Presenter {

    private val model by lazy { RoomSettingModel() }

    override fun modifyRoomName(roomId: String?, roomName: String?) {
        model?.modifyName(roomId, roomName)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.roomNameSuccess(bean, roomName)

                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun modifyRoomPsw(roomId: String, type: String, password: String?) {
        model.modifyPsw(roomId, type, password)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.roomPswSuccess(bean, type == "1", password)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


    override fun setGpSwitch(roomId: String, type: Boolean) {
        model.setPublic(roomId, if (type) 1 else 0)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.isOpenGpSuccess(bean!!, type)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun pickSwitch(roomId: String?, type: Int?, isPick: Boolean?) {
        model.pickSwitch(roomId, type.toString())
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onPickSwitch(bean, isPick)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


}