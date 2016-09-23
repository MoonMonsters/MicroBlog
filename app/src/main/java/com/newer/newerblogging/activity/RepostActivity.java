package com.newer.newerblogging.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.newer.newerblogging.R;
import com.newer.newerblogging.base.BaseActivity;
import com.newer.newerblogging.utils.Config;
import com.newer.newerblogging.utils.LoggerUtil;
import com.newer.newerblogging.utils.NetConnectionUtil;

import butterknife.Bind;

/**
 * 转发微博评论界面
 */
public class RepostActivity extends BaseActivity {


    @Bind(R.id.et_repost_status)
    EditText etRepostStatus;
    @Bind(R.id.cb_repost_choose)
    CheckBox cbRepostChoose;

    private String mIdStr;
    private String mText;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_repost;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mIdStr = intent.getStringExtra(Config.WEIBO_IDSTR);
        mText = intent.getStringExtra(Config.WEIBO_CONTENT);

        etRepostStatus.setText(mText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_repost,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_repost_send){
            String text = etRepostStatus.getText().toString();
            boolean b = cbRepostChoose.isSelected();
            int id_comment = 0;
            if(b){
                id_comment = 3;
            }
            NetConnectionUtil.netToRepostStatus(this, mIdStr, text, id_comment, new NetConnectionUtil.NetCallback() {
                @Override
                public void doSuccess(String data) {
                    LoggerUtil.i("TAG","转发成功");
                    RepostActivity.this.finish();
                }

                @Override
                public void doFail(String message) {
                    Toast.makeText(RepostActivity.this,"转发失败",Toast.LENGTH_SHORT).show();
                }
            });
        }else if(item.getItemId() == R.id.action_repost_cancel){
            RepostActivity.this.finish();
        }

        return true;
    }
}
