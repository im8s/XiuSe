package com.xsvideoLive.www.mvp.ui.activity.mine;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xsvideoLive.www.BuildConfig;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.OnItemDeleteCallback;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.CacheConstant;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.MyPhotosConstract;
import com.xsvideoLive.www.mvp.presenter.MyPhotosPresenter;
import com.xsvideoLive.www.mvp.ui.adapter.MyPhotosAdapter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.UserPicturesResult;
import com.xsvideoLive.www.utils.FileUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.utils.UCropUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class MyPhotosAct extends BaseMvpActivity<MyPhotosPresenter> implements MyPhotosConstract.View, TitleBarClickCallback {


    private static final String TAG = "MyPhotosAct";
    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.rcy_photo)
    RecyclerView rcyPhoto;


    private Uri uri;
    private File cameraSavePath;
    private String photoPath;
    private MineReslut mResult;
    private MyPhotosAdapter mAdapter;

    private ArrayList<UserPicturesResult> userPicturesList = new ArrayList<>();

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_photos;
    }

    @Override
    protected MyPhotosPresenter createPresenter() {
        return new MyPhotosPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
        initData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rcyPhoto.setLayoutManager(gridLayoutManager);

        mAdapter = new MyPhotosAdapter();
        rcyPhoto.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserPicturesResult item = (UserPicturesResult) adapter.getItem(position);
                if (item.getId().equals("0")) {
                    if (userPicturesList.size() < 9) {
                        mPresenter.selectPhoto();
                    } else {
                        ToastUtils.showShort("???????????????????????????");
                    }
                } else {

                    ArrayList<String> photo = new ArrayList<>();

                    for (UserPicturesResult userPicturesResult : userPicturesList) {
                        String id = userPicturesResult.getId();
                        if (!id.equals("0")) {
                            photo.add(userPicturesResult.getUserPicture());
                        }
                    }

                    photoPreviewWrapper((position - 1), photo);
                }
            }
        });

        mAdapter.setOnItemDeleteCallback(new OnItemDeleteCallback() {
            @Override
            public void onItemDelete(int position) {
                UserPicturesResult item = mAdapter.getItem(position);

                mPresenter.delete(item.getId(), position);

            }
        });

        mPresenter.getMyPhotos(mResult.getId());

    }

    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                .saveImgDir(null); // ????????????????????????????????? null??????????????????????????????

        if (imageInfo.size() == 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // ??????????????????
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // ???????????????????????????
        }
        startActivity(photoPreviewIntentBuilder.build());
    }

    private void initData() {

        Bundle extras = getIntent().getExtras();

        mResult = (MineReslut) extras.getSerializable(Constant.MINE_RESLUT);

        List<UserPicturesResult> userPictures = mResult.getUserPictures();

        photoAdd(userPictures);
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

            mPresenter.upPhoto(photoPath);

        }


    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        mAdapter.setEdit();
    }


    private void photoAdd(List<UserPicturesResult> userPictures) {
        userPicturesList.clear();
        UserPicturesResult userPicturesResult = initList();
        userPicturesList.add(userPicturesResult);
        userPicturesList.addAll(userPictures);
    }

    private UserPicturesResult initList() {
        UserPicturesResult bean = new UserPicturesResult();
        bean.setId("0");
        return bean;

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
    public void onError(String msg) {
        ToastUtils.showShort(msg);
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
    public void myPhotoSuccess(List<UserPicturesResult> myPhotos) {

        photoAdd(myPhotos);

        mAdapter.setList(userPicturesList);

        if (CacheConstant.result != null) {
            CacheConstant.result.setUserPictures(myPhotos);
        }

    }

    @Override
    public void upPhotoSuccess(String status) {

        if (status.equals("1")) {
            mPresenter.getMyPhotos(mResult.getId());
        } else {
            ToastUtils.showShort("????????????");
        }
    }

    @Override
    public void deleteSuccess(String status, int position) {
        if ("1".equals(status)) {
            mPresenter.getMyPhotos(mResult.getId());
        } else {
            ToastUtils.showShort("????????????");
        }
    }
}
