package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.BlackListMineContract;
import com.xsvideoLive.www.mvp.model.BlackListMineModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BlackListMineBean;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class BlackListMinePresenter extends BasePresenter<BlackListMineContract.View> implements BlackListMineContract.Presenter {


    public int initPage = 1;
    public int mPage = initPage;
    private int size = 10;
    private String blactListType = "2";

    private BlackListMineModel model = new BlackListMineModel();


    @Override
    public void getBlackList(RefreshLayout refresh, String currentId) {

        model.getBlackListMine(mPage + "", size + "", blactListType, currentId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<BlackListMineBean>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<BlackListMineBean>> bean, BaseResponse<HomeRoomResult<List<BlackListMineBean>>> response) {
                        List<BlackListMineBean> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refreshSuccess(refresh, records);
                            } else {
                                getView().loadMoreSuccess(refresh, records);
                            }
                            if (records.size() > 0) mPage++;
                        }

                    }

                    @Override
                    protected void error(String msg) {

                        if (refresh != null) {
                            refresh.finishRefresh();
                            refresh.finishLoadMore();
                        }
                        getView().onError(msg);
                    }


    });

}




    @Override
    public void removeBlackMine(String id, int position) {
        getView().showLoading();
        model.removeBlackMine(id)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().removeSuccess(position,bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }
}
