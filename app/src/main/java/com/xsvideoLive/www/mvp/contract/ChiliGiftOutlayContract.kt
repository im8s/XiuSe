package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GoldGiftResult
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface ChiliGiftOutlayContract {


    interface View : BaseView {
        fun onError(msg: String?)

        /**
         * 设置时间
         *
         * @param date
         */
        fun setTime(date: String?)

        /**
         * 刷新成功
         *
         * @param refresh
         * @param incomeList
         */
        fun refreshSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?)

        /**
         * 加载成功
         *
         * @param refresh
         * @param incomeList
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?)
    }

    interface Presenter {
        /**
         * 时间选择
         */
        fun selectTime(date: String?)

        /**
         * 获取收入列表
         *
         * @param date
         */
        fun getChiliGiftOutlayList(refresh: RefreshLayout?, date: String?)
    }

    interface Model {

        fun getChiliGiftOutlayList(current: String?, size: String?, date: String?): HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?>?
    }

}