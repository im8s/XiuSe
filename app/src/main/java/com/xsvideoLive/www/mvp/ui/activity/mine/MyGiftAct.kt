package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import android.view.View
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.mvp.contract.MyGiftContract
import com.xsvideoLive.www.mvp.presenter.MyGiftPresenter
import com.xsvideoLive.www.utils.ActStartUtils
import kotlinx.android.synthetic.main.activity_my_gift.*

class MyGiftAct : BaseMvpActivity<MyGiftPresenter>(), MyGiftContract.View, TitleBarClickCallback, View.OnClickListener {


    override fun setLayoutId(): Int = R.layout.activity_my_gift

    override fun createPresenter(): MyGiftPresenter = MyGiftPresenter();

    override fun initView(savedInstanceState: Bundle?) {
        init()
    }

    fun init() {
        title_bar.setOnClickCallback(this)
        rl_give_away.setOnClickListener(this)
        rl_income.setOnClickListener(this)
    }


    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.rl_income -> {

                ActStartUtils.startAct(this@MyGiftAct, GoldCoinGiftAct::class.java)

            }

            R.id.rl_give_away -> {
                ActStartUtils.startAct(this@MyGiftAct, OutlayGiftAct::class.java)
            }

        }

    }

}
