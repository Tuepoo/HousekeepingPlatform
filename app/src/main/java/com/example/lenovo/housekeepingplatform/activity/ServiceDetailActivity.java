package com.example.lenovo.housekeepingplatform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.base.BaseActivity;
import com.example.lenovo.housekeepingplatform.adapter.ServiceCommentAdapter;
import com.example.lenovo.housekeepingplatform.module.service.ServiceCommentValue;
import com.example.lenovo.housekeepingplatform.module.service.ServiceHeaderValue;
import com.example.lenovo.housekeepingplatform.module.service.ServiceModel;
import com.example.lenovo.housekeepingplatform.nlp.PartOfSpeech;
import com.example.lenovo.housekeepingplatform.nlp.TernarySearchTrie;
import com.example.lenovo.housekeepingplatform.nlp.WordToken;
import com.example.lenovo.housekeepingplatform.view.service.ServiceDetailHeaderView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.lenovo.housekeepingplatform.nlp.PartOfSpeech.vn;

/**
 * Created by lenovo on 2019/3/31.
 */

public class ServiceDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //自定义创建订单广播Action
    public static final String WAITPAY_ACTION = "com.example.lenovo.housekeepingplatform.action.WAITPAY_ACTION";


    public static String SERVICE_TITLE = "SERVICE_TITLE";
    public static String SERVICE_TIME = "SERVICE_TIME";
    public static String SERVICE_PRICE = "SERVICE_PRICE";
    public static String SERVICE_SUB_TITLE = "SERVICE_SUB_TITLE";
    public static String SERVICE_PIC = "SERVICE_PIC";
    public static String FROM_VOICE = "FROM_VOICE";

    /**
     * UI
     */
    private ImageView mBackView;
    private ListView mListView;
    private ImageView mLoadingView;
    private RelativeLayout mBottomLayout;
    private TextView mBookView;
    private TextView mWaitBuyView;
    private ServiceDetailHeaderView headerView;
    private ServiceCommentAdapter mAdapter;
    /**
     * Data
     */
    private String mServiceTitle;
    private String mServiceTime;
    private String mServicePrice;
    private String mServiceSubTitle;
    private int mServicePic;
    private ServiceModel mData;
    private String tempHint = "";
    private Boolean fromVoice = false;
    private String res;//语音识别的结果
    private SpeechSynthesizer mSpeechSynthesizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail_layout);
        initData();
        initView();
        initialTts();
        if (fromVoice) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    keeptalk();
                }
            }, 18000);
        }


//        requestDeatil();
    }

    private void keeptalk() {
        //连续语音识别
        //        Intent intent = new Intent("com.baidu.action.RECOGNIZE_SPEECH");
        Intent intent = new Intent(this,BaiduASRDigitalDialog.class);
        intent.putExtra("grammar", "asset:///baidu_speech_grammardemo.bsg"); // 设置离线的授权文件(离线模块需要授权), 该语法可以用自定义语义工具生成, 链接http://yuyin.baidu.com/asr#m5
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
        initView();
        initialTts();


    }
//        requestDeatil();


    //初始化数据
    private void initData() {
        mData = new ServiceModel();
        mData.body = new ServiceCommentValue();
        mData.head = new ServiceHeaderValue();
        Intent intent = getIntent();
        mServiceTitle = intent.getStringExtra(SERVICE_TITLE);
        mData.body.title = mServiceTitle;
        mData.head.title = mServiceTitle;
        mServiceTime = intent.getStringExtra(SERVICE_TIME);
        mData.body.time = mServiceTime;
        mServicePrice = intent.getStringExtra(SERVICE_PRICE);
        mData.body.price = mServicePrice;
        mServiceSubTitle = intent.getStringExtra(SERVICE_SUB_TITLE);
        mData.head.sub_title = mServiceSubTitle;
        mServicePic = intent.getIntExtra(SERVICE_PIC,0);
        mData.head.pic = mServicePic;
        fromVoice = intent.getBooleanExtra(FROM_VOICE,false);
    }

    //初始化数据
    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.comment_list_view);
        //mListView.setOnItemClickListener(this);
//        mListView.setVisibility(View.GONE);
//        mLoadingView = (ImageView) findViewById(R.id.loading_view);
//        mLoadingView.setVisibility(View.VISIBLE);
//        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
//        anim.start();
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mBookView = (TextView) findViewById(R.id.book_view);
        mBookView.setOnClickListener(this);
        mWaitBuyView = (TextView)findViewById(R.id.buynumber_view);
//        mSendView.setOnClickListener(this);
//        mBottomLayout.setVisibility(View.GONE);
//        intoEmptyState();
//        mLoadingView.setVisibility(View.GONE);
//        mListView.setVisibility(View.VISIBLE);
        mAdapter = new ServiceCommentAdapter(this, mData.body);
        mListView.setAdapter(mAdapter);
        if (headerView != null) {
            mListView.removeHeaderView(headerView);
        }
        headerView = new ServiceDetailHeaderView(this, mData.head);
        mListView.addHeaderView(headerView);

    }

