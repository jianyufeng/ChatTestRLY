package com.fule.myapplication.common.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.fule.myapplication.R;
import com.fule.myapplication.common.LogUtil;
import com.fule.myapplication.common.activity.until.AnimatorUtils;
import com.fule.myapplication.common.activity.until.MethodInvoke;
import com.fule.myapplication.common.activity.until.SDKVersionUtils;

import java.lang.ref.WeakReference;


/**
 *
 * Created by Jorstin on 2015/6/6.
 */
public class SwipeBackLayout extends FrameLayout {

    private static final String TAG = "SwipeBackLayout";
    /**
     * Minimum velocity that will be detected as a fling
     * 将被检测到的最小速度
     */
    private static final int MIN_FLING_VELOCITY = 100; // dips per second
    private static final int FULL_ALPHA = 255;
    //是否正在调用Layout()
    private boolean mInLayout;
    //默认手势使能
    private boolean mEnable = true;
    private View mContentView;
    private float mScrimOpacity; //左边的阴影
    private ViewDragHelper mDragHelper;
    //滑动的百分比
    private float mScrollPercent;
    //获取滑动时左侧的显示灰色图片
    private Drawable mShadowLeft;
    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     * 滚动的阈值，超过将关闭活动，当scrollpercent过超这个值；
     *
     *
     */
    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;
    private Rect mTmpRect = new Rect();
    /**
     * Default threshold of scroll
     * 默认的阈值
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 1.0f;
    private static final int OVERSCROLL_DISTANCE = 10;
    private int mContentLeft;
    private int mContentTop;
    public boolean mScrolling = false;
    public boolean mTranslucent = false;
    private boolean mRequestTranslucent = false;
    private boolean mFastRelease = false;
    public OnSwipeGestureDelegate mOnSwipeGestureDelegate;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        //获取滑动时左侧的显示灰色图片
        mShadowLeft = ContextCompat.getDrawable(context, R.drawable.shadow_left);
        setFocusable(true);
        //聚焦处理 先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        init();
    }

    //是否正在侧边滑动
    public boolean isSwipeBacking() {
        isScrolling();
        return mScrolling;
    }
    //判断是否正在滑动
    public boolean isScrolling() {
        //没有滑动
        if (!mScrolling) {
            return false;
        }
        //比较是否有滑动距离  有 滑动
        if (Float.compare(this.mContentView.getLeft(), 0.01F) <= 0) {
            mScrolling = false;
            return false;
        }
        return true;
    }
    //拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //未使能 不拦截
        if (!mEnable) {
            return false;
        }
        try {
            LogUtil.d(TAG, "onInterceptTouchEvent");
            return mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            // FIXME: handle exception
            // issues #9
            return false;
        }
    }
    //处理触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //未使能 不拦截
        if (!mEnable) {
            return false;
        }
        LogUtil.d(TAG, "onTouchEvent");
        //处理触摸事件
        mDragHelper.processTouchEvent(event);
        return true;
    }
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!mEnable) {
            return super.dispatchTouchEvent(ev);
        }
        if(isScrolling()) {
            return super.dispatchTouchEvent(ev);
        }
        try {
            LogUtil.d(TAG , "11111111111111111111111");
            if(mDragHelper.getViewDragState() != ViewDragHelper.STATE_DRAGGING) {
                LogUtil.d(TAG , "22222222222222222222222222");
                if (!mDragHelper.shouldInterceptTouchEvent(ev)) {
                    LogUtil.d(TAG , "33333333333333333333333333333333");
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.dispatchTouchEvent(ev);
                    return true;
                }
                return super.dispatchTouchEvent(ev);
            }
            LogUtil.d(TAG , "444444444444444444444444444444444");
            mDragHelper.processTouchEvent(ev);
            return true;
        } catch (NullPointerException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            // FIXME: handle exception
            // issues #9
            return false;
        }
    }*/
    //绘制子类视图
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        LogUtil.d(TAG, "drawChild " + drawingTime);
        final boolean drawContent = child == mContentView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        //滑动时要 绘制左边的阴影
        if (Float.compare(mScrimOpacity, 0.0F) > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
        }
        return ret;
    }
    // 绘制左边的阴影
    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        //找到控件占据的矩形区域的矩形坐标
        child.getHitRect(childRect);
        //设置阴影的边界
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                childRect.left, childRect.bottom);
        //根据滑动的距离设置阴影的透明度
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        //绘制到画布中
        mShadowLeft.draw(canvas);
    }


    public void init() {
        //滑动的阈值 设置
        mScrollThreshold = 0.3f;
        //创建拖拽的 监视器和处理器 初始化一些参数
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        //设置左边拖拽
        setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        //获取像素密度
        final float density = getResources().getDisplayMetrics().density;
        //设置最小速度
        final float minVel = MIN_FLING_VELOCITY * density;

        mDragHelper.setMinVelocity(minVel);
        //设置最大速度
        mDragHelper.setMaxVelocity(minVel * 3f);
        mContentLeft = 0;
        mContentTop = 0;
    }


    public void onFinishInflate() {
        mContentView = this;
        //加载xml中的视图完成时调用
        super.onFinishInflate();
    }

    //使透明结果被记录
    public void markTranslucent(boolean translucent) {
        LogUtil.i(TAG, "markTranslucent : " + translucent);
        mTranslucent = translucent;
    }
    //设置布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null)
            mContentView.layout(mContentLeft, mContentTop,
                    mContentLeft + mContentView.getMeasuredWidth(),
                    mContentTop + mContentView.getMeasuredHeight());
        mInLayout = false;
    }

    //重新测量位置 和布局
    @Override
    public void requestLayout() {
        //子空间是否正在调用 Layout()
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    /**
     * Set up contentView which will be moved by user gesture
     *  设置内容视图
     * @param view
     */
    public void setContentView(View view) {
        mContentView = view;
    }
    //设置手势是否使能    决定事件的拦截和处理
    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    public void setNeedRequestActivityTranslucent(boolean request) {
        mRequestTranslucent = request;
    }
    //设置滑动的手势拖拽
    public void setSwipeGestureDelegate(OnSwipeGestureDelegate onSwipeGestureDelegate) {
        mOnSwipeGestureDelegate = onSwipeGestureDelegate;
    }

    /**
     * Enable edge tracking for the selected edges of the parent view. The
     * callback's
     * 使父视图的选定边缘的边缘跟踪。这个调的
     * {@link ViewDragHelper.Callback#onEdgeTouched(int, int)}
     * and
     * methods will only be invoked for edges for which edge tracking has been
     * enabled.
     *方法只会被调用边缘跟踪已被  *启用。
     * @param edgeFlags Combination of edge flags describing the edges to watch
     */
    public void setEdgeTrackingEnabled(int edgeFlags) {
        mDragHelper.setEdgeTrackingEnabled(edgeFlags);
    }

    //易于控制滑屏控制  估算滑动
    @Override
    public void computeScroll() {
        super.computeScroll();
        mScrimOpacity = Math.max(0.0F, 1.0F - mScrollPercent);
        LogUtil.d(TAG, "computeScroll :: mScrimOpacity " + mScrimOpacity);
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback implements MethodInvoke.OnSwipeInvokeResultListener {
        private static final String TAG = "ViewDragCallback";
        private int mTemp = 0;
        private int mReleasedLeft = 0;
        private boolean mIsScrollOverValid;

        // 判断child是否是拖动的目标
        @Override
        public boolean tryCaptureView(View view, int i) {
            //是否是边缘拖拽
            boolean edgeTouched = mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT, i);
            LogUtil.d(TAG, "tryCaptureView i :" + i + " ,edgeTouched:" + edgeTouched);
            return edgeTouched;
        }

        //  拖动手势释放后的处理
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            final int childWidth = releasedChild.getWidth();
            int left = ((xvel > 0.0F) || (xvel == 0.0F) && (mScrollPercent > mScrollThreshold)) ? childWidth + mShadowLeft.getIntrinsicWidth() + OVERSCROLL_DISTANCE : 0;
            int top = 0;
            mReleasedLeft = left;
            //mIsScrollOverValid = true;
            LogUtil.i(TAG, "onViewReleased, xvel: " + xvel + " yvel: " + yvel + ", releaseLeft: " + left + ", releaseTop: " + top + ", translucent: " + mTranslucent);
            if (!mTranslucent) {
                return;
            }
            mDragHelper.settleCapturedViewAt(left, top);
            invalidate();
            mFastRelease = true;
        }

        // 拖动状态的改变
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            LogUtil.d(TAG, "onViewDragStateChanged state " + state + "mTranslucent " + mTranslucent + " , requestedTranslucent " + mRequestTranslucent + " fastRelease " + mFastRelease);
            Activity mActivity = null;
            if (state == ViewDragHelper.STATE_DRAGGING) {
                LogUtil.d(TAG, "on drag");
                if (SwipeBackLayout.this.getContext() instanceof Activity) {
                    ((Activity) SwipeBackLayout.this.getContext()).getWindow().getDecorView().setBackgroundResource(R.drawable.transparent);
                }
                if (mOnSwipeGestureDelegate != null) {
                    mOnSwipeGestureDelegate.onDragging();
                }
                mIsScrollOverValid = false;
                if (mTranslucent) {
                    SwipeActivityManager.notifySwipe(0.0F);
                }
            }

            if (state == ViewDragHelper.STATE_IDLE && !mFastRelease) {
                LogUtil.i(TAG, "on cancel");
                if (mOnSwipeGestureDelegate != null) {
                    mOnSwipeGestureDelegate.onCancel();
                }
                SwipeActivityManager.notifySwipe(1.0F);
            }

            if (state == ViewDragHelper.STATE_DRAGGING /*&& mScrolling*/ && (SwipeBackLayout.this.getContext() instanceof Activity) && !mTranslucent && !mRequestTranslucent) {
                LogUtil.i(TAG, " match dragging");
                mTranslucent = true;
                mActivity = ((Activity) SwipeBackLayout.this.getContext());
                if (SDKVersionUtils.isGreaterorEqual(16)) {
                    LogUtil.w(TAG, "convertActivityToTranslucent::Android Version Error " + Integer.valueOf(Build.VERSION.SDK_INT));
                }
            }
            if (state == ViewDragHelper.STATE_SETTLING) {
                LogUtil.i(TAG, "notify settle, mReleasedLeft " + mReleasedLeft);
                boolean open = (mReleasedLeft > 0);
                SwipeActivityManager.notifySettle(open, mReleasedLeft);
            }
            if (mActivity != null) {
                MethodInvoke.SwipeInvocationHandler handler = new MethodInvoke.SwipeInvocationHandler();
                handler.mWeakReference = new WeakReference<MethodInvoke.OnSwipeInvokeResultListener>(this);
                SwipTranslucentMethodUtils.convertActivityToTranslucent(mActivity, handler);
            }
        }

        // 拖动后view位置的改变
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            LogUtil.d(TAG, "onViewPositionChanged: Translucent : " + mTranslucent + "' ,left :"
                    + left + " ,top :" + top + " ,dx:" + dx + " ,dy:" + dy);
            if (!mTranslucent) {
                return;
            }
            //初始化滑动的百分比  左边的距离是
            mScrollPercent = Math.abs((float) left / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
            mContentLeft = left;
            mContentTop = top;
            invalidate();
            LogUtil.d(TAG, "onViewPositionChanged: mScrollPercent : " + mScrollPercent + "' ,mIsScrollOverValid :" + mIsScrollOverValid);
            if (Float.compare(mScrollPercent, DEFAULT_SCROLL_THRESHOLD) >= 0 && !mIsScrollOverValid) {
                mIsScrollOverValid = true;
                mScrolling = true;
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mOnSwipeGestureDelegate != null) {
                            mOnSwipeGestureDelegate.onSwipeBack();
                        }
                        mTranslucent = false;
                    }
                });
            } else {
                if (!(Float.compare(mScrollPercent, 0.01F) > 0)) {
                    mIsScrollOverValid = false;
                    mScrolling = false;
                }
            }
            if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_DRAGGING) {
                SwipeActivityManager.notifySwipe(mScrollPercent);
            }
        }

        // 拖动位置的处理，可以处理拖动过程中的最高位置或者最低位置
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtil.d(TAG, "clampViewPositionHorizontal : translucent : " + mTranslucent + " ,left " + left + " , dx " + dx);
            int ret = 0;
            if (mTranslucent) {
                int max = Math.max(mTemp, left);
                mTemp = 0;
                ret = Math.min(child.getWidth(), Math.max(max, 0));
            } else {
                mTemp = Math.max(mTemp, left);
            }
            LogUtil.d(TAG, "clampViewPositionHorizontal ret " + ret);
            return ret;
        }


        @Override
        public int getViewHorizontalDragRange(View child) {
            return ViewDragHelper.EDGE_LEFT;
        }

        @Override
        public void onSwipeInvoke(final boolean result) {
            LogUtil.d(TAG, "onSwipeInvoke :" + result);
            post(new Runnable() {
                @Override
                public void run() {
                    LogUtil.i(TAG, "on Complete, result " + result + " ,releaseLeft " + mReleasedLeft);
                    if (result) {
                        if (mReleasedLeft > 0) {
                            SwipeActivityManager.notifySwipe(0.0F);
                            SwipeBackLayout.this.markTranslucent(result);
                            if (result && !mFastRelease) {
                                if (mReleasedLeft == 0) {
                                    AnimatorUtils.updateViewAnimation(SwipeBackLayout.this, 200L, 0.0F, new AnimatorUtils.OnAnimationListener() {
                                        @Override
                                        public void onAnimationCancel() {
                                            onAnimationEnd();
                                        }

                                        @Override
                                        public void onAnimationEnd() {
                                            mTranslucent = false;
                                        }
                                    });
                                } else {
                                    AnimatorUtils.updateViewAnimation(SwipeBackLayout.this, 200L, mReleasedLeft, new AnimatorUtils.OnAnimationListener() {
                                        @Override
                                        public void onAnimationCancel() {
                                            onAnimationEnd();
                                        }

                                        @Override
                                        public void onAnimationEnd() {
                                            mIsScrollOverValid = true;
                                            post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (mOnSwipeGestureDelegate != null) {
                                                        mOnSwipeGestureDelegate.onSwipeBack();
                                                        LogUtil.d(TAG, "on onSwipeBack");
                                                    }
                                                    SwipeActivityManager.notifySwipe(1.0F);
                                                    mIsScrollOverValid = false;
                                                    mScrolling = false;
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }
    }


    public interface OnSwipeGestureDelegate {
        void onSwipeBack();

        void onDragging();

        void onCancel();
    }
}
