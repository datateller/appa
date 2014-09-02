package net.ywb.app.ui;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.ywb.app.AppConfig;
import net.ywb.app.AppContext;
import net.ywb.app.AppException;
import net.ywb.app.R;
//import net.ywb.app.adapter.ListViewCommentAdapter;
import net.ywb.app.bean.Comment;
import net.ywb.app.bean.CommentList;
import net.ywb.app.bean.FavoriteList;
import net.ywb.app.bean.Knowledge;
import net.ywb.app.bean.Notice;
import net.ywb.app.bean.Post;
import net.ywb.app.bean.Result;
import net.ywb.app.bean.URLs;
import net.ywb.app.common.StringUtils;
import net.ywb.app.common.UIHelper;
import net.ywb.app.widget.BadgeView;
import net.ywb.app.widget.PullToRefreshListView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * 知识详情
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class KnowledgeDetail extends BaseActivity {

	private FrameLayout mHeader;
	private LinearLayout mFooter;
	private ImageView mBack;
	private ImageView mFavorite;
	private ImageView mRefresh;
	private TextView mHeadTitle;
	private ProgressBar mProgressbar;
	private ScrollView mScrollView;
    
	private ImageView mOption;
	
	private TextView mTitle;
	
	private WebView mWebView;
    private Handler mHandler;
    private Knowledge knowledgeDetail;
    private int knowledgeId;
    
	private final static int VIEWSWITCH_TYPE_DETAIL = 0x001;
	private final static int VIEWSWITCH_TYPE_COMMENTS = 0x002;
	
	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	private final static int DATA_LOAD_FAIL = 0x003;
	
    private int lvSumData;
    
    private int curId;
	private int curCatalog;	
	private int curLvDataState;
	private int curLvPosition;//当前listview选中的item位置
	
	private int _catalog;
	private int _id;
	private int _uid;
	private String _content;
	private int _isPostToMyZone;
	
	private GestureDetector gd;
	private boolean isFullScreen;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_detail);
        
        this.initView();        
        this.initData();
    	//注册双击全屏事件
    	this.regOnDoubleEvent();
    }
    
    //初始化视图控件
    private void initView()
    {
		knowledgeId = getIntent().getIntExtra("post_id", 0);
		
    	mHeader = (FrameLayout)findViewById(R.id.question_detail_header);
    	mFooter = (LinearLayout)findViewById(R.id.question_detail_footer);
    	mBack = (ImageView)findViewById(R.id.question_detail_back);
    	mRefresh = (ImageView)findViewById(R.id.question_detail_refresh);
    	mHeadTitle = (TextView)findViewById(R.id.question_detail_head_title);
    	mProgressbar = (ProgressBar)findViewById(R.id.question_detail_head_progress);
    	mScrollView = (ScrollView)findViewById(R.id.question_detail_scrollview);

    	mOption = (ImageView)findViewById(R.id.question_detail_footbar_option);
    	mFavorite = (ImageView)findViewById(R.id.question_detail_footbar_favorite);
    	
    	mWebView = (WebView)findViewById(R.id.question_detail_webview);
    	mWebView.getSettings().setSupportZoom(true);
    	mWebView.getSettings().setBuiltInZoomControls(true);
    	mWebView.getSettings().setDefaultFontSize(15);    	
    	
    	mBack.setOnClickListener(UIHelper.finish(this));
    	mFavorite.setOnClickListener(favoriteClickListener);
    	mRefresh.setOnClickListener(refreshClickListener);
//    	mOption.setOnClickListener(optionClickListener);
    }
    
    //初始化控件数据
	private void initData()
	{	
		headButtonSwitch(DATA_LOAD_ING);
		mWebView.loadUrl(URLs.KNOWLEDGE_DETAIL_BASE + "?id=" + String.valueOf(knowledgeId));
		headButtonSwitch(DATA_LOAD_COMPLETE);
//		mHandler = new Handler()
//		{
//			public void handleMessage(Message msg) {
//
//				headButtonSwitch(DATA_LOAD_COMPLETE);
//				
//				if(msg.what == 1)
//				{					
//					mTitle.setText(knowledgeDetail.getTitle());
//					mAuthor.setText(postDetail.getAuthor());
//					mPubDate.setText(StringUtils.friendly_time(postDetail.getPubDate()));
//					mCommentCount.setText(postDetail.getAnswerCount()+"回/"+postDetail.getViewCount()+"阅");
					
					//是否收藏
//					if(knowledgeDetail.getFavorite() == 1)
//						mFavorite.setImageResource(R.drawable.widget_bar_favorite2);
//					else
//						mFavorite.setImageResource(R.drawable.widget_bar_favorite);

					//显示标签
//					String tags = getPostTags(postDetail.getTags());
//					
//					String body = UIHelper.WEB_STYLE + postDetail.getBody() + tags + "<div style=\"margin-bottom: 80px\" />";
//					//读取用户设置：是否加载文章图片--默认有wifi下始终加载图片
//					boolean isLoadImage;
//					AppContext ac = (AppContext)getApplication();
//					if(AppContext.NETTYPE_WIFI == ac.getNetworkType()){
//						isLoadImage = true;
//					}else{
//						isLoadImage = ac.isLoadImage();
//					}
//					if(isLoadImage){
//						body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+","$1");
//						body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+","$1");
//						// 添加点击图片放大支持
//						body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
//								"$1$2\" onClick=\"javascript:mWebViewImageListener.onImageClick('$2')\"");
//					}else{
//						body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>","");
//					}
					
//					mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8",null);
//					mWebView.setWebViewClient(UIHelper.getWebViewClient());	
					
					//发送通知广播
//					if(msg.obj != null){
//						UIHelper.sendBroadCast(KnowledgeDetail.this, (Notice)msg.obj);
//					}
//				}
//				else if(msg.what == 0)
//				{
//					headButtonSwitch(DATA_LOAD_FAIL);
//					
//					UIHelper.ToastMessage(KnowledgeDetail.this, R.string.msg_load_is_null);
//				}
//				else if(msg.what == -1 && msg.obj != null)
//				{
//					headButtonSwitch(DATA_LOAD_FAIL);
//					
//					((AppException)msg.obj).makeToast(KnowledgeDetail.this);
//				}
//			}
//		};
//		
//		initData(knowledgeId, false);
	}
	
	
    /**
     * 头部按钮展示
     * @param type
     */
    private void headButtonSwitch(int type) {
    	switch (type) {
		case DATA_LOAD_ING:
			mScrollView.setVisibility(View.GONE);
			mProgressbar.setVisibility(View.VISIBLE);
			mRefresh.setVisibility(View.GONE);
			break;
		case DATA_LOAD_COMPLETE:
			mScrollView.setVisibility(View.VISIBLE);
			mProgressbar.setVisibility(View.GONE);
			mRefresh.setVisibility(View.VISIBLE);
			break;
		case DATA_LOAD_FAIL:
			mScrollView.setVisibility(View.GONE);
			mProgressbar.setVisibility(View.GONE);
			mRefresh.setVisibility(View.VISIBLE);
			break;
		}
    }
    
    private View.OnClickListener refreshClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			mWebView.loadUrl(URLs.KNOWLEDGE_DETAIL_BASE + "?id=" + String.valueOf(knowledgeId));
			headButtonSwitch(DATA_LOAD_COMPLETE);
		}
	};
    
