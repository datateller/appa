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
//		login_imageButton=(ImageButton)findViewById(R.id.main_login_imageButton);
//		main_imageView=(ImageView)findViewById(R.id.main_imageView);
//		babyBirthday_textview=(TextView)findViewById(R.id.main_babyBirthday_textview);
//		dateAndWeather_textview=(TextView)findViewById(R.id.main_dateAndWeather_textview);
		knowledgeButton=(Button)findViewById(R.id.knowledge_button);
		commericalButton=(Button)findViewById(R.id.commerical_button);
		surroundingserviceButton=(Button)findViewById(R.id.surroundingservice_button);
		agecircleButton=(Button)findViewById(R.id.agecircle_button);
		schoolcircleButton=(Button)findViewById(R.id.schoolcircle_button);
		familycircleButton=(Button)findViewById(R.id.familycircle_button);
		growthdialyButton=(Button)findViewById(R.id.growthdiary_button);
		settingupButton=(Button)findViewById(R.id.settingup_button);
		collectionButton=(Button)findViewById(R.id.collection_button);
//		
//		ViewGroup group=(ViewGroup)findViewById(R.id.viewGroup);
//		viewPager=(ViewPager)findViewById(R.id.viewPager);
////		����ͼƬ��Դid
//		imgIdArray=new int[]{R.drawable.mainview,R.drawable.mainview1,R.drawable.mainview2,R.drawable.mainview3};
////		�������뵽viewgroup��
//		tips=new ImageView[imgIdArray.length];
//		for(int i=0;i<tips.length;i++){
//			ImageView imageView=new ImageView(Main.this);
//			imageView.setLayoutParams(new LayoutParams(10,10));
//			tips[i]=imageView;
//			if(i==0){
//				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
//			}else{
//				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
//			}
//		    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,    
//                LayoutParams.WRAP_CONTENT));  
//            layoutParams.leftMargin = 5;  
//            layoutParams.rightMargin = 5;  
//            group.addView(imageView, layoutParams);  
//		}
////      ��ͼƬת�ص�������
//        mImageViews=new ImageView[imgIdArray.length];
//        for(int i=0;i<mImageViews.length;i++){
//      	   ImageView imageView=new ImageView(Main.this);
//      	   mImageViews[i]=imageView;
//      	   imageView.setBackgroundResource(imgIdArray[i]);  
//        }
//		
//        viewPager.setAdapter(new MyAdapter());  
//        viewPager.setOnPageChangeListener(Main.this);
//        viewPager.setCurrentItem((mImageViews.length) * 100);  
//        
//		String babyBirthdayString=SharedPreferencesUtils.readBabyBirthdayInfor(Main.this);
//		if(babyBirthdayString==""){
//			babyBirthdayString="�������뱦���ĳ�������";
//		}else{
//			babyBirthdayString = "1990";
//			//babyBirthdayString="��ı����Ѿ�"+UserHelper.getBabyAgeInfo(babyBirthdayString)+"����";
//		}
		//��ǰ���ڼ�ȥ�����ĳ�������
//		babyBirthdayString="���Ѿ�4��6������";
//		babyBirthday_textview.setText(babyBirthdayString);
//		dateAndWeather_textview.setText(DateUtils.getCurrentDay());
//		login_imageButton.setOnClickListener(new ButtonClick());
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
//			case R.id.knowledge_button:
//				intent.setClass(Home.this, KnowledgeActivity.class);
//				break;
//			case R.id.commerical_button:
//				intent.setClass(Home.this, CommericalActivity.class);
//				break;
//			case R.id.surroundingservice_button:
//				intent.setClass(Home.this, SurroundingActivity.class);
//				break;
//			case R.id.agecircle_button:
//				intent.setClass(Home.this,AgeCircleActivity.class);
//				break;
//			case R.id.schoolcircle_button:
//				intent.setClass(Home.this, SchoolCircleActivity.class);
//				break;
//			case R.id.familycircle_button:
//				intent.setClass(Home.this, FamilyCircleActivity.class);
//				break;
//			case R.id.collection_button:
//				intent.setClass(Main.this, UserInfo.class);
//				break;
			case R.id.settingup_button:
				//判断登录
				final AppContext ac = (AppContext)getApplication();
				if(!ac.isLogin()){
					UIHelper.showLoginDialog(Main.this);
					return;
				} else {
					intent.setClass(Main.this, UserInfo.class);
				}
				break;
//			case R.id.growthdiary_button:
//				intent.setClass(Home.this, GrowthDialyActivity.class);
//				break;
//			case R.id.main_login_imageButton:
//				intent.setClass(Home.this, LoginActivity.class);
//				break;
			default:
				break;
			}
			startActivity(intent);
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
