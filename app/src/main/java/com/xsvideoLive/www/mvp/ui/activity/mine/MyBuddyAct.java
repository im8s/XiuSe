package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.OnFriendsSelectCallback;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.MyBuddyContract;
import com.xsvideoLive.www.mvp.presenter.MyBuddyPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.ClanResult;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.MyFollowResult;
import com.xsvideoLive.www.net.bean.ShareRoom;
import com.xsvideoLive.www.utils.ToastUtils;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MyBuddyAct extends BaseMvpActivity<MyBuddyPresenter> implements MyBuddyContract.View, TitleBarClickCallback, OnFriendsSelectCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ns_view_pager)
    ViewPager nsViewPager;

//    private Decks decks;

    private String buddyKey;

    private FriendsCircleResult mResult;

    private ClanResult mClanResult;

    private ShareRoom mShareRoom;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_buddy;
    }

    @Override
    protected MyBuddyPresenter createPresenter() {
        return new MyBuddyPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();

    }

    private void init() {

        Bundle extras = getIntent().getExtras();

        buddyKey = extras.getString(Constant.BUDDY_KEY);


        if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.TOPIC)) {
            mTitleBar.setTitle("??????");
            mResult = (FriendsCircleResult) extras.getSerializable(Constant.TOPIC);
        } else if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.CLAN)) {
            mTitleBar.setTitle("??????");
            mClanResult = (ClanResult) extras.getSerializable(Constant.CLAN);
        } else if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.PUBLIC_CHAT)) {
            mTitleBar.setTitle("??????");
        }else if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.SHARE_ROOM)) {
            mTitleBar.setTitle("??????");
            mShareRoom = (ShareRoom) extras.getSerializable(Constant.SHARE_ROOM);
        }

        mPresenter.attachView(this);

        mPresenter.initFragment(magicIndicator, nsViewPager, this, buddyKey);

        mTitleBar.setOnClickCallback(this);


//        decks = (Decks) extras.getSerializable(Constant.DECKS);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }

    @Override
    public void onFriendsSelect(MyFollowResult result) {

        if (result == null) return;

        Gson gson = new Gson();

        Map<String, String> map = new HashMap<>();

        if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.TOPIC)) {

            ToastUtils.showShort("????????????");

            String userName = mResult.getUserName();
            String userNameStr;
            if (userName.length() >= 5) {
                userNameStr = userName.substring(0, 5);
            } else {
                userNameStr = userName;
            }

            map.put("id", mResult.getId());
            map.put("title", userNameStr + "...?????????????????????");
            map.put("detail", mResult.getTopicContent());
            map.put("type", "Topic");
            if (mResult.getPictureList().size() > 0) {
                map.put("img", mResult.getPictureList().get(0));
            } else {
                map.put("img", "chat_share");
            }

        } else if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.CLAN)) {
            ToastUtils.showShort("????????????");
            String clanName = mClanResult.getClanName();
            String userNameStr;
            if (clanName.length() >= 5) {
                userNameStr = clanName.substring(0, 5);
            } else {
                userNameStr = clanName;
            }
            map.put("type","Clan");
            map.put("title","???????????????????????????"+userNameStr+",??????????????????????????????????????????~");
            map.put("id",mClanResult.getClanId());
            map.put("img",mClanResult.getClanPicture());
        } else if (!TextUtils.isEmpty(buddyKey) && buddyKey.equals(Constant.SHARE_ROOM)){

            ToastUtils.showShort("????????????");
            String clanName = mShareRoom.getRoomName();
//            String userNameStr;
//            if (clanName.length() >= 5) {
//                userNameStr = clanName.substring(0, 5);
//            } else {
//                userNameStr = clanName;
//            }
            map.put("type","Live");
            map.put("title","?????????????????????"+clanName+",??????????????????????????????????????????~");
            map.put("id",mShareRoom.getRoomId());
            map.put("img",mShareRoom.getRoomPicture());

        }


        String data = gson.toJson(map);
        MessageInfo info = MessageInfoUtil.buildCustomMessage(data);

        V2TIMMessage v2TIMMessage = info.getTimMessage();

        V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, result.getUserId(), null,
                V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, null);


    }

    @Override
    public void onFriendsClick(MyFollowResult result) {
        Intent intent = new Intent();
        intent.putExtra(Constant.BUDDY_KEY,result);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void giveAwaySuccess(Integer status) {
        if (status == 1) {
            ToastUtils.showShort("????????????");
            finish();
        } else {
            ToastUtils.showShort("????????????");
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }
}