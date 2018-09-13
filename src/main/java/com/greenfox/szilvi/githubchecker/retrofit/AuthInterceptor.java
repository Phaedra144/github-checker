package com.greenfox.szilvi.githubchecker.retrofit;

import com.greenfox.szilvi.githubchecker.user.service.UserHandling;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        UserHandling userHandling = new UserHandling();
        String accessToken = userHandling.findLastUser().getAccessToken();
        Request.Builder builder = chain.request().newBuilder();
        if(accessToken != null){
            builder.addHeader("Authorization", "token " + accessToken);
        }
        builder.addHeader("Accept", "application/vnd.github.v3+json");

        return chain.proceed(builder.build());
    }
}
