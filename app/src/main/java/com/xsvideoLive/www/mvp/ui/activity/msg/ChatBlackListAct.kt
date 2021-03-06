package com.xsvideoLive.www.mvp.ui.activity.msg

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.ChatBlackContract
import com.xsvideoLive.www.mvp.presenter.ChatBlackPresenter
import com.xsvideoLive.www.mvp.ui.activity.mine.HomePageAct
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.GlideAppUtil
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils
import com.xsvideoLive.www.view.CircleImageView
import com.xsvideoLive.www.view.TipsDialog
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo

class ChatBlackListAct : BaseMvpActivity<ChatBlackPresenter>(), ChatBlackContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.civ_user_avatar)
    lateinit var mCivUserAvatar: CircleImageView

    @BindView(R.id.tv_name)
    lateinit var mTvName: TextView

    @BindView(R.id.tv_lh)
    lateinit var mTvLh: TextView

    private var isBlack = -1;

    private var mChatInfo: ChatInfo? = null

    override fun setLayoutId(): Int = R.layout.activity_chat_black_list

    override fun createPresenter(): ChatBlackPresenter {
        return ChatBlackPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        mChatInfo = intent.extras.getSerializable(Constant.CHAT_INFO) as ChatInfo?
        mPresenter.isBlack(mChatInfo?.id)

        initData(mChatInfo)
    }

    private fun initData(chatInfo: ChatInfo?) {
        GlideAppUtil.loadImage(this, chatInfo?.iconUrlList!![0].toString(), mCivUserAvatar)
        mTvName.text = chatInfo.chatName
    }

    @OnClick(R.id.cl_user_info, R.id.tv_jb, R.id.tv_lh)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.cl_user_info -> {
                val bundle = Bundle()
                bundle.putString(Constant.USER_ID, mChatInfo?.id)
                ActStartUtils.startAct(this, HomePageAct::class.java, bundle)
            }

            R.id.tv_jb -> {
                var bundle = Bundle()
                bundle.putString(Constant.REPOTR_TYPE,Constant.REPORT_TYPE_USER)
                bundle.putString(Constant.REPORT_ID,mChatInfo?.id)
                ActStartUtils.startAct(this,ReportAct::class.java,bundle)

            }

            R.id.tv_lh -> {

                if (isBlack == 1) {
                    TipsDialog.createDialog(this, R.layout.dialog_chat_black)
                            .bindClick(R.id.tv_cancel) { v, dialog ->
                                dialog.dismiss()
                            }
                            .bindClick(R.id.tv_confirm) { v, dialog ->
                                mPresenter.addBlackList("2", SPUtils.getInstance().userInfo.id, mChatInfo?.id)
                                dialog.dismiss()
                            }
                            .show()

                } else if (isBlack == 0) {
                    mPresenter.removeBlackList("2", SPUtils.getInstance().userInfo.id, mChatInfo?.id)
                }
            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onBlackSuccess(status: String?) {
        if (status == "1") {
            isBlack = 1
            mTvLh.text = "???????????????"
        } else if (status == "0") {
            isBlack = 0
            mTvLh.text = "???????????????"
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?) {
        if (status == "1") {
            if (resultStatus == 1) {
                isBlack = 0
                mTvLh.text = "???????????????"
                ToastUtils.showShort("?????????????????????")
                addOrRemoveBlack(1,userId!!)

            } else {
                ToastUtils.showShort("?????????????????????")
            }
        } else if (status == "2") {
            if (resultStatus == 1) {
                isBlack = 1
                mTvLh.text = "???????????????"
                ToastUtils.showShort("?????????????????????")
                addOrRemoveBlack(0,userId!!)
            } else {
                ToastUtils.showShort("?????????????????????")
            }
        }
    }

    private fun addOrRemoveBlack(status: Int,userId:String) {
        val friendshipManager = V2TIMManager.getFriendshipManager()

        var userList = ArrayList<String>()
        userList.add(userId)

        if (status == 1) {
            friendshipManager.addToBlackList(userList, null)
        } else if (status == 0) {
            friendshipManager.deleteFromBlackList(userList,null)
        }
    }
}