package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.CompileMateriaContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.AvatarResult;
import com.xsvideoLive.www.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public class CompileMateriaModel implements CompileMateriaContract.Model {
    @Override
    public HttpObservable<BaseResponse<AvatarResult>> upAvatar(MultipartBody.Part file) {
        return HttpClient.getApi().upAvatar(file);
    }

    @Override
    public HttpObservable<BaseResponse<String>> editBirthday(String birthday) {
        return HttpClient.getApi().editBirthday(birthday);
    }
}
