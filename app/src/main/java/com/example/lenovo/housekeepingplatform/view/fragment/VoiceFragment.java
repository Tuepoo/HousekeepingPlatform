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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.OrderDetilActivity;
import com.example.lenovo.housekeepingplatform.activity.ServiceDetailActivity;
import com.example.lenovo.housekeepingplatform.adapter.VoiceAdapter;
import com.example.lenovo.housekeepingplatform.module.speech.ListData;
import com.example.lenovo.housekeepingplatform.module.speech.SpeechModel;
import com.example.lenovo.housekeepingplatform.network.http.RequestCenter;
import com.example.lenovo.housekeepingplatform.nlp.PartOfSpeech;
import com.example.lenovo.housekeepingplatform.nlp.TernarySearchTrie;
import com.example.lenovo.housekeepingplatform.nlp.WordToken;
import com.tuepoo.okhttp.listener.DisposeDataListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.lenovo.housekeepingplatform.module.speech.ListData.RECEIVER;
import static com.example.lenovo.housekeepingplatform.nlp.PartOfSpeech.vn;

/**
 * Created by lenovo on 2018/8/31.
 */

public class VoiceFragment extends BaseFragment implements View.OnClickListener{
    /**
     * UI
     */
    private View mContentView;
    private ImageView mSpeechView;
    private ListView mListView;
    private ImageView mLoadingView;
    private RelativeLayout mSpeechLayout;
    /**
     * data
     */
    private String res;//语音识别的结果
    private String result;
    private ListData listData;
    private List<ListData> lists;
    private VoiceAdapter mAdapter;
    private SpeechModel mSpeechData;
    private String title;
    private String price;


    //下面是百度语音用到的
    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    public VoiceFragment(){

    }

    private WaitPayBroadcastReceiver waitpayReceiver =
            new WaitPayBroadcastReceiver();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        registerBroadcast();
//        if (isNetworkConnect(mContext)){
//            mLoadingView.setVisibility(View.GONE);
//            mListView.setVisibility(View.VISIBLE);
//            mSpeechLayout.setVisibility(View.VISIBLE);
//            //刷新界面
//            refresh(getWelcomeTips(), RECEIVER);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    @Override
    public void onClick(View v) {

        //开启语音识别
//        Intent intent = new Intent("com.baidu.action.RECOGNIZE_SPEECH");
        Intent intent = new Intent(mContext,BaiduASRDigitalDialog.class);
        intent.putExtra("grammar", "asset:///baidu_speech_grammardemo.bsg"); // 设置离线的授权文件(离线模块需要授权), 该语法可以用自定义语义工具生成, 链接http://yuyin.baidu.com/asr#m5
        startActivityForResult(intent, 1);

    }



    //语音识别返回的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

            refresh(res, ListData.SEND);
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
                    if (word.type == PartOfSpeech.vn)
                        verbNoun.termText = word.termText;
                }
                //                tokens.add(word);
            }while(word!=null);
            //            ArrayList<TreeNode> depTrees = TreeBuilder.getDepTree(tokens);
            //            if(res.contains("不")){
            if(verb.termText.contains("找")||verb.termText.contains("看")){
                if(noun.termText.contains("清洁")||noun.termText.contains("保洁")) {
                    Intent cleanintent = new Intent(mContext, ServiceDetailActivity.class);
                    cleanintent.putExtra("SERVICE_TITLE", "日常保洁");
                    cleanintent.putExtra("SERVICE_SUB_TITLE", "家庭室内表面清洁、物品整理、基础除尘除垢");
                    cleanintent.putExtra("SERVICE_PIC", 4);
                    cleanintent.putExtra("SERVICE_TIME", "2小时起");
                    cleanintent.putExtra("SERVICE_PRICE", "35");
                    cleanintent.putExtra("FROM_VOICE",true);
                    mContext.startActivity(cleanintent);
                    String info = "为您跳转到日常保洁服务，服务包含家庭室内表面清洁、物品整理、基础除尘除垢，价格每小时35元，需两小时起预定，请问您是否需要预约该服务呢？";
                    refresh(info, RECEIVER);
                    speak(listData.getContent());
//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        public void run() {
//                            keeptalk();
//                        }
//                    }, 18000);
                }
                if (noun.termText.contains("订单")){
                    if (title!=null&&price!=null) {
                        Intent waitpayintent = new Intent(mContext, OrderDetilActivity.class);
                        waitpayintent.putExtra("SERVICE_TITLE", title);
                        waitpayintent.putExtra("SERVICE_PRICE", price);
                        mContext.startActivity(waitpayintent);
                    }else {
                        String info = "抱歉我没找到最新订单";
                        refresh(info,RECEIVER);
                        speak(listData.getContent());
                    }
                }
            }else {
                String info = "抱歉我没听懂";
                refresh(info,RECEIVER);
                speak(listData.getContent());
            }


