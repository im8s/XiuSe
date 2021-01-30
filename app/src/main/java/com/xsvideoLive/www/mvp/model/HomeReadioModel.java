package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.HomeRadioContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.RoomResult;

import java.util.List;

public class HomeReadioModel implements HomeRadioContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<RoomResult>>>> getRooms(String current, String size, String typeId) {
        return HttpClient.getApi().getRooms(current, size, typeId);
    }
}
