package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/1/10.
 */

public class ViewPagerFragment extends Fragment {

    private View view;
//    private ImageView dot_1, dot_2, dot_3, dot_4, dot_5;
    private ViewPager viewPager;
    private List<ImageView> dotList;
    private List<View> adertisementList;
    private int[] backgroundRes;
    private int[] dotviewId;

    private int currentposition = 1;
    private int dotposition = 0;

    private ScheduledExecutorService autoService;
    private boolean isHeadle = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_viewpager, container);

            viewPager = (ViewPager) view.findViewById(R.id.frag_viewpager_advertisement);
//            dot_1 = (ImageView) view.findViewById(R.id.frag_viewpager_imagedot_1);
//            dot_2 = (ImageView) view.findViewById(R.id.frag_viewpager_imagedot_2);
//            dot_3 = (ImageView) view.findViewById(R.id.frag_viewpager_imagedot_3);
//            dot_4 = (ImageView) view.findViewById(R.id.frag_viewpager_imagedot_4);
//            dot_5 = (ImageView) view.findViewById(R.id.frag_viewpager_imagedot_5);

            dotviewId = new int[]{
                R.id.frag_viewpager_imagedot_1,
                R.id.frag_viewpager_imagedot_2,
                R.id.frag_viewpager_imagedot_3,
                R.id.frag_viewpager_imagedot_4,
                R.id.frag_viewpager_imagedot_5
            };


            dotList = new ArrayList<>();
//            dotList.add(dot_1);
//            dotList.add(dot_2);
//            dotList.add(dot_3);
//            dotList.add(dot_4);
//            dotList.add(dot_5);
            for (int id : dotviewId) {
                ImageView imageView = (ImageView) view.findViewById(id);
                imageView.setSelected(false);
                dotList.add(imageView);
            }

            backgroundRes = new int[]{
                    R.drawable.wanmeihongyan,
                    R.drawable.mofawangzuo,
                    R.drawable.tanwanlanyue,
                    R.drawable.chuanqibaye,
                    R.drawable.daomubiji,
                    R.drawable.wanmeihongyan,
                    R.drawable.mofawangzuo
            };

            adertisementList = new ArrayList<>();
            for (int res : backgroundRes) {
                View advertisementView = inflater.inflate(R.layout.frag_viewpager_advertisement_item, container);
                advertisementView.setBackgroundResource(res);
                adertisementList.add(advertisementView);
            }

            viewPager.setAdapter(advertisementAdapter);
            viewPager.addOnPageChangeListener(new PagerChangeListener());
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(currentposition ,false);
        dotList.get(dotposition).setSelected(true);
        autoService = Executors.newSingleThreadScheduledExecutor();
        //第一个参数执行线程，第二个第一次执行延迟时间，第三个上一次结束到下一次开始时间，第四个延迟时间单位
        autoService.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (viewPager) {
                            viewPager.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (isHeadle) {
                                        return;
                                    }
//                                    Log.d("currentposition", "---------------------------" + currentposition);
//                                    currentposition++;
                                    currentposition = (currentposition % dotList.size()) + 1;
//                                    currentposition = currentposition % (viewPager.getAdapter().getCount() - 2);
                                    viewPager.setCurrentItem(currentposition);
                                }
                            });
                        }
                    }
                }, 1000, 3000, TimeUnit.MILLISECONDS);
    }

    private class PagerChangeListener implements ViewPager.OnPageChangeListener {

        //正在滚动时的处理方法
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //滚动完毕
        @Override
        public void onPageSelected(int position) {
//            if (position == viewPager.getAdapter().getCount() - 1) {
//                position = 1;
//            }
//            if (position == 0) {
//                position = viewPager.getAdapter().getCount() - 2;
//            }
            currentposition = position;
            if (position == 0 || position == dotList.size()) {        //最后一张图,dot为4
                dotList.get(dotList.size() - 2).setSelected(false);         //右滑，上一个设为不选
                dotList.get(dotList.size() - 1).setSelected(true);
                dotList.get(0).setSelected(false);            //左滑，下一个设为不选
                dotposition = dotList.size() -1;
            } else if (position == 1 || position == dotList.size()+1) {   //第一张图，dot为0
                dotList.get(dotList.size() - 1).setSelected(false);         //右滑，上一个设为不选
                dotList.get(0).setSelected(true);
                dotList.get(1).setSelected(false);
                dotposition = 0;
            } else {
                dotList.get(position - 2).setSelected(false);
                dotList.get(position - 1).setSelected(true);
                dotList.get(position).setSelected(false);
                dotposition = position - 1;
            }
//            Log.d("position", "-------------------------------" + currentposition);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:             //没有动作时,值为0
                    if (currentposition == viewPager.getAdapter().getCount() - 1) {
                        viewPager.setCurrentItem(1,false);
                    }
                    if (currentposition == 0) {
                        viewPager.setCurrentItem(5,false);
                    }
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:             //正在滑动，值为1
                    isHeadle = true;                              //触点广告时，定时停止
//                    Log.d("isheadle", "-------------------------------" + isHeadle);
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:             //滑动结束，值为2
                    isHeadle = false;                             //手指松开，定时继续执行
//                    Log.d("ishandle", "-------------------------------" + isHeadle);
//                    if (currentposition == viewPager.getAdapter().getCount() - 1) {
//                        currentposition = 1;
//                    }
//                    if (currentposition == 0) {
//                        currentposition = 5;
//                    }
                    break;
            }
        }
    }



    private PagerAdapter advertisementAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return adertisementList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == adertisementList.get((int)object));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(adertisementList.get(position));

            return position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(adertisementList.get(position));
        }
    };

//    @Override
//    public void onPause() {
//        super.onPause();
//        autoService.shutdown();
//    }
}
