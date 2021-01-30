package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.PublicChatContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GiftAllResult;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

public class PublicChatModel implements PublicChatContract.Model {
    @Override
    public HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getUserInfo(String userId) {
        return HttpClient.getApi().getUserInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> queryFollow(String userId) {
        return HttpClient.getApi().queryFollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<GiftAllResult>> getGift() {
        return HttpClient.getApi().getRoomGift();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> getNoble() {
        return HttpClient.getApi().getNoble();
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId, status);
    }
}
