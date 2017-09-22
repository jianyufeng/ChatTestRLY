package com.fule.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fule.myapplication.chatting.ui.ChattingFragment;
import com.fule.myapplication.common.Account;
import com.fule.myapplication.group.grouplist.GroupListActivity;

import io.rong.imlib.RongIMClient;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    Button account;Button account1;
    Button login;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = (Button) findViewById(R.id.account);
        account1 = (Button) findViewById(R.id.account1);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account:
                //jianyufeng
               token = (String) Account.JIANYUFENG.getmDefaultValue();
                break;
            case R.id.account1:
                //jianyufeng1
                token = (String) Account.JIANYUFENG1.getmDefaultValue();
                break;
            default:
                if (TextUtils.isEmpty(token)) {
                    Toast.makeText(LoginActivity.this, "空", Toast.LENGTH_SHORT).show();
                    return;
                }
        }
        connect(token);
    }
    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess---" + userid);
//                    startActivity(new Intent(LoginActivity.this, ChattingActivity.class).putExtra(ChattingFragment.RECIPIENTS,"jianyufeng1").putExtra(ChattingFragment.CONTACT_USER,"简玉锋1"));
                    startActivity(new Intent(LoginActivity.this, GroupListActivity.class).putExtra(ChattingFragment.RECIPIENTS,"jianyufeng1").putExtra(ChattingFragment.CONTACT_USER,"简玉锋1"));
//                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }
}
