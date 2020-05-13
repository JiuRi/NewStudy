package com.mingpao.xuexi;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APi {

    // @GET注解的作用:采用Get方法发送网络请求
    // getNews(...) = 接收网络请求数据的方法
    // 其中返回类型为Call<News>，News是接收数据的类（即上面定义的News类）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAazjo0_4:APA91bEDIU43zhe5HswSp7Xq4kbYcHmZjWzRtlVBTlQzc-Z4HvnfPPhQoJadVLrEiHOESRbltpFwxPcWIjWUdWoEebwtwLMLQfBhCIteCek6MawaXjrXPq4PfsG2NDaZDgvORVwxoyhq"
            })

    @POST("fcm/send")
    Call<ResponseBody> pushMessage(@Body RequestBody messagebody );

}
