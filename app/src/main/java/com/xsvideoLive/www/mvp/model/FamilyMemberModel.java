package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FamilyMemberContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.ClanResult;
import com.xsvideoLive.www.net.bean.FamilyMemberResult;

import java.util.List;

public class FamilyMemberModel implements FamilyMemberContract.Model {
    @Override
    public HttpObservable<BaseResponse<ClanResult>> getClanInfo(String clanId) {
        return HttpClient.getApi().getClanInfo(clanId);
    }

    @Override
    public HttpObservable<BaseResponse<List<FamilyMemberResult>>> familyMembers(String clanId) {
        return HttpClient.getApi().familyMembers(clanId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> joinFamily(String userId, String clanId) {
        return HttpClient.getApi().joinFamily(userId,clanId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> outFamily(String userId, String clanId) {
        return HttpClient.getApi().outFamily(userId, clanId);
    }
}
