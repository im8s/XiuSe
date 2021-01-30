package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyHeadwearContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;

public class MyHeadwearModel implements MyHeadwearContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> getMyHeadwear() {
        return HttpClient.getApi().getMyHeadwear();
    }

    @Override
    public HttpObservable<BaseResponse<String>> useHeadwear(String id) {
        return HttpClient.getApi().useHeadwear(id);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
