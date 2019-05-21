package me.zy.sports.activitys.BodyKeep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
public class bodyData extends AppCompatActivity {
    private RulerView ruler_height;   //身高的view
    private RulerView ruler_weight ;  //体重的view
    private TextView tv_register_info_height_value,tv_register_info_weight_value;
    private CheckBox sex;
    Button save_body;
    Button de_body;
    boolean sex1;
    float height,weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulew_activity);

        ruler_height=(RulerView)findViewById(R.id.ruler_height);
        ruler_weight=(RulerView)findViewById(R.id.ruler_weight);

        tv_register_info_height_value=(TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value=(TextView) findViewById(R.id.tv_register_info_weight_value);
        sex=(CheckBox)findViewById(R.id.btn_register_info_sex);
        boolean sex1=sex.isChecked();


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

        Body body=new Body();
        body.setSex(sex1);
        body.setHeight(height);
        body.setWeight(weight);
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
        ruler_height.setValue(165, 80, 220, 1);

        ruler_weight.setValue(55, 20, 200, 0.1f);

    }
}
