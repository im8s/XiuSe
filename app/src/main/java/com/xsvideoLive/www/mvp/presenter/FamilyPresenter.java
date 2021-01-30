package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.FamilyContract;
import com.xsvideoLive.www.mvp.model.FamilyModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MyFamilyResult;
import com.xsvideoLive.www.net.bean.StartRecommendResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class FamilyPresenter extends BasePresenter<FamilyContract.View> implements FamilyContract.Presenter {

    private FamilyModel model;

    public FamilyPresenter() {
        model = new FamilyModel();
    }

    @Override
    public void getFamilyInfo(String userId, RefreshLayout refreshLayout) {
        getView().showLoading();
        model.getFamilyInfo(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MyFamilyResult>() {
                    @Override
                    protected void success(MyFamilyResult result, BaseResponse<MyFamilyResult> response) {
                        getView().familySuccess(result, refreshLayout);

                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg, refreshLayout);
                    }
                });
    }

    @Override
    public void getStartRecommend(RefreshLayout refreshLayout) {
        model.getStartRecommend()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<StartRecommendResult>>() {
                    @Override
                    protected void success(List<StartRecommendResult> bean, BaseResponse<List<StartRecommendResult>> response) {
                        getView().hideLoading();
                        getView().recommendSuccess(bean, refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg, refreshLayout);
                    }
                });
    }
}
