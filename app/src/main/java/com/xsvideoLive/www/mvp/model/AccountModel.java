package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.AccountContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;

public class AccountModel implements AccountContract.Model {
    @Override
    public HttpObservable<BaseResponse<LogonResult>> loginPsd(String phone, String password) {
        return HttpClient.getApi().loginPsd(phone,password);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
