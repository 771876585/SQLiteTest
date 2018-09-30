//package com.yezh.sqlite.sqlitetest;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.AnimationSet;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//
//import com.cmread.reader.util.DeviceUtil;
//import com.cmread.reader.util.SystemBarTintManager;
//import com.cmread.utils.log.NLog;
//import com.cmread.utils.preferences.ReaderPreferences;
//import com.cmread.config.reader.ButtonType;
//import com.cmread.utils.statistics.UmengEvent;
//import com.cmread.utils.statistics.UmengStatistics;
//import com.cmread.reader.R;
//
//public class ReaderToolsBar extends RelativeLayout{
//
//	private Context mContext;
//	public ReaderTitleBar mTopBar;
//	public ReaderBottomBar mBottomBar;
//
//	//private ImageView mBookCatalog;//add by Henry
//
//	private final int ALLANIMATION_COUNT = 2;
//	private final int ANIMATION_DURATION = 250;
//	private final float ANIMATION_HIDEALPHA = 0.3f;
//	private final float ANIMATION_SHOWALPHA = 1.0f;
//
//	private Animation mTopToolbarShow;
//	private Animation mTopToolbarHide;
//	private Animation mBottomBarShow;
//	private Animation mBottomBarHide;
//
//	private int mFinishedAnimation;
//	private float mTopToolbarHeight;
//	private float mBottomBarHeight;
//	private boolean mAnimationInProcess;
//	private boolean mToHideToolbar;
//	private Rect mShowToolbarRect;
//	private Rect mHideToolbarRect;
//
//	private ReaderToolsBarObserver mObserver;
//	private PopupWindow mTopPopupWindow;
//	private ButtonType  mTopTag;
//	private ButtonType  mTag = ButtonType.PROGRESSBUTTON;
//	private View mPopupView;
//	private Rect mPopupRect = new Rect();
//	private HashMap<ButtonType, View> mPopupMap = new HashMap<ButtonType, View>();
//	private LinearLayout mBottomLayout;
//	private boolean mIsFullScreen = true;
//	private PopupWindow mWapConnRemindPop;
//
//
//	public static final int ANIM_START = 1;
//	public static final int ANIM_END = 2;
//	private int mStatusHeight = 0;
//
//	private ImageView mWriteNote;
//	private ReaderProgressView mReaderProgressView;
//	private View mStatusBarView;
//	private View mTopBarLayout;
//
//	private View mContentView;
//	private View mChapterListView;
//
//	private SystemBarTintManager mTintManager;
//
//	public ReaderToolsBar(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		mContext = context;
//		initView();
//	}
//
//	public ReaderToolsBar(Context context, View contentView) {
//		super(context);
//		mContext = context;
//		mContentView = contentView;
//		initView();
//		initEvent();
//	}
//
//	public void setChapterListView(View chapterListView)
//	{
//		mChapterListView = chapterListView;
//	}
//
//	private void initView() {
//		try {
//			LayoutInflater.from(mContext).inflate(R.layout.reader_toolbar, this);
//			mPopupView = findViewById(R.id.reader_bottombar_progress);
//			mStatusBarView = findViewById(R.id.reader_topbar_statusbar);
//			mTopBarLayout = findViewById(R.id.reader_topbar_linearlayout);
//			mTopBar = (ReaderTitleBar) findViewById(R.id.reader_topbar);
//			mBottomBar = (ReaderBottomBar) findViewById(R.id.reader_bottombar);
//			mWriteNote = (ImageView) findViewById(R.id.reader_bottombar_write_note);
//			mBottomLayout = (LinearLayout) findViewById(R.id.reader_bottombar_layout);
//			//	mBottomBar.enableButtonDay(true);
//			final Resources resources = getContext().getResources();
//			mTopToolbarHeight = resources.getDimension(R.dimen.reader_title_bar_height);
//			mBottomBarHeight = resources.getDimension(R.dimen.reader_bottom_navigation_height);
//
//			mStatusHeight = getSystemStatusHeight();
//			if(mStatusHeight == 0)
//			{
//				mStatusHeight = ReaderPreferences.getStatusbarHeight();
//			}
//			LayoutParams params = (LayoutParams) mTopBar.getLayoutParams();
//			params.setMargins(params.leftMargin, mStatusHeight, params.rightMargin, params.bottomMargin);
//			mTopBar.setLayoutParams(params);
//
//			LayoutParams statusParams = (LayoutParams) mStatusBarView.getLayoutParams();
//			statusParams.height = mStatusHeight;
//			mStatusBarView.setLayoutParams(statusParams);
//
//			mTopToolbarShow = createAnimation(-mTopToolbarHeight - mStatusHeight, 0, true);
//			mTopToolbarHide = createAnimation(0, -mTopToolbarHeight - mStatusHeight, false);
//			mBottomBarShow = createAnimation(mBottomBarHeight, 0, true);
//			mBottomBarHide = createAnimation(0, mBottomBarHeight, false);
//
//			if(Build.VERSION.SDK_INT >= 21)
//			{
//				mStatusBarView.setBackgroundColor(SystemBarTintManager.DEFAULT_TINT_COLOR);
//				mTintManager = new SystemBarTintManager((Activity) mContext);
//				mTintManager.setStatusBarTintEnabled(false);
//				mTintManager.setStatusBarTintColor(SystemBarTintManager.DEFAULT_TINT_COLOR);//通知栏所需颜色
//			}
//			updateWriteNoteUIResource();
//		} catch (Exception e){
//			UmengStatistics.onEvent(mContext, UmengEvent.READER_TOOL_BAR_INFLATE_EXCEPTION, e.toString());
//			e.printStackTrace();
//		}
//	}
//
//	private void initEvent(){
//		mWriteNote.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				if(mObserver != null)
//				{
//					mObserver.onWriteNoteChecked();
//				}
//			}
//		});
//	}
//
//	public void resume() {
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event)
//	{
//		if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
//		{
//			mTopPopupWindow.dismiss();
//			mTopBar.dismissMenu();
//		}
//		if (!mIsFullScreen)
//		{
//			if (event.getAction() == MotionEvent.ACTION_UP)
//			{
//				int x = (int)event.getX();
//				int y = (int) event.getY();
//				if(mPopupView != null && mPopupView.getVisibility() == View.VISIBLE)
//				{
//					mPopupView.getGlobalVisibleRect(mPopupRect);
//					if(mHideToolbarRect.contains(x, y) && !mPopupRect.contains(x, y))
//					{
//						switchShowing(true);
//					}
//				}
//				else
//				{
//					if(mHideToolbarRect.contains((int)event.getX(), (int) event.getY()))
//					{
//						switchShowing(true);
//					}
//				}
//			}
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent event)
//	{
//		if(mIsFullScreen || mAnimationInProcess)
//		{
//			return false;
//		}
//		return super.dispatchTouchEvent(event);
//	}
//
//	private Animation createAnimation(float fromYDelta, float toYDelta, boolean fadeIn)
//	{
//		AnimationSet anim = new AnimationSet(true);
//
//		if (fadeIn)
//		{
//			anim.addAnimation(new AlphaAnimation(ANIMATION_HIDEALPHA, ANIMATION_SHOWALPHA));
//		}
//		else
//		{
//			anim.addAnimation(new AlphaAnimation(ANIMATION_SHOWALPHA, ANIMATION_HIDEALPHA));
//		}
//
//		anim.addAnimation(new TranslateAnimation(0f, 0f, fromYDelta, toYDelta));
//
//		anim.setDuration(ANIMATION_DURATION);
//		anim.setFillAfter(true);
//		anim.setAnimationListener(mAnimListener);
//		return anim;
//	}
//
//
//	private AnimationListener mAnimListener = new AnimationListener()
//	{
//		private Runnable mRunnable = new Runnable()
//		{
//			public void run()
//			{
//				handleAllAnimationEnd();
//			}
//		};
//
//		public void onAnimationStart(Animation anim)
//		{
//		}
//
//		public void onAnimationRepeat(Animation anim)
//		{
//		}
//
//		public void onAnimationEnd(Animation anim)
//		{
//
//			post(mRunnable);
//			if(anim == mBottomBarShow || anim == mBottomBarHide)
//			{
//
//				if(mObserver != null)
//				{
//					mObserver.onFullScreenChanged(mIsFullScreen, ANIM_END);
//				}
//			}
//
//
//		}
//	};
//
//	private void handleAllAnimationEnd()
//	{
//		if (ALLANIMATION_COUNT == ++mFinishedAnimation)
//		{
//			mAnimationInProcess = false;
//			mFinishedAnimation = 0;
//
//			if (mToHideToolbar)
//			{
//				setVisibility(INVISIBLE);
//				mToHideToolbar = false;
//				if(mContext != null)
//				{
//					fullScreenFlag((Activity) mContext, true);
//				}
//			}
//			else
//			{
//				mToHideToolbar = true;
//			}
//		}
//	}
//
//	public void setContentViewSize(int contentViewWidth, int contentViewHeight)
//	{
//		int height = (int) getResources().getDimension(R.dimen.reader_scrollbar_height);
//		height += (int) getResources().getDimension(R.dimen.reader_toolbar_height);
//		mShowToolbarRect = new Rect(0, height + ReaderPreferences.getStatusbarHeight(), contentViewWidth, contentViewHeight - height);
//		int[] topLocation = { 0, 0 };
//		int[] bottomLocation = { 0, 0 };
//		if(mTopBar != null)
//		{
//			mTopBar.getLocationOnScreen(topLocation);
//		}
//		if(mBottomBar != null)
//		{
//			mBottomBar.getLocationOnScreen(bottomLocation);
//		}
//		mHideToolbarRect = new Rect(0,mStatusHeight+ mContext.getResources().getDimensionPixelOffset(R.dimen.title_height), contentViewWidth, contentViewHeight - mContext.getResources().getDimensionPixelOffset(R.dimen.reader_bottom_navigation_height));
//	}
//
//	public Rect getShowToolbarRect()
//	{
//		return mShowToolbarRect;
//	}
//
//	public void switchShowing()
//	{
//		switchShowing(true);
//	}
//
//	public void switchShowingWithoutAnim()
//	{
//		switchShowing(false);
//	}
//
//	private void switchShowing(boolean animable)
//	{
//		if (mAnimationInProcess) {
//			return;
//		}
//
//		if (null == mTopBarLayout) {
//			return;
//		}
//
//		mTopBarLayout.clearAnimation();
////		mBottomBar.clearAnimation();
//		mBottomLayout.clearAnimation();
//
//		if (VISIBLE == getVisibility())
//		{
//			if(!animable)
//			{
//				if(mContext != null)
//				{
//					fullScreenFlag((Activity) mContext, true);
//				}
//			}
//			if(mTintManager != null)
//			{
//				mTintManager.setStatusBarTintEnabled(false);
//			}
//		}
//		else
//		{
//			if(mContext != null)
//			{
//				fullScreenFlag((Activity) mContext, false);
//			}
//		}
//
//		if (mToHideToolbar)
//		{
////			hidePopupMenu();
//			mIsFullScreen = true;
//			if(mObserver != null)
//			{
//				mObserver.onFullScreenChanged(mIsFullScreen, ANIM_START);
//			}
//			int popHeight = 0;
//			if(mTag != null && mPopupView != null && mPopupView.getVisibility() == View.VISIBLE)
//			{
//				popHeight = mPopupView.getHeight();
//				if(popHeight <= 0)
//				{
//					measureView(mPopupView);
//					popHeight = mPopupView.getMeasuredHeight();
//				}
//				if(mPopupView != null && mPopupView instanceof ReaderProgressView)
//				{
//					((ReaderProgressView)mPopupView).showPop(mBottomBar, 0, false);
//				}
//			}
//			if(mTopTag != null && mTopPopupWindow != null && mTopPopupWindow.isShowing())
//			{
//				mTopTag = null;
//				mTopPopupWindow.dismiss();
//				mTopBar.dismissMenu();
//			}
//			mBottomBarHide = null;
//			mBottomBarHide = createAnimation(0, mBottomBarHeight + popHeight, false);
//
//			if(animable)
//			{
//				mTopBarLayout.startAnimation(mTopToolbarHide);
////				mBottomBar.startAnimation(mBottomBarHide);
//				mBottomLayout.startAnimation(mBottomBarHide);
//			}
//		}
//		else
//		{
//			setVisibility(VISIBLE);
//			mIsFullScreen = false;
//			if(mObserver != null)
//			{
//				mObserver.onFullScreenChanged(mIsFullScreen, ANIM_START);
//			}
//			int popHeight = 0;
//			if(mTag != null && mPopupView != null && mPopupView.getVisibility() == View.VISIBLE)
//			{
//				popHeight = mPopupView.getHeight();
//				if(popHeight <= 0)
//				{
//					measureView(mPopupView);
//					popHeight = mPopupView.getMeasuredHeight();
//				}
//			}
//			mBottomBarShow = null;
//			mBottomBarShow = createAnimation(mBottomBarHeight + popHeight, 0, true);
//			if(animable)
//			{
//
//				mTopBarLayout.startAnimation(mTopToolbarShow);
////				mBottomBar.startAnimation(mBottomBarShow);
//				mBottomLayout.startAnimation(mBottomBarShow);
//
//			}
//		}
//		if(mObserver != null)
//		{
//			mObserver.onFullScreenChanged(mIsFullScreen);
//		}
//		mAnimationInProcess = true;
//		if(!animable)
//		{
//			mAnimationInProcess = false;
//			mFinishedAnimation = 0;
//			if (mToHideToolbar)
//			{
//				setVisibility(INVISIBLE);
//				mToHideToolbar = false;
//			}
//			else
//			{
//				mToHideToolbar = true;
//			}
//			if(mObserver != null)
//			{
//				mObserver.onFullScreenChanged(mIsFullScreen, ANIM_END);
//			}
//		}
//	}
//
//	public void setToolsBarObserver(ReaderToolsBarObserver observer)
//	{
//		mObserver = observer;
//		if(mBottomBar != null)
//		{
//			mBottomBar.setBottomBarObserver(mObserver);
//		}
//		if(mTopBar != null)
//		{
//			mTopBar.setTitleBarObserver(observer);
//		}
//	}
//
//	public void clear() {
//		mTintManager = null;
//		mContentView = null;
//		if(mTopBar != null)
//		{
//			mTopBar.clear();
//			mTopBar = null;
//		}
//		if(mBottomBar != null)
//		{
//			mBottomBar.clear();
//			mBottomBar = null;
//		}
//		if(mTopToolbarShow != null)
//		{
//			mTopToolbarShow.cancel();
//			mTopToolbarShow = null;
//		}
//		if(mTopToolbarHide != null)
//		{
//			mTopToolbarHide.cancel();
//			mTopToolbarHide = null;
//		}
//		if(mBottomBarShow != null)
//		{
//			mBottomBarShow.cancel();
//			mBottomBarShow = null;
//		}
//		if(mBottomBarHide != null)
//		{
//			mBottomBarHide.cancel();
//			mBottomBarHide = null;
//		}
//		mShowToolbarRect = null;
//		mHideToolbarRect = null;
//		mObserver = null;
//		if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
//		{
//			mTopPopupWindow.dismiss();
//			mTopPopupWindow = null;
//		}
//		if(mPopupMap != null)
//		{
//			mPopupMap.clear();
//			mPopupMap = null;
//		}
//		if(mPopupView != null)
//		{
//			mPopupView.destroyDrawingCache();
//			mPopupView = null;
//		}
//		mTag = null;
//		if(mWapConnRemindPop != null && mWapConnRemindPop.isShowing())
//		{
//			mWapConnRemindPop.dismiss();
//			mWapConnRemindPop = null;
//		}
//
//		removeAllViews();
//		setBackgroundDrawable(null);
//	}
//
//	public void setTitle(String title)
//	{
//		mTopBar.setTitle(title);
//	}
//
//	public void setBookMarkBtnEnable(boolean able) {
//
//	}
//
//	private void hidePopupView(boolean isButtonClick)
//	{
////		if(mPopupView != null)
////		{
////			mPopupView.setVisibility(View.GONE);
////
////		}
//		if(mBottomBar != null && !isButtonClick)
//		{
//			mBottomBar.dismissMenu();
//		}
//		if(mPopupView != null && mPopupView instanceof ReaderProgressView)
//		{
//			((ReaderProgressView)mPopupView).showPop(mBottomBar, 0, false);
//		}
//
////		if(mPopupView != null && mPopupView instanceof TTSModeSelectView)
////		{
////			stopShowTimer();
////		}
//	}
//
//	public void showChapterPop()
//	{
//		if(mPopupView != null && mPopupView instanceof ReaderProgressView)
//		{
//			int loc = mBottomBar.getHeight() + mPopupView.getHeight() + getResources().getDimensionPixelSize(R.dimen.dimen_14dp) + getNavigationBarHeight();
//			NLog.i("joker UI",Integer.toString(getNavigationBarHeight()));
//			((ReaderProgressView)mPopupView).showPop(mBottomBar, loc, true);
//		}
//
//	}
//
//	public void updateChapterPop()
//	{
//		if(mPopupView != null && mPopupView instanceof ReaderProgressView)
//		{
//			int loc = mBottomBar.getHeight() + mPopupView.getHeight() + getResources().getDimensionPixelSize(R.dimen.dimen_14dp) + getNavigationBarHeight();
//			NLog.i("joker UI",Integer.toString(getNavigationBarHeight()));
//			((ReaderProgressView)mPopupView).updatePop(mBottomBar, loc);
//		}
//	}
//
//	public int getNavigationBarHeight() {
//		if (!DeviceUtil.isNavigationBarShown((Activity)mContext)){
//			return 0;
//		}
//
//		return DeviceUtil.getNavigationBarHeight(mContext);
//	}
//
//	public void switchPopupMenu(View v,ButtonType tag)
//	{
//		switchPopupMenu(v, tag, true);
//	}
//
//	public void switchPopupMenu(View v,ButtonType tag, boolean isButtonClick)
//	{
////		if(mIsFullScreen)
////		{
////			if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
////			{
////				mTopPopupWindow.dismiss();
////				mTopTag = null;
////				mTopBar.dimisMenu();
////			}
////			if(isPopupShowing())
////			{
////				hidePopupView(isButtonClick);
////			}
////			mBottomBar.dimisMenu();
////			mTag = null;
////			if(mPopupView != null && mPopupView instanceof ReaderProgressView)
////			{
////				((ReaderProgressView)mPopupView).showPop(mBottomBar, 0, false);
////			}
////			return;
////		}
////		if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
////		{
////			mTopPopupWindow.dismiss();
////			mTopTag = null;
////			mTopBar.dimisMenu();
////		}
////		if(isPopupShowing())
////		{
////			hidePopupView(isButtonClick);
////			if(mTag == tag && isButtonClick)
////			{
////				mTag = null;
////				return;
////			}
////		}
////		if(v != null && mPopupMap != null && !mPopupMap.containsKey(tag))
////		{
////			mPopupMap.put(tag, v);
////			mBottomLayout.addView(v, 0);
////		}
////		mPopupView = v;
////		mPopupView.setVisibility(View.VISIBLE);
////		if(!isButtonClick || (tag == ButtonType.TTSBUTTON  &&  mTag == ButtonType.TTSTIMER))
////		{
////			mBottomBar.setSelectedButton(tag);
////		}
////		mTag = tag;
//	}
//
//	public void switchTopPopupMenu(View v, ButtonType tag)
//	{
//		hidePopupView(false);
////		mTag = null;
//		if(mIsFullScreen)
//		{
//			if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
//			{
//				mTopPopupWindow.dismiss();
//				mTopTag = null;
//				mTopBar.dismissMenu();
//			}
//			return;
//		}
//		if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
//		{
//			mTopPopupWindow.dismiss();
//			mTopBar.dismissMenu();
//			if(mTopTag == tag)
//			{
//				return;
//			}
//		}
//		mTopTag = tag;
//		if(mTopPopupWindow == null)
//		{
//			mTopPopupWindow = new PopupWindow(mContext);
//			mTopPopupWindow.setBackgroundDrawable(new BitmapDrawable(mContext.getResources()));
//			mTopPopupWindow.setOutsideTouchable(true);
//			mTopPopupWindow.setTouchInterceptor(new OnTouchListener(){
//
//				public boolean onTouch(View v, MotionEvent event) {
//					if(event.getAction() == MotionEvent.ACTION_OUTSIDE)
//					{
//						return true;
//					}
//					return false;
//				}
//
//			});
//		}
//		mTopPopupWindow.setWidth(LayoutParams.WRAP_CONTENT);
//		mTopPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
//		mTopPopupWindow.setContentView(v);
//		int realScreenWidth = calculatePopWindow().right;
//		int extraPadding = 0;
//		extraPadding = (int) mContext.getResources().getDimension(R.dimen.comic_moreview_extra_height);
//		if(mStatusHeight == 0)
//		{
//			mStatusHeight = ReaderPreferences.getStatusbarHeight();
//		}
//		mTopPopupWindow.showAtLocation(mTopBar, Gravity.TOP, realScreenWidth,(int)(mStatusHeight + mTopToolbarHeight + extraPadding));
//	}
//
//	private Rect calculatePopWindow()
//	{
//		Rect frame = new Rect();
//		((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//		return frame;
//	}
//
//
//	public void hidePopupMenu()
//	{
//		if(mTopPopupWindow != null && mTopPopupWindow.isShowing())
//		{
//			mTopPopupWindow.dismiss();
//			mTopBar.dismissMenu();
//		}
//		if(mPopupView instanceof ReaderProgressView)
//		{
//			((ReaderProgressView)mPopupView).showPop(mBottomBar, 0, false);
//		}
//		hidePopupView(false);
//	}
//	public void setWriteNoteButtonVisible(int visible){
//		if(mWriteNote != null)
//		{
//			mWriteNote.setVisibility(visible);
//		}
//	}
//
//	public void setTopInvisibleButton(ButtonType type)
//	{
//		mTopBar.setInvisibleButton(type);
//	}
//
//	public void setTopVisibleButton(ButtonType type)
//	{
//        if (null != mTopBar) {
//            mTopBar.setVisibleButton(type);
//        }
//	}
//
//	public void invalidateTopButton(ButtonType type) {
//		mTopBar.invalidateButton(type);
//	}
//
//	public void validateTopButton(ButtonType type)
//	{
//		mTopBar.validateButton(type);
//	}
//
//	public boolean isPopupShowing()
//	{
//		return mTag != null && mPopupView != null && mPopupView.getVisibility() == View.VISIBLE || (mTopPopupWindow != null && mTopPopupWindow.isShowing());
//	}
//
//	public void setNightTheme(boolean isNight)
//	{
//		//setNightMode(isNight);
//	}
//
//	public ButtonType getCurPopupTag()
//	{
//		return mTag;
//	}
//
//	public boolean isFullScreen()
//	{
//		return mIsFullScreen;
//	}
//
//	public LayoutParams getTopBarParams()
//	{
//		return (LayoutParams) mTopBar.getLayoutParams();
//	}
//
//	public void updateUIResource()
//	{
//		if(mTopBar != null)
//		{
//			mTopBar.updateUIResource();
//		}
//		if(mBottomBar != null)
//		{
//			mBottomBar.updateUIResource();
//		}
//
//		if(mWriteNote!= null)
//		{
//			updateWriteNoteUIResource();
//		}
//
//		if (mReaderProgressView != null) {
//			mReaderProgressView.updateUIResource();
//		}
//	}
//
//	private void updateWriteNoteUIResource(){
//		if(ReaderPreferences.getNightTheme())
//		{
//			mWriteNote.setImageResource(R.drawable.book_reader_write_note_dark);
//		}
//		else
//		{
//			mWriteNote.setImageResource(R.drawable.book_reader_write_note_default);
//		}
//	}
//
////	public void checkBottomButton(ButtonType button)
////	{
////		if (mBottomBar != null)
////		{
////			mBottomBar.checkSelectedButton(button);
////			mTag = button;
////		}
////	}
////
////	public void setSelectBottomButton(ButtonType button)
////	{
////		if (mBottomBar != null)
////		{
////			mBottomBar.setSelectedButton(button);
////			mTag = button;
////		}
////	}
//
//	public void initAnimation()
//	{
//		mAnimationInProcess = false;
//	}
//
//	private void measureView(View child)
//	{
//		ViewGroup.LayoutParams p = child.getLayoutParams();
//		if (p == null)
//		{
//			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		}
//		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
//		int lpHeight = p.height;
//		int childHeightSpec;
//		if (lpHeight > 0)
//		{
//			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
//		}
//		else
//		{
//			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//		}
//		child.measure(childWidthSpec, childHeightSpec);
//	}
//
//	private int getSystemStatusHeight()
//	{
//		try
//		{
//			Class<?> c = Class.forName("com.android.internal.R$dimen");
//			Object obj = c.newInstance();
//			Field field = c.getField("status_bar_height");
//			int x = Integer.parseInt(field.get(obj).toString());
//			int y = getResources().getDimensionPixelSize(x);
//			return y;
//		}
//		catch (Exception | Error e)
//		{
//			e.printStackTrace();
//		}
//		return 0;
//	}
//
//	public int getStatusHeight() {
//		return mStatusHeight;
//	}
//
//	public boolean hitPopupView(int x, int y)
//	{
//		if(mPopupView != null && mPopupView.getVisibility() == View.VISIBLE)
//		{
//			mPopupView.getGlobalVisibleRect(mPopupRect);
//			if(mPopupRect.contains(x, y))
//			{
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public void setView(ReaderProgressView v)
//	{
//		mReaderProgressView = v;
//	}
//
//	public void fullScreenFlag(Activity activity, boolean isFullScreen)
//	{
//		if(Build.VERSION.SDK_INT >= 21)
//		{
//			fullScreenFlagBarTint(activity, isFullScreen);
////			newfullScreenFlag(activity, isFullScreen);
//		}
//		else
//		{
//			oldfullScreenFlag(activity, isFullScreen);
//		}
//	}
//
//	/**
//	 * 切换状态栏，保证系统状态栏颜色显示系统的颜色
//	 * @param activity
//	 * @param isFullScreen
//	 */
//	private void newfullScreenFlag(Activity activity, boolean isFullScreen) {
//		if(mContentView != null)
//		{
//			if(isFullScreen)
//			{
//				mContentView.setPadding(0, 0, 0, 0);
//			}
//			else
//			{
//				mContentView.setPadding(0, -mStatusHeight, 0, 0);
//			}
//
//		}
//		if(mChapterListView != null)
//		{
//			if(isFullScreen)
//			{
//				mChapterListView.setPadding(0, 0, 0, 0);
//			}
//			else
//			{
//				mChapterListView.setPadding(0, -mStatusHeight, 0, 0);
//			}
//
//		}
//		if(isFullScreen)
//		{
//			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		}
//		else
//		{
//			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		}
//	}
//	/**
//	 * 切换状态栏，但vivo手机系统状态栏异常
//	 * @param activity
//	 * @param isFullScreen
//	 */
//	private void oldfullScreenFlag(Activity activity, boolean isFullScreen)
//	{
//		if(Build.VERSION.SDK_INT >= 21 && mContentView != null)
//		{
//			if(isFullScreen)
//			{
//				activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//				return;
//			}
//			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);
//			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//		}
//		else
//		{
//			simplefullScreenFlag(activity, isFullScreen);
//		}
//	}
//	/**
//	 * 切换状态栏，设置系统状态栏半透明和认为添加黑色背景
//	 * @param activity
//	 * @param isFullScreen
//	 */
//	private void fullScreenFlagBarTint(Activity activity, boolean isFullScreen)
//	{
//		if(Build.VERSION.SDK_INT >= 21)
//		{
//			if(!isFullScreen && mTintManager != null)
//			{
//				mTintManager.setStatusBarTintEnabled(true);
//			}
//		}
//		simplefullScreenFlag(activity, isFullScreen);
//	}
//
//	private void simplefullScreenFlag(Activity activity, boolean isFullScreen)
//	{
//		if (isFullScreen) {
//			activity.getWindow().clearFlags(2048);
//			activity.getWindow().addFlags(1024);
//			return;
//		}
//		activity.getWindow().addFlags(2048);
//	}
//
//	public void updatePublicNum(String publicNum)
//	{
//		if(mTopBar != null)
//		{
//			mTopBar.updateInteractionNum(publicNum);
//		}
//	}
//	public void updatePushSwitchStatus(String pushSwitch){
//		if(mTopBar != null)
//		{
//			mTopBar.updatePushSwitchStatus(pushSwitch);
//		}
//	}
//	public void changeSwitchStatus(String pushSwitch){
//		if(mTopBar != null)
//		{
//			mTopBar.changeSwitchStatus(pushSwitch);
//		}
//	}
//
//	public void setBtnEnable(String pushSwitch){
//		if(mTopBar != null)
//		{
//			mTopBar.setBtnEnable(pushSwitch);
//		}
//	}
//}
