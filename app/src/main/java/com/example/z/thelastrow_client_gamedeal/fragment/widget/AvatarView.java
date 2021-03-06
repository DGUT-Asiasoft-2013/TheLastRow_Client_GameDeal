package com.example.z.thelastrow_client_gamedeal.fragment.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;


import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.Server;
import com.example.z.thelastrow_client_gamedeal.fragment.api.entity.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AvatarView extends View {
	
	public AvatarView(Context context) {
		super(context);
	}
	
	public AvatarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	Paint paint;
	float srcWidth, srcHeight;
	Handler mainThreadHandler = new Handler();

	//设置图像
	public void setBitmap(Bitmap bmp){
		if(bmp==null) {
//			paint = new Paint();
//			paint.setColor(Color.BLUE);
//			paint.setStyle(Paint.Style.STROKE);
//			paint.setStrokeWidth(10);
//		    paint.setPathEffect(new DashPathEffect(new float[]{5, 10, 15, 20}, 0));
//			paint.setAntiAlias(true);
			bmp=BitmapFactory.decodeResource(getResources(),R.drawable.me_head);
		}

			paint = new Paint();
			paint.setShader(new BitmapShader(bmp, TileMode.REPEAT, TileMode.REPEAT));
			paint.setAntiAlias(true);
			
			srcWidth = bmp.getWidth();
			srcHeight = bmp.getHeight();	

		invalidate();
	}
	
	public void load(User user){
		if (user==null||user.getAccount().contentEquals("")){
			mainThreadHandler.post(new Runnable() {
				public void run() {
					Bitmap bmp=BitmapFactory.decodeResource(getResources(),R.drawable.me_head);
					setBitmap(bmp);
				}
			});
		}else {
			load(user.getAvatar());
		}

	}
	
	public void load(String url){
			Request request = new Request.Builder()
					.url(Server.serverAddress+url)
					.get()
					.build();
			Server.getSharedClient().newCall(request).enqueue(new Callback() {
				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {

					byte[] bytes = arg1.body().bytes();
					final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
					mainThreadHandler.post(new Runnable() {
						public void run() {
							setBitmap(bmp);
						}
					});
				}

				@Override
				public void onFailure(Call arg0, IOException arg1) {

				}
			});
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(paint!=null){
			canvas.save();
			
			float dstWidth = getWidth();
			float dstHeight = getHeight();
			
			float scaleX = srcWidth / dstWidth;
			float scaleY = srcHeight / dstHeight;

			canvas.scale(1/scaleX, 1/scaleY);
			canvas.drawCircle(srcWidth/2, srcHeight/2, Math.min(srcWidth, srcHeight)/2, paint);
			canvas.restore();
		}
		
	}
}
