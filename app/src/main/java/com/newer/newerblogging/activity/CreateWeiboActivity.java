package com.newer.newerblogging.activity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.utils.NetConnectionUtil;

import butterknife.Bind;

public class CreateWeiboActivity extends BaseActivity {


    @Bind(R.id.et_create_weibo)
    EditText etCreateWeibo;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_create_weibo;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_create_weibo,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String status = etCreateWeibo.getText().toString();

        if(item.getItemId() == R.id.action_create_weibo_send){
            NetConnectionUtil.netToUpdateStatus(this, status, new NetConnectionUtil.NetCallback() {
                @Override
                public void doSuccess(String data) {
                    Toast.makeText(CreateWeiboActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    CreateWeiboActivity.this.finish();
                }

                @Override
                public void doFail(String message) {
                    Toast.makeText(CreateWeiboActivity.this,"发布失败，请稍后再试...",Toast.LENGTH_SHORT).show();
                }
            });
        }else if(item.getItemId() == R.id.action_create_weibo_cancel){
            this.finish();
        }else{
            this.finish();
        }

        return true;
    }
}
