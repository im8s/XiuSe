package com.xsvideoLive.www.mvp.contract;

import android.os.Handler;
import android.widget.RelativeLayout;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BuyGoldResult;
import com.xsvideoLive.www.net.bean.GoldResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.List;


public interface GoldRechargeContract {

    interface View extends BaseView {

        /**
         * 查询金币数量成功
         * @param goldNum
         */
        void myGoldSuccess(Double goldNum);

        /**
         * 金币列表请求成功
         *
         * @param golds
         */
        void goldListSuccess(List<GoldResult> golds);

        /**
         * 获取令牌成功
         * @param result
         */
        void buyGoldSuccess(BuyGoldResult result);

        /**
         * 请求失败
         *
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        void getMyGoldNum();

        /**
         * 获取金币列表
         *
         * @param userId
         */
        void getGoldList(String userId);

        /**
         * 购买金币
         * @param payType
         * @param goldId
         * @param mRlRecharge
         */
        void buyGold(int payType, String goldId, RelativeLayout mRlRecharge);

        /**
         * 微信支付
         * @param result
         */
        void weCharPay(IWXAPI api, BuyGoldResult result);

        /**
         * 支付宝支付
         * @param handler
         * @param orderString
         */
        void aliPay(Handler handler, String orderString,int what);
    }

    interface Model {
        /**
         * 获取充值金币列表
         *
         * @param current
         * @param size
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<GoldResult>>>> getGoldList(String current, String size, String userId);

        /**
         * 购买金币
         * @param payType
         * @param payTargetId
         * @return
         */
        HttpObservable<BaseResponse<BuyGoldResult>> buyGold(int payType, String payTargetId);

        /**
         * 查询金币数量
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyGoldNum();
    }
}
