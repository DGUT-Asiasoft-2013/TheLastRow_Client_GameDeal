package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.FeedsSearchActivity;
import com.example.z.thelastrow_client_gamedeal.GoodActivity;
import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.SellActivity;
import com.example.z.thelastrow_client_gamedeal.fragment.api.ImageDownload;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.FailurePanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.LoadingPanelFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.MainBarFragment;
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
 * Created by Z on 2016/12/21.
 */

public class NoteListFragment extends Fragment {
    Handler handler = new Handler();
    private View view;
    private TextView notes_sell, notes_buy;
    private ListView notes_list;

    private MainBarFragment mainBarFragment;

    private List<Equipment> equipList;
    private int page;

    private FailurePanelFragment failurePanelFragment;
    private LoadingPanelFragment loadingPanelFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_page_notes, null);

            if (SDKVersion.isMoreThanAPI19()) {
                failurePanelFragment = (FailurePanelFragment) getChildFragmentManager().findFragmentById(R.id.notes_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getChildFragmentManager().findFragmentById(R.id.notes_loadingpanel);
            } else {
                failurePanelFragment = (FailurePanelFragment) getFragmentManager().findFragmentById(R.id.notes_failurepanel);
                loadingPanelFragment = (LoadingPanelFragment) getFragmentManager().findFragmentById(R.id.notes_loadingpanel);
            }

            failurePanelFragment.setOnFailureButtonClickListener(new FailurePanelFragment.OnFailureButtonClickListener() {
                @Override
                public void click() {
                    reload();
                }
            });

            equipList = new ArrayList<>();
            notes_list = (ListView) view.findViewById(R.id.notes_list);
            notes_list.setAdapter(baseAdapter);
            notes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listClick(position);
                }
            });

            if (SDKVersion.isMoreThanAPI19()) {
                mainBarFragment = (MainBarFragment) getChildFragmentManager().findFragmentById(R.id.frag_main_bar2);
            } else {
                mainBarFragment = (MainBarFragment) getFragmentManager().findFragmentById(R.id.frag_main_bar2);
            }
            mainBarFragment.setOnAddListener(new MainBarFragment.OnAddListener() {
                @Override
                public void add() {
                    if (Server.getUser() == null) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), SellActivity.class));
                    }
                }
            });
            mainBarFragment.setOnSearchListener(new MainBarFragment.OnSearchListener() {
                @Override
                public void onSearch() {
                    startActivity(new Intent(getActivity(), FeedsSearchActivity.class));
                }
            });
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {
        failurePanelFragment.setMiss(true);
        loadingPanelFragment.setMiss(false);

        Request request = Server.getAllEquipment().get().build();

        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                if (getActivity() == null) {
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       NoteListFragment.this.loadingPanelFragment.setMiss(true);
                       NoteListFragment.this.equipList.clear();
                       NoteListFragment.this.failurePanelFragment.setTextText(R.string.failurelink_internet);
                       NoteListFragment.this.failurePanelFragment.setMiss(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final List<Equipment> data = new ObjectMapper()
                            .readValue(response.body().string(), new TypeReference<List<Equipment>>() {
                            });

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NoteListFragment.this.loadingPanelFragment.setMiss(true);
                            NoteListFragment.this.equipList = data;

                            NoteListFragment.this.failurePanelFragment.setMiss(true);
//                            NoteListFragment.this.equipList = data.getContent();
//                            NoteListFragment.this.page = data.getNumber();

                        }
                    });

                } catch (final Exception ex) {

                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            new AlertDialog.Builder(getActivity())
//                                    .setMessage(e.getMessage()).setNegativeButton("知道",null).show();
                            NoteListFragment.this.loadingPanelFragment.setMiss(true);
                            NoteListFragment.this.equipList.clear();
                            NoteListFragment.this.failurePanelFragment.setTextText(R.string.failureLink_response);
                            NoteListFragment.this.failurePanelFragment.setMiss(false);
                        }
                    });
                }
            }
        });
        baseAdapter.notifyDataSetChanged();
    }

    public void listClick(int position) {
        Equipment good = equipList.get(position);
        Intent intent = new Intent(getActivity(), GoodActivity.class);
        intent.putExtra("good", good);
        startActivity(intent);
    }

    BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (equipList == null ? 0 : equipList.size());
        }

        @Override
        public Object getItem(int position) {
            return equipList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_goods_item, null);
                TextView text_type = (TextView) convertView.findViewById(R.id.list_good_text_type);
                TextView notes_listitem_thingsname = (TextView) convertView.findViewById(R.id.list_good_text_title);
                TextView notes_listitem_thingsvalue = (TextView) convertView.findViewById(R.id.list_good_text_price);
                TextView text_equip = (TextView) convertView.findViewById(R.id.list_good_text_equip);
                final ImageView notes_listitem_thingspicture = (ImageView) convertView.findViewById(R.id.list_good_avatar);

                final Equipment equip = equipList.get(position);
                if (equip != null) {
//                    final Bitmap bmp=new GoodService().getBmp(equip.getAvatar_img1());
//                            notes_listitem_thingspicture.setImageBitmap(bmp);
                    notes_listitem_thingsname.setText(""
                            + "[" + equip.getGameservice().getGame().getGamename() + "]"
                            + "[" + equip.getGameservice().getGameservicename() + "]"
                    );
                    text_equip.setText(equip.getEquipname());
                    notes_listitem_thingsvalue.setText("" + equip.getEquipvalue());
                }

                if (equip != null && equip.getEquippicture() != null) {
                    new ImageDownload(parent.getContext()).download(notes_listitem_thingspicture, equip.getEquippicture()[0],
                            new ImageDownload.ImageDownloadBack() {
                                @Override
                                public void onBack(ImageView imageView, Bitmap bmp) {
                                    imageView.setImageBitmap(bmp);
                                }
                            } , 0);
                }else {
                    notes_listitem_thingspicture.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cancel_50));
                }


//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (equip.getEquippicture() == null) {
//                            return;
//                        }
//
//                        Server.getSharedClient().newCall(
//                                new Request.Builder().url(Server.serverAddress + equip.getEquippicture()[0]).get().build()
//                        ).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(Call call, final IOException e) {
//
//                            }
//
//                            @Override
//                            public void onResponse(Call call, Response response) throws IOException {
//                                try {
//                                    byte[] bytes = response.body().bytes();
//                                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            notes_listitem_thingspicture.setImageBitmap(bmp);
//                                        }
//                                    });
//                                } catch (final Exception e) {
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//                        });
//
//                    }
//                });
            }
            return convertView;
        }
    };

}
