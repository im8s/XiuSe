package com.xsvideoLive.www.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.mvp.ui.view.CharmLevelView;
import com.xsvideoLive.www.mvp.ui.view.UserLevelView;
import com.xsvideoLive.www.net.bean.MyFollowResult;
import com.xsvideoLive.www.utils.GlideAppUtil;
import com.xsvideoLive.www.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class BuddySelectAdapter extends BaseQuickAdapter<MyFollowResult, BuddySelectAdapter.Holder> {


    public BuddySelectAdapter() {
        super(R.layout.adapter_buddy_select);
        addChildClickViewIds(R.id.rl_give_away);
    }

    @Override
    protected void convert(@NotNull Holder holder, MyFollowResult result) {
        GlideAppUtil.loadImage(getContext(),result.getUserPictureUrl(),holder.mCivAvatar);
        GlideAppUtil.loadImage(getContext(),result.getUserPictureUrl(),holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mUlv.setUserLevel(result.getUserLevel());
        holder.mClv.setCharmLevel(result.getCharmLevel());
        holder.mTvUserIntroduce.setText(result.getUserIntroduce());
        int userSex = result.getUserSex();
        if (userSex == 0) {
            holder.mIvSex.setImageResource(R.mipmap.icon_gender_female);
        } else if (userSex == 1){
            holder.mIvSex.setImageResource(R.mipmap.icon_gender_male);
        }

    }


    static class Holder extends BaseViewHolder {

        private CircleImageView mCivAvatar;
        private TextView mTvNickName;
        private UserLevelView mUlv;
        private CharmLevelView mClv;
        private TextView mTvUserIntroduce;
        private ImageView mIvSex;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mUlv = view.findViewById(R.id.ulv);
            mClv = view.findViewById(R.id.clv);
            mTvUserIntroduce = view.findViewById(R.id.tv_user_introduce);
            mIvSex = view.findViewById(R.id.iv_sex);
        }
    }
}
