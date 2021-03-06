package com.xsvideoLive.www.mvp.ui.activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.xsvideoLive.www.BuildConfig;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.EventConstant;
import com.xsvideoLive.www.constant.LoginStatus;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.RegisterContract;
import com.xsvideoLive.www.mvp.presenter.RegisterPresenter;
import com.xsvideoLive.www.net.bean.Event;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.DateUtil;
import com.xsvideoLive.www.utils.FileUtils;
import com.xsvideoLive.www.utils.GlideAppUtil;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.utils.UCropUtils;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View, IUIKitCallBack {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_birthday)
    TextView etBirthday;
    @BindView(R.id.tv_logon)
    TextView tvLogon;

    private int mSex = 1;
    private Uri uri;
    private File cameraSavePath;
    private String photoPath;
    String phoneNum;

    private LogonResult mLogonResult;

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        phoneNum = (String) extras.get(Constant.PHONE_NUM);

        Date date = new Date();
        String dateToString = DateUtil.get18String(date, DateUtil.DatePattern.ONLY_DAY);
        etBirthday.setText(dateToString);
    }

    @OnTextChanged({R.id.et_nickname, R.id.et_birthday})
    void onLoginCheck(Editable editable) {
        tvLogon.setEnabled(etNickname.length() > 0 && etBirthday.length() > 0);
        tvLogon.setAlpha(tvLogon.isEnabled() ? 1f : 0.5f);
    }


    @OnClick({R.id.iv_back, R.id.iv_photo, R.id.iv_sex, R.id.et_birthday, R.id.tv_logon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_photo:
                mPresenter.selectPhoto();
                break;
            case R.id.iv_sex:

                if (mSex == 0) {
                    mSex = 1;
                } else if (mSex == 1) {
                    mSex = 0;
                }

                selectSex(mSex);
                break;
            case R.id.et_birthday:
                mPresenter.selectTime(etBirthday.getText().toString().trim());
                break;
            case R.id.tv_logon:
                if (TextUtils.isEmpty(photoPath)) {
                    ToastUtils.showShort(getResources().getString(R.string.select_avatar));
                }

                String nickName = etNickname.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.showShort(getResources().getString(R.string.select_nickname));
                }

                String birthday = etBirthday.getText().toString().trim();
                if (TextUtils.isEmpty(birthday)) {
                    ToastUtils.showShort(getResources().getString(R.string.select_birthday));
                }

                String replaceBirthday = birthday.replace("-", "/");

                mPresenter.register(photoPath, nickName, phoneNum, replaceBirthday, String.valueOf(mSex), "");
        }
    }

    public void selectSex(int sex) {

        if (sex == 0) {
            ivSex.setImageResource(R.mipmap.icon_female);
        } else if (sex == 1) {
            ivSex.setImageResource(R.mipmap.icon_male);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.CAMERA) {

            String photoPath;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }

            UCropUtils.startUCrop(this, photoPath, Constant.CROP_RESULT, 1, 1);
        }

        if (resultCode == RESULT_OK && requestCode == Constant.GALLERY_RESULT) {
            Uri uri = data.getData();
            String filePathByUri = FileUtils.getFilePathByUri(this, uri);
            UCropUtils.startUCrop(this, filePathByUri, Constant.CROP_RESULT, 1, 1);
        }

        if (resultCode == RESULT_OK && requestCode == Constant.CROP_RESULT) {
            Uri resultUri = UCrop.getOutput(data);
            photoPath = FileUtils.getFilePathByUri(this, resultUri);
            GlideAppUtil.loadImage(this, photoPath, ivPhoto);
        }
    }


    @Override
    public void camera() {
        takePhoto();
    }

    @Override
    public void gallery() {
        selectPhoto();
    }

    @Override
    public void setTime(String date) {
        etBirthday.setText(date);
    }

    @Override
    public void registerSuccess(LogonResult result) {

        mLogonResult = result;

        //?????????????????????????????????????????????????????????????????????sig???????????????IM
        if (LoginStatus.getValue(mLogonResult.getStatus()).equals(LoginStatus.SUCCESS)) {
            SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
            mPresenter.getUserSig(result);

        } else {
            ActStartUtils.loginStartAct(this, result, null);
        }

    }

    /**
     * ??????sig
     *
     * @param data
     * @param sig
     */
    @Override
    public void sigSuccess(LogonResult data, String sig) {

        if (!TextUtils.isEmpty(sig)) {
            mPresenter.loginIm(data, sig, this);
        } else {
            ToastUtils.showShort(getString(R.string.login_error));
        }

    }

    @Override
    public void registerError(String msg) {
        ToastUtils.showShort(msg);
    }

    private void takePhoto() {

        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".JPG");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //?????????????????? ??????.fileprovider
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, Constant.CAMERA);
    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Constant.GALLERY_RESULT);
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        if (event.getCode() == EventConstant.LOGIN_SUCCESS) {
            finish();
        }
    }

    @Override
    public void onSuccess(Object data) {

        switch (mLogonResult.getStatus()) {
            case Constant.REGISTER_SUCCESS:

                String strUser = new Gson().toJson(mLogonResult.getUser());
                SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
                SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
                ActStartUtils.loginStartAct(this, mLogonResult, null);

                break;
            case Constant.REGISTER_ERROR:
                ToastUtils.showShort(R.string.register_error);
                break;
            case Constant.REGISTER_REPEAT:
                ToastUtils.showShort(R.string.register_repeat);
                break;
            case "10":
                ToastUtils.showShort("???????????????");
                break;
        }

    }

    @Override
    public void onError(String module, int errCode, String errMsg) {
        ToastUtils.showShort(getResources().getString(R.string.login_error));
    }
}
