package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.OneKeyLogonContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;


public class OneKeyLogonModel implements OneKeyLogonContract.Model {
    @Override
    public  HttpObservable<BaseResponse<LogonResult>>  oneKeyLogin(String phoneNum) {
        return HttpClient.getApi().oneKeyLogin(phoneNum);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
