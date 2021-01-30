package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.NobleWebContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.NobleResult;

import java.util.List;

public class NobleWebModel implements NobleWebContract.Model {


    @Override
    public HttpObservable<BaseResponse<List<NobleResult>>> getNobleList() {
        return HttpClient.getApi().getNobleList();
    }
}
