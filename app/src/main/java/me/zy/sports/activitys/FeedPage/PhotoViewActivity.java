package me.zy.sports.activitys.FeedPage;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import me.zy.sports.R;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/20.
 * 描述：
 */
public class PhotoViewActivity extends AppCompatActivity {
    PhotoView photoView;
    Toolbar mToolbar;
    String pictureUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view_activity);
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.img_btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewActivity.this.onBackPressed();
            }
        });
        photoView=(PhotoView) findViewById(R.id.photo_view);
        pictureUrl = getIntent().getStringExtra("pictureUrl");
        Glide.with(this)
                .load(pictureUrl)
                .listener(glideRequestListener)
                .crossFade()
                .into(photoView);
    }


    /**
     * Glide的监听
     */
    private RequestListener<String, GlideDrawable> glideRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Toast.makeText(PhotoViewActivity.this, "图片加载失败，请稍后再试。。。", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };


}
