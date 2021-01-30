package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MaterialContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MyFamilyResult;

public class MaterialModel implements MaterialContract.Model {
    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }
}
