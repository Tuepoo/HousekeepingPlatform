package com.example.lenovo.housekeepingplatform.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.example.lenovo.housekeepingplatform.R;
import com.example.lenovo.housekeepingplatform.activity.base.BaseActivity;
import com.example.lenovo.housekeepingplatform.nlp.PartOfSpeech;
import com.example.lenovo.housekeepingplatform.nlp.TernarySearchTrie;
import com.example.lenovo.housekeepingplatform.nlp.WordToken;
import com.example.lenovo.housekeepingplatform.view.fragment.ClassifyFragment;
import com.example.lenovo.housekeepingplatform.view.fragment.HomeFragment;
import com.example.lenovo.housekeepingplatform.view.fragment.MineFragment;
import com.example.lenovo.housekeepingplatform.view.fragment.VoiceFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private boolean speek_one = false;
    private boolean islogined = false;

    private FragmentManager fm;
    private HomeFragment mHomeFragment;
    private Fragment mCommonFragmentOne;
    private VoiceFragment mVoiceFragment;
    private ClassifyFragment mShopcarFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrent;

    private RelativeLayout mHomeLayout;
    private RelativeLayout mVoiceLayout;
    private RelativeLayout mShopcarLayout;
    private RelativeLayout mMineLayout;
    private TextView mHomeView;
    private TextView mVoiceView;
    private TextView mShopcarView;
    private TextView mMineView;

    //下面是百度语音用到的
    private SpeechSynthesizer mSpeechSynthesizer;
    private String mSampleDirPath;
    public String res;//语音识别的结果
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";


    private LoginBroadcastReceiver loginReceiver =
            new LoginBroadcastReceiver();


    private class LoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            islogined = true;
        }
    }

    private void registerBroadcast() {
        IntentFilter loginFilter =
                new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(loginReceiver, loginFilter);
    }

    private void unregisterBroadcast() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(loginReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(R.color.color_0b988f);
        setContentView(R.layout.activity_home_layout);
        initview();
        initialEnv();
        initialTts();
        mHomeFragment = new HomeFragment();
        fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout,mHomeFragment);
        fragmentTransaction.commit();
        registerBroadcast();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask(){
//            public void run(){
//                keeptalk();
//            }
//        },7000);
    }

    private void keeptalk() {
        //连续语音识别
//        Intent intent = new Intent("com.baidu.action.RECOGNIZE_SPEECH");
        Intent intent = new Intent(this,BaiduASRDigitalDialog.class);
        intent.putExtra("grammar", "asset:///baidu_speech_grammardemo.bsg"); // 设置离线的授权文件(离线模块需要授权), 该语法可以用自定义语义工具生成, 链接http://yuyin.baidu.com/asr#m5
        startActivityForResult(intent, 1);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (islogined) {
            if (!speek_one) {
                speak("欢迎来到语音家政软件，请问是否需要通过语音进行操作？");
                speek_one = true;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        keeptalk();
                    }
                }, 10000);
                //keeptalk();
            }
        }
    }



    //语音识别返回的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case REQUEST_CITYCODE:
//                if (resultCode == 1110) {
//                    super.onActivityResult(requestCode, resultCode, data);
//                }
//                break;
//            case :
//        }
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
            WordToken verb = new WordToken("",PartOfSpeech.vn);
            WordToken noun = new WordToken("",PartOfSpeech.d);
//            ArrayList<WordToken> tokens = new ArrayList<WordToken>();
//            tokens.clear();
            do {

                word = Seg.nextWord();
                if (word!=null) {
                    if (word.type == PartOfSpeech.vn)
                        verb.termText = word.termText;
                    if (word.type == PartOfSpeech.d)
                        noun.termText = word.termText;
                }
//                tokens.add(word);
            }while(word!=null);
