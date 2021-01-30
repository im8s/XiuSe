package com.xsvideoLive.www.mvp.ui.activity.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.WebContract
import com.xsvideoLive.www.mvp.presenter.WebPresenter
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.ToastUtils

class WebActivity : BaseMvpActivity<WebPresenter>(), WebContract.View, TitleBarClickCallback {

//    @BindView(R.id.title_bar)
//    lateinit var titleBar: TitleBarView


    @BindView(R.id.iv_black)
    lateinit var mIvBlack: ImageView

    @BindView(R.id.iv_finish)
    lateinit var mIvFinish: ImageView

    @BindView(R.id.tv_title_name)
    lateinit var mTitleName: TextView


    @BindView(R.id.webview)
    lateinit var mWebView: WebView

    var mUrl: String? = ""
    var mWebTitle: String? = ""

    override fun setLayoutId(): Int = R.layout.activity_web

    override fun createPresenter(): WebPresenter = WebPresenter()

    @SuppressLint("JavascriptInterface")
    override fun initView(savedInstanceState: Bundle?) {


        mPresenter.attachView(this)
        mPresenter.initWebView(mWebView,mTitleName)

        mUrl = intent.extras.getString(Constant.WEB_URL, "")
        mWebTitle = intent.extras.getString(Constant.WEB_TITLE, "")


        mWebView.loadUrl(mUrl);

        mWebView.addJavascriptInterface(this, "user")

        mTitleName.text = mWebTitle

    }

    @JavascriptInterface
    fun userId(id: String?) {
        if(!TextUtils.isEmpty(id)) {
            val bundle = Bundle()
            bundle.putString(Constant.USER_ID, id)
            ActStartUtils.startAct(this, HomePageAct::class.java, bundle)
        }
    }

    @OnClick(R.id.iv_black, R.id.iv_finish)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_black -> {
                val canGoBack = mWebView.canGoBack()
                if (canGoBack) {
                    mWebView.goBack()
                } else {
                    finish()
                }
            }

            R.id.iv_finish -> {
                finish()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            if (mWebView.canGoBack()) {
                mWebView.goBack()
            } else {
                finish()
            }

            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }


    override fun titleLeftClick() {

        mWebView.goBack()

        finish()
    }

    override fun titleRightClick(status: Int) {
    }


}
