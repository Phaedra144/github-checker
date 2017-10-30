package httpconnection;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
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
