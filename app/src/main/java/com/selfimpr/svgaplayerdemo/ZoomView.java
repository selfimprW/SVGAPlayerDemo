package com.selfimpr.svgaplayerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * description：   <br/>
 * ===============================<br/>
 * creator：Jiacheng<br/>
 * create time：2019-11-06 14:53<br/>
 * ===============================<br/>
 * reasons for modification：  <br/>
 * Modifier：  <br/>
 * Modify time：  <br/>
 */
public class ZoomView extends View {
    private static final int TOUCH_NONE = 0x00;
    private static final int TOUCH_ONE = 0x20;
    private static final int TOUCH_TWO = 0x21;
    /**
     * 当前触摸模式：
     * 无触摸；
     * 单指触摸；
     * 双指触摸；
     */
    private int currentTouchMode = TOUCH_NONE;

    private float lastScale = 1;
    private int lastX, lastY;
    private boolean isEditable = false;

    private float baseValue;
    //刚触摸时的view坐标（用来获取按下时view的大小）
    private int oriLeft;
    private int oriRight;
    private int oriTop;
    private int oriBottom;

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 缩放view
     *
     * @param scale 当前距离按下时的比例  (0.8：缩小到0.8倍)
     */
    private void touchZoom(float scale) {
        int oriWidth = Math.abs(oriRight - oriLeft);
        int oriHeight = Math.abs(oriBottom - oriTop);

        //需要缩放的比例（1-0.9=0.1，需要缩小0.1倍；-0.1：放大0.1倍）
        float zoomScale = (lastScale - scale);

        int dx = (int) (oriWidth * zoomScale / 2f);
        int dy = (int) (oriHeight * zoomScale / 2f);

        int left = getLeft() + dx;
        int top = getTop() + dy;
        int right = getRight() - dx;
        int bottom = getBottom() - dy;

        layout(left, top, right, bottom);
        lastScale = scale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEditable) {
            return super.onTouchEvent(event);
        }

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // 记录触摸点坐标
                lastX = x;
                lastY = y;
                oriLeft = getLeft();
                oriRight = getRight();
                oriTop = getTop();
                oriBottom = getBottom();
                currentTouchMode = TOUCH_ONE;
                baseValue = 0;
                lastScale = 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://多指触摸
                oriLeft = getLeft();
                oriRight = getRight();
                oriTop = getTop();
                oriBottom = getBottom();
                currentTouchMode = TOUCH_TWO;
                baseValue = 0;
                lastScale = 1;
                break;
            case MotionEvent.ACTION_MOVE: // 计算偏移量
                if (event.getPointerCount() == 2) {
                    float xx = event.getX(0) - event.getX(1);
                    float yy = event.getY(0) - event.getY(1);
                    float value = (float) Math.sqrt(xx * xx + yy * yy);// 计算两点的距离
                    if (baseValue == 0) {
                        baseValue = value;
                    } else {
                        if ((value - baseValue) >= 10 || value - baseValue <= -10) {
                            // 当前两点间的距离 除以 手指落下时两点间的距离就是需要缩放的比例。
                            float scale = value / baseValue;
                            touchZoom(scale);  //改变大小进行缩放（只能缩放当前view的大小，如果是父布局，则里面的子控件无法缩小）
                        }
                    }
                } else if (currentTouchMode == TOUCH_ONE) {
                    int offsetX = x - lastX;
                    int offsetY = y - lastY;
                    // 在当前left、top、right、bottom的基础上加上偏移量
                    layout(getLeft() + offsetX,
                            getTop() + offsetY,
                            getRight() + offsetX,
                            getBottom() + offsetY);
                    // offsetLeftAndRight(offsetX);
                    // offsetTopAndBottom(offsetY);
                }
                break;
            case MotionEvent.ACTION_UP:
                baseValue = 0;
                break;
            default:
                currentTouchMode = TOUCH_NONE;
                break;
        }
        return true;
    }
}
