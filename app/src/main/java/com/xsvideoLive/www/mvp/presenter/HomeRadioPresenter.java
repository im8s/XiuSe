package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.HomeRadioContract;
import com.xsvideoLive.www.mvp.model.HomeReadioModel;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.RoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public class HomeRadioPresenter extends BasePresenter<HomeRadioContract.View> implements HomeRadioContract.Presenter {

    private HomeReadioModel model;

    public int initPage = 1;
    public int mPage = initPage;
    public int size = 10;

    public HomeRadioPresenter() {
        model = new HomeReadioModel();
    }

    @Override
    public void getRooms(RefreshLayout refreshLayout, String tpeId) {
        model.getRooms(mPage + "", size + "", tpeId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<RoomResult>>>() {

                    @Override
                    protected void success(HomeRoomResult<List<RoomResult>> bean, BaseResponse<HomeRoomResult<List<RoomResult>>> response) {

                        List<RoomResult> records = bean.getRecords();
                        if (records != null) {
                            if (mPage == initPage) {
                                getView().refreshRoom(refreshLayout, records);
                            } else {
                                getView().loadMoreRoom(refreshLayout, records);
                            }
                            if (records.size() > 0)
                                mPage++;
                        }
                    }


                    @Override
                    protected void error(String msg) {
                        getView().onError(refreshLayout, msg);

                    }
                });
    }
}
