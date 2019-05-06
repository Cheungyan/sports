package me.zy.sports.activitys.eat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;

/**
 * Created by Administrator on 2019/1/2.
 */

public class eat_Activity extends AppCompatActivity {
    private ListView eListView;
    private List<BmobArticle>mlist=new ArrayList<>();

    private List<String>mListTitle=new ArrayList<>();
    private List<String>mListUrl=new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eat_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        initView();


    }

    private void initView() {
        eListView= (ListView)findViewById(R.id.eListView);
        BmobSuc();
        //点击事件
        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(eat_Activity.this,WebViewActivity.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("url", mListUrl.get(position));
                startActivity(intent);
            }
        });
     }

    private void BmobSuc() {
        BmobQuery<BmobArticle> bmobArticleBmobQuery = new BmobQuery<>();
        bmobArticleBmobQuery.findObjects(new FindListener<BmobArticle>() {
            @Override
            public void done(List<BmobArticle> object, BmobException e) {
                if (e == null) {
                    for(int i=0;i<object.size();i++)
                    {
                        BmobArticle bArticle=(BmobArticle)object.get(i);
                        String title=bArticle.getTitle();
                        String url=bArticle.getUrl();
                        mListTitle.add(title);
                        mListUrl.add(url);
                    }
                    eListView.setAdapter(new eatAdapter(eat_Activity.this,object));
                } else {

                }
            }
        });
    }


}