package me.zy.sports.activitys.LoginHeader;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import es.dmoral.toasty.Toasty;
import me.zy.sports.R;
import me.zy.sports.activitys.homepage.HomeActivity;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.utils.ShareUtils;
import me.zy.sports.utils.Utils;

public class LoginHeader extends AppCompatActivity implements View.OnClickListener {
    private CheckBox keep_password;
    private Button btn_login;
    private Button btn_registered;
    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
            setContentView(R.layout.header_login);
            initView();
    }

    private void initView() {

        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_registered=(Button)findViewById(R.id.btn_registered);
        btn_login.setOnClickListener(this);
        btn_registered.setOnClickListener(this);
        keep_password=(CheckBox) findViewById(R.id.keep_password);
        //设置选中状态
        boolean isCheak=ShareUtils.getBoolean(this,"keeppass",false);
        keep_password.setChecked(isCheak);
        if(isCheak){
            //设置密码
            et_username.setText(ShareUtils.getString(this,"username",""));
            et_password.setText(ShareUtils.getString(this,"password",""));

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {

            case R.id.btn_login:
                String name=et_username.getText().toString().trim();
                String pass=et_password.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result = Utils.isPhone(name);
                    if (result == false) {
                        Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Myuser user=new Myuser();
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.login(new SaveListener<Myuser>() {
                            @Override
                            public void done(Myuser myuser, BmobException e) {
                                if(e==null)
                                {
                                    startActivity(new Intent(LoginHeader.this, HomeActivity.class));

                                }
                                else {
                                    Toast.makeText(LoginHeader.this, "登录失败:"+e.toString() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
                break;
            case R.id. btn_registered:
               startActivity(new Intent(this,RegisteredActivity.class));
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //保存状态
        ShareUtils.putBoolean(this,"keeppass",keep_password.isChecked());
        //是否记住密码
        Log.d("LoginHeader"," keep_password:"+keep_password.isChecked());
        if(keep_password.isChecked())
        {
            ShareUtils.putString(this,"username",et_username.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
            Log.d("LoginHeader"," keep_password:"+et_username.getText());
            Log.d("LoginHeader"," keep_password:"+et_password.getText());
        }
        else
        {
            ShareUtils.deleShare(this,"username");
            ShareUtils.deleShare(this,"password");

        }
    }
}
