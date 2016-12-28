package com.example.z.thelastrow_client_gamedeal.fragment.page;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.BuyActivity;
import com.example.z.thelastrow_client_gamedeal.EquipmentNewsActivity;
import com.example.z.thelastrow_client_gamedeal.FeedsSearchActivity;
import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.SellActivity;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.CompanyEntity;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;

/**
 * Created by Z on 2016/12/21.
 */

public class FeedsListFragment extends Fragment {

    private View view, frag_buy, frag_sell;
    private TextView frag_search;
    private ImageView frag_avatar, frag_camera;
    private CompanyEntity[] feeds_fragments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_page_feeds, null);

            frag_search = (TextView) view.findViewById(R.id.frag_search);
            frag_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goFeedsSearch();
                }
            });

            frag_avatar = (ImageView) view.findViewById(R.id.frag_avatar);
            frag_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goLogin();
                }
            });

            frag_camera = (ImageView) view.findViewById(R.id.frag_camera);
            frag_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goCamera();
                }
            });

            frag_buy = view.findViewById(R.id.frag_buy);
            frag_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goBuy();
                }
            });

            frag_sell = view.findViewById(R.id.frag_sell);
            frag_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goSell();
                }
            });


            feeds_fragments = new CompanyEntity[8];

            int[] feeds_fragmentsid = new int[]{
                    R.id.feeds_fragment1,
                    R.id.feeds_fragment2,
                    R.id.feeds_fragment3,
                    R.id.feeds_fragment4,
                    R.id.feeds_fragment5,
                    R.id.feeds_fragment6,
                    R.id.feeds_fragment7,
                    R.id.feeds_fragment8};

            for (int i = 0; i < 8; i++) {
<<<<<<< HEAD

                feeds_fragments[i] = (CompanyEntity) getChildFragmentManager().findFragmentById(feeds_fragmentsid[i]);
                feeds_fragments[i].setOnCompanyEntityListener(new CompanyEntity.OnCompanyEntityListener() {
                    @Override
                    public void onCampantEnyityClick() {
                        goEquipmentNews();
                    }
                });
=======
                if (SDKVersion.isMoreThanAPI19()) {
                    feeds_fragments[i] = (CompanyEntity) getChildFragmentManager().findFragmentById(feeds_fragmentsid[i]);
                    feeds_fragments[i].setOnCompanyEntityListener(new CompanyEntity.OnCompanyEntityListener() {
                        @Override
                        public void onCampantEnyityClick() {
                            goEquipmentNews();
                        }
                    });
                } else {
                    feeds_fragments[i] = (CompanyEntity) getFragmentManager().findFragmentById(feeds_fragmentsid[i]);
                    feeds_fragments[i].setOnCompanyEntityListener(new CompanyEntity.OnCompanyEntityListener() {
                        @Override
                        public void onCampantEnyityClick() {
                            goEquipmentNews();
                        }
                    });
                }
>>>>>>> b3d7fe464b8a84778c4ed1cb0712e183044dd97a
            }

        }

        return view;
    }

    private void goEquipmentNews() {
        startActivity(new Intent(getActivity(), EquipmentNewsActivity.class));
    }

    private void goSell() {
        startActivity(new Intent(getActivity(), SellActivity.class));
    }

    private void goBuy() {
        startActivity(new Intent(getActivity(), BuyActivity.class));
    }

    private void goCamera() {

    }

    private void goLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void goFeedsSearch() {
        startActivity(new Intent(getActivity(), FeedsSearchActivity.class));
    }

    @Override
    public void onResume() {
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
                feeds_fragments[i].setCompanySloganColor(255, 0, 153, 204);
            } else {
                feeds_fragments[i].setCompanySloganColor(255, 204, 0, 0);
            }
            feeds_fragments[i].setCompanyNameText(companyname[i]);
            feeds_fragments[i].setCompanySloganText(companynameslogan[i]);
            feeds_fragments[i].setSmalllogDrawnable(bmpSmall[i]);
            feeds_fragments[i].setBiglogDrawnable(bmpBig[i]);
            feeds_fragments[i].setCompanyNameColor(255, 0, 0, 0);
        }


    }


}
