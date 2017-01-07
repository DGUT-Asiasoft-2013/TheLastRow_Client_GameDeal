package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.CompanyEntity;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;

/**
 * Created by Administrator on 2017/1/7.
 */

public class ClassifyActivity extends Activity {

    private CompanyEntity[] fragments;
    private ActionBarFragment mainBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        mainBarFragment = (ActionBarFragment) getFragmentManager().findFragmentById(R.id.classity_bar);
        mainBarFragment.setTitle("分类");

        fragments = new CompanyEntity[8];

        final int[] feeds_fragmentsid = new int[]{
                R.id.classify_fragment1,
                R.id.classify_fragment2,
                R.id.classify_fragment3,
                R.id.classify_fragment4,
                R.id.classify_fragment5,
                R.id.classify_fragment6,
                R.id.classify_fragment7,
                R.id.classify_fragment8};

        final int[] feeds_smalllog = new int[]{
                R.drawable.logo_tencent,
                R.drawable.logo_netease,
                R.drawable.logo_shendagames,
                R.drawable.logo_perfectworld,
                R.drawable.logo_cyou,
                R.drawable.logo_tiancity,
                R.drawable.logo_seasun,
                R.drawable.logo_giant
        };

        for (int i = 0; i < 8; i++) {
            final int finalI = i;
            fragments[i] = (CompanyEntity) getFragmentManager().findFragmentById(feeds_fragmentsid[i]);
            fragments[i].setOnCompanyEntityListener(new CompanyEntity.OnCompanyEntityListener() {
                @Override
                public void onCampantEnyityClick() {
                    goEquipmentNews(fragments[finalI], feeds_smalllog[finalI]);
                }
            });
        }


    }

    private void goEquipmentNews(CompanyEntity companyEntity, int logoID) {

        Intent intent = new Intent(ClassifyActivity.this, EquipmentNewsActivity.class);
        intent.putExtra("companyname", companyEntity.getCompanyName());

        intent.putExtra("logo", logoID);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCompangyInfo();
    }

    private void setCompangyInfo() {

        String[] companyname = new String[]{
                getString(R.string.frag_tencent),
                getString(R.string.frag_netease),
                getString(R.string.frag_shendagames),
                getString(R.string.frag_perfectworld),
                getString(R.string.frag_cyou),
                getString(R.string.frag_tiancity),
                getString(R.string.frag_seasun),
                getString(R.string.frag_giant)};

        String[] companynameslogan = new String[]{
                getString(R.string.frag_tencent_slogan),
                getString(R.string.frag_netease_slogan),
                getString(R.string.frag_shendagames_slogan),
                getString(R.string.frag_perfectworld_slogan),
                getString(R.string.frag_cyou_slogan),
                getString(R.string.frag_tiancity_slogan),
                getString(R.string.frag_seasun_slogan),
                getString(R.string.frag_giant_slogan)};

        Bitmap[] bmpSmall = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_tencent),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_netease),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_shendagames),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_perfectworld),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_cyou),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_tiancity),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_seasun),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_giant)};

        Bitmap[] bmpBig = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_tencent_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_netease_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_shendagames_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_perfectworld_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_cyou_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_tiancity_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_seasun_game),
                BitmapFactory.decodeResource(getResources(), R.drawable.logo_giant_game)};


        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                fragments[i].setCompanySloganColor(255, 0, 153, 204);
            } else {
                fragments[i].setCompanySloganColor(255, 204, 0, 0);
            }
            fragments[i].setCompanyNameText(companyname[i]);
            fragments[i].setCompanySloganText(companynameslogan[i]);
            fragments[i].setSmalllogDrawnable(bmpSmall[i]);
            fragments[i].setBiglogDrawnable(bmpBig[i]);
            fragments[i].setCompanyNameColor(255, 0, 0, 0);
        }


    }
}
