package httpconnection;

import models.GfCommits;
import models.Repo;
import models.RepoSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Szilvi on 2017. 10. 03..
 */
public interface GitHubAPIService {

    @GET("orgs/{org}/repos")
    Call<List<Repo>> getOrgRepos(@Path("org") String org);

    @GET("search/repositories?q=topic:oracle+topic:pallida&per_page=100")
    Call<RepoSearchResult> getSearchedRepos();

    @GET("repos/{owner}/{repo}/commits?per_page=100")
    Call<List<GfCommits>> getClassCommits(@Path("owner") String owner, @Path("repo") String repo);


}
