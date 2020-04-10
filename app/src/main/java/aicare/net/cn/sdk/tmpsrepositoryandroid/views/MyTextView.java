package aicare.net.cn.sdk.tmpsrepositoryandroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import aicare.net.cn.sdk.tmpsrepositoryandroid.R;
import aicare.net.cn.sdk.tmpsrepositoryandroid.utils.L;


/**
 * Created by Suzy on 2016/1/19.
 */

public class MyTextView extends TextView implements View.OnTouchListener{
    private final static String TAG = "MyTextView";

    private int mDrawableSize;// xml文件中设置的大小

    public MyTextView(Context context) {
        this(context, null, 0);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            L.i(TAG, "attr:" + attr);
            switch (attr) {
                case R.styleable.MyTextView_drawable_Size:
                    mDrawableSize = a.getDimensionPixelSize(R.styleable.MyTextView_drawable_Size, 50);
                    L.i(TAG, "mDrawableSize:" + mDrawableSize);
                    break;
                case R.styleable.MyTextView_drawable_Top:
                    drawableTop = a.getDrawable(attr);
                    break;
                case R.styleable.MyTextView_drawable_Bottom:
                    drawableRight = a.getDrawable(attr);
                    break;
                case R.styleable.MyTextView_drawable_Right:
                    drawableBottom = a.getDrawable(attr);
                    break;
                case R.styleable.MyTextView_drawable_Left:
                    drawableLeft = a.getDrawable(attr);
                    break;
                default:
                    break;
            }
        }
        a.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        this.setOnTouchListener(this);
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                v.setAlpha(1f);
                return true;
        }
        return false;
    }
}
