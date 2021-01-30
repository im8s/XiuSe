package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.BlackListConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.RoomBlackListEntity

class BlackListModel : BlackListConstract.Model {
    override fun getRoomBlackList(current: String?, size: String?, blacklistType: String?, current_id: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBlackListEntity>>>> {
        return HttpClient.getApi().getRoomBlackList(current, size, blacklistType, current_id)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }
}