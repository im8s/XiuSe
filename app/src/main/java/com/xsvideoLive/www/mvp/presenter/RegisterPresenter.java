package com.xsvideoLive.www.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.RegisterContract;
import com.xsvideoLive.www.mvp.model.RegisterModel;
import com.xsvideoLive.www.mvp.ui.activity.login.RegisterActivity;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.DateUtil;
import com.xsvideoLive.www.utils.FileUtils;
import com.xsvideoLive.www.utils.PermissionUtil;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.TipsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterModel model;

    public RegisterPresenter() {
        model = new RegisterModel();
    }

    @Override
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions((RegisterActivity) getView());
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
    public void selectTime(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.stringToDate(date, DateUtil.DatePattern.ONLY_DAY));

        TimePickerView pvTime = new TimePickerBuilder((Context) getView(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                String dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY);
                getView().setTime(dateToString);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
                .setCancelText("??????")//??????????????????
                .setSubmitText("??????")//??????????????????
//                .setContentSize(18)//??????????????????
                .setTitleSize(20)//??????????????????
                .setTitleText("???????????????")//????????????
                .setOutSideCancelable(false)//???????????????????????????????????????????????????????????????
                .isCyclic(true)//??????????????????
                .setTitleColor(Color.BLACK)//??????????????????
                .setSubmitColor(Color.BLUE)//????????????????????????
                .setCancelColor(Color.BLUE)//????????????????????????
                .setTitleBgColor(Color.WHITE)//?????????????????? Night mode
                .setBgColor(Color.WHITE)//?????????????????? Night mode
                .setDate(calendar)// ?????????????????????????????????????????????*/
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .isDialog(false)//??????????????????????????????
                .setDecorView(((Activity) (getView())).getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvTime.show();

    }

    @Override
    public void register(String filePath, String userName, String userPhone, String userBirthday, String userSex, String wechatOpenid) {

        getView().showLoading();

        String fileStr = FileUtils.qualityCompress(filePath);

        File file = new File(fileStr);
        File file2 = new File(file.getAbsolutePath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file2.getName(), photoRequestBody);
        model.register(body, userName, userPhone, userBirthday, userSex, wechatOpenid)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {
                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();

                        if (bean == null) {
                            getView().registerError(((Context) getView()).getResources().getString(R.string.register_error));
                            return;
                        }
                        switch (bean.getStatus()) {
                            case Constant.REGISTER_SUCCESS:

//                                String strUser = new Gson().toJson(bean.getUser());
//                                SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
//                                SPUtils.getInstance().put(SpConstant.APP_TOKEN, bean.getToken());
                                getView().registerSuccess(bean);

                                break;
                            case Constant.REGISTER_ERROR:
                                getView().registerError(((Context) getView()).getResources().getString(R.string.register_error));
                                break;
                            case Constant.REGISTER_REPEAT:
                            case "10":
                                getView().registerError(((Context) getView()).getResources().getString(R.string.register_repeat));
                                break;
                            default:
                                ToastUtils.showLong(((Context) getView()).getResources().getString(R.string.register_error));
                                break;
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().registerError(msg);
                    }
                });
    }

    @Override
    public void getUserSig(LogonResult data) {

        getView().showLoading();

        model.getUserSig(data.getUser().getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().sigSuccess(data, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().registerError(msg);
                    }
                });
    }

    @Override
    public void loginIm(LogonResult data, String sig, IUIKitCallBack callBack) {

        SPUtils.getInstance().put(SpConstant.APP_SIG, sig);

//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserId(data.getUser().getId());
//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserSig(sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((RegisterActivity) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack!=null) {
                    callBack.onError("????????????",code,msg);
                }
            } else {
//                if (TUIKitConfigs.getConfigs().getGeneralConfig().isSupportAVCall()) {
//                    UserModel self = new UserModel();
//                    self.userId = data.getUser().getId();
//                    self.userSig = sig;
//                    ProfileManager.getInstance().setUserModel(self);
//                    AVCallManager.getInstance().init(((RegisterActivity) getView()).getApplicationContext());
//                }
                callBack.onSuccess(null);
            }
        });

//        TUIKit.login(data.getUser().getId(), sig, callBack);
    }

}
