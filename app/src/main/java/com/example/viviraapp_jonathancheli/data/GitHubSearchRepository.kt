package com.example.viviraapp_jonathancheli.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.viviraapp_jonathancheli.api.GitHubSearchAPI
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GitHubSearchRepository @Inject constructor(private val githubApi: GitHubSearchAPI) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GithubPagingSource(githubApi, query) }
        ).liveData
}