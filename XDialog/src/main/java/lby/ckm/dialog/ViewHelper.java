package lby.ckm.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

public class ViewHelper {
    private final XDialog mDialog;
    private final SparseArray<View> mChildViews;

    public ViewHelper(XDialog dialog) {
        mDialog = dialog;
        mChildViews = new SparseArray<>();
    }

    public void setText(SparseArray<CharSequence> textArr) {
        if (textArr.size() > 0) {
            for (int i = 0; i < textArr.size(); i++) {
                int key = textArr.keyAt(i);
                View view = getView(key);
                if (view instanceof TextView) {
                    ((TextView) view).setText(textArr.get(key));
                }
            }
        }
    }

    public void setText(int viewId, CharSequence text) {
        View view = getView(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public String getText(int viewId) {
        View view = getView(viewId);
        return view instanceof TextView ? ((TextView) view).getText().toString() : "";
    }


    public View getView(int viewId) {
        View view = mChildViews.get(viewId);
        if (view == null) {
            view = mDialog.findViewById(viewId);
            if (view != null) {
                mChildViews.put(viewId, view);
            }
        }
        return view;
    }

    public void setViewOnClickListener(int viewId, View.OnClickListener listener) {
        View view = null;
        if (viewId != 0) {
            view = getView(viewId);
        }
        if (view != null && listener != null) {
            view.setOnClickListener(listener);
        }
    }
}
