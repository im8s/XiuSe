package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.OtherContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;

public class OtherLoginModel implements OtherContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<LogonResult>> loginCode(String phone, String code) {
        return HttpClient.getApi().loginCode(phone, code);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
