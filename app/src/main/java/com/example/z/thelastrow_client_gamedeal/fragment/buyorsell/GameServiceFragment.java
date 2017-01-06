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
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.GameService;
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

public class GameServiceFragment extends Fragment {

    private View view;
    private ListView list;
    private List<GameService> gameServices;
    private String gamename;
    private FailurePanelFragment failurePanelFragment;
    private LoadingPanelFragment loadingPanelFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_gameservice, null);

            if (SDKVersion.isMoreThanAPI19()) {
                failurePanelFragment = (FailurePanelFragment) getChildFragmentManager().findFragmentById(R.id.gameservice_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getChildFragmentManager().findFragmentById(R.id.gameservice_loadingpanel);
            } else {
                failurePanelFragment = (FailurePanelFragment) getFragmentManager().findFragmentById(R.id.gameservice_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getFragmentManager().findFragmentById(R.id.gameservice_loadingpanel);
            }

            failurePanelFragment.setOnFailureButtonClickListener(new FailurePanelFragment.OnFailureButtonClickListener() {
                @Override
                public void click() {
                    reload();
                }
            });

            list = (ListView)view.findViewById(R.id.gameservice_listview);
            list.setAdapter(gameserviceslist);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onGameServiceClick(view);
                }
            });

            view.findViewById(R.id.gameservice_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

        }
        return view;
    }

    public interface OnGameServiceItemClick{
        void onItemClick(View view);
    }

    private OnGameServiceItemClick onGameServiceItemClick;

    public void setOnGameServiceItemClick(OnGameServiceItemClick onGameServiceItemClick){
        this.onGameServiceItemClick = onGameServiceItemClick;
    }

    private void onGameServiceClick(View view) {
        if (onGameServiceItemClick != null) {
            onGameServiceItemClick.onItemClick(view);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null) {
            gamename = getArguments().getString("gamename");
        }

        ((TextView)view.findViewById(R.id.gameservice_text)).setText(gamename);

        gameServices = new ArrayList<>();
        reload();
    }

    private void reload() {
        failurePanelFragment.setMiss(true);
        loadingPanelFragment.setMiss(false);

        Request request = Server.getAllGameService(gamename).get().build();


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
                        GameServiceFragment.this.loadingPanelFragment.setMiss(true);
                        GameServiceFragment.this.gameServices.clear();
                        GameServiceFragment.this.failurePanelFragment.setTextText(R.string.failurelink_internet);
                        GameServiceFragment.this.failurePanelFragment.setMiss(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final List<GameService> getgamesservice = new ObjectMapper().readValue(response.body().string(), new TypeReference<List<GameService>>() {});

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GameServiceFragment.this.loadingPanelFragment.setMiss(true);
                            GameServiceFragment.this.gameServices = getgamesservice;

                            gameserviceslist.notifyDataSetChanged();
                            GameServiceFragment.this.failurePanelFragment.setMiss(true);
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
                            GameServiceFragment.this.loadingPanelFragment.setMiss(true);
                            GameServiceFragment.this.gameServices.clear();
                            GameServiceFragment.this.failurePanelFragment.setTextText(R.string.failureLink_response);
                            GameServiceFragment.this.failurePanelFragment.setMiss(false);
                        }
                    });
                }
            }
        });
    }

//    private void showAlertDialog(String message) {
//
//        new AlertDialog.Builder(getActivity())
//                .setMessage(message).setNegativeButton("知道",null).show();
//    }

    BaseAdapter gameserviceslist = new BaseAdapter() {
        @Override
        public int getCount() {
            return (gameServices == null ? 0 : gameServices.size());
        }

        @Override
        public Object getItem(int position) {
            return gameServices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_gameservice_listitem, null);
            }

            TextView gameservicename = (TextView) convertView.findViewById(R.id.gameservice_listitem_text);

            GameService gameService = gameServices.get(position);

            gameservicename.setText(gameService.getGameservicename());

            return  convertView;
        }
    };
}
