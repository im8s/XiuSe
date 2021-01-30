package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomTypeContract
import com.xsvideoLive.www.mvp.model.RoomTypeModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeSortResult

class RoomTypePresenter : BasePresenter<RoomTypeContract.View>(), RoomTypeContract.Presenter {

    private val model by lazy { RoomTypeModel() }

    override fun getSort() {
        model.getHomeSort()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<MutableList<HomeSortResult>>() {
                    override fun success(bean: MutableList<HomeSortResult>?, response: BaseResponse<MutableList<HomeSortResult>>?) {
                        view.onSortSuccess(bean)

                    }

                    override fun error(msg: String?) {

                        view.onError(msg)
                    }

                })
    }

    override fun setRoomType(roomId: String, roomTypeId: String, type: HomeSortResult) {
        model.modifyRoomType(roomId, roomTypeId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onTypeSuccess(bean!!, type)
                    }

                    override fun error(msg: String?) {

                    }

                })
    }
}