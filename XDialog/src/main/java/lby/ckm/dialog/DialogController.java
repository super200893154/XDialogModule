package lby.ckm.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

class DialogController {
    private final XDialog mDialog;
    private final ViewHelper mViewHelper;
    private int mLayoutResId;
    private View mContentView;
    private final Window mWindow;
    private int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    private int mWidth = WindowManager.LayoutParams.WRAP_CONTENT;

    public DialogController( XDialog dialog) {
        mDialog = dialog;
        mWindow = mDialog.getWindow();
        mViewHelper = new ViewHelper(mDialog);
    }

    private void installContent() {
        if (mLayoutResId != 0) {
            mDialog.setContentView(mLayoutResId);
        } else if (mContentView != null) {
            mDialog.setContentView(mContentView);
        }
    }

    public void showed() {
        setLayout();
    }

    private void setLayout() {
        mWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams p = mWindow.getAttributes();
        p.width = mWidth;
        p.height = mHeight;
        mWindow.setAttributes(p);
    }

    /**
     * 设置TextView的文本
     * @param viewId id
     * @param text 文本
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId,text);
    }

    private void setText(SparseArray<CharSequence> textArr) {
        mViewHelper.setText(textArr);
    }

    /**
     * 设置Dialog宽高
     *
     * @param width  宽度
     * @param height 高度
     */
    public void setLayout(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param gravity {@link android.view.Gravity}
     */
    public void setGravity(int gravity) {
        mWindow.setGravity(gravity);
    }

    /**
     * 设置Dialog内容视图
     *
     * @param layoutResId 视图资源id
     */
    public void setContentView(int layoutResId) {
        mLayoutResId = layoutResId;
        mContentView = null;
    }

    /**
     * 设置Dialog内容视图
     *
     * @param contentView 内容view
     */
    public void setContentView(View contentView) {
        mContentView = contentView;
        mLayoutResId = 0;
    }

    public View getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    public void setViewOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setViewOnClickListener(viewId, listener);
    }

    public String getText(int viewId) {
        return mViewHelper.getText(viewId);
    }


    public static class Params {
        public Context mContext;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public SparseArray<View.OnClickListener> mOnClickListener = new SparseArray<>();
        public int mLayoutResId;
        public View mContentView;
        public int mGravity = Gravity.CENTER;
        public int mWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        public int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        public SparseArray<CharSequence> mTextArr = new SparseArray<>();


        public Params(Context context) {
            mContext = context;
        }


        /**
         * 组装配置参数
         * @param controller {@link DialogController}
         */
        public void apply(DialogController controller) {

            //组装内容视图
            if (mLayoutResId != 0) {
                controller.setContentView(mLayoutResId);
            } else if (mContentView != null) {
                controller.setContentView(mContentView);
            }
            controller.installContent();
            //设置显示位置
            if (mGravity != Gravity.CENTER) {
                controller.setGravity(mGravity);
            }
            //设置宽高
            if (mWidth != WindowManager.LayoutParams.WRAP_CONTENT ||
                    mHeight != WindowManager.LayoutParams.WRAP_CONTENT) {
                controller.setLayout(mWidth, mHeight);
            }
            //设置文本
            if (mTextArr.size() > 0) {
                controller.setText(mTextArr);
            }
            //设置点击事件
            if (mOnClickListener.size() > 0) {
                for (int i = 0; i < mOnClickListener.size(); i++) {
                    int key = mOnClickListener.keyAt(i);
                    controller.setViewOnClickListener(key, mOnClickListener.get(key));
                }
            }

        }
    }



}
