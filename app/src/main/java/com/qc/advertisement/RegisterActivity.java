package com.qc.advertisement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class RegisterActivity extends Activity implements View.OnClickListener {
    String APPKEY = "1fae32056b428";
    String APPSECRETE = "e2ec23e95d2d1f6af8e4619d7f670f2c";
    @Bind(R.id.title_bar_text)
    TextView titleBarText;
    @Bind(R.id.title_bar_back)
    ImageButton titleBarBack;
    @Bind(R.id.next_commit_btn)
    Button nextCommitBtn;
    @Bind(R.id.rl_iphone)
    RelativeLayout rlIphone;
    @Bind(R.id.login_input_password_et)
    EditText loginInputPasswordEt;
    @Bind(R.id.login_input_repassword_et)
    EditText loginInputRepasswordEt;
    @Bind(R.id.login_hint)
    TextView loginHint;

    // 手机号输入框
    private EditText inputPhoneEt;
    // 验证码输入框
    private EditText inputCodeEt;
    // 获取验证码按钮

    // 注册按钮
    private Button commitBtn;
    //
    int i = 30;
    private boolean count = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        inputPhoneEt = (EditText) findViewById(R.id.login_input_phone_et);
        inputCodeEt = (EditText) findViewById(R.id.login_input_code_et);

        commitBtn = (Button) findViewById(R.id.login_commit_btn);
        titleBarText.setText("快速登录");
        titleBarBack.setVisibility(View.VISIBLE);

        nextCommitBtn.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
        // 启动短信验证sdk
        SMSSDK.initSDK(RegisterActivity.this, APPKEY, APPSECRETE);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        String phoneNums = inputPhoneEt.getText().toString();

        switch (v.getId()) {
            case R.id.next_commit_btn:
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                }
                count = true;
                // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                nextCommitBtn.setClickable(false);
                nextCommitBtn.setText("重新发送(" + i + ")");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();

                break;

            case R.id.login_commit_btn:
                if(!judgePhoneNums(phoneNums)){
                    loginHint.setText("*请输入11位手机号码");
                    loginHint.setVisibility(View.VISIBLE);
                    return;
                }
                if(!count){
                    loginHint.setText("*请点击按钮获取验证码");
                    loginHint.setVisibility(View.VISIBLE);
                    return;
                }
                if(TextUtils.isEmpty(inputCodeEt.getText().toString().trim())){

                    loginHint.setText("*请输入验证码");
                    loginHint.setVisibility(View.VISIBLE);
                    return;
                }
                if (!judgePassword()) {
                    return;
                }
                //将收到的验证码和手机号提交再次核对
                SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
                        .getText().toString());
                //createProgressBar();
                break;
        }
    }

    private Boolean judgePassword() {
        String password = loginInputPasswordEt.getText().toString().trim();
        String rePassword = loginInputRepasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            loginHint.setText("*密码不能为空，请重新输入");
            loginHint.setVisibility(View.VISIBLE);
            return false;
        }
        if (!password.equals(rePassword)) {
            loginInputRepasswordEt.setText("");
            loginHint.setText("*两次密码不相同，请重新输入");
            loginHint.setVisibility(View.VISIBLE);
            return false;
        }
        if (password.length() < 6 || rePassword.length() < 6) {
            loginHint.setText("*密码长度必须大于6小于20");
            loginHint.setVisibility(View.VISIBLE);
            return false;
        }
        String reg = "^[A-Za-z][A-Za-z0-9_-]+$";
        if(password.matches(reg) && password.matches(reg)){

        }else{
            loginHint.setText("*密码首位必须为字母");
            loginHint.setVisibility(View.VISIBLE);
            return false;
        }
       /* String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(reg);
        Matcher m1 = p.matcher(password);
        boolean b1 = m1.matches();
        Matcher m2 = p.matcher(password);
        boolean b2 = m2.matches();
        if (!b1) {
            loginHint.setText("*密码首位必须为字母");
            loginHint.setVisibility(View.VISIBLE);
            loginInputRepasswordEt.setText("");
            return false;
        }
        if (!b2) {
            loginHint.setText("*密码首位必须为字母");
            loginHint.setVisibility(View.VISIBLE);
            loginInputRepasswordEt.setText("");
            return false;
        }*/
        return true;
    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                nextCommitBtn.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                nextCommitBtn.setText("获取验证码");
                nextCommitBtn.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);

                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        loginHint.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,
                                IndexActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    loginHint.setText("*验证码错误");
                    loginHint.setVisibility(View.VISIBLE);
                    return ;
                }
            }
        }
    };
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        loginHint.setText("*手机号码输入有误！");
        loginHint.setVisibility(View.VISIBLE);
        return false;
    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
       /*
        * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
        * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @OnClick(R.id.title_bar_back)
    public void onViewClicked() {
        if (rlIphone.getVisibility() == View.GONE) {
            rlIphone.setVisibility(View.INVISIBLE);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (rlIphone.getVisibility() == View.GONE) {
            rlIphone.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }

    }
}
