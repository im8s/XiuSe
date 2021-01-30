package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyPhotosConstract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.UserPicturesResult;

import java.util.List;

import okhttp3.MultipartBody;

public class MyPhotosModel implements MyPhotosConstract.Model {

    @Override
    public HttpObservable<BaseResponse<String>> addPicture(MultipartBody.Part file) {
        return HttpClient.getApi().addPicture(file);
    }

    @Override
    public HttpObservable<BaseResponse<String>> deletePhoto(String id) {
        return HttpClient.getApi().deletePhoto(id);
    }

    @Override
    public HttpObservable<BaseResponse<List<UserPicturesResult>>> getMyPhotos(String userId) {
        return HttpClient.getApi().getMyPhotos(userId);
    }

}
