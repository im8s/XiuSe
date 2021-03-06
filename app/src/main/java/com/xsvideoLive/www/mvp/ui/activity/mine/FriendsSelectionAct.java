package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.OnFriendsSelectCallback;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.FriendsSelectionContract;
import com.xsvideoLive.www.mvp.presenter.FriendsSelectionPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MyFollowResult;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.TipsDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;


public class FriendsSelectionAct extends BaseMvpActivity<FriendsSelectionPresenter> implements FriendsSelectionContract.View, TitleBarClickCallback, OnFriendsSelectCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ns_view_pager)
    ViewPager nsViewPager;

    private Decks decks;

    @Override
    public int setLayoutId() {
        return R.layout.activity_friends_selection;
    }

    @Override
    protected FriendsSelectionPresenter createPresenter() {
        return new FriendsSelectionPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();

    }

    private void init() {
        mPresenter.attachView(this);
        mPresenter.initFragment(magicIndicator,nsViewPager,this);
        mTitleBar.setOnClickCallback(this);
        Bundle extras = getIntent().getExtras();
        decks = (Decks) extras.getSerializable(Constant.DECKS);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void onFriendsSelect(MyFollowResult result) {

        if (decks == null || result == null) return;

        TipsDialog.createDialog(this, R.layout.dialog_headwear_store)
                .setText(R.id.tv_title, "????????????")
                .setText(R.id.tv_gift_info, "?????? " + result.getUserName() + " " + decks.getDeckName() + "(" + decks.getUseDay() + "???)")
                .setText(R.id.tv_price, decks.getCostNumber() + (decks.getCostType() == 1 ? "??????" : decks.getCostType() == 2 ? "??????" : ""))
                .bindClick(R.id.tv_cancel, (v, dialog) -> {
                    dialog.dismiss();
                })
                .bindClick(R.id.tv_comit, (v, dialog) -> {
                    mPresenter.giveAway(decks.getId(), result.getUserId());

                })
                .show();

    }

    @Override
    public void onFriendsClick(MyFollowResult result) {

    }

    @Override
    public void giveAwaySuccess(Integer status) {
        if (status == 1) {
            ToastUtils.showShort("????????????");
            finish();
        } else {
            ToastUtils.showShort("????????????");
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}