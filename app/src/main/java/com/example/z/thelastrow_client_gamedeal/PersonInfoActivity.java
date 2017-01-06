package com.example.z.thelastrow_client_gamedeal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ActionBarFragment;
import com.example.z.thelastrow_client_gamedeal.fragment.widget.ToastAndDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by z on 2017/1/4.
 */

public class PersonInfoActivity extends Activity {
    Handler handler=new Handler();
    User user;
    ActionBarFragment actionBarFragment;
    TextView account, name, email, chang_avatar;
    ImageView avatar;

    byte[] pngData;
    final int REQUESTCODE_CAMERA = 1;
    final int REQUESTCODE_ALBUM = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        actionBarFragment=(ActionBarFragment)getFragmentManager().findFragmentById(R.id.info_action_bar);
        actionBarFragment.setTitle("个人信息");
        actionBarFragment.setText("修改");
        actionBarFragment.setField(new ActionBarFragment.SetTextClick() {
            @Override
            public void setTextClickLisener() {
                new AlertDialog.Builder(PersonInfoActivity.this)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new changeThread()).start();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .setMessage("确定修改？")
                        .show();

            }
        });
        account=(TextView)findViewById(R.id.info_account);
        name=(TextView)findViewById(R.id.info_name);
        email=(TextView)findViewById(R.id.info_email);
        chang_avatar=(TextView)findViewById(R.id.info_text_avatar);
        chang_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClicked();
            }
        });
        avatar=(ImageView)findViewById(R.id.info_avatar);

        new Thread(new imgThread()).start();
        user= Server.getUser();
        account.setText(user.getAccount());
        name.setText(user.getName());
        email.setText(user.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public class changeThread extends Thread {
        @Override
        public void run() {
            super.run();
            MultipartBody.Builder requestBody=new MultipartBody.Builder()
                    .addFormDataPart("email",email.getText().toString())
                    .addFormDataPart("name",name.getText().toString());
            if (pngData!=null){
                requestBody
                        .addFormDataPart(
                                "avatar",
                                "avatar",
                                RequestBody.create(MediaType.parse("image/png"),pngData));
            }

            Request request=Server.requestBuilderWithApi("changeInfo")
                    .post(requestBody.build())
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    ToastAndDialog.setDialog(PersonInfoActivity.this,e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Boolean status=new ObjectMapper().readValue(response.body().string(),Boolean.class);
                    if (status){
                        new Server().setUser();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastAndDialog.setDialog(PersonInfoActivity.this,"修改成功");
                            }
                        });
                    }
                }
            });
        }



    }


    public class imgThread extends Thread{
        @Override
        public void run() {
            super.run();
            Request request = new Request.Builder()
                    .url(Server.serverAddress+Server.getUser().getAvatar())
                    .get()
                    .build();
            Server.getSharedClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call arg0, Response arg1) throws IOException {

                    byte[] bytes = arg1.body().bytes();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    handler.post(new Runnable() {
                        public void run() {
                            avatar.setImageBitmap(bmp);
                        }
                    });
                }
                @Override
                public void onFailure(Call arg0, IOException arg1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            avatar.setImageResource(R.drawable.me_head);
                        }
                    });
                }
            });
        }
    }


    void onImageViewClicked(){
        String[] items = {
                "拍照","相册"
        };

        new AlertDialog.Builder(PersonInfoActivity.this)
                .setTitle("选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;

                            case 1:
                                pickFromAlbum();
                                break;

                            default:
                                break;
                        }
                    }
                })
                .setNegativeButton("确定", null)
                .show();
    }


    void takePhoto(){
        Intent itnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(itnt, REQUESTCODE_CAMERA);
    }

    void pickFromAlbum(){
        Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
        itnt.setType("image/*");
        startActivityForResult(itnt, REQUESTCODE_ALBUM);
    }

    void saveBitmap(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        pngData = baos.toByteArray();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) return;

        if(requestCode == REQUESTCODE_CAMERA){

            Bitmap bmp = (Bitmap)data.getExtras().get("data");
            saveBitmap(bmp);
            avatar.setImageBitmap(bmp);
        }else if(requestCode == REQUESTCODE_ALBUM){

            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                saveBitmap(bmp);

                avatar.setImageBitmap(bmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
