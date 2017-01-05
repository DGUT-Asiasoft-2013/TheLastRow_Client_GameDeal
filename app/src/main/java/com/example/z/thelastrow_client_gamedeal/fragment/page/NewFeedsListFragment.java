package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by z on 2017/1/3.
 */

public class NewFeedsListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_feeds_new,null);
        return view;
    }
}
