package com.example.lenovo.housekeepingplatform.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.base.BaseActivity;
import com.example.lenovo.housekeepingplatform.view.CommonDialog;
import com.example.lenovo.housekeepingplatform.view.associatemail.MailBoxAssociateView;

/**
 * Created by lenovo on 2019/4/22.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    //自定义登陆广播Action
    public static final String LOGIN_ACTION = "action.LOGIN_ACTION";

    /**
     * UI
     */
    private MailBoxAssociateView mUserNameAssociateView;
    private EditText mPasswordView;
    private ImageView mLoginView;
    ImageView mImageView1;
    ImageView mImageView2;
    ImageView mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();

        initViews();
        startAnimation();

    }

    private void initViews() {
        mImageView1 = (ImageView) findViewById(R.id.image1);
        mImageView2 = (ImageView) findViewById(R.id.image2);
        mImageView3 = (ImageView) findViewById(R.id.image3);
        mUserNameAssociateView = (MailBoxAssociateView)findViewById(R.id.associate_email_input);
        mPasswordView =  (EditText)findViewById(R.id.login_input_password);
        mLoginView = (ImageView)findViewById(R.id.btn_login);
        mLoginView.setOnClickListener(this);
    }


    private void startAnimation() {
        ObjectAnimator anim1 = new ObjectAnimator().ofFloat(mImageView1, "alpha", 0.7f, 0f).setDuration(5000);
        ObjectAnimator anim2 = new ObjectAnimator().ofFloat(mImageView2, "alpha", 0f, 0.7f).setDuration(5000);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1, anim2);

        ObjectAnimator anim3 = new ObjectAnimator().ofFloat(mImageView2, "alpha", 0.7f, 0f).setDuration(5000);
        ObjectAnimator anim4 = new ObjectAnimator().ofFloat(mImageView3, "alpha", 0f, 0.7f).setDuration(5000);
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(anim3, anim4);

        ObjectAnimator anim5 = new ObjectAnimator().ofFloat(mImageView3, "alpha", 0.7f, 0f).setDuration(5000);
        ObjectAnimator anim6 = new ObjectAnimator().ofFloat(mImageView1, "alpha", 0f, 0.7f).setDuration(5000);
        AnimatorSet set2 = new AnimatorSet();
        set2.playTogether(anim5, anim6);

        AnimatorSet set3 = new AnimatorSet();
        set3.playSequentially(set, set1, set2);
        set3.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled;

            @Override
            public void onAnimationStart(Animator animation) {
                mCanceled = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCanceled) {
                    animation.start();
                }
            }

        });
        set3.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    //发送登陆请求
    private void login() {
        String userName = mUserNameAssociateView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            return;
        }

        if (TextUtils.isEmpty(password)) {
            return;
        }

        if(userName.equals("13415634743") && password.equals("12345")){
            sendLoginBroadcast();
            finish();//销毁当前登陆页面
        }else {
            final CommonDialog dialog = new CommonDialog(this);
            //对话框中退出按钮事件监听
            dialog.setButtonSure(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果对话框处于显示状态
                    if (dialog.isShowing()) {
                        dialog.dismiss();                     //关闭对话框
                    }
                }
            });
            dialog.show();//实例化自定义对话框
        }
    }

    private void sendLoginBroadcast(){
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGIN_ACTION));
    }

}
