package me.zy.sports.activitys.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import me.zy.sports.R;
import me.zy.sports.dao.bean.KeepNoteEntity;
import me.zy.sports.utils.DecimalUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.homepage
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class sportsAdapter  extends BaseQuickAdapter<KeepNoteEntity, BaseViewHolder>{

    private Context mContext;

    public sportsAdapter(Context context, @Nullable List<KeepNoteEntity> data) {
        super(R.layout.item_keep_list, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, final KeepNoteEntity item) {
        holder.setText(R.id.item_date, TextUtils.isEmpty(item.getDate()) ? "暂无数据" : item.getDate())
                .setText(R.id.item_exercise_length_tv, TextUtils.isEmpty(item.getExerciseDuration().toString()) ? "暂无数据" : DecimalUtils.formatDecimalWithZero2(item.getExerciseDuration()) + " (h)")
                .setText(R.id.item_run_length_tv, TextUtils.isEmpty(item.getRunLength().toString()) ? "暂无数据" : DecimalUtils.formatDecimalWithZero2(item.getRunLength()) + " (km)")
                .setText(R.id.item_situp_tv, TextUtils.isEmpty(item.getSitUps().toString()) ? "暂无数据" : item.getSitUps().toString() + " 个")
                .setText(R.id.item_sports_apparatus_tv, TextUtils.isEmpty(item.getSportsApparatusTimes().toString()) ? "暂无数据" : item.getSportsApparatusTimes().toString() + " 组");
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddKeepNoteActivity.class);
                intent.putExtra("objectId", item.getObjectId());
                mContext.startActivity(intent);
            }
        });
    }
}
