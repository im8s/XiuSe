package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FriendsSelectionContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class FriendsSelectionModel implements FriendsSelectionContract.Model {
    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
