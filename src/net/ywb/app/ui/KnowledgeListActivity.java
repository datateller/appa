package net.ywb.app.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.ywb.app.AppContext;
import net.ywb.app.AppException;
import net.ywb.app.R;
import net.ywb.app.adapter.ListViewKnowledgeAdapter;
import net.ywb.app.bean.Notice;
import net.ywb.app.bean.Post;
import net.ywb.app.bean.PostList;
import net.ywb.app.bean.Knowledge;
import net.ywb.app.bean.KnowledgeList;
import net.ywb.app.common.StringUtils;
import net.ywb.app.common.UIHelper;
import net.ywb.app.widget.PullToRefreshListView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Tag相关知识列表
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-8-27
 */
public class KnowledgeListActivity extends BaseActivity{
	
	private ImageView mHome;
	private TextView mHeadTitle;
	private ProgressBar mProgressbar;
	
	private PullToRefreshListView lvKnowledge;
	private ListViewKnowledgeAdapter lvKnowledgeAdapter;
	private List<Knowledge> lvKnowledgeData = new ArrayList<Knowledge>();
	private View lvKnowledge_footer;
	private TextView lvKnowledge_foot_more;
	private ProgressBar lvKnowledge_foot_progress;
    private Handler lvKnowledgeHandler;
    private int lvKnowledgeSumData;
    
	private final static int DATA_LOAD_ING = 0x001;
	private final static int DATA_LOAD_COMPLETE = 0x002;
	private final static int DATA_LOAD_FAIL = 0x003;
    
