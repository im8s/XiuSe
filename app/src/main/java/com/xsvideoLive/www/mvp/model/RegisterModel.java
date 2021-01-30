package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.RegisterContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;

import okhttp3.MultipartBody;

public class RegisterModel implements RegisterContract.Model {
    @Override
    public HttpObservable<BaseResponse<LogonResult>> register(MultipartBody.Part file, String userName, String userPhone, String userBirthday, String userSex, String wechatOpenid) {
        return HttpClient.getApi().register(file, userName, userPhone, userBirthday, userSex, wechatOpenid);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
