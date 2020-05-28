package com.example.lenovo.housekeepingplatform.activity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.base.BaseActivity;
import com.example.lenovo.housekeepingplatform.module.service.ServiceCommentValue;

import java.util.Date;

/**
 * Created by lenovo on 2018/9/3.
 */

public class OrderDetilActivity extends BaseActivity implements View.OnClickListener{

    public static final String PAY_ACTION = "com.example.lenovo.housekeepingplatform.action.PAY_ACTION";

    /**
     * UI
     *
     */
    private TextView mOrderDetilBackBtn;
    private TextView mOrderDetilServiceTitle;
    private TextView mOrderDetilGoodPrice;
    private TextView mOrderDetilGoodAmount;
    private TextView mOrderDetilGoodPay;
    private TextView mOrderDetilBuyTime;
    private TextView mPayBtn;
    private FrameLayout mOrderDetilServiceDetil;

    /**
     * DATA
     *
     */
    private ServiceCommentValue mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(R.color.color_0b988f);
        setContentView(R.layout.order_detil_layout);
        initview();
        initdata();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initview();
        initdata();
    }

    private void initview(){
        mOrderDetilBackBtn = (TextView)findViewById(R.id.order_detil_titlebar_back_btn);
        mOrderDetilBackBtn.setOnClickListener(this);
        mOrderDetilServiceTitle = (TextView)findViewById(R.id.order_product_count_txtv);
        mOrderDetilServiceDetil = (FrameLayout)findViewById(R.id.order_product_flayout);
        mOrderDetilServiceDetil.setOnClickListener(this);
        mOrderDetilGoodPrice = (TextView)findViewById(R.id.fee_goodsfee_txtv);
        mOrderDetilGoodAmount = (TextView)findViewById(R.id.fee_amount_txtv);
        mOrderDetilGoodPay = (TextView)findViewById(R.id.fee_balance_txtv);
        mOrderDetilBuyTime = (TextView)findViewById(R.id.buy_time_txtv);
        mPayBtn = (TextView)findViewById(R.id.pay_btn);
        mPayBtn.setOnClickListener(this);
    }

    private void initdata(){
        Intent intent = getIntent();
        mOrderDetilServiceTitle.setText(intent.getStringExtra("SERVICE_TITLE"));
        mOrderDetilGoodPrice.setText("¥"+intent.getStringExtra("SERVICE_PRICE"));
        int amount = Integer.parseInt(intent.getStringExtra("SERVICE_PRICE"))-10;
        mOrderDetilGoodPay.setText("¥"+amount);
        mOrderDetilGoodAmount.setText("待付款:¥"+amount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        mOrderDetilBuyTime.setText("下单时间："+simpleDateFormat.format(date));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_detil_titlebar_back_btn:
                finish();
                break;
            case R.id.pay_btn:
                sendWaitPayBroadcast();
                finish();
        }
    }

    //向整个应用发送登陆广播事件
    private void sendWaitPayBroadcast() {
        Intent intent= new Intent(PAY_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
