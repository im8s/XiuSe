package com.xsvideoLive.www.mvp.ui.adapter;

import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.callback.FriendFabulousCallback;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.view.ExpandableTextView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.PhotoCallback;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends BaseQuickAdapter<FriendsCircleResult, DynamicAdapter.Holder> {

    private final SparseBooleanArray mCollapsedStatus;
    private PhotoCallback mCallback;
    private FriendFabulousCallback mFablousCallback;

    public DynamicAdapter(PhotoCallback callback) {
        super(R.layout.adapter_dynamic);
        mCollapsedStatus = new SparseBooleanArray();
        this.mCallback = callback;

    }

    public void setFabulousCallback(FriendFabulousCallback fablousCallback) {
        this.mFablousCallback = fablousCallback;
    }

    @Override
    protected void convert(@NotNull Holder holder, FriendsCircleResult result) {

        holder.mTvDate.setText(result.getCreateTime());
        int position = getItemPosition(result);
        holder.mExtView.setText(result.getTopicContent(), mCollapsedStatus, position);

        List<String> pictureList = result.getPictureList();
        if (pictureList == null || pictureList.size() <= 0) {
            holder.mIvStatus.setImageResource(R.mipmap.icon_my_home_dynamic_tags2);
        } else {
            holder.mIvStatus.setImageResource(R.mipmap.icon_my_home_dynamic_tags);
            ArrayList<ImageInfo> images = new ArrayList<>();
            for (String s : pictureList) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(s);
                info.setBigImageUrl(s);
                images.add(info);
            }

            NineGridViewClickAdapter nineGridViewClickAdapter = new NineGridViewClickAdapter(getContext(), images);
            nineGridViewClickAdapter.setPhotoCallback(mCallback);
            holder.mNineGrid.setAdapter(nineGridViewClickAdapter);
        }

        setFabulous(holder.mTvFabulous, result.getHasLike(), result.getLikeNumber());
        holder.mTvComment.setText(String.valueOf(result.getCommentNumber()));


        holder.mRlFabulous.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickFabulous(result, position);
            }
        });

        holder.mRlComment.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickComment(result, position);
            }
        });

        holder.mRlShare.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickShare(result, position);
            }
        });

        holder.mRlMore.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickMore(result, position);
            }
        });

    }

    /**
     * ????????????
     *
     * @param fabulous   ????????????
     * @param hasLike    ???????????? 1 ??? 0 ???
     * @param likeNumber ?????????
     */
    public void setFabulous(TextView fabulous, int hasLike, int likeNumber) {
        fabulous.setEnabled(hasLike == 0 ? false : true);
        fabulous.setText(String.valueOf(likeNumber));
    }

    static class Holder extends BaseViewHolder {

        ImageView mIvStatus;
        TextView mTvDate;
        ExpandableTextView mExtView;            //??????
        NineGridView mNineGrid;                 //?????????
        RelativeLayout mRlFabulous;             //?????????????????????
        TextView mTvFabulous;                   //?????????
        TextView mTvComment;                    //?????????
        TextView mTvShare;                      //????????????
        TextView mTvMore;                       //??????
        RelativeLayout mRlComment;              //??????????????????
        RelativeLayout mRlShare;                //??????????????????
        RelativeLayout mRlMore;                 //??????????????????

        public Holder(@NotNull View view) {
            super(view);
            mTvDate = view.findViewById(R.id.tv_date);
            mIvStatus = view.findViewById(R.id.iv_dynamic_status);
            mExtView = view.findViewById(R.id.expand_text_view);
            mNineGrid = view.findViewById(R.id.nineGrid);
            mRlFabulous = view.findViewById(R.id.rl_fabulous);
            mTvFabulous = view.findViewById(R.id.tv_fabulous);
            mTvComment = view.findViewById(R.id.tv_comment);
            mTvShare = view.findViewById(R.id.tv_share);
            mTvMore = view.findViewById(R.id.tv_more);
            mRlComment = view.findViewById(R.id.rl_comment);
            mRlShare = view.findViewById(R.id.rl_share);
            mRlMore = view.findViewById(R.id.rl_more);

        }
    }
}
