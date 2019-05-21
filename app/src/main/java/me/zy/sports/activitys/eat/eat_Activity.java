package me.zy.sports.activitys.eat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.MyArticle;

/**
 * Created by Administrator on 2019/1/2.
 */

public class eat_Activity extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;
    private ListView eListView;
    private List<String>mListTitle=new ArrayList<>();
    private List<String>mListUrl=new ArrayList<>();
    private List<String>mListId=new ArrayList<>();
    private List<String>mListImage=new ArrayList<>();
    private ImageView i_collectlist;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eat_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        toolbar = (Toolbar) findViewById(R.id.eatwtb);
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
        i_collectlist=(ImageView)findViewById(R.id.i_collectlist) ;
        i_collectlist.setOnClickListener(this);
        eListView= (ListView)findViewById(R.id.eListView);
        BmobSuc();
        //点击事件
        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(eat_Activity.this,WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                intent.putExtra("id", mListId.get(position));
                intent.putExtra("image", mListImage.get(position));
                startActivity(intent);
            }
        });
     }

    private void BmobSuc() {
        BmobQuery<MyArticle> MyArticleBmobQuery = new BmobQuery<>();
        MyArticleBmobQuery.order("-id");
        MyArticleBmobQuery.findObjects(new FindListener<MyArticle>() {
            @Override
            public void done(List<MyArticle> object, BmobException e) {
                if (e == null) {
                    Log.w("TTTTTT", "查表成功"+object.get(0).getAUrl()+object.get(0).getTitle());
                    for(int i=0;i<object.size();i++)
                    {
                        String imageurl=object.get(i).getImage_title().getFileUrl();
                        String title= object.get(i).getTitle();
                        String url= object.get(i).getAUrl();
                        String id= object.get(i).getObjectId();
                        mListTitle.add(title);
                        mListUrl.add(url);
                        mListId.add(id);
                        mListImage.add(imageurl);
                    }
                    eListView.setAdapter(new eatAdapter(eat_Activity.this,object));
                } else {

                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.i_collectlist)
        {
            startActivity(new Intent(eat_Activity.this, collectEat_Activity.class));
        }

    }
}