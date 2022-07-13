package com.abhilash.githubissues.model.network

import com.abhilash.githubissues.model.entities.Comment
import com.abhilash.githubissues.model.entities.Issue
import com.abhilash.githubissues.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IssuesApiService {
    @GET(AppConstants.API_ENDPOINT_ISSUES)
    suspend fun getAllIssues(): Response<List<Issue>>

    @GET("${AppConstants.API_ENDPOINT_ISSUES}/{issueId}${AppConstants.API_ENDPOINT_COMMENTS}")
    suspend fun getAllCommentsForSelectedIssue(@Path(value = "issueId") issueId: String): Response<List<Comment>>

}