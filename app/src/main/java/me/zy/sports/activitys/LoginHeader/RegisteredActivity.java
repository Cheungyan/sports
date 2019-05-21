package me.zy.sports.activitys.LoginHeader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import me.zy.sports.R;
import me.zy.sports.activitys.homepage.HomeActivity;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.utils.Utils;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.LoginHeader
 * Created by Administrator on 2019/5/4.
 * 描述：
 */
public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_ver;
    private EditText et_pass;
    private Button btn_getver;
    private  Button btn_registersuc;
    private TextView textView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        initView();



    }

    private void initView() {
        et_user=(EditText)findViewById(R.id.et_user);
        et_ver=(EditText)findViewById(R.id.et_ver);
        et_pass=(EditText)findViewById(R.id.et_pass);
        btn_getver=(Button)findViewById(R.id. btn_getver);
        btn_registersuc=(Button)findViewById(R.id. btn_registersuc);
        textView=(TextView) findViewById(R.id. textView);
        btn_getver.setOnClickListener(this);
        btn_registersuc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone=et_user.getText().toString().trim();
        switch (v.getId())
        {
            case R.id.btn_getver:
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = Utils.isPhone(phone);
                    if (result == false) {
                        Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        BmobSMS.requestSMSCode(phone, "", new QueryListener<Integer>() {
                            @Override
                            public void done(Integer smsId, BmobException e)
                            {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this, "发送验证码成功" , Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "发送验证码失败" , Toast.LENGTH_SHORT).show();
                                    textView.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.btn_registersuc:
                //获取到输入框的值
                String ver=et_ver.getText().toString().trim();
                String password=et_pass.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(password))
                {
                    Myuser user=new Myuser();
                    //TODO 设置用户名默认为手机号码
                    user.signOrLoginByMobilePhone(phone, ver, new LogInListener< Myuser>() {
                        public void done(Myuser bmobUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisteredActivity.this,"短信注册成功：" + bmobUser.getUsername(), Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisteredActivity.this, HomeActivity.class));
                            } else {
                                Toast.makeText(RegisteredActivity.this,"短信注册失败：" + e.getErrorCode() + "-" + e.getMessage(), Toast.LENGTH_LONG).show();
                                textView.append("短信注册失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                            }
                        }
                    });

                }
                else
                {
                    Toast.makeText(this,"验证码和密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
