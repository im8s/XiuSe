package com.xsvideoLive.www.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.FamilyCreateContract;
import com.xsvideoLive.www.mvp.model.FamilyCreateModel;
import com.xsvideoLive.www.mvp.ui.activity.square.FamilyCreateAct;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.utils.FileUtils;
import com.xsvideoLive.www.utils.PermissionUtil;
import com.xsvideoLive.www.view.TipsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FamilyCreatePresenter extends BasePresenter<FamilyCreateContract.View> implements FamilyCreateContract.Presenter {

    private FamilyCreateModel model;

    public FamilyCreatePresenter() {
        this.model = new FamilyCreateModel();
    }

    @Override
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions((FamilyCreateAct) getView());
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
    public void getFamilyApplyStatu(String userId) {

        getView().showLoading();
        model.getFamilyApplyStatu(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().ApplyStatusSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);

                    }
                });

    }

    @Override
    public void createFamily(String clanName, String applicantid, String applyComment, String filePath) {

        if (!TextUtils.isEmpty(clanName)&&!TextUtils.isEmpty(applicantid)&&!TextUtils.isEmpty(applyComment)&&!TextUtils.isEmpty(filePath)) {

            getView().showLoading();

//            File file = new File(filePath);
//            File file2 = new File(file.getAbsolutePath());

            String filsStr = FileUtils.qualityCompress(filePath);
            File file = new File(filsStr);
//            File file2 = new File(file.getAbsolutePath());

            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);

            model.createFamily(clanName,applicantid,applyComment,body)
                    .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                        @Override
                        protected void success(String bean, BaseResponse<String> response) {
                            getView().hideLoading();
                            getView().createFamilySuccess(bean);
                        }

                        @Override
                        protected void error(String msg) {
                            getView().hideLoading();
                            getView().onError(msg);
                        }
                    });
        }

    }
}
