package com.xsvideoLive.www.mvp.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.ui.activity.mine.HeadgearAct;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarEmptyView extends RelativeLayout {


    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_give_away)
    TextView tvGiveAway;
    @BindView(R.id.rl_give_away)
    RelativeLayout rlGiveAway;

    private boolean isSelf;

    private MineReslut mResult;

    public CarEmptyView(Context context) {
        this(context, null);
    }

    public CarEmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.car_empty, this, true);
        ButterKnife.bind(view);
    }

    public void setMineResult(MineReslut result) {

        this.mResult = result;

        if (result.getId().equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
            tvGiveAway.setText("立即购买");
        } else {
            isSelf = false;
            tvGiveAway.setText("送TA一部");
        }
    }

    @OnClick(R.id.rl_give_away)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MINE_RESLUT,mResult);
        ActStartUtils.startAct(getContext(), HeadgearAct.class,bundle);
    }
}
