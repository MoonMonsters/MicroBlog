package com.newer.newerblogging.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.newer.newerblogging.utils.AccessTokenKeeper;
import com.newer.newerblogging.utils.Constants;
import com.newer.newerblogging.utils.NetConnectionUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 拉取授权界面
 */
public class MicroblogOauthActivity extends AppCompatActivity {

    AuthInfo mAuthInfo;
    Oauth2AccessToken mOauth2AccessToken;
    SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(this,MainActivity.class));

//        判断是否已经登录过，如果登录过，就不再进入微博的登录界面
        if(!TextUtils.isEmpty(AccessTokenKeeper.readAccessToken(this).getUid())){
            intoMainActivity();

            finish();
            return ;
        }

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this,mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    class AuthListener implements WeiboAuthListener{

        //授权成功，获得token
        @Override
        public void onComplete(Bundle bundle) {
            mOauth2AccessToken = Oauth2AccessToken.parseAccessToken(bundle);

            //如果授权成功
            if(mOauth2AccessToken.isSessionValid()){
                AccessTokenKeeper.writeAccessToken(MicroblogOauthActivity.this, mOauth2AccessToken);

                NetConnectionUtil.netGetLocalUserInfo(MicroblogOauthActivity.this,mOauth2AccessToken.getUid());

                intoMainActivity();
            }else{
                String code = bundle.getString("code", "");
                Toast.makeText(MicroblogOauthActivity.this,code,Toast.LENGTH_SHORT).show();
                MicroblogOauthActivity.this.finish();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Log.i("TAG",e.getMessage());
        }

        @Override
        public void onCancel() {
            Log.i("TAG","onCancel");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 进入主界面
     */
    private void intoMainActivity(){

        startActivity(new Intent(MicroblogOauthActivity.this,MainActivity.class));
    }
}