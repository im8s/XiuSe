package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.BindBlackContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class BindBlackModel implements BindBlackContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> bindBlackNum(String type, String account, String bankType, String bankName, String realName, String code) {
        return HttpClient.getApi().bindBlackNum(type, account, bankType, bankName, realName, code);
    }

}
