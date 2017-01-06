package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.GetAllLocalPictureView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class GetAllLocalPictureActivity extends Activity {

    final static int RESULT_CODE_PICTURECHOOSE = 0x606;
    private TextView checkText;
    private GridView gridView;
    private List<Uri> imagePathList;
    private List<String> positionList;
    private Button button;
//    private SimpleAdapter simpleAdapter;
//    private List<Map<String,Object>> pictureMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getalllocalpicyure);

        gridView = (GridView) findViewById(R.id.getalllocalpicture_gridview);
        checkText = (TextView) findViewById(R.id.getalllocalpicture_text);
        positionList = new ArrayList<>();

        button = (Button) findViewById(R.id.getalllocalpicture_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("picturechoose", (ArrayList<String>) positionList);
                setResult(RESULT_CODE_PICTURECHOOSE,intent);
                finish();
            }
        });

        //第一个参数为context值，第二个为map对象的键值对，第三个为你想要的布局
        //第四个为键的名字，第五个为对应键位置的布局控件id
//        simpleAdapter = new SimpleAdapter(this , pictureMap , R.layout.getalllocalpicture_girdview_item,
//                new String[]{"picture"} , new int[]{R.id.getalllocalpicture_gridviewitem_picture});


//        gridView.setAdapter(simpleAdapter);
        gridView.setAdapter(baseAdapter);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (positionList.contains(view)) {
//                    positionList.remove(positionList.indexOf(view));
//                } else {
//                    positionList.add(view);
//                }
//
//                setItemSelect(position);
//
//            }
//
//        });
    }

//    private void setItemSelect(int position) {
//        for (View view : positionList) {
//            if (view.isSelected()) {
//                Log.d("1", "" + 1);
//                continue;
//            } else {
//                Log.d("2", "" + 2);
//                view.setSelected(true);
//            }
//        }
//    }


    @Override
    protected void onResume() {
        super.onResume();

        button.setText("取消");
        findPicture();
    }

    private void findPicture() {

        //第一个参数为想获取data的url地址，第二个参数为想获取data的列属性类型，如：名字，大小等
        //第三个为筛选的判定string，如size >= ?，第四个为第三个参数的？值
        //第五个为升降序
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA}, null, null,
                MediaStore.Images.Media.DATE_ADDED + " DESC");

//        pictureMap = new ArrayList<Map<String, Object>>();
        imagePathList = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            int i = 0;
            do {
                imagePathList.add(Uri.parse("file://" + cursor.getString(0)));
                i++;
            } while (cursor.moveToNext() && i < cursor.getCount());
        }
        baseAdapter.notifyDataSetChanged();
        cursor.close();
    }


    BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (imagePathList == null ? 0 : imagePathList.size());
        }

        @Override
        public Object getItem(int position) {
            return imagePathList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final GetAllLocalPictureView item;
            if (convertView == null) {
                item = new GetAllLocalPictureView(parent.getContext());
                item.setLayoutParams(new ViewGroup.LayoutParams(110, 110));
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.getalllocalpicture_girdview_item, null);
//                convertView.setLayoutParams(new ViewGroup.LayoutParams(110, 110));
            } else {
                item = (GetAllLocalPictureView) convertView;
            }
            final String uri = imagePathList.get(position).getPath();

            item.setImageView(BitmapFactory.decodeFile(uri));
//            item.setImageView(imagePathList.get(position).getPath());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetAllLocalPictureView view = (GetAllLocalPictureView) v;
                    if (view.isChecked()) {
                        view.setChecked(false);
                        positionList.remove(positionList.indexOf(uri));
                    } else {
                        if (positionList.size() < 6) {
                            view.setChecked(true);
                            positionList.add(uri);
                        } else {
                            return;
                        }
                    }
                    checkText.setText("已选择（" + positionList.size() + "/6）张");
                    if (positionList.size() == 0) {
                        button.setText("取消");
                    } else {
                        button.setText("完成");
                    }
//                    Log.d("size", "" + positionList.size());
                }
            });

//            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.getalllocalpicture_gridviewitem_check);
//            checkBox.setClickable(false);
//            checkBox.setChecked(true);
//            final ImageView imageView = (ImageView) convertView.findViewById(R.id.getalllocalpicture_gridviewitem_picture);
//
//            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePathList.get(position).getPath()));
//            scaleImage(imageView , imagePathList.get(position));

//            if (checkBox.isChecked()) {
//                convertView.setSelected(true);
//            } else {
//                convertView.setSelected(false);
//            }
//            RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.getalllocalpicture_gridviewitem_layout);
//            positionList.add(relativeLayout);
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v.isSelected()) {
//                        v.setSelected(false);
//                        checkBox.setVisibility(View.GONE);
//                        positionList.remove(positionList.indexOf(imageView));
//                    } else {
//                        v.setSelected(true);
//                        checkBox.setVisibility(View.VISIBLE);
//                        positionList.add(imageView);
//                    }
//                    Log.d("positionlist", "" + positionList.size());
//                }
//            });
            return item;
        }
    };


//    private void scaleImage(ImageView imageView , Uri uri) {
//        float iw , ih , vw , vh ;
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//
//        //只读取图片边框大小
//        options.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(uri.getPath(), options);
//
//        iw = options.outWidth;
//        ih = options.outHeight;
//        vh = imageView.getHeight();
//        vw = imageView.getWidth();
//
//        //计算缩放比例
//        int scaleFactor = (int)Math.min(iw / vw, ih / vh);
//
//        //取消只读边框，设置缩放比例
//        options.inJustDecodeBounds = false;
//        options.inSampleSize = 1 /scaleFactor;
//
//        Bitmap bmp = BitmapFactory.decodeFile(uri.getPath(),options);
//        imageView.setImageBitmap(bmp);
//    }

}
