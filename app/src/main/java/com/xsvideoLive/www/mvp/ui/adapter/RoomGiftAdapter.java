package com.xsvideoLive.www.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.net.bean.RoomGiftResult;
import com.xsvideoLive.www.utils.GlideAppUtil;

import java.util.List;

public class RoomGiftAdapter extends BaseAdapter {
    private List<RoomGiftResult> list;
    private Context mContext;

    public RoomGiftAdapter(List<RoomGiftResult> list, Context mContext) {
        super();
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RoomGiftResult getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RoomGiftResult gift = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_gift, null);
            holder.mIvGift = convertView.findViewById(R.id.iv_gift);
            holder.mTvGiftName = convertView.findViewById(R.id.tv_gift_name);
            holder.mTvGiftPrice = convertView.findViewById(R.id.tv_gift_price);
            holder.mConstraint = convertView.findViewById(R.id.constrat_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (gift != null) {
            GlideAppUtil.loadImage(mContext, gift.getGiftStaticUrl(), holder.mIvGift);

            holder.mTvGiftName.setText(gift.getGiftName());

            if (gift.getGiftPriceType() == 1) {
                holder.mTvGiftPrice.setText(gift.getGiftPrice() + "??????");
            } else if (gift.getGiftPriceType() == 2) {
                holder.mTvGiftPrice.setText(gift.getGiftPrice() + "??????");
            }

            if (gift.isSelect()) {
                holder.mConstraint.setBackgroundResource(R.drawable.shape_room_gift_select);
                holder.mTvGiftName.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                holder.mTvGiftPrice.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                holder.mConstraint.setBackground(null);
                holder.mTvGiftName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                holder.mTvGiftPrice.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            }
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView mIvGift;
        private TextView mTvGiftName;
        private TextView mTvGiftPrice;
        private ConstraintLayout mConstraint;

    }
}