//            ArrayList<TreeNode> depTrees = TreeBuilder.getDepTree(tokens);
//            if(res.contains("不")){
            if(noun.termText.equals("不")&&verb.termText.equals("需要")){

            }else if (noun.termText.equals("")&&verb.termText.equals("需要")) {
                onClick(mVoiceLayout);
            }else {
                speak("抱歉我没听懂，请点击语音按钮进入语音助手");
            }
        }
    }

    private void initview(){

        mHomeLayout = (RelativeLayout)findViewById(R.id.home_layout_view);
        mHomeLayout.setOnClickListener(this);
        mVoiceLayout =(RelativeLayout) findViewById(R.id.pond_layout_view);
        mVoiceLayout.setOnClickListener(this);
        mShopcarLayout = (RelativeLayout)findViewById(R.id.message_layout_view);
        mShopcarLayout.setOnClickListener(this);
        mMineLayout = (RelativeLayout) findViewById(R.id.mine_layout_view);
        mMineLayout.setOnClickListener(this);

        mHomeView = (TextView) findViewById(R.id.home_image_view);
        mVoiceView = (TextView) findViewById(R.id.fish_image_view);
        mShopcarView = (TextView) findViewById(R.id.message_image_view);
        mMineView = (TextView) findViewById(R.id.mine_image_view);
        mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    private void hideFragment(Fragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.home_layout_view:
                changeStatusBarColor(R.color.color_0b988f);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home_selected);
                mVoiceView.setBackgroundResource(R.drawable.comui_tab_pond);
                mShopcarView.setBackgroundResource(R.drawable.comui_tab_message);
                mMineView.setBackgroundResource(R.drawable.comui_tab_mine);

                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mVoiceFragment, fragmentTransaction);
                hideFragment(mShopcarFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, mHomeFragment);
                } else {
                    mCurrent = mHomeFragment;
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.message_layout_view:
                changeStatusBarColor(R.color.color_0b988f);
                mShopcarView.setBackgroundResource(R.drawable.comui_tab_message_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mVoiceView.setBackgroundResource(R.drawable.comui_tab_pond);
                mMineView.setBackgroundResource(R.drawable.comui_tab_mine);

                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mVoiceFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mShopcarFragment == null) {
                    mShopcarFragment = new ClassifyFragment();
                    fragmentTransaction.add(R.id.content_layout, mShopcarFragment);
                } else {
                    mCurrent = mShopcarFragment;
                    fragmentTransaction.show(mShopcarFragment);
                }
                break;
            case R.id.mine_layout_view:
                changeStatusBarColor(R.color.color_0b988f);
                mMineView.setBackgroundResource(R.drawable.comui_tab_person_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mVoiceView.setBackgroundResource(R.drawable.comui_tab_pond);
                mShopcarView.setBackgroundResource(R.drawable.comui_tab_message);
                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mVoiceFragment, fragmentTransaction);
                hideFragment(mShopcarFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
            case R.id.pond_layout_view:
                changeStatusBarColor(R.color.color_0b988f);
                mVoiceView.setBackgroundResource(R.drawable.comui_tab_pond_selected);
                mHomeView.setBackgroundResource(R.drawable.comui_tab_home);
                mMineView.setBackgroundResource(R.drawable.comui_tab_mine);
                mShopcarView.setBackgroundResource(R.drawable.comui_tab_message);
                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                hideFragment(mShopcarFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                if (mVoiceFragment == null) {
                    mVoiceFragment = new VoiceFragment();
                    fragmentTransaction.add(R.id.content_layout, mVoiceFragment);
                } else {
                    mCurrent = mVoiceFragment;
                    fragmentTransaction.show(mVoiceFragment);
                }
                break;
        }

        fragmentTransaction.commit();
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

    //下面都是百度语音合成的初始化设置，直接从demo里拷贝的
    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {

            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

            }

            @Override
            public void onSynthesizeFinish(String s) {

            }

            @Override
            public void onSpeechStart(String s) {

            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {

            }

            @Override
            public void onSpeechFinish(String s) {

            }

            @Override
            public void onError(String s, SpeechError speechError) {

            }
        });
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("10836968"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("hehu9tr5ZsOZaugPoPVbIE9U",
                "9776416156f011c0be51af717f620711"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
//            Toast.makeText(this, "语音合成授权成功", Toast.LENGTH_LONG).show();
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
//            Toast.makeText(this, "语音合成授权失败，错误码:" + errorMsg, Toast.LENGTH_LONG).show();
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        //        int result =
        //                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
        //                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        //Toast.makeText(this,"loadEnglishModel result=" + result,Toast.LENGTH_LONG).show();

        //打印引擎信息和model基本信息
        //printEngineInfo();
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
