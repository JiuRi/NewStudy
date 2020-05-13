package com.mingpao.xuexi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity {

    // private String userToken =   直接通过api  获取

    private String title = "推送的标题";
    private String body = "推送的内容  推送的内容 ";
    private String color = "#ffcc0000";
    private String sound = "defoult";
    private String click_action = "com.mingpao.xuexi.push";
    private String icon = "jl";
    private String image = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3880341262,3308316348&fm=26&gp=0.jpg";
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTittle = findViewById(R.id.toolbar_tittle);
        mHandler = new Handler();
        final EditText et_title = findViewById(R.id.et_title);
        final   EditText et_body = findViewById(R.id.et_body);
        final EditText et_sound = findViewById(R.id.et_sound);
        final EditText et_color = findViewById(R.id.et_color);
        final  EditText et_icon = findViewById(R.id.et_icon);
        final EditText et_image = findViewById(R.id.et_image);
        final  EditText et_clickaction = findViewById(R.id.et_clickaction);

        et_title.setText(title);
        et_body.setText(body);
        et_sound.setText(sound);
        et_color.setText(color);
        et_icon.setText(icon);
        et_image.setText(image);
        et_clickaction.setText(click_action);

        toolbarTittle.setText("测试 FireBase Message ");
        setSupportActionBar(toolbar);




        findViewById(R.id.btn_push_f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pushMessage(et_clickaction, et_title, et_body, et_sound, et_color, et_icon, et_image);


            }
        });


        findViewById(R.id.btn_push_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pushMessage(et_clickaction, et_title, et_body, et_sound, et_color, et_icon, et_image);
                    }
                } ,2000);

                finish();
            }
        });

    }

    private void pushMessage(EditText et_clickaction, EditText et_title, EditText et_body, EditText et_sound, EditText et_color, EditText et_icon, EditText et_image) {

        title = et_title.getText().toString().trim();
        body = et_body.getText().toString().trim();
        sound = et_sound.getText().toString().trim();
        color = et_color.getText().toString().trim();
        icon = et_icon.getText().toString().trim();
        image = et_image.getText().toString().trim();
        click_action = et_clickaction.getText().toString().trim();


        String pushMessage = DataUtil.getPushMessage(title, body, color, sound, click_action, icon, image);
        final RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), pushMessage);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/") //设置网络请求的Url地址
                .addConverterFactory(ScalarsConverterFactory.create()) //设置数据解析器
                .build();
        APi aPi = retrofit.create(APi.class);
        final Call<ResponseBody> call = aPi.pushMessage(body);
        final Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Toast.makeText(MainActivity.this,"消息发送成功",Toast.LENGTH_SHORT).show();
                    Log.e("ffff", "onResponse: __________________"+response.body().string() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"消息发送失败",Toast.LENGTH_SHORT).show();
                Log.e("ffff", "onFailure: __________________"+t.getMessage() );
            }
        };
        call.enqueue(callback);
    }


}
