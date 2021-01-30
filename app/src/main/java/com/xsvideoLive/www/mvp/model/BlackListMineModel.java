package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.BlackListMineContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BlackListMineBean;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class BlackListMineModel implements BlackListMineContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<BlackListMineBean>>>> getBlackListMine(String current, String size, String blackListType, String currentId) {
        return HttpClient.getApi().getBlackListMine(current,size,blackListType,currentId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> removeBlackMine(String id) {
        return HttpClient.getApi().removeBlackMine(id);
    }
}
