package com.example.z.thelastrow_client_gamedeal.fragment.inputmodule;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.z.thelastrow_client_gamedeal.R;

/**
 * Created by Administrator on 2017/1/5.
 */

public class GetAllLocalPictureView extends RelativeLayout implements Checkable {

    private ImageView imageView , imageCheck;
    private FrameLayout layout;
    private boolean mark;

    public GetAllLocalPictureView(Context context){
        this(context, null, 0);
    }

    public GetAllLocalPictureView(Context context, AttributeSet attributeSet) {
        this(context, null, 0);
    }

    public GetAllLocalPictureView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context,attributeSet,defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.getalllocalpicture_girdview_item, this);
        imageCheck = (ImageView) findViewById(R.id.getalllocalpicture_gridviewitem_check);
        imageView = (ImageView) findViewById(R.id.getalllocalpicture_gridviewitem_picture);
        layout = (FrameLayout) findViewById(R.id.getalllocalpicture_gridviewitem_layout);

    }



    @Override
    public void setChecked(boolean checked) {
        mark = checked;
        imageCheck.setVisibility( checked ? VISIBLE : GONE);
        layout.setSelected(checked);
    }

    @Override
    public boolean isChecked() {
        return mark;
    }

    @Override
    public void toggle() {
        setChecked(!mark);
    }

    public void setImageView(Bitmap bmp) {
        if (imageView != null){
            imageView.setImageBitmap(bmp);
        }
    }
}
