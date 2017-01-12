package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PictureDeleteActivity extends Activity {

    final static int RESULT_CODE_PICTUREDELETE = 0x101;

    private ViewPager viewPager;
    private List<String> pictureGet;
    private List<ImageView> imageList;
    private List<ImageView> preimageList;
    private TextView textView;
    private int checkPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturedelete);

        viewPager = (ViewPager) findViewById(R.id.picturedeletd_viewpager);
        textView = (TextView) findViewById(R.id.picturedeletd_text);
        pictureGet = getIntent().getStringArrayListExtra("pictureget");
        checkPosition = getIntent().getIntExtra("position", 0);

        imageList = new ArrayList<>();
        preimageList = new ArrayList<>();

        for (String res : pictureGet) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(BitmapFactory.decodeFile(res));
            imageList.add(imageView);
            preimageList.add(imageView);
        }


        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(checkPosition);
        viewPager.addOnPageChangeListener(new ViewPagerListener());

        findViewById(R.id.picturedeletd_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_PICTUREDELETE , new Intent().putStringArrayListExtra("deletelist" , (ArrayList<String>) pictureGet));
                finish();
            }
        });

        findViewById(R.id.picturedeletd_deletd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture();
            }
        });
    }

    private void deletePicture() {
//        Log.d("getCurrent", "--------------------------" + viewPager.getCurrentItem());
//        if (imageList.size() == 0) {
//            return;
//        }
        int currentItem = viewPager.getCurrentItem();
        imageList.remove(currentItem);              //长度-1
        pictureGet.remove(currentItem);
        viewPagerAdapter.notifyDataSetChanged();
        preimageList.remove(currentItem);
        if (imageList.size() == 0) {
            setResult(RESULT_CODE_PICTUREDELETE , new Intent().putStringArrayListExtra("deletelist" , (ArrayList<String>) pictureGet));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText("(" + String.valueOf(checkPosition + 1) +"/" + imageList.size() +")");
    }

    private class ViewPagerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            checkPosition = position;
            textView.setText("(" + String.valueOf(checkPosition + 1) +"/" + imageList.size() +")");
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private PagerAdapter viewPagerAdapter = new PagerAdapter() {
//        ViewGroup container;

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == imageList.get((int) object));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
//            this.container = container;

            return position;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(preimageList.get(position));
//            this.container = container;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        //        @Override
//        public void notifyDataSetChanged() {
//            destroyItem(container, viewPager.getCurrentItem(), imageList.get(viewPager.getCurrentItem()));
////            viewPager.setCurrentItem();
//            super.notifyDataSetChanged();
//        }
    };
}
