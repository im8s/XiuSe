package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.HomePageConract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MineReslut;

public class HomePageModel implements HomePageConract.Model {
    @Override
    public HttpObservable<BaseResponse<MineReslut>> getMine(String userId) {
        return HttpClient.getApi().getMine(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> queryFollow(String userId) {
        return HttpClient.getApi().queryFollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId,status);
    }

    @Override
    public HttpObservable<BaseResponse<String>> unfollow(String userId) {
        return HttpClient.getApi().unfollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> isBlack(String userId) {
        return HttpClient.getApi().isBlack(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> addBlackList(String blacklistType, String currentId, String blacklistUserId) {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> removeBlackList(String blacklistType, String currentId, String blacklistUserId) {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId);
    }
}
