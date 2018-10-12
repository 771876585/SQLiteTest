//package com.yezh.sqlite.sqlitetest;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Rect;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.FragmentTransaction;
//import android.telephony.TelephonyManager;
//import android.text.Editable;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.TextWatcher;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.text.style.ForegroundColorSpan;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.view.Window;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.cmread.bplusc.app.CMActivity;
//import com.cmread.bplusc.bookshelf.LocalLoginUtil;
//import com.cmread.bplusc.bootlogo.BootLogoManager;
//import com.cmread.bplusc.httpservice.constant.ResponseErrorCodeConst;
//import com.cmread.bplusc.httpservice.netstate.CM_ProxyType;
//import com.cmread.bplusc.httpservice.netstate.NetState;
//import com.cmread.bplusc.httpservice.netstate.PhoneState;
//import com.cmread.bplusc.httpservice.util.AesEncryptReader;
//import com.cmread.bplusc.login.AlertDialogForLogin;
//import com.cmread.bplusc.login.KindlyRemindDialog;
//import com.cmread.bplusc.login.LoginAgent;
//import com.cmread.bplusc.login.LoginManager;
//import com.cmread.bplusc.login.LoginModel;
//import com.cmread.bplusc.login.ShowDialogs;
//import com.cmread.bplusc.login.UserInfoParser;
//import com.cmread.bplusc.preferences.ReaderPreferences;
//import com.cmread.bplusc.presenter.GetEnterpriseByClientPresenter;
//import com.cmread.bplusc.presenter.GetExperienceAccountInfoPresenter;
//import com.cmread.bplusc.presenter.model.EnterpriseInfo;
//import com.cmread.bplusc.presenter.model.EnterpriseListObject;
//import com.cmread.bplusc.presenter.util.XML.Doc;
//import com.cmread.bplusc.reader.MessageDef;
//import com.cmread.bplusc.reader.ui.CMReaderAlertDialog;
//import com.cmread.bplusc.reader.ui.mainscreen.BroadcastConst;
//import com.cmread.bplusc.upgrade.UpgradeDialog;
//import com.cmread.bplusc.util.BPlusCApp;
//import com.cmread.bplusc.util.CMTrack;
//import com.cmread.bplusc.util.CMTrackLog;
//import com.cmread.bplusc.util.LogBaseInfo;
//import com.cmread.bplusc.util.ModuleNum;
//import com.cmread.bplusc.util.NLog;
//import com.cmread.bplusc.util.StringUtil;
//import com.cmread.bplusc.util.ToastUtil;
//import com.cmread.bplusc.view.CMTitleBar;
//import com.cmread.bplusc.view.ProgressAlertDialog;
//import com.cmread.bplusc.view.SelectSchoolEditTextWithDel;
//import com.cmread.bplusc.web.TheThirdPartLoginWebPage;
//import com.ytmlab.client.R;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.Collator;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//
//import static android.content.Context.INPUT_METHOD_SERVICE;
//
//public class LoginActivity extends CMActivity implements OnFocusChangeListener, OnItemClickListener, OnClickListener {
//
//	public static boolean mChgAccountFlag;
//	private final String TAG = "LoginActivity";
//	private static final int REQUEST_RESET_PASSWORD = 101;
//	private static final int LOGIN_NORMAL_VIEW = 1;
//	private static final int LOGIN_SELECT_SCHOOL_VIEW = 2;
//	private static final int LOGIN_SOFT_INPUT_VIEW = 3;
//	public static boolean isSwitchAccount = false;
//	public static boolean isBackgroundLoginFailed = false;
//	private static final Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
//	private int loginViewType;
//	private EditText mAccountNumberEditText;
//	private EditText mPasswordEditText;
//	private SelectSchoolEditTextWithDel mSchoolEditText;
//	private Button mLoginButton, mOneKeyLoginButton;
////	private TextView mResetPasswordLinkTextView;
////	private TextView mTpExpireText;
////	private Button mWeiBoLoginButton;
////	private Button mTengXunLoginButton;
////	private Button mFastLoginButton;
//	private LinearLayout mWholeLayout;
//	private CMTitleBar mLoginTitleBar;
//	private RelativeLayout mLoginAppIcon;
//	private LinearLayout mUserInputLayout;
//	private LinearLayout mSearchResultListLayout;
//	private RelativeLayout mSearchResultEmptyLayout;
//	private LinearLayout mLoginButtonLayout;
//	private LinearLayout mBottomImageLayout;
//	private View mSchoolInputLayoutDivider;
//	private View mUsernameTextDivider;
//	private View mPasswordTextDivider;
//	private ListView mSearchResultListView;
//	private List<EnterpriseInfo> mSchoolList; //从请求返回得到的学校的总数据源
//	private List<EnterpriseInfo> mSchoolResultList = new ArrayList<EnterpriseInfo>(); //根据用户输入的搜索信息，经过过滤后的搜索结果数据
//	private SelectSchoolAdapter mSchoolAdapter;
//	private LoginAgent mAgent;
//	private IntentFilter filter = new IntentFilter();
//
//	private String mAccountNumber;
//	private String mPassword;
//	private String mSearchSchoolText;
//	private Context mContext;
//
//	private boolean mChangePasswordFlag;
//	private boolean mClearEditText;
//	private boolean mIsTpTokenExpired;
//
//	private int mStatus = 0;
//	private int mMsgWhat;
//	private int mMsgArg1;
//	private String event;
//	private String value;
//	private boolean mIsNewUser = false;
//	private AlertDialogForLogin mLoadingDialog;
//	private TpLoginType mTpLoginType;
//
//	private final int mWlanRegistId = 2001;
//
//	private boolean mBakLoginTypeTemp = false;
//	Build mBuild = null;
//	private static LoginActivity mInstance;
//	private boolean mVerifyPasswordFailed;
//
//	private boolean isOneKeyLogin;
//	private EnterpriseListObject enterpriseListObject;
//	private ProgressAlertDialog mDialog;
//
//	private ImageButton accountDelBtn;
//	private ImageButton passwordDelBtn;
//	private ImageButton passwordSeeBtn;
//	private LinearLayout accountDelBtnLayout;
//	private LinearLayout passwordDelBtnLayout;
//	private LinearLayout passwordSeeBtnLayout;
//	private boolean displayFlg;
//	private boolean isPasswordEditHasFocus = false;
//	private boolean mAccountNumberFirstInit = true;
//
//	public static LoginActivity instance()
//	{
//		return mInstance;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.login_activity);
//		mContext = this;
//		loginViewType = LOGIN_NORMAL_VIEW;
//		mBuild = new Build();
//
//		if (getIntent() != null) {
//			NLog.e(TAG, "mIsTpTokenExpired"+getIntent()
//					.getBooleanExtra("tpTokenExpired", false));
//			mClearEditText = getIntent()
//					.getBooleanExtra("clearEditText", false);
//			mIsTpTokenExpired = getIntent()
//					.getBooleanExtra("tpTokenExpired", false);
//
//			mVerifyPasswordFailed = getIntent().getBooleanExtra("verifyPasswordFailed", false);
//
//		}
//		mStatus = 1;
//		mInstance = this;
//		mAgent = LoginManager.getAgent();
//
//		initView();
//		initData();
//		dialLoginButtonState();
//		registerReceiver(mBroadcastReceiver, filter);// Added For Guest
//
////		checkLoginType();
//	}
//
//	private void checkLoginType(){
//		if (LoginModel.getInstance().getLoginType() == LoginModel.LOGIN_TYPE_WAP) {
//			if (ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_VISITOR_
//            		|| ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_CANNOT) {
//				//鉴权请求还没回来
//				showDialog();
//			}
//			if (ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_WAP) {
//				//cmwap鉴权请求已成功
//				if (BPlusCApp.getEnterpriseListObject() == null) {
//					//获取企业信息请求还没回来
//					showDialog();
//				} else {
//					//获取企业信息请求成功
//					finish();
//				}
//			}
//		}
//	}
//
//    public void showDialog(){
//        if (mDialog == null)
//        {
//            mDialog = new ProgressAlertDialog(this, false);
//            mDialog.setMessage(this.getString(R.string.boutique_reserve_progress_info));
//            mDialog.setCancelable(false);
//            mDialog.show();
//        }
//    }
//
//    public void dismissDialog(){
//        if (mDialog != null && mDialog.isShowing()) {
//            mDialog.dismiss();
//        }
//        mDialog = null;
//    }
//
//	//judge是否为android L型号手机
//	private String getDeviceInfo(){
//		if(mBuild != null){
//			return Build.MODEL;
//		}else{
//			return "" ;
//		}
//	}
//
//	private Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == MessageDef.GET_EXPERIENCE_ACCOUNT_INFO) {
//				if (msg.arg1 == 0) {
//					Bundle bundle = msg.getData();
//					if (bundle != null) {
//						String accountName = bundle.getString("accountName");
//						String password = bundle.getString("password");
//						String enterpriseId = bundle.getString("enterpriseId");
//						BPlusCApp.mSelectedEnterprisedId = enterpriseId;
//						ReaderPreferences.setExperienceAccount(accountName);
//						BPlusCApp.mSupportUnityCertificate = false;
//						oneKeyLogin(accountName, AesEncryptReader.decryptAesPassword(password, BPlusCApp.getClientVersion() + BPlusCApp.getPassword()));
//					}
//				} else {
//					if (mLoadingDialog != null)
//						mLoadingDialog.setProgressCompleted(true);
//					ToastUtil.showMessage(LoginActivity.this,
//							R.string.toast_get_experience_account_failed,
//							Toast.LENGTH_SHORT);
//				}
//				return;
//			}
//
////			if (msg.what == MessageDef.SELECT_CUSTOMER) {
////				enterpriseListObject = (EnterpriseListObject)msg.obj;
////				if (mLoadingDialog != null)
////					mLoadingDialog.setProgressCompleted(true);
////				return;
////			}
//
//			mMsgWhat = msg.what;
//			mMsgArg1 = msg.arg1;
//			if (msg.arg2 == 1) {
//				mChangePasswordFlag = true;
//			} else {
//				mChangePasswordFlag = false;
//			}
//			if (mLoadingDialog != null)
//				mLoadingDialog.setProgressCompleted(true);
//		}
//	};
//
//	private void handleResult(int what, int resultCode) {
//		NLog.i("LoginActivity", String.valueOf(what));
//		isAuthenticateFinish = true;
//		switch (what) {
//		case LoginModel.FLAG_AUTHENTICATE_SUCCESS:
//			NLog.i("LoginActivity", "case LoginModel.LOGIN_SUCCESS:");
//			event = "login_sucess";
//			value = "ls_login_sucess";
//			startCMTrackOnEvent(event, value);
//
//			String errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 6);
//			String msg = "LoginActivity.handleResult Authenticate success!";
//			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
//
//			if (!LoginModel.getUserPermission())
//			{
//				event = "login_visitorLogin";
//				value = "";
//				startCMTrackOnEvent(event, value);
//			}
//			if(LoginModel.Instance(LoginActivity.this).getLoginType() == LoginModel.LOGIN_TYPE_TP_AUTH)
//			{
//				ReaderPreferences.setTpLoginType(mTpLoginType.ordinal());
//			}
//			if(CMTrack.CAN_TRACK)
//			{
//				if (LoginModel.Instance(LoginActivity.this).getLoginType() == LoginModel.LOGIN_TYPE_WAP)
//				{
//					String eventKey = "rate_wapL_wapLogSu";
//					startCMTrackOnEvent(eventKey, "");
//				}
//				else if (LoginModel.Instance(LoginActivity.this).getLoginType() == LoginModel.LOGIN_TYPE_SMS_DIRECT_MOBILE)
//				{
//					String mEventKey = "rate_mesDirL_mesDSu";
//					startCMTrackOnEvent(mEventKey, "");
//				}
//				else if (LoginModel.Instance(LoginActivity.this).getLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD)
//				{
//					String mEventKey = "rate_usLog_usLoSuc";
//					startCMTrackOnEvent(mEventKey, "");
//				}
//				else if (LoginModel.Instance(LoginActivity.this).getLoginType() == LoginModel.LOGIN_TYPE_TP_AUTH)
//				{
//					int tpLoginType = ReaderPreferences.getTpLoginType();
//					if(tpLoginType == 1)
//					{
//						String eventQQ = "rate_qqLog_qqLogSuc";
//						startCMTrackOnEvent(eventQQ, "");
//					}
//					else if(tpLoginType == 2)
//					{
//						String eventSina = "rate_sinaL_sinaLogS";
//						startCMTrackOnEvent(eventSina, "");
//					}
//				}
//				else
//				{
//					NLog.d("LoginActivity", "login sucess type");
//				}
//			}
//			mChgAccountFlag = false;
//			if (!"".equals(ReaderPreferences.getExperienceAccount())
//					&& !mAccountNumber.equals(ReaderPreferences.getExperienceAccount())) {
//				//如果体验账号不为空，并且这次登录是用户名密码登录，则清空体验账号，这样下次登录就知道这次登录是用户名密码登录了
//				ReaderPreferences.setExperienceAccount("");
//			}
////			if (enterpriseListObject != null && enterpriseListObject.getEnterpriseList() != null
////					&& enterpriseListObject.getEnterpriseList().size() > 0) {
////				//登录成功，并且获取到企业信息
////				if (enterpriseListObject.getEnterpriseList().size() > 1) {
////					BPlusCApp.setEnterpriseListObject(enterpriseListObject);
////					Intent intent = new Intent(this, SelectCustomerActivity.class);
////	                Bundle bundle = new Bundle();
////	                bundle.putSerializable(SelectCustomerActivity.ENTERPRISE_LIST, enterpriseListObject);
////	                bundle.putInt(SelectCustomerActivity.FROM_LOGIN_ACTIVITY, 1);
////	                intent.putExtras(bundle);
////					startActivity(intent);
////				} else if (enterpriseListObject.getEnterpriseList().size() == 1) {
////					BPlusCApp.setEnterpriseListObject(enterpriseListObject);
////					EnterpriseInfo enterpriseInfo = enterpriseListObject.getEnterpriseList().get(0);
////					BPlusCApp.mEnterprisedId = enterpriseInfo.getEnterpriseId();
////					ReaderPreferences.setEnterprisedId(BPlusCApp.mEnterprisedId);
////				}
////			} else {
////				//咪咕阅读账号可以鉴权成功，但不属于任何一个企业，给出登录错误的提示，并停留在登录页。
////				//清除登录信息是为了防止用户这时手动杀死程序后，下次登录还用这个咪咕账号登录。
////				ReaderPreferences.saveAccount("");
////				ReaderPreferences.savePassword("");
////				ReaderPreferences.setLastLoginType(LoginModel.LOGIN_TYPE_CANNOT);
////				mLoadingDialog.dismiss();
////				Toast.makeText(this, R.string.register_username_enterpriseid_error2,
////						Toast.LENGTH_LONG).show();
////		        if (ReaderPreferences.getEnterprisedId() != null && !"".equals(ReaderPreferences.getEnterprisedId())) {
////		        	BPlusCApp.mEnterprisedId = ReaderPreferences.getEnterprisedId();
////		        }
////				return;
////			}
//			if (BPlusCApp.mSelectedEnterprisedId != null) {
//				ReaderPreferences.setEnterprisedId(BPlusCApp.mSelectedEnterprisedId);
//				BPlusCApp.mEnterprisedId = BPlusCApp.mSelectedEnterprisedId;
//				ReaderPreferences.setSupportUnityCertificate(BPlusCApp.mSupportUnityCertificate);
//				ReaderPreferences.setEnterprisedName(BPlusCApp.mEnterpriseName);
//			}
//			if (resultCode == ResponseErrorCodeConst.REQUEST_STATUS_SUCCESS_INT) {
//				if (ReaderPreferences.getFirstOrder()) {
//					ToastUtil.showMessage(LoginActivity.this,
//							R.string.add_free_reading_success_tip,
//							Toast.LENGTH_SHORT);
//				}
//			} else if (resultCode == ResponseErrorCodeConst.ORDER_FAIL_INT) {
//				ToastUtil.showMessage(LoginActivity.this,
//						R.string.add_free_reading_fail_tip,
//						Toast.LENGTH_SHORT);
//			}
//			BootLogoManager.getInstance().getClientBootLogo();
//			ReaderPreferences.setVersionName(getVersionName(this));
//			loginSuccess();
//			finish();
//			break;
//		case LoginModel.FLAG_AUTHENTICATE_FAILED:
//			NLog.i("LoginActivity", "case LoginModel.LOGIN_FAILED:");
//			mChgAccountFlag = false;
//			if(LoginModel.Instance(this).getLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD ){
//				if(mBakLoginTypeTemp && ReaderPreferences.getDymPwdLoginStatus() == false && ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD){
//					ReaderPreferences.setDymPwdLoginStatus(true);
//					mBakLoginTypeTemp = false;
//				}
//				LoginModel.setUserID(ReaderPreferences.getUserID());
//				LoginModel.Instance(this).setLoginAccountPassword(ReaderPreferences.getAccount(),
//						ReaderPreferences.getPassword());
//			}
//			String errorCode1 = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 7);
//			String msg1 = "LoginActivity.handleResult Authenticate failed! resultCode="+resultCode;
//			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode1 , null), null, msg1);
//
//			if (resultCode == ResponseErrorCodeConst.TP_LOGIN_EXPIRED_INT) {
////				mTpExpireText.setVisibility(View.VISIBLE);
//				if (mLoadingDialog != null)
//					mLoadingDialog.setProgressCompleted(true);
//				showAlertDialog(getString(R.string.default_title_text), getString(R.string.tp_login_expire));
//				return;
//			} else if (resultCode == ResponseErrorCodeConst.NOT_IN_WHITELIST_ERROR) {
//				ToastUtil.showMessage(LoginActivity.this,
//						R.string.login_user_error,
//						Toast.LENGTH_SHORT);
//				return;
//			} else if (resultCode == ResponseErrorCodeConst.VERIFY_PASSWORD_FAILED_INT) {
//					if (ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_VISITOR_
//					|| ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_CANNOT
//					|| LoginModel.Instance(LoginActivity.this).isSessionTimeout())
//					{
////						showAlertDialogPasswordVerifyFailed();
//						ToastUtil.showMessage(LoginActivity.this,
//								R.string.server_response_7076,
//								Toast.LENGTH_SHORT);
//						return;
//					}
//			} else if (resultCode == ResponseErrorCodeConst.REGISTER_FAIL_INT) {
//				ToastUtil.showMessage(LoginActivity.this,
//						R.string.register_white_list_fail_tip,
//						Toast.LENGTH_SHORT);
//				return;
//			} else if (resultCode == ResponseErrorCodeConst.AUTHENTICATION_FAIL_INT) {
//				ToastUtil.showMessage(LoginActivity.this,
//						R.string.identity_authentication_fail_tip,
//						Toast.LENGTH_SHORT);
//				return;
//			}
//		case LoginModel.FLAG_AUTHENTICATE_GET_TOKEN_FAILED:
//			NLog.i("LoginActivity", "case LoginModel.GET_TOKEN_FAILED:");
//			event = "login_failed";
//			value = "lg_login_failed";
//			startCMTrackOnKVEvent(event, value, String.valueOf(resultCode));
//			mChgAccountFlag = false;
//			LoginModel.Instance(this).restoreLoginStatus();
//
//			if ((mIsNewUser && resultCode != -1) ||what ==LoginModel.FLAG_AUTHENTICATE_GET_TOKEN_FAILED) {
//				LoginModel.setUserID(ReaderPreferences.getUserID());
//				LoginModel.Instance(LoginActivity.this).setLoginAccountPassword(ReaderPreferences.getAccount(),
//												ReaderPreferences.getPassword());
//				if (resultCode == ResponseErrorCodeConst.MULTIPLE_USER_BIND_ERROR) {
//					ToastUtil.showMessage(LoginActivity.this,
//							R.string.toast_multiple_user_bind_error,
//							Toast.LENGTH_SHORT);
//				} else {
//					ToastUtil.showMessage(LoginActivity.this,
//							R.string.toast_change_account_failed,
//							Toast.LENGTH_SHORT);
//				}
//
//				if (mAgent != null)
//				{
//					mAgent.onLoginFail(String.valueOf(resultCode));
//				}
//				finish();
//				break;
//			} else {
//				if (resultCode == ResponseErrorCodeConst.MULTIPLE_USER_BIND_ERROR) {
//					ToastUtil.showMessage(LoginActivity.this,
//							R.string.toast_multiple_user_bind_error,
//							Toast.LENGTH_SHORT);
//				} else {
//					if(!LoginModel.Instance(this).mCancelFlag){
//						ShowDialogs.alertLoginFailed(LoginActivity.this,
//								resultCode, mDailogHandler);
//						}
//				}
//			}
////			if (mAgent != null)
////			{
////				mAgent.onLoginFail(String.valueOf(resultCode));
////			}
////			finish();
//
//			break;
//		case LoginModel.FLAG_AUTHENTICATE_PLATFORM_UPGRADE:
//			ShowDialogs.showPlatformUpgradeDialog(LoginActivity.this,
//					mDailogHandler);
//			NLog.i("LoginActivity", "case LoginModel.PLATFORM_UPGRADE:");
//			break;
//		case LoginModel.FLAG_AUTHENTICATE_NON_MANDATORY_UPDATE:
//			NLog.i("LoginActivity", "case LoginModel.NON_MANDATORY_UPDATE:");
//			if (!"".equals(ReaderPreferences.getExperienceAccount())
//					&& !mAccountNumber.equals(ReaderPreferences.getExperienceAccount())) {
//				ReaderPreferences.setExperienceAccount("");
//			}
////			if (enterpriseListObject != null && enterpriseListObject.getEnterpriseList() != null
////					&& enterpriseListObject.getEnterpriseList().size() > 0) {
////				if (enterpriseListObject.getEnterpriseList().size() > 1) {
////					BPlusCApp.setEnterpriseListObject(enterpriseListObject);
////					Intent intent = new Intent(this, SelectCustomerActivity.class);
////	                Bundle bundle = new Bundle();
////	                bundle.putSerializable(SelectCustomerActivity.ENTERPRISE_LIST, enterpriseListObject);
////	                bundle.putInt(SelectCustomerActivity.FROM_LOGIN_ACTIVITY, 1);
////	                intent.putExtras(bundle);
////					startActivity(intent);
////				} else if (enterpriseListObject.getEnterpriseList().size() == 1) {
////					BPlusCApp.setEnterpriseListObject(enterpriseListObject);
////					EnterpriseInfo enterpriseInfo = enterpriseListObject.getEnterpriseList().get(0);
////					BPlusCApp.mEnterprisedId = enterpriseInfo.getEnterpriseId();
////					ReaderPreferences.setEnterprisedId(BPlusCApp.mEnterprisedId);
////				}
////			} else {
////				ReaderPreferences.saveAccount("");
////				ReaderPreferences.savePassword("");
////				ReaderPreferences.setLastLoginType(LoginModel.LOGIN_TYPE_CANNOT);
////				mLoadingDialog.dismiss();
////				Toast.makeText(this, R.string.register_username_enterpriseid_error2,
////						Toast.LENGTH_LONG).show();
////		        if (ReaderPreferences.getEnterprisedId() != null && !"".equals(ReaderPreferences.getEnterprisedId())) {
////		        	BPlusCApp.mEnterprisedId = ReaderPreferences.getEnterprisedId();
////		        }
////				return;
////			}
//			if (BPlusCApp.mSelectedEnterprisedId != null) {
//				ReaderPreferences.setEnterprisedId(BPlusCApp.mSelectedEnterprisedId);
//				BPlusCApp.mEnterprisedId = BPlusCApp.mSelectedEnterprisedId;
//				ReaderPreferences.setSupportUnityCertificate(BPlusCApp.mSupportUnityCertificate);
//				ReaderPreferences.setEnterprisedName(BPlusCApp.mEnterpriseName);
//			}
//			showUpadteDialog(UpgradeDialog.TYPE_NONMANDATORY_UPDATE_DIALOG);
//			BootLogoManager.getInstance().getClientBootLogo();
//			ReaderPreferences.setVersionName(getVersionName(this));
//			loginSuccess();
//			finish();
//			break;
//		case LoginModel.FLAG_AUTHENTICATE_MANDATORY_UPDATE:
//			NLog.i("LoginActivity", "case LoginModel.MANDATORY_UPDATE:");
//			if (!"".equals(ReaderPreferences.getExperienceAccount())
//					&& !mAccountNumber.equals(ReaderPreferences.getExperienceAccount())) {
//				ReaderPreferences.setExperienceAccount("");
//			}
//			if (BPlusCApp.mSelectedEnterprisedId != null) {
//				ReaderPreferences.setEnterprisedId(BPlusCApp.mSelectedEnterprisedId);
//				BPlusCApp.mEnterprisedId = BPlusCApp.mSelectedEnterprisedId;
//				ReaderPreferences.setSupportUnityCertificate(BPlusCApp.mSupportUnityCertificate);
//				ReaderPreferences.setEnterprisedName(BPlusCApp.mEnterpriseName);
//			}
//			showUpadteDialog(UpgradeDialog.TYPE_FORCE_UPDATE_DIALOG);
//			BootLogoManager.getInstance().getClientBootLogo();
//			ReaderPreferences.setVersionName(getVersionName(this));
//			loginSuccess();
//			finish();
//			break;
//		}
//	}
//
//	private Handler mDailogHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case AlertDialogForLogin.PROGRESS_FINISH_OK:
//				KindlyRemindDialog.showBindDialog(
//						mAuthenticateSuccessHandler,
//						AlertDialogForLogin.PROGRESS_FINISH_OK);
//				break;
//			case AlertDialogForLogin.PROGRESS_FINISH_FIALED:
//				ShowDialogs.alertLoginFailed(LoginActivity.this, -1,
//						mDailogHandler);
//				LoginModel.Instance(LoginActivity.this)
//						.cancelAuthenticateRequest();
//				break;
//			case AlertDialogForLogin.FLAG_RELOGIN:
//				doRelogin();
//
//			case 1:
//				break;
//			case -1:
//				if (mAgent != null)
//				{
//					mAgent.onLoginFail(String.valueOf(-1));
//				}
////				finish();
//				break;
//			case -2:
//				if (mAgent != null)
//				{
//					mAgent.onLoginFail(String.valueOf(-1));
//				}
//				break;
//			}
//		}
//	};
//
//	private void doRelogin(){
//		int loginType = LoginModel.Instance(this).getLoginType();
//		if(loginType == LoginModel.LOGIN_TYPE_TP_AUTH ){
//			LoginModel.Instance(this).TpAuthLogin(this, true, mHandler);
//			startLoading();
//		}
//		else if(loginType == LoginModel.LOGIN_TYPE_USER_PASSWORD){
//			wlanLogin();
//		}
//		else if(loginType == LoginModel.LOGIN_TYPE_SMS_DIRECT_MOBILE
//			||loginType == LoginModel.LOGIN_TYPE_WAP){
//			mobileFastLogin();
//		}else{
//			LoginModel.Instance(LoginActivity.this).autoLogin(true,	mHandler);
//			startLoading();
//		}
//	}
//
////	/**
////	 * when change account failed , do logout.
////	 * */
////	public void logout() {
////		ReaderPreferences.load(this);
////		ReaderPreferences.setLoginMode(true);
////		ReaderPreferences.setFirstInListenBookChannel(true);
////		ReaderPreferences.save();
////
////		SendNotificationUtil.cancelAllNotification(this);
////		Intent intent = new Intent(this, LocalMainActivity.class);
////		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////		intent.putExtra(LocalMainActivity.LOGOUT, true);
////		startActivity(intent);
////
////		finish();
////		// CM_Utility.setCookie(null);
////		// ICM_HttpConnectOperation httpOP =
////		// CM_HttpConnectFactory.createNetworkConnectOperation(0,
////		// CM_Utility.Instance());
////		// httpOP.cleanCookie();
////	}
//
//	protected void onResume() {
//		this.getWindow().setBackgroundDrawableResource(R.color.white);
//		super.onResume();
//		if(!StringUtil.isNullOrEmpty(getDeviceInfo()) && getDeviceInfo().contains("Nexus")){
//			setContentView(R.layout.login_activity);
//			initView();
//			initData();
//			this.getWindow().getDecorView().postInvalidate();
//		}
//	}
//
//	public void onPause() {
//		super.onPause();
//		// SettingCustomer.setScreenOffTime(this,
//		// ReaderPreferences.getSystemScreenOffTimeout());
//		InputMethodManager imm = (InputMethodManager) this
//				.getSystemService(INPUT_METHOD_SERVICE);
//		imm.hideSoftInputFromWindow(mAccountNumberEditText.getWindowToken(),
//				InputMethodManager.HIDE_NOT_ALWAYS);
//		imm.hideSoftInputFromInputMethod(
//				mAccountNumberEditText.getWindowToken(),
//				InputMethodManager.HIDE_NOT_ALWAYS);
//		imm.hideSoftInputFromWindow(mPasswordEditText.getWindowToken(),
//				InputMethodManager.HIDE_NOT_ALWAYS);
//		imm.hideSoftInputFromInputMethod(mPasswordEditText.getWindowToken(),
//				InputMethodManager.HIDE_NOT_ALWAYS);
//	}
//
//	public void onDestroy() {
//		try {
//			mAgent = null;
//			mStatus = 0;
//			mInstance = null;
//			unregisterReceiver(mBroadcastReceiver); // Added For Guest
//			mHintHandler = null;
//			isSwitchAccount = false;
//			isBackgroundLoginFailed = false;
//			if (mDailogHandler != null) {
//				mDailogHandler.removeCallbacksAndMessages(null);
//				mDailogHandler = null;
//			}
//
//			if (mAccountNumberEditText != null) {
//				mAccountNumberEditText.setBackgroundResource(0);
//				mAccountNumberEditText = null;
//			}
//
//			if (mPasswordEditText != null) {
//				mPasswordEditText.setBackgroundResource(0);
//				mPasswordEditText = null;
//			}
//
//			if (mSchoolEditText != null) {
//				mSchoolEditText.setBackgroundResource(0);
//				mSchoolEditText = null;
//			}
//
//			if (mLoginButton != null) {
//				mLoginButton.setBackgroundResource(0);
//				mLoginButton = null;
//			}
//
//			if(accountDelBtn != null){
//				try{
//					accountDelBtn.setBackground(null);
//				}catch (Throwable t){
//					accountDelBtn.setBackgroundDrawable(null);
//				}
//				accountDelBtn = null ;
//			}
//
//			if(passwordDelBtn != null){
//				try{
//					passwordDelBtn.setBackground(null);
//				}catch (Throwable t){
//					passwordDelBtn.setBackgroundDrawable(null);
//				}
//				passwordDelBtn = null ;
//			}
//
//			if(passwordSeeBtn != null){
//				try{
//					passwordSeeBtn.setBackground(null);
//				}catch (Throwable t){
//					passwordSeeBtn.setBackgroundDrawable(null);
//				}
//				passwordSeeBtn = null ;
//			}
//
//			if (mOneKeyLoginButton != null) {
//				mOneKeyLoginButton.setBackgroundResource(0);
//				mOneKeyLoginButton = null;
//			}
//
//			if (mWholeLayout != null) {
//				mWholeLayout = null;
//			}
//
//			if (mLoginTitleBar != null) {
//				mLoginTitleBar.removeAllViews();
//				mLoginTitleBar = null;
//			}
//
//			if (mLoginAppIcon != null) {
//				mLoginAppIcon.removeAllViews();
//				mLoginAppIcon = null;
//			}
//
//			if (mUserInputLayout != null) {
//				mUserInputLayout = null;
//			}
//
//			if (mSearchResultListLayout != null) {
//				mSearchResultListLayout.removeAllViews();
//				mSearchResultListLayout = null;
//			}
//
//			if (mSearchResultEmptyLayout != null) {
//				mSearchResultEmptyLayout.removeAllViews();
//				mSearchResultEmptyLayout = null;
//			}
//
//			if (mLoginButtonLayout != null) {
//				mLoginButtonLayout.removeAllViews();
//				mLoginButtonLayout = null;
//			}
//
//			if (mBottomImageLayout != null) {
//				mBottomImageLayout.removeAllViews();
//				mBottomImageLayout = null;
//			}
//
//			mSchoolInputLayoutDivider = null;
//			mUsernameTextDivider = null;
//			mPasswordTextDivider = null;
//
////			if (mSearchResultListView != null) {
////				mSearchResultListView = null;
////			}
//
////			if (mSchoolList != null) {
////				if (mSchoolList.size() > 0) {
////					mSchoolList.clear();
////				}
////				mSchoolList = null;
////			}
//
////			if (mSchoolResultList != null) {
////				if (mSchoolResultList.size() > 0) {
////					mSchoolResultList.clear();
////				}
////				mSchoolResultList = null;
////			}
//
////			if (mSchoolAdapter != null) {
////				mSchoolAdapter = null;
////			}
//
//			filter = null;
//			mContext = null;
//
//			if (mLoadingDialog != null) {
//				mLoadingDialog.onDestroy();
//			}
//
//			if (mTpLoginType != null) {
//				mTpLoginType = null;
//			}
//			mBuild = null;
////			enterpriseListObject = null;
//			if (mDialog != null) {
//				mDialog = null;
//			}
//		}catch (Throwable throwable){
//
//		}
//		super.onDestroy();
//	}
//
//	public int status() {
//		return mStatus;
//	}
//
//	private void initView() {
////		ScrollView mLoginScrollView = (ScrollView) findViewById(R.id.login_scrollview);
////		mLoginScrollView.setBackgroundColor(ResourcesUtil.getColor(R.color.bookshelf_bg_color));
//
//		mWholeLayout = (LinearLayout) findViewById(R.id.login_view);
//		mWholeLayout.setOnClickListener(this);
//		mLoginButtonLayout = (LinearLayout) findViewById(R.id.login_button_layout);
//		mLoginTitleBar = (CMTitleBar) findViewById(R.id._title_bar);
//		mLoginAppIcon = (RelativeLayout) findViewById(R.id.login_app_icon_layout);
//		mUserInputLayout = (LinearLayout) findViewById(R.id.user_input_layout);
//		mSchoolInputLayoutDivider = (View) findViewById(R.id.school_input_layout_divider);
//		mUsernameTextDivider = (View) findViewById(R.id.usernametext_divider);
//		mPasswordTextDivider = (View) findViewById(R.id.passwordtext_divider);
//		mSearchResultListLayout = (LinearLayout) findViewById(R.id.search_result_list_layout);
//		mSearchResultEmptyLayout = (RelativeLayout) findViewById(R.id.search_result_empty_layout);
//		mSearchResultEmptyLayout.setOnClickListener(this);
//		mBottomImageLayout = (LinearLayout) findViewById(R.id.bottom_image_layout);
//		mAccountNumberEditText = (EditText)findViewById(R.id.usernametext);
//		mAccountNumberEditText.addTextChangedListener(mAccountWatcher);
//		mAccountNumberEditText.setOnFocusChangeListener(this);
////		mAccountNumberEditText.setDrawable(this.getResources().getDrawable(R.drawable.user_name), null, null, null, true);
////		int padding = this.getResources().getDimensionPixelSize(R.dimen.login_EditText_padding);
////		mAccountNumberEditText.setPadding(padding,0, padding, 0);
//		mAccountNumberEditText.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_DONE) {
//					if (StringUtil.isNullOrEmpty(mSchoolEditText.getText().toString())
//							|| StringUtil.isNullOrEmpty(mAccountNumberEditText.getText().toString())
//							|| StringUtil.isNullOrEmpty(mPasswordEditText.getText().toString())) {
//				    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//				        if (imm.isActive()) {
//				            imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
//				        }
//					} else {
//						String errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 1);
//						String msg = "LoginActivity.userPasswordLogin() entered";
//						CMTrackLog.getInstance().infoLog(LoginActivity.this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
//						mChgAccountFlag = true;
//						wlanLogin();
//					}
//					return true;
//				}
//				return  false ;
//			}
//		});
//
//		mPasswordEditText = (EditText)findViewById(R.id.passwordtext);
//		mPasswordEditText.addTextChangedListener(mTextWatcher);
//		mPasswordEditText.setOnFocusChangeListener(this);
//		mPasswordEditText.setOnEditorActionListener(new EditText.OnEditorActionListener(){
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_DONE) {
//					if (StringUtil.isNullOrEmpty(mSchoolEditText.getText().toString())
//							|| StringUtil.isNullOrEmpty(mAccountNumberEditText.getText().toString())
//							|| StringUtil.isNullOrEmpty(mPasswordEditText.getText().toString())) {
//				    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//				        if (imm.isActive()) {
//				            imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
//				        }
//					} else {
//						String errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 1);
//						String msg = "LoginActivity.userPasswordLogin() entered";
//						CMTrackLog.getInstance().infoLog(LoginActivity.this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
//						mChgAccountFlag = true;
//						wlanLogin();
//					}
//					return true;
//				}
//				return  false ;
//			}
//		});
//
//		mSchoolEditText = (SelectSchoolEditTextWithDel) findViewById(R.id.school_text);
//		mSchoolEditText.setOnFocusChangeListener(this);
//		mSchoolEditText.setOnClickListener(this);
//		mSchoolEditText.addTextChangedListener(mSelectSchoolWatcher);
//		if (BPlusCApp.getEnterpriseListObject() == null) {
//			mSchoolEditText.setFocusable(false);
//		}
//		mSearchResultListView = (ListView) findViewById(R.id.search_result_list);
//		mLoginAppIcon.getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
//
///*		if (Build.VERSION.SDK_INT >= 11)
//		{
//			mPasswordEditText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//
//				@Override
//				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//					NLog.d("zh.d", "onActionItemClicked");
//					return false;
//				}
//
//				@Override
//				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//					NLog.d("zh.d", "onCreateActionMode");
//					return false;
//				}
//
//				@Override
//				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//					NLog.d("zh.d", "onPrepareActionMode");
//					return false;
//				}
//
//				@Override
//	            public void onDestroyActionMode(ActionMode mode) {
//	            }
//	        });
//		}*/
//
////		mPasswordEditText.setDrawable(null, null,this.getResources().getDrawable(R.drawable.icon_password_hide), null, true);
////		mPasswordEditText.setPadding(padding,0, padding, 0);
////		DisplayMetrics dm = getResources().getDisplayMetrics();
////	    if(dm.widthPixels == 1440)
////	    {
////	    	mAccountNumberEditText.setPadding(25, 0, 40, 0);
////	    	mPasswordEditText.setPadding(25, 0, 40, 0);
////	    }
////	    else if(dm.widthPixels <=480){
////	    	mAccountNumberEditText.setPadding(25, 0, 10, 0);
////	    	mPasswordEditText.setPadding(25, 0, 10, 0);
////	    }
////	    else
////	    {
////	    	mAccountNumberEditText.setPadding(15, 0, 30, 0);
////	    	mPasswordEditText.setPadding(15, 0, 30, 0);
////	    }
//		mLoginButton = (Button) findViewById(R.id.savelogin);
//		mLoginButton.setOnClickListener(this);
//		mOneKeyLoginButton = (Button) findViewById(R.id.one_key_login);
//		mOneKeyLoginButton.setOnClickListener(this);
//
//		accountDelBtn = (ImageButton) findViewById(R.id.login_username_delete);
//		accountDelBtnLayout =  (LinearLayout) findViewById(R.id.login_username_delete_layout);
//		accountDelBtn.setOnClickListener(this);
//		accountDelBtnLayout.setOnClickListener(this);
//
//		passwordDelBtn = (ImageButton) findViewById(R.id.login_password_delete);
//		passwordDelBtnLayout =  (LinearLayout) findViewById(R.id.login_password_delete_layout);
//		passwordDelBtn.setOnClickListener(this);
//		passwordDelBtnLayout.setOnClickListener(this);
//		passwordSeeBtn = (ImageButton) findViewById(R.id.login_password_see_icon);
//		passwordSeeBtn.setOnClickListener(this);
//		passwordSeeBtnLayout =  (LinearLayout) findViewById(R.id.login_password_see_layout);
//		passwordSeeBtnLayout.setOnClickListener(this);
//
////		mResetPasswordLinkTextView = (TextView) findViewById(R.id.lookforpasswordtext);
////		mResetPasswordLinkTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
////		mResetPasswordLinkTextView.getPaint().setAntiAlias(true);
////		mResetPasswordLinkTextView.setOnClickListener(this);
//
////		showOrHideFastLoginButton();
////		Drawable mWeiBoLogo = this.getResources().getDrawable(R.drawable.weibo);
////		Drawable mTengXunLogo = this.getResources().getDrawable(R.drawable.tengxun);
////		mWeiBoLoginButton = (Button) findViewById(R.id.weibo_login);
////		mWeiBoLoginButton.setOnClickListener(this);
////		mTengXunLoginButton = (Button) findViewById(R.id.tengxun_login);
////		mTengXunLoginButton.setOnClickListener(this);
//
////		DisplayMetrics dm = getResources().getDisplayMetrics();
////		if (dm.widthPixels <= 320)//(0,320]
////		{
////			mWeiBoLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()/2, mWeiBoLogo.getIntrinsicHeight()/2);
////			mTengXunLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()/2, mWeiBoLogo.getIntrinsicHeight()/2);
////		}
////		else if (dm.widthPixels < 720) //(320,720)
////		{
////			mWeiBoLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*2/3, mWeiBoLogo.getIntrinsicHeight()*2 /3);
////			mTengXunLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*2/3, mWeiBoLogo.getIntrinsicHeight()*2/3);
////		}
////		else if(dm.widthPixels <= 800)//[720,800)
////		{
////			mWeiBoLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth(), mWeiBoLogo.getIntrinsicHeight());
////			mTengXunLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth(), mWeiBoLogo.getIntrinsicHeight());
////		}
////		else if(dm.widthPixels<1400)//[800,1400)
////		{
////			mWeiBoLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*3/2, mWeiBoLogo.getIntrinsicHeight()*3/2);
////			mTengXunLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*3/2, mWeiBoLogo.getIntrinsicHeight()*3/2);
////
////		}else { //(1400,∞)
////			mWeiBoLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*2, mWeiBoLogo.getIntrinsicHeight()*2);
////			mTengXunLogo.setBounds(0, 0, mWeiBoLogo.getIntrinsicWidth()*2, mWeiBoLogo.getIntrinsicHeight()*2);
////		}
////		mWeiBoLoginButton.setCompoundDrawables(mWeiBoLogo, null, null, null);
////		mTengXunLoginButton.setCompoundDrawables(mTengXunLogo, null, null, null);
////
////		mTpExpireText = (TextView)this.findViewById(R.id.tpExpired);
////		if(mIsTpTokenExpired){
////			mTpExpireText.setVisibility(View.VISIBLE);
////		}else{
////			mTpExpireText.setVisibility(View.GONE);
////		}
//	}
//
//	private void showOrHideFastLoginButton() {
////		mFastLoginButton = (Button) findViewById(R.id.cmwap_fast_login);
////		mFastLoginButton.setVisibility(View.GONE);
//
///*//		String phoneNum = PhoneState.Instance().getPhoneNumber();
////		int simType = PhoneState.Instance().getSIMCardType(null);
//		if (PhoneState.Instance().checkSimState()) {
////		&& (LoginModel.smsSwitch != null && LoginModel.smsSwitch.equals("1"))
////				&& (simType == PhoneState.SIM_CARD_MOBILE
////						|| simType == PhoneState.SIM_CARD_UNICOM
////						|| simType == PhoneState.SIM_CARD_CTMOBILE)) {
////			if (LoginModel.Instance(this).getLoginType() == LoginModel.LOGIN_TYPE_SMS_DIRECT_MOBILE
////					|| LoginModel.Instance(this).getLoginType() == LoginModel.LOGIN_TYPE_WAP
////					|| (phoneNum != null && phoneNum.equals(LoginModel.getmLoginAccount()))
////					|| (PhoneState.Instance().isInternetRoaming() && LoginModel
////							.Instance(this).getLoginType() != LoginModel.LOGIN_TYPE_SMS_DIRECT_MOBILE)) {
////				mFastLoginButton.setVisibility(View.INVISIBLE);
////			} else {
//					mFastLoginButton.setVisibility(View.VISIBLE);
//					mFastLoginButton.setOnClickListener(this);
////			}
//		} else {
//			mFastLoginButton.setVisibility(View.INVISIBLE);
//		}*/
//	}
//
//	private void initData() {
//		File dir = new File(ReaderPreferences.TEMP_PATH);
//		if (!dir.exists()) {
//			dir.mkdir();
//		}
//
//		filter.addAction(BroadcastConst.CMWAP_LOGIN_SUCCESS);
//		filter.addAction(BroadcastConst.CMWAP_LOGIN_FAIL);
//		filter.addAction(BroadcastConst.GET_ENTERPRISE_SUCCESS);
//		filter.addAction(BroadcastConst.GET_ENTERPRISE_FAIL);
//
//		filter.addAction("com.ytmlab.client.WLANRAPIDLOGIN.LOADING"); // Added
//																			// For
//																			// Guest
//		// mRememberPassword = ReaderPreferences.getRememberPassword();
//
//		// mRememberPasswordCheckBox.setOnCheckedChangeListener(new
//		// OnCheckedChangeListener()
//		// {
//		// @Override
//		// public void onCheckedChanged(CompoundButton buttonView, boolean
//		// isChecked)
//		// {
//		// mIsRememberPasswordChecked = isChecked;
//		// if (!isChecked)
//		// {
//		// mAutoLoginCheckBox.setChecked(false);
//		// }
//		// }
//		// });
//
//		// mAutoLoginCheckBox.setOnCheckedChangeListener(new
//		// OnCheckedChangeListener()
//		// {
//		// @Override
//		// public void onCheckedChanged(CompoundButton buttonView, boolean
//		// isChecked)
//		// {
//		// mIsAutoLoginChecked = isChecked;
//		// if (isChecked)
//		// {
//		// mRememberPasswordCheckBox.setChecked(true);
//		// }
//		// }
//		// });
//
//		initEnterpriseData();
//
////		if (mRememberPassword) {
//			mAccountNumber = ReaderPreferences.getAccount();
//			mPassword = ReaderPreferences.getPassword();
//			if (mAccountNumber != null && !"".equals(ReaderPreferences.getExperienceAccount())
//					&& mAccountNumber.equals(ReaderPreferences.getExperienceAccount())) {
//				return;
//			}
//			if (mAccountNumber != null && mVerifyPasswordFailed) 	{
//				mAccountNumberEditText.setText(mAccountNumber);
//			}
//			else if (mAccountNumber != null && mPassword != null && !mClearEditText) {
//				mAccountNumberEditText.setText(mAccountNumber);
//				if (isSwitchAccount == true || isBackgroundLoginFailed == true) {
//					mPasswordEditText.setText("");
//				} else {
//					mPasswordEditText.setText(mPassword);
//				}
//				if (ReaderPreferences.getEnterprisedName() != null && !"".equals(ReaderPreferences.getEnterprisedName())
//						&& !"".equals(mAccountNumber) && !"".equals(mPassword)) {
//					mSchoolEditText.setText(ReaderPreferences.getEnterprisedName());
//					mSchoolEditText.setBackgroundResource(0);
//				}
//			}
////		}
//	}
//
//	private void wlanLogin() {
//		String strUsername = (mAccountNumberEditText.getText()).toString()
//				.trim();
//		String strpassword = (mPasswordEditText.getText()).toString().trim();
////		if(BPlusCApp.mSupportUnityCertificate)
////		{
////			if (strUsername == null || "".equals(strUsername))
////			{
////				Toast.makeText(this, R.string.wlan_error_message_empty_phonenumber,
////						Toast.LENGTH_LONG).show();
////				mAccountNumberEditText.setFocusable(true);
////				return;
////			}
////
////			if (strpassword == null || "".equals(strpassword)) {
////				Toast.makeText(LoginActivity.this,
////						R.string.wlan_error_message_empty_password,
////						Toast.LENGTH_LONG).show();
////				mPasswordEditText.setFocusable(true);
////				return;
////			}
////		}
////		else{
////
////			if (strUsername != null && !"".equals(strUsername)) {
////				if (strUsername.length() <= 20) {
////					if (strUsername.length() == 11) {
////						if (strUsername.matches("[0-9]+")) {
////					        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
////					        Matcher m = p.matcher(strUsername);
////					        if (!m.matches()) {
////								Toast.makeText(this, R.string.register_username_error,
////										Toast.LENGTH_LONG).show();
////								mAccountNumberEditText.setFocusable(true);
////								return;
////					        }
////						} else if (strUsername.matches("[a-zA-Z]+")) {
////							Toast.makeText(this, R.string.register_username_error,
////									Toast.LENGTH_LONG).show();
////							mAccountNumberEditText.setFocusable(true);
////							return;
////						}
////					} else {
////						if (strUsername.matches("[0-9]+") || strUsername.matches("[a-zA-Z]+")) {
////							Toast.makeText(this, R.string.register_username_error,
////									Toast.LENGTH_LONG).show();
////							mAccountNumberEditText.setFocusable(true);
////							return;
////						}
////					}
////				} else {
////					Toast.makeText(this, R.string.register_username_error,
////							Toast.LENGTH_LONG).show();
////					mAccountNumberEditText.setFocusable(true);
////					return;
////				}
////			} else {
////				Toast.makeText(this, R.string.wlan_error_message_empty_phonenumber,
////						Toast.LENGTH_LONG).show();
////				mAccountNumberEditText.setFocusable(true);
////				return;
////			}
////
////			if (strpassword != null && !"".equals(strpassword)) {
////				if (strpassword.length() < 6) {
////					Toast.makeText(
////							this,
////							R.string.wlan_error_message_password_length_less_than_six,
////							Toast.LENGTH_LONG).show();
////					mPasswordEditText.setFocusable(true);
////					return;
////				} else if (strpassword.length() > 16) {
////					Toast.makeText(
////							this,
////							R.string.wlan_error_message_password_length_more_than_sixteen,
////							Toast.LENGTH_LONG).show();
////					mPasswordEditText.setFocusable(true);
////					return;
////				}
////			} else {
////				Toast.makeText(LoginActivity.this,
////						R.string.wlan_error_message_empty_password,
////						Toast.LENGTH_LONG).show();
////				mPasswordEditText.setFocusable(true);
////				return;
////			}
////
////		}
//
//		mAccountNumber = strUsername;
//		mPassword = strpassword;
//		if (LoginModel.getUserPermission()
//				&& mAccountNumber != LoginModel
//						.getmLoginAccount()) {
//			mIsNewUser = true;
//		} else {
//			mIsNewUser = false;
//		}
//		LoginModel.Instance(this).setLoginAccountPassword(mAccountNumber,
//				mPassword);
//		NLog.i("cc", "wlanLogin setRememberPassword true");
//
//		startLoading();
//		isAuthenticateFinish = false;
//		if (BPlusCApp.mSupportUnityCertificate == true) {
//			showAddFreeReadingHint();
//			showCustomToast(LoginActivity.this, R.string.identity_authentication_tip, 1000);
//		}
////		Toast.makeText(LoginActivity.this,
////				R.string.identity_authentication_tip,
////				Toast.LENGTH_SHORT).show();
//		NLog.e("Henry", "USER Login Model onKVEventStart");
//		String eventKey = "time_userNameLogin";//by henry
//		String eventValue = "tunl_userNameLogin";//by henry
//		onKVEventStart(eventKey, eventValue);//by henry
//		String event = "rate_usLog_callUsLo";
//		startCMTrackOnEvent(event, "");
//		if(ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD
//				&& ReaderPreferences.getDymPwdLoginStatus()){
//			mBakLoginTypeTemp = true;
//		}
//		ReaderPreferences.setDymPwdLoginStatus(false);
//		try
//		{
//			if (mAccountNumber != null && !"".equals(mAccountNumber)){
//				String tempAccount = URLEncoder.encode(mAccountNumber, "UTF-8");
//				LoginModel.Instance(this).userPasswordLogin(true, mHandler,tempAccount,mPassword);
//			}
//		}
//		catch (UnsupportedEncodingException e)
//		{
//			e.printStackTrace();
//		}
////		LoginModel.Instance(this).userPasswordLogin(true, mHandler,LoginModel.getmLoginAccount(),mPassword);
//	}
//
//	private void oneKeyLogin(String username, String password) {
//		isOneKeyLogin = true;
//		mAccountNumber = username;
//		mPassword = password;
//		if (LoginModel.getUserPermission()
//				&& mAccountNumber != LoginModel
//						.getmLoginAccount()) {
//			mIsNewUser = true;
//		} else {
//			mIsNewUser = false;
//		}
//		LoginModel.Instance(this).setLoginAccountPassword(mAccountNumber,
//				mPassword);
//		NLog.i("cc", "wlanLogin setRememberPassword true");
//
////		startLoading();
//		NLog.e("Henry", "USER Login Model onKVEventStart");
//		String eventKey = "time_userNameLogin";//by henry
//		String eventValue = "tunl_userNameLogin";//by henry
//		onKVEventStart(eventKey, eventValue);//by henry
//		String event = "rate_usLog_callUsLo";
//		startCMTrackOnEvent(event, "");
//		if(ReaderPreferences.getLastLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD
//				&& ReaderPreferences.getDymPwdLoginStatus()){
//			mBakLoginTypeTemp = true;
//		}
//		ReaderPreferences.setDymPwdLoginStatus(false);
//		try
//		{
//			if (mAccountNumber != null && !"".equals(mAccountNumber)){
//				String tempAccount = URLEncoder.encode(mAccountNumber, "UTF-8");
//				LoginModel.Instance(this).userPasswordLogin(true, mHandler,tempAccount,mPassword);
//			}
//		}
//		catch (UnsupportedEncodingException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private void mobileFastLogin() {
//		int simType = PhoneState.Instance().getSIMCardType(null);
//		switch (simType) {
//		case PhoneState.SIM_CARD_MOBILE:
//			if (NetState.getInstance().getProxyType() == CM_ProxyType.MOBILE_NET
//					|| NetState.getInstance().getProxyType() == CM_ProxyType.WIFI) {
//				if(LoginModel.mSmsLogingIn == false){
//				// sms
//				LoginModel.Instance(this).smsDirectLogin(LoginActivity.this,
//						true, mHandler);
//				}
//			} else {// cmwap
//				LoginModel.Instance(this).cmwapLogin(true, mHandler);
//			}
//			break;
//		case PhoneState.SIM_CARD_UNICOM:
//		case PhoneState.SIM_CARD_CTMOBILE:
//			if (!PhoneState.Instance().getStoreToken().equals("")
//					&& !PhoneState.Instance().checkIsim()) {// if already have token
//				LoginModel.Instance(this).smsDirectLogin(LoginActivity.this,
//						true, mHandler);
//			} else {
//				if(LoginModel.mSmsLogingIn == false){
//					LoginModel.Instance(this).smsDirectLogin(LoginActivity.this,
//							true, mHandler);
//				}
//			}
//			break;
//		default:
//			NLog.e(TAG, "Unable to detect SIM card type");
//			return;
//		}
//		startLoading();
//	}
//
//	private void registerLink()
//	{
//		Intent registerIntent = new Intent(this, WLanRegister.class);
//		startActivity(registerIntent);
//	}
//
//	private void resetLink()
//	{
//		Intent intent = new Intent(this, SMSCodeRetrievePassword.class);
//		startActivity(intent);
//	}
//
//	private void saveLoginFlag() {
//		ReaderPreferences.setRememberPassword(true);
//		if (mAccountNumber != null && !mAccountNumber.equals("")) {
//			ReaderPreferences.saveAccount(mAccountNumber);
//		} else {
//			ReaderPreferences.saveAccount("");
//		}
//		if (mPassword != null && !mPassword.equals("")) {
//			ReaderPreferences.savePassword(mPassword);
//		} else {
//			ReaderPreferences.savePassword("");
//		}
//		LoginModel.Instance(this).setLoginAccountPassword(mAccountNumber,
//				mPassword);
//		ReaderPreferences.setAutoLogin(true);
//		ReaderPreferences.save();
//	}
//
//	private void loginSuccess() {
//		ReaderPreferences.setLastLoginType(LoginModel.Instance(this).getLoginType());
//		if (enterpriseListObject != null && enterpriseListObject.getEnterpriseList() != null
//				&& enterpriseListObject.getEnterpriseList().size() > 1) {
//
//		} else {
//			sendBroadcast(new Intent(BroadcastConst.WEB_VIEW_REFRESH_ACTION));
//		}
//		LocalLoginUtil.getInstance(this).setLoadingStatus(-1);
//		ReaderPreferences.setChInfoBak(null);
//		if (LoginModel.Instance(this).getLoginType() == LoginModel.LOGIN_TYPE_USER_PASSWORD)
//		{
//			saveLoginFlag();
//		}
//
//		if (mChangePasswordFlag) {
//			ToastUtil.showMessage(this, R.string.wlan_login_change_password,
//					Toast.LENGTH_LONG);
//		} else {
//			LocalLoginUtil.getInstance(this).showWlanLoginSuccessToast();
//		}
//		if (mAgent != null) {
//			mAgent.onLoginSuccess();
//			NLog.e("LoginActivity", "agent entered");
//			mAgent.execute();
//		}
//		ReaderPreferences.setLoginMode(false);
//		ReaderPreferences.setPresubCountValue("0");
//		ReaderPreferences.save();
//		if (!LoginModel.getUserPermission()) {
//			CMTrack.getInstance().clearUserId();
//			CMTrackLog.getInstance().clearAccountName();
//		} else {
//			String userId = ReaderPreferences.getUserID();
//			String accountName = ReaderPreferences.getAccount();
//
//			CMTrack.getInstance().setUserId(userId);
//			CMTrackLog.getInstance().setAccountName(accountName);
//		}
//	}
//
//	private Handler mUserHandle = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			NLog.i(TAG, "send request call back already");
//			switch (msg.what)
//			{
//			case MessageDef.GET_USERINFO:
//				if (msg.arg1 == 0)
//				{
//					Hashtable<String, String> params =new Hashtable<String, String>();
//					params=UserInfoParser.getInstance().parseViewParamsFormXml((Doc)msg.obj);
//					String downloadCodeRate=params.get("downloadCodeRate");
//					String mBindPhoneNum = params.get("bindPayMsisdn");
//					if(mBindPhoneNum == null || "".equals(mBindPhoneNum))
//					{
//						ReaderPreferences.setBindNumberState(false);
//					}
//					else
//					{
//						ReaderPreferences.setBindNumberState(true);
//					}
//					if(downloadCodeRate!=null)
//					{
//						try
//						{
//							ReaderPreferences.setListeningBookDownloadQuility(Integer.parseInt(downloadCodeRate));
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//						}
//					}
//				}
//				break;
//			}
//		}
//	};
//
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch (keyCode) {
//		case KeyEvent.KEYCODE_BACK:
//			if (mAgent != null){
//				mAgent.onCancel();
//				mAgent = null;
//			}
//			if (isSwitchAccount == true) {
//				finish();
//			}
////			finish();
//			return true;
//		case KeyEvent.KEYCODE_MENU:
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	//密码输入框的Watcher
//	private TextWatcher mTextWatcher = new TextWatcher() {
//		@Override
//		public void afterTextChanged(Editable s) {
//
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence cs, int start, int count,
//				int after) {
//		}
//
//		@Override
//		public void onTextChanged(CharSequence cs, int start, int before,
//				int count) {
//			dialLoginButtonState();
//
//			String text = cs.toString();
//			if(text.length() > 0) {
//				//用户输入空格时的处理
//				if (text.toString().contains(" ")) {
//					String[] str = text.toString().split(" ");
//					String str1 = "";
//					for (int i = 0; i < str.length; i++) {
//						str1 += str[i];
//					}
//					mPasswordEditText.setText(str1);
//					mPasswordEditText.setSelection(str1.length());
//					return;
//				}
//
//				if (isPasswordEditHasFocus) {
//					passwordDelBtn.setVisibility(View.VISIBLE);
//				}
//			} else {
//				passwordDelBtn.setVisibility(View.GONE);
//			}
//
//			if (text.length() > 20) {
//				mPasswordEditText.setText(text.substring(0,20));
//				mPasswordEditText.setSelection(20);
//				if (checktToast()) {
//					ToastUtil.showCenterMessage(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.password_length_limit));
//				}
//			}
//		}
//	};
//
//	private TextWatcher mSelectSchoolWatcher = new TextWatcher() {
//		@Override
//		public void afterTextChanged(Editable s) {
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence cs, int start, int count,
//				int after) {
//		}
//
//		@Override
//		public void onTextChanged(CharSequence cs, int start, int before,
//				int count) {
//			dialLoginButtonState();
//
//			mSearchSchoolText = cs.toString();
//			if (loginViewType == LOGIN_NORMAL_VIEW && !StringUtil.isNullOrEmpty(mSearchSchoolText)) {
//				return;
//			}
//	        if (mSearchSchoolText != null) {
//	        	//当输入框内容为空时，隐藏无搜索结果页，设置输入框的hint
//	        	if ("".equals(mSearchSchoolText)) {
//	        		if (loginViewType == LOGIN_NORMAL_VIEW) {
//	        			mSchoolEditText.setBackground(getResources().getDrawable(R.drawable.select_school_edittext_background));
//	        			return;
//	        		}
//	        		if (loginViewType == LOGIN_SELECT_SCHOOL_VIEW) {
//		        		mSchoolEditText.setBackground(getResources().getDrawable(R.drawable.select_school_edittext_background));
//		        		mSearchResultListLayout.setVisibility(View.VISIBLE);
//		        		mSearchResultEmptyLayout.setVisibility(View.GONE);
//	        		}
//	        	} else {
//	        		mSchoolEditText.setBackgroundResource(0);
//	        	}
//
//	        	//根据输入框内容的改变，重新填充搜索结果数据
//	        	mSchoolResultList.clear();
//	        	for (int i = 0; i < mSchoolList.size(); i++) {
//		        	if (mSchoolList.get(i).getEnterpriseName().startsWith(mSearchSchoolText)) {
//		        		mSchoolResultList.add(mSchoolList.get(i));
//		        	}
//	        	}
//
//	        	//当搜索结果为空时，显示无搜索结果页；当搜索结果不为空时，如果结果数据小于五条，填充空数据，凑足五条，如果结果数据大于五条，正常显示
//	        	if (mSchoolResultList.size() == 0) {
//	        		mSearchResultListLayout.setVisibility(View.GONE);
//	        		mSearchResultEmptyLayout.setVisibility(View.VISIBLE);
//	        		return;
//	        	} else {
//	        		mSearchResultListLayout.setVisibility(View.VISIBLE);
//	        		mSearchResultEmptyLayout.setVisibility(View.GONE);
////		        	if (mSchoolResultList.size() < 5) {
////		        		for (int i = mSchoolResultList.size(); i < 5; i++) {
////		            		EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
////		            		enterpriseInfo.setEnterpriseName("");
////		            		mSchoolResultList.add(enterpriseInfo);
////		        		}
////		        	}
//		        	mSchoolAdapter = new SelectSchoolAdapter(mContext, mSchoolResultList);
//		        	mSchoolAdapter.type = SelectSchoolAdapter.ON_TEXT_CHANGED;
//	        	}
//	        }
//	        mSearchResultListView.setAdapter(mSchoolAdapter);
//		}
//	};
//
//
//	private TextWatcher mAccountWatcher = new TextWatcher() {
//		@Override
//		public void afterTextChanged(Editable s) {
//
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence cs, int start, int count,
//				int after) {
//		}
//
//		@Override
//		public void onTextChanged(CharSequence cs, int start, int before,
//				int count) {
//			dialLoginButtonState();
//
//			if(mAccountNumberEditText.length() < 1) {
//				accountDelBtn.setVisibility(View.GONE);
//			}else {
//				if (mAccountNumberFirstInit) {
//					accountDelBtn.setVisibility(View.GONE);
//					mAccountNumberFirstInit = false;
//				} else {
//					accountDelBtn.setVisibility(View.VISIBLE);
//				}
//			}
//
//			String text = cs.toString();
//			if (text.length() > 25) {
//				mAccountNumberEditText.setText(text.substring(0,25));
//				mAccountNumberEditText.setSelection(25);
//				if (checktToast()) {
//					ToastUtil.showCenterMessage(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.account_length_limit));
//				}
//			}
//		}
//	};
//
//	/** 第一次时间 */
//	private long firstShowTime = 0;
//	/** 第二次时间 */
//	private long secondShowTime = 0;
//	private boolean checktToast() {
//		secondShowTime = System.currentTimeMillis() ;
//		if (secondShowTime - firstShowTime > 2500) {
//			firstShowTime = secondShowTime;
//			return true;
//		}
//		return false;
//	}
//
//	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (action.equals("com.ytmlab.client.WLANRAPIDLOGIN.LOADING")) {
//				startLoading();
//				LoginModel.Instance(LoginActivity.this).userPasswordLogin(true,
//						mHandler,LoginModel.getmLoginAccount(),mPassword);
//			}
//
//			if (action.equals(BroadcastConst.CMWAP_LOGIN_SUCCESS)) {
//				dismissDialog();
//				finish();
//			}
//			if (action.equals(BroadcastConst.CMWAP_LOGIN_FAIL)) {
//				dismissDialog();
//			}
//			if (action.equals(BroadcastConst.GET_ENTERPRISE_FAIL)) {
//				dismissDialog();
//				ToastUtil.showMessage(LoginActivity.this, R.string.login_activity_get_school_data_fail, Toast.LENGTH_SHORT);
//			}
//			if (action.equals(BroadcastConst.GET_ENTERPRISE_SUCCESS)) {
//				boolean needRequestFocus = false;
//		        if (mDialog != null && mDialog.isShowing()) {
//		        	needRequestFocus = true;
//		        }
//				dismissDialog();
//				initEnterpriseData();
//				mSchoolEditText.setFocusableInTouchMode(true);
//				mSchoolEditText.setFocusable(true);
//				mSchoolEditText.setOnFocusChangeListener(LoginActivity.this);
//				if (needRequestFocus) {
//					mSchoolEditText.requestFocus();
//					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//					if(!inputMethodManager.isActive()){
//					   ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//					}
//				}
//			}
//		}
//	};
//
//	@Override
//	public void onClick(View button) {
//		if (!button.isEnabled())
//			return;
//			if(!NetState.getInstance().isNetWorkConnected())
//			{
//				ToastUtil.showMessage(this, R.string.network_error_hint, Toast.LENGTH_LONG);
//				return;
//			}
//		String errorCode = null;
//		String msg = null;
//		switch (button.getId()) {
//		case R.id.savelogin:
//			clearFocus();
//			switchView(LOGIN_NORMAL_VIEW);
//			errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 1);
//			msg = "LoginActivity.userPasswordLogin() entered";
//			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
//			mChgAccountFlag = true;
//			wlanLogin();
//			break;
//		case R.id.one_key_login:
//			clearFocus();
//			switchView(LOGIN_NORMAL_VIEW);
//			startLoading();
//			isAuthenticateFinish = false;
////			showAddFreeReadingHint();
////			showCustomToast(LoginActivity.this, R.string.identity_authentication_tip, 1000);
////			Toast.makeText(LoginActivity.this,
////					R.string.identity_authentication_tip,
////					Toast.LENGTH_LONG).show();
//			GetExperienceAccountInfoPresenter mPresenter = new GetExperienceAccountInfoPresenter(this, mHandler);
//			mPresenter.sendRequest(null);
//			break;
////		case R.id.cmwap_fast_login:
////			event = "login_phoneLogin";
////			value = "";
////			startCMTrackOnEvent(event, value);
////
////			errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 2);
////			msg = "LoginActivity.mobileFastLogin() entered,LoginType= "+LoginModel.Instance(this).getLoginType();
////			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
////			mChgAccountFlag = true;
////			mobileFastLogin();
////			break;
////		case R.id.weibo_login:
////			NLog.e(TAG, "weibo_login");
////			mTpLoginType = TpLoginType.Sina;
////
////			errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 4);
////			msg = "LoginActivity.sina weibo login entered";
////			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
////
////			sinaLogin();
////			break;
////		case R.id.tengxun_login:
////			NLog.e(TAG, "tengxun_login");
////			mTpLoginType = TpLoginType.QQ;
////
////			errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 3);
////			msg = "LoginActivity.QQ weibo login entered";
////			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
////
////			qqLogin();
////			break;
////		case R.id.wlan_regist:
////			mClickedButtonId = R.id.wlan_regist;
////			sendGetSmsNum();
////			break;
////		case R.id.lookforpasswordtext:
////			event = "login_getPassWord";
////			value = "";
////			startCMTrackOnEvent(event, value);
////			resetLink();
////			break;
//		case R.id.school_text:
//			if (BPlusCApp.getEnterpriseListObject() == null) {
//				GetEnterpriseByClientPresenter mGetEnterprisePresenter = new GetEnterpriseByClientPresenter(this, null);
//				mGetEnterprisePresenter.sendRequest();
//				showDialog();
//			} else {
//				if (loginViewType != LOGIN_SELECT_SCHOOL_VIEW) {
//					switchView(LOGIN_SELECT_SCHOOL_VIEW);
//				}
//			}
//			break;
//		case R.id.login_username_delete:
//		case R.id.login_username_delete_layout:
//			if (accountDelBtn.getVisibility() == View.VISIBLE) {
//				mAccountNumberEditText.setText("");
//			}
//			break;
//		case R.id.login_password_delete:
//		case R.id.login_password_delete_layout:
//			if (passwordDelBtn.getVisibility() == View.VISIBLE) {
//				mPasswordEditText.setText("");
//			}
//			break;
//		case R.id.login_password_see_icon:
//		case R.id.login_password_see_layout:
//			if(displayFlg)
//			{
//				displayFlg = false;
//				passwordSeeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_password_hide));
//				mPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
//				if (mPasswordEditText.getText().length() > 0) {
//					mPasswordEditText.setSelection(mPasswordEditText.getText().length());
//				}
//			}
//			else
//			{
//				displayFlg = true;
//				passwordSeeBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_password_show));
//				mPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//				if (mPasswordEditText.getText().length() > 0) {
//					mPasswordEditText.setSelection(mPasswordEditText.getText().length());
//				}
//			}
//			break;
//		case R.id.search_result_empty_layout:
//			break;
//		}
//	}
//	private void qqLogin(){
//		Intent intent1 = new Intent(this, TheThirdPartLoginWebPage.class);
//		intent1.putExtra("URL","http://wap.cmread.com/sso/p/thirdLoginForClient.jsp?e_l=3&client_flag=1&type=1");
//		intent1.putExtra("login_type", "1");
//		startActivityForResult(intent1,0);
//	}
//	private void sinaLogin(){
//		Intent intent = new Intent(this, TheThirdPartLoginWebPage.class);
//		intent.putExtra("URL","http://wap.cmread.com/sso/p/thirdLoginForClient.jsp?e_l=3&client_flag=1&type=2");
//		intent.putExtra("login_type", "2");
//		startActivityForResult(intent,0);
//	}
//
//	private void showUpadteDialog(int type) {
//		Intent intent = new Intent(this, UpgradeDialog.class);
//		intent.putExtra(UpgradeDialog.UPDATE_DIALOG_TYPE, type);
//		startActivityForResult(intent, type);
//	}
//
//	private Handler mAuthenticateSuccessHandler = new Handler() {
//		public void handleMessage(Message msg) {
//
//			super.handleMessage(msg);
//			handleResult(mMsgWhat, mMsgArg1);
//			mMsgWhat = -100;
//			mMsgArg1 = -100;
//		}
//
//	};
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == UpgradeDialog.RESULT_CODE_CANCEL)
//		{
//		}
//		else if (resultCode == UpgradeDialog.RESULT_CODE_NOT_PROMPT) {
//			loginSuccess();
//			finish();
//		} else if (resultCode == UpgradeDialog.RESULT_CODE_RESTART_PACKAGES) {
//			finish();
//		}else if(resultCode == TheThirdPartLoginWebPage.TP_LOGIN_RESULT_OK) {//add for the third part login
//
//			String errorCode = ModuleNum.LOG_IN + (ModuleNum.EXCEPTION_CODE_BASE_20000 + 5);
//			String msg = "LoginActivity.onActivityResult() get tpToken success! tpToken="+ReaderPreferences.getTpToken();
//			CMTrackLog.getInstance().infoLog(this, new LogBaseInfo(CMTrackLog.LOG_TYPE_SERVICE, errorCode , null), null, msg);
//
//			startLoading();
//			LoginModel.Instance(this).TpAuthLogin(this, true, mHandler);
//		}else if(resultCode == TheThirdPartLoginWebPage.TP_LOGIN_RESULT_CANCEL){
//			//Do nothing TODO
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//	private void startCMTrackOnEvent(String event, String value)
//	{
//		CMTrack.getInstance().onEvent(LoginActivity.this, event, value);
//	}
//	private void startCMTrackOnKVEvent(String event, String value, String resultCode)
//	{
//		CM_ProxyType mProxyType;
//		mProxyType = NetState.getInstance().getProxyType();
//		TelephonyManager mTelephonyMgr = (TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
//		String imsi;
//		imsi = mTelephonyMgr.getSubscriberId();
//		String imei;
//		imei = mTelephonyMgr.getDeviceId();
//		String model;
//		model = Build.MODEL;
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("resultcode", resultCode);
//		map.put("phone_type", model);
//		map.put("imsi", imsi);
//		map.put("imei", imei);
//		map.put("network_type", String.valueOf(mProxyType));
//		CMTrack.getInstance().onKVEventForLoginFailed(LoginActivity.this, event, value, map);
//	}
//
//	public void showAlertDialogPasswordVerifyFailed()
//	{
////		final CMReaderAlertDialog dialog = new CMReaderAlertDialog(this, 0, 2);
////		dialog.setCustomMessage(getString(R.string.server_response_7076));
////        dialog.setCustomMessageLineSpace(0, 1.25f);
////		dialog.setTitleTxt(R.string.login_failed);
////
////        dialog.setNegativeButton(R.string.button_reset_password, new OnClickListener()
////		{
////			public void onClick(View v)
////			{
////				dialog.dismiss();
////				if (LoginActivity.this != null)
////				{
////					Intent intent = new Intent(LoginActivity.this, SMSCodeRetrievePassword.class);
////					String strUserName = (mAccountNumberEditText.getText()).toString().trim();
////					intent.putExtra("Username", strUserName);
////					startActivityForResult(intent, REQUEST_RESET_PASSWORD);
////				}
////			}
////		});
////
////        dialog.setPositiveButton(R.string.try_login_again, new OnClickListener()
////		{
////			public void onClick(View v)
////			{
////				dialog.dismiss();
////			}
////		});
////
////		dialog.show();
//	}
//
//	public static void showAlertDialog(String title, String content)
//	{
//		final CMReaderAlertDialog dialog = new CMReaderAlertDialog(mInstance, 0, 0);
//		dialog.setTitleTxt(title);
//		dialog.setCustomMessage(content);
//		dialog.setCustomMessageLineSpace(0, 1.25f);
//
//		dialog.setPositiveButton(R.string.button_confirm, new OnClickListener()
//		{
//			public void onClick(View v)
//			{
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//	}
//
//	public void startLoading()
//	{
//		if(this.isFinishing()){
//			return;
//		}
//		if (mLoadingDialog != null)
//			mLoadingDialog.dismissAllowingStateLoss();
//		mLoadingDialog = new AlertDialogForLogin(mDailogHandler);
//		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//		transaction.add(mLoadingDialog, "loading");
//		transaction.commitAllowingStateLoss();
//	}
//	public enum TpLoginType{
//		QQ,
//		Sina
//	};
//
//	@Override
//	public void onWlanRegistClickListener()
//	{
//		if (!NetState.getInstance().isNetWorkConnected())
//		{
//			ToastUtil.showMessage(this, R.string.network_error_hint, Toast.LENGTH_SHORT);
//			return ;
//		}
//
//	    // mClickedButtonId = mWlanRegistId;
//		registerLink();
//	}
//
//	@Override
//	public void onBackClickListener()
//	{
//		if (mAgent != null)
//		{
//			mAgent.onCancel();
//			mAgent = null;
//		}
//		super.onBackClickListener();
//	}
//
//	public String getVersionName(Context context){
//	    PackageManager packageManager=context.getPackageManager();
//	    PackageInfo packageInfo;
//	    String versionName="";
//	    try {
//	        packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
//	        versionName=packageInfo.versionName;
//	    } catch (PackageManager.NameNotFoundException e) {
//	        e.printStackTrace();
//	    }
//	    return versionName;
//	}
//
//	private boolean isAuthenticateFinish;
//	private Handler mHintHandler = new Handler(){
//		@Override
//		public void handleMessage(Message message) {
//			ToastUtil.showMessage(mContext, R.string.free_reading_query_tip, Toast.LENGTH_SHORT);
//		}
//	};
//	private void showAddFreeReadingHint() {
//		if (mHintHandler != null) {
//			mHintHandler.postDelayed(new Runnable(){
//				public void run() {
//					if (!isAuthenticateFinish && mHintHandler != null) {
//						mHintHandler.sendMessage(mHintHandler.obtainMessage());
//					}
//				}
//			}, 5000);
//		}
//	}
//
//    public void showCustomToast(Context mContext, int resId, long duration){
//        final Toast toast = Toast.makeText(mContext, mContext.getResources().getString(resId), Toast.LENGTH_LONG);
//        toast.show();
//        Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//               public void run() {
//                   toast.cancel();
//               }
//        }, duration);
//    }
//
//	private Handler mMoveFocusHandler = new Handler();
//    public void moveSchoolEditTextFocus(){
//        mMoveFocusHandler.postDelayed(new Runnable() {
//           public void run() {
//				mSchoolEditText.requestFocus();
//				mSchoolEditText.setSelection(mSchoolEditText.getEditableText().toString().length());
//				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//           }
//        }, 10);
//    }
//
//	@Override
//	public void onFocusChange(View view, boolean hasFocus) {
//		switch (view.getId()) {
//		case R.id.school_text:
//			if (hasFocus) {
//				if (!StringUtil.isNullOrEmpty(mSchoolEditText.getEditableText().toString())) {
//					mSchoolEditText.setDrawable(null, null, null, null);
//					if (loginViewType == LOGIN_NORMAL_VIEW) {
//						mSchoolEditText.clearFocus();
//						moveSchoolEditTextFocus();
//					}
//				}
////				mSchoolEditText.setBackgroundResource(0);
//			} else {
//				mSchoolEditText.setDrawable(null, null, null, null);
//			}
//			changeDividerColor(view, hasFocus);
//			break;
//		case R.id.usernametext:
//			if (hasFocus) {
//				if (!StringUtil.isNullOrEmpty(mAccountNumberEditText.getEditableText().toString())) {
//					accountDelBtn.setVisibility(View.VISIBLE);
//				} else {
//					accountDelBtn.setVisibility(View.GONE);
//				}
//			} else {
//				accountDelBtn.setVisibility(View.GONE);
//			}
//			changeDividerColor(view, hasFocus);
//			break;
//		case R.id.passwordtext:
//			if (hasFocus) {
//				isPasswordEditHasFocus = true;
//				if (!StringUtil.isNullOrEmpty(mPasswordEditText.getEditableText().toString())) {
//					passwordDelBtn.setVisibility(View.VISIBLE);
//				}
//			} else {
//				isPasswordEditHasFocus = false;
//				passwordDelBtn.setVisibility(View.GONE);
////				mPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
////				mPasswordEditText.setDisplayFlag(false);
//			}
//			changeDividerColor(view, hasFocus);
//			break;
//		}
//	}
//
//	private void switchView(int type){
//		if (type == LOGIN_SELECT_SCHOOL_VIEW) {
//			loginViewType = LOGIN_SELECT_SCHOOL_VIEW;
//			mLoginTitleBar.setVisibility(View.VISIBLE);
//			mLoginAppIcon.setVisibility(View.GONE);
//			mUserInputLayout.setVisibility(View.GONE);
//			mSearchResultListLayout.setVisibility(View.VISIBLE);
////			mLoginButtonLayout.setVisibility(View.GONE);
//			mBottomImageLayout.setVisibility(View.GONE);
//
////			mSchoolEditText.setText("");
//			mSchoolInputLayoutDivider.setBackgroundColor(getResources().getColor(R.color.bind_phone_color_green));
//			mUsernameTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//			mPasswordTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//
//			initEnterpriseData();
//		} else if (type == LOGIN_SOFT_INPUT_VIEW) {
//			loginViewType = LOGIN_SOFT_INPUT_VIEW;
//			mLoginTitleBar.setVisibility(View.VISIBLE);
//			mLoginAppIcon.setVisibility(View.GONE);
//			mUserInputLayout.setVisibility(View.VISIBLE);
//			mSearchResultListLayout.setVisibility(View.GONE);
////			mLoginButtonLayout.setVisibility(View.VISIBLE);
//			mBottomImageLayout.setVisibility(View.GONE);
//		} else {
//			loginViewType = LOGIN_NORMAL_VIEW;
//			mLoginTitleBar.setVisibility(View.GONE);
//			mLoginAppIcon.setVisibility(View.VISIBLE);
//			mUserInputLayout.setVisibility(View.VISIBLE);
//			mSearchResultListLayout.setVisibility(View.GONE);
////			mLoginButtonLayout.setVisibility(View.VISIBLE);
//			mBottomImageLayout.setVisibility(View.VISIBLE);
//			if (mSearchResultEmptyLayout.getVisibility() == View.VISIBLE) {
//				mSearchResultEmptyLayout.setVisibility(View.GONE);
//				mSchoolEditText.setText("");
//			}
//		}
//	}
//
//	private void dialLoginButtonState(){
//		if (StringUtil.isNullOrEmpty(mSchoolEditText.getText().toString())
//				|| StringUtil.isNullOrEmpty(mAccountNumberEditText.getText().toString())
//				|| StringUtil.isNullOrEmpty(mPasswordEditText.getText().toString())) {
//			mLoginButton.setBackgroundResource(R.drawable.disable_green_button_background);
//			mLoginButton.setEnabled(false);
//		} else {
//			mLoginButton.setBackgroundResource(R.drawable.btn_blue_style);
//			mLoginButton.setEnabled(true);
//		}
//	}
//
//	private void changeDividerColor(View view, boolean hasFocus){
//		if (hasFocus) {
//			if (view.getId() == R.id.usernametext) {
//				mUsernameTextDivider.setBackgroundColor(getResources().getColor(R.color.bind_phone_color_green));
//				mPasswordTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				mSchoolInputLayoutDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				switchView(LOGIN_SOFT_INPUT_VIEW);
//			} else if (view.getId() == R.id.passwordtext) {
//				mPasswordTextDivider.setBackgroundColor(getResources().getColor(R.color.bind_phone_color_green));
//				mUsernameTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				mSchoolInputLayoutDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				switchView(LOGIN_SOFT_INPUT_VIEW);
//			} else if (view.getId() == R.id.school_text) {
//				if (loginViewType != LOGIN_SELECT_SCHOOL_VIEW) {
//					switchView(LOGIN_SELECT_SCHOOL_VIEW);
//				}
//			}
//		} else {
//			if (!mSchoolEditText.hasFocus() && !mAccountNumberEditText.hasFocus() && !mPasswordEditText.hasFocus()) {
//				mPasswordTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				mUsernameTextDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				mSchoolInputLayoutDivider.setBackgroundColor(getResources().getColor(R.color.yuntu_divider_color_gray));
//				switchView(LOGIN_NORMAL_VIEW);
//				if (isSoftInputShown) {
//					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//					inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//				}
//			}
//		}
//	}
//
//	private void initEnterpriseData() {
//		mSchoolList = getEnterpriseData();
////		if (mSchoolList.size() >= 5) {
////			mSchoolAdapter = new SelectSchoolAdapter(this, mSchoolList.subList(0, 5));
////		} else {
////        	for (int i = mSchoolList.size(); i < 5; i++) {
////        		EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
////        		enterpriseInfo.setEnterpriseName("");
////        		mSchoolList.add(enterpriseInfo);
////        	}
////        	mSchoolAdapter = new SelectSchoolAdapter(this, mSchoolList);
////		}
//		mSchoolAdapter = new SelectSchoolAdapter(this, mSchoolList);
//		mSchoolAdapter.type = SelectSchoolAdapter.FIRST_INIT;
//		mSearchResultListView.setAdapter(mSchoolAdapter);
//		mSearchResultListView.setOnItemClickListener(this);
//	}
//
//    private List<EnterpriseInfo> getEnterpriseData(){
//        List<EnterpriseInfo> list = null;
//        if (BPlusCApp.getEnterpriseListObject() != null) {
//        	list = BPlusCApp.getEnterpriseListObject().getEnterpriseList();
//        }
//
//		List<EnterpriseInfo> result = new ArrayList<EnterpriseInfo>();
//		if (list != null && list.size() > 0) {
//			List<String> enterpriseNameList = new ArrayList<String>(); ;
//			for (int i = 0; i < list.size(); i++) {
//				enterpriseNameList.add(list.get(i).getEnterpriseName());
//			}
//			Collections.sort(enterpriseNameList, CHINA_COMPARE);
//			for (int i = 0; i < enterpriseNameList.size(); i++) {
//				for (int j = 0; j < list.size(); j++) {
//					if (enterpriseNameList.get(i).equals(list.get(j).getEnterpriseName())) {
//						result.add(list.get(j));
//					}
//				}
//			}
//		}
//
//        return result;
//    }
//
//    private void clearFocus(){
//    	if (mSchoolEditText != null && mSchoolEditText.hasFocus()) {
//    		mSchoolEditText.clearFocus();
//    	}
//    	if (mAccountNumberEditText != null && mAccountNumberEditText.hasFocus()) {
//    		mAccountNumberEditText.clearFocus();
//    	}
//    	if (mPasswordEditText != null && mPasswordEditText.hasFocus()) {
//    		mPasswordEditText.clearFocus();
//    	}
//    }
//
//	class SelectSchoolAdapter extends BaseAdapter {
//		private Context mContext;
//		private List<EnterpriseInfo> schoolList;
//		private int type;
//		private static final int FIRST_INIT = 0;
//		private static final int ON_TEXT_CHANGED = 1;
//
//		public SelectSchoolAdapter(Context context , List<EnterpriseInfo> dataList) {
//			mContext = context;
//			if (dataList != null) {
//				schoolList = dataList;
//			} else {
//				schoolList = new ArrayList<EnterpriseInfo>();
//			}
//		}
//
//		@Override
//		public int getCount() {
//			return schoolList.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return schoolList.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//	        ViewHolder viewHolder;
//	        if (convertView == null) {
//	            viewHolder = new ViewHolder();
//	            convertView = LayoutInflater.from(mContext).inflate(R.layout.select_school_item, parent, false);
//	            viewHolder.schoolName = (TextView) convertView.findViewById(R.id.select_school_name);
//	            viewHolder.searchIcon = (ImageView) convertView.findViewById(R.id.select_school_search_icon);
//	            convertView.setTag(viewHolder);
//	        } else {
//	            viewHolder = (ViewHolder) convertView.getTag();
//	        }
//
//	        String schoolText = schoolList.get(position).getEnterpriseName();
//	        if (schoolText != null && !"".equals(schoolText)) {
//	        	if (mSearchSchoolText == null || "".equals(mSearchSchoolText)) {
//	        		viewHolder.schoolName.setText(schoolList.get(position).getEnterpriseName().trim());
//	        		viewHolder.searchIcon.setVisibility(View.VISIBLE);
//	        	} else {
//	        		if (type == FIRST_INIT) {
//		        		viewHolder.schoolName.setText(schoolList.get(position).getEnterpriseName().trim());
//		        		viewHolder.searchIcon.setVisibility(View.VISIBLE);
//	        		} else {
//			        	SpannableStringBuilder style = new SpannableStringBuilder(schoolText);
//			        	style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bind_phone_color_green)), 0, mSearchSchoolText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			        	style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.select_customer_item_text_color)), mSearchSchoolText.length(), schoolText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			            viewHolder.schoolName.setText(style);
//			            viewHolder.searchIcon.setVisibility(View.VISIBLE);
//	        		}
//	        	}
//	        } else {
//        		viewHolder.searchIcon.setVisibility(View.GONE);
//        		viewHolder.schoolName.setText("");
//	        }
//
//			return convertView;
//		}
//
//	    private class ViewHolder {
//	        private TextView schoolName;
//	        private ImageView searchIcon;
//	    }
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//		EnterpriseInfo item = (EnterpriseInfo)mSchoolAdapter.getItem(i);
//		mSchoolEditText.setText(item.getEnterpriseName());
//		if (item.getEnterpriseId() != null && !"".equals(item.getEnterpriseId())) {
//			BPlusCApp.mSelectedEnterprisedId = item.getEnterpriseId();
//			BPlusCApp.mSupportUnityCertificate = item.getSupportUnityCertificate();
//			BPlusCApp.mEnterpriseName = item.getEnterpriseName();
//		}
//		switchView(LOGIN_SOFT_INPUT_VIEW);
//		mAccountNumberEditText.requestFocus();
//		if (!StringUtil.isNullOrEmpty(mAccountNumberEditText.getEditableText().toString())) {
//			mAccountNumberEditText.setSelection(mAccountNumberEditText.getEditableText().toString().length());
//		}
//	}
//
//	private boolean isSoftInputShown = false;
//	ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new  ViewTreeObserver.OnGlobalLayoutListener() {
//		@Override
//		public void onGlobalLayout() {
//			Rect r = new Rect();
//			View decorView = null;
//			int screenHeight = 0;
//			int softKeyboardHeight = 0;
//			try {
//				decorView =	LoginActivity.this.getWindow().getDecorView();
//				decorView.getWindowVisibleDisplayFrame(r); //获取到程序显示的区域，包括标题栏，但不包括状态栏
//				screenHeight = decorView.getRootView().getHeight(); //getRootView()，获得当前界面所用的xml文件的根view
//				softKeyboardHeight = screenHeight - r.bottom;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return;
//			}
//			if (softKeyboardHeight > 100) {
//				isSoftInputShown = true;
//			} else {
//				isSoftInputShown = false;
//			}
////			if (loginViewType != LOGIN_SELECT_SCHOOL_VIEW) {
////				if (softKeyboardHeight > 100) {
////					switchView(LOGIN_SOFT_INPUT_VIEW);
////				} else {
////					switchView(LOGIN_NORMAL_VIEW);
////				}
////			}
//		}
//	};
//
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//	    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//	    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//	        if (imm.isActive()) {
//	            imm.hideSoftInputFromWindow(LoginActivity.this.getCurrentFocus().getWindowToken(), 0);
//	        }
//	        return true;
//	    }
//	    return super.dispatchKeyEvent(event);
//	}
//
//}
