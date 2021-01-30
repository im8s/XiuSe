package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.utils.AppUtils
import kotlinx.android.synthetic.main.act_about.*

class AboutUsAct : BaseActivity(), TitleBarClickCallback {


    override fun setLayoutId(): Int {
        return R.layout.act_about
    }

    override fun initView(savedInstanceState: Bundle?) {
        title_bar.setOnClickCallback(this)
        tv_name.text = "v "+AppUtils.getAppVersionName(this)
    }


    override fun titleLeftClick() {
        finish()
    }


    override fun titleRightClick(status: Int) {
    }


}