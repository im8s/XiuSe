package com.xsvideoLive.www.mvp.presenter;

import android.content.Intent;
import android.view.View;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseFragment;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.helper.ConversationLayoutHelper;
import com.xsvideoLive.www.mvp.contract.SpeakContract;
import com.xsvideoLive.www.mvp.model.SpeakModel;
import com.xsvideoLive.www.mvp.ui.activity.msg.SpeakAct;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.SystemNotic;
import com.xsvideoLive.www.view.TipsDialog;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;

import java.util.List;

public class SpeakPresenter extends BasePresenter<SpeakContract.View> implements SpeakContract.Presenter {

    private SpeakModel model;

    public SpeakPresenter() {
        model = new SpeakModel();
    }

    @Override
    public void initIM(ConversationLayout mConversationLayout) {

        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        mConversationLayout.getTitleBar().setVisibility(View.GONE);

        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                startChatActivity(conversationInfo);
            }
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, ConversationInfo conversationInfo) {


                startPopShow(position, mConversationLayout, conversationInfo);

            }
        });
    }

    @Override
    public void getSystemNoic() {
        model.getSystemNotic("1","1")
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<HomeRoomResult<List<SystemNotic>>>(){

                    @Override
                    protected void success(HomeRoomResult<List<SystemNotic>> bean, BaseResponse<HomeRoomResult<List<SystemNotic>>> response) {
                        List<SystemNotic> records = bean.getRecords();
                        getView().onSystemNoicSuccess(records);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    private void startPopShow(int position, ConversationLayout mConversationLayout, ConversationInfo conversationInfo) {
        TipsDialog tipsDialog = TipsDialog.createDialog(((BaseFragment) getView()).getContext(), R.layout.dialog_news_top_delete);
        if (conversationInfo.isTop()) {
            tipsDialog.setText(R.id.tv_top, "取消置顶");
        } else {
            tipsDialog.setText(R.id.tv_top, "置顶");
        }
        tipsDialog.bindClick(R.id.tv_top, (v, dialog) -> {
            mConversationLayout.setConversationTop(position, conversationInfo);
        });

        tipsDialog.bindClick(R.id.tv_delete, (v, dialog) -> {
            mConversationLayout.deleteConversation(position, conversationInfo);
        });

        tipsDialog.show();

    }

    private void startChatActivity(ConversationInfo conversationInfo) {

        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        chatInfo.setIconUrlList(conversationInfo.getIconUrlList());

//        Intent intent = new Intent(((NewsFragment) getView()).getContext(), ChatActivity.class);
        Intent intent = new Intent(((BaseFragment) getView()).getContext(), SpeakAct.class);
        intent.putExtra(Constant.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((BaseFragment) getView()).getContext().startActivity(intent);

    }
}
