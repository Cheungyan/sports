package me.zy.sports.activitys.eat;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.activitys.homepage.HomeActivity;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.collect;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.eat
 * Created by Administrator on 2019/5/7.
 * 描述：
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {


    private String cId;


    private TextView t_title;
    private ImageView i_shoucang;
    private WebView mWebView;

    private String title;
    private String sUrl;
    private String imageUrl;
    private String id;


    //网页

    Boolean shouchang = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        initView();
    }

    private void initView() {
        t_title = (TextView) findViewById(R.id.t_title);
        i_shoucang = (ImageView) findViewById(R.id.i_shoucang);
        //设置当前收藏按钮
        i_shoucang.setOnClickListener(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        imageUrl=intent.getStringExtra("image");
        id = intent.getStringExtra("id");//获取文章id
        t_title.setText(title);//设置标题
        final String url = intent.getStringExtra("url");
        sUrl=url;
        Log.d("TAG", "initView: " + title +"::"+id);
        //查看当前是否收藏
        if(BmobUser.isLogin())
        {
            String userid=BmobUser.getCurrentUser(Myuser.class).getObjectId();
            Log.d("TAG", "initView: " + userid);
            BmobQuery<collect> eq1 = new BmobQuery<collect>();
            eq1.addWhereEqualTo("articleId", id);
            BmobQuery<collect> eq2 = new BmobQuery<collect>();
            eq2.addWhereEqualTo("userId", userid);
            List<BmobQuery<collect>> andQuerys = new ArrayList<BmobQuery<collect>>();
            andQuerys.add(eq1);
            andQuerys.add(eq2);//组合查询
            BmobQuery<collect> query = new BmobQuery<collect>();
            query.and(andQuerys);
            query.findObjects(new FindListener<collect>() {
                @Override
                public void done(List<collect> object, BmobException e) {
                    if (e == null) {
                        shouchang = true;
                        setIma(shouchang);
                        cId=object.get(0).getObjectId();
                        Toast.makeText(WebViewActivity.this, "收藏过了："+object.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                    } else {
                        shouchang = false;
                        setIma(shouchang);
                        Toast.makeText(WebViewActivity.this, "未收藏" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            startActivity(new Intent(WebViewActivity.this, LoginHeader.class));
        }


        mWebView = (WebView) findViewById(R.id.mWebView);




        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);

        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebChromeClient());
        //加载网页
        mWebView.loadUrl(url);

        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //我接受这个事件
                return true;
            }
        });


    }
    @Override
    //收藏按钮
    public void onClick(View v) {
        if (v.getId() == R.id.i_shoucang)
        {
            collect c2 = new collect();
            c2.setArticleId(id);
            c2.setUserId(BmobUser.getCurrentUser(Myuser.class).getObjectId());
            c2.setTitle(title);
            c2.setUrl(sUrl);
            c2.setImage_title(imageUrl);
            Log.d("TAG", "点击收藏: " + title +"::"+id);
            if (!shouchang) {
                c2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(WebViewActivity.this, "收藏成功，返回objectId为：" + objectId, Toast.LENGTH_SHORT).show();
                            cId=objectId;
                            shouchang=true;
                            setIma(shouchang);

                        } else {
                            Toast.makeText(WebViewActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                c2.setObjectId(cId);
                c2.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(WebViewActivity.this, "收藏取消成功", Toast.LENGTH_SHORT).show();
                            shouchang=false;
                            setIma(shouchang);
                        }else{
                            Toast.makeText(WebViewActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        }
    }

        private void setIma (Boolean isCheak){
            if (isCheak) {
                i_shoucang.setBackgroundResource(R.drawable.shoucangsuc);
            } else {
                i_shoucang.setBackgroundResource(R.drawable.shoucang);
            }
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shouchang = false;
        setIma(shouchang);
    }

    @Override
    protected void onStop() {
        super.onStop();
        shouchang = false;
        setIma(shouchang);
    }

    @Override
    protected void onPause() {
        super.onPause();
        shouchang = false;
        setIma(shouchang);
    }
}
