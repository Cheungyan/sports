package me.zy.sports.activitys.eat;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import cn.bmob.v3.BmobArticle;
import me.zy.sports.R;
import me.zy.sports.dao.bean.MyArticle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.eat_Activity
 * Created by Administrator on 2019/5/7.
 * 描述：
 */
public class eatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<MyArticle> mList;
    private MyArticle data;



    public eatAdapter(Context mContext, List<MyArticle> mList) {
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
        ViewHolder viewHolder= null;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.eat_item, parent,false);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();

        }
        data=mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_desc.setText(data.getDesc());
        Log.w("TTTTTT", "提取成功"+data.getObjectId()+data.getImage_title());
        if(!TextUtils.isEmpty(data.getImage_title().getUrl())){
            //加载图片
            Picasso.with(mContext).load(data.getImage_title().getUrl()).into(viewHolder.iv_img);
        }



    return convertView;


     }


    class ViewHolder {
    private ImageView iv_img;
    private TextView tv_title;
    private TextView tv_desc;
   }
}