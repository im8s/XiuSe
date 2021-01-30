package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomBgConstract
import com.xsvideoLive.www.mvp.model.RoomBgModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomBgEntity

class RoomBgPresenter : BasePresenter<RoomBgConstract.View>(), RoomBgConstract.Presenter {

    private val model by lazy { RoomBgModel() }

    override fun setRoomBg(roomId: String?, userId: String?, roomBg: RoomBgEntity?) {
        view.showLoading()
        model.setRoomBg(roomId, userId, roomBg?.id)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.hideLoading()
                        view.onSuccess(bean,roomBg)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun getMyNoble() {

        model.getNoble()
                .execOnThread(view.getActLifeRecycle(),object:HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {

                        view.onNobleSuccess(bean)
                    }

                    override fun error(msg: String?) {

                        view.onError(msg)
                    }

                })
    }
}