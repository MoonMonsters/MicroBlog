package com.newer.newerblogging.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.utils.LoggerUtil;
import com.newer.newerblogging.utils.NetConnectionUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;

public class CreateWeiboActivity extends BaseActivity {


    @Bind(R.id.et_create_weibo)
    EditText etCreateWeibo;
    @Bind(R.id.iv_create_weibo)
    ImageView ivCreateWeibo;
    @Bind(R.id.iv_remove_pic)
    ImageView ivRemovePic;

    private String mPath;
    private boolean mSendPic = false;
    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的

    @Override
    public int getLayoutResource() {
        return R.layout.activity_create_weibo;
    }

    @Override
    public void initListener() {
        ivCreateWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType(IMAGE_TYPE);
                startActivityForResult(getAlbum, IMAGE_CODE);
            }
        });

        ivRemovePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSendPic = false;
                ivCreateWeibo.setImageBitmap(null);
                ivRemovePic.setVisibility(View.GONE);
            }
        });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            return;
        }

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);//显得到bitmap图片
                ivCreateWeibo.setImageBitmap(bitmap);
                mSendPic = true;
                ivRemovePic.setVisibility(View.VISIBLE);

                Uri uri = data.getData();
                File file = new File(Environment.getExternalStorageDirectory(), "data.txt");
                LoggerUtil.i("TAG", "path = " + file.getAbsolutePath());
                LoggerUtil.i("TAG", uri.toString());
                mPath = uri.toString().substring("file://".length());
                LoggerUtil.i("TAG", "mPath = " + mPath);

            } catch (IOException e) {
                Toast.makeText(this, "图片选取错误", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_create_weibo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_create_weibo_send) {
            final String status = etCreateWeibo.getText().toString();
            if (mSendPic) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetConnectionUtil.netToUploadStatues(CreateWeiboActivity.this, status, mPath, new NetConnectionUtil.NetCallback() {
                            @Override
                            public void doSuccess(String data) {
                                Toast.makeText(CreateWeiboActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                CreateWeiboActivity.this.finish();
                            }

                            @Override
                            public void doFail(String message) {
                                Toast.makeText(CreateWeiboActivity.this, "发布失败，请稍后再试...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            } else {
                NetConnectionUtil.netToUpdateStatus(this, status, new NetConnectionUtil.NetCallback() {
                    @Override
                    public void doSuccess(String data) {
                        Toast.makeText(CreateWeiboActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        CreateWeiboActivity.this.finish();
                    }

                    @Override
                    public void doFail(String message) {
                        Toast.makeText(CreateWeiboActivity.this, "发布失败，请稍后再试...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (item.getItemId() == R.id.action_create_weibo_cancel) {
            this.finish();
        } else {
            this.finish();
        }

        return true;
    }
}
