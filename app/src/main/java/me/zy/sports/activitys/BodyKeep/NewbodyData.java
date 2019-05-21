package me.zy.sports.activitys.BodyKeep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.zkk.view.rulerview.RulerView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Body;
import me.zy.sports.dao.bean.Myuser;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.BodyKeep
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class NewbodyData extends AppCompatActivity implements View.OnClickListener {
    private RulerView ruler_weight ;  //体重的view
    private TextView tv_register_info_weight_value;
    private Button save_body;
    private Button de_body;
    Toolbar mToolbar;
    float weight;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulewnow_activity);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("设定体重新目标");
        Intent intent = getIntent();
        id= intent.getStringExtra("id");
        ruler_weight=(RulerView)findViewById(R.id.ruler_weight);
        tv_register_info_weight_value=(TextView) findViewById(R.id.tv_register_info_weight_value);
        save_body=(Button)findViewById(R.id.save_body);
        de_body=(Button)findViewById(R.id.de_body);
        de_body.setOnClickListener(this);
        save_body.setOnClickListener(this);


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value+"");
                weight=value;
            }
        });

        ruler_weight.setValue(55, 20, 200, 0.1f);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save_body:
                Body body=new Body();
                body.setNewweight(weight);
                body.setUser(BmobUser.getCurrentUser(Myuser.class));
                body.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null)
                        { Toast.makeText(NewbodyData.this, "更新成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {}
                    }
                });
                break;
            case R.id.de_body:
                finish();
                break;

        }

    }
}
