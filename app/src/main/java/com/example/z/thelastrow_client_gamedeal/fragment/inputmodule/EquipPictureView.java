package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/3.
 */

public class EquipPictureView extends View {

    public EquipPictureView(Context context){
        super(context);
    }

    public EquipPictureView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    public EquipPictureView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context,attributeSet,defStyleAttr);
    }

    Paint paint;
    float srcWidth,srcHeight;

    public void setBitmap(Bitmap bmp) {
        if (bmp == null)  {
            paint = new Paint();
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(1);
            //5长度直线，10长度空白，15长度支线，20长度空白
            paint.setPathEffect(new DashPathEffect(new float[]{5, 10, 15, 20}, 0));
            //抗锯齿
            paint.setAntiAlias(true);
        }else {
            paint = new Paint();
            paint.setShader(new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            paint.setAntiAlias(true);

            srcHeight = bmp.getHeight();
            srcWidth = bmp.getWidth();
        }

        invalidate();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (paint != null) {

            canvas.save();

            float dstWidth = getWidth();
            float dstHeight = getHeight();

            //计算缩放比例
            float scaleX = srcWidth / dstWidth;
            float scaleY = srcHeight / dstHeight;

            //设置缩放
            canvas.scale(1/scaleX,1/scaleY);

            //画圆，第1，2个参数设置圆心，第三个为半径，第四个为采用的画笔
            canvas.drawCircle(srcWidth/2,srcHeight/2,Math.min(srcWidth,srcHeight),paint);



            canvas.restore();
        }
    }


}
