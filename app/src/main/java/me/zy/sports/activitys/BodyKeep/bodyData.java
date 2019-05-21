package me.zy.sports.activitys.BodyKeep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Body;
import me.zy.sports.dao.bean.Myuser;

import com.zkk.view.rulerview.RulerView;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.BodyKeep
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class bodyData extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight ;  //体重的view
    Toolbar mToolbar;
    private TextView tv_register_info_height_value,tv_register_info_weight_value;
    private CheckBox sex;
    Button save_body;
    Button de_body;
    boolean sex1;
    float height,weight;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulew_activity);
        Intent intent = getIntent();
        id= intent.getStringExtra("id");
        ruler_height=(RulerView)findViewById(R.id.ruler_height);
        ruler_weight=(RulerView)findViewById(R.id.ruler_weight);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("更新现在体重");
        tv_register_info_height_value=(TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value=(TextView) findViewById(R.id.tv_register_info_weight_value);
        save_body=(Button)findViewById(R.id.save_body);
        de_body=(Button)findViewById(R.id.de_body);
        de_body.setOnClickListener(this);
        save_body.setOnClickListener(this);
        sex=(CheckBox)findViewById(R.id.btn_register_info_sex);
        sex.setOnCheckedChangeListener(this);



        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value+"");
                height=value;
            }
        });



        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value+"");
                weight=value;
            }
        });


        ruler_height.setValue(165, 80, 220, 1);

        ruler_weight.setValue(55, 20, 200, 0.1f);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.save_body:
                Body p2 = new Body();
                p2.setObjectId(id);
                p2.delete(new UpdateListener() {
                              @Override
                              public void done(BmobException e) {
                                  if (e == null) {

                                  } else {
                                  }
                              }
                          });
                Body body=new Body();
                body.setSex(sex1);
                body.setHeight(height);
                body.setWeight(weight);
                body.setNewweight(weight);
                body.setUser(BmobUser.getCurrentUser(Myuser.class));
                body.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(bodyData.this, "更新成功"+objectId, Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                        }
                    }
                });
                break;

            case R.id.de_body:
                finish();
                break;

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sex1=isChecked;
    }
}
