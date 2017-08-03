package com.qc.advertisement.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideActivityViewPagerAdapter extends PagerAdapter {
	private ArrayList<ImageView> mImageViewList;
	public GuideActivityViewPagerAdapter(Context context,
			ArrayList<ImageView> mImageViewList) {
		this.mImageViewList = mImageViewList;
	}

	@Override
	public int getCount() {
		return mImageViewList.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView mImageView = mImageViewList.get(position);
		container.addView(mImageView);
		return mImageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

}
