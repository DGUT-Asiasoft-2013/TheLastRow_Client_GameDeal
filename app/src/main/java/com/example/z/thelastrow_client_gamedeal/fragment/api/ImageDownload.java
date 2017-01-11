package com.example.z.thelastrow_client_gamedeal.fragment.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.z.thelastrow_client_gamedeal.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/9.
 */

public class ImageDownload {

    private Context context;

    public ImageDownload(Context context) {
        this.context = context;
    }

    public void download(final ImageView imageView, String path, final ImageDownloadBack imageDownloadBack , final long delaytime) {

        Server.getSharedClient().newCall(
                new Request.Builder().url(Server.serverAddress + path).get().build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                imageView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (imageDownloadBack != null) {
                            imageDownloadBack.onBack(imageView, BitmapFactory.decodeResource(context.getResources(), R.drawable.cancel_50));
                        }
                    }
                } , delaytime);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                byte[] bytes = response.body().bytes();
                final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                try {
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (bmp != null && imageDownloadBack != null) {
                                imageDownloadBack.onBack(imageView, bmp);
                            }
                        }
                    } , delaytime);
                } catch (Exception e) {
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (imageDownloadBack != null) {
                                imageDownloadBack.onBack(imageView, BitmapFactory.decodeResource(context.getResources(), R.drawable.cancel_50));
                            }
                        }
                    } ,delaytime);
                }
            }
        });
    }

    public interface ImageDownloadBack {
        void onBack(ImageView imageView, Bitmap bmp);
    }

}
