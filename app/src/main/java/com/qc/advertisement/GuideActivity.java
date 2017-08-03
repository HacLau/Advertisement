package com.qc.advertisement;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.qc.advertisement.adapter.GuideActivityViewPagerAdapter;
import com.qc.advertisement.util.Constants;
import com.qc.advertisement.util.SharedPreferencesUtils;
import com.qc.advertisement.util.UnitTransformUtil;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class GuideActivity extends Activity {
	private int[] mImageIds = new int[] { R.mipmap.guide_1,
			R.mipmap.guide_2, R.mipmap.guide_3 };
	private ArrayList<ImageView> mImageViewList;
	private ArrayList<ImageView> mImageViewDotList;
	private ViewPager mViewPager;
	private LinearLayout mLinearLayout;
	private ImageView mRedPoint;
	private Button mButton;
	private Context context = this;
	private Shimmer shimmer;
	private ShimmerTextView myShimmerTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initView();
		initData();
	}
	/**
	 * 初始化视图控件
	 * 
	 * void
	 * 20172017-4-1上午10:14:13
	 * HacLau
	 */
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.guide_vp);
		mButton = (Button) findViewById(R.id.guide_btn);
		mLinearLayout = (LinearLayout) findViewById(R.id.guide_ll_point_gray);
		mRedPoint = (ImageView) findViewById(R.id.guide_ll_point_red);
		myShimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer_tv);
		mImageViewList = new ArrayList<ImageView>();
		mImageViewDotList = new ArrayList<ImageView>();
		context = this;
		mImageViewList.clear();
		mImageViewDotList.clear();
	}
	/**
	 * 初始化试图数据
	 * 
	 * void
	 * 20172017-4-1上午10:14:44
	 * HacLau
	 */
	private void initData() {

		shimmer = new Shimmer();
		shimmer.start(myShimmerTextView);
		for(int i = 0;i < mImageIds.length;i++){
			createImageView(i);
			createDot();
		}
		GuideActivityViewPagerAdapter adapter = new GuideActivityViewPagerAdapter(context,mImageViewList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(viewListener);
		mButton.setOnClickListener(btnListener);
	}
	/**
	 * 动态创建导航点
	 * 
	 * void
	 * 20172017-4-1上午10:15:04
	 * HacLau
	 */
	private void createDot() {
		ImageView point = new ImageView(this);
		point.setBackgroundResource(R.drawable.shape_guideactivity_viewpager_dot_gray);
		mImageViewDotList.add(point);
		
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.rightMargin = UnitTransformUtil.dip2px(context,5);
		params.leftMargin = UnitTransformUtil.dip2px(context,5);
		point.setLayoutParams(params);
		
		mLinearLayout.addView(point);
	}
	/**
	 * 向ImageView中添加图片
	 * @param i
	 * void
	 * 20172017-4-1上午10:15:21
     */
	private void createImageView(int i) {
		ImageView mImageView = new ImageView(this);
		mImageView.setBackgroundResource(mImageIds[i]);
		mImageViewList.add(mImageView);
	}

	/**
	 * 点击登录按钮的监听事件
	 */
	public OnClickListener btnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SharedPreferencesUtils.putBoolean(context, Constants.ISFIRSTINTO, false);
			Intent intent = new Intent(context,LoginActivity.class);
			startActivity(intent);
			finish();
		}
	};
	/**
	 * ViewPager页面改变的时候的监听事件
	 */
	public OnPageChangeListener viewListener = new OnPageChangeListener(){
		
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			int liftMargin = (UnitTransformUtil.dip2px(context,5 + 20 * (position + positionOffset)));
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
			layoutParams.leftMargin = liftMargin;
			mRedPoint.setLayoutParams(layoutParams);
		}
		
		@Override
		public void onPageSelected(int position) {
			if(position == mImageIds.length - 1){
				mButton.setVisibility(View.VISIBLE);
				myShimmerTextView.setVisibility(View.VISIBLE);
			}else{
				mButton.setVisibility(View.GONE);
				myShimmerTextView.setVisibility(View.GONE);
			}
		}
		
		@Override
		public void onPageScrollStateChanged(int state) {

			
		}
		
	};
	
}
