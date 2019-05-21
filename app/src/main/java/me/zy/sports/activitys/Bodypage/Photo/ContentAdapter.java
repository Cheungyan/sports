package me.zy.sports.activitys.Bodypage.Photo;

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
import me.zy.sports.dao.bean.ClassContent;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.Bodypage.Photo
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class ContentAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ClassContent> mList;
    private ClassContent data;


    public ContentAdapter(Context mContext, List<ClassContent> mList) {
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
            convertView = inflater.inflate(R.layout.item_photo_content, parent,false);
            viewHolder.ipt_image= (ImageView) convertView.findViewById(R.id.ipt_ima);
            viewHolder.ipt_title = (TextView) convertView.findViewById(R.id.ipt_title);
            viewHolder.ipt_desc = (TextView) convertView.findViewById(R.id.ipt_desc);
            viewHolder.ipt_yaodian= (TextView) convertView.findViewById(R.id.ipt_yaodian);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();

        }
        data=mList.get(position);
        viewHolder.ipt_title.setText(data.getTitle());
        viewHolder.ipt_desc.setText(data.getDesc());
        Log.w("TTTTTT", "提取成功"+data.getObjectId()+data.getImage());
        if(!TextUtils.isEmpty(data.getImage().getUrl())){
            //加载图片
            Picasso.with(mContext).load(data.getImage().getUrl()).into(viewHolder.ipt_image);
        }
        return convertView;
    }


    class ViewHolder {
        private ImageView ipt_image;
        private TextView ipt_title;
        private TextView ipt_desc;
        private TextView ipt_yaodian;
    }

}
