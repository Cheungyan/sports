package me.zy.sports.activitys.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.KeepNoteEntity;
import me.zy.sports.dao.bean.Myuser;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.homepage
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class AddKeepNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private static final String TAG = "AddKeepFitNoteActivity";
    TextView addKeepFitDateTv;
    EditText addKeepFitTimeLength;
    EditText addKeepFitRunLength;
    EditText addKeepFitSitUps;
    EditText addKeepFitSportsApparatusTimes;
    TextView addKeepFitSubmit;
    RelativeLayout contentRl;
    private Context mContext;
    private boolean isEdit = false;
    private String objectId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        mContext = this;
        init();
    }

    private void init() {
        contentRl=( RelativeLayout)findViewById(R.id.content_rl);
        addKeepFitDateTv=(TextView) findViewById(R.id.add_keep_fit_date_tv);
        addKeepFitTimeLength=(EditText)findViewById(R.id.add_keep_fit_time_length);
        addKeepFitRunLength=(EditText )findViewById(R.id.add_keep_fit_run_length);
        addKeepFitSitUps=( EditText )findViewById(R.id.add_keep_fit_sitUps);
        addKeepFitSportsApparatusTimes=(EditText )findViewById(R.id.add_keep_fit_sports_apparatus_times);
        addKeepFitSubmit=(TextView )findViewById(R.id.add_keep_fit_submit);
        addKeepFitSubmit.setOnClickListener(this);
        //设置日期
        addKeepFitDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddKeepNoteActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        if (getIntent().getStringExtra("objectId") != null) {
            isEdit = true;
            objectId = getIntent().getStringExtra("objectId");
        }
        //改变按钮文字
        addKeepFitSubmit.setText(isEdit ? "更新" : "保存");

        if (isEdit) {
            queryData();
        } else {
            contentRl.setVisibility(View.VISIBLE);
            Date todayDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(todayDate);
            addKeepFitDateTv.setText(dateString);
        }
    }


    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + formatSingle(monthOfYear+1) + formatSingle(dayOfMonth);
        addKeepFitDateTv.setText(date);
    }

    private String formatSingle(int num) {
        return num >= 10 ? String.valueOf(num) : ("0" + num);
    }
    /**
     * 查询本条数据
     */
    private void queryData() {
        BmobQuery<KeepNoteEntity> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<KeepNoteEntity>() {
            @Override
            public void done(KeepNoteEntity object, BmobException e) {
                if (e == null) {
                    fillData(object);
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }



    /**
     * 填充页面数据
     *
     * @param object
     */
    private void fillData(KeepNoteEntity object) {

        addKeepFitDateTv.setText(object.getDate().toString());
        addKeepFitTimeLength.setText(object.getExerciseDuration().toString());
        addKeepFitRunLength.setText(object.getRunLength().toString());
        addKeepFitSitUps.setText(object.getSitUps().toString());
        addKeepFitSportsApparatusTimes.setText(object.getSportsApparatusTimes().toString());

        contentRl.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_keep_fit_submit:
                KeepNoteEntity bean = new KeepNoteEntity();
                bean.setDate(addKeepFitDateTv.getText().toString());
                bean.setExerciseDuration(Float.valueOf(addKeepFitTimeLength.getText().toString()));
                bean.setRunLength(Float.valueOf(addKeepFitRunLength.getText().toString()));
                bean.setSitUps(Integer.valueOf(addKeepFitSitUps.getText().toString()));
                bean.setSportsApparatusTimes(Integer.valueOf(addKeepFitSportsApparatusTimes.getText().toString()));
                bean.setUserId(BmobUser.getCurrentUser(Myuser.class).getObjectId());
                if (isEdit) {
                    bean.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddKeepNoteActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Log.d(TAG, "done: " + e.getMessage());
                                Toast.makeText(AddKeepNoteActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    bean.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(AddKeepNoteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddKeepNoteActivity.this, "添加失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }

    }





}
