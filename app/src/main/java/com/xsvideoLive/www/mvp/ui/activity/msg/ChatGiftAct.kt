package com.xsvideoLive.www.mvp.ui.activity.msg

import android.os.Bundle
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.mvp.contract.ChatGiftContract
import com.xsvideoLive.www.mvp.presenter.ChatGiftPresenter

class ChatGiftAct : BaseMvpActivity<ChatGiftPresenter>(), ChatGiftContract.View {

    override fun setLayoutId(): Int = R.layout.activity_chat_gift

    override fun createPresenter(): ChatGiftPresenter = ChatGiftPresenter()


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun isFull(): Boolean = false
}
