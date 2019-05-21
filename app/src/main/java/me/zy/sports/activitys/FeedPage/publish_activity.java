package me.zy.sports.activitys.FeedPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.liuguangqiang.ipicker.IPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Myuser;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/19.
 * 描述：
 */
public class publish_activity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    AppCompatEditText mMoodInfo;
    ImageView mIvSubmit;
    ImageView mImageView;
    ImageView mImageView_add;

    String picture = "";
    String mInfo = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_activity);
        Bmob.initialize(this, "623a4535779272842b9fe7411f7a6e0e");
        init();

    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toobar);
        mMoodInfo = (AppCompatEditText) findViewById(R.id.feed_info_publish);
        mIvSubmit = (ImageView) findViewById(R.id.iv_submit);
        mImageView = (ImageView) findViewById(R.id.feed_image);
        mImageView_add = (ImageView) findViewById(R.id.add_feed_image);
        infeed();
        mImageView_add.setOnClickListener(this);
        mIvSubmit.setOnClickListener(this);
        mImageView.setOnClickListener(this);

    }

    private void infeed() {

        IPicker.setOnSelectedListener(new IPicker.OnSelectedListener() {
            @Override
            public void onSelected(List<String> paths) {
                final String picPath = paths.get(0);
                picture = picPath;
                mImageView.setVisibility(View.VISIBLE);
                Glide.with(publish_activity.this).load(picPath).into(mImageView);
                mImageView_add.setVisibility(View.GONE);
                new AlertDialog.Builder(publish_activity.this).setMessage("需要压缩图片吗？")
                        .setPositiveButton("需要", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Luban.get(publish_activity.this)
                                        .load(new File(picPath))                     //传人要压缩的图片
                                        .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                                        .setCompressListener(new OnCompressListener() { //设置回调

                                            @Override
                                            public void onStart() {
                                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                            }

                                            @Override
                                            public void onSuccess(File file) {
                                                Log.d("TAG", "onSuccess: " + file.getAbsolutePath());
                                                upLoad(file);
                                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                // TODO 当压缩过去出现问题时调用
                                                Toast.makeText(publish_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }).launch();    //启动压缩
                            }
                        }).setNegativeButton("不需要", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upLoad(new File(picPath));
                    }
                }).show();
            }
        });

        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(publish_activity.this).setMessage("你确定要删除该图片吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                picture = null;
                                mImageView.setVisibility(View.GONE);
                                mImageView_add.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return false;
            }
        });
    }

    private void upLoad(File file) {
        final BmobFile bmobFile = new BmobFile(file);
        final AlertDialog dialog = new AlertDialog.Builder(publish_activity.this).create();
        final View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.setCancelable(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    picture = bmobFile.getFileUrl();//getFileUrl()--返回的上传文件的完整地址
                    dialog.dismiss();
                    Toast.makeText(publish_activity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(publish_activity.this, "上传失败!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgress(Integer value) {
                NumberProgressBar numberProgressBar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
                numberProgressBar.setProgress(value);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_submit:
                mInfo = mMoodInfo.getText().toString().trim();
                if (TextUtils.isEmpty(mInfo)) {
                    Toast.makeText(this, "好歹写点什么吧！", Toast.LENGTH_SHORT).show();
                } else {
                    Feed feed = new Feed();
                    feed.setFeedInfo(mInfo);
                    feed.setLikeNum(0);
                    feed.setCommentNum(0);
                    feed.setSeeNum(0);
                    feed.setIamge(picture);
                    feed.setLike(false);
                    feed.setUser(BmobUser.getCurrentUser(Myuser.class));
                    feed.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(publish_activity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(publish_activity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.add_feed_image:
                IPicker.setLimit(1);
                IPicker.open(this);
                break;
            case R.id.feed_image:
                startActivity(new Intent(this, PhotoViewActivity.class).putExtra("pictureUrl", picture));
                break;
        }
    }
}
