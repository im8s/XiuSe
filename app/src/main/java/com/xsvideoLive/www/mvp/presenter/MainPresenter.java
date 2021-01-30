package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MainContract;
import com.xsvideoLive.www.mvp.model.MainModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainModel model = new MainModel();

    @Override
    public void exitRoom(String roomId) {
        model.exitRoom(roomId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {

                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }

    @Override
    public void getTeensStatus(String userId) {
        model.getTeensStatus(userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().teenSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
