package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.FamilyMemberResult
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.StartRecommendResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface FamilyDiamondContract {
    interface View:BaseView{
        /**
         * 刷新成功
         *
         * @param refreshLayout
         * @param records
         */
        fun refresh(refreshLayout: RefreshLayout?, result: MutableList<FamilyMemberResult>?)

        /**
         * 加载成功
         *
         * @param refreshLayout
         * @param records
         */
        fun loadMore(refreshLayout: RefreshLayout?, result: MutableList<FamilyMemberResult>?)

        fun onFamilyInfoSuccess(result:StartRecommendResult?)

        fun onError(msg:String?)
    }

    interface Presenter {

        fun getFamilyUser(refreshLayout: RefreshLayout?,userId:String?)

        fun getFamilyInfo(userId:String?)

    }

    interface Model {

        /**
         * 族长获取族员列表
         *
         * @param current
         * @param size
         * @param userId
         * @return
         */
        fun getFamilyUser(current: String?,size: String?,userId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<FamilyMemberResult>>>>

        /**
         * 族长获取家族信息
         * @param userId
         * @return
         */
        fun getFamilyDiamondInfo(userId: String?): HttpObservable<BaseResponse<StartRecommendResult>>
    }
}