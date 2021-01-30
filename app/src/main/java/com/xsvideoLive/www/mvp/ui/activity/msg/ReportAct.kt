package com.xsvideoLive.www.mvp.ui.activity.msg

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.ReportViewCallback
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.ReportContract
import com.xsvideoLive.www.mvp.presenter.ReportPresenter
import com.xsvideoLive.www.mvp.ui.adapter.ReportTypeAdapter
import com.xsvideoLive.www.mvp.ui.view.ReportFooterView
import com.xsvideoLive.www.mvp.ui.view.ReportHeaderView
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.ReportFoot
import com.xsvideoLive.www.net.bean.ReportType
import com.xsvideoLive.www.utils.FileUtils
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils


class ReportAct : BaseMvpActivity<ReportPresenter>(), ReportContract.View, ReportViewCallback, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.rcy_report_type)
    lateinit var mRcyReportType: RecyclerView

    companion object {
        val RC_CHOOSE_PHOTO = 1

        val RC_PHOTO_PREVIEW = 2
    }

    private var mReportType: String? = null

    private var mReportId: String? = null

    private var mAdapter: ReportTypeAdapter? = null

    private var mReport: ReportType? = null

    private var reportFooterView: ReportFooterView? = null

    override fun setLayoutId(): Int = R.layout.activity_report

    override fun createPresenter(): ReportPresenter {
        return ReportPresenter()
    }

    override fun isFull(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {
        //设置软键盘不被遮挡
//        val win: Window = window
//        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        mPresenter.getReportType()
        mReportType = intent.extras.getString(Constant.REPOTR_TYPE)
        mReportId = intent.extras.getString(Constant.REPORT_ID)
        mAdapter = ReportTypeAdapter()
        mAdapter?.setHeaderView(ReportHeaderView(this))

        reportFooterView = ReportFooterView(this)

        reportFooterView?.setOnReportViewCallback(this)

        mAdapter?.addFooterView(reportFooterView!!)

        mRcyReportType.layoutManager = LinearLayoutManager(this)
        mRcyReportType.adapter = mAdapter

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter?.getItem(position)
            if (mReport == null) {
                item?.isSelect = true
                mReport = item
            } else {
                mReport?.isSelect = false
                item?.isSelect = true
                mReport = item
            }

            mAdapter?.notifyDataSetChanged()
        }

    }

    override fun onReproTypeSuccess(reportList: MutableList<ReportType>?) {
        mAdapter?.data = reportList!!
        mAdapter?.notifyDataSetChanged()
    }

    override fun onReportSuccess(status: String?) {
        if (status == "1") {
            ToastUtils.showShort("举报成功")
            finish()
        } else {
            ToastUtils.showShort("举报失败")
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            reportFooterView?.addPhoto(BGAPhotoPickerActivity.getSelectedPhotos(data))
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            reportFooterView?.setPhoto(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data))
        }
    }

    override fun reportResult(result: ReportFoot) {
        if (mReport == null) {
            ToastUtils.showShort("请选择举报类型")
            return
        }

        if (result.photoPath == null || result.photoPath?.size!! <= 0) {
            ToastUtils.showShort("请至少上传一张图片")
            return
        }

        if (TextUtils.isEmpty(result.content)) {
            ToastUtils.showShort("请输入相关描述")
            return
        }

        var phtotString: MutableList<String> = ArrayList()
        for (photo in result.photoPath!!) {
            val qualityCompress = FileUtils.qualityCompress(photo)
            phtotString.add(qualityCompress)
        }

        mPresenter.report(phtotString, SPUtils.getInstance().userInfo.id, mReportType, mReport?.id, mReportId, result.content)


    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}