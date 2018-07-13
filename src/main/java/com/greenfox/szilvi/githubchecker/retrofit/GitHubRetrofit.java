package com.greenfox.szilvi.githubchecker.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Service
public class GitHubRetrofit {

//    @Bean
//    public AuthInterceptor getAuthInterceptor() {
//        return new AuthInterceptor();
//    }


    public Retrofit getRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit;
    }
}
