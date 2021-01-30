package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomListConstart
import com.xsvideoLive.www.mvp.model.RoomListModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.UserRankResult

class RoomListPresenter : BasePresenter<RoomListConstart.View>(), RoomListConstart.Presenter {

    private val model: RoomListModel by lazy { RoomListModel() }

    override fun getUserRank(roomId: String?, type: Int?) {

        model.getUserRank(roomId, type)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<MutableList<UserRankResult>>() {
                    override fun success(bean: MutableList<UserRankResult>?, response: BaseResponse<MutableList<UserRankResult>>?) {
                        view.userRankSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}