package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.ChangePswMineContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class ChangePswMineModel implements ChangePswMineContract.Model {
    @Override
    public HttpObservable<BaseResponse<Integer>> changePswMine(String type, String pswNew, String smsCode, String pswOld) {
        return HttpClient.getApi().changePswMine(type,pswNew,smsCode,pswOld);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }
}
