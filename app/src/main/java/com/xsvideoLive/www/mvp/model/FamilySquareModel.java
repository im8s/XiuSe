package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FamilySquareContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FamilyRankResult;

import java.util.List;

public class FamilySquareModel implements FamilySquareContract.Model {
    @Override
    public HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank() {
        return HttpClient.getApi().getFamilyRank();
    }
}
