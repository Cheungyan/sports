package me.zy.sports.activitys.eat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.MyArticle;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.collect;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.eat
 * Created by Administrator on 2019/5/8.
 * 描述：
 */
public class collectEat_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView cListView;
    private List<String> mListTitle=new ArrayList<>();
    private List<String>mListUrl=new ArrayList<>();
    private List<String>mListId=new ArrayList<>();
    private List<String>mListImage=new ArrayList<>();
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eat_collectlist);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        toolbar = (Toolbar) findViewById(R.id.eat_liesttb);
        setSupportActionBar(toolbar);//设置ToolBar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
    }

    private void initView() {
        cListView= (ListView)findViewById(R.id.cListView);
        BmobSuc();
        //点击事件
        cListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(collectEat_Activity.this,WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                intent.putExtra("id", mListId.get(position));
                intent.putExtra("image", mListImage.get(position));
                startActivity(intent);
            }
        });
    }

    private void BmobSuc() {
        String userid= BmobUser.getCurrentUser(Myuser.class).getObjectId();
        Log.d("TAG", "initView: " + userid);
        BmobQuery<collect> eq = new BmobQuery<collect>();
        eq.addWhereEqualTo("userId", userid);
        eq.findObjects(new FindListener<collect>() {
            @Override
            public void done(List<collect> object, BmobException e) {
                if (e == null) {
                    Log.w("TTTTTT", "查表成功");
                    for(int i=0;i<object.size();i++)
                    {
                        String imageurl=object.get(i).getImage_title();
                        String title= object.get(i).getTitle();
                        String url= object.get(i).getUrl();
                        String id= object.get(i).getObjectId();
                        mListTitle.add(title);
                        mListUrl.add(url);
                        mListId.add(id);
                        mListImage.add(imageurl);
                    }
                    cListView.setAdapter(new collectAdapter(collectEat_Activity.this,object));
                } else {

                }
            }
        });
    }

}
