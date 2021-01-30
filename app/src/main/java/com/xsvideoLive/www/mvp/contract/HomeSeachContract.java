package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeSeachEntity;
import com.xsvideoLive.www.net.bean.SeachRoomEntty;

import java.util.List;

public interface HomeSeachContract {

    interface View extends BaseView {

        void onRecordResult(List<SeachRoomEntty> result);

        void onDeleteSuccess(String status);

        void onSeachSuccess(List<HomeSeachEntity> seachResult);

        void onSeachError(String msg);

        void onError(String msg);

    }

    interface Presenter {

        void getSeachRoomRecord();

        void removeEnterRoomRecord(String userId);

        void seach(String searchString);

    }

    interface Model {

        HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord();

        HttpObservable<BaseResponse<String>> removeEnterRoomRecord(String userId);

        HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(String searchString);
    }
}
