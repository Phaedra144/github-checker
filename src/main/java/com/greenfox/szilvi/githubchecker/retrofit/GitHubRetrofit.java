package com.greenfox.szilvi.githubchecker.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Service
public class GitHubRetrofit {

    public Retrofit getRetrofit() {
        AuthInterceptor authInterceptor = new AuthInterceptor();
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(authInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit;
    }
}
