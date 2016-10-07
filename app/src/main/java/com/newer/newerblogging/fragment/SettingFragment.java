package com.newer.newerblogging.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newer.newerblogging.R;
import com.newer.newerblogging.activity.MicroblogOauthActivity;
import com.newer.newerblogging.application.NewerApplication;
import com.newer.newerblogging.base.BaseFragment;
import com.newer.newerblogging.db.DeleteFromTable;
import com.newer.newerblogging.utils.AccessTokenKeeper;

import butterknife.Bind;

public class SettingFragment extends BaseFragment {

    @Bind(R.id.tv_setting_clear)
    TextView tvSettingClear;
    @Bind(R.id.btn_setting_exit)
    Button btnSettingExit;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initListener();
    }

    private void initListener() {
        btnSettingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessTokenKeeper.clear(getContext());
                getContext().startActivity(new Intent(getActivity(), MicroblogOauthActivity.class));
                getActivity().finish();
            }
        });

        tvSettingClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(NewerApplication.getInstance()).clearDiskCache();
                    }
                }).start();
                //清空Glide缓存数据
                Glide.get(NewerApplication.getInstance()).clearMemory();
                //清空表格数据
                DeleteFromTable.clearMicroblog();
                Toast.makeText(getContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}