package com.xsvideoLive.www.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseApplication;
import com.xsvideoLive.www.callback.DialogCallback;


/**
 * Created by zd on 16/3/22.
 */
public class TipsDialog {
    private static Dialog dialog;
    private static TipsDialog tipsDialog;

    private static TipsDialog getInstance() {
        if (tipsDialog == null)
            tipsDialog = new TipsDialog();
        return tipsDialog;
    }

    public static TipsDialog createDialog(Context context, int layoutId) {
        getInstance();


        try {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {

        } finally {
            dialog = null;
        }

//        if (dialog != null)
//            dialog.dismiss();


        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(layoutId);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        fillWindow();
        return tipsDialog;
    }



    public static TipsDialog createDialog(Context context, int layoutId, int styleIds) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, styleIds);
        dialog.setContentView(layoutId);
        fillWindow();
        return tipsDialog;
    }

    public TipsDialog setCancelable(boolean cancel) {
        if (dialog != null)
            dialog.setCancelable(cancel);
        return tipsDialog;
    }

    public TipsDialog setCanceledOnTouchOutside(boolean cancel) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancel);
        return tipsDialog;
    }

    public static TipsDialog createDialog(Context context, View layout) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(layout);
        fillWindow();
        return tipsDialog;
    }

    public static TipsDialog createDialogFromBottom(Context context, int layoutId) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.Dialog_NoTitle);
        dialog.setContentView(layoutId);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        fromBottom();
        return tipsDialog;
    }

    public static TipsDialog createDialogFromBottom(Context context, View layout) {
        getInstance();
        if (dialog != null)
            dialog.dismiss();
        dialog = new Dialog(context, R.style.Dialog_NoTitle);
        dialog.setContentView(layout);
        fromBottom();
        return tipsDialog;
    }

    private static TipsDialog fillWindow() {
        WindowManager.LayoutParams localLayoutParams = dialog.getWindow().getAttributes();
        localLayoutParams.width = getScreenWidth();
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        dialog.onWindowAttributesChanged(localLayoutParams);
        return tipsDialog;
    }

    private static TipsDialog fromBottom() {
        WindowManager.LayoutParams localLayoutParams = dialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.width = getScreenWidth();
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        dialog.onWindowAttributesChanged(localLayoutParams);
        return tipsDialog;
    }

    /**
     * ??????view???????????????
     *
     * @param alpha
     * @return
     */
    public TipsDialog setBgAplha(float alpha) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.alpha = alpha;
        dialog.getWindow().setAttributes(lp);
        return tipsDialog;
    }

    /**
     * ??????window???????????????
     *
     * @param alpha
     * @return
     */
    public TipsDialog setDimAplha(float alpha) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = alpha;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return tipsDialog;
    }

    /**
     * ??????????????????
     *
     * @param viewId
     * @param size
     * @return
     */
    public TipsDialog setTextSize(int viewId, int size) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setTextSize(size);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    /**
     * ??????????????????
     *
     * @param viewId
     * @param size
     * @return
     */
    public TipsDialog setTextSize(int viewId, int unit, int size) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setTextSize(unit, size);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setHtmlText(int viewId, String title) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setText(Html.fromHtml(title));
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setText(int viewId, String title) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setText(title);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setTextColor(int viewId, int colorRes) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setTextColor(dialog.getContext().getResources().getColor(colorRes));
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setTextGravity(int viewId, int gravity) {
        if (dialog != null) {
            ((TextView) dialog.findViewById(viewId)).setGravity(gravity);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public TipsDialog setBackgroundResouce(int viewId, int backgroundId) {
        if (dialog != null) {
            dialog.findViewById(viewId).setBackgroundResource(backgroundId);
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
        }
        return tipsDialog;
    }

    public <T extends View> T getView(int viewId) {
        if (dialog != null) {
            return dialog.findViewById(viewId);
        }
        return null;
    }

    public void show(DialogCallback callback) {
        //false ?????????
        if (!callback.callback(this, dialog))
            return;
        if (dialog != null)
            dialog.show();
    }

    public void show() {
        if (dialog != null)
            dialog.show();
    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
        tipsDialog = null;
        dialog = null;
    }

    public TipsDialog bindClick(final int viewId, final TipClickListener onClickListener) {
        if (dialog != null) {
            dialog.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (onClickListener != null)
                        onClickListener.onClick(v, TipsDialog.this);
                }
            });
        }
        return tipsDialog;
    }

    public void showBefore(TipShowBeforeListener listener) {
        listener.before(tipsDialog);
    }

    /**
     * ??????????????????
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    public TipsDialog bindClickV1(final int viewId, final TipClickListener onClickListener) {
        if (dialog != null) {
            dialog.findViewById(viewId).setVisibility(View.VISIBLE);
            dialog.findViewById(viewId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onClick(v, TipsDialog.this);
                }
            });
        }
        return tipsDialog;
    }

    public TipsDialog OnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null && onCancelListener != null) {
            dialog.setOnCancelListener(onCancelListener);
        }
        return tipsDialog;
    }

    public interface TipClickListener {
        void onClick(View v, TipsDialog dialog);
    }

    public interface TipShowBeforeListener {
        void before(TipsDialog dialog);
    }

    public TipsDialog setVisible(int viewId, boolean isVisible) {
        if (dialog != null) {
            if (isVisible) {
                dialog.findViewById(viewId).setVisibility(View.VISIBLE);
            } else {
                dialog.findViewById(viewId).setVisibility(View.GONE);
            }
        }
        return tipsDialog;
    }

    /**
     * ?????????????????????????????????px???
     *
     * @return ?????????px
     */
    private static int getScreenWidth() {
        WindowManager windowManager =
                (WindowManager) BaseApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// ?????????????????????
        windowManager.getDefaultDisplay().getMetrics(dm);// ?????????????????????
        return dm.widthPixels;
    }

    /**
     * ??????icon
     */
    public static void setIcon(int id, int drawable) {
        ImageView imageView = dialog.findViewById(id);
        imageView.setImageResource(drawable);
    }
}
