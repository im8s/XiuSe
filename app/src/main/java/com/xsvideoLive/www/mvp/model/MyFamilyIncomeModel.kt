package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.MyFamilyIncomeContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.MyFamilyResult
import com.xsvideoLive.www.net.bean.UserIncome

class MyFamilyIncomeModel: MyFamilyIncomeContract.Model {
    override fun getFamilyIncome(current: String?, size: String?, userId: String?,clanId:String?, startTime: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<UserIncome>>>> {
        return HttpClient.getApi().getFamilyIncome(current, size, userId,clanId, startTime)
    }

    override fun getFamilyInfo(userId: String?): HttpObservable<BaseResponse<MyFamilyResult>> {
        return HttpClient.getApi().getFamilyInfo(userId)
    }

}