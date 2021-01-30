package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.MyFamilyResult
import com.xsvideoLive.www.net.bean.UserIncome
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface MyFamilyIncomeContract {

    interface View:BaseView{

        /**
         * 设置时间
         *
         * @param date
         */
        fun setTime(date: String?)

        /**
         * 刷新成功
         * @param refresh
         * @param incomeList
         */
        fun refreshSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?)

        /**
         * 加载成功
         * @param refresh
         * @param incomeList
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?)

        fun onFamilySuccess(fammilyResult:MyFamilyResult?)

        fun onError(msg:String?)

    }

    interface Presenter {

        /**
         * 时间选择
         */
        fun selectTime(date: String?)


        fun getFamilyIncome(refresh: RefreshLayout?,userId: String?,clanId:String?,startTime:String?)

        fun getMyFamilyInfo(userId:String?)

    }

    interface Model {

        fun getFamilyIncome(current: String?, size: String?,userId: String?,clanId:String?,startTime:String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<UserIncome>>>>

        fun getFamilyInfo(userId: String?): HttpObservable<BaseResponse<MyFamilyResult>>

    }

}