//    private void requestDeatil() {
//
//        com.example.lenovo.housekeepingplatform.network.http.RequestCenter.requestServiceDetail(mServiceID, new DisposeDataListener() {
//            @Override
//            public void onSuccess(Object responseObj) {
//                mData = (ServiceModel) responseObj;
//                updateUI();
//            }
//
//            @Override
//            public void onFailure(Object reasonObj) {
//
//            }
//        });
//    }

    //根据数据填充UI
    private void updateUI() {
        mLoadingView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapter = new ServiceCommentAdapter(this, mData.body);
        mListView.setAdapter(mAdapter);
        if (headerView != null) {
            mListView.removeHeaderView(headerView);
        }
        headerView = new ServiceDetailHeaderView(this, mData.head);
        mListView.addHeaderView(headerView);
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle results = data.getExtras();
            ArrayList<String> results_recognition = results.getStringArrayList("results_recognition");

            //将数组形式的识别结果变为正常的String类型，例：[给张三打电话]变成给张三打电话
            String str = results_recognition + "";
            if (str.contains(","))
                res = str.substring(str.indexOf("[") + 1, str.indexOf(","));
            else
                res = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
            TernarySearchTrie Seg = new TernarySearchTrie(res);
            WordToken word;
            WordToken verb = new WordToken("", PartOfSpeech.v);
            WordToken noun = new WordToken("",PartOfSpeech.n);
            WordToken verbNoun = new WordToken("", vn);
            //            ArrayList<WordToken> tokens = new ArrayList<WordToken>();
            //            tokens.clear();
            do {

                word = Seg.nextWord();
                if (word!=null) {
                    if (word.type == PartOfSpeech.v)
                        verb.termText = word.termText;
                    if (word.type == PartOfSpeech.n)
                        noun.termText = word.termText;
                    if (word.type == vn)
                        verbNoun.termText = word.termText;
                }
                //                tokens.add(word);
            }while(word!=null);
            if(verbNoun.termText.contains("需要")||verbNoun.termText.contains("是")){
                Intent bookintent = new Intent(this,OrderDetilActivity.class);
                bookintent.putExtra("SERVICE_TITLE",mData.body.title);
                bookintent.putExtra("SERVICE_PRICE",mData.body.price);
                startActivity(bookintent);
                sendWaitPayBroadcast();
                speak("已为您创建新的订单");
                finish();
            }
        }
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
    }

    //语音合成
    private void speak(String text) {
        //        String text = this.mInput.getText().toString();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        //        if (TextUtils.isEmpty(mInput.getText())) {
        //            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
        //            mInput.setText(text);
        //        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            Toast.makeText(this, "error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int cursor = position - mListView.getHeaderViewsCount();
//        if (cursor >= 0 && cursor < mAdapter.getCommentCount()) {
//            if (UserManager.getInstance().hasLogined()) {
//                CourseCommentValue value = (CourseCommentValue) mAdapter.getItem(
//                        position - mListView.getHeaderViewsCount());
//                if (value.userId.equals(UserManager.getInstance().getUser().data.userId)) {
//                    //自己的评论不能回复
//                    intoEmptyState();
//                    Toast.makeText(this, "不能回复自己!", Toast.LENGTH_SHORT).show();
//                } else {
//                    //不是自己的评论，可以回复
//                    tempHint = getString(R.string.comment_hint_head).concat(value.name).
//                            concat(getString(R.string.comment_hint_footer));
//                    intoEditState(tempHint);
//                }
//            } else {
//                startActivity(new Intent(this, LoginActivity.class));
//            }
//        }
    }

    /**
     * EditText进入编辑状态
     */
    private void intoEditState(String hint) {
//        mInputEditView.requestFocus();
//        mInputEditView.setHint(hint);
//        Util.showSoftInputMethod(this, mInputEditView);
    }

    public void intoEmptyState() {
//        tempHint = "";
//        mInputEditView.setText("");
//        mInputEditView.setHint(getString(R.string.input_comment));
//        Util.hideSoftInputMethod(this, mInputEditView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
            case R.id.book_view:
                Intent bookintent = new Intent(this,OrderDetilActivity.class);
                bookintent.putExtra("SERVICE_TITLE",mData.body.title);
                bookintent.putExtra("SERVICE_PRICE",mData.body.price);
                startActivity(bookintent);
                sendWaitPayBroadcast();
                finish();
//                String comment = mInputEditView.getText().toString().trim();
//                if (UserManager.getInstance().hasLogined()) {
//                    if (!TextUtils.isEmpty(comment)) {
//                        mAdapter.addComment(assembleCommentValue(comment));
//                        intoEmptyState();
//                    }
//                } else {
//                    startActivity(new Intent(this, LoginActivity.class));
//                }

                break;
//            case R.id.jianpan_view:
//                mInputEditView.requestFocus();
//                Util.showSoftInputMethod(this, mInputEditView);
//                break;
        }
    }

    //向整个应用发送登陆广播事件
    private void sendWaitPayBroadcast() {
        Intent intent= new Intent(WAITPAY_ACTION);
        intent.putExtra("SERVICE_TITLE",mData.body.title);
        intent.putExtra("SERVICE_PRICE",mData.body.price);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * 组装CommentValue对象
     *
     * @return
     */
//    private CourseCommentValue assembleCommentValue(String comment) {
//        User user = UserManager.getInstance().getUser();
//        CourseCommentValue value = new CourseCommentValue();
//        value.name = user.data.name;
//        value.logo = user.data.photoUrl;
//        value.userId = user.data.userId;
//        value.type = 1;
//        value.text = tempHint + comment;
//        return value;
//    }

}
