package com.xsvideoLive.www.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.net.bean.UserPicturesResult;
import com.xsvideoLive.www.utils.GlideAppUtil;
import com.xsvideoLive.www.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class HorizontalPhotoAdapter extends BaseQuickAdapter<UserPicturesResult, HorizontalPhotoAdapter.Holder> {

    public HorizontalPhotoAdapter() {
        super(R.layout.adapter_horizontal_photo);
    }

    @Override
    protected void convert(@NotNull Holder holder, UserPicturesResult userPicturesResult) {
        if (userPicturesResult.getId().equals("0")) {
            holder.mCivPhoto.setImageResource(R.mipmap.square_release_increase);
        } else {
            GlideAppUtil.loadImage(getContext(), userPicturesResult.getUserPicture(), holder.mCivPhoto);
        }
    }

    static class Holder extends BaseViewHolder {

        private CircleImageView mCivPhoto;

        public Holder(@NotNull View view) {
            super(view);

            mCivPhoto = view.findViewById(R.id.civ_photo);
        }
    }
}
