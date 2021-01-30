package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.FamilyDiamondContract
import com.xsvideoLive.www.mvp.model.FamilyDiamondModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.FamilyMemberResult
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.StartRecommendResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

class FamilyDiamondPresenter:BasePresenter<FamilyDiamondContract.View>(),FamilyDiamondContract.Presenter {

    var initPage = 1
    var mPage = initPage
    var size = 10

    private val model by lazy { FamilyDiamondModel() }

    override fun getFamilyUser(refreshLayout: RefreshLayout?, userId: String?) {
        model.getFamilyUser(mPage.toString() + "", size.toString() + "", userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<HomeRoomResult<MutableList<FamilyMemberResult>>>(){
                    override fun success(bean: HomeRoomResult<MutableList<FamilyMemberResult>>?, response: BaseResponse<HomeRoomResult<MutableList<FamilyMemberResult>>>?) {
                        val results: MutableList<FamilyMemberResult>? = bean!!.records

                        if (results != null) {
                            if (mPage == initPage) {
                                view.refresh(refreshLayout, results)
                            } else {
                                view.loadMore(refreshLayout, results)
                            }
                            if (results.size > 0) mPage++
                        }

                    }

                    override fun error(msg: String?) {

                    }

                })
    }

    override fun getFamilyInfo(userId: String?) {
        model.getFamilyDiamondInfo(userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<StartRecommendResult>(){
                    override fun success(bean: StartRecommendResult?, response: BaseResponse<StartRecommendResult>?) {
                        view.onFamilyInfoSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}