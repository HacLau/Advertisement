package com.qc.advertisement.fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qc.advertisement.R;
import com.qc.advertisement.util.ToastUtils;
import com.qc.advertisement.view.RollViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/20.
 */
public class CommunityFragment extends Fragment {
    private Context context = getActivity();
    //存放轮播图的线性布局
    private LinearLayout linearLayout;
    //存放指示点的线性布局
    private LinearLayout pointLinearLayout;
    //存放指示点的集合
    private List<ImageView> pointList=new ArrayList<>();
    //上一个指示点
    private int lastPosition=0;
    private int[] urls={R.mipmap.banner_do,R.mipmap.banner_advantage,R.mipmap.banner_information,R.mipmap.banner_start_line};

   // private ImageList<Integer> images=new ImageList<Integer>(){R.mipmap.banner_do,R.mipmap.banner_advantage,R.mipmap.banner_information,R.mipmap.banner_start_line};
    //Integer[] images={R.mipmap.banner_do,R.mipmap.banner_advantage,R.mipmap.banner_information,R.mipmap.banner_start_line};
    //private List<Bitmap> mImage = new ArrayList<Bitmap>(){R.mipmap.banner_do,R.mipmap.banner_advantage,R.mipmap.banner_information,R.mipmap.banner_start_line};
    //private List<String> mTitle = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_roll_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lastPosition=0;
        linearLayout= (LinearLayout) view.findViewById(R.id.top_news_viewpager_ll);
        pointLinearLayout= (LinearLayout) view.findViewById(R.id.dots_ll);

        //第一步：添加轮播图（也可以直接将布局写成咱们自定义的viewpager）
        RollViewPager rollViewPager = new RollViewPager(getActivity());
        linearLayout.addView(rollViewPager);

        //第二步：传递轮播图需要的图片url集合或者数组（在真实项目中我们需要获得图片的url集合，然后传递给轮播图）
        rollViewPager.setImageUrls(urls);

        //第三步：添加指示点
        addPoints();

        //第四步：给轮播图添加界面改变监听，切换指示点
        rollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                position=position%urls.length;
                //切换指示点
                pointList.get(lastPosition).setImageResource(R.mipmap.dot_normal);
                pointList.get(position).setImageResource(R.mipmap.dot_focus);
                lastPosition=position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        //第五步：轮播图点击监听
        rollViewPager.setOnItemClickListener(new RollViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showStaticToast(getActivity(),position+"");
            }
        });

        //第六步：设置当前页面，最好不要写最大数除以2，其实写了50就足够了，谁没事无聊到打开app二话不说直接对着轮播图往相反方向不停的划几十下
        rollViewPager.setCurrentItem(50 - 50 % urls.length);

        //第七步：设置完之后就可以轮播了：开启自动轮播
        rollViewPager.startRoll();
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //整个方法直接复制就可以
    private void addPoints() {
        pointList.clear();
        pointLinearLayout.removeAllViews();
        for(int x=0;x<urls.length;x++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.mipmap.dot_normal);
            //导报的时候指示点的父View是什么布局就导什么布局的params,这里导的是线性布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置间隔
            params.leftMargin=24;
            //添加到线性布局;params指定布局参数，不然点就按在一起了
            pointLinearLayout.addView(imageView,params);
            //把指示点存放到集合中
            pointList.add(imageView);
        }
    }

}
