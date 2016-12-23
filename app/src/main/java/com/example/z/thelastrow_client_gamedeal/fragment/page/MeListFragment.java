package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.MainActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ListViewFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Z on 2016/12/21.
 */

public class MeListFragment extends Fragment {
    Handler handler;
    int[] drawableArray;
    String[] text1Array;
    String[] text2Array;

    TextView txt_goto_login,text_money,text_recharge;
    ListViewFragment listViewFragment =new ListViewFragment();

    User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_page_me,null);
        listViewFragment= (ListViewFragment) getChildFragmentManager().findFragmentById(R.id.page_me_frag);
        text_money= (TextView) view.findViewById(R.id.page_me_text_money);
        text_recharge= (TextView) view.findViewById(R.id.page_me_text_recharge);
        text_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recharge();
            }
        });
        txt_goto_login=(TextView) view.findViewById(R.id.text_login_or_register);
        txt_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToLogin();
            }
        });
        handler=new Handler();
        return view;
    }
    //充值
    private void Recharge() {
        final EditText et = new EditText(getActivity());
         String rechargeMoney;
        if(user==null){
            ToastAndDialog.setToastShort(getActivity(),"请先登录");
        }else {
            new AlertDialog.Builder(getActivity()).setTitle("充值")
                    //.setIcon(android.R.drawable.ic_dialog_info)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String input = et.getText().toString();
                            if (input.equals("")) {
                                ToastAndDialog.setToastShort(getActivity(), "不能为空！" + input);
                            } else {
                                text_recharge.setText(input);
                                MultipartBody requestBody = new MultipartBody.Builder()
                                        .addFormDataPart("text", input)
                                        .build();
                                Request request = Server.requestBuilderWithApi("/me/" + user.getId() + "/Recharge")
                                        .post(requestBody)
                                        .build();
                                Server.getSharedClient().newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                text_recharge.setText("充值");
                                                ToastAndDialog.setToastShort(getActivity(), "充值成功！");
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setListView();
        getAccountInformation();
    }

//    获取用户信息
    private void getAccountInformation() {
        Request request= Server.requestBuilderWithApi("me")
                .get()
                .build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                user=new ObjectMapper().readValue(response.body().string(),User.class);
                txt_goto_login.setText("Hello！ "+user.getName());
                text_money.setText("余额："+user.getMoney());

            }
        });
    }
    public void goToLogin() {
        if (txt_goto_login.getText().toString().contentEquals(getString(R.string.page_me_text_goto_login))) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else {
            txt_goto_login.setClickable(false);
        }
    }
    private void setListView() {
        drawableArray = new int[]{R.drawable.my_wallet, R.drawable.my_wallet,R.drawable.my_wallet,R.drawable.my_wallet};
        text1Array = new String[]{"我的订单","我的收藏", "消费记录","我的消息"};
        text2Array = new String[]{">", ">",">",">"};
        ListViewFragment.setArrays(drawableArray, text1Array, text2Array);
    }
}
