package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.HomeSeachContract;
import com.xsvideoLive.www.mvp.model.HomeSeachModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeSeachEntity;
import com.xsvideoLive.www.net.bean.SeachRoomEntty;

import java.util.List;

public class HomeSeachPresenter extends BasePresenter<HomeSeachContract.View> implements HomeSeachContract.Presenter {

    private HomeSeachModel model = new HomeSeachModel();

    @Override
    public void getSeachRoomRecord() {
        model.getSeachRoomRecord()
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<List<SeachRoomEntty>>(){

                    @Override
                    protected void success(List<SeachRoomEntty> bean, BaseResponse<List<SeachRoomEntty>> response) {
                        getView().onRecordResult(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void removeEnterRoomRecord(String userId) {
        model.removeEnterRoomRecord(userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().onDeleteSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void seach(String searchString) {
        model.getSeachResult(searchString)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<HomeSeachEntity>>() {
                    @Override
                    protected void success(List<HomeSeachEntity> bean, BaseResponse<List<HomeSeachEntity>> response) {
                        getView().onSeachSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onSeachError(msg);
                    }
                });
    }
}
