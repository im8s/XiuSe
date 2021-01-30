package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.MyPhotosConstract;
import com.xsvideoLive.www.mvp.model.MyPhotosModel;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyPhotosAct;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.UserPicturesResult;
import com.xsvideoLive.www.utils.FileUtils;
import com.xsvideoLive.www.utils.PermissionUtil;
import com.xsvideoLive.www.view.TipsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyPhotosPresenter extends BasePresenter<MyPhotosConstract.View> implements MyPhotosConstract.Presenter {

    private MyPhotosModel model;

    public MyPhotosPresenter() {
        model = new MyPhotosModel();
    }

    @Override
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions((MyPhotosAct) getView());
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                TipsDialog.createDialog((Context) getView(), R.layout.dialog_select_camera)
                        .bindClick(R.id.tv_camera, (v, dialog) -> {
                            getView().camera();
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_photo, (v, dialog) -> {
                            getView().gallery();
                        })
                        .show();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            }
        }, rxPermissions);
    }

    @Override
    public void upPhoto(String photoPath) {
        if (TextUtils.isEmpty(photoPath)) return;

        getView().showLoading();

        String filePath = FileUtils.qualityCompress(photoPath);
        File file = new File(filePath);

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);

        model.addPicture(body)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().upPhotoSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void delete(String id, int position) {
        getView().showLoading();
        model.deletePhoto(id)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().deleteSuccess(bean,position);
                    }

                    @Override
                    protected void error(String msg) {

                        getView().onError(msg);

                    }
                });
    }


    @Override
    public void getMyPhotos(String userId) {

        model.getMyPhotos(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<UserPicturesResult>>() {

                    @Override
                    protected void success(List<UserPicturesResult> bean, BaseResponse<List<UserPicturesResult>> response) {
                        getView().hideLoading();
                        getView().myPhotoSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);

                    }
                });
    }
}
