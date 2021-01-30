package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.CarShopContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.Decks;

import java.util.List;

public class CarShopModel implements CarShopContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<Decks>>> store(Integer deckType) {
        return HttpClient.getApi().store(deckType);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId) {
        return HttpClient.getApi().buyDeck(deckId, userId);
    }
}
