package lby.ckm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class XDialog extends Dialog {
    private final DialogController mDialogController;

    protected XDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    protected XDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mDialogController = new DialogController(this);
    }

    @Override
    public void show() {
        super.show();
        mDialogController.showed();
    }

    @SuppressWarnings("unchecked")
    public  <T extends  View> T getView(int viewId) {
        return (T) mDialogController.getView(viewId);
    }

    public String getText(int viewId) {
        return mDialogController.getText(viewId);
    }


    /**
     * 设置Dialog从底部弹出
     */
    public void formBottom() {
        mDialogController.setGravity(Gravity.BOTTOM);
    }

    /**
     * 设置Dialog宽度占满屏幕宽度
     */
    public void fullWidth() {
        mDialogController.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Dialog宽高
     *
     * @param width  宽度
     * @param height 高度
     */
    public void setLayout(int width, int height) {
        mDialogController.setLayout(width, height);
    }

    /**
     * 设置TextView的文本
     *
     * @param viewId id
     * @param text   文本
     */
    public void setText(int viewId, CharSequence text) {
        mDialogController.setText(viewId, text);
    }

    public void setViewOnClickListener(int viewId,View.OnClickListener listener) {
        mDialogController.setViewOnClickListener(viewId, listener);
    }


    public static class Builder {
        private final DialogController.Params P;
        private final int mTheme;

        public Builder(Context context) {
            this(context, R.style.BaseDialog);
        }

        public Builder(Context context, int theme) {
            P = new DialogController.Params(context);
            mTheme = theme;
        }

        public static Builder createBuilder(Context context) {
            return new Builder(context);
        }

        public static Builder createBuilder(Context context, int theme) {
            return new Builder(context, theme);
        }

        /**
         * 设置Dialog内容视图
         *
         * @param layoutResId 视图资源id
         * @return {@link Builder}
         */
        public Builder setContentView(int layoutResId) {
            P.mLayoutResId = layoutResId;
            P.mContentView = null;
            return this;
        }

        /**
         * 设置Dialog内容视图
         *
         * @param contentView 内容view
         * @return {@link Builder}
         */
        public Builder setContentView(View contentView) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
            P.mContentView = contentView;
            P.mLayoutResId = 0;
            return this;
        }

        /**
         * 设置Dialog从底部弹出
         *
         * @return {@link Builder}
         */
        public Builder formBottom() {
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置Dialog宽度占满屏幕宽度
         *
         * @return {@link Builder}
         */
        public Builder fullWidth() {
            P.mWidth = WindowManager.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 设置Dialog宽高
         *
         * @param width  宽度
         * @param height 高度
         * @return {@link Builder}
         */
        public Builder setLayout(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 设置TextView的文本
         *
         * @param viewId id
         * @param text   文本
         */
        public Builder setText(int viewId, CharSequence text) {
            if (TextUtils.isEmpty(P.mTextArr.get(viewId))) {
                P.mTextArr.put(viewId, text);
            }
            return this;
        }

        public Builder setViewOnClickListener(int viewId,View.OnClickListener listener) {
            if (P.mOnClickListener.get(viewId) == null) {
                P.mOnClickListener.put(viewId,listener);
            }
            return this;
        }

        public XDialog create() {
            final XDialog dialog = new XDialog(P.mContext, mTheme);
            P.apply(dialog.mDialogController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public XDialog show() {
            final XDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
