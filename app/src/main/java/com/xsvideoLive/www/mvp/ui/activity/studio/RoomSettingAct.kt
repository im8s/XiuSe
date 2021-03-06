package com.xsvideoLive.www.mvp.ui.activity.studio

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.RoomInPutCallback
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.EventConstant
import com.xsvideoLive.www.constant.SpConstant
import com.xsvideoLive.www.mvp.contract.RoomSettingContract
import com.xsvideoLive.www.mvp.presenter.RoomSettingPresenter
import com.xsvideoLive.www.mvp.ui.view.RoomInPutView
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.*
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.EventBusUtil
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils
import com.xsvideoLive.www.view.TipsDialog

class RoomSettingAct : BaseMvpActivity<RoomSettingPresenter>(), RoomSettingContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.ll_room_name)
    lateinit var mLlRoomName: LinearLayout

    @BindView(R.id.tv_room_name)
    lateinit var mTvRoomName: TextView

    @BindView(R.id.ll_room_psd)
    lateinit var mLlRoomPsd: LinearLayout

    @BindView(R.id.tv_room_psd)
    lateinit var mTvRoomPsd: TextView

    @BindView(R.id.switch_room_lock)
    lateinit var mSwitchRoomLock: SwitchCompat

    @BindView(R.id.ll_room_type)
    lateinit var mLlRoomType: LinearLayout

    @BindView(R.id.ll_manager)
    lateinit var mLlmanager: LinearLayout

    @BindView(R.id.ll_room_bg)
    lateinit var mLlRoomBg: LinearLayout

    @BindView(R.id.tv_type_name)
    lateinit var mTvTypeName: TextView

    @BindView(R.id.switch_gift)
    lateinit var mSwitchGift: SwitchCompat

    @BindView(R.id.switch_public)
    lateinit var mSwitchPublic: SwitchCompat

    @BindView(R.id.swtich_pick)
    lateinit var mSwitchPick: SwitchCompat

    private var mRoomInfo: CreateRoomResult? = null   //???????????????????????????

    private val mRoomInputView by lazy { RoomInPutView(this) }

    private var START_ROOM_TYPE = 1

    override fun setLayoutId(): Int = R.layout.activity_room_setting

    override fun createPresenter(): RoomSettingPresenter {
        return RoomSettingPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        initData()
        initListent()
    }

    private fun initListent() {

        mSwitchRoomLock.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {

                if (isChecked) {
                    mRoomInputView.setDialogCallback("????????????", if (mRoomInfo?.roomLock == 1) mRoomInfo?.roomPassword else "", 2, object : RoomInPutCallback {
                        override fun onInPutResult(msg: String?) {
                            if (!TextUtils.isEmpty(msg)) {
                                mPresenter.modifyRoomPsw(mRoomInfo?.id!!, "1", msg)
                            } else {
                                mSwitchRoomLock.isChecked = false
                                ToastUtils.showShort("???????????????")
                            }
                        }

                        override fun onInputDismiss() {
                            mSwitchRoomLock.isChecked = false
                        }

                    })

                    mRoomInputView.show()
                } else {
                    mPresenter.modifyRoomPsw(mRoomInfo?.id!!, "0", "")
                }
            }

        }

        mSwitchGift.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (!isChecked) {
                    TipsDialog.createDialog(this@RoomSettingAct, R.layout.dialog_gift_switch)
                            .setText(R.id.tv_tis1, "???????????????????????????????????????????????????")
                            .setText(R.id.tv_tis2, "??????????????????????????????")
                            .bindClick(R.id.tv_cancel) { v, dialog ->
                                mSwitchGift.isChecked = true
                            }
                            .bindClick(R.id.tv_confirm) { v, dialog ->
                                EventBusUtil.sendEvent(Event(EventConstant.ROOM_GIFT, isChecked))
                            }
                            .show()

                } else {
                    EventBusUtil.sendEvent(Event(EventConstant.ROOM_GIFT, isChecked))
                }
            }
        }

        mSwitchPublic.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (!isChecked) {
                    TipsDialog.createDialog(this@RoomSettingAct, R.layout.dialog_gift_switch)
                            .setText(R.id.tv_tis1, "????????????????????????????????????????????????")
                            .setText(R.id.tv_tis2, "????????????????????????")
                            .bindClick(R.id.tv_cancel) { v, dialog ->

                                mSwitchPublic.isChecked = true

                            }
                            .bindClick(R.id.tv_confirm) { v, dialog ->

                                mPresenter.setGpSwitch(mRoomInfo?.id!!, false)


                            }
                            .show()

                } else {
                    mPresenter.setGpSwitch(mRoomInfo?.id!!, true)
                }
            }
        }

        mSwitchPick.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                if (isChecked) {
                    TipsDialog.createDialog(this@RoomSettingAct, R.layout.dialog_gift_switch)
                            .setText(R.id.tv_tis1, "????????????????????????????????????")
                            .setText(R.id.tv_tis2, "??????????????????")
                            .bindClick(R.id.tv_cancel) { v, dialog ->

                                mSwitchPick.isChecked = false

                            }
                            .bindClick(R.id.tv_confirm) { v, dialog ->

//                                mPresenter.setGpSwitch(mRoomInfo?.id!!, false)
                                mPresenter.pickSwitch(mRoomInfo?.id!!, 1, isChecked)


                            }
                            .show()

                } else {
                    mPresenter.pickSwitch(mRoomInfo?.id!!, 0, isChecked)
                }
            }

        }

    }


    private fun initData() {

        mRoomInfo = intent.extras.getSerializable(Constant.ROOM_INFO) as CreateRoomResult?

        if (mRoomInfo?.userKind == 1) {
            mLlmanager.visibility = View.VISIBLE
            mLlRoomBg.visibility = View.VISIBLE
        } else {
            mLlmanager.visibility = View.GONE
            mLlRoomBg.visibility = View.GONE
        }


        setRoomInfo(mRoomInfo)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == START_ROOM_TYPE && resultCode == RESULT_OK) {
            val extras = data?.extras
            val mRoomType: HomeSortResult = extras?.getSerializable(Constant.ROOM_TYPE) as HomeSortResult
            mTvTypeName.text = mRoomType.roomTypeName

            var roomTypeEntity = RoomTypeEntity(mRoomType.id, mRoomType.roomTypeName, mRoomType.roomTypeColor)

            EventBusUtil.sendEvent(Event(EventConstant.ROOM_TYPE, roomTypeEntity))

        }
    }

    private fun setRoomInfo(roomInfo: CreateRoomResult?) {
        mTvRoomName.text = roomInfo?.roomName

        if (roomInfo?.roomLock == 1) {
            mSwitchRoomLock.isChecked = true
            mLlRoomPsd.visibility = View.VISIBLE
            mTvRoomPsd.text = roomInfo?.roomPassword
        } else {
            mSwitchRoomLock.isChecked = false
            mLlRoomPsd.visibility = View.GONE
            mTvRoomPsd.text = ""
        }

        if(!roomInfo?.roomTypeColor.isNullOrEmpty()) {
            mTvTypeName.setTextColor(Color.parseColor(roomInfo?.roomTypeColor))
        }

        mTvTypeName.text = roomInfo?.roomNoticeTitle

        mSwitchGift.isChecked = SPUtils.getInstance().getBoolean(SpConstant.ROOM_IS_PLAY, true)

        mSwitchPublic.isChecked = roomInfo?.roomPublic == 1

        mSwitchPick.isChecked = roomInfo?.roomExclusion == 1
    }

    @OnClick(R.id.ll_room_name, R.id.ll_room_type, R.id.ll_manager, R.id.ll_black_list, R.id.ll_room_bg)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_room_name -> {
                mRoomInputView.setDialogCallback("?????????", mRoomInfo?.roomName, 1, object : RoomInPutCallback {
                    override fun onInPutResult(msg: String?) {

                        if (TextUtils.isEmpty(msg)) {
                            ToastUtils.showShort("??????????????????")
                        } else {
                            mPresenter?.modifyRoomName(mRoomInfo?.id, msg)
                        }

                    }

                    override fun onInputDismiss() {

                    }

                })

                mRoomInputView.show()

            }

            R.id.ll_room_type -> {
                var bundle = Bundle()
                bundle.putString(Constant.ROOM_TYPE_ID, mRoomInfo?.roomTypeId)
                bundle.putString(Constant.ROOM_ID, mRoomInfo?.id)
                ActStartUtils.startForAct(this, RoomTypeAct::class.java, bundle, START_ROOM_TYPE)
            }

            R.id.ll_manager -> {
                var bundle = Bundle()
                bundle.putString(Constant.ROOM_ID, mRoomInfo?.id)
                ActStartUtils.startAct(this, RoomManagerAct::class.java, bundle)
            }

            R.id.ll_black_list -> {
                var bundle = Bundle()
                bundle.putString(Constant.ROOM_ID, mRoomInfo?.id)
                ActStartUtils.startAct(this, BlackListAct::class.java, bundle)
            }

            R.id.ll_room_bg -> {
                var bundle = Bundle()
                bundle.putString(Constant.ROOM_ID, mRoomInfo?.id)
                ActStartUtils.startAct(this, RoomBackgroundAct::class.java, bundle)
            }
        }
    }

    override fun roomNameSuccess(status: Int?, roomName: String?) {
        if (status == 1) {
            ToastUtils.showShort("?????????????????????")
            mTvRoomName.text = roomName
            mRoomInfo?.roomName = roomName
            EventBusUtil.sendEvent(Event(EventConstant.ROOM_NAME, roomName))
        } else {
            ToastUtils.showShort("????????????")
        }
    }

    override fun roomPswSuccess(status: Int?, isLock: Boolean, psw: String?) {
        if (isLock) {

            mSwitchRoomLock.isChecked = status == 1
            if (status == 1) {
                mRoomInfo?.roomLock = 1
                mRoomInfo?.roomPassword = psw
                var mRoomPsd = RoomPsd(true, psw)
                ToastUtils.showShort("??????????????????")
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PSD, mRoomPsd))
                mLlRoomPsd.visibility = View.VISIBLE
                mTvRoomPsd.text = psw
            } else {
                ToastUtils.showShort("??????????????????")
            }


        } else {
            mSwitchRoomLock.isChecked = status != 1
            if (status == 1) {
                mRoomInfo?.roomLock = 0
                mRoomInfo?.roomPassword = ""
                var mRoomPsd = RoomPsd(false, "")
                ToastUtils.showShort("??????????????????")
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PSD, mRoomPsd))
                mLlRoomPsd.visibility = View.GONE
                mTvRoomPsd.text = ""
            } else {
                ToastUtils.showShort("??????????????????")
            }
        }
    }

    override fun isOpenGpSuccess(status: Int?, isOpen: Boolean?) {
        //????????????
        if (isOpen!!) {
            if (status == 1) {
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PUBLIC, true))
            } else {
                mSwitchGift.isChecked = false
                ToastUtils.showShort("??????????????????")
            }
        } else {
            if (status == 1) {
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PUBLIC, false))
            } else {
                mSwitchGift.isChecked = true
                ToastUtils.showShort("??????????????????")
            }
        }
    }

    override fun onPickSwitch(status: Int?, isPick: Boolean?) {
        if (isPick!!) {
            if (status == 1) {
                mSwitchPick.isChecked = true
                ToastUtils.showShort("????????????????????????")
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PICK, true))
            } else {
                mSwitchPick.isChecked = false
                ToastUtils.showShort("????????????????????????")
            }
        } else {
            if (status == 1) {
                ToastUtils.showShort("????????????????????????")
                mSwitchPick.isChecked = false
                EventBusUtil.sendEvent(Event(EventConstant.ROOM_PICK, false))
            } else {
                mSwitchPick.isChecked = true
                ToastUtils.showShort("????????????????????????")
            }
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}
