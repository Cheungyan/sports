package me.zy.sports.activitys.eat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.zy.sports.R;
import me.zy.sports.dao.bean.MyArticle;
import me.zy.sports.dao.bean.collect;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.eat
 * Created by Administrator on 2019/5/9.
 * 描述：
 */
public class collectAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<collect> mList;
    private collect data;

    public collectAdapter(Context mContext, List<collect> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        collectAdapter.ViewHolder viewHolder= null;
        if(convertView==null) {
            viewHolder = new collectAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.eat_item, parent,false);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(collectAdapter.ViewHolder)convertView.getTag();

        }
        data=mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());

        Log.w("TTTTTT", "提取成功"+data.getObjectId()+data.getImage_title());
        if(!TextUtils.isEmpty(data.getImage_title())){
            //加载图片
            Picasso.with(mContext).load(data.getImage_title()).into(viewHolder.iv_img);
        }



        return convertView;


    }


    class ViewHolder {
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_desc;
    }
}
