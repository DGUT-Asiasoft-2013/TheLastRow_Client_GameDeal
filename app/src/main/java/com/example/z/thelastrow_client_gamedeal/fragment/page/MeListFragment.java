package com.example.z.thelastrow_client_gamedeal.fragment.page;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.z.thelastrow_client_gamedeal.GoodActivity;
import com.example.z.thelastrow_client_gamedeal.LoginActivity;
import com.example.z.thelastrow_client_gamedeal.MainActivity;
import com.example.z.thelastrow_client_gamedeal.PersonInfoActivity;
import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.TestActivity;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.AvatarView;
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
    static Handler handler=new Handler();
    int[] drawableArray;
    String[] text1Array;
    String[] text2Array;
    View view;

    TextView txt_goto_login,text_money,text_recharge,text_sign_out;
    ListViewFragment listViewFragment =new ListViewFragment();
    AvatarView avatarView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_page_me,null);
        text_sign_out=(TextView)view.findViewById(R.id.page_me_text_sign_out);
        text_sign_out.setClickable(true);
        text_sign_out.setFocusable(true);
        text_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        avatarView=(AvatarView)view.findViewById(R.id.page_me_avatar);
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

        setListView();

        return view;
    }

    private void signOut() {
        ToastAndDialog.setDialog(getActivity(), "退出当前账号？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request=Server.requestBuilderWithApi("exit")
                        .get()
                        .build();
                Server.getSharedClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Server.setUser(null);
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    //充值
    private void Recharge() {
        final EditText et = new EditText(getActivity());
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
         String rechargeMoney;
        if(Server.getUser()==null){
            goToLogin();
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
                                Request request = Server.requestBuilderWithApi("/me/" + Server.getUser().getId() + "/Recharge")
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
                                                getAccountInformation();
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
        getAccountInformation();

    }

    //    获取用户信息
    private void getAccountInformation() {
        new Server().setUser();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        avatarView.load(Server.getUser());
        if (Server.getUser() != null) {
            txt_goto_login.setText("Hello！ " + Server.getUser().getName());
            text_money.setText("余额：" + Server.getUser().getMoney());
            text_sign_out.setVisibility(View.VISIBLE);
        } else {
            txt_goto_login.setText("登录/注册");
            text_money.setText("余额：0");
        }
    }

    public void goToLogin() {
        Intent intent = null;
        if (txt_goto_login.getText().toString().contentEquals("登录/注册")){
            intent=new Intent(getActivity(), LoginActivity.class);
        }else {
            intent=new Intent(getActivity(), PersonInfoActivity.class);
        }
        startActivity(intent);
    }
    private void setListView() {
        drawableArray = new int[]{R.drawable.my_wallet, R.drawable.my_collection,R.drawable.my_wallet,R.drawable.my_message};
        text1Array = new String[]{"我的订单","我的收藏", "消费记录","我的消息"};
        text2Array = new String[]{">", ">",">",">"};
        listViewFragment.setArrays(drawableArray, text1Array, text2Array,Server.getUser());
    }

}
