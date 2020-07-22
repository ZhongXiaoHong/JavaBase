package com.silang.javalib.hand_write_simple_retrofit;

import com.silang.javalib.hand_write_simple_retrofit.custom.CustomGitHubService;
import com.silang.javalib.hand_write_simple_retrofit.custom.MyCallback;
import com.silang.javalib.hand_write_simple_retrofit.custom.SimpleRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestSimpleRetrofit {


    public static void main(String[] args) {

        CustomGitHubService service = new SimpleRetrofit().create(CustomGitHubService.class);
        service.listRepos("zhongxiaohong").enqueue(new MyCallback<List<Repo>>() {
            @Override
            public void onResonse(List<Repo> repos) {
                System.out.println("########"+repos.toString());
            }
        });
        System.out.println("*********");


    }

    private static void requestOfficialRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> result = service.listRepos("zhongxiaohong");
        result.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

}
