package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.OnLineContract
import com.xsvideoLive.www.mvp.model.OnLineModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

class OnLinePresenter : BasePresenter<OnLineContract.View>(), OnLineContract.Presenter {

    var initPage = 1
    var mPage = initPage
    private var size = 10

    private val model by lazy { OnLineModel() }

    override fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?) {
        model?.getOnLineUser(mPage.toString() + "", size.toString() + "", roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<OnLineUserResult>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<OnLineUserResult>>?, response: BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>?) {
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