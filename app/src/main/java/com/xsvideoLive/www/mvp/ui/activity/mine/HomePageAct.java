package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.appbar.AppBarLayout;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.AppBarStateChangeListener;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.CacheConstant;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.helper.EnterRoomHelp;
import com.xsvideoLive.www.mvp.contract.HomePageConract;
import com.xsvideoLive.www.mvp.presenter.HomePagePresenter;
import com.xsvideoLive.www.mvp.ui.activity.msg.ChatActivity;
import com.xsvideoLive.www.mvp.ui.activity.msg.ReportAct;
import com.xsvideoLive.www.mvp.ui.view.AvatarView;
import com.xsvideoLive.www.mvp.ui.view.CharmLevelView;
import com.xsvideoLive.www.mvp.ui.view.ConstellationView;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.mvp.ui.view.UserLevelView;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.GlideAppUtil;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.utils.UserManagerUtils;
import com.xsvideoLive.www.view.TipsDialog;
import com.opensource.svgaplayer.SVGAImageView;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMFriendshipManager;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomePageAct extends BaseMvpActivity<HomePagePresenter> implements HomePageConract.View, TitleBarClickCallback {

    private static final String TAG = "HomePageAct";
    @BindView(R.id.appbar_title)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.svga)
    SVGAImageView mSvga;
    @BindView(R.id.main_backdrop)
    ImageView mMainBackdrop;
    @BindView(R.id.av_avatar)
    AvatarView mAvatar;
    @BindView(R.id.tv_nick_name)
    TextView mNickName;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.ulv)
    UserLevelView mUserLv;
    @BindView(R.id.clv)
    CharmLevelView mCharmLv;
    @BindView(R.id.tv_start)
    ConstellationView mTvStart;
    @BindView(R.id.tv_attention)
    TextView mTvAttention;
    @BindView(R.id.tv_fans_nbum)
    TextView mTvFansNum;
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tv_heed)
    TextView tvHeed;
    @BindView(R.id.ll_attention)
    LinearLayout llAttention;
    @BindView(R.id.rl_attention)
    RelativeLayout rlAttention;
    @BindView(R.id.ll_room)
    LinearLayout llRoom;
    @BindView(R.id.iv_lh)
    ImageView mIvLh;
    @BindView(R.id.realtime)
    RealtimeBlurView mRealtime;

    private String title = "";

    private String mRoomId = "";

    private String ownerRoomId = "";

    private String userId;

    private boolean isSelf = false;

    private boolean isFollow = false;

    private MineReslut mResult;

    private boolean isFrist;

    private int isBlack = 0;


    @Override
    public int setLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected HomePagePresenter createPresenter() {
        return new HomePagePresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mPresenter.init();

    }

    @Override
    public void init() {

        Bundle extras = getIntent().getExtras();
        userId = extras.getString(Constant.USER_ID);
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        String myId = userInfo.getId();
        if (userId.equals(myId)) {
            isSelf = true;
            llAttention.setVisibility(View.GONE);
            llRoom.setVisibility(View.GONE);
        } else {
            isSelf = false;
            mPresenter.queryFollow(userId);
            llRoom.setVisibility(View.VISIBLE);
            mPresenter.isBlack(userId);
        }


        mTitleBar.setOnClickCallback(this);
        //????????????????????????
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {

                    //????????????
                    mTitleBar.setLeftImage(R.mipmap.icon_my_home_dynamic_back);
                    if (isSelf) {
                        mTitleBar.setR2TextAndImg(R.mipmap.icon_my_home_dynamic_edit, "");
                    } else {
                        mTitleBar.setR2TextAndImg(R.mipmap.icon_family_my_family_more, "");
                    }
                    mTitleBar.setTitle("");
                } else if (state == State.COLLAPSED) {
                    //????????????
                    mTitleBar.setLeftImage(R.mipmap.icon_my_home_slide_back);
                    if (isSelf) {
                        mTitleBar.setR2TextAndImg(R.mipmap.icon_my_home_slide_edit, "");
                    } else {
                        mTitleBar.setR2TextAndImg(R.mipmap.icon_family_my_family_more, "");
                    }
                    mTitleBar.setTitle(title);

                }
            }
        });
    }

    @Override
    public void mineSuccess(MineReslut result) {
        mResult = result;
        if (result == null) return;

        if (isSelf) {
            CacheConstant.result = result;
        }

        mPresenter.initFragment(indicator, mViewPager, userId, result);

        if (!isSelf) {
            setMineResult(result);
        } else {
            setMineResult(CacheConstant.result);
        }

    }

    /**
     * ?????????????????????
     *
     * @param status
     */
    @Override
    public void queryFollowSuccess(Integer status) {

        if (status == 0) {          //?????????
            isFollow = false;
        } else if (status == 1) {   // ??????
            isFollow = true;
        }

        setBg(isFollow);

    }

    private void setBg(boolean isFollow) {

        llAttention.setVisibility(View.VISIBLE);
        if (isFollow) {
            rlAttention.setBackgroundResource(R.drawable.shape_line_gray);
            tvHeed.setText("?????????");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_friends_face);
            tvHeed.setCompoundDrawables(drawable, null, null, null);
            tvHeed.setTextColor(getResources().getColor(R.color.color_787878));
        } else {
            rlAttention.setBackgroundResource(R.drawable.shape_rounded_reg_btn1);
            tvHeed.setText("??????");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_personal_follow);
            tvHeed.setCompoundDrawables(drawable, null, null, null);
            tvHeed.setTextColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * ???????????????
     *
     * @param status
     */
    @Override
    public void followSuccess(String status) {
        if ("1".equals(status)) {
            isFollow = true;
        }

        setBg(isFollow);
    }

    /**
     * ?????????????????????
     *
     * @param status
     */
    @Override
    public void unfollowSuccess(String status) {
        if ("1".equals(status)) {
            isFollow = false;
        }

        setBg(isFollow);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mResult == null) {
            mPresenter.getMine(userId);
        } else {

            if (isSelf) {
                setMineResult(CacheConstant.result);
            } else {
                setMineResult(mResult);
            }

        }


    }

    private void setMineResult(MineReslut result) {

        mRoomId = result.getRoomId();

        ownerRoomId = result.getOwnerRoomId();

        title = result.getUserName();

        //??????
        Decks car = result.getCar();
        if (car != null && !isFrist) {
            mPresenter.animation(car.getDeckUrl(), mSvga);
//            mPresenter.animation("", mSvga);
            isFrist = true;
        }
        //??????
        String userPicture = result.getUserPicture();

        int userNoble = UserManagerUtils.userPage(result.getUserNoble());

        if (userNoble == 0) {
            mRealtime.setVisibility(View.VISIBLE);
            GlideAppUtil.loadImage(this, userPicture, mMainBackdrop);
        } else {
            GlideAppUtil.loadImage(this, userNoble, mMainBackdrop);
            mRealtime.setVisibility(View.GONE);
        }

        //??????
        String deckUrl = "";
        if (result.getHeadwear() != null) {
            deckUrl = result.getHeadwear().getDeckUrl();
        }
        mAvatar.setAvatarAndHeadear(userPicture,deckUrl);

        //??????
        mNickName.setText(result.getUserName());

        //??????
        int userSex = result.getUserSex();
        if (userSex == 0) { //??????
            mIvSex.setImageResource(R.mipmap.icon_gender_female);
        } else if (userSex == 1) {//??????
            mIvSex.setImageResource(R.mipmap.icon_gender_male);
        }

        if (result.getIsBeautiful() == 1) {
            mIvLh.setVisibility(View.VISIBLE);
        } else {
            mIvLh.setVisibility(View.GONE);
        }

        //id
        mTvId.setText("ID:" + result.getUserNumber());
        //????????????
        mUserLv.setUserLevel(result.getUserLevel());
        //????????????
        mCharmLv.setCharmLevel(result.getCharmLevel());
        //??????
        mTvStart.setStart(result.getUserBirthday());

        //????????????
        mTvAttention.setText(String.valueOf(result.getFollowTotal()));
        //????????????
        mTvFansNum.setText(String.valueOf(result.getFanTotal()));

    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onBlackSuccess(String status) {
        if (status == "1") {
            isBlack = 1;
        } else if (status == "0") {
            isBlack = 0;
        }
    }

    @Override
    public void onAddAndRemoveBlackSuccess(String status, Integer resultStatus) {
        if (status == "1") {
            if (resultStatus == 1) {
                isBlack = 0;
                ToastUtils.showShort("?????????????????????");
                addOrRemoveBlack(1,userId);
            } else {
                ToastUtils.showShort("?????????????????????");
            }
        } else if (status == "2") {
            if (resultStatus == 1) {
                isBlack = 1;
                ToastUtils.showShort("?????????????????????");
                addOrRemoveBlack(0,userId);
            } else {
                ToastUtils.showShort("?????????????????????");
            }
        }
    }

    private void addOrRemoveBlack(int status,String userId) {
        V2TIMFriendshipManager friendshipManager = V2TIMManager.getFriendshipManager();

        ArrayList<String> userList = new ArrayList<>();
        userList.add(userId);

        if (status == 1) {
            friendshipManager.addToBlackList(userList, null);
        } else if (status == 0) {
            friendshipManager.deleteFromBlackList(userList,null);
        }
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {
        if (isSelf) {
            ActStartUtils.startAct(this, CompileMateriaAct.class);
        } else {

            TipsDialog tipsDialog = TipsDialog.createDialog(this, R.layout.dialog_black_report)
                    .setTextColor(R.id.tv_camera, R.color.color_black)
                    .setText(R.id.tv_camera, "??????")
                    .setTextColor(R.id.tv_photo, R.color.color_aaaaaa);
            if (isBlack == 0){
                tipsDialog .setTextColor(R.id.tv_black, R.color.color_black)
                        .setText(R.id.tv_black,"????????????");

            } else if (isBlack == 1) {
                tipsDialog .setTextColor(R.id.tv_black, R.color.color_black)
                        .setText(R.id.tv_black,"???????????????");
            }




            tipsDialog .setText(R.id.tv_photo,"??????")
                    .bindClick(R.id.tv_camera, (v, dialog) -> {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.REPOTR_TYPE,Constant.REPORT_TYPE_USER);
                        bundle.putString(Constant.REPORT_ID,userId);
                        ActStartUtils.startAct(HomePageAct.this, ReportAct.class,bundle);
                        dialog.dismiss();
                    })
                    .bindClick(R.id.tv_black, (v, dialog) -> {
                        if (isBlack == 0){
                            mPresenter.removeBlackList("2",mResult.getId());
                        } else if (isBlack == 1){
                            mPresenter.addBlackList("2", mResult.getId());
                        }
                        dialog.dismiss();
                    })
                    .bindClick(R.id.tv_photo, (v, dialog) -> {
                        dialog.dismiss();
                    })
                    .show();
        }

    }

    @OnClick({R.id.rl_attention, R.id.rl_contact, R.id.ll_find, R.id.ll_he_room})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_find:
                if (!TextUtils.isEmpty(mRoomId)) {
                    EnterRoomHelp.enterRoomControl(this,this,mRoomId);
                } else {
                    ToastUtils.showShort("??????????????????????????????");
                }

                break;
            case R.id.ll_he_room:
                if (!TextUtils.isEmpty(ownerRoomId)) {
                    EnterRoomHelp.enterRoomControl(this,this,ownerRoomId);
                } else {
                    ToastUtils.showShort("?????????????????????????????????");
                }
                break;
            case R.id.rl_attention:

                if (isFollow) {
                    mPresenter.unfollow(userId);
                } else {
                    mPresenter.follow(userId);
                }

                break;
            case R.id.rl_contact:

                if (mResult == null ) return;

                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(V2TIMConversation.V2TIM_C2C);
                chatInfo.setId(mResult.getId());
                chatInfo.setChatName(mResult.getUserName());
                List<Object> iconUrl = new ArrayList<>();
                iconUrl.add(mResult.getUserPicture());
                chatInfo.setIconUrlList(iconUrl);

                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(Constant.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFrist = false;
    }
}
