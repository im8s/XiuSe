package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.FamilySquareContract;
import com.xsvideoLive.www.mvp.model.FamilySquareModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FamilyRankResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class FamilySquarePresenter extends BasePresenter<FamilySquareContract.View> implements FamilySquareContract.Presenter {

    private  FamilySquareModel model;
    public FamilySquarePresenter() {
        model = new FamilySquareModel();
    }

    @Override
    public void getFamilyRank(RefreshLayout refreshLayout) {
        model.getFamilyRank()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<FamilyRankResult>>() {
                    @Override
                    protected void success(List<FamilyRankResult> bean, BaseResponse<List<FamilyRankResult>> response) {
                        getView().familyRankSuccess(bean,refreshLayout);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout,msg);
                    }
                });
    }
}
