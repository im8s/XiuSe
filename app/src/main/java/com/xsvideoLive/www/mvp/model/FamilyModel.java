package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FamilyContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MyFamilyResult;
import com.xsvideoLive.www.net.bean.StartRecommendResult;

import java.util.List;

public class FamilyModel implements FamilyContract.Model {
    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<List<StartRecommendResult>>> getStartRecommend() {
        return HttpClient.getApi().getStartRecommend();
    }
}
