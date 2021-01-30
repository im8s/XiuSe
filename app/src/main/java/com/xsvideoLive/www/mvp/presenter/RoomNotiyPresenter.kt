package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomNotiyContract
import com.xsvideoLive.www.mvp.model.RoomNotifyModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomNotiyPresenter : BasePresenter<RoomNotiyContract.View>(), RoomNotiyContract.Presenter {

    private val model: RoomNotifyModel by lazy { RoomNotifyModel() }

    override fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?) {
        model.editRoomNotify(roomId, noticeTitle, noticeComment)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


}