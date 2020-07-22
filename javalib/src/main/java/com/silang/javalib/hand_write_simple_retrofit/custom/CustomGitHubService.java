package com.silang.javalib.hand_write_simple_retrofit.custom;

import com.silang.javalib.hand_write_simple_retrofit.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CustomGitHubService {
    @MyGet("users/{user}/repos")
    MyCall<List<Repo>> listRepos(@MyPath("user") String user);

}
