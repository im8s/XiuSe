package com.xsvideoLive.www.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xsvideoLive.www.R
import com.xsvideoLive.www.callback.OnGiftCallback
import com.xsvideoLive.www.callback.OnGiftSendCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.ui.activity.mine.MyGoldAct
import com.xsvideoLive.www.mvp.ui.adapter.HomeRadioPageAdapter
import com.xsvideoLive.www.mvp.ui.adapter.RoomGiftSeatAdapter
import com.xsvideoLive.www.mvp.ui.fragment.studio.RoomGiftFragment
import com.xsvideoLive.www.mvp.ui.fragment.studio.RoomMyGiftFragment
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GiftAllResult
import com.xsvideoLive.www.net.bean.GiftViewSeat
import com.xsvideoLive.www.net.bean.RoomGiftResult
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils
import com.xsvideoLive.www.view.NoScrollViewPager
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomGiftView private constructor() : BottomSheetDialogFragment(), View.OnClickListener, OnGiftCallback {

    private var mView: View? = null

    private var mRcySeat: RecyclerView? = null

    private var mTvGift: TextView? = null

    private var mTvNoble: TextView? = null

    private var mTvMagic: TextView? = null

    private var mTvBackPack: TextView? = null

    private var mTvNobleName: TextView? = null

    private var mViewPager: NoScrollViewPager? = null

    private var mRoomGift: GiftAllResult? = null

    private var mRoomId: String? = null

    private var mSelfNoble: String? = null

    private var mGiftSeat: MutableList<GiftViewSeat> = ArrayList()

    private var mSeatAdapter: RoomGiftSeatAdapter? = null

    private var mGiveAway: GiftViewSeat? = null

    private var mFragments: MutableList<Fragment> = ArrayList()

    private var isTotal: Boolean? = false

    private var mCallback: OnGiftSendCallback? = null

    fun setOnGiftSendCallback(callback: OnGiftSendCallback) {
        mCallback = callback
    }

    companion object {
        fun newInstance(roomGift: GiftAllResult?, roomId: String?, selfNoble: String?): RoomGiftView {
            val roomGiftView = RoomGiftView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_GIFT, roomGift)
            args.putString(Constant.ROOM_ID, roomId)
            args.putString(Constant.SELF_NOBLE, selfNoble)
            roomGiftView.arguments = args
            return roomGiftView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
        mRoomGift = arguments?.getSerializable(Constant.ROOM_GIFT) as GiftAllResult?
        mRoomId = arguments?.getString(Constant.ROOM_ID)
        mSelfNoble = arguments?.getString(Constant.SELF_NOBLE)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.view_room_gift, container, false)
        initView()
        return mView
    }

    private fun initView() {
        mRcySeat = mView?.findViewById(R.id.rcy_seat)
        mTvGift = mView?.findViewById(R.id.tv_gift)
        mTvNoble = mView?.findViewById(R.id.tv_noble)
        mTvMagic = mView?.findViewById(R.id.tv_magic)
        mTvBackPack = mView?.findViewById(R.id.tv_backpack)
        mViewPager = mView?.findViewById(R.id.viewpager)
        mTvNobleName = mView?.findViewById(R.id.tv_noble_name)

        mTvGift?.setOnClickListener(this)
        mTvNoble?.setOnClickListener(this)
        mTvMagic?.setOnClickListener(this)
        mTvBackPack?.setOnClickListener(this)
        mTvNobleName?.setOnClickListener(this)

        initFragment()

        setNobleName(mSelfNoble)

    }

    private fun setNobleName(noble: String?) {
        when (noble) {
            "0" -> {
                mTvNobleName?.text = "????????????"
            }

            "1" -> {
                mTvNobleName?.text = "????????????"
            }

            "2" -> {
                mTvNobleName?.text = "????????????"
            }

            "3" -> {
                mTvNobleName?.text = "????????????"
            }

            "4" -> {
                mTvNobleName?.text = "????????????"
            }

            "5" -> {
                mTvNobleName?.text = "????????????"
            }

            "6" -> {
                mTvNobleName?.text = "????????????"

            }

            "7" -> {

                mTvNobleName?.text = "????????????"
            }

            else -> {
                mTvNobleName?.text = "????????????"
            }

        }
    }


    private fun initFragment() {

        mFragments.clear()

        val mRoomGiftFragment = RoomGiftFragment.newInstance(mRoomGift?.generals, 0)
        mRoomGiftFragment.setOnGiftCallback(this)

        val mRoomNobleFragment = RoomGiftFragment.newInstance(mRoomGift?.nobles, 1)
        mRoomNobleFragment.setOnGiftCallback(this)

        val mRoomMagicFragment = RoomGiftFragment.newInstance(mRoomGift?.magics, 2)
        mRoomMagicFragment.setOnGiftCallback(this)

        val mPackageFragment = RoomMyGiftFragment.newInstance(mRoomGift?.packages, 3)
        mPackageFragment.setOnGiftCallback(this)

        mFragments.add(mRoomGiftFragment)
        mFragments.add(mRoomNobleFragment)
        mFragments.add(mRoomMagicFragment)
        mFragments.add(mPackageFragment)

        val adapter = HomeRadioPageAdapter(childFragmentManager, mFragments)
        mViewPager!!.adapter = adapter


        mSeatAdapter = RoomGiftSeatAdapter(mGiftSeat)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRcySeat?.layoutManager = linearLayoutManager
        mRcySeat?.adapter = mSeatAdapter


        mSeatAdapter?.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
            val giftViewSeat = mGiftSeat[position]

            giftViewSeat.isSelect = !giftViewSeat.isSelect!!

            //?????????????????????
            if (giftViewSeat.isAllSelect!!) {

                for (seat in mGiftSeat) {
                    seat.isSelect = giftViewSeat.isSelect
                }

                isTotal = giftViewSeat.isSelect

            } else {   //????????????

                if (mGiftSeat[0].isAllSelect!!) {

                    var isAll = true

                    for (seat in mGiftSeat) {
                        if (!seat.isSelect!! && !seat.isAllSelect!!) {
                            isAll = false
                            break
                        }
                    }
                    mGiftSeat[0].isSelect = isAll
                    isTotal = isAll
                }
            }

            mSeatAdapter?.notifyDataSetChanged()

        }
    }


    override fun onStart() {
        super.onStart()
        //??????dialog??????
        val dialog = dialog as BottomSheetDialog?
        dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        //??????diglog????????????
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            //??????????????????LayoutParams??????
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getPeekHeight()
            //?????????????????????????????????????????????????????????????????????
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            //peekHeight????????????????????????
            behavior.peekHeight = getPeekHeight()
            // ?????????????????????
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isHideable = false
        }

    }

    override fun onResume() {
        super.onResume()
        mViewPager?.currentItem = 0
        mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
        mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
        mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

    }

    private fun getPeekHeight(): Int {
        //????????????????????????????????????3/4
        return dp2px(420f)
    }

    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_gift -> {
                mViewPager?.currentItem = 0
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

            }

            R.id.tv_noble -> {
                mViewPager?.currentItem = 1
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
            }

            R.id.tv_magic -> {
                mViewPager?.currentItem = 2
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

            }

            R.id.tv_backpack -> {
                mViewPager?.currentItem = 3
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            }

            R.id.tv_noble_name -> {
                if (mCallback != null) {
                    mCallback?.onNobleClick()
                }
            }
        }

    }


    fun setGiveAway(giftSeat: GiftViewSeat?, mainSeat: VoiceRoomSeatEntity?, seatInfo: MutableList<VoiceRoomSeatEntity>?) {
        mGiveAway = giftSeat

        setSeatAdapter(mainSeat, seatInfo)

    }


    fun setSeatAdapter(mainSeat: VoiceRoomSeatEntity?, seatInfo: MutableList<VoiceRoomSeatEntity>?) {

        var mSeat: MutableList<VoiceRoomSeatEntity> = ArrayList()
        if (mainSeat == null || seatInfo == null) return
        mSeat.add(mainSeat!!)
        mSeat.addAll(seatInfo!!)

        mGiftSeat.clear()


        //??????????????????????????????????????? ????????????
        for (i in mSeat.indices) {

            if (mSeat?.get(i).isUsed) {
                if (mSeat[i].userId != SPUtils.getInstance().userInfo.id) {
                    var seat: GiftViewSeat?
                    seat = if (i == 0) {
                        GiftViewSeat(mSeat[i].userId, mSeat[i].userName, mSeat[i].userAvatar, mSeat[i].userInfo.noble, index = i, isAllSelect = false, isRoom = true, isSelect = false,isSeat = true)
                    } else {
                        GiftViewSeat(mSeat[i].userId, mSeat[i].userName, mSeat[i].userAvatar, mSeat[i].userInfo.noble, index = i, isAllSelect = false, isRoom = false, isSelect = false,isSeat = true)
                    }

                    mGiftSeat.add(seat)
                }
            }
        }

        //???????????????????????????????????????????????????????????????
        if (mGiveAway == null) {

            val giftViewSeat = GiftViewSeat(null, null, null, null, null, isAllSelect = true, isRoom = false, isSelect = false, isSeat = true)
            mGiftSeat.add(0, giftViewSeat)

        } else {

            var isInSeat = false  //????????????????????????

            for (giftViewSeat in mGiftSeat) {
                if (giftViewSeat.userId == mGiveAway?.userId) {
                    isInSeat = true
                    giftViewSeat.isSelect = true
                    break
                }
            }

            if (isInSeat) { //??????????????????????????????
                val giftViewSeat = GiftViewSeat(null, null, null, null, null, isAllSelect = true, isRoom = false, isSelect = false,isSeat = true)
                mGiftSeat.add(0, giftViewSeat)
            } else {    //?????????????????????????????????????????????
                mGiveAway?.index = -1
                mGiveAway?.isSelect = true
                mGiveAway?.isSeat = false
                mGiftSeat.clear()
                mGiftSeat.add(mGiveAway!!)

            }

        }

        mSeatAdapter?.data = mGiftSeat
        mSeatAdapter?.notifyDataSetChanged()

    }

    override fun setGiftResult(result: RoomGiftResult?, num: Int, mFragmentIndex: Int) {
        var select: MutableList<GiftViewSeat> = ArrayList()

        for (giftViewSeat in mGiftSeat) {
            if (!giftViewSeat.isAllSelect!! && giftViewSeat.isSelect!!) {
                select.add(giftViewSeat)
            }
        }

        if (select.isNotEmpty()) {

            if (mFragmentIndex == 3) {
                sendGift(mRoomId, result, select, 2, num, mFragmentIndex, isTotal)
            } else {
                sendGift(mRoomId, result, select, 1, num, mFragmentIndex, isTotal)
            }

        } else {
            ToastUtils.showShort("????????????????????????????????????")
        }

    }

    override fun rechargeEntry() {
        ActStartUtils.startAct(activity, MyGoldAct::class.java)
        dismiss()
    }

    private fun sendGift(roomId: String?, giftResult: RoomGiftResult?, userIds: MutableList<GiftViewSeat>?, type: Int, number: Int, mFragmentIndex: Int, isTotal: Boolean?) {


        var userIdList: MutableList<String> = ArrayList()
        userIds?.forEach {
            userIdList.add(it.userId!!)
        }


        HttpClient.getApi().giveGift(roomId, giftResult?.id, userIdList, type, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        if (bean == 1) {
                            if (mFragmentIndex == 3) {
                                (mFragments[mFragmentIndex] as RoomMyGiftFragment).getPackageGift()
                            } else {
                                (mFragments[mFragmentIndex] as RoomGiftFragment).getGoldAndChili()
                            }
                        }

                        if (mCallback != null) {
                            mCallback?.onSendGiftResult(bean, userIds, giftResult, number, isTotal)
                        }

                    }

                    override fun error(msg: String?) {

                    }

                })

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onGiftDismiss()
    }

}