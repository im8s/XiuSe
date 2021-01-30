package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MineContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CustomerServiceEntity;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.MyFamilyResult;

public class MineModel implements MineContract.Model {

    @Override
    public HttpObservable<BaseResponse<MineReslut>> getMine(String userId) {
        return HttpClient.getApi().getMine(userId);
    }

    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<CustomerServiceEntity>> getService() {
        return HttpClient.getApi().getService();
    }
}
