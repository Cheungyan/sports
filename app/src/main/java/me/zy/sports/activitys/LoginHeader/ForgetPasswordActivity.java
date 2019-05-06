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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.utils.Utils;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.LoginHeader
 * Created by Administrator on 2019/5/4.
 * 描述：
 */
public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_newuser;
    private EditText et_newver;
    private EditText et_newpass;
    private Button btn_newgetver;
    private  Button btn_updatepassword;
    private TextView textnew;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        initView();



    }

    private void initView() {
        et_newuser=(EditText)findViewById(R.id.et_newuser);
        et_newver=(EditText)findViewById(R.id.et_newver);
        et_newpass=(EditText)findViewById(R.id.et_newpass);
        btn_newgetver=(Button)findViewById(R.id. btn_newgetver);
        btn_updatepassword=(Button)findViewById(R.id. btn_updatepassword);
        textnew=(TextView) findViewById(R.id. textnew);
        btn_newgetver.setOnClickListener(this);
        btn_updatepassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone=et_newuser.getText().toString().trim();
        switch (v.getId())
        {
            case R.id.btn_newgetver:
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
                                    Toast.makeText(ForgetPasswordActivity.this, "发送验证码成功" , Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "发送验证码失败" , Toast.LENGTH_SHORT).show();
                                    textnew.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                                }
                            }
                        });
                    }
                }
                break;
            case R.id.btn_updatepassword:
                //获取到输入框的值
                String ver=et_newver.getText().toString().trim();
                String newpassword=et_newpass.getText().toString().trim();
                //判断是否为空
                if(!TextUtils.isEmpty(newpassword))
                {
                    Myuser user=new Myuser();
                    //TODO 设置用户密码
                    user.setPassword(newpassword);
                    user.resetPasswordBySMSCode(ver, newpassword, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this,"短信修改成功" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this, LoginHeader.class));
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this,"短信注册失败：" + e.getErrorCode() + "-" + e.getMessage(), Toast.LENGTH_LONG).show();
                                textnew.append("短信注册失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
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
