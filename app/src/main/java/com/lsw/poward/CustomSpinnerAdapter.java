package com.lsw.poward;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lsw.poward.R;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private int[] spinnerImages;
    private String[] spinnerNames;
    private Context context;

    public CustomSpinnerAdapter(@NonNull Context context, int[] images, String[] names) {   //NonNull(null 불가능)

        super(context, R.layout.spinner_row);
        this.spinnerImages = images;
        this.spinnerNames = names;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {  //스피너 펼쳤을때
        return getView(position, convertView, parent);  //아래 getView 메서드 호출
    }

    @Override
    public int getCount() {
        return spinnerImages.length;
    }   //인덱스 가져오기

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {  //가장 중요

        ViewHolder mViewHolder = new ViewHolder();  //아래 ViewHolder 객체 생성

        //ConvertView: 가능하면 재사용될 뷰 (불가능하면 새 뷰 만듦)
        if (convertView == null) {

            //일치하는 뷰 객체 안에 레이아웃을 객체화
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //인플레이트 변수 설정
            convertView = mInflater.inflate(R.layout.spinner_row, parent, false);   //ArrayAdapter에 레이아웃 인플레이트

            mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.spinner_image);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.spinner_name);
            convertView.setTag(mViewHolder);

        } else {
            //뷰에 태그로 저장될 객체, 또는 설정돼있지 않다면 null
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.mImage.setImageResource(spinnerImages[position]);
        mViewHolder.mName.setText(spinnerNames[position]);

        return convertView;
    }

    //ViewHolder클래스 필드 설정. getView 메서드에서 객체화됨
    private static class ViewHolder {

        ImageView mImage;
        TextView mName;
    }
}