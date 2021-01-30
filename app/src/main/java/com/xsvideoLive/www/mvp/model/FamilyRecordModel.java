package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FamilyRecordContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.RecordResult;

import java.util.List;

public class FamilyRecordModel implements FamilyRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<RecordResult>>>> getapplyList(String userId, String begin, String size) {
        return HttpClient.getApi().getapplyList(userId, begin, size);
    }
}
