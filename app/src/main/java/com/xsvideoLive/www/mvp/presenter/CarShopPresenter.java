package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.CarShopContract;
import com.xsvideoLive.www.mvp.model.CarShopModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MineReslut;

import java.util.List;

public class CarShopPresenter extends BasePresenter<CarShopContract.View> implements CarShopContract.Presenter {

    private CarShopModel model;

    public CarShopPresenter() {
        model = new CarShopModel();
    }


    @Override
    public void getStoreCar() {
        model.store(2)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<Decks>>() {
                    @Override
                    protected void success(List<Decks> bean, BaseResponse<List<Decks>> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {

                        getView().onError(msg);

                    }
                });
    }

    @Override
    public void buyDeck(String deckId) {
        getView().showLoading();
        model.buyDeck(deckId,"")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().buyDeckSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void giveAway(String deckId, String userId, Decks decks, MineReslut mResult) {
        getView().showLoading();
        model.buyDeck(deckId,userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().giveAwaySuccess(bean,decks,mResult);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }
}
