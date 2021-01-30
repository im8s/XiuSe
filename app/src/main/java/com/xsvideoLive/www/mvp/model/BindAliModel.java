package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.BindAliContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class BindAliModel implements BindAliContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> bindWeChat(String type, String account, String realName, String code) {
        return HttpClient.getApi().bindWeChat(type, account, realName, code);
    }

}
