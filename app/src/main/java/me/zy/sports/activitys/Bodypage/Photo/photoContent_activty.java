package me.zy.sports.activitys.Bodypage.Photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.ClassContent;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.Bodypage.Photo
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class photoContent_activty extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView ptListView;
    private String id;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photocontent_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        setSupportActionBar(toolbar);//设置ToolBar
        toolbar = (Toolbar) findViewById(R.id.contenttb);
        getSupportActionBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }
    private void  init() {
        ptListView = (ListView)findViewById(R.id.paListView);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");//获取动作id
        BmobSuc();

    }

    private void BmobSuc() {
        BmobQuery<ClassContent> Query = new BmobQuery<>();
        Query.addWhereEqualTo("buildClassid", id);
        Query.findObjects(new FindListener<ClassContent>() {
                @Override
                public void done(List<ClassContent> object, BmobException e)
                {
                    if (e == null) {
                        Log.w("TTTTTT", "查表成功");
                        ptListView.setAdapter(new ContentAdapter(photoContent_activty.this,object));
                    } else {

                    }
                }
            });

        }


    }