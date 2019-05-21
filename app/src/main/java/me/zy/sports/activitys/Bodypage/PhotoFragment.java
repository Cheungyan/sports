package me.zy.sports.activitys.Bodypage;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;
import me.zy.sports.activitys.Bodypage.Photo.PhotoAdapter;
import me.zy.sports.activitys.Bodypage.Photo.photoContent_activty;
import me.zy.sports.dao.bean.BuildClass;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.Bodypage
 * Created by Administrator on 2019/5/9.
 * 描述：
 */
public class PhotoFragment extends Fragment {
    private ListView eListView;
    private List<String> mListTitle=new ArrayList<>();
    private List<String>mListId=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_photo,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        eListView = (ListView) view.findViewById(R.id.pListView);
        BmobSuc();
        //点击事件
        eListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), photoContent_activty.class);
                intent.putExtra("title", mListTitle.get(position));
                intent.putExtra("id", mListId.get(position));
                startActivity(intent);
            }
        });

    }

    private void BmobSuc() {
        BmobQuery<BuildClass> BUildBmobQuery = new BmobQuery<>();
        BUildBmobQuery.findObjects(new FindListener<BuildClass>() {
            @Override
            public void done(List<BuildClass> object, BmobException e) {
                if (e == null) {
                    Log.w("TTTTTT", "查表成功");
                    for(int i=0;i<object.size();i++)
                    {
                        String title= object.get(i).getTitle();
                        String id= object.get(i).getObjectId();
                        mListTitle.add(title);
                        mListId.add(id);
                    }
                    eListView.setAdapter(new PhotoAdapter(getActivity(),object));
                } else {

                }
            }
        });

    }


}