    private String curTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_tag);
		
		//初始化视图控件
		initView();
		
		//初始化控件数据
		initData();
	}
	
	//初始化视图控件
	private void initView(){
		curTag = getIntent().getStringExtra("post_tag");
		
		mHome = (ImageView)findViewById(R.id.question_tag_home);
		mHeadTitle = (TextView)findViewById(R.id.question_tag_head_title);
		mProgressbar = (ProgressBar)findViewById(R.id.question_tag_head_progress);
		
		mHome.setOnClickListener(homeClickListener);
		mHeadTitle.setText(curTag);
		
		lvKnowledgeAdapter = new ListViewKnowledgeAdapter(this, lvKnowledgeData, R.layout.question_listitem);        
        lvKnowledge_footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
        lvKnowledge_foot_more = (TextView)lvKnowledge_footer.findViewById(R.id.listview_foot_more);
        lvKnowledge_foot_progress = (ProgressBar)lvKnowledge_footer.findViewById(R.id.listview_foot_progress);
        lvKnowledge = (PullToRefreshListView)findViewById(R.id.question_tag_listview);
        lvKnowledge.addFooterView(lvKnowledge_footer);//添加底部视图  必须在setAdapter前
        lvKnowledge.setAdapter(lvKnowledgeAdapter); 
        lvKnowledge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		//点击头部、底部栏无效
        		if(position == 0 || view == lvKnowledge_footer) return;
        		
        		Knowledge knowledge = null;		
        		//判断是否是TextView
        		if(view instanceof TextView){
        			knowledge = (Knowledge)view.getTag();
        		}else{
        			TextView tv = (TextView)view.findViewById(R.id.question_listitem_title);
        			knowledge = (Knowledge)tv.getTag();
        		}
        		if(knowledge == null) return;
        		
        		//跳转到知识详情
        		UIHelper.showKnowledgeDetail(view.getContext(), knowledge.getId());
        	}        	
		});
        lvKnowledge.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvKnowledge.onScrollStateChanged(view, scrollState);
				
				//数据为空--不用继续下面代码了
				if(lvKnowledgeData.size() == 0) return;
				
				//判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if(view.getPositionForView(lvKnowledge_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				int lvDataState = StringUtils.toInt(lvKnowledge.getTag());
				if(scrollEnd && lvDataState==UIHelper.LISTVIEW_DATA_MORE)
				{
					lvKnowledge.setTag(UIHelper.LISTVIEW_DATA_LOADING);
					lvKnowledge_foot_more.setText(R.string.load_ing);
					lvKnowledge_foot_progress.setVisibility(View.VISIBLE);
					//当前pageIndex
					int pageIndex = lvKnowledgeSumData/AppContext.PAGE_SIZE;
					loadLvKnowledgeData(curTag, pageIndex, lvKnowledgeHandler, UIHelper.LISTVIEW_ACTION_SCROLL);
				}
			}
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				lvKnowledge.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
        lvKnowledge.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
            	loadLvKnowledgeData(curTag, 0, lvKnowledgeHandler, UIHelper.LISTVIEW_ACTION_REFRESH);
            }
        });			
	}
	
	//初始化控件数据
	private void initData(){
		lvKnowledgeHandler = new Handler()
		{
			public void handleMessage(Message msg) 
			{				
				if(msg.what >= 0){	
					
					headButtonSwitch(DATA_LOAD_COMPLETE);
					
					KnowledgeList list = (KnowledgeList)msg.obj;
					Notice notice = list.getNotice();
					//处理listview数据
					switch (msg.arg1) {
					case UIHelper.LISTVIEW_ACTION_INIT:
					case UIHelper.LISTVIEW_ACTION_REFRESH:
						lvKnowledgeSumData = msg.what;
						lvKnowledgeData.clear();//先清除原有数据
						lvKnowledgeData.addAll(list.getKnowledgelist());
						break;
					case UIHelper.LISTVIEW_ACTION_SCROLL:
						lvKnowledgeSumData += msg.what;
						if(lvKnowledgeData.size() > 0){
							for(Knowledge p1 : list.getKnowledgelist()){
								boolean b = false;
								for(Knowledge p2 : lvKnowledgeData){
									if(p1.getId() == p2.getId()){
										b = true;
										break;
									}
								}
								if(!b) lvKnowledgeData.add(p1);
							}
						}else{
							lvKnowledgeData.addAll(list.getKnowledgelist());
						}
						break;
					}						
					
					if(msg.what < 20){
						lvKnowledge.setTag(UIHelper.LISTVIEW_DATA_FULL);
						lvKnowledgeAdapter.notifyDataSetChanged();
						lvKnowledge_foot_more.setText(R.string.load_full);
					}else if(msg.what == 20){					
						lvKnowledge.setTag(UIHelper.LISTVIEW_DATA_MORE);
						lvKnowledgeAdapter.notifyDataSetChanged();
						lvKnowledge_foot_more.setText(R.string.load_more);
					}
					//发送通知广播
					if(notice != null){
						UIHelper.sendBroadCast(KnowledgeListActivity.this, notice);
					}
				}
				else if(msg.what == -1){
					
					headButtonSwitch(DATA_LOAD_FAIL);
					
					//有异常--显示加载出错 & 弹出错误消息
					lvKnowledge.setTag(UIHelper.LISTVIEW_DATA_MORE);
					lvKnowledge_foot_more.setText(R.string.load_error);
					((AppException)msg.obj).makeToast(KnowledgeListActivity.this);
				}
				if(lvKnowledgeData.size()==0){
					lvKnowledge.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
					lvKnowledge_foot_more.setText(R.string.load_empty);
				}
				lvKnowledge_foot_progress.setVisibility(View.GONE);
				if(msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH){
					lvKnowledge.onRefreshComplete(getString(R.string.pull_to_refresh_update) + new Date().toLocaleString());
					lvKnowledge.setSelection(0);
				}
			}
		};
		this.loadLvKnowledgeData(curTag, 0, lvKnowledgeHandler, UIHelper.LISTVIEW_ACTION_INIT);
    }
	
    /**
     * 线程加载问答列表数据
     * @param tag 当前Tag
     * @param pageIndex 当前页数
     * @param handler 处理器
     * @param action 动作标识
     */
	private void loadLvKnowledgeData(final String tag, final int pageIndex, final Handler handler, final int action){
		
		headButtonSwitch(DATA_LOAD_ING);
		
		new Thread(){
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				if(action == UIHelper.LISTVIEW_ACTION_REFRESH || action == UIHelper.LISTVIEW_ACTION_SCROLL)
					isRefresh = true;
				try {
					KnowledgeList list = ((AppContext)getApplication()).getKnowledgeListByTag(tag, pageIndex, isRefresh);				
					msg.what = list.getPageSize();
					msg.obj = list;
	            } catch (AppException e) {
	            	e.printStackTrace();
	            	msg.what = -1;
	            	msg.obj = e;
	            }
				msg.arg1 = action;//告知handler当前action
                handler.sendMessage(msg);
			}
		}.start();
	}
	
    /**
     * 头部加载动画展示
     * @param type
     */
    private void headButtonSwitch(int type) {
    	switch (type) {
		case DATA_LOAD_ING:
			mProgressbar.setVisibility(View.VISIBLE);
			break;
		case DATA_LOAD_COMPLETE:
			mProgressbar.setVisibility(View.GONE);
			break;
		case DATA_LOAD_FAIL:
			mProgressbar.setVisibility(View.GONE);
			break;
		}
    }
    
	private View.OnClickListener homeClickListener = new View.OnClickListener() {
		public void onClick(View v) {	
			UIHelper.showHome(KnowledgeListActivity.this);
		}
	};
}
