package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.EventConstant
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.Event
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.SPUtils

class BindPhoneAct : BaseActivity(), TitleBarClickCallback {
    
    @BindView(R.id.title_bar)
    lateinit var mTitleBar:TitleBarView

    @BindView(R.id.tv_phone_num)
    lateinit var mTvPhoneNum:TextView

    private val mUserInfo by lazy { SPUtils.getInstance().userInfo }

    override fun setLayoutId(): Int = R.layout.activity_bind_phone

    override fun initView(savedInstanceState: Bundle?) {
        init();
    }

    private fun init() {

        mTitleBar.setOnClickCallback(this)

       var  phoneNumber =  mUserInfo.userPhone

       var  maskNumber = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length)

        mTvPhoneNum.text = maskNumber

    }

    @OnClick(R.id.tv_change)
    fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_change -> {
                ActStartUtils.startAct(this,PhoneTestAct::class.java)
            }
        }

    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun isRegisterEventBus(): Boolean = true

    override fun receiveEvent(event: Event<*>?) {
        if (event != null && event.code == EventConstant.CHANGE_PHONE_SUCCESS) {
            finish()
        }
    }


}