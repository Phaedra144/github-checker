package httpconnection;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Szilvi on 2017. 10. 06..
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String authToken = System.getenv("COMMIT_CHECKER");
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Authorization", authToken);
        builder.addHeader("Accept", "application/vnd.github.v3+json");

        return chain.proceed(builder.build());
    }
}
