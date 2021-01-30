package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FamilyCreateContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public class FamilyCreateModel implements FamilyCreateContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getFamilyApplyStatu(String userId) {
        return HttpClient.getApi().getFamilyApplyStatu(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> createFamily(String clanName, String applicantId, String applyComment, MultipartBody.Part file) {
        return HttpClient.getApi().createFamily(clanName, applicantId, applyComment, file);
    }
}
