package com.koddev.chatapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAALmG5SlY:APA91bHmZEN0DM46CSRAUAxLRKcnG_6aZZDQzZjUBtw93cQTf6nzPHBIB_pYkBTwryuRHg2GJQwMa9soykf1fcGyVbOpDtPF640rpgIxwJhK1CdsbNY5v0X1IQXIeOHwbieCP32AXPZa"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
