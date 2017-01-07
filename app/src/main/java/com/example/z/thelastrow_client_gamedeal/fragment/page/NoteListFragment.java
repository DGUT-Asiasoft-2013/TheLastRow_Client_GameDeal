package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.Intent;
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
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.Equipment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.MainBarFragment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/21.
 */

public class NoteListFragment extends Fragment {
    Handler handler=new Handler();
    private View view;
    private TextView notes_sell,notes_buy;
    private ListView notes_list;

    private MainBarFragment mainBarFragment;

    private List<Equipment> equipList;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_page_notes, null);
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
                    startActivity(new Intent(getActivity() , FeedsSearchActivity.class));
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
        Request request = Server.getAllEquipment().get().build();

        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    final List<Equipment> data = new ObjectMapper()
                            .readValue(response.body().string(), new TypeReference<List<Equipment>>() {});

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NoteListFragment.this.equipList = data;
//                            NoteListFragment.this.equipList = data.getContent();
//                            NoteListFragment.this.page = data.getNumber();

                            baseAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (final Exception ex) {

                }
            }
        });

    }

    public void   listClick(int position){
        Equipment good=equipList.get(position);
        Intent intent=new Intent(getActivity(), GoodActivity.class);
        intent.putExtra("good",good);
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
                TextView text_type=(TextView)convertView.findViewById(R.id.list_good_text_type);
                final TextView notes_listitem_thingsname = (TextView) convertView.findViewById(R.id.list_good_text_title);
                final TextView notes_listitem_thingsvalue = (TextView) convertView.findViewById(R.id.list_good_text_price);
                final TextView text_equip=(TextView)convertView.findViewById(R.id.list_good_text_equip);
                final ImageView notes_listitem_thingspicture = (ImageView) convertView.findViewById(R.id.list_good_avatar);

                if (equipList.get(position)!=null){
                    final Equipment equip = equipList.get(position);
//                    final Bitmap bmp=new GoodService().getBmp(equip.getAvatar_img1());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            notes_listitem_thingspicture.setImageBitmap(bmp);
                            notes_listitem_thingsname.setText(""
                                    +"["+equip.getGameservice().getGame().getGamename()+"]"
                                    +"["+equip.getGameservice().getGameservicename()+"]"
                            );
                            text_equip.setText(equip.getEquipname());
                            notes_listitem_thingsvalue.setText("" +equip.getEquipvalue());
                        }
                    });
                }
            }
            return convertView;
        }
    };

}