//            refresh(res, ListData.SEND);
//            if (res.contains("保姆")){
//                String info = "为您查找到在您附近的一位保姆，她擅长日常保洁，厨房保养，卫生间保养，请问您是否需要找该保姆服务呢？";
//                refresh(info,RECEIVER);
//                speak(listData.getContent());
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask(){
//                    public void run(){
//                        keeptalk();
//                    }
//                },12000);
//                return;
//            }
//            if (res.contains("是")||res.contains("需要")){
//                String info = "已为您创建了新的订单，跟我说查看最新订单可查看到最新订单详情";
//                refresh(info,RECEIVER);
//                speak(listData.getContent());
//                return;
//            }
//            if (res.contains("最新订单")){
//                String info = "马上为您查找最新订单，请稍等";
//                refresh(info,RECEIVER);
//                speak(listData.getContent());
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask(){
//                    public void run(){
//                        Intent orderDetilIntent = new Intent(mContext, OrderDetilActivity.class);
//                        mContext.startActivity(orderDetilIntent);
//                    }
//                },4000);
//                return;
//            }
//
//            chat();
        }
    }

    private void keeptalk() {
        //连续语音识别
        Intent intent = new Intent(mContext,BaiduASRDigitalDialog.class);
        intent.putExtra("grammar", "asset:///baidu_speech_grammardemo.bsg"); // 设置离线的授权文件(离线模块需要授权), 该语法可以用自定义语义工具生成, 链接http://yuyin.baidu.com/asr#m5
        startActivityForResult(intent, 1);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_voice_layout,container,false);
        initview();
        initialEnv();
        initialTts();
        return mContentView;
    }

    private void initview(){
        mListView = (ListView) mContentView.findViewById(R.id.lv);
        mSpeechView = (ImageView)mContentView.findViewById(R.id.iv_send);
        mSpeechView.setOnClickListener(this);
       // mLoadingView = (ImageView)mContentView.findViewById(R.id.voice_loading_view);
        mSpeechLayout = (RelativeLayout)mContentView.findViewById(R.id.speechview);
//        AnimationDrawable anim = (AnimationDrawable)mLoadingView.getDrawable();
//        anim.start();
        lists = new ArrayList<ListData>();
        mAdapter  = new VoiceAdapter(lists,mContext);
        mListView.setAdapter(mAdapter);
        //刷新界面
        refresh(getWelcomeTips(), RECEIVER);
    }

    //聊天机器人
    private void chat() {
        String key = "9b23a5fe87ea4b82bc1077a78fbc82bc";
        // 去掉空格
        String dropk = res.replace(" ", "");
        // 去掉回车
        String droph = dropk.replace("\n", "");
        //api_key请换成自己图灵机器人的api_key
        RequestCenter.requestTuling(key,droph, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
//                Log.d(mSpeechData.intent.toString(), "onSuccess: ");
//                Toast.makeText(mContext, mSpeechData.intent.toString(), Toast.LENGTH_LONG).show();
                mSpeechData = (SpeechModel)responseObj;
//                for (ResultBodyValue mresultBodyValue:mSpeechData.results.list) {
//                    result = mresultBodyValue.values.text;
                    refresh(mSpeechData.text,RECEIVER);
                    //语音合成返回的结果
                    speak(listData.getContent());
//                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    //刷新页面
    private void refresh(String content,int flag) {
        listData = new ListData(content, flag);
        lists.add(listData);
        //如果item数量大于30，清空数据
        if (lists.size() > 30) {
            for (int i = 0; i < lists.size(); i++) {
                // 移除数据
                lists.remove(i);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    //用户第一次进入，获取欢迎语
    private String getWelcomeTips() {
        String welcome_tip = "欢迎使用语音助手";
        return welcome_tip;
    }

    //判断网络是否连接
//    private boolean isNetworkConnect(Context context) {
//        ConnectivityManager connMgr = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        Log.d("tag",networkInfo+"");
//
//        boolean isNetworkAvailable;
//        if (networkInfo==null){
//            isNetworkAvailable=false;
//        }else {
//            isNetworkAvailable=true;
//        }
//        Log.d("tag",isNetworkAvailable+"");
//        return isNetworkAvailable;
//    }


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
            Toast.makeText(mContext, "error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ", Toast.LENGTH_LONG).show();
        }
    }

    //下面都是百度语音合成的初始化设置，直接从demo里拷贝的
    private void initialEnv() {
//        if (mSampleDirPath == null) {
//            String sdcardPath = Environment.getExternalStorageDirectory().toString();
//            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
//        }
//        makeDir(mSampleDirPath);
//        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
//        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
//        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
//        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
//        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
//                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
//        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
//                + ENGLISH_SPEECH_MALE_MODEL_NAME);
//        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
//                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
//        this.mSpeechSynthesizer.setContext(mContext);
//        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
//            @Override
//            public void onSynthesizeStart(String s) {
//
//            }
//
//            @Override
//            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
//
//            }
//
//            @Override
//            public void onSynthesizeFinish(String s) {
//
//            }
//
//            @Override
//            public void onSpeechStart(String s) {
//
//            }
//
//            @Override
//            public void onSpeechProgressChanged(String s, int i) {
//
//            }
//
//            @Override
//            public void onSpeechFinish(String s) {
//
//            }
//
//            @Override
//            public void onError(String s, SpeechError speechError) {
//
//            }
//        });
//        // 文本模型文件路径 (离线引擎使用)
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
//                + TEXT_MODEL_NAME);
//        // 声学模型文件路径 (离线引擎使用)
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
//                + SPEECH_FEMALE_MODEL_NAME);
//        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
//        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
//                + LICENSE_FILE_NAME);
//        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
//        this.mSpeechSynthesizer.setAppId("10836968"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
//        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
//        this.mSpeechSynthesizer.setApiKey("hehu9tr5ZsOZaugPoPVbIE9U",
//                "9776416156f011c0be51af717f620711"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
//        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
//        // 设置Mix模式的合成策略
//        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
//        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
//        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
//        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
//
//        if (authInfo.isSuccess()) {
//            Toast.makeText(mContext, "语音合成授权成功", Toast.LENGTH_LONG).show();
//        } else {
//            String errorMsg = authInfo.getTtsError().getDetailMessage();
//            Toast.makeText(mContext, "语音合成授权失败，错误码:" + errorMsg, Toast.LENGTH_LONG).show();
//        }
//
//        // 初始化tts
//        mSpeechSynthesizer.initTts(TtsMode.MIX);
//        // 加载离线英文资源（提供离线英文合成功能）
//        //        int result =
//        //                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
//        //                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
//        //Toast.makeText(this,"loadEnglishModel result=" + result,Toast.LENGTH_LONG).show();
//
//        //打印引擎信息和model基本信息
//        //printEngineInfo();
//    }
//
//    /**
//     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
//     *
//     * @param isCover 是否覆盖已存在的目标文件
//     * @param source
//     * @param dest
//     */
//    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
//        File file = new File(dest);
//        if (isCover || (!isCover && !file.exists())) {
//            InputStream is = null;
//            FileOutputStream fos = null;
//            try {
//                is = getResources().getAssets().open(source);
//                String path = dest;
//                fos = new FileOutputStream(path);
//                byte[] buffer = new byte[1024];
//                int size = 0;
//                while ((size = is.read(buffer, 0, 1024)) >= 0) {
//                    fos.write(buffer, 0, size);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (fos != null) {
//                    try {
//                        fos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                try {
//                    if (is != null) {
//                        is.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private void registerBroadcast() {
        IntentFilter waitpayFilter =
                new IntentFilter(ServiceDetailActivity.WAITPAY_ACTION);
        LocalBroadcastManager.getInstance(mContext)
                .registerReceiver(waitpayReceiver, waitpayFilter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(mContext)
                .unregisterReceiver(waitpayReceiver);
    }

    /**
     * 接收mina发送来的消息，并更新UI
     */
    private class WaitPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新我们的fragment
            title = intent.getStringExtra("SERVICE_TITLE");
            price = intent.getStringExtra("SERVICE_PRICE");
        }
    }

}

