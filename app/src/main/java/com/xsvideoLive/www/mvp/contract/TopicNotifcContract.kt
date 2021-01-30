package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.TopicMsg
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface TopicNotifcContract {

    interface View : BaseView {

        /**
         * 刷新在线用户
         *
         */
        fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<TopicMsg>)

        /**
         * 加载更多在线用户
         *
         */
        fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<TopicMsg>)

        fun onError(msg: String?)
    }

    interface Presenter {

        fun getTopicList(refresh: RefreshLayout?, userId: String?)
    }

    interface Model {
        /**
         * 话题列表
         * @param current
         * @param size
         * @param userId
         * @return
         */
        fun getTopicMsg(current: String?, size: String?, userId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<TopicMsg>>>>
    }
}