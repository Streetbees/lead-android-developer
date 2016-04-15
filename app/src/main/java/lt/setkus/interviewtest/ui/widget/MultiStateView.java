package lt.setkus.interviewtest.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import lt.setkus.interviewtest.app.R;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 * @since 2016.02.18
 */
public class MultiStateView extends FrameLayout {

    private LayoutInflater mInflater;

    private View mContentView;
    private View mLoadingView;
    private View mEmptyView;

    @ViewState
    private int mViewState = VIEW_STATE_CONTENT;

    public static final int VIEW_STATE_CONTENT = 0;
    public static final int VIEW_STATE_EMPTY = 1;
    public static final int VIEW_STATE_LOADING = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_CONTENT, VIEW_STATE_EMPTY, VIEW_STATE_LOADING})
    public @interface ViewState {
    }

    private void init(AttributeSet attributeSet) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MultiStateView);

        int loadingViewResId = typedArray.getResourceId(R.styleable.MultiStateView_luc_loadingView, -1);
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }

        int emptyViewResId = typedArray.getResourceId(R.styleable.MultiStateView_luc_emptyView, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int viewState = typedArray.getInt(R.styleable.MultiStateView_luc_viewState, VIEW_STATE_CONTENT);

        switch (viewState) {
            case VIEW_STATE_CONTENT:
                mViewState = VIEW_STATE_CONTENT;
                break;

            case VIEW_STATE_EMPTY:
                mViewState = VIEW_STATE_EMPTY;
                break;

            case VIEW_STATE_LOADING:
                mViewState = VIEW_STATE_LOADING;
                break;
        }

        typedArray.recycle();
    }

    private void setView() {
        switch (mViewState) {
            case VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }

                mLoadingView.setVisibility(View.VISIBLE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }

                mEmptyView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                break;


            case VIEW_STATE_CONTENT:
            default:
                if (mContentView == null) {
                    // Should never happen, the view should throw an exception if no content view is present upon creation
                    throw new NullPointerException("Content View");
                }

                mContentView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mEmptyView;
    }

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MultiStateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }

        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)){
            mContentView = child;
        }

        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        }

        setView();
    }

    /**
     * All of the addView methods have been overridden so that it can obtain the content view via XML
     * It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     * any of the setViewForState methods to set views for their given ViewState accordingly
     *
     * @param child {@link View}
     */
    @Override
    public void addView(View child) {
        if (isValidContentView(child)) {
            mContentView = child;
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) {
            mContentView = child;
        }

        super.addView(child, width, height);
    }

    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case VIEW_STATE_LOADING:
                return mLoadingView;

            case VIEW_STATE_CONTENT:
                return mContentView;

            case VIEW_STATE_EMPTY:
                return mEmptyView;

            default:
                return null;
        }
    }

    @ViewState
    public int getViewState() {
        return mViewState;
    }

    public void setViewState(@ViewState int state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        switch (state) {
            case VIEW_STATE_LOADING:
                if (mLoadingView != null) {
                    removeView(mLoadingView);
                }

                mLoadingView = view;
                addView(mLoadingView);
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView != null) {
                    removeView(mEmptyView);
                }

                mEmptyView = view;
                addView(mEmptyView);
                break;

            case VIEW_STATE_CONTENT:
                if (mContentView != null) {
                    removeView(mContentView);
                }

                mContentView = view;
                addView(mContentView);
                break;
        }

        if (switchToState) {
            setViewState(state);
        }
    }

    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }
}
