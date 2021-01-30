package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.TopicNotifcContract
import com.xsvideoLive.www.mvp.model.TopicNotifcModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.TopicMsg
import com.scwang.smartrefresh.layout.api.RefreshLayout

class TopicNotifcPresenter:BasePresenter<TopicNotifcContract.View>(),TopicNotifcContract.Presenter {

    var initPage = 1
    var mPage = initPage
    private var size = 10

    private val model by lazy { TopicNotifcModel() }

    override fun getTopicList(refresh: RefreshLayout?,userId: String?) {
        model?.getTopicMsg(mPage.toString() + "", size.toString() + "", userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<TopicMsg>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<TopicMsg>>?, response: BaseResponse<HomeRoomResult<MutableList<TopicMsg>>>?) {
                        val records = bean?.records
                        if (records != null) {
                            if (mPage == initPage) {

                                view.refreshSuccess(refresh, records)

                            } else {

                                view.loadMoreSuccess(refresh, records)

                            }
                            if (records.size > 0) mPage++
                        }
                    }

                    override fun error(msg: String?) {

                        if (refresh != null) {
                            refresh.finishRefresh()
                            refresh.finishLoadMore()
                        }

                        view.onError(msg)
                    }

                })
    }
}