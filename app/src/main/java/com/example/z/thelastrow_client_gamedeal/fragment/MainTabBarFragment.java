package com.example.z.thelastrow_client_gamedeal.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Z on 2016/12/21.
 */

public class MainTabBarFragment extends Fragment {
    View view;
    View btnNew, tabFeeds, tabNotes, tabSearch, tabMe;
    View[] tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_tab_bar, container);
        tabFeeds = view.findViewById(R.id.tab_bar_img_feeds);
        tabNotes = view.findViewById(R.id.tab_bar_img_notes);
        tabSearch = view.findViewById(R.id.tab_bar_img_search);
        tabMe = view.findViewById(R.id.tab_bar_img_me);

        tabs = new View[]{
                tabFeeds, tabNotes, tabSearch, tabMe
        };

        for (final View tab : tabs) {
            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTabClicked(tab);
                }
            });
        }
        return view;
    }

    public static interface OnTabSelectedListener {
        void onTabSelected(int index);
    }

    OnTabSelectedListener onTabSelectedListener;

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public void setSelectedItem(int index) {
        if (index >= 0 && index < tabs.length) {
            onTabClicked(tabs[index]);
        }
    }

    void onTabClicked(View tab) {
        int selectedIndex = -1;
        for (int i = 0; i < tabs.length; i++) {
            //otherTab.setSelected(otherTab == tab);
            if (tabs[i] == tab) {
                tabs[i].setSelected(true);
                selectedIndex = i;
                //Toast.makeText(getActivity(),"第"+i+"被点击了",Toast.LENGTH_SHORT).show();
            } else {
                tabs[i].setSelected(false);
            }
        }
        if (onTabSelectedListener != null && selectedIndex >= 0) {
            onTabSelectedListener.onTabSelected(selectedIndex);
        }
    }
}
