package com.lsw.poward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListItem> listItems = new ArrayList<ListItem>();

    public ListViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listItems.size();    //리스트의 요소 개수 알아내기
    }

    @Override
    public Object getItem(int i) {  //리스트의 번호에 해당하는 요소 가져오기
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //아이템 가져오기

        // item.xml 레이아웃을 inflate해서 참조획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sub, parent, false);
        }

        // item.xml 의 참조 획득
//        TextView stNum = (TextView) convertView.findViewById(R.id.stNum);
//        TextView stGiverName = (TextView) convertView.findViewById(R.id.stGiverName);
//        TextView stReward = (TextView) convertView.findViewById(R.id.stReward);
//        TextView stDate = (TextView) convertView.findViewById(R.id.stDate);

        ListItem listItem = listItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
//        stNum.setText(listItem.getNum());
//        stGiverName.setText(listItem.getGivername());
//        stReward.setText(listItem.getReward());
//        stDate.setText(listItem.getDate());


        return convertView;
    }

    public void addItem(String stNum, String givername, String reward, String date){  //어댑터에 아이템 넣기
        ListItem listItem = new ListItem();

        listItem.setNum(stNum);
        listItem.setGivername(givername);
        listItem.setReward(reward);
        listItem.setDate(date);

        listItems.add(listItem);
    }
}
