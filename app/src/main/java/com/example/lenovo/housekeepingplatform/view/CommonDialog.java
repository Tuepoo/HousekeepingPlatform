package com.example.lenovo.housekeepingplatform.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.housekeepingplatform.R;

/**
 * Created by lenovo on 2019/4/22.
 */

public class CommonDialog extends Dialog {
    private Button button; //获取布局文件中的组件
    private TextView textViewTitle,textViewContent;
    public CommonDialog(Context context) {
        super(context, R.style.mdialog);
        //采用LayoutInflater方法作用是将layout的xml布局文件实例化为View类对象。
        View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_common_layout,null);
        //实例化组件，其实用到的只有一个buntton组件
        textViewTitle=view.findViewById(R.id.textTitle);
        textViewContent=view.findViewById(R.id.textContent);
        button=view.findViewById(R.id.buttonExit);
        setContentView(view);
    }
    //为button组件添加监听
    public void setButtonSure(View.OnClickListener listener){
        button.setOnClickListener(listener);

    }

}
