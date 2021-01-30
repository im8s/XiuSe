package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.FamilyUserContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.MyFamilyResult
import com.xsvideoLive.www.net.bean.UserIncome

class FamilyUserModel: FamilyUserContract.Model {
    override fun getUserIncome(current: String?, size: String?, userId: String?, clanId: String?,startTime: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<UserIncome>>>> {
        return HttpClient.getApi().getUserIncome(current, size, userId, clanId,startTime)
    }

    override fun getFamilyInfo(userId: String?): HttpObservable<BaseResponse<MyFamilyResult>> {
        return HttpClient.getApi().getFamilyInfo(userId)
    }

}