//	private View.OnClickListener authorClickListener = new View.OnClickListener() {
//		public void onClick(View v) {				
//			UIHelper.showUserCenter(v.getContext(), postDetail.getAuthorId(), postDetail.getAuthor());
//		}
//	};
	
//	private View.OnClickListener optionClickListener = new View.OnClickListener() {
//		public void onClick(View v) {
//			if(knowledgeDetail == null){
//				UIHelper.ToastMessage(v.getContext(), R.string.msg_read_detail_fail);
//				return;
//			}
//			UIHelper.showQuestionOption(KnowledgeDetail.this, mOption, knowledgeDetail);
//		}
//	};
	
	
	private View.OnClickListener favoriteClickListener = new View.OnClickListener() {
		public void onClick(View v) {	
//			if(knowledgeId == 0 || postDetail == null){
//				return;
//			}
//			
//			final AppContext ac = (AppContext)getApplication();
//			if(!ac.isLogin()){
//				UIHelper.showLoginDialog(KnowledgeDetail.this);
//				return;
//			}
//			final int uid = ac.getLoginUid();
//						
//			final Handler handler = new Handler(){
//				public void handleMessage(Message msg) {
//					if(msg.what == 1){
//						Result res = (Result)msg.obj;
//						if(res.OK()){
//							if(postDetail.getFavorite() == 1){
//								postDetail.setFavorite(0);
//								mFavorite.setImageResource(R.drawable.widget_bar_favorite);
//							}else{
//								postDetail.setFavorite(1);
//								mFavorite.setImageResource(R.drawable.widget_bar_favorite2);
//							}
//							//重新保存缓存
//							ac.saveObject(postDetail, postDetail.getCacheKey());
//						}
//						UIHelper.ToastMessage(KnowledgeDetail.this, res.getErrorMessage());
//					}else{
//						((AppException)msg.obj).makeToast(KnowledgeDetail.this);
//					}
//				}        			
//    		};
//    		new Thread(){
//				public void run() {
//					Message msg = new Message();
//					Result res = null;
//					try {
//						if(postDetail.getFavorite() == 1){
//							res = ac.delFavorite(uid, knowledgeId, FavoriteList.TYPE_POST);
//						}else{
//							res = ac.addFavorite(uid, knowledgeId, FavoriteList.TYPE_POST);
//						}
//						msg.what = 1;
//						msg.obj = res;
//		            } catch (AppException e) {
//		            	e.printStackTrace();
//		            	msg.what = -1;
//		            	msg.obj = e;
//		            }
//	                handler.sendMessage(msg);
//				}        			
//    		}.start();	
		}
	};
	
	//初始化视图控件

    
	
	
	/**
	 * 注册双击全屏事件
	 */
	private void regOnDoubleEvent(){
		gd = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				isFullScreen = !isFullScreen;
				if (!isFullScreen) {   
                    WindowManager.LayoutParams params = getWindow().getAttributes();   
                    params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);   
                    getWindow().setAttributes(params);   
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  
                    mHeader.setVisibility(View.VISIBLE);
                    mFooter.setVisibility(View.VISIBLE);
                } else {    
                    WindowManager.LayoutParams params = getWindow().getAttributes();   
                    params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;   
                    getWindow().setAttributes(params);   
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);   
                    mHeader.setVisibility(View.GONE);
                    mFooter.setVisibility(View.GONE);
                }
				return true;
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (isAllowFullScreen()) {
			gd.onTouchEvent(event);
		}
		return super.dispatchTouchEvent(event);
	}
}
