package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.mvp.contract.GoldCoinGiftContract
import com.xsvideoLive.www.mvp.presenter.GoldCoinGiftPresenter
import kotlinx.android.synthetic.main.activity_gold_coin_gift.*
import kotlinx.android.synthetic.main.activity_my_gift.title_bar

class GoldCoinGiftAct : BaseMvpActivity<GoldCoinGiftPresenter>(), GoldCoinGiftContract.View, TitleBarClickCallback {


    override fun createPresenter(): GoldCoinGiftPresenter = GoldCoinGiftPresenter()

    override fun setLayoutId(): Int = R.layout.activity_gold_coin_gift;

    override fun initView(savedInstanceState: Bundle?) {
        title_bar.setOnClickCallback(this)
        this.mPresenter.attachView(this)
        mPresenter.initFragment(index = index, viewPager = viewpager)

    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }
}
