package com.xsvideoLive.www.mvp.ui.activity.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.URLConstant
import com.xsvideoLive.www.mvp.contract.NobleWebContract
import com.xsvideoLive.www.mvp.presenter.NobleWebPresenter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.NobleListBean
import com.xsvideoLive.www.net.bean.NobleResult
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.ToastUtils

class NobleWebActivity : BaseMvpActivity<NobleWebPresenter>(), NobleWebContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var titleBar: TitleBarView

    @BindView(R.id.webview)
    lateinit var mWebView: WebView

    @BindView(R.id.tv_frist_open)
    lateinit var mTvFristOpen: TextView

    @BindView(R.id.tv_renew)
    lateinit var mTvRenew: TextView

    var mUrl: String? = null

    private var mResults: MutableList<NobleResult>? = null

    private var result: NobleResult? = null

    override fun setLayoutId(): Int = R.layout.activity_noble_web

    override fun createPresenter(): NobleWebPresenter = NobleWebPresenter()

    @SuppressLint("JavascriptInterface")
    override fun initView(savedInstanceState: Bundle?) {
        titleBar.setOnClickCallback(this)
        mPresenter.attachView(this)
        mPresenter.initWebView(mWebView)

//        mUrl = intent.extras.getString(Constant.WEB_URL,"")
//
//        if (!TextUtils.isEmpty(mUrl)) {
//
//            mWebView.loadUrl(mUrl);
//        } else {
//            mWebView.loadUrl("file:///android_asset/my_privilege/index.html")
            mWebView.loadUrl(URLConstant.NOBLE_URL)
//        }



        mWebView.addJavascriptInterface(this, "noble")
        mPresenter.getNobleList()

    }

    @JavascriptInterface
    fun selectNoble(msg: String?) {
        if (mResults!!.isNotEmpty()) {
            for (result in mResults!!) {

                if (result.id == msg) {
                    if (this.result != null) this.result?.isSelect = false
                    this@NobleWebActivity.result = result
                    this.result?.isSelect = true
                    break
                }
            }
            setNobleResult(result)
        }
    }


    override fun nobleSuccess(results: MutableList<NobleResult>?) {
        mResults = results
        for (result in mResults!!) {
            if (result.id == "3") {
                if (this.result != null) this.result?.isSelect = false
                this@NobleWebActivity.result = result
                this.result?.isSelect = true
                break
            }
        }


        setNobleResult(result)
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    private fun setNobleResult(nobleResult: NobleResult?) {

        runOnUiThread {
            mTvFristOpen.text = nobleResult?.rmbPrice.toString()
            mTvRenew.text = nobleResult?.againRmbPrice.toString()
        }
    }

    @OnClick(R.id.rl_open)
    fun onClick(v: View) {
        when (v.id) {
            R.id.rl_open -> {
                var nobleResult = NobleListBean()
                nobleResult.result = mResults
                var bundle = Bundle()
                bundle.putSerializable(Constant.NOBLE_LIST, nobleResult)
                ActStartUtils.startAct(this, NobleOrderAct::class.java, bundle)
            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }


}
