package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.CompanyEntity;

/**
 * Created by Z on 2016/12/21.
 */

public class FeedsListFragment extends Fragment {

    private View view;
    private TextView frag_seach;
    private ImageView frag_avatar, frag_camera;
    private CompanyEntity[] feeds_fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_page_feeds, null);

            frag_seach = (TextView) view.findViewById(R.id.frag_seach);
            frag_avatar = (ImageView) view.findViewById(R.id.frag_avatar);
            frag_camera = (ImageView) view.findViewById(R.id.frag_camera);

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
                feeds_fragments[i] = (CompanyEntity) getChildFragmentManager().findFragmentById(feeds_fragmentsid[i]);
            }

        }

        return view;
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





        for (int i=0; i < 8 ;i++) {
            if (i % 2 == 0) {
                feeds_fragments[i].setCompanySloganColor();
                feeds_fragments[i].setCompanyNameText(companyname[i]);
                feeds_fragments[i].setCompanySloganText(companynameslogan[i]);
            }else{
                feeds_fragments[i].setCompanySloganColor();
                feeds_fragments[i].setCompanyNameText(companyname[i]);
                feeds_fragments[i].setCompanySloganText(companynameslogan[i]);
            }
        }


    }
}
