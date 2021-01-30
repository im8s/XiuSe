package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MineContract;
import com.xsvideoLive.www.mvp.model.MineModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CustomerServiceEntity;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.MyFamilyResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    MineModel model;

    public MinePresenter() {
        model = new MineModel();
    }

    @Override
    public void getMine(RefreshLayout refreshLayout, String userId) {
        model.getMine(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MineReslut>() {
                    @Override
                    protected void success(MineReslut bean, BaseResponse<MineReslut> response) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        getView().MineSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        if (refreshLayout != null) {
                            refreshLayout.finishRefresh();
                        }
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void family(int isClanElder, String userId) {


        if (isClanElder == 1) { //是族长

            getView().familyOnSuccess("1", null, userId);

        } else if (isClanElder == 0) {  //不是族长
            getView().showLoading();
            model.getFamilyInfo(userId)
                    .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MyFamilyResult>() {

                        @Override
                        protected void success(MyFamilyResult bean, BaseResponse<MyFamilyResult> response) {
                            getView().hideLoading();
                            if (bean != null) { //有家族
                                getView().familyOnSuccess("2", bean.getClanId(), userId);
                            } else {
                                getView().familyOnSuccess("0", null, null);
                            }
                        }

                        @Override
                        protected void error(String msg) {
                            getView().hideLoading();
                            getView().onError(msg);
                        }
                    });
        }
    }

    @Override
    public void getService() {
        model.getService()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<CustomerServiceEntity>() {

                    @Override
                    protected void success(CustomerServiceEntity bean, BaseResponse<CustomerServiceEntity> response) {
                        getView().onServiceSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
