package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.RegisterAndLoginContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class RegisterAndLoginModel implements RegisterAndLoginContract.Model {

    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<String>> checkCode(String phone, String code) {
        return HttpClient.getApi().checkCode(phone,code);
    }
}
