package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2016/12/26.
 */

public class PictureThingsFragment extends Fragment {

    final int REQUESTCODE_CAMERA = 1;
    final int REQUESTCODE_ALBUNM = 2;

    private View view;
    private ImageView image;
    private TextView tips;
    private Uri imgUri;

    private byte[] data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_things_picture, null);

            tips = (TextView) view.findViewById(R.id.thing_input_picturetips);

            image = (ImageView) view.findViewById(R.id.thing_input_picture);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPictureChoose();
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onPictureChoose() {
        String[] choose = new String[]{"拍照", "相册"};

        new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle("你可以")
                .setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;

                            case 1:
                                pickFromAlbunm();
                                break;

                            default:
                                break;
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void pickFromAlbunm() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUESTCODE_ALBUNM);
    }

    private void takePhoto() {

        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

        String fname = "equip" + System.currentTimeMillis() + ".jpg";

        imgUri = Uri.parse("file://" + dir + "/" + fname);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        startActivityForResult(intent, REQUESTCODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        if (requestCode == REQUESTCODE_CAMERA && resultCode == Activity.RESULT_OK) {

            //共享到公共图片库
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imgUri);

            //发布广播
            getActivity().sendBroadcast(intent);
            scaleImage();

        } else if (requestCode == REQUESTCODE_ALBUNM && resultCode == Activity.RESULT_OK) {
            imgUri = convertUri(data.getData());
            scaleImage();
        }
    }

    //计算路径名
    private Uri convertUri(Uri data) {
        if (data.toString().substring(0, 7).equals("content")) {
            String[] colName = { MediaStore.MediaColumns.DATA};
            Cursor cursor = getActivity().getContentResolver().query(data, colName, null, null, null);
            cursor.moveToFirst();
            data = Uri.parse("file://" + cursor.getString(0));
            cursor.close();
        }
        return data;
    }

    private void scaleImage() {
        tips.setVisibility(View.GONE);
        float iw , ih , vw , vh ;

        BitmapFactory.Options options = new BitmapFactory.Options();

        //只读取图片边框大小
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imgUri.getPath(), options);

        iw = options.outWidth;
        ih = options.outHeight;
        vh = image.getHeight();
        vw = image.getWidth();

        //计算缩放比例
        int scaleFactor = (int)Math.min(iw / vw, ih / vh);

        //取消只读边框，设置缩放比例
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap bmp = BitmapFactory.decodeFile(imgUri.getPath(),options);
        saveBitmap(BitmapFactory.decodeFile(imgUri.getPath()));
        image.setImageBitmap(bmp);
    }

    private void saveBitmap(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        data = baos.toByteArray();
    }

    public byte[] getData() {
        return data;
    }

}
