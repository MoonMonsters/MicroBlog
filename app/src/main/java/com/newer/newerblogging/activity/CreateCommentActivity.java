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
import com.newer.newerblogging.utils.NetConnectionUtil;

import butterknife.Bind;

/**
 * 写微博评论界面
 */
public class CreateCommentActivity extends BaseActivity {


    @Bind(R.id.et_create_comment_status)
    EditText etCreateCommentStatus;
    @Bind(R.id.cb_create_comment_choose)
    CheckBox cbCreateCommentChoose;

    private String mIdStr;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_create_comment;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mIdStr = intent.getStringExtra(Config.WEIBO_IDSTR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_create_comment,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_create_comment_send){
            String text = etCreateCommentStatus.getText().toString();
            boolean b = cbCreateCommentChoose.isSelected();

            NetConnectionUtil.netToCreateComments(this, mIdStr, text, b ? 1 : 0, new NetConnectionUtil.NetCallback() {
                @Override
                public void doSuccess(String data) {
                    Toast.makeText(CreateCommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                    CreateCommentActivity.this.finish();
                }

                @Override
                public void doFail(String message) {
                    Toast.makeText(CreateCommentActivity.this,"评论失败，请稍后再试。",Toast.LENGTH_SHORT).show();
                }
            });

        }else if(item.getItemId() == R.id.action_create_comment_cancel){
            this.finish();
        }else{
            this.finish();
        }

        return true;
    }
}
