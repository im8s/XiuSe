package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MaterialContract;
import com.xsvideoLive.www.mvp.model.MaterialModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MyFamilyResult;

public class MaterialPresenter extends BasePresenter<MaterialContract.View> implements MaterialContract.Presenter {

    private MaterialModel model;

    public MaterialPresenter() {
        model = new MaterialModel();
    }

    @Override
    public void getMyFamily(String userId) {
        model.getFamilyInfo(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MyFamilyResult>() {
                    @Override
                    protected void success(MyFamilyResult bean, BaseResponse<MyFamilyResult> response) {
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
