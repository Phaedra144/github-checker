package com.greenfox.szilvi.githubchecker.httpconnection;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
@Service
public class GitHubRetrofit {

    AuthInterceptor authInterceptor = new AuthInterceptor();
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(authInterceptor).build();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build();

    GitHubAPIService service = retrofit.create(GitHubAPIService.class);

    public GitHubAPIService getService() {
        return service;
    }
}
