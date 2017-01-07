package com.example.z.thelastrow_client_gamedeal.fragment.buyorsell;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.z.thelastrow_client_gamedeal.R;
import com.example.z.thelastrow_client_gamedeal.fragment.api.SDKVersion;
import com.example.z.thelastrow_client_gamedeal.fragment.inputmodule.InputThingsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ThingsSellFragment extends Fragment {

    final static int RESULT_CODE_PICTURECHOOSE = 0x606;
    final static int REQUEST_CODE_PICTURECHOOSE = 0x616;
    private View view;
    private InputThingsFragment things_name, things_value;
    private EditText things_number;
    private GridView thing_picture;
    private List<String> picturechoose;
    private ImageView addbutton;
    private LinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sell_things, null);

            if (SDKVersion.isMoreThanAPI19()) {
                things_name = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_name);
                things_value = (InputThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_value);

//                things_picture = (PictureThingsFragment) getChildFragmentManager().findFragmentById(R.id.things_sell_picture);
            } else {
                things_name = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_name);
                things_value = (InputThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_value);

//                things_picture = (PictureThingsFragment) getFragmentManager().findFragmentById(R.id.things_sell_picture);
            }

            things_number = (EditText) view.findViewById(R.id.things_sell_number);

            view.findViewById(R.id.things_sell_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goSubmit();
                }
            });

            view.findViewById(R.id.things_sell_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            view.findViewById(R.id.things_sell_plus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(things_number.getText().toString());
                    number++;
                    things_number.setText("" + number);
                }
            });

            view.findViewById(R.id.things_sell_minus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(things_number.getText().toString());
                    if (number == 0) {
                        return;
                    } else {
                        number--;
                        things_number.setText("" + number);
                    }
                }
            });
            ///////////
            layout = (LinearLayout) view.findViewById(R.id.things_sell_layout);
            addbutton = addButton();
            layout.addView(addbutton);

            picturechoose = new ArrayList<>();

            thing_picture = (GridView) view.findViewById(R.id.things_sell_gridview);
            thing_picture.setAdapter(chooseAdapter);


        }
        return view;
    }

    private ImageView addButton() {
        ImageView addPicture = new ImageView(getActivity());
        addPicture.setLayoutParams(new ViewGroup.LayoutParams(60, 60));
        addPicture.setBackgroundResource(R.drawable.plus2math_50);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetAllLocalPictureActivity.class);
                intent.putExtra("size", picturechoose == null ? 0 : picturechoose.size());
                startActivityForResult(intent , REQUEST_CODE_PICTURECHOOSE);
            }
        });
        return  addPicture;
    }

//    private void removeAddButton() {
//        if (picturechoose.size() < 6) {
//            thing_picture.addView(addPicture);
//        } else {
//            thing_picture.removeView(addPicture);
//        }
//    }

//    private void addPictureClick(List<String> pictures) {
////        View imageView = LayoutInflater.from(getActivity()).inflate(R.layout.equippictureview, null);
////        ImageView imageView = (ImageView) viewOFImage.findViewById(R.id.equippictureview_image);
//
//
//        ImageView imageView = new ImageView(getActivity());
//
//        imageView.setBackgroundResource(R.drawable.equippictureview_background);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(70, 70);
//        params.setMargins(2, 2, 2, 2);
//        imageView.setLayoutParams(params);
//        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logo_tencent_game));
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//        picturePanel.addView(imageView, 0);
//
//        if (picturePanel.getChildCount() > 6) {
//            addPicture.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        things_name.setThingsInputItemname("装备名称：");
        things_name.setThingsInputItemHint("请输入装备名");
        things_name.setThingsInputItemtypeText();

        things_value.setThingsInputItemname("价格:");
        things_value.setThingsInputItemHint("请输入价格");
        things_value.setThingsInputItemImeOption(EditorInfo.IME_ACTION_DONE);

    }

    public String getThingsName() {
        return things_name.getThingsInputItemtext();
    }

    public String getThingValue() {
        return things_value.getThingsInputItemtext();
    }

    public String getThingNumber() {
        return things_number.getText().toString();
    }

    public List<String> getPictures() {
        return picturechoose;
    }

//    public byte[] getThingPicture() {
//        return things_picture.getData();
//    }

    public interface OnSellSubmitListener {
        void onSubmit();
    }

    private OnSellSubmitListener onSellSubmitListener;

    public void setOnSubmitListener(OnSellSubmitListener onSellSubmitListener) {
        this.onSellSubmitListener = onSellSubmitListener;
    }

    private void goSubmit() {
        if (onSellSubmitListener != null) {
            onSellSubmitListener.onSubmit();
        }
    }

    BaseAdapter chooseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return (picturechoose == null ? 0 : picturechoose.size());
        }

        @Override
        public Object getItem(int position) {
            return picturechoose.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;


            if (convertView == null) {
                imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturechoose.get(position)));
            return imageView;
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICTURECHOOSE) {
            if (resultCode == RESULT_CODE_PICTURECHOOSE) {
                if (picturechoose == null) {
                    picturechoose = data.getStringArrayListExtra("picturechoose");
                } else {
                    picturechoose.addAll(data.getStringArrayListExtra("picturechoose"));
                }
                if (picturechoose.size() == 6) {
                    layout.removeView(addbutton);
                }
                chooseAdapter.notifyDataSetChanged();
            }
        }
    }
}
