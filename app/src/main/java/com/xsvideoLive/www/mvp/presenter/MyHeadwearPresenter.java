package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MyHeadwearContract;
import com.xsvideoLive.www.mvp.model.MyHeadwearModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;
import com.xsvideoLive.www.net.bean.Decks;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public class MyHeadwearPresenter extends BasePresenter<MyHeadwearContract.View> implements MyHeadwearContract.Presenter {

    private MyHeadwearModel model;

    public MyHeadwearPresenter() {
        model = new MyHeadwearModel();
    }

    @Override
    public void getMyHeadwear(RefreshLayout refreshLayout) {

        model.getMyHeadwear()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<DeckResult>() {
                    @Override
                    protected void success(DeckResult bean, BaseResponse<DeckResult> response) {
                        getView().onSuccess(bean, refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        getView().myHeadwearError(msg);
                    }
                });
    }

    @Override
    public void useHeadwear(Decks decks) {
        if (decks == null) return;
        model.useHeadwear(decks.getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().useHeadwearSuccess(decks, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().equals(msg);
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


}
