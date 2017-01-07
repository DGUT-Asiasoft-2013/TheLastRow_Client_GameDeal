package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by z on 2017/1/3.
 */

public class MainBarFragment extends Fragment {
    ImageView search,add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_main_action_bar,null);
        search=(ImageView)view.findViewById(R.id.main_bar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClick();
            }
        });
        add=(ImageView)view.findViewById(R.id.main_bar_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClick();
            }
        });
        return view;
    }

    private void addClick() {
        if (onAddListener != null) {
            onAddListener.add();
        }
    }

    public interface OnAddListener{
        void add();
    }

    private OnAddListener onAddListener;

    public void setOnAddListener(OnAddListener onAddListener) {
        this.onAddListener = onAddListener;
    }

    private void searchClick() {
        if (onSearchListener != null) {
            onSearchListener.onSearch();
        }
    }

    public interface OnSearchListener{
        void onSearch();
    }

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }
}
