package com.example.lenovo.housekeepingplatform.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.LoginActivity;
import com.example.lenovo.housekeepingplatform.activity.OrderDetilActivity;
import com.example.lenovo.housekeepingplatform.activity.ServiceDetailActivity;
import com.example.lenovo.housekeepingplatform.view.MoreImageView;

/**
 * Created by lenovo on 2018/8/31.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{
    /**
     * UI
     */
    private View mContentView;
    private TextView mNickname;
    private TextView mWaitPayNumberView;
    private TextView mWaitServiceNumberView;
    private MoreImageView mHeadview;
    private RelativeLayout mWaitPayLayout;
    private RelativeLayout mUserLayout;
    private LinearLayout mLevelLayout;
    private TextView mBalance;
    private TextView mPoint;
    private TextView mCoupon;
    private Button mSettingbutton;

    /**
     * data
     */
    //private PersonAdapter mAdapter;
    //private PersonModel mPersonData;
    private String title;
    private String price;

    /**
     * 负责处理接收到的mina消息
     */
//    private MinaReceiver mReceiver;


    //自定义了一个广播接收器接收新单单生成同志通知
    private WaitPayBroadcastReceiver waitpayReceiver =
            new WaitPayBroadcastReceiver();

    private PayBroadcastReceiver payReceiver =
            new PayBroadcastReceiver();

    private LoginBroadcastReceiver loginReceiver =
            new LoginBroadcastReceiver();

    public MineFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        registerBroadcast();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_mine_layout,container,false);
        initview();
        return mContentView;
    }

    private void initview(){
        mWaitPayNumberView = (TextView)mContentView.findViewById(R.id.buynumber_view);
        mWaitServiceNumberView = (TextView)mContentView.findViewById(R.id.servicenumber_view);
        mWaitPayLayout = (RelativeLayout)mContentView.findViewById(R.id.personal_order_waitpay_layout);
        mWaitPayLayout.setOnClickListener(this);
        mUserLayout = (RelativeLayout)mContentView.findViewById(R.id.header_relayout);
        mUserLayout.setOnClickListener(this);
        mHeadview = (MoreImageView) mContentView.findViewById(R.id.head_mimgv);
        mNickname = (TextView)mContentView.findViewById(R.id.nickname_txtv);
        mLevelLayout = (LinearLayout)mContentView.findViewById(R.id.level_layout);
        mBalance = (TextView)mContentView.findViewById(R.id.person_balance_txtv);
        mPoint = (TextView)mContentView.findViewById(R.id.person_point_txtv);
        mCoupon = (TextView)mContentView.findViewById(R.id.person_coupon_txtv);
    }

    private void registerBroadcast() {
        IntentFilter waitpayFilter =
                new IntentFilter(ServiceDetailActivity.WAITPAY_ACTION);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(waitpayReceiver, waitpayFilter);
        IntentFilter loginFilter =
                new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(loginReceiver, loginFilter);
        IntentFilter payFilter =
                new IntentFilter(OrderDetilActivity.PAY_ACTION);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(payReceiver, payFilter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(waitpayReceiver);
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(payReceiver);
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(loginReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_order_waitpay_layout:
                Intent waitpayintent = new Intent(mContext,OrderDetilActivity.class);
                waitpayintent.putExtra("SERVICE_TITLE",title);
                waitpayintent.putExtra("SERVICE_PRICE",price);
                mContext.startActivity(waitpayintent);
                break;
            case R.id.header_relayout:
                Intent loginintent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(loginintent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    /**
     * 接收mina发送来的消息，并更新UI
     */
    private class WaitPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新我们的fragment
            if (mWaitPayNumberView.getVisibility() == View.GONE) {
//                String
                mWaitPayNumberView.setVisibility(View.VISIBLE);
//                mWaitPayNumberView.setText(intent.getStringExtra());
                title = intent.getStringExtra("SERVICE_TITLE");
                price = intent.getStringExtra("SERVICE_PRICE");
            }

        }
    }

    private class PayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新我们的fragment
            mWaitPayNumberView.setVisibility(View.GONE);
            mWaitServiceNumberView.setVisibility(View.VISIBLE);
        }
    }

    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新我们的fragment
            mLevelLayout.setVisibility(View.VISIBLE);
            mNickname.setText("13415634743");
            mHeadview.setImageDrawable(mContext.getDrawable(R.drawable.img_head));
            mBalance.setText("10");
            mPoint.setText("100");
            mCoupon.setText("5");
        }
    }

}
