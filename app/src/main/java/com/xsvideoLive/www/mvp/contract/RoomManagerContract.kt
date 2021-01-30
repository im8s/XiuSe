package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomManagerEntity
import retrofit2.http.Field

interface RoomManagerContract {

    interface View : BaseView {

        fun onRoomManagerSuccess(roomManager: MutableList<RoomManagerEntity>?)

        fun onRemoveSuccess(status: Int?, user: RoomManagerEntity?)

        fun onError(msg:String?)

    }

    interface Presenter {

        fun getRoomManager(roomId: String?)

        fun removeRoomManager(roomId: String?, user: RoomManagerEntity?)
    }

    interface Model {

        /**
         * 房内榜
         *
         * @param roomId
         * @return
         */
        fun getManager(@Field("roomId") roomId: String?): HttpObservable<BaseResponse<MutableList<RoomManagerEntity>>>

        /**
         * 移除管理员
         * @param roomId
         * @param userId
         * @return
         */
        fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

    }

}