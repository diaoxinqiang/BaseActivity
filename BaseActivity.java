/*
 * Activity的基类
 */
public abstract class BaseActivity extends Activity implements OnClickListener {
	public static final String INTENTTAG = "intentTag";
	public static final String INTENTTAG2 = "intentTag2";
	public static final String INTENTTAG3 = "intentTag3";
	protected RelativeLayout baseactivity_topLayout;
	protected ImageView baseactivity_backImg, baseactivity_rightImg1,
			baseactivity_rightImg2;
	protected TextView baseactivity_title, baseactivity_backText,
			baseactivity_rightText;
	protected LinearLayout baseactivity_contextLayout, badnetworkLayout,
			loadingLayout, baseactivity_backLayout, baseactivity_rightLayout;
	protected LayoutInflater inflater;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);

		baseactivity_topLayout = (RelativeLayout) findViewById(R.id.baseactivity_topLayout);
		baseactivity_backImg = (ImageView) findViewById(R.id.baseactivity_backImg);
		baseactivity_rightImg1 = (ImageView) findViewById(R.id.baseactivity_rightImg1);
		baseactivity_rightImg2 = (ImageView) findViewById(R.id.baseactivity_rightImg2);
		baseactivity_title = (TextView) findViewById(R.id.baseactivity_title);
		baseactivity_backText = (TextView) findViewById(R.id.baseactivity_backText);
		baseactivity_rightText = (TextView) findViewById(R.id.baseactivity_rightText);
		baseactivity_contextLayout = (LinearLayout) findViewById(R.id.baseactivity_contextLayout);
		badnetworkLayout = (LinearLayout) findViewById(R.id.baseactivity_badnetworkLayout);
		loadingLayout = (LinearLayout) findViewById(R.id.baseactivity_loadingLayout);
		baseactivity_backLayout = (LinearLayout) findViewById(R.id.baseactivity_backLayout);
		baseactivity_rightLayout = (LinearLayout) findViewById(R.id.baseactivity_rightLayout);
		inflater = LayoutInflater.from(this);
        findView();
        initView();
        initListener();
        initData();
		baseactivity_backLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				closeActivity();
			}
		});

		ActivityManage.getInstance().addActivity(this);
	}

	/*
	 * 返回事件
	 */
	public void closeActivity() {
		ActivityManage.getInstance().delectActivity(this);
		finish();
		overridePendingTransition(R.anim.dialog_right_in,
				R.anim.dialog_right_out);
	}

	/*
	 * Activity的跳转
	 */
	public void setIntentClass(Class<?> cla) {
		Intent intent = new Intent();
		intent.setClass(this, cla);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/*
	 * Activity的跳转-带参数
	 */
	public void setIntentClass(Class<?> cla, Object obj) {
		Intent intent = new Intent();
		intent.setClass(this, cla);
		intent.putExtra(INTENTTAG, (Serializable) obj);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * Activity-webviewactivity的跳转-带参数
	 * 
	 * @param cla
	 * @param title
	 * @param link
	 */
	public void setIntentWebView(String title, String link) {
		Intent intent = new Intent();
		intent.setClass(this, WebviewActivity.class);
		intent.putExtra(INTENTTAG, title);
		intent.putExtra(INTENTTAG2, link);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/*
	 * 显示加载前的动画
	 */
	protected void showLoadingLayout() {
		loadingLayout.setVisibility(View.VISIBLE);
		badnetworkLayout.setVisibility(View.GONE);
		baseactivity_contextLayout.setVisibility(View.GONE);
		// ImageView loading_imageView = (ImageView) loadingLayout
		// .findViewById(R.id.loading_imageView);
		// loading_imageView.setBackgroundResource(R.anim.loading);
		// AnimationDrawable animationDrawable = (AnimationDrawable)
		// loading_imageView
		// .getBackground();
		// animationDrawable.start();
	}

	/*
	 * 加载失败或没网络
	 */
	protected void showBadnetworkLayout() {
		loadingLayout.setVisibility(View.GONE);
		baseactivity_contextLayout.setVisibility(View.GONE);
		badnetworkLayout.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载完
	 */
	protected void showContextLayout() {
		loadingLayout.setVisibility(View.GONE);
		baseactivity_contextLayout.setVisibility(View.VISIBLE);
		badnetworkLayout.setVisibility(View.GONE);

	}

	/**
	 * showToast
	 */
	protected void showToast(String msg) {
		Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
		showLog(getClass().getSimpleName(), msg);
	}

	/**
	 * showLog
	 */
	protected void showLog(String flag, String msg) {
		Log.v("flag", "-----------" + flag + "---------------" + msg);
	}


	/**
	 * [是否连续两次返回退出]
	 * 
	 */
	public void setBackExit(boolean isBackExit) {
		this.isBackExit = isBackExit;
	}

	private long exitTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isBackExit) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 初始化事件
	 */
	protected abstract void initListener();

}