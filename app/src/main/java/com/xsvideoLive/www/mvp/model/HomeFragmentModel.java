package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.HomeFragmentContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BannerResult;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeSortResult;

import java.util.List;

public class HomeFragmentModel implements HomeFragmentContract.Model {
    @Override
    public HttpObservable<BaseResponse<List<BannerResult>>> getBanner() {
        return HttpClient.getApi().getBanner();
    }

    @Override
    public HttpObservable<BaseResponse<List<HomeSortResult>>> getHomeSort() {
        return HttpClient.getApi().getHomeSort();
    }
}
