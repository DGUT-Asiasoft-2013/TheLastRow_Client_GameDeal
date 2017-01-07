package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Game;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.FailurePanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.LoadingPanelFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/3.
 */

public class GameFragment extends Fragment {

    private View view;
    private ListView list;
    private List<Game> games;
    private FailurePanelFragment failurePanelFragment;
    private LoadingPanelFragment loadingPanelFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_game, null);
            list = (ListView)view.findViewById(R.id.game_listview);


            if (SDKVersion.isMoreThanAPI19()) {
                failurePanelFragment = (FailurePanelFragment) getChildFragmentManager().findFragmentById(R.id.game_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getChildFragmentManager().findFragmentById(R.id.game_loadingpanel);
            } else {
                failurePanelFragment = (FailurePanelFragment) getFragmentManager().findFragmentById(R.id.game_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getFragmentManager().findFragmentById(R.id.game_loadingpanel);
            }

            failurePanelFragment.setOnFailureButtonClickListener(new FailurePanelFragment.OnFailureButtonClickListener() {
                @Override
                public void click() {
                    reload();
                }
            });

            list.setAdapter(gameslist);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ongameItemClick(view);
                }
            });

            view.findViewById(R.id.game_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

        }
        return view;
    }

    public interface OnGameItemClick{
        void onItemClick(View view);
    }

    private OnGameItemClick onGameItemClick;

    public void setOnGameItemClick(OnGameItemClick onGameItemClick){
        this.onGameItemClick = onGameItemClick;
    }

    private void ongameItemClick(View view) {
        if (onGameItemClick != null) {
            onGameItemClick.onItemClick(view);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        games = new ArrayList<>();
        reload();
    }

    private void reload() {
        failurePanelFragment.setMiss(true);
        loadingPanelFragment.setMiss(false);

        Request request = Server.getAllGame().get().build();


//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("请稍等");
//        //点屏幕和物理返回键退出进度对话框
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        //物理返回键可以退出进度框，点屏幕无效
//        progressDialog.setCanceledOnTouchOutside(false);


        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (getActivity() == null) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GameFragment.this.loadingPanelFragment.setMiss(true);
                        GameFragment.this.games.clear();
                        GameFragment.this.failurePanelFragment.setTextText(R.string.failurelink_internet);
                        GameFragment.this.failurePanelFragment.setMiss(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final List<Game> getgames = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<Game>>() {});

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            GameFragment.this.loadingPanelFragment.setMiss(true);
                            GameFragment.this.games = getgames;

                            gameslist.notifyDataSetChanged();
                            GameFragment.this.failurePanelFragment.setMiss(true);
                        }
                    });

                } catch (Exception ex) {
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            new AlertDialog.Builder(getActivity())
//                                    .setMessage(e.getMessage()).setNegativeButton("知道",null).show();
                            GameFragment.this.loadingPanelFragment.setMiss(true);
                            GameFragment.this.games.clear();
                            GameFragment.this.failurePanelFragment.setTextText(R.string.failureLink_response);
                            GameFragment.this.failurePanelFragment.setMiss(false);
                        }
                    });
                }
            }
        });
    }


    BaseAdapter gameslist = new BaseAdapter() {
        @Override
        public int getCount() {
            return (games == null ? 0 : games.size());
        }

        @Override
        public Object getItem(int position) {
            return games.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_game_listitem, null);
            }

            TextView gamename = (TextView) convertView.findViewById(R.id.game_listitem_text);

            Game game = games.get(position);

            gamename.setText(game.getGamename());

            return  convertView;
        }
    };
}
