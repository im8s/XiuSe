package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.FamilyDiamondContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.FamilyMemberResult
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.StartRecommendResult

class FamilyDiamondModel: FamilyDiamondContract.Model {
    override fun getFamilyUser(current: String?, size: String?, userId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<FamilyMemberResult>>>> {
        return HttpClient.getApi().getFamilyUser(current, size, userId)
    }

    override fun getFamilyDiamondInfo(userId: String?): HttpObservable<BaseResponse<StartRecommendResult>> {
        return HttpClient.getApi().getFamilyDiamondInfo(userId)
    }
}