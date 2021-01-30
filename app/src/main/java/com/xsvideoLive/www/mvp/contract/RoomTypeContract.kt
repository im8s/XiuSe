package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeSortResult

interface RoomTypeContract {

    interface View : BaseView {

        fun onSortSuccess(sortList: MutableList<HomeSortResult>?)

        fun onTypeSuccess(status: Int, type: HomeSortResult)

        fun onError(msg: String?)

    }

    interface Presenter {


        /**
         * 获取首页房间分类
         * @param indicator
         */
        fun getSort()

        fun setRoomType(roomId: String, roomTypeId: String, type: HomeSortResult)
    }

    interface Model {

        /**
         * 首页分类
         * @return
         */
        fun getHomeSort(): HttpObservable<BaseResponse<MutableList<HomeSortResult>>>

        /**
         * 修改房间属性
         */
        fun modifyRoomType(roomId: String?, typeId: String?): HttpObservable<BaseResponse<Int>>

    }
}