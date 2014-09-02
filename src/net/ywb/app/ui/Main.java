package net.ywb.app.ui;

import net.ywb.app.AppContext;
import net.ywb.app.common.UIHelper;
//import net.ywb.app.ui.UserCenter;
import net.ywb.app.R;
import net.ywb.utils.DateUtils;
import net.ywb.utils.SharedPreferencesUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends BaseActivity implements OnPageChangeListener{

	private ImageButton login_imageButton;
//	private TextView babyBirthday_textview;
	private TextView dateAndWeather_textview;
	private Button knowledgeButton;
	private Button commericalButton;
	private Button surroundingserviceButton;
	private Button agecircleButton;
	private Button familycircleButton;
	private Button schoolcircleButton;
	private Button growthdialyButton;
	private Button collectionButton;
	private Button settingupButton;
	
	private ViewPager viewPager;
	private ImageView[] tips;
	private ImageView[] mImageViews;
	private int[] imgIdArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		String username=UserHelper.readUserName(Home.this);
//		if(username.equals("")){
//			UserHelper.saveUserInfo(Home.this,"anonymous","wjbb123",3);
//		}
		InitWidget();
	}

	private void InitWidget() {
		// TODO Auto-generated method stub
		knowledgeButton=(Button)findViewById(R.id.knowledge_button);
		commericalButton=(Button)findViewById(R.id.commerical_button);
		surroundingserviceButton=(Button)findViewById(R.id.surroundingservice_button);
		agecircleButton=(Button)findViewById(R.id.agecircle_button);
		schoolcircleButton=(Button)findViewById(R.id.schoolcircle_button);
		familycircleButton=(Button)findViewById(R.id.familycircle_button);
		growthdialyButton=(Button)findViewById(R.id.growthdiary_button);
		settingupButton=(Button)findViewById(R.id.settingup_button);
		collectionButton=(Button)findViewById(R.id.collection_button);

		knowledgeButton.setOnClickListener(new ButtonClick());
		commericalButton.setOnClickListener(new ButtonClick());
		surroundingserviceButton.setOnClickListener(new ButtonClick());
		agecircleButton.setOnClickListener(new ButtonClick());
		familycircleButton.setOnClickListener(new ButtonClick());
		schoolcircleButton.setOnClickListener(new ButtonClick());
		growthdialyButton.setOnClickListener(new ButtonClick());
		settingupButton.setOnClickListener(new ButtonClick());
		collectionButton.setOnClickListener(new ButtonClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class ButtonClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.knowledge_button:
//				UIHelper.showKnowledgeList(Main.this, "知识");
				intent.setClass(Main.this, KnowledgeListActivity.class);
				startActivity(intent);
				break;
			case R.id.settingup_button:
				//判断登录
				final AppContext ac = (AppContext)getApplication();
				if(!ac.isLogin()){
					UIHelper.showLoginDialog(Main.this);
					return;
				} else {
					intent.setClass(Main.this, UserInfo.class);
					startActivity(intent);
				}
				break;
			default:
				break;
			}
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		 setImageBackground(arg0 % mImageViews.length); 
	}
	
	public class MyAdapter extends PagerAdapter{  
		  
        @Override  
        public int getCount() {  
            return Integer.MAX_VALUE;  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public void destroyItem(View container, int position, Object object) {  
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);  
              
        }  
  
        /** 
         * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ� 
         */  
        @Override  
        public Object instantiateItem(View container, int position) {  
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);  
            return mImageViews[position % mImageViews.length];  
        }     
    }  
	
	 private void setImageBackground(int selectItems){  
	        for(int i=0; i<tips.length; i++){  
	            if(i == selectItems){  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);  
	            }else{  
	                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);  
	            }  
	        }  
	    }  
}
