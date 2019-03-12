package com.example.myapplication;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Myserver {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("phone") String phone);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("username") String username,
                                 @Field("password") String password);
}
