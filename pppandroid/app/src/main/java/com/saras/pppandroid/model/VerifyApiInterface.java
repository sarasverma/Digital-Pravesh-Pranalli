package com.saras.pppandroid.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface VerifyApiInterface {

    @POST("/verify")
    Call<Boolean> checkQuery(@Body VerifyApiQuery query);
